/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.analyzer;

import com.hrzafer.fatihparser.structure.Word;
import java.util.List;

/**
 *
 * @author test
 */
public interface WordAnalyzer {

    public abstract List<Word> analyze(String token);

}
