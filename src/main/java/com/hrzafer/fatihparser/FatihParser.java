package com.hrzafer.fatihparser;

import com.hrzafer.fatihparser.util.TableFileReader;
import edu.osu.ling.pep.PepException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 *
 * @author hrzafer
 */
public class FatihParser {

    public static void printTrees(List<BracketedTree> trees) {
        System.out.println("\t" + trees.size() + " parses found");
        int valid = 0;
        for (int i = 0; i < trees.size(); i++) {
            System.out.println("\tParse " + (i + 1) + ": " + trees.get(i).print());
            if (trees.get(i).isValid()) {
                valid++;
            }
        }
        System.out.println("\t" + valid + " valid and " + (trees.size() - valid) + " invalid parses found\n");
    }

    public static void printParsedSentences(List<ParsedSentence> sentences) {
        System.out.println("\t" + sentences.size() + " sentences are derived");
        for (int i = 0; i < sentences.size(); i++) {
            System.out.println("Derivation " + i + " :" + sentences.get(i).getSentence().toString());
            printTrees(sentences.get(i).getTrees());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    private static List<String> getSentencesToBeParsed(String filename) throws IOException {
        List<String> sentences;
        sentences = Files.readAllLines(Paths.get(filename), Charset.forName("UTF-8"));
        for (int i = 0; i < sentences.size(); i++) {
            if (isDisabled(sentences.get(i))) {
                sentences.remove(i);
                i--;
            } else {
                sentences.set(i, TableFileReader.removeComment(sentences.get(i)));
            }
        }
        return sentences;

    }

    private static boolean isDisabled(String line) {
        return line.startsWith("\t") || line.startsWith("#") || line.startsWith("    ") || line.isEmpty();
    }

    public static void runTest(NonTerminal nonTerminal, Language language) throws IOException {
        String testFile = language.getTestDirectoryPath() + "/" + nonTerminal + ".txt";
        Parser parser = ParserFactory.create(language);
        List<String> sentences = getSentencesToBeParsed(testFile);
        for (String string : sentences) {
            List<ParsedSentence> parsedSentences = parser.parse(string, nonTerminal.toString());
            printParsedSentences(parsedSentences);
        }
    }

    public static void main(String[] args) throws FileNotFoundException, IOException, PepException {

        //Parser parser = new Parser(Language.TR);
        //List<ParsedSentence> parsedSentences = parser.parse("Bu bir denemedir");
        //printParsedSentences(parsedSentences);
        runTest(NonTerminal.S, Language.TR);
        //runTest(NonTerminal.S, Language.TK);



    }
}
