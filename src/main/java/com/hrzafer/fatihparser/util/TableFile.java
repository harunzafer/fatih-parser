/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.util;

import java.util.Map;

/**
 *
 * @author hrzafer
 */
public class TableFile {
    
    Map<String, String[]> file;

    public TableFile(Map<String, String[]> file) {
        this.file = file;
    }
    
    public boolean containsKey(String key){
        return file.containsKey(key);
    }
    
    public String[] getValues(String key){
        return file.get(key);
    }
    
    
    
}
