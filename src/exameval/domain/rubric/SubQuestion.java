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
class SubQuestion {
    String id;
    String modelDiagram;
    int totalMarks;
    ArrayList<MarkSet> markSets;
    
    public SubQuestion(String id){
        this.id = id;
    }

    public void setID(String id) {
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }

    public void setModelDiagram(String modelDiagram) {
        this.modelDiagram = modelDiagram;
    }

    public void setSubQuestionTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void addMarkSet(int id) {
        if(markSets == null)
            markSets = new ArrayList<>();
            
        markSets.add(new MarkSet(id));        
    }
    
    public void setMarkSetMarks(int marks, int markSetIndex){
        markSets.get(markSetIndex).setMarks(marks);
    }

    public void setMarkSetMarks(String method, int markSetIndex) {
        markSets.get(markSetIndex).setMethod(method);
    }

    void addMarkSetElementZone(String zoneLabel, String zoneValue, String zoneColor, int markSetIndex) {
        markSets.get(markSetIndex).addElementZone(zoneLabel, zoneValue, zoneColor);
    }

    void addMarkSetElementSet(String setLabel, int markSetIndex) {
        markSets.get(markSetIndex).addMarkSetElementSet(setLabel);
    }

	public int getNoOfMarkSets() {
		return markSets.size();
	}

	public int getNoOfMarkSetElements(int markSetIndex) {
		return markSets.get(markSetIndex).getNoOfElements();
	}

	public String getMarkSetMethod(int markSetIndex) {
		return markSets.get(markSetIndex).getMethod();
	}

	public ArrayList<MarkData> getMarkDataSets(int markSetIndex) {
		return markSets.get(markSetIndex).getMarkDataSets();
	}
	
	public int getMarkSetMarks(int markSetIndex){
		return markSets.get(markSetIndex).getMarks();
	}
}
