/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.rubric.Rubric;

/**
 *
 * @author Diunuge
 */
public interface MarkingRubricReadPlatformService {

    public void parse(Rubric markingRubric, String markingSchemePath);
}
