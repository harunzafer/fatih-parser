package com.hrzafer.fatihparser;

public class Language {
    
    private static String directoryPath = "lang" ;
    public static void setDirectoryPath(String directoryPath) {
        Language.directoryPath = directoryPath;
    }
    
    private String langCode;
    private Language( String langCode ) {
        this.langCode = langCode;
    }
    
    public static final Language TR = new Language("tr");
    public static final Language TK = new Language("tk");
        
    public String getMainDirectoryPath() {
        return directoryPath + "/" + langCode;
    }
    
    public String getTestDirectoryPath(){
        return getMainDirectoryPath() + "/test";
    }
    
    public String getAnalysisDirectoryPath(){
        return getMainDirectoryPath() + "/analysis";
    }
    
    public String getValidationDirectoryPath(){
        return getMainDirectoryPath() + "/validation";
    }
        
    
    public String getGrammarFilePath(){
        return  getMainDirectoryPath() + "/grammar.xml";
    }
    
    public String getManualRootAnalysesFilePath(){
        return getAnalysisDirectoryPath() + "/manual_root_analyses.txt";
    }
    
    public String getManualWordAnalysesFilePath(){
        return  getAnalysisDirectoryPath() + "/manual_word_analyses.txt";
    }
    
    public String getZemberekTagsFilePath(){
        return  getAnalysisDirectoryPath() + "/zemberek_tags.txt";
    }
        
    public String getValidationFilePath(){
        return  getValidationDirectoryPath() + "/validation_rule.xml";
    }
    
    public String getVerbsFilePath(){
        return  getValidationDirectoryPath() + "/verbs.txt";
    }

    @Override
    public String toString() {
        return langCode;
    }

}
