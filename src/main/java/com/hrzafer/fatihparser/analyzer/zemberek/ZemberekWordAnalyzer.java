/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.analyzer.zemberek;

import com.hrzafer.fatihparser.Language;
import com.hrzafer.fatihparser.analyzer.WordAnalyzer;
import com.hrzafer.fatihparser.structure.Morpheme;
import com.hrzafer.fatihparser.structure.Word;
import com.hrzafer.fatihparser.util.TableFileReader;
import java.util.ArrayList;
import java.util.List;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;
import net.zemberek.yapi.Kelime;
import net.zemberek.yapi.ek.Ek;


/**
 * Bu sınıf bir kelimeyi zemberek vasıtası ile çözümler. Kelimenin her çözümünü Word sınıfı olarak oluşturur.<br>
 * Ve bu çözümleri bir List<Word> olarak döndürür.
 * @author hrzafer
 */
public class ZemberekWordAnalyzer implements WordAnalyzer {

    private Zemberek zemberek;  
    private ManualRootTagger rootTagger;
    private ZemberekMorphemeTagger morphemeTagger;
    public ZemberekWordAnalyzer(Zemberek zemberek, ManualRootTagger rootTagger, ZemberekMorphemeTagger morphemeTagger) {
        this.zemberek = zemberek;
        this.rootTagger = rootTagger;
        this.morphemeTagger = morphemeTagger;
    }
    
    private Kelime[] solutions = null;
    private List<String[]> solutionSurfaces = null;
    private boolean debugMode = false;
    
//    public ZemberekWordAnalyzer(Language language) {
//    
//    
//        if(language == Language.TR){
//            zemberek = new Zemberek(new TurkiyeTurkcesi());
//        }
//        else if (language == Language.TK){
//            zemberek = new Zemberek(new Turkmence());
//        }
//    }




    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * Bu sınıf bir kelimeyi zemberek vasıtası ile çözümler. Kelimenin her çözümünü Word sınıfı olarak oluşturur.<br>
     * Ve bu çözümleri bir List<Word> olarak döndürür.
     * @param token kelime ör:"kalemler"
     * @return Kelimenin çözümlerinin yer aldığı Word listesi
     */
    public List<Word> analyze(String token) {
        getSolutionsAndSurfacesAndTryToMatch(token);
        List<Word> analyses = getAnalyses(solutions, solutionSurfaces);
        return getManualRootAnalyses(analyses);
    }

    private List<Word> getManualRootAnalyses(List<Word> analyses){
        for (int i = 0; i < analyses.size(); i++) {
            List <Word> newWords = rootTagger.tag(analyses.get(i));
            if (!newWords.isEmpty()){
                analyses.remove(i);
                analyses.addAll(i, newWords);
                i += newWords.size()-1;
            }
        }
        return analyses;
    }

        /**
     * Gets solutions as Kelime[] from the Zemberek<br>
     * Gets surfaces as List<String[]> from the Zemberek<br>
     * Checks if each solutions fits to its corresponding surface<br>
     * If not trys 100 times until solutions and surfaces match.<br>
     * Note: This method is especially needed because of inconsistency of zemberek.kelimeAyristir method.<br>
     * @param token : a word, ex:"kalemler"
     * @return false if after 100 trials a matching solutions-surfaces pair cannot be found
     */
    private boolean getSolutionsAndSurfacesAndTryToMatch(String token) {
        for (int i = 0; i < 100; i++) {
            solutions = zemberek.kelimeCozumle(token);//Kelimenin Ek etiketlerine göre çözümlenmiş biçimlerini al

            if (debugMode)
            for (Kelime kelime : solutions) {
                System.out.println(kelime.toString());
                System.out.println(kelime.kok().tip().toString());

            }

            solutionSurfaces = zemberek.kelimeAyristir(token);//Kelimenin Ek yüzeylerine göre çözümlenmiş biçimlerini al
            if (matches(solutions, solutionSurfaces)) {
                return true;
            }
        }
        return false;
    }

      /**
     * Checks if a solutions matches to a surface
     */
    private boolean matches(Kelime[] solutions, List<String[]> solutionSurfaces) {
        if (solutions.length != solutionSurfaces.size()) {
            throw new RuntimeException("Size (" + solutions.length + ") of solutions (Kelime[]) is not equal to size (" + solutionSurfaces.size() + ") of surfaces (List<String[]>)");
        }
        for (int i = 0; i < solutions.length; i++) {
            Ek[] suffixes = solutions[i].ekDizisi(); // Ekler
            String[] surfaces = solutionSurfaces.get(i); //Eklerin yüzey biçimleri
            if (surfaces.length != suffixes.length) {
                return false;
            }
        }
        return true;
    }

    private String getRootType(Kelime kelime) {
        return kelime.kok().tip().toString();
    }


    private List<Word> getAnalyses(Kelime[] solutions, List<String[]> solutionSurfaces) {
        List<Word> analyses = new ArrayList<Word>();
        for (int i = 0; i < solutions.length; i++) {
            Ek[] suffixes = solutions[i].ekDizisi();    //Ekler
            String[] surfaces = solutionSurfaces.get(i); //Eklerin yüzey biçimleri
            String rootType = getRootType(solutions[i]);
            Word word = null;
            try {
                word = zemberekAnalysisToWord(surfaces, suffixes, rootType);
            } catch (RuntimeException e) {
                System.out.println("Zemberek Exception: " + e.getMessage());
            }
            if (word != null) {
                analyses.add(word);
            }
        }
//        if (analyses.isEmpty()){
//            return null;
//        }
        return analyses;
    }

    /**
     * Creates a Word object from a zemberek analysis which is a surface/suffix pair
     */
    private Word zemberekAnalysisToWord(String[] surfaces, Ek[] suffixes, String rootType) {        
        List<Morpheme> morphemes = new ArrayList<Morpheme>();
        if (suffixes.length != surfaces.length) {
            throw new ZemberekException(suffixes.length, surfaces.length);
        }
        rootType = morphemeTagger.tag(rootType);
        Morpheme root = new Morpheme(surfaces[0], rootType);
        morphemes.add(root);
        for (int i = 1; i < surfaces.length; i++) {
            if (morphemeTagger.tagExists(suffixes[i].toString())) {
                morphemes.add(new Morpheme(surfaces[i], morphemeTagger.tag(suffixes[i].toString())));
            } else {
                return null;
            }
        }
        return new Word(morphemes);
    }

    // Testing area for this class
    public static void main(String[] args) {

        Zemberek zemberek = zemberek = new Zemberek(new TurkiyeTurkcesi());
        ManualRootTagger rootTagger = new ManualRootTagger(TableFileReader.read(Language.TR.getManualRootAnalysesFilePath()));
        ZemberekMorphemeTagger morphemeTagger = new ZemberekMorphemeTagger(TableFileReader.read(Language.TR.getZemberekTagsFilePath()));
        WordAnalyzer analyzer = new ZemberekWordAnalyzer(zemberek, rootTagger, morphemeTagger);
        ((ZemberekWordAnalyzer)analyzer).setDebugMode(true);
        List<Word> analyses = analyzer.analyze("olmuştum");

        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10000; i++) {
            zemberek.kelimeCozumle("kitaplarımdaki");
        }
        
        long stopTime = System.currentTimeMillis();
        
        long runTime = stopTime - startTime;
        
        System.out.println("Run time: " + runTime);
        
        for (Word word : analyses) {
            System.out.println(word.toString());
        }

    }
}
