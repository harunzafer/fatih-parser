package com.hrzafer.fatihparser;

import com.hrzafer.fatihparser.format.ParseDetail;
import com.hrzafer.fatihparser.format.ParseLanguage;
import com.hrzafer.fatihparser.structure.Sentence;
import java.util.List;

/**
 *
 * @author test
 */
public class ParsedSentence {
    private List<BracketedTree> trees;
    private Sentence sentence;

    /**
     * Bir cümle (Sentence) ile bu cümleye ait çözümleri (List<BracketedTree>) içerir.
     */
    public ParsedSentence(List<BracketedTree> trees, Sentence sentence) {
        this.trees = trees;
        this.sentence = sentence;
    }
    
    public Sentence getSentence() {
        return sentence;
    }    

    public List<BracketedTree> getTrees() {
        return trees;
    }

    public void setFormat(ParseDetail parseDetail, ParseLanguage parseLanguage){
        for (BracketedTree bracketedTree : trees) {
            bracketedTree.setParseDetail(parseDetail);
            bracketedTree.setParseLanguage(parseLanguage);
        }
    }
    
    

}
