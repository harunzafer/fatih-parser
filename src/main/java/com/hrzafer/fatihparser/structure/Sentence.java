/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.structure;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author hrzafer
 */
public class Sentence implements Iterable<Word> {

    private final List<Word> words;

    public Sentence(List<Word> words) {
        this.words = words;
    }

    public String toPosTagged() {
        StringBuilder sb = new StringBuilder();
        for (Word word : words) {
            sb.append(word.toPosTagged()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Word word : words) {
            sb.append(quote(word.toString())).append(" ");
        }
        return sb.toString().trim();
    }

    private String quote(String str) {
        return "\"" + str + "\"";
    }

    public Iterator<Word> iterator() {
        return new Iterator<Word>() {

            int pos = 0;

            public boolean hasNext() {
                return pos < words.size();
            }

            public Word next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return words.get(pos++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
