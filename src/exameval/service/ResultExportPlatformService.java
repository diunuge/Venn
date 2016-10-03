/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.feedback.Feedback;

/**
 *
 * @author Diunuge
 */
public interface ResultExportPlatformService {

    public void exportXML(Feedback feedback);
    
}
