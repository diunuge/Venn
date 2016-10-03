package exameval.domain.feedback;

import java.util.ArrayList;

public class Feedback {
	String questionId;
	String questionType;
	int totalMarks;
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
	
	public void addSubQuestion(String subQuestionId, int marks){
		if(subquestionFeddbacks==null)
			subquestionFeddbacks = new ArrayList<>();
		
		subquestionFeddbacks.add(new FeedbackSubQuestion(subQuestionId, marks));
	}
	
	public void addSubQuestionFeedback(String feedback, int subQuestionFeddbackIndex){
		subquestionFeddbacks.get(subQuestionFeddbackIndex).addFeedback(feedback);
	}
	
	public String toString(){
		String feedback = "";
		
		feedback += "Question Type:\t\"" + this.questionType + "\"\n";
		feedback += "Question id:\t\"" + this.questionId + "\"\n";
		feedback += "Total Marks:\t" + this.totalMarks + "\n\n";
		
		for(FeedbackSubQuestion subFeddback: subquestionFeddbacks){

			feedback += "Sub Question: \"" + subFeddback.subQuestionId + "\"\n";
			feedback += "\tMarks: " + subFeddback.marks + "\n";
			
			if(subFeddback.feedbacks!=null)
				feedback += "\tFeedbacks; \n";
			
			for (String subFeedbackStr: subFeddback.feedbacks){
				feedback += "\t\t# "+ subFeedbackStr +"\n";
			}
		}
		
		return feedback;
	}
	
	
}
