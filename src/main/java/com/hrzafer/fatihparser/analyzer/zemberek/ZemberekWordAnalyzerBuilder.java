/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.analyzer.zemberek;

import com.hrzafer.fatihparser.Language;
import com.hrzafer.fatihparser.util.TableFileReader;
import net.zemberek.erisim.Zemberek;
import net.zemberek.tk.yapi.Turkmence;
import net.zemberek.tr.yapi.TurkiyeTurkcesi;


/**
 *
 * @author hrzafer
 */
public class ZemberekWordAnalyzerBuilder {

    public static ZemberekWordAnalyzer build(Language language) {
        Zemberek zemberek = null;
        
        if (language.equals(Language.TR)) {
            zemberek = new Zemberek(new TurkiyeTurkcesi());
        }
        
        else if (language.equals(Language.TK)) {
            zemberek = new Zemberek(new Turkmence());
        }
        
        ManualRootTagger rootTagger = new ManualRootTagger(TableFileReader.read(language.getManualRootAnalysesFilePath()));
        
        ZemberekMorphemeTagger morphemeTagger = new ZemberekMorphemeTagger(TableFileReader.read(language.getZemberekTagsFilePath()));
        
        return new ZemberekWordAnalyzer(zemberek, rootTagger, morphemeTagger);
    }
}
