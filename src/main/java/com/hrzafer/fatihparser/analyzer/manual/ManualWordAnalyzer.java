/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.analyzer.manual;

import com.hrzafer.fatihparser.Language;
import com.hrzafer.fatihparser.analyzer.WordAnalyzer;
import com.hrzafer.fatihparser.structure.Morpheme;
import com.hrzafer.fatihparser.structure.Word;
import com.hrzafer.fatihparser.util.TableFile;
import com.hrzafer.fatihparser.util.TableFileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class ManualWordAnalyzer implements WordAnalyzer {

    TableFile analysesTable;

    public ManualWordAnalyzer(TableFile analysesTable) {
        this.analysesTable = analysesTable;
    }
    
    public List<Word> analyze(String token) {
        List<Word> words = new ArrayList<Word>();
        if (!analysesTable.containsKey(token)){
            return Collections.EMPTY_LIST;
        }

        String[] analyses = analysesTable.getValues(token);
        for (String analysis : analyses) {
            words.add(analysisToWord(analysis));
        }
        return words;
    }

    private Word analysisToWord(String analysis) {
        List<Morpheme> morphemes = new ArrayList<Morpheme>();
        String[] tokens = splitToMorphemes(analysis);
        for (String string : tokens) {
            morphemes.add(stringToMorpheme(string));
        }
        return new Word(morphemes);
    }

    private Morpheme stringToMorpheme(String token) {
        String surface = token.split("/")[0];
        String tag = token.split("/")[1];
        tag = tag.replace("-", "+");
        return new Morpheme(surface, tag);
    }

    private String[] splitToMorphemes(String analysis) {
        return analysis.split("\\+");
    }

    public static void main(String[] args) {
        TableFile analysisTable = TableFileReader.read(Language.TR.getManualWordAnalysesFilePath());
        WordAnalyzer analyzer = new ManualWordAnalyzer(analysisTable);
        System.out.println(analyzer.analyze("gece"));

    }

    
}
