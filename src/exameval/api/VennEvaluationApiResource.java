package exameval.api;

import exameval.domain.feedback.Feedback;
import exameval.domain.question.Question;
import exameval.domain.rubric.Rubric;
import exameval.domain.svg.SVGImage;
import exameval.domain.venn.VennDiagram;
import exameval.service.EvaluationPlatformService;
import exameval.service.EvaluationPlatformServiceImpl;
import exameval.service.FileHandlingPlatformService;
import exameval.service.FileHandlingPlatformServiceImpl;
import exameval.service.MarkingRubricExportPlatformService;
import exameval.service.MarkingRubricExportPlatformServiceImpl;
import exameval.service.MarkingRubricReadPlatformService;
import exameval.service.MarkingRubricReadPlatformServiceImpl;
import exameval.service.QuestionReadPlatformService;
import exameval.service.QuestionReadPlatformServiceImpl;
import exameval.service.ResultExportPlatformService;
import exameval.service.ResultExportPlatformServiceImpl;
import exameval.service.SVG2VennTranslatePlatformService;
import exameval.service.SVG2VennTranslatePlatformServiceImplMulti;
import exameval.service.SVGReadPlatformService;
import exameval.service.SVGReadPlatformServiceImpl;

public class VennEvaluationApiResource {
	
	public static void evaluate(String studentAnswerPath, String questionPath, String modelAnswerPath, String markingSchemePath, String resultsPath){
        
        Question question = new Question();
        
        SVGImage svgImageStudentAnswer = new SVGImage();
        SVGImage svgImageModelAnswer = new SVGImage();
        
        VennDiagram vennDiagramStudentAnswer = new VennDiagram("Untitled");
        VennDiagram vennDiagramModelAnswer = new VennDiagram("Untitled");
        
        Rubric markingRubric = new Rubric("Venn Diagram");

        Feedback feedback = new Feedback();
        feedback.setQuestionType("Venn Diagram");
        
        //Parse question
        QuestionReadPlatformService questionReader = new QuestionReadPlatformServiceImpl();
        questionReader.parse(question, questionPath);
        
        //Parse Model answer & Student answer diagrams
        SVGReadPlatformService svgReader = new SVGReadPlatformServiceImpl();
        svgReader.parse(svgImageStudentAnswer, studentAnswerPath);
        svgReader.parse(svgImageModelAnswer, modelAnswerPath);
        svgImageStudentAnswer.print();
        
        //Extract set information
        SVG2VennTranslatePlatformService svg2VennTranslator = new SVG2VennTranslatePlatformServiceImplMulti();
        
        svg2VennTranslator.translate(vennDiagramStudentAnswer, svgImageStudentAnswer);
        svg2VennTranslator.translate(vennDiagramModelAnswer, svgImageModelAnswer);
        
        //vennDiagramModelAnswer.print();
        FileHandlingPlatformService fileHandlingPlatformService = new FileHandlingPlatformServiceImpl();
        System.out.println(fileHandlingPlatformService.xmlToJson(vennDiagramModelAnswer.toString(), 2));
        vennDiagramStudentAnswer.print();
        
        //Parse the Rubric
        MarkingRubricReadPlatformService rubricReader = new MarkingRubricReadPlatformServiceImpl();
        rubricReader.parse(markingRubric, markingSchemePath);
        
        //Evaluate
        EvaluationPlatformService evaluator = new EvaluationPlatformServiceImpl();
        evaluator.evaluate(vennDiagramStudentAnswer, question, vennDiagramModelAnswer, markingRubric, feedback);
        
        System.out.println(feedback);
        
        //Export results
        ResultExportPlatformService resultExporter = new ResultExportPlatformServiceImpl();
        //resultExporter.exportText(resultsPath, feedback);
        resultExporter.exportXML(resultsPath, feedback);
    }
	
	public static String getVennDiagram(String svgPath){

		String vennStr = "";

        SVGImage svgImage = new SVGImage();        
        VennDiagram vennDiagram = new VennDiagram("Untitled");
        
        SVGReadPlatformService svgReader = new SVGReadPlatformServiceImpl();
        svgReader.parse(svgImage, svgPath);
        
        SVG2VennTranslatePlatformService svg2VennTranslator = new SVG2VennTranslatePlatformServiceImplMulti();
        svg2VennTranslator.translate(vennDiagram, svgImage);

		FileHandlingPlatformService fileHandlingPlatformService = new FileHandlingPlatformServiceImpl();
        vennStr = fileHandlingPlatformService.xmlToJson(vennDiagram.toString(), 2);
		
		return vennStr;
	}
	
	public static void exportVennDiagramJson(String vennStr, String filePath){
		
		FileHandlingPlatformService fileHandlingPlatformService = new FileHandlingPlatformServiceImpl();
        fileHandlingPlatformService.stringToFile(vennStr, filePath);
	}
	
	public static void exportRubricXML(String jsonStrRubric, String filePath){
		
		MarkingRubricExportPlatformService markingRubricExportPlatformService = new MarkingRubricExportPlatformServiceImpl();
		markingRubricExportPlatformService.produceXMLfromJSON(jsonStrRubric, filePath);
	}
}
