/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.question.Question;
import exameval.domain.rubric.Rubric;
import exameval.domain.venn.VennDiagram;

/**
 *
 * @author Diunuge
 */
public interface EvaluationPlatformService {

    public void evaluate(VennDiagram svgImageStudentAnswer, Question question, VennDiagram svgImageModelAnswer, Rubric markingRubric, String results);
    
}
