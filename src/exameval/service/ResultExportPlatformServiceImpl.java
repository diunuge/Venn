/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import exameval.domain.feedback.Feedback;
import exameval.domain.feedback.FeedbackSubQuestion;

/**
 *
 * @author Diunuge
 */
public class ResultExportPlatformServiceImpl implements ResultExportPlatformService{

    @Override
    public void exportXML(String fileName, Feedback feedback) {

    	exameval.domain.feedback.jaxb.Feedback jaxbFeedback = new exameval.domain.feedback.jaxb.Feedback();
    	
    	jaxbFeedback.setQuestionId(feedback.getQuestionId());
    	jaxbFeedback.setQuestionType(feedback.getQuestionType());
    	jaxbFeedback.setTotalMarks(feedback.getTotalMarks());
    	jaxbFeedback.setQuestionId(feedback.getQuestionId());
    	
    	if(feedback.getQuestionFeedbacks()!=null){
			
			for(String _feedback: feedback.getQuestionFeedbacks()){
				jaxbFeedback.addQuestionFeedback(_feedback);
			}
		}
    	
    	exameval.domain.feedback.jaxb.Feedback.FeedbackSubQuestions feedbackSubQuestionsJaxB = null;
		
		if(feedback.getSubQuestionFeddbacks()!=null){
			
			feedbackSubQuestionsJaxB = new exameval.domain.feedback.jaxb.Feedback.FeedbackSubQuestions();

			exameval.domain.feedback.jaxb.Feedback.FeedbackSubQuestions.FeedbackSubQuestion _feedbackSubQuestionJaxB = null;
			
			for(FeedbackSubQuestion _subFeeddback: feedback.getSubQuestionFeddbacks()){
	
				_feedbackSubQuestionJaxB = new exameval.domain.feedback.jaxb.Feedback.FeedbackSubQuestions.FeedbackSubQuestion();
				
				_feedbackSubQuestionJaxB.setSubQuestionId(_subFeeddback.getSubQuestionId());
				_feedbackSubQuestionJaxB.setMarks(_subFeeddback.getMarks());
				
				if(_subFeeddback.getSubQuestionFeedbacks()!=null){
					for (String subFeedbackStr: _subFeeddback.getSubQuestionFeedbacks()){
						_feedbackSubQuestionJaxB.addFeedback(subFeedbackStr);
					}
				}

				feedbackSubQuestionsJaxB.addFeedbackSubQuestion(_feedbackSubQuestionJaxB);
			}
		}
		
		jaxbFeedback.setFeedbackSubQuestions(feedbackSubQuestionsJaxB);
		
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(exameval.domain.feedback.jaxb.Feedback.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			//m.marshal(jaxbFeedback, System.out);\
			
			File file = new File(fileName);
			FileOutputStream fop = new FileOutputStream(file);;
			m.marshal(jaxbFeedback, fop);
			fop.flush();
			fop.close();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
	    

    }

	@Override
	public void exportText(String fileName, Feedback feedback) {

		try {

			String content = feedback.toString();

			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
    
}
