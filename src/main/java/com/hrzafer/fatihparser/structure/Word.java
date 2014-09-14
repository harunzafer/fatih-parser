package com.hrzafer.fatihparser.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * @author hrzafer
 */
public class Word implements Iterable<Morpheme> {

    private final List<Morpheme> morphemes;

    public Word(List<Morpheme> morphemes) {
        this.morphemes = morphemes;
    }

    public List<Morpheme> getCopyOfMorphemes() {
        List<Morpheme> copy = new ArrayList<Morpheme>(morphemes);
        Collections.copy(copy, morphemes);
        return copy;
    }

    public Morpheme getMorphemeAt(int position){
        return morphemes.get(position);
    }

    public Morpheme getLastMorpheme(){
        return morphemes.get(morphemes.size()-1);
    }

    public Morpheme getFirstMorpheme(){
        return morphemes.get(0);
    }

    public String getSurface() {
        StringBuilder sb = new StringBuilder();
        for (Morpheme morpheme : morphemes) {
            sb.append(morpheme.getSurface());
        }
        return sb.toString();
    }

    public String toPosTagged() {
        StringBuilder sb = new StringBuilder();
        for (Morpheme morpheme : morphemes) {
            sb.append(morpheme.getTag()).append(" ");
        }
        return sb.toString().trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Morpheme morpheme : morphemes) {
            sb.append(morpheme.toString()).append(" ");
        }
        return sb.toString().trim();
    }

    public Iterator<Morpheme> iterator() {
        return new Iterator<Morpheme>() {

            int pos = 0;

            public boolean hasNext() {
                return pos < morphemes.size();
            }

            public Morpheme next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return morphemes.get(pos++);
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
