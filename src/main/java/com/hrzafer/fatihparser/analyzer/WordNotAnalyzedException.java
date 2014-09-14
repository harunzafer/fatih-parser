/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer;

/**
 *
 * @author test
 */
public class WordNotAnalyzedException extends RuntimeException {

    private String word;
    public WordNotAnalyzedException(String word) {
        super("The word \"" + word + "\" can not be analyzed!!!" );
        this.word = word;
    }
    
    public String getWord(){
        return word;
    }

}
