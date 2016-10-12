/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.rubric;

import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class Rubric {
    String type;
    ArrayList<Question> questions;
    
    public Rubric(String type){
        this.type = type;
        questions = new ArrayList<Question>();
        questions.add(new Question());
    }
    
    public void setDiagramType(String type) {
        this.type = type;
    }

    public void setQuestionID(String id) {
        this.questions.get(0).setID(id);
    }

    public void setQuestionTotalMarks(int totalMarks) {
        this.questions.get(0).setTotalMarks(totalMarks);
    }
    
    public void addSubQuestion(String id) {
        this.questions.get(0).addSubQuestion(id);
    }

    public void setSubQuestionID(String a, String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setSubQuestionModelDiagram(String modelDiagram, int index) {
        this.questions.get(0).setSubQuestionModelDiagram(modelDiagram, index);
    }

    public void setSubQuestionModelDiagram(String string, String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setSubQuestionTotalMarks(int totalMarks, int index) {
        this.questions.get(0).setSubQuestionTotalMarks(totalMarks, index);
    }

    public void setSubQuestionTotalMarks(int i, String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void addSubQuestionMarkSet(int id, int subQuestionIndex) {
        questions.get(0).addSubQuestionMarkSet(id, subQuestionIndex);
    }

    public void setSubQuestionMarkSetMarks(int marks, int subQuestionIndex, int markSetIndex) {
        questions.get(0).setSubQuestionMarkSetMarks(marks, subQuestionIndex, markSetIndex);
    }

    public void setSubQuestionMarkSetMethod(String method, int subQuestionIndex, int markSetIndex) {
        questions.get(0).setSubQuestionMarkSetMethod(method, subQuestionIndex, markSetIndex);
    }

    public void addSubQuestionMarkSetElementZone(String zoneLabel, String zoneValue, String zoneColor, int markSetIndex, int subQuestionIndex) {
        questions.get(0).addSubQuestionMarkSetElementZone(zoneLabel, zoneValue, zoneColor, markSetIndex, subQuestionIndex);
    }

    public void addSubQuestionMarkSetElementSet(String setLabel, int markSetIndex, int subQuestionIndex) {
        questions.get(0).addSubQuestionMarkSetElementSet(setLabel, markSetIndex, subQuestionIndex);
    }
    
    public int getNoOfSubQuestions(){
    	return questions.get(0).getNoOfSubQuestions();
    }

	public int getNoOfMarkSets(int subQuestionIndex) {
		return questions.get(0).getNoOfMarkSets(subQuestionIndex);
	}

	public int getNoOfMarkSetElements(int subQuestionIndex, int markSetIndex) {
		return questions.get(0).getNoOfMarkSetElements(subQuestionIndex, markSetIndex);
	}

	public String getSubQuestionId(int subQuestionIndex) {
		return questions.get(0).getSubQuestionId(subQuestionIndex);
	}

	public String getSubQuestionMarkSetMethod(int subQuestionIndex, int markSetIndex) {
		return questions.get(0).getSubQuestionMarkSetMethod(subQuestionIndex, markSetIndex);
	}

	public ArrayList<MarkData> getSubQuestionMarkDataSets(int subQuestionIndex, int markSetIndex) {
		return questions.get(0).getSubQuestionMarkDataSets(subQuestionIndex, markSetIndex);
	}
	
	public int getSubQuestionMarkSetMarks(int subQuestionIndex, int markSetIndex){
		return questions.get(0).getSubQuestionMarkSetMarks(subQuestionIndex, markSetIndex);
	}

	public String getQuestionID() {
		// TODO Auto-generated method stub
		return questions.get(0).getID();
	}

}
