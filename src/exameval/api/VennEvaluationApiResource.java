package exameval.api;

import exameval.domain.question.Question;
import exameval.domain.rubric.Rubric;
import exameval.domain.svg.SVGImage;
import exameval.domain.venn.VennDiagram;
import exameval.service.EvaluationPlatformService;
import exameval.service.EvaluationPlatformServiceImpl;
import exameval.service.MarkingRubricReadPlatformService;
import exameval.service.MarkingRubricReadPlatformServiceImpl;
import exameval.service.QuestionReadPlatformService;
import exameval.service.QuestionReadPlatformServiceImpl;
import exameval.service.ResultExportPlatformService;
import exameval.service.ResultExportPlatformServiceImpl;
import exameval.service.SVG2VennTranslatePlatformService;
import exameval.service.SVG2VennTranslatePlatformServiceImpl;
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
        SVG2VennTranslatePlatformService svg2VennTranslator = new SVG2VennTranslatePlatformServiceImpl();
        
        svg2VennTranslator.translate(vennDiagramStudentAnswer, svgImageStudentAnswer);
        svg2VennTranslator.translate(vennDiagramModelAnswer, svgImageModelAnswer);
        
        //Parse the Rubric
        MarkingRubricReadPlatformService rubricReader = new MarkingRubricReadPlatformServiceImpl();
        rubricReader.parse(markingRubric, markingSchemePath);
        
        //Evaluate
        EvaluationPlatformService evaluator = new EvaluationPlatformServiceImpl();
        evaluator.evaluate(vennDiagramStudentAnswer, question, vennDiagramModelAnswer, markingRubric, results);
        
        //Export results
        ResultExportPlatformService resultExporter = new ResultExportPlatformServiceImpl();
        resultExporter.exportXML(results);
    }
}
