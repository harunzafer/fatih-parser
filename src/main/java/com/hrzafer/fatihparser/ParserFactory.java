package com.hrzafer.fatihparser;


import com.hrzafer.fatihparser.analyzer.SentenceAnalyzer;
import com.hrzafer.fatihparser.analyzer.SentenceAnalyzerBuilder;
import com.hrzafer.fatihparser.restriction.ParseTreeValidator;
import com.hrzafer.fatihparser.restriction.ParseTreeValidatorBuilder;
import edu.osu.ling.pep.EarleyParser;
import edu.osu.ling.pep.Grammar;
import edu.osu.ling.pep.Pep;
import edu.osu.ling.pep.PepException;
import java.io.File;

/**
 *
 * @author hrzafer
 */
public class ParserFactory {
    public static Parser create(Language language){
        SentenceAnalyzer analyzer = SentenceAnalyzerBuilder.build(language);
        EarleyParser parser = buildEarlyParser(language.getGrammarFilePath());
        ParseTreeValidator validator = ParseTreeValidatorBuilder.build(language);
        return new Parser(analyzer, parser, validator);
    }
    
    private static EarleyParser buildEarlyParser(String grammarFile) {
        EarleyParser ep = null;
        try {
            Grammar grammar = new Pep.GrammarParser(new File(grammarFile)).parse();
            ep = new EarleyParser(grammar);
        } catch (PepException ex) {
            throw new RuntimeException("EarlyParser could not be built!!!");
        }
        return ep;
    }
    
}
