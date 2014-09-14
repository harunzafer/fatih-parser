/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hrzafer.fatihparser.restriction;

import com.hrzafer.fatihparser.Language;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author test
 */
public class ValidationRuleReader {

    private String validationRuleFilePath;

    public ValidationRuleReader(String validationRuleFilePath) {
        this.validationRuleFilePath = validationRuleFilePath;
    }

    public List<ValidationRule> read() {
        return read(validationRuleFilePath);
    }

    public List<ValidationRule> read(String file) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //parse using builder to get DOM representation of the XML file
            Document doc = db.parse(file);
            doc.getDocumentElement().normalize();
            //get the root element
            Element rootElement = doc.getDocumentElement();
            NodeList nodes = rootElement.getElementsByTagName("rule");

            List<ValidationRule> rules = new ArrayList();
            for (int i = 0; i < nodes.getLength(); i++) {
                ValidationRule rule = readRule((Element)nodes.item(i));
                rules.add(rule);
            }
            return rules;

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return Collections.EMPTY_LIST;
    }

    public ValidationRule readRule(Element el) {

        String scope = el.getAttribute("scope");
        String rec = el.getAttribute("recursive");
        String ignore = el.getAttribute("ignore");
        String description = el.getElementsByTagName("description").item(0).getTextContent();
        List<Status> statusList = new ArrayList();
        NodeList nodes = el.getElementsByTagName("status");
        for (int i = 0; i < nodes.getLength(); i++) {
            Status s = ReadStatus((Element) nodes.item(i));
            statusList.add(s);
        }
        return new ValidationRule(scope, description, statusList, rec, ignore);
    }

    public Status ReadStatus(Element el) {
        String type = el.getAttribute("type");
        String cat = el.getAttribute("cat");
        String op = el.getAttribute("op");
        String str = el.getAttribute("str");
        return new Status(type, cat, op, str);
    }

    public static void main(String[] args) {
        ValidationRuleReader reader = new ValidationRuleReader(Language.TR.getValidationFilePath());
        List<ValidationRule> rules = reader.read();
        for (ValidationRule restrictionRule : rules) {
            System.out.println(restrictionRule);
        }

//        boolean b = rules.get(5).isTreeValid("[SS[IObj[NP[pron[ben]]]][Pre[VP[VP[VP[verb[git]]][tense[ti]]][Prs[1sg[m]]]]]]");
//        System.out.println(b);
//
//        String verb = BracketedTree.getNonTerminal("[Pre[VP[VP[VP[verb[git]]][tense[ti]]][Prs[1sg[m]]]]]", "verb");
//
//        System.out.println(verb);

    }
}
