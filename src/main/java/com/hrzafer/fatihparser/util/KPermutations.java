/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.hrzafer.fatihparser.util;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author hrzafer
 */
public class KPermutations<T> implements Iterable<List<T>> {
    Combinations<T> combinations;

    public KPermutations(List<T> list, int k) {
        if (k<1){
            throw new IllegalArgumentException("Subset length k must me at least 1");
        }
        combinations = new Combinations<T>(list, k);
    }

    public Iterator<List<T>> iterator() {
        return new Iterator<List<T>>() {
            Iterator<List<T>> it = combinations.iterator();
            Permutations<T> permutations = new Permutations<T>(combinations.iterator().next());

            // Has more combinations but no more permutation for current combination
            public boolean hasNext() {
                if (combinations.iterator().hasNext() && !permutations.iterator().hasNext()){
                    permutations = new Permutations<T>(combinations.iterator().next());
                    return true;
                }
                //Has more permutation for current combination
                else if (permutations.iterator().hasNext()){
                    return true;
                }
                // No more combination and permutation
                return false;
            }

            public List<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return permutations.iterator().next();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


}
