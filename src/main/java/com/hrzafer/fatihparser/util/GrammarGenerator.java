/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class GrammarGenerator {

    public static String generateGrammar(List<String> rightSymbols) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= rightSymbols.size(); i++) {
            Combinations<String> combinations = new Combinations<String>(rightSymbols, i);
            sb.append(permuteCombinations(combinations));
        }
        return sb.toString();
    }

    private static StringBuilder permuteCombinations(Combinations<String> combinations) {
        StringBuilder sb = new StringBuilder();
        for (List<String> combination : combinations) {
            if (containsElement(combination, MANDATORY_SYMBOL)) {
                Permutations<String> permutations = new Permutations<String>(combination);
                sb.append(toXmlRules(permutations));
            }
        }
        return sb;
    }

    private static boolean containsElement(List<String> list, String element) {
        for (String string : list) {
            if (string.equals(element)) {
                return true;
            }
        }
        return false;
    }



     private static boolean  yanyanaMI(List<String> permutation){
          for (int i = 0; i < permutation.size(); i++) {
             if (permutation.get(i).equals("Pre")){
                 if (i>0){
                     if (permutation.get(i-1).equals("IObj"))
                         return true;
                 }
             }
         }
        return false;
     }


    private static StringBuilder toXmlRules(Permutations<String> permutations) {
        StringBuilder sb = new StringBuilder();
        for (List<String> permutation : permutations) {
        if (yanyanaMI(permutation))
            sb.append(toXmlRule(LEFT_SYMBOL, permutation));
        }
        return sb;
    }

    private static StringBuilder toXmlRule(String leftSymbol, List<String> rightSymbols) {
        StringBuilder sb = new StringBuilder("<rule category=\"" + leftSymbol + "\">\n");
        for (String symbol : rightSymbols) {
            sb.append("\t<category terminal=\"true\" name=\"" + symbol + "\"/>\n");
        }
        sb.append("</rule>\n");
        return sb;
    }


    public static String MANDATORY_SYMBOL = "Pre";
    public static String LEFT_SYMBOL = "S";
    private static List<String> RIGHT_SYMBOLS = new ArrayList<String>();
    static {
        RIGHT_SYMBOLS.add("Sub");
        RIGHT_SYMBOLS.add("IObj");
        RIGHT_SYMBOLS.add("Pre");
        RIGHT_SYMBOLS.add("Comp");
        RIGHT_SYMBOLS.add("Adv");
        RIGHT_SYMBOLS.add("Excl");
        //RIGHT_SYMBOLS.add("CD");
    }

    public static void main(String[] args) {
        String grammar = generateGrammar(RIGHT_SYMBOLS);
        IO.write("S2_grammar.txt", grammar);
        //System.out.println(grammar);
    }
}
