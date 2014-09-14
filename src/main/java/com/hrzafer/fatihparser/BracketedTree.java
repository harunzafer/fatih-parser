package com.hrzafer.fatihparser;

import com.hrzafer.fatihparser.format.ParseDetail;
import com.hrzafer.fatihparser.format.ParseFormatter;
import com.hrzafer.fatihparser.format.ParseLanguage;
import com.hrzafer.fatihparser.format.ParseSimplifier;
import com.hrzafer.fatihparser.restriction.ValidationRule;
import com.hrzafer.fatihparser.structure.Morpheme;
import com.hrzafer.fatihparser.structure.Sentence;
import com.hrzafer.fatihparser.structure.Word;
import edu.osu.ling.pep.ParseTree;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hrzafer
 */
public class BracketedTree {

    private Sentence sentence;
    private ParseTree tree;
    private ValidationRule invalidator;
    private ParseLanguage parseLanguage = ParseLanguage.Turkish;
    private ParseDetail parseDetail = ParseDetail.Level_0;


    /**
     * Bir parse tree'nin köşeli parantez gösterimini temsil eder.
     */
    public BracketedTree(Sentence sentence, ParseTree tree) {
        this.sentence = sentence;
        this.tree = tree;
        this.invalidator = null;
    }
    
    public boolean isValid() {
        return invalidator == null;
    }

    public void setInvalidator(ValidationRule rule) {
        this.invalidator = rule;
    }

    public String getInvalidatorDescription(){
        if (isValid()){
            return "valid";
        }
        return invalidator.getDescription();
    }

    public void setParseDetail(ParseDetail parseDetail) {
        this.parseDetail = parseDetail;
    }

    public void setParseLanguage(ParseLanguage parseLanguage) {
        this.parseLanguage = parseLanguage;
    }
    
    private String simplifyAndFormat(String tree){
            String simplifiedTree = ParseSimplifier.simplify(tree, parseDetail);
            return ParseFormatter.format(simplifiedTree, parseLanguage);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(tree.toString());
        for (Word word : sentence) {
            for (Morpheme morpheme : word) {
                String tag = bracketed(morpheme.getTag());
                int index = sb.indexOf(tag) - 1;
                sb.insert(index + tag.length(), bracketed(morpheme.getSurface()));
            }
        }
        return sb.toString();
    }
    
    public String getFormattedString(){
        return simplifyAndFormat(toString());
    }

    public String print(){
        StringBuilder sb = new StringBuilder(this.getFormattedString());
        sb.append("\t").append(getInvalidatorDescription());
        return sb.toString();
    }

    
    /* Static Functions */
    
    private static String bracketed(String str) {
        return "[" + str + "]";
    }

    public static int findBeginIndex(String tree, String category, int fromIndex){
        return tree.indexOf("[" + category + "[", fromIndex);
    }

    /**
     * Bir kategorinin başlangıç index'ini bulur.
     * @param tree
     * @param category
     * @return 
     */
    public static int findBeginIndex(String tree, String category){
        return tree.indexOf("[" + category + "[");
    }
    
    /**
     * Bir kategorinin bir tree içerisindeki başlangıç indexlerini döndürür.
     * <br> Misal:
     * <br> tree = "[S[SS[Pre[VP[VP[verb[git]]][tense[ti]]]]]]"
     * <br> category = "S"
     * <br> return = List[0]
     * <br>
     * <br> Misal: (Aynı tree için)
     * <br> category = "VP"
     * <br> return = List[9,12]
     * <br>
     * <br>şeklinde çalışır
     */
    public static List<Integer> findBeginIndexes(String tree, String category){
        List<Integer> beginIndexList = new ArrayList<Integer>();
        int fromIndex = 0;
        int beginIndex = findBeginIndex(tree, category, fromIndex);
        while (beginIndex != -1){
            beginIndexList.add(beginIndex);
            fromIndex = beginIndex + category.length() + 1;
            beginIndex = findBeginIndex(tree, category, fromIndex);
        }
        return beginIndexList;
    }

    public static int findEndIndex(String tree, int beginIndex){
        int counter = 1;
        int index = beginIndex+1;
        while (counter > 0 ){
            if (tree.charAt(index)=='['){
                counter++;
            }
            if (tree.charAt(index)==']'){
                counter--;
            }
            index++;
        }
        return index-1;
    }

    public static String getNonTerminal(String tree, String category){
        int beginIndex = findBeginIndex(tree, category, 0);
        if (beginIndex == -1){
            return null;
        }
        beginIndex += category.length() + 1 ;
        int endIndex = findEndIndex(tree, beginIndex);
        return tree.substring(beginIndex + 1 , endIndex);
    }

    public static List<String> getSubTreeList(String tree, String category){
        List<Integer> beginIndexList = BracketedTree.findBeginIndexes(tree, category);
        List<String> subTreeList = new ArrayList<String>();
        for (Integer beginIndex : beginIndexList) {
            int endIndex = findEndIndex(tree, beginIndex);
            String subTree = tree.substring(beginIndex, endIndex + 1);
            subTreeList.add(subTree);
        }
        return subTreeList;
    }

    public static String getSubTree(String tree, String category){
        int beginIndex = findBeginIndex(tree, category);
        if (beginIndex == -1){
            return null;
        }

        int endIndex = findEndIndex(tree, beginIndex);
        return tree.substring(beginIndex, endIndex + 1 );
    }
    
    public static String removeCategory(String tree, String category){
        StringBuilder sb  = new StringBuilder(tree);
        return removeCategory(sb, category);
    }
    
    
    public static String removeCategory(StringBuilder tree, String category){
        int beginIndex = BracketedTree.findBeginIndex(tree.toString(), category);
        while (beginIndex != -1){
            int endIndex = findEndIndex(tree.toString(), beginIndex);
            tree.deleteCharAt(endIndex);
            tree.delete(beginIndex, beginIndex + category.length()+1);
            beginIndex = BracketedTree.findBeginIndex(tree.toString(), category);       
        }
        return tree.toString();
    }
}
