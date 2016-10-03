/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

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

import exameval.domain.rubric.Rubric;

/**
 *
 * @author Diunuge
 */
public class MarkingRubricReadPlatformServiceImpl implements MarkingRubricReadPlatformService{

    @Override
    public void parse(Rubric markingRubric, String markingRubricPath) {
        
        String svgFile = null;
        try {
            svgFile = readXMLFile(markingRubricPath);
        } catch (IOException ex) {
            Logger.getLogger(MarkingRubricReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        InputSource source = new InputSource(new StringReader(svgFile)); //or use your own input source

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList list;
        try {
            
            //Read type
            list = (NodeList)xPath.evaluate("//type", source, XPathConstants.NODESET);
            
            List<Element> type = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                type.add((Element)list.item(i));
            }
            
            markingRubric.setDiagramType(type.get(0).getTextContent());
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//question", source, XPathConstants.NODESET);
            
            List<Element> questions = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                questions.add((Element)list.item(i));
            }
            
            markingRubric.setQuestionID(questions.get(0).getAttribute("id"));
            markingRubric.setQuestionTotalMarks(Integer.parseInt(questions.get(0).getAttribute("totalMarks")));
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//subQuestion", source, XPathConstants.NODESET);
            
            List<Element> subQuestions = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                subQuestions.add((Element)list.item(i));
            }
            
            for (int subQuestionIndex = 0; subQuestionIndex < subQuestions.size(); subQuestionIndex++)
            {
                markingRubric.addSubQuestion(subQuestions.get(subQuestionIndex).getAttribute("id"));
                markingRubric.setSubQuestionModelDiagram(subQuestions.get(subQuestionIndex).getAttribute("modelDiagram"), subQuestionIndex);
                markingRubric.setSubQuestionTotalMarks(Integer.parseInt(subQuestions.get(subQuestionIndex).getAttribute("totalMarks")), subQuestionIndex);
                
                source = new InputSource(new StringReader(svgFile));
                //String query = "/rubric/question/subQuestion[@id = 'q01-01']/markSet"
                String query = "//markSet[../@id = \""+subQuestions.get(subQuestionIndex).getAttribute("id")+"\"]";
                //String query = "/rubric/question/subQuestion[@id = \'"+subQuestions.get(subQuestionIndex).getAttribute("id")+"\']/markSet";
                //String query = "/rubric/question/subQuestion[@id = \'"+subQuestions.get(subQuestionIndex).getAttribute("id")+"\']";
                NodeList nodelListMarkSets = (NodeList)xPath.evaluate(query, source, XPathConstants.NODESET);
                List<Element> elementMarkSets = new ArrayList<>(nodelListMarkSets.getLength());
                for (int j = 0; j < nodelListMarkSets.getLength(); j++)
                {
                    Element tempItem = (Element)nodelListMarkSets.item(j);
                    if(tempItem!=null)
                        elementMarkSets.add((Element)nodelListMarkSets.item(j));
                }
                
                for (int markSetIndex = 0; markSetIndex < elementMarkSets.size(); markSetIndex++)
                {
                    markingRubric.addSubQuestionMarkSet(Integer.parseInt(elementMarkSets.get(markSetIndex).getAttribute("id")), subQuestionIndex);
                    markingRubric.setSubQuestionMarkSetMarks(Integer.parseInt(elementMarkSets.get(markSetIndex).getAttribute("marks")), subQuestionIndex, markSetIndex);
                    markingRubric.setSubQuestionMarkSetMethod(elementMarkSets.get(markSetIndex).getAttribute("method"), subQuestionIndex, markSetIndex);
                    
                    source = new InputSource(new StringReader(svgFile));
                    query = "//subQuestion[@id = \'"+subQuestions.get(subQuestionIndex).getAttribute("id")+"\']/markSet[@id = \'"+elementMarkSets.get(markSetIndex).getAttribute("id")+"\']/*";
                    NodeList nodelListMarkSetElemets = (NodeList)xPath.evaluate(query, source, XPathConstants.NODESET);
                    List<Element> elementMarkSetElements = new ArrayList<>(nodelListMarkSets.getLength());
                    for (int j = 0; j < nodelListMarkSetElemets.getLength(); j++)
                    {
                        Element tempItem = (Element)nodelListMarkSetElemets.item(j);
                        if(tempItem!=null)
                            elementMarkSetElements.add(tempItem);
                    }
                    
                    for (int markSetElementIndex = 0; markSetElementIndex < elementMarkSetElements.size(); markSetElementIndex++)
                    {
                         
                        if(elementMarkSetElements.get(markSetElementIndex).getTagName() == "zone"){
                            
                            Element label = (Element)elementMarkSetElements.get(markSetElementIndex).getElementsByTagName("label").item(0);
                            Element value = (Element)elementMarkSetElements.get(markSetElementIndex).getElementsByTagName("value").item(0);
                            Element color = (Element)elementMarkSetElements.get(markSetElementIndex).getElementsByTagName("color").item(0);
                            
                            markingRubric.addSubQuestionMarkSetElementZone(
                                    label.getTextContent(),
                                    value.getTextContent(), 
                                    color.getTextContent(),
                                    markSetIndex, 
                                    subQuestionIndex); 
                        }
                        else if(elementMarkSetElements.get(markSetElementIndex).getTagName() == "set"){
                            
                            markingRubric.addSubQuestionMarkSetElementSet(
                                    elementMarkSetElements.get(markSetElementIndex).getTextContent(),
                                    markSetIndex, 
                                    subQuestionIndex);
                        }
                        
                    }
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
