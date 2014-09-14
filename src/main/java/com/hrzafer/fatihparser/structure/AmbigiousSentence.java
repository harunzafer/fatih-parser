/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.structure;

import com.hrzafer.fatihparser.util.SubListCombinator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class AmbigiousSentence {

    private List<List<Word>> ambigiousWords;

    public AmbigiousSentence(List<List<Word>> ambigiousWords) {
        this.ambigiousWords = ambigiousWords;
    }

    public List<Sentence> getPossibleSentences() {
        List<List<Word>> combinations = new ArrayList<List<Word>>();

        combinations = SubListCombinator.combinate(ambigiousWords);

        List<Sentence> sentences = new ArrayList<Sentence>();
        for (List<Word> combination : combinations) {
            sentences.add(new Sentence(combination));
        }

        return sentences;
    }
}
