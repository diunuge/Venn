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

    public void exportXML(String fileName, Feedback feedback);
    
    public void exportText(String fileName, Feedback feedback);
    
}
