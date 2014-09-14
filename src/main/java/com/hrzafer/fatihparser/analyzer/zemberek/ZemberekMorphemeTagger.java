/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer.zemberek;

import com.hrzafer.fatihparser.analyzer.MorphemeTagger;
import com.hrzafer.fatihparser.util.TableFile;

/**
 *
 * @author hrzafer
 */
public class ZemberekMorphemeTagger implements MorphemeTagger {

    private TableFile tagsTable ;

    public ZemberekMorphemeTagger(TableFile tagsTable) {
        this.tagsTable = tagsTable;
    }

    public boolean tagExists(String morpheme){
        return tagsTable.containsKey(morpheme);
    }

    public String tag(String morpheme){
        return tagsTable.getValues(morpheme)[0];
    }

}
