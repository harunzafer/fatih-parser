/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.util;

import com.hrzafer.fatihparser.Language;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author test
 */
public class TableFileReader {

    public static TableFile read(String path) {
        Map<String, String[]> tagMap = new HashMap<String, String[]>();
        List<String> tagLines = Resources.getLines(path);
        for (String tagLine : tagLines) {
            tagLine = removeComment(tagLine);
            if (!tagLine.isEmpty()){
                String[] columns = tagLine.split("[\\s]+");
                String id = columns[0];
                tagMap.put(id, Arrays.copyOfRange(columns, 1, columns.length));
            }
        }
        return new TableFile(tagMap);
    }

    public static String removeComment(String line){
        line = line.trim();
        int endIndex = line.indexOf("//");
        if (endIndex > -1){
            return line.substring(0, endIndex);
        }
        return line;
    }
    
    public static void main(String[] args) {
        String[] str = TableFileReader.read(Language.TR.getZemberekTagsFilePath()).getValues("ZAMIR");
        System.out.println(str[0] );
    }
}
