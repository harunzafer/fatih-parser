/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.format;

import com.hrzafer.fatihparser.BracketedTree;

/**
 *
 * @author hrzafer
 */
public class ParseSimplifier {
    
    public static String simplify(String tree, ParseDetail detail){
        StringBuilder sb= new StringBuilder(tree);
        for (String category : detail.getCategoriesToRemove()) {
            BracketedTree.removeCategory(sb, category);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        
        String tree = "[S[SS[Sub[NP[prop[zeynep]]]][SS[DObj[NP[JC[JP[adj[bu]]][NP[noun[roman]]]]][acc[ı]]][SS[Pre[VP[VP[verb[oku]]][tense[du]]]]]]]]";
        System.out.println(simplify(tree, ParseDetail.Level_0));
        System.out.println(simplify(tree, ParseDetail.Level_1));
        System.out.println(simplify(tree, ParseDetail.Level_2));
        System.out.println(simplify(tree, ParseDetail.Level_3));
        System.out.println(simplify(tree, ParseDetail.Level_4));
        
    }
    
}
