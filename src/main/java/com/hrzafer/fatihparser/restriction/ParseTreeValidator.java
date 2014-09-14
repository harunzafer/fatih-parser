package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.BracketedTree;
import com.hrzafer.fatihparser.Language;
import java.util.List;

/**
 *
 * @author test
 */
public class ParseTreeValidator {

    private List<ValidationRule> rules;
    public ParseTreeValidator(List<ValidationRule> rules) {
        this.rules = rules;
    }


//    private void readRules() {
//        ValidationRuleReader reader = new ValidationRuleReader(language);
//        this.rules = reader.read();
//    }

    public void validate(List<BracketedTree> trees) {
        for (BracketedTree tree : trees) {
            try {
                tree.setInvalidator(isValid(tree));
            } catch (VerbNotFoundException e) {

            }
        }
    }

    public ValidationRule isValid(BracketedTree tree) {
        for (ValidationRule rule : rules) {
            if (!rule.isTreeValid(tree.toString())) {
                return rule;
            }
        }
        return null;
    }

    private boolean isValid(String tree) {
        for (ValidationRule rule : rules) {
            System.out.println("Rule: " + rule.toString());
            if (!rule.isTreeValid(tree.toString())) {
                System.out.println("Result: NOT VALID");
                return false;
            }
        }
        System.out.println("Result: VALID");
        return true;
    }

    public static void main(String[] args) {
        
        ValidationRuleReader reader = new ValidationRuleReader(Language.TR.getValidationFilePath());
        ParseTreeValidator validator = new ParseTreeValidator(reader.read());
        validator.isValid("[SS[Adv[AP[VJG[IObj[NP[pron[biz]]]][VJ[VP[verb[geliş]]][+An[en]]]]]][SS[Pre[NP[JC[JP[adj[bir]]][NP[noun[ülke]]]]][Prs[1pl[yiz]]]]]] ");

    }
}
