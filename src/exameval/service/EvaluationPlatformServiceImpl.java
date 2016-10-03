/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.algorithm.nlp.LSAImplementation;
import exameval.algorithm.nlp.LevenshteinDistance;
import exameval.algorithm.nlp.StringCheck;
import exameval.domain.feedback.Feedback;
import exameval.domain.feedback.FeedbackSubQuestion;
import exameval.domain.question.Question;
import exameval.domain.rubric.MarkData;
import exameval.domain.rubric.Rubric;
import exameval.domain.venn.VennDiagram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diunuge
 */
public class EvaluationPlatformServiceImpl implements EvaluationPlatformService{

    @Override
    public void evaluate(VennDiagram svgImageStudentAnswer, Question question, VennDiagram svgImageModelAnswer, Rubric markingRubric, String results) {

    	Feedback feedback = new Feedback();
    	//identify the labels pairs
    	ArrayList<String> setLabelModel = svgImageModelAnswer.getSetLabels();
    	ArrayList<String> setLabelAnswer = svgImageStudentAnswer.getSetLabels();
    	/*
    	ArrayList<String> setLabelModel = new ArrayList<String>(){{
    		add("දැව සහිත");
    		add("දේශීයව නිෂ්පාදිත");
    		add("වට්ටම් ලබා දෙන");
    	}};
    	ArrayList<String> setLabelAnswer = new ArrayList<>();
    	setLabelAnswer.add("දැව සහිත");
    	setLabelAnswer.add("දේශීය");
    	setLabelAnswer.add("වට්ටම් ලබා දෙන");*/
    	
    	Double[][] smilarity = new Double[setLabelModel.size()][setLabelAnswer.size()];
    	Integer[] association = new Integer[setLabelModel.size()];
    	
    	for(int modelLabelIndex=0; modelLabelIndex<setLabelModel.size(); modelLabelIndex++){
    		double maxRowSimilarity = 0.0;
    		Integer associationIndex = null;
    		for(int answerLabelIndex=0; answerLabelIndex<setLabelAnswer.size(); answerLabelIndex++){
        		
    			double similarityTemp = StringCheck.getSimilarity(
    					setLabelModel.get(modelLabelIndex),
    					setLabelAnswer.get(answerLabelIndex));
    			smilarity[modelLabelIndex][answerLabelIndex] = similarityTemp;
    			
    			if(similarityTemp>maxRowSimilarity && similarityTemp >= 0.1){
    				maxRowSimilarity = similarityTemp;
    				associationIndex = answerLabelIndex;
    			}
        	}
    		association[modelLabelIndex] = associationIndex;
    	}
    	
	    //TODO: generate feedback for missing labels
    	
    	//Check for the basic structure
    	if(setLabelModel.size()!= setLabelAnswer.size()){
    		//TODO: generate feedback : diagram mismatch - set numbers are different
    	}
    	else{
    		feedback.setQuestionId(question.getId());
    		
    		for(int subQuestionIndex = 0; 
    				subQuestionIndex< markingRubric.getNoOfSubQuestions(); 
    				subQuestionIndex++){
    			
    			int marks = 0;
    			
    			ArrayList<FeedbackSubQuestion> markSetFeedbacks = 
    					new ArrayList<>(markingRubric.getNoOfMarkSets(subQuestionIndex));
    			
    			for(int markSetIndex = 0; 
    					markSetIndex< markingRubric.getNoOfMarkSets(subQuestionIndex); 
    					markSetIndex++){
    				
    				ArrayList<MarkData> markDataSets = 
    						markingRubric.getSubQuestionMarkDataSets(subQuestionIndex, markSetIndex);
    				
    				if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase() == "all"){
    					
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markingRubric.getNoOfMarkSetElements(subQuestionIndex, markSetIndex); 
        						markSetElementIndex++){
                			
            				
                		}
    					
    				}
    				else if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase().contains("any")){
    					
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markingRubric.getNoOfMarkSetElements(subQuestionIndex, markSetIndex); 
        						markSetElementIndex++){
                			
            				
                		}
    				}
    				
    				//Find the maximum mark
        		}

    			feedback.addSubQuestion(markingRubric.getSubQuestionId(subQuestionIndex), marks);
    			
    			//calculate total Marks
    		}
    	}
    	
    	
    }
    
    
}
