package com.hrzafer.fatihparser.analyzer;

import com.hrzafer.fatihparser.structure.AmbigiousSentence;
import com.hrzafer.fatihparser.structure.Sentence;
import com.hrzafer.fatihparser.structure.Word;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class SentenceAnalyzer {

    private List<WordAnalyzer> analyzers = new ArrayList<WordAnalyzer>();
    public void addWordAnalyzer(WordAnalyzer analyzer) {
        analyzers.add(analyzer);
    }
    
    public List<Sentence> analyze(String sentence) {
        String[] words = splitSentence(sentence);
        List<List<Word>> ambigiousWords = new ArrayList<List<Word>>();
        for (String word : words) {
            List<Word> solutions = analyzeWord(word);
            ambigiousWords.add(solutions);
        }
        return new AmbigiousSentence(ambigiousWords).getPossibleSentences();
    }

    private String[] splitSentence(String sentence) {
        sentence = sentence.replaceAll("[,!\\?\\.]", " $0 ");
        sentence = sentence.replaceAll("( )+", " ");
        return sentence.split("\\s");
    }

    private List<Word> analyzeWord(String token) {
        List<Word> solutions;
        for (WordAnalyzer wordAnalyzer : analyzers) {
            solutions = wordAnalyzer.analyze(token);
            if (!solutions.isEmpty()) {
                return solutions;
            }
        }
        throw new WordNotAnalyzedException(token);
    }
}
