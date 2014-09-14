/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.format;

import java.util.Map;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author hrzafer
 */
public class ParseFormatter {

    public static String format(String tree, ParseLanguage detail) {

        for (Map.Entry<String, String> entry : detail.getTags().entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            String regex = "\\[" + entry.getKey() + "\\[";
            String replacement = "\\[" + entry.getValue() + "\\[";
            tree = tree.replaceAll(regex, replacement);
        }
        return tree;
    }

    public static void main(String[] args) throws Exception {

        String parse = "[S[SS[Sub[NP[prop[zeynep]]]][SS[DObj[NP[JC[JP[adj[bu]]][NP[noun[roman]]]]][acc[ı]]][SS[Pre[VP[VP[verb[oku]]][tense[du]]]]]]]]";
        String temp;
        for (ParseDetail detail : ParseDetail.getAllLevels()) {
            for (ParseLanguage language : ParseLanguage.getAllLanguages()) {
                temp = ParseSimplifier.simplify(parse, detail);
                System.out.println(ParseFormatter.format(temp, language));
            }
        }
        
        String source = "[S[SS[Yüklem[fiilUnsuru[VP[fiilUnsuru[Fiil[git]]][FiilKipi[ti]]][Şahıs[1.tekilŞahıs[m]]]]]]]";
        System.out.println(StringEscapeUtils.escapeHtml4(source));
        
    }
}
