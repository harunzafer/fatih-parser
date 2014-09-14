package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.BracketedTree;

/**
 *
 * @author test
 */
public enum Operator {

    equals {
        @Override
        public boolean isTrue(String subTree, String str) {
            return subTree.equals(str);
        }
    },
    startsWith {
        @Override
        public boolean isTrue(String subTree, String str) {
            return subTree.startsWith(str);
        }
    },
    endsWith {
        @Override
        public boolean isTrue(String subTree, String str) {
            return subTree.endsWith(str);
        }
    },
    notEndsWith {
        @Override
        public boolean isTrue(String subTree, String str) {
            return !subTree.endsWith(str);
        }
    },
    includes {
        @Override
        public boolean isTrue(String subTree, String str) {
            if (subTree.indexOf(str) > -1) {
                return true;
            }
            return false;
        }
    },
    matches {
        @Override
        public boolean isTrue(String subTree, String str) {
            str = escapeSquareBrackets(str);
            return subTree.matches(".*" + str + ".*");
        }
    },
    notIncludes {
        @Override
        public boolean isTrue(String subTree, String str) {
            if (subTree.indexOf(str) > -1) {
                return false;
            }
            return true;
        }
    },
    exists {
        @Override
        public boolean isTrue(String subTree, String str) {
            return (subTree != null);
        }
    },
    notExists {
        @Override
        public boolean isTrue(String subTree, String str) {
            return (subTree == null);
        }
    },
    hasTransitiveVerb {
        @Override
        public boolean isTrue(String subTree, String str) {
            String verb = BracketedTree.getNonTerminal(subTree, "verb");
            return Verbs.getInstance().isTransitive(verb);
        }
    },
    hasNonTransitiveVerb {
        @Override
        public boolean isTrue(String subTree, String str) {
            String verb = BracketedTree.getNonTerminal(subTree, "verb");
            return !Verbs.getInstance().isTransitive(verb);
        }
    },
    isRepetition {
        @Override
        public boolean isTrue(String subTree, String str) {
            String verb = BracketedTree.getNonTerminal(subTree, "verb");
            return !Verbs.getInstance().isTransitive(verb);
        }
    },
    notMoreThanOne {
        @Override
        public boolean isTrue(String subTree, String str) {
            int count = 0;
            while (subTree.indexOf(str) > -1) {
                subTree = subTree.replaceFirst(escapeSquareBrackets(str), "");
                count++;
            }
            return count < 2;
        }
    },
    hasNonTransitivePassiveVerb {
        @Override
        public boolean isTrue(String subTree, String str) {
            String verb = BracketedTree.getNonTerminal(subTree, "verb");
            return Verbs.getInstance().isNonTransitivePassive(verb);
        }
    };

     public String escapeSquareBrackets(String str) {
        return str.replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]");
    }

    
    public boolean isTrue(String subTree, String str) {
        return subTree.startsWith(str);
    }
}
