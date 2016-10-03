/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.question.Question;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Diunuge
 */
public class QuestionReadPlatformServiceImpl implements QuestionReadPlatformService{

    @Override
    public void parse(Question question, String questionPath) {
        
        String questionXmlFile = null;
        try {
            questionXmlFile = readXMLFile(questionPath);
        } catch (IOException ex) {
            Logger.getLogger(MarkingRubricReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InputSource source = new InputSource(new StringReader(questionXmlFile));

        XPath xPath = XPathFactory.newInstance().newXPath();

        
        try {
            
            NodeList nodeListQuestions = (NodeList)xPath.evaluate("//question", source, XPathConstants.NODESET);
            List<Element> elementListQuestions = new ArrayList<>(nodeListQuestions.getLength());
            
            for (int i = 0; i < nodeListQuestions.getLength(); i++)
            {
                elementListQuestions.add((Element)nodeListQuestions.item(i));
            }
            
            for (int questionIndex=0; questionIndex < elementListQuestions.size(); questionIndex++)
            {
                String type = elementListQuestions.
                        get(questionIndex).
                        getElementsByTagName("type").
                        item(0).
                        getTextContent();
                
                question.setType(type);
                
                String textContent = elementListQuestions.
                        get(questionIndex).
                        getElementsByTagName("description").
                        item(0).
                        getTextContent();
                
                question.setTextContent(textContent);
                
                NodeList nodeListSubQuestions = elementListQuestions.get(questionIndex).getElementsByTagName("subquestion");
                List<Element> elementListSubQuestions = new ArrayList<>(nodeListSubQuestions.getLength());
            
                for (int i = 0; i < nodeListSubQuestions.getLength(); i++)
                {
                    elementListSubQuestions.add((Element)nodeListSubQuestions.item(i));
                }
                
                for (int subQuestionIndex = 0; subQuestionIndex < elementListSubQuestions.size(); subQuestionIndex++)
                {
                    question.addSubQuestion(
                            elementListSubQuestions.get(subQuestionIndex).getAttribute("id"), 
                            elementListSubQuestions.get(subQuestionIndex).getTextContent());
                }
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String readXMLFile (String svgfileIn) throws IOException {
        
        String xmlFile=null;
        
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(svgfileIn));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            xmlFile = sb.toString();
        } 
        catch(java.io.IOException ex){
            
        }
        finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
        return xmlFile;
    }
    
}
