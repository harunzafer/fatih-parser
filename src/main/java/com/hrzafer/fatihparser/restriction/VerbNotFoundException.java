/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.restriction;

/**
 *
 * @author test
 */
public class VerbNotFoundException extends RuntimeException {

    public VerbNotFoundException(String verb) {
        System.out.println("The verb " + verb + " can not found!");

    }
}
