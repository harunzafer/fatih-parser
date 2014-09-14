/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.BracketedTree;
import com.hrzafer.fatihparser.restriction.Status.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author test
 */
public class ValidationRule {

    private String scope;
    private boolean recursive;
    private List<String> ignoredCategories;
    private String description;
    private List<Status> statusList;

    public ValidationRule(String scope, String description, List<Status> statusList, String recursive, String ignore) {
        this.scope = scope;
        this.statusList = statusList;
        this.description = description;
        this.recursive = recursive.equalsIgnoreCase("true");
        this.ignoredCategories = getIgnoredCats(ignore);
    }

    private List<String> getIgnoredCats(String str) {
        if (str.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        return Arrays.asList(str.split("[\\s,]+"));
    }

    private List<String> getSubTrees(String parseTree) {
        List<String> subTrees = BracketedTree.getSubTreeList(parseTree, scope);
        if (!recursive && !subTrees.isEmpty()) {
            subTrees.subList(1, subTrees.size()).clear();
        }
        removeIgnoreds(subTrees);
        return subTrees;
    }

    private void removeIgnoreds(List<String> subTrees) {
        List<String> ignoredSubTrees = getIgnoredSubTrees(subTrees);
        for (int i = 0; i < subTrees.size(); i++) {
            for (String ignoredST : ignoredSubTrees) {
                subTrees.set(i, subTrees.get(i).replace(ignoredST, "$"));
            }
        }

    }

    private List<String> getIgnoredSubTrees(List<String> subTrees) {
        List<String> ignoredSubTrees = new ArrayList<String>();
        for (String category : ignoredCategories) {
            for (String subTree : subTrees) {
                ignoredSubTrees.addAll(BracketedTree.getSubTreeList(subTree, category));
            }
        }
        return ignoredSubTrees;
    }

    public String getDescription() {
        return description;
    }

    public boolean isTreeValid(String parseTree) {
        List<String> subTreeList = getSubTrees(parseTree);
        if (subTreeList.isEmpty()) {
            return true;
        }
        for (String subTree : subTreeList) {
            if (!isSubTreeValid(subTree)){
                return false;
            }
        }
        return true;
    }

    private boolean isSubTreeValid(String subTree) {
        if (areConditionsValid(subTree)) {
            return areRestrictionsValid(subTree);
        }
        return true;
    }


    public boolean areConditionsValid(String subTree) {
        for (Status status : statusList) {
            if (status.getType() == Type.Condition) {
                if (!status.isValid(subTree)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean areRestrictionsValid(String subTree) {
        for (Status status : statusList) {
            if (status.getType() == Type.Restriction) {
                    if (!status.isValid(subTree)) {
                        return false;
                    }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("<rule scope=\"");
        sb.append(this.scope).append("\" ");
        sb.append("recursive=\"").append(this.recursive).append("\" ");
        sb.append("ignore=\"").append(this.ignoredCategories).append("\"");
        sb.append(">");
        sb.append("\n<description>").append(this.description).append("</description>");

        for (Status status : statusList) {
            sb.append("\n\t");
            sb.append(status.toString());
        }
        sb.append("\n</rule>");
        return sb.toString();
    }

    public static void main(String[] args) {
    }
}
