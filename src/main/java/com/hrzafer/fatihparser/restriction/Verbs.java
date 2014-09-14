/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.Language;
import com.hrzafer.fatihparser.util.TableFile;
import com.hrzafer.fatihparser.util.TableFileReader;

/**
 *
 * @author test
 */
public class Verbs {

    private TableFile verbsTable;
    private final int FREQUENCY = 0;
    private final int TRANSITIVE = 1;
    private final int PASSIVE = 2;
    private static Verbs instance = null;

    public static Verbs getInstance() {
        if (instance == null) {
            instance = new Verbs();
        }
        return instance;
    }

    private Verbs() {
        verbsTable = TableFileReader.read(Language.TR.getVerbsFilePath());
    }

    public boolean isTransitive(String verb) {
        if (verb == null){
            return false;
        }
        if (!verbsTable.containsKey(verb)){
            throw new VerbNotFoundException(verb);
        }
        String[] properties = verbsTable.getValues(verb);
        return properties[TRANSITIVE].equals("T");
    }

    public boolean isPassive(String verb) {
        if (verb == null){
            return false;
        }
        String[] properties = verbsTable.getValues(verb);
        return properties[PASSIVE].equals("T");
    }

    public boolean isNonTransitivePassive(String verb) {
        if (verb == null){
            return false;
        }
        String[] properties = verbsTable.getValues(verb);
        return properties[TRANSITIVE].equals("F") && properties[PASSIVE].equals("T");
    }
}
