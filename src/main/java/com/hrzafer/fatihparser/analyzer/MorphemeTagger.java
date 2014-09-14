/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer;

/**
 *
 * @author test
 */
public interface MorphemeTagger {

    public String tag(String morpheme);
    public boolean tagExists(String morpheme);

}
