/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval;

import exameval.algorithm.set.SetNameCheck;
import exameval.api.VennEvaluationApiResource;
import exameval.domain.feedback.Feedback;
import exameval.domain.question.Question;
import exameval.domain.rubric.Rubric;
import exameval.domain.svg.SVGEllipse;
import exameval.domain.svg.SVGImage;
import exameval.domain.svg.SVGText;
import exameval.domain.venn.VennDiagram;
import exameval.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Diunuge
 */
public class ExamEval {

  /**
   * Reads a file and parses the path elements.
   * 
   * @param args args[0] - Filename to parse.
   * @throws IOException Error reading the SVG file.
   */
    public static void main(String[] args) {
        
        ExamEval eval = new ExamEval();
        
        //String temp = "hi&#3515;&#3501;&#3540; &#3508;&#3537;&#3505;&#3530;";
        //String str = StringEscapeUtils.unescapeHtml4(temp) ;
        //System.out.println(str);
        
        if(true){
    		
    		String studentAnswerPath  = "C:/wamp/www/demo/bin/answer_min.svg";
        	String questionPath  = "C:/wamp/www/demo/bin/question.xml";
        	String modelAnswerPath  = "C:/wamp/www/demo/bin/model_min.svg";
        	String markingSchemePath = "C:/wamp/www/demo/bin/rubric.xml";
        	String resultsPath  = "C:/wamp/www/demo/bin/feedback.xml";
        	
        	VennEvaluationApiResource.evaluate(studentAnswerPath, questionPath, modelAnswerPath, markingSchemePath, resultsPath);
        }
        
        if(false){
            MarkingRubricReadPlatformService rubricReader = new MarkingRubricReadPlatformServiceImpl();
            Rubric markingRubric = new Rubric("Venn Diagram");

            //Check for exceptions
            rubricReader.parse(markingRubric, "res/test/marking_scheme_1.0_1.xml");   
        }
        
        if(false){
            //String svgFile = "venn_min.svg";
            //String svgFile = "res/test/marking_scheme_1.0_1.xml";
            String svgFile = "res/test/1.1_min.svg";
            SVGImage svgImage = new SVGImage();
            VennDiagram vennDiagram = new VennDiagram("Untitled");

            SVGReadPlatformService svgReader = new SVGReadPlatformServiceImpl();
            svgReader.parse(svgImage, svgFile);
            svgImage.print();

            SVG2VennTranslatePlatformService svg2VennTranslator = new SVG2VennTranslatePlatformServiceImplOld();

            svg2VennTranslator.translate(vennDiagram, svgImage);

            vennDiagram.printDebug();
        }
        
        if(false){
            String questionPath = "res/test/question.xml";
            Question question = new Question();
            QuestionReadPlatformService questionReader = new QuestionReadPlatformServiceImpl();
            questionReader.parse(question, questionPath);
            System.out.println(question.getCompleteTextContent());
            //String temp = new String("", "UTF-16");
            System.out.println("අනුපාතිකය");
        }
        
        if(false){
        	EvaluationPlatformService evaluator = new EvaluationPlatformServiceImpl();
        	//evaluator.evaluate(null, null, null, null, null);
        	
        	SetNameCheck.isSameZone("", "", new ArrayList<String>(), new ArrayList<String>());
        }
    }
    
    ArrayList<SVGEllipse> sets;
    ArrayList<SVGText> lablesNumeric;
    ArrayList<SVGText> lablesNominal;
    
    public ExamEval(){
        sets = new ArrayList<>();
        lablesNumeric = new ArrayList<>();
        lablesNominal = new ArrayList<>();
    }
    
    public void evaluate(String studentAnswerPath, String questionPath, String modelAnswerPath, String markingSchemePath, String resultsPath){
        
        Question question = new Question();
        
        SVGImage svgImageStudentAnswer = new SVGImage();
        SVGImage svgImageModelAnswer = new SVGImage();
        
        VennDiagram vennDiagramStudentAnswer = new VennDiagram("Untitled");
        VennDiagram vennDiagramModelAnswer = new VennDiagram("Untitled");
        
        Rubric markingRubric = new Rubric("Venn Diagram");
        
        Feedback feedback = new Feedback();
        feedback.setQuestionType("Venn Diagram");

        String results = null;
        
        
        //Parse question
        QuestionReadPlatformService questionReader = new QuestionReadPlatformServiceImpl();
        questionReader.parse(question, questionPath);
        
        //Parse Model answer & Student answer diagrams
        SVGReadPlatformService svgReader = new SVGReadPlatformServiceImpl();
        svgReader.parse(svgImageStudentAnswer, studentAnswerPath);
        svgReader.parse(svgImageModelAnswer, modelAnswerPath);
        //svgImage.print();
        
        //Extract set information
        SVG2VennTranslatePlatformService svg2VennTranslator = new SVG2VennTranslatePlatformServiceImplMulti();
        
        svg2VennTranslator.translate(vennDiagramStudentAnswer, svgImageStudentAnswer);
        svg2VennTranslator.translate(vennDiagramModelAnswer, svgImageModelAnswer);
        
        //Parse the Rubric
        MarkingRubricReadPlatformService rubricReader = new MarkingRubricReadPlatformServiceImpl();
        rubricReader.parse(markingRubric, markingSchemePath);
        
        //Evaluate
        EvaluationPlatformService evaluator = new EvaluationPlatformServiceImpl();
        evaluator.evaluate(vennDiagramStudentAnswer, question, vennDiagramModelAnswer, markingRubric, feedback);
        
        //Export results
        ResultExportPlatformService resultExporter = new ResultExportPlatformServiceImpl();
        //resultExporter.exportXML(feedback);
    }
    
//    private void initialize(){
//        sets.add(new SVGEllipse(197.5, 154, 149, 97.5));
//        sets.add(new SVGEllipse(367.5, 151, 149, 99));
//        sets.add(new SVGEllipse(280, 256, 144.5, 109.5));
//        
//        SVGText temp = new SVGText(86.5, 68.5, "A");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(463.5, 56.5, "B");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(268.5, 347.5, "C");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        
//        temp = new SVGText(162.5, 67.5, "100");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(421.5, 63.5, "80");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(405.5, 126.5, "x");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(260.5, 183.5, "10");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(260.5, 75.5, "15");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(184.5, 219.5, "8");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(362.5, 344.5, "30");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//        
//        temp = new SVGText(263.5, 282.5, "5");
//        if(temp.isNumeric())
//            lablesNumeric.add(temp);
//        else
//            lablesNominal.add(temp);
//    }
}
