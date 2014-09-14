/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.structure;

import edu.osu.ling.pep.PepException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author hrzafer
 */
public class Morpheme {

    private String surface;
    private String tag;

    public Morpheme(String surface, String tag) {
        this.surface = surface;
        this.tag = tag;
    }

    public String getSurface() {
        return surface;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return surface + "/" + tag;
    }





    public static void main(String[] args) throws FileNotFoundException, IOException, PepException {
        
        Morpheme a = new Morpheme("A", "A");
        Morpheme b = new Morpheme("B", "B");
        List<Morpheme> list1 = new ArrayList<Morpheme>();
        List<Morpheme> list2 = new ArrayList<Morpheme>();
        list1.add(a);
        list1.add(b);

        list2.add(a);
        list2.add(b);

        Set s = new HashSet();
        s.add(list1);
        s.add(list2);
        System.out.println(s);
        
    }



}
