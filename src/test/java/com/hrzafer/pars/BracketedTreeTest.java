/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.pars;

import com.hrzafer.fatihparser.BracketedTree;
import com.hrzafer.fatihparser.restriction.ValidationRule;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hrzafer
 */
public class BracketedTreeTest {
    

    /**
     * Test of getNonTerminal method, of class BracketedTree.
     */
    @Test
    public void testGetNonTerminal() {
        System.out.println("getNonTerminal");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "verb";
        String expResult = "git";
        String result = BracketedTree.getNonTerminal(tree, category);
        assertEquals(expResult, result);
        
        category = "tense";
        expResult = "ti";
        result = BracketedTree.getNonTerminal(tree, category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getSubTreeList method, of class BracketedTree.
     */
    @Test
    public void testGetSubTreeList() {
        System.out.println("getSubTreeList");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "VP";
        List expResult = new ArrayList();
        expResult.add("[VP[VP[verb[git]]][tense[ti]]]");
        expResult.add("[VP[verb[git]]]");
        List result = BracketedTree.getSubTreeList(tree, category);
        System.out.println(result);
        System.out.println(expResult);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getSubTree method, of class BracketedTree.
     */
    @Test
    public void testGetSubTree() {
        System.out.println("getSubTree");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "VP";
        String expResult = "[VP[VP[verb[git]]][tense[ti]]]";
        String result = BracketedTree.getSubTree(tree, category);
        System.out.println(result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of removeCategory method, of class BracketedTree.
     */
    @Test
    public void testRemoveCategory() {
        System.out.println("removeCategory");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "S";
        String expResult = "[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]";
        String result = BracketedTree.removeCategory(tree, category);
        assertEquals(expResult, result);
        
        tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        category = "SS";
        expResult = "[S[Pre[VP[VP[verb[git]]][tense[ti]]]]]";
        result = BracketedTree.removeCategory(tree, category);
        assertEquals(expResult, result);
        
        tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        category = "VP";
        expResult = "[S[SS[Pre[verb[git]][tense[ti]]]]]";
        result = BracketedTree.removeCategory(tree, category);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of removeCategory method, of class BracketedTree.
     */
    @Test
    public void testRemoveCategory_StringBuilder() {
        System.out.println("removeCategory");
        StringBuilder tree = new StringBuilder("[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]");
        String category = "VP";
        String expResult = "[S[SS[Pre[verb[git]][tense[ti]]]]]";
        String result = BracketedTree.removeCategory(tree, category);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findEndIndex method, of class BracketedTree.
     */
    @Test
    public void testFindEndIndex() {
        System.out.println("findEndIndex");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        int beginIndex = 5;
        int expResult = 39;
        int result = BracketedTree.findEndIndex(tree, beginIndex);
        assertEquals(expResult, result);
        
        beginIndex = 0; //category "S"
        expResult = 41;
        result = BracketedTree.findEndIndex(tree, beginIndex);
        assertEquals(expResult, result);
        
        beginIndex = 2;//category "SS"
        expResult = 40;
        result = BracketedTree.findEndIndex(tree, beginIndex);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of findBeginIndex method, of class BracketedTree.
     */
    @Test
    public void testFindBeginIndex_3args() {
        System.out.println("findBeginIndex");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "VP";
        int fromIndex = 11;
        int expResult = 12;
        int result = BracketedTree.findBeginIndex(tree, category, fromIndex);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of findBeginIndex method, of class BracketedTree.
     */
    @Test
    public void testFindBeginIndex_String_String() {
        System.out.println("findBeginIndex");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "S";
        int expResult = 0;
        int result = BracketedTree.findBeginIndex(tree, category);
        assertEquals(expResult, result);
        
        category = "Pre";
        expResult = 5;
        result = BracketedTree.findBeginIndex(tree, category);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of findBeginIndexes method, of class BracketedTree.
     */
    @Test
    public void testFindBeginIndexes() {
        System.out.println("findBeginIndexes");
        String tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]";
        String category = "VP";
        List expResult = new ArrayList();
        expResult.add(9);
        expResult.add(12);
        List result = BracketedTree.findBeginIndexes(tree, category);
        assertEquals(expResult, result);
        
    }

    /**
     * Test of isValid method, of class BracketedTree.
     */
    @Test
    public void testIsValid() {
        System.out.println("isValid");
        BracketedTree instance = null;
        boolean expResult = false;
        boolean result = instance.isValid();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setInvalidator method, of class BracketedTree.
     */
    @Test
    public void testSetInvalidator() {
        System.out.println("setInvalidator");
        ValidationRule rule = null;
        BracketedTree instance = null;
        instance.setInvalidator(rule);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInvalidatorDescription method, of class BracketedTree.
     */
    @Test
    public void testGetInvalidatorDescription() {
        System.out.println("getInvalidatorDescription");
        BracketedTree instance = null;
        String expResult = "";
        String result = instance.getInvalidatorDescription();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class BracketedTree.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        BracketedTree instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of print method, of class BracketedTree.
     */
    @Test
    public void testPrint() {
        System.out.println("print");
        BracketedTree instance = null;
        String expResult = "";
        String result = instance.print();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    

    
}
