/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.algorithm.nlp.StringCheck;
import exameval.algorithm.set.SetNameCheck;
import exameval.domain.feedback.Feedback;
import exameval.domain.feedback.FeedbackSubQuestion;
import exameval.domain.question.Question;
import exameval.domain.rubric.MarkData;
import exameval.domain.rubric.MarkDataSet;
import exameval.domain.rubric.MarkDataZone;
import exameval.domain.rubric.Rubric;
import exameval.domain.venn.VennDiagram;
import exameval.domain.venn.VennZone;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 *
 * @author Diunuge
 */
public class EvaluationPlatformServiceImpl implements EvaluationPlatformService{

    @Override
    public void evaluate(VennDiagram svgImageStudentAnswer, Question question, VennDiagram svgImageModelAnswer, Rubric markingRubric, Feedback feedback) {

    	feedback = new Feedback();
    	//identify the labels pairs
    	ArrayList<String> setLabelsModel = svgImageModelAnswer.getSetLabels();
    	ArrayList<String> setLabelsAnswer = svgImageStudentAnswer.getSetLabels();
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
    	
    	Double[][] smilarity = new Double[setLabelsModel.size()][setLabelsAnswer.size()];
    	Integer[] association = new Integer[setLabelsModel.size()];
    	
    	ArrayList<String> setLabelsAnswerArranged = new ArrayList<>();
    	
    	for(int modelLabelIndex=0; 
    			modelLabelIndex<setLabelsModel.size(); 
    			modelLabelIndex++){
    		
    		double maxRowSimilarity = 0.0;
    		Integer associationIndex = null;
    		
    		for(int answerLabelIndex=0; answerLabelIndex<setLabelsAnswer.size(); answerLabelIndex++){
        		
    			double similarityTemp = StringCheck.getSimilarity(
    					setLabelsModel.get(modelLabelIndex),
    					setLabelsAnswer.get(answerLabelIndex));
    			smilarity[modelLabelIndex][answerLabelIndex] = similarityTemp;
    			
    			if(similarityTemp>maxRowSimilarity && similarityTemp >= 0.1){
    				maxRowSimilarity = similarityTemp;
    				associationIndex = answerLabelIndex;
    			}
        	}
    		association[modelLabelIndex] = associationIndex;
    		setLabelsAnswerArranged.add(setLabelsAnswer.get(associationIndex));
    	}
    	
	    //TODO: generate feedback for missing labels
    	
    	//Check for the basic structure
    	if(setLabelsModel.size()!= setLabelsAnswer.size()){
    		//TODO: generate feedback : diagram mismatch - set numbers are different
    	}
    	else{
    		feedback.setQuestionId(question.getId());
    		
    		for(int subQuestionIndex = 0; 
    				subQuestionIndex< markingRubric.getNoOfSubQuestions(); 
    				subQuestionIndex++){
    			    			
    			String subQuestionId = markingRubric.getSubQuestionId(subQuestionIndex);
    			FeedbackSubQuestion feedbackSubQ;
    			ArrayList<FeedbackSubQuestion> markSetFeedbacks = 
    					new ArrayList<>(markingRubric.getNoOfMarkSets(subQuestionIndex));
    			
    			for(int markSetIndex = 0; 
    					markSetIndex< markingRubric.getNoOfMarkSets(subQuestionIndex); 
    					markSetIndex++){
    				
    				feedbackSubQ = new FeedbackSubQuestion(subQuestionId, markSetIndex);
    				ArrayList<MarkData> markDataSets = 
    						markingRubric.getSubQuestionMarkDataSets(subQuestionIndex, markSetIndex);
    				
    				if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase() == "all"){
    					
    					boolean check = true;
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markDataSets.size(); 
        						markSetElementIndex++){
                			
    						
    						MarkData markData = markDataSets.get(markSetElementIndex);
    						String type = markData.getType();
    						
    						if(type == "set"){
    							MarkDataSet markDataSet = (MarkDataSet)markData;
    							if(!SetNameCheck.isSameSetName(markDataSet.getSetName(), 
    									setLabelsModel, setLabelsAnswerArranged)){
    								
    								check = false;
    								feedbackSubQ.addMarks(0);
    								feedbackSubQ.addFeedback("Invalid set name \'" + markDataSet.getSetName() + "\'");
    								break;
    							}
    							else{
    								feedbackSubQ.addMarks(markingRubric.
    										getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex));
    								feedbackSubQ.addFeedback(null);
    							}
    						}
    						else if(type == "zone"){
    							MarkDataZone markDataZone = (MarkDataZone)markData;
    							
    							for(int zoneIndex=0; 
    									zoneIndex < svgImageStudentAnswer.getNoOfZones(); 
    									zoneIndex++){
    								
    								//Matching zone identifire
    								if(SetNameCheck.
    										isSameZone(
    												markDataZone.getZoneIdentifire(), 
    												svgImageStudentAnswer.getZoneIdentifire(zoneIndex), 
    												setLabelsModel, setLabelsAnswerArranged)){
    									
    									VennZone zoneAnswer = svgImageStudentAnswer.getZone(zoneIndex);
    									if((markDataZone.getLabel().equals(zoneAnswer.getVennValue().getValue())
    											|| markDataZone.getLabel().equals("ignore"))
    											&&
    											(markDataZone.getColor().equals(zoneAnswer.getColor())
    											|| markDataZone.getColor().equals("ignore"))){
    										//Part of mark set is correct
    									}
    									else{
    										check = false;
    										
    										//Add feedbacks
    										if(!markDataZone.getLabel().equals(zoneAnswer.getVennValue().getValue()) 
    												&& !markDataZone.getLabel().equals("ignore")){
    											//Labels are not matching
    											if(zoneAnswer.getVennValue().getValue()==null)
    												feedbackSubQ.addFeedback("No label present at the zone "+zoneAnswer.getIdentifire());
    											else{
    												feedbackSubQ.addFeedback("Label present at the zone \'"+zoneAnswer.getIdentifire()
    														+ "\' should be corrected to " + markDataZone.getLabel());
    												
    											}
    											
    										}
    										
    										if(zoneAnswer.getColor()==null 
        											&& !markDataZone.getColor().equals("ignore")){
    											//Zone is not colored
    											feedbackSubQ.addFeedback("Zone \'"+zoneAnswer.getIdentifire() +"\' has to be colored");
    										}
    										
    										break;
    									}
    								}
    								if(zoneIndex== svgImageStudentAnswer.getNoOfZones()-1){
    									//No zone has matched
    									check = false;
    								}
    								if(!check){
    									break;
    								}
    							}
    						}
    						else{
    							Logger.getLogger("Invalid type\n");
    						}
            				
                		}
    					
    					//Add feedbacks
    					
    					if(check){
    						//TODO; Add general feedbacks to sub question
    					}
    					else{
    						
    					}
    					
    				} // Evaluated for each mark set "all"
    				else if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase().contains("any")){
    					
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markingRubric.getNoOfMarkSetElements(subQuestionIndex, markSetIndex); 
        						markSetElementIndex++){
                			
            				
                		}
    				} // Evaluated for each mark set "any"
    				
    				markSetFeedbacks.add(feedbackSubQ);
    				
    				//Find the markset feedback with maximum mark
    				FeedbackSubQuestion markSetFeedbackWithMaxMark = markSetFeedbacks.get(0);
    				for(int markSetFeedbackIndex=1; markSetFeedbackIndex<markSetFeedbacks.size(); markSetFeedbackIndex++){
    					if(markSetFeedbackWithMaxMark.getMarks() < markSetFeedbacks.get(markSetFeedbackIndex).getMarks()){
    						markSetFeedbackWithMaxMark = markSetFeedbacks.get(markSetFeedbackIndex);
    					}
    				}
    				
    				feedback.addSubQuestion(markSetFeedbackWithMaxMark);
    				
        		} //Evaluated for each sub question
    			
    			//calculate total Marks
    			int totalMarks = 0;
    			for(int subQuestionFeedbackIndex=0; 
    					subQuestionFeedbackIndex<feedback.getSubQuestionFeddbacks().size(); 
    					subQuestionFeedbackIndex++){
    				
    				totalMarks+= feedback.getSubQuestionFeddback(subQuestionFeedbackIndex).getMarks();
    			}
    		}
    	}
    	
    	
    }
    
    
}
