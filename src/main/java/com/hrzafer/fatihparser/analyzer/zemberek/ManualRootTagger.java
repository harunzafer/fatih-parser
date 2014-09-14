/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer.zemberek;

import com.hrzafer.fatihparser.structure.Morpheme;
import com.hrzafer.fatihparser.structure.Word;
import com.hrzafer.fatihparser.util.TableFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author test
 */
public class ManualRootTagger {

    TableFile rootsTable;

    public ManualRootTagger(TableFile rootsTable) {
        this.rootsTable = rootsTable;
    }

    public List<Word> tag(Word word){

        List<Word> words = new ArrayList<Word>();
        String root = word.getFirstMorpheme().getSurface();
        if (!rootsTable.containsKey(root)){
            return Collections.EMPTY_LIST;
        }

        String[] tags = rootsTable.getValues(root);

        for (String tag : tags) {
            List<Morpheme> morphemes = word.getCopyOfMorphemes();
            morphemes.set(0, new Morpheme(root, tag));
            words.add(new Word(morphemes));
        }
        return words;
    }

}
