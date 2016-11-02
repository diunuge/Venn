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
public class Question {
    String id;
    int totalMarks;
    ArrayList<SubQuestion> subQuestions;

    public void setID(String id){
        this.id = id;
    }
    
    public String getID(){
        return this.id;
    }
    
    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
    
    public void addSubQuestion(String id) {
        if(subQuestions==null)
            subQuestions = new ArrayList<>();
            
        subQuestions.add(new SubQuestion(id));
    }

    public void setSubQuestionID(String id, int index) {
        subQuestions.get(index).setID(id);
    }

    public void setSubQuestionModelDiagram(String modelDiagram, int index) {
        subQuestions.get(index).setModelDiagram(modelDiagram);
    }

    public void setSubQuestionModelDiagram(String modelDiagram, String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setSubQuestionTotalMarks(int totalMarks, int index) {
        subQuestions.get(index).setSubQuestionTotalMarks(totalMarks);
    }

    public void setSubQuestionTotalMarks(int i, String id) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void addSubQuestionMarkSet(int id, int subQuestionIndex) {
        subQuestions.get(subQuestionIndex).addMarkSet(id);
    }
    
    void setSubQuestionMarkSetTotalMarks(int marks, int subQuestionIndex, int markSetIndex) {
        subQuestions.get(subQuestionIndex).setMarkSetTotalMarks(marks, markSetIndex);
    }

//    void setSubQuestionMarkSetMethod(String method, int subQuestionIndex, int markSetIndex) {
//        subQuestions.get(subQuestionIndex).setMarkSetMarks(method, markSetIndex);
//    }
//
//    void addSubQuestionMarkSetElementZone(String zoneLabel, String zoneValue, String zoneColor, int markSetIndex, int subQuestionIndex) {
//        subQuestions.get(subQuestionIndex).addMarkSetElementZone(zoneLabel, zoneValue, zoneColor, markSetIndex);
//    }
//
//    void addSubQuestionMarkSetElementSet(String setLabel, int markSetIndex, int subQuestionIndex) {
//        subQuestions.get(subQuestionIndex).addMarkSetElementSet(setLabel, markSetIndex);
//    }

	public int getNoOfSubQuestions() {
		return subQuestions.size();
	}

	public int getNoOfMarkSets(int subQuestionIndex) {
		return subQuestions.get(subQuestionIndex).getNoOfMarkSets();
	}

//	public int getNoOfMarkSetElements(int subQuestionIndex, int markSetIndex) {
//		return subQuestions.get(subQuestionIndex).getNoOfMarkSetElements(markSetIndex);
//	}

	public String getSubQuestionId(int subQuestionIndex) {
		return subQuestions.get(subQuestionIndex).getId();
	}

//	public String getSubQuestionMarkSetMethod(int subQuestionIndex, int markSetIndex) {
//		return subQuestions.get(subQuestionIndex).getMarkSetMethod(markSetIndex);
//	}
//
//	public ArrayList<MarkData> getSubQuestionMarkDataSets(int subQuestionIndex, int markSetIndex) {
//		return subQuestions.get(subQuestionIndex).getMarkDataSets(markSetIndex);
//	}
//	
//	public int getSubQuestionMarkSetMarks(int subQuestionIndex, int markSetIndex){
//		return subQuestions.get(subQuestionIndex).getMarkSetMarks(markSetIndex);
//	}
}
