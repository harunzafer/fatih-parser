package com.hrzafer.fatihparser.format;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author hrzafer
 */
public class ParseLanguage {

    private final Properties properties;
    private static final String enTagsFilePath = "engTags.properties";
    private static final String trTagsFilePath = "trTags.properties";

    public ParseLanguage(String propertiesFile) {
        InputStream is = ParseLanguage.class.getClassLoader().getResourceAsStream(propertiesFile);
        properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException ex) {
           throw new RuntimeException(ex);
        }
    }

    public Map<String, String> getTags() {
        return new HashMap<String, String>((Map) properties);
    }
    public static final ParseLanguage English = new ParseLanguage(enTagsFilePath);
    public static final ParseLanguage Turkish = new ParseLanguage(trTagsFilePath);

    public static List<ParseLanguage> getAllLanguages() {
        List<ParseLanguage> languages = new ArrayList<ParseLanguage>();
        languages.add(English);
        languages.add(Turkish);
        return languages;
    }
    //public static final TagDetail Turkish_Detailed;
    //public static final TagDetail Turkish_Simple;
}
