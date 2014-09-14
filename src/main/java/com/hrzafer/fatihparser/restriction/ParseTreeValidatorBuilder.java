package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.Language;

/**
 *
 * @author hrzafer
 */
public class ParseTreeValidatorBuilder {
    
    public static ParseTreeValidator build(Language language){
        ValidationRuleReader reader = new ValidationRuleReader(language.getValidationFilePath());
        ParseTreeValidator validator = new ParseTreeValidator(reader.read());
        return validator;
    }
    
}
