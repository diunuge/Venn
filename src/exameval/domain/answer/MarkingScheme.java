/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.answer;

import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class MarkingScheme {
    private String type;
    private ArrayList<Solution> solutions;

    public void setDiagramType(String type) {
        this.type = type;
    }

    public void setQuestionID(String id) {
        solutions.add(new Solution());
        this.solutions.get(0);
    }

    public void setTotalMarks(String totalMarks) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public String getType(){
    	return this.type;
    }
}
