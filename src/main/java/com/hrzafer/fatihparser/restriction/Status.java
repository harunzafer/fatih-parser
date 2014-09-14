/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.BracketedTree;
import com.hrzafer.fatihparser.Language;
import java.util.EnumSet;

/**
 *
 * @author test
 */
public class Status {

    private Type type;
    private String cat;
    private Operator operator;
    private String str;   

    public Status(String type, String cat, String op, String str) {
        this.type = Type.getByValue(type);
        this.cat = cat;
        this.operator = Operator.valueOf(op);
        this.str = str;
    }

    public boolean isValid(String tree) {
        String subTree = BracketedTree.getSubTree(tree, cat);
        if (subTree != null){
            return operator.isTrue(subTree, str);
        }
        else if (subTree == null && operator == Operator.notExists){
            return true;
        }
        return false;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<status ");
        sb.append("type=\"").append(this.type);
        sb.append("\" cat=\"").append(this.cat);
        sb.append("\" op=\"").append(this.operator);
        if (!str.equals("")){
            sb.append("\" str=\"").append(this.str);
        }
        sb.append("\"/>");
        return sb.toString();
    }

    public enum Type {

        Condition("cond"), Restriction("rest");
        private String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public static Type getByValue(String value) {
            Type returnValue = null;
            for (final Type element : EnumSet.allOf(Type.class)) {
                if (element.toString().equals(value)) {
                    returnValue = element;
                }
            }
            return returnValue;
        }
    }
}
