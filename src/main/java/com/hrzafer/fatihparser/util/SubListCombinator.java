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
public class SubListCombinator {
      /**
     * Örneğin [["1a", "1b", "1c"], ["2a", "2b"], ["3a", "3d"]] <br>
     * şekline bir iç içe liste için tüm combinasyonların bulunduğu bir liste döndürür: <br>
     * ["1a", "2a", "3a"] <br>
     * ["1a", "2a", "3d"] <br>
     * ["1a", "2b", "3a"] <br>
     * ["1a", "2b", "3d"] <br>
     * ["1b", "2a", "3a"] <br>
     * ["1b", "2a", "3d"] <br>
     * ["1b", "2b", "3a"] <br>
     * ["1b", "2b", "3d"] <br>
     * ["1c", "2a", "3a"] <br>
     * ["1c", "2a", "3d"] <br>
     * ["1c", "2b", "3a"] <br>
     * ["1c", "2b", "3d"] <br> <br>
     * Kaynak: http://www.daniweb.com/software-development/java/threads/177956 <br>
     */
    public static <T> List<List<T>> combinate(List<List<T>> uncombinedList) {
        List<List<T>> list = new ArrayList<List<T>>();
        int index[] = new int[uncombinedList.size()];
        int combinations = combinations(uncombinedList) - 1;
        // Initialize index
        for (int i = 0; i < index.length; i++) {
            index[i] = 0;
        }
        // First combination is always valid
        List<T> combination = new ArrayList<T>();
        for (int m = 0; m < index.length; m++) {
            combination.add(uncombinedList.get(m).get(index[m]));
        }
        list.add(combination);
        for (int k = 0; k < combinations; k++) {
            combination = new ArrayList<T>();
            boolean found = false;
            // We Use reverse order
            for (int l = index.length - 1; l >= 0 && found == false; l--) {
                int currentListSize = uncombinedList.get(l).size();
                if (index[l] < currentListSize - 1) {
                    index[l] = index[l] + 1;
                    found = true;
                } else {
                    // Overflow
                    index[l] = 0;
                }
            }
            for (int m = 0; m < index.length; m++) {
                combination.add(uncombinedList.get(m).get(index[m]));
            }
            list.add(combination);
        }
        return list;
    }

    private static <T> int combinations(List<List<T>> list) {
        int count = 1;
        for (List<T> current : list) {
            count = count * current.size();
        }
        return count;
    }

}
