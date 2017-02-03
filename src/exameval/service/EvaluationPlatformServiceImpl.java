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
    	
    	//identify the labels pairs
    	ArrayList<String> setLabelsModel = svgImageModelAnswer.getSetLabels();
    	ArrayList<String> setLabelsAnswer = svgImageStudentAnswer.getSetLabels();
    	
//    	ArrayList<String> setLabelModel = new ArrayList<String>(){{
//    		add("දැව සහිත");
//    		add("දේශීයව නිෂ්පාදිත");
//    		add("වට්ටම් ලබා දෙන");
//    	}};
//    	ArrayList<String> setLabelAnswer = new ArrayList<>();
//    	setLabelAnswer.add("දැව සහිත");
//    	setLabelAnswer.add("දේශීය");
//    	setLabelAnswer.add("වට්ටම් ලබා දෙන");
    	
    	Double[][] smilarity = new Double[setLabelsModel.size()][setLabelsAnswer.size()];
    	Integer[] association = new Integer[setLabelsModel.size()];
    	
    	ArrayList<String> setLabelsAnswerArranged = new ArrayList<>();
    	
	    //TODO: generate feedback for missing labels
    	Boolean validityCheck = true;
    	//Check for the basic structure
    	if(setLabelsModel.size()!= setLabelsAnswer.size()){
    		//TODO: generate feedback : diagram mismatch - set numbers are different
    		feedback.addQuestionFeedback("Set structure is different, Need to draw diagram with "+ setLabelsModel.size() + " sets");
    		feedback.setTotalMarks(0);
    		validityCheck = false;
    	}
    	
    	for(int answerLabelIndex=0; answerLabelIndex<setLabelsAnswer.size(); answerLabelIndex++){
    		if(setLabelsAnswer.get(answerLabelIndex).toLowerCase().contains("unlabeled")){
    			feedback.addQuestionFeedback("All sets are not labeled correctly!");
        		feedback.setTotalMarks(0);
        		validityCheck = false;
        		break;
    		}
    	}
    	
    	if(validityCheck){
    		
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
        		if(association[modelLabelIndex] != null)
        			setLabelsAnswerArranged.add(setLabelsAnswer.get(associationIndex));
        		else{
        			setLabelsAnswerArranged.add("Unlabeled_0");
        		}
        	}
    		
    		feedback.setQuestionId(markingRubric.getQuestionID());
    		
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
    				
    				int marks = 0;
    				feedbackSubQ = new FeedbackSubQuestion(subQuestionId, marks);
    				
    				//Evaluate for each data
    				for(int dataIndex = 0; 
    						dataIndex< markingRubric.getNoOfMarkSetData(subQuestionIndex, markSetIndex); 
    						dataIndex++){
    					
    					//check method
    					//for "all"
    					 	//get elements
    						//evaluate
    						//if sucess
    							//add marks to "marks"
    					
    					//
    					ArrayList<MarkData> markDataSets = 
        						markingRubric.getSubQuestionMarkDataSets(subQuestionIndex, markSetIndex, dataIndex);
        				
        				//String method = markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex);
        				if(markingRubric.getSubQuestionMarkSetDataMethod(subQuestionIndex, markSetIndex, dataIndex).
        						toLowerCase().equals("all")){
        					
        					boolean check = true;
        					for(int markSetElementIndex = 0; 
            						markSetElementIndex< markDataSets.size(); 
            						markSetElementIndex++){
                    			
        						
        						MarkData markData = markDataSets.get(markSetElementIndex);
        						String type = markData.getType();
        						
        						if(type.equals("set")){
        							MarkDataSet markDataSet = (MarkDataSet)markData;
        							if(!SetNameCheck.isSameSetName(markDataSet.getSetName(), 
        									setLabelsModel, setLabelsAnswerArranged)){
        								
        								check = false;
        								feedbackSubQ.addFeedback("Set \'" + markDataSet.getSetName() + "\' is not correctly labelled!");
        								break;
        							}
        							else{
        								//feedbackSubQ.addFeedback("Correct set label");
        							}
        						}
        						else if(type.equals("zone")){
        							MarkDataZone markDataZone = (MarkDataZone)markData;
        							
        							boolean isZoneMatched = false;
        							
        							for(int zoneIndex=0; 
        									zoneIndex < svgImageStudentAnswer.getNoOfZones(); 
        									zoneIndex++){
        								
        								//Matching zone identifire
        								if(SetNameCheck.
        										isSameZone(
        												markDataZone.getZoneIdentifire(), 
        												svgImageStudentAnswer.getZoneIdentifire(zoneIndex), 
        												setLabelsModel, setLabelsAnswerArranged)){
        									
        									isZoneMatched = true;
        									
        									VennZone zoneAnswer = svgImageStudentAnswer.getZone(zoneIndex);
        									if((markDataZone.getLabel().equals(zoneAnswer.getValue())
        											|| markDataZone.getLabel().equals("ignore"))
        											&&
        											(markDataZone.getColor().equals(zoneAnswer.getColor())
        											|| markDataZone.getColor().equals("ignore"))){
        										//feedbackSubQ.addFeedback("Correct zone");
        									}
        									else{
        										check = false;
        										
        										//Add feedbacks
        										if(!markDataZone.getLabel().equals(zoneAnswer.getValue()) 
        												&& !markDataZone.getLabel().equals("ignore")){
        											//Labels are not matching
        											if(zoneAnswer.getValue()==null)
        												feedbackSubQ.addFeedback("No label present at the zone "+zoneAnswer.getIdentifire());
        											else{
        												if(zoneAnswer.getValue().contains("$"))
        													feedbackSubQ.addFeedback("Multiple labels present at the zone \'"+zoneAnswer.getIdentifire());
        												else
        													feedbackSubQ.addFeedback("Label present at the zone \'"+zoneAnswer.getIdentifire()
        														+ "\' should be corrected to " + markDataZone.getLabel()+ ", instead of "+zoneAnswer.getValue());
        												
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
        							} // end of zone element evaluation

    								if(!isZoneMatched){
    									//No zone has matched
    									String[] labelPartsModel = markDataZone.getZoneIdentifire().split("\\.");
    									if(labelPartsModel.length < setLabelsModel.size())
    										feedbackSubQ.addFeedback("No label present at the zone "+markDataZone.getZoneIdentifire());
    									check = false;
    									break;
    								}
        						}
        						else{
        							Logger.getLogger("Invalid type\n");
        						}
                				
                    		}
        					
        					//Add feedbacks
        					
        					if(check){
        						// feedbackSubQ.addMarks(markingRubric.getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex));
        						marks += markingRubric.getSubQuestionMarkSetDataMarks(subQuestionIndex, markSetIndex, dataIndex);
        						//feedbackSubQ.addFeedback("Correct answer");
        					}
        					else{
    							//feedbackSubQ.addMarks(0);
        					}
        					
        				} 
        				
        				// Evaluated for each mark set "all"
        				else if(markingRubric.getSubQuestionMarkSetDataMethod(subQuestionIndex, markSetIndex, dataIndex).
        						toLowerCase().contains("any")){
        					
        					int reqiredCorrectElements = Integer.parseInt(markingRubric.
        							getSubQuestionMarkSetDataMethod(subQuestionIndex, markSetIndex, dataIndex).
        							split("_")[1]);
        					int totalCorrectElements = 0;
        					
        					for(int markSetElementIndex = 0; 
            						markSetElementIndex< markDataSets.size(); 
            						markSetElementIndex++){
                    			
        						
        						MarkData markData = markDataSets.get(markSetElementIndex);
        						String type = markData.getType();
        						
        						if(type.equals("set")){
        							MarkDataSet markDataSet = (MarkDataSet)markData;
        							if(!SetNameCheck.isSameSetName(markDataSet.getSetName(), 
        									setLabelsModel, setLabelsAnswerArranged)){
        								
        								feedbackSubQ.addFeedback("Set \'" + markDataSet.getSetName() + "\' is not correctly labelled!");
        							}
        							else{
        								//feedbackSubQ.addFeedback("Correct set label");
        								totalCorrectElements++;
        							}
        						}
        						else if(type.equals("zone")){
        							MarkDataZone markDataZone = (MarkDataZone)markData;
        							
        							boolean isZoneMatched = false;
        							
        							for(int zoneIndex=0; 
        									zoneIndex < svgImageStudentAnswer.getNoOfZones(); 
        									zoneIndex++){
        								
        								//Matching zone identifire
        								if(SetNameCheck.
        										isSameZone(
        												markDataZone.getZoneIdentifire(), 
        												svgImageStudentAnswer.getZoneIdentifire(zoneIndex), 
        												setLabelsModel, setLabelsAnswerArranged)){
        									
        									isZoneMatched = true;
        									
        									VennZone zoneAnswer = svgImageStudentAnswer.getZone(zoneIndex);
        									if((markDataZone.getLabel().equals(zoneAnswer.getValue())
        											|| markDataZone.getLabel().equals("ignore"))
        											&&
        											(markDataZone.getColor().equals(zoneAnswer.getColor())
        											|| markDataZone.getColor().equals("ignore"))){
        										
        										//feedbackSubQ.addFeedback("Correct zone");
        										totalCorrectElements++;
        									}
        									else{
        										
        										//Add feedbacks
        										if(!markDataZone.getLabel().equals(zoneAnswer.getValue()) 
        												&& !markDataZone.getLabel().equals("ignore")){
        											//Labels are not matching
        											if(zoneAnswer.getValue()==null)
        												feedbackSubQ.addFeedback("No label present at the zone "+zoneAnswer.getIdentifire());
        											else{
        												if(zoneAnswer.getValue().contains("$"))
        													feedbackSubQ.addFeedback("Multiple labels present at the zone \'"+zoneAnswer.getIdentifire());
        												else
        													feedbackSubQ.addFeedback("Label present at the zone \'"+zoneAnswer.getIdentifire()
        														+ "\' should be corrected to " + markDataZone.getLabel()+ ", instead of "+zoneAnswer.getValue());
        												
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
        							} // end of zone element evaluation

    								if(!isZoneMatched){
    									//No zone has matched
    								}
        						}
        						else{
        							Logger.getLogger("Invalid type\n");
        						}
                				
                    		}
        					
        					//Add feedbacks
        					
        					if(reqiredCorrectElements<=totalCorrectElements){
        						//feedbackSubQ.addMarks(markingRubric.getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex));
        						marks += markingRubric.getSubQuestionMarkSetDataMarks(subQuestionIndex, markSetIndex, dataIndex);
        						//feedbackSubQ.addFeedback("Correct answer");
        					}
        					else{
    							//feedbackSubQ.addMarks(0);
    							feedbackSubQ.addFeedback(totalCorrectElements+" correct, "+reqiredCorrectElements+" reqired!");
        					}
        				} // Evaluated for each mark set "any"
    					
    				}
    				
    				feedbackSubQ.addMarks(marks);
    				
    				if(feedbackSubQ.getSubQuestionFeedbacks()==null)
    					feedbackSubQ.addFeedback("Correct answer");
    				
    				markSetFeedbacks.add(feedbackSubQ);
    				
        		} // evaluated for one mark set
    			
    			//Find the markset feedback with maximum mark
				FeedbackSubQuestion markSetFeedbackWithMaxMark = markSetFeedbacks.get(0);
				for(int markSetFeedbackIndex=1; markSetFeedbackIndex<markSetFeedbacks.size(); markSetFeedbackIndex++){
					if(markSetFeedbackWithMaxMark.getMarks() < markSetFeedbacks.get(markSetFeedbackIndex).getMarks()){
						markSetFeedbackWithMaxMark = markSetFeedbacks.get(markSetFeedbackIndex);
					}
				}
				
				feedback.addSubQuestion(markSetFeedbackWithMaxMark);
    		}// End of marking sub questions
    		
    		//calculate total Marks
			int totalMarks = 0;
			for(int subQuestionFeedbackIndex=0; 
					subQuestionFeedbackIndex<feedback.getSubQuestionFeddbacks().size(); 
					subQuestionFeedbackIndex++){
				
				totalMarks+= feedback.getSubQuestionFeddback(subQuestionFeedbackIndex).getMarks();
			}
			
			feedback.setTotalMarks(totalMarks);
    	}
    }


 /*   
    public void evaluateOld(VennDiagram svgImageStudentAnswer, Question question, VennDiagram svgImageModelAnswer, Rubric markingRubric, Feedback feedback) {
    	
    	//identify the labels pairs
    	ArrayList<String> setLabelsModel = svgImageModelAnswer.getSetLabels();
    	ArrayList<String> setLabelsAnswer = svgImageStudentAnswer.getSetLabels();
    	
//    	ArrayList<String> setLabelModel = new ArrayList<String>(){{
//    		add("දැව සහිත");
//    		add("දේශීයව නිෂ්පාදිත");
//    		add("වට්ටම් ලබා දෙන");
//    	}};
//    	ArrayList<String> setLabelAnswer = new ArrayList<>();
//    	setLabelAnswer.add("දැව සහිත");
//    	setLabelAnswer.add("දේශීය");
//    	setLabelAnswer.add("වට්ටම් ලබා දෙන");
    	
    	Double[][] smilarity = new Double[setLabelsModel.size()][setLabelsAnswer.size()];
    	Integer[] association = new Integer[setLabelsModel.size()];
    	
    	ArrayList<String> setLabelsAnswerArranged = new ArrayList<>();
    	
	    //TODO: generate feedback for missing labels
    	
    	//Check for the basic structure
    	if(setLabelsModel.size()!= setLabelsAnswer.size()){
    		//TODO: generate feedback : diagram mismatch - set numbers are different
    		feedback.addQuestionFeedback("Set structure is different, Need to draw diagram with "+ setLabelsModel.size() + " sets");
    		feedback.setTotalMarks(0);
    	}
    	else{
    		
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
        		if(association[modelLabelIndex] != null)
        			setLabelsAnswerArranged.add(setLabelsAnswer.get(associationIndex));
        		else{
        			setLabelsAnswerArranged.add("Unlabeled_0");
        		}
        	}
    		
    		feedback.setQuestionId(markingRubric.getQuestionID());
    		
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
    				
    				//String method = markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex);
    				if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase().equals("all")){
    					
    					boolean check = true;
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markDataSets.size(); 
        						markSetElementIndex++){
                			
    						
    						MarkData markData = markDataSets.get(markSetElementIndex);
    						String type = markData.getType();
    						
    						if(type.equals("set")){
    							MarkDataSet markDataSet = (MarkDataSet)markData;
    							if(!SetNameCheck.isSameSetName(markDataSet.getSetName(), 
    									setLabelsModel, setLabelsAnswerArranged)){
    								
    								check = false;
    								feedbackSubQ.addFeedback("Set \'" + markDataSet.getSetName() + "\' is not correctly labelled!");
    								break;
    							}
    							else{
    								//feedbackSubQ.addFeedback("Correct set label");
    							}
    						}
    						else if(type.equals("zone")){
    							MarkDataZone markDataZone = (MarkDataZone)markData;
    							
    							boolean isZoneMatched = false;
    							
    							for(int zoneIndex=0; 
    									zoneIndex < svgImageStudentAnswer.getNoOfZones(); 
    									zoneIndex++){
    								
    								//Matching zone identifire
    								if(SetNameCheck.
    										isSameZone(
    												markDataZone.getZoneIdentifire(), 
    												svgImageStudentAnswer.getZoneIdentifire(zoneIndex), 
    												setLabelsModel, setLabelsAnswerArranged)){
    									
    									isZoneMatched = true;
    									
    									VennZone zoneAnswer = svgImageStudentAnswer.getZone(zoneIndex);
    									if((markDataZone.getLabel().equals(zoneAnswer.getValue())
    											|| markDataZone.getLabel().equals("ignore"))
    											&&
    											(markDataZone.getColor().equals(zoneAnswer.getColor())
    											|| markDataZone.getColor().equals("ignore"))){
    										//feedbackSubQ.addFeedback("Correct zone");
    									}
    									else{
    										check = false;
    										
    										//Add feedbacks
    										if(!markDataZone.getLabel().equals(zoneAnswer.getValue()) 
    												&& !markDataZone.getLabel().equals("ignore")){
    											//Labels are not matching
    											if(zoneAnswer.getValue()==null)
    												feedbackSubQ.addFeedback("No label present at the zone "+zoneAnswer.getIdentifire());
    											else{
    												feedbackSubQ.addFeedback("Label present at the zone \'"+zoneAnswer.getIdentifire()
    														+ "\' should be corrected to " + markDataZone.getLabel()+ ", instead of "+zoneAnswer.getValue());
    												
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
    							} // end of zone element evaluation

								if(!isZoneMatched){
									//No zone has matched
									String[] labelPartsModel = markDataZone.getZoneIdentifire().split("\\.");
									if(labelPartsModel.length < setLabelsModel.size())
										feedbackSubQ.addFeedback("No label present at the zone "+markDataZone.getZoneIdentifire());
									check = false;
									break;
								}
    						}
    						else{
    							Logger.getLogger("Invalid type\n");
    						}
            				
                		}
    					
    					//Add feedbacks
    					
    					if(check){
    						feedbackSubQ.addMarks(markingRubric.getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex));
    						feedbackSubQ.addFeedback("Correct answer");
    					}
    					else{
							feedbackSubQ.addMarks(0);
    					}
    					
    				} 
    				
    				// Evaluated for each mark set "all"
    				else if(markingRubric.getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    						toLowerCase().contains("any")){
    					
    					int reqiredCorrectElements = Integer.parseInt(markingRubric.
    							getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex).
    							split("_")[1]);
    					int totalCorrectElements = 0;
    					
    					for(int markSetElementIndex = 0; 
        						markSetElementIndex< markDataSets.size(); 
        						markSetElementIndex++){
                			
    						
    						MarkData markData = markDataSets.get(markSetElementIndex);
    						String type = markData.getType();
    						
    						if(type.equals("set")){
    							MarkDataSet markDataSet = (MarkDataSet)markData;
    							if(!SetNameCheck.isSameSetName(markDataSet.getSetName(), 
    									setLabelsModel, setLabelsAnswerArranged)){
    								
    								feedbackSubQ.addFeedback("Set \'" + markDataSet.getSetName() + "\' is not correctly labelled!");
    							}
    							else{
    								//feedbackSubQ.addFeedback("Correct set label");
    								totalCorrectElements++;
    							}
    						}
    						else if(type.equals("zone")){
    							MarkDataZone markDataZone = (MarkDataZone)markData;
    							
    							boolean isZoneMatched = false;
    							
    							for(int zoneIndex=0; 
    									zoneIndex < svgImageStudentAnswer.getNoOfZones(); 
    									zoneIndex++){
    								
    								//Matching zone identifire
    								if(SetNameCheck.
    										isSameZone(
    												markDataZone.getZoneIdentifire(), 
    												svgImageStudentAnswer.getZoneIdentifire(zoneIndex), 
    												setLabelsModel, setLabelsAnswerArranged)){
    									
    									isZoneMatched = true;
    									
    									VennZone zoneAnswer = svgImageStudentAnswer.getZone(zoneIndex);
    									if((markDataZone.getLabel().equals(zoneAnswer.getValue())
    											|| markDataZone.getLabel().equals("ignore"))
    											&&
    											(markDataZone.getColor().equals(zoneAnswer.getColor())
    											|| markDataZone.getColor().equals("ignore"))){
    										
    										//feedbackSubQ.addFeedback("Correct zone");
    										totalCorrectElements++;
    									}
    									else{
    										
    										//Add feedbacks
    										if(!markDataZone.getLabel().equals(zoneAnswer.getValue()) 
    												&& !markDataZone.getLabel().equals("ignore")){
    											//Labels are not matching
    											if(zoneAnswer.getValue()==null)
    												feedbackSubQ.addFeedback("No label present at the zone "+zoneAnswer.getIdentifire());
    											else{
    												feedbackSubQ.addFeedback("Label present at the zone \'"+zoneAnswer.getIdentifire()
    														+ "\' should be corrected to " + markDataZone.getLabel()+ ", instead of "+zoneAnswer.getValue());
    												
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
    							} // end of zone element evaluation

								if(!isZoneMatched){
									//No zone has matched
								}
    						}
    						else{
    							Logger.getLogger("Invalid type\n");
    						}
            				
                		}
    					
    					//Add feedbacks
    					
    					if(reqiredCorrectElements<=totalCorrectElements){
    						feedbackSubQ.addMarks(markingRubric.getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex));
    						feedbackSubQ.addFeedback("Correct answer");
    					}
    					else{
							feedbackSubQ.addMarks(0);
							feedbackSubQ.addFeedback(totalCorrectElements+" correct, "+reqiredCorrectElements+" reqired!");
    					}
    				} // Evaluated for each mark set "any"
    				
    				markSetFeedbacks.add(feedbackSubQ);
    				
        		} // evaluated for one mark set
    			
    			//Find the markset feedback with maximum mark
				FeedbackSubQuestion markSetFeedbackWithMaxMark = markSetFeedbacks.get(0);
				for(int markSetFeedbackIndex=1; markSetFeedbackIndex<markSetFeedbacks.size(); markSetFeedbackIndex++){
					if(markSetFeedbackWithMaxMark.getMarks() < markSetFeedbacks.get(markSetFeedbackIndex).getMarks()){
						markSetFeedbackWithMaxMark = markSetFeedbacks.get(markSetFeedbackIndex);
					}
				}
				
				feedback.addSubQuestion(markSetFeedbackWithMaxMark);
    		}// End of marking sub questions
    		
    		//calculate total Marks
			int totalMarks = 0;
			for(int subQuestionFeedbackIndex=0; 
					subQuestionFeedbackIndex<feedback.getSubQuestionFeddbacks().size(); 
					subQuestionFeedbackIndex++){
				
				totalMarks+= feedback.getSubQuestionFeddback(subQuestionFeedbackIndex).getMarks();
			}
			
			feedback.setTotalMarks(totalMarks);
    	}
    }
*/
    
}