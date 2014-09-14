/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer.zemberek;

/**
 *
 * @author hrzafer
 */
public class ZemberekException extends RuntimeException{

    public ZemberekException(int suffixCount, int surfaceCount) {
        super("Suffix count ("+suffixCount+") is not equal to surface count ("+surfaceCount+")");
    }

}
