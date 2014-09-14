/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.format;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class ParseDetail {

    private final List<String> categories;
    private static final String[] elements = {"SS", "NP", "JP", "AP", "VP", "VJ", "VN", "VA", "IP"};
    private static final String[] wordGroups = {"NC", "JC", "VJG", "VNG", "VAG", "RG", "PG", "CG", "TG",
        "PNG", "IG", "NG", "CV", "SG"};
    private static final String[] nonTerminals = {"noun", "adj", "adv", "pron", "verb", "posp", "conj", "intj", "prop",
        "psv", "plu", "gen", "dat", "loc", "abl", "acc", "ins", "rel", "p1s",
        "p2s", "p3s", "p1p", "p2p", "p3p", "Poss", "1sg", "2sg", "3sg", "1pl",
        "2pl", "3pl", "tense"
    };

    public List<String> getCategoriesToRemove() {
        return categories;
    }

    private ParseDetail(String[]... categoriesToRemove) {
        categories = new ArrayList<String>();
        for (String[] cats : categoriesToRemove) {
            Collections.addAll(categories, cats);
        }
    }

    public static List<ParseDetail> getAllLevels() {

        List<ParseDetail> levels;
        levels = new ArrayList<ParseDetail>();
        levels.add(Level_0);
        levels.add(Level_1);
        levels.add(Level_2);
        levels.add(Level_3);
        levels.add(Level_4);
        return levels;
    }

    public static final ParseDetail Level_0 = new ParseDetail();
    public static final ParseDetail Level_1 = new ParseDetail(elements);
    public static final ParseDetail Level_2 = new ParseDetail(elements, nonTerminals);
    public static final ParseDetail Level_3 = new ParseDetail(elements, wordGroups);
    public static final ParseDetail Level_4 = new ParseDetail(elements, wordGroups, nonTerminals);
}
