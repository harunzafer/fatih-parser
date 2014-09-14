package com.hrzafer.fatihparser.analyzer;

import com.hrzafer.fatihparser.Language;
import com.hrzafer.fatihparser.analyzer.manual.ManualWordAnalyzer;
import com.hrzafer.fatihparser.analyzer.zemberek.ZemberekWordAnalyzer;
import com.hrzafer.fatihparser.analyzer.zemberek.ZemberekWordAnalyzerBuilder;
import com.hrzafer.fatihparser.util.TableFile;
import com.hrzafer.fatihparser.util.TableFileReader;

/**
 *
 * @author hrzafer
 */
public class SentenceAnalyzerBuilder {
    
    public static SentenceAnalyzer build(Language language){
        SentenceAnalyzer analyzer = new SentenceAnalyzer();
        ManualWordAnalyzer manualWordAnalyzer = buildManualWordAnalyzer(language);

        if (language.equals(Language.TR)){
            ZemberekWordAnalyzer zemberekWordAnalyzer = ZemberekWordAnalyzerBuilder.build(language);
            analyzer.addWordAnalyzer(manualWordAnalyzer);
            analyzer.addWordAnalyzer(zemberekWordAnalyzer);
        }
        else if (language.equals(Language.TK)){
            analyzer.addWordAnalyzer(manualWordAnalyzer);
        }
        
        return analyzer;
    }
    
    private static ManualWordAnalyzer buildManualWordAnalyzer(Language language){
        TableFile analysesTable = TableFileReader.read(language.getManualWordAnalysesFilePath());
        return new ManualWordAnalyzer(analysesTable);
    }
    
}
