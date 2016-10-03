/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.question.Question;

/**
 *
 * @author Diunuge
 */
public interface QuestionReadPlatformService {
    
    public void parse(Question question, String questionPath);
}
