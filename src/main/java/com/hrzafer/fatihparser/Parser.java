package com.hrzafer.fatihparser;

import com.hrzafer.fatihparser.analyzer.SentenceAnalyzer;
import com.hrzafer.fatihparser.restriction.ParseTreeValidator;
import com.hrzafer.fatihparser.structure.Sentence;
import edu.osu.ling.pep.Category;
import edu.osu.ling.pep.EarleyParser;
import edu.osu.ling.pep.Parse;
import edu.osu.ling.pep.ParseTree;
import edu.osu.ling.pep.PepException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author test
 */
public class Parser {

    private SentenceAnalyzer analyzer; //Önce cümle analiz edilir
    private EarleyParser earleyParser; //Sonra parse edilir
    private ParseTreeValidator validator; //Sonra elde edilen parse tree'ler kontrol edilir.

    public Parser(SentenceAnalyzer analyzer, EarleyParser earleyParser, ParseTreeValidator validator) {
        this.analyzer = analyzer;
        this.earleyParser = earleyParser;
        this.validator = validator;
    }

    public List<ParsedSentence> parse(String tokens) {
        return parse(tokens, "S");
    }

    public List<ParsedSentence> parse(String tokens, String nonTerminal) {
        List<Sentence> sentences = analyzer.analyze(tokens);
        List<ParsedSentence> parsedSentences = new ArrayList<ParsedSentence>();
        for (Sentence sentence : sentences) {
            List<BracketedTree> trees = parse(sentence, nonTerminal);
            validator.validate(trees);
            parsedSentences.add(new ParsedSentence(trees, sentence));
        }
        return parsedSentences;
    }

    private List<BracketedTree> parse(Sentence sentence, String category) {
        List<BracketedTree> bTrees = new ArrayList<BracketedTree>();
        Parse parse = null;
        try {
            parse = earleyParser.parse(sentence.toPosTagged(), new Category(category));
        } catch (PepException ex) {
            System.out.println(ex.getMessage());

        }
        Set<ParseTree> trees = parse.getParseTrees();
        for (ParseTree tree : trees) {
            bTrees.add(new BracketedTree(sentence, tree));
        }
        return bTrees;
    }
}
