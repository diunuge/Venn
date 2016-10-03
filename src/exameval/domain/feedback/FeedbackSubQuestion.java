package exameval.domain.feedback;

import java.util.ArrayList;

public class FeedbackSubQuestion {
	
	String subQuestionId;
	int marks;
	ArrayList<String> feedbacks;
	
	public FeedbackSubQuestion(String subQuestionId, int marks) {
		this.subQuestionId = subQuestionId;
		this.marks = marks;
	}
	
	public void addFeedback(String feedback){
		if(feedbacks==null)
			feedbacks = new ArrayList<>();
		
		feedbacks.add(feedback);
	}
}
