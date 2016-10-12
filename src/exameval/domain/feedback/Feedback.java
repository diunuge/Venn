package exameval.domain.feedback;

import java.util.ArrayList;

public class Feedback {
	String questionId;
	String questionType;
	int totalMarks;
	ArrayList<String> questionFeddbacks;
	ArrayList<FeedbackSubQuestion> subquestionFeddbacks;
	
	public Feedback(){
		
	}
	
	public Feedback(String questionId, String questionType){
		this.questionId = questionId;
		this.questionType = questionType;
	}
	
	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}
	
	public void setQuestionType(String questionType){
		this.questionType = questionType;
	}
	
	public void setTotalMarks(int totalMarks){
		this.totalMarks = totalMarks;
	}
	
	public void addQuestionFeedback(String feedback){
		if(questionFeddbacks==null)
			questionFeddbacks = new ArrayList<>();
		
		questionFeddbacks.add(feedback);
	}
	
	public ArrayList<String> getQuestionFeedbacks(){
		return this.questionFeddbacks;
	}
	
	public void addSubQuestion(FeedbackSubQuestion subQuestion){
		if(subquestionFeddbacks==null)
			subquestionFeddbacks = new ArrayList<>();
		
		subquestionFeddbacks.add(subQuestion);
	}
	
	public void addSubQuestion(String subQuestionId, int marks){
		if(subquestionFeddbacks==null)
			subquestionFeddbacks = new ArrayList<>();
		
		subquestionFeddbacks.add(new FeedbackSubQuestion(subQuestionId, marks));
	}
	
	public void addSubQuestionFeedback(String feedback, int subQuestionFeddbackIndex){
		subquestionFeddbacks.get(subQuestionFeddbackIndex).addFeedback(feedback);
	}
	
	public String getQuestionId(){
		return this.questionId;
	}
	
	public String getQuestionType(){
		return this.questionType;
	}
	
	public int getTotalMarks(){
		return this.totalMarks;
	}
	
	public ArrayList<FeedbackSubQuestion> getSubQuestionFeddbacks(){
		return this.subquestionFeddbacks;
	}
	
	public FeedbackSubQuestion getSubQuestionFeddback(int subQuestionFeedbackIndex){
		return this.subquestionFeddbacks.get(subQuestionFeedbackIndex);
	}
	
	public String toString(){
		String feedback = "";
		
		feedback += "Question Type:\t\"" + this.questionType + "\"\n";
		feedback += "Question id:\t\"" + this.questionId + "\"\n";
		feedback += "Total Marks:\t" + this.totalMarks + "\n\n";

		if(questionFeddbacks!=null){

			feedback += "=== Generic feedbacks ===\n\n";
			
			for(String feeddback: questionFeddbacks){
				feedback += "\t" + feeddback + "\n";
			}
		}
		
		if(subquestionFeddbacks!=null){

			feedback += "=== Subquestion feedbacks ===\n\n";
			
			for(FeedbackSubQuestion subFeeddback: subquestionFeddbacks){
	
				feedback += "Sub Question: \"" + subFeeddback.subQuestionId + "\"\n";
				feedback += "\tMarks: " + subFeeddback.marks + "\n";
				
				if(subFeeddback.feedbacks!=null){
					feedback += "\tFeedbacks; \n";
				
					for (String subFeedbackStr: subFeeddback.feedbacks){
						feedback += "\t\t# "+ subFeedbackStr +"\n";
					}
				}
			}
		}
		
		return feedback;
	}
	
	
}
