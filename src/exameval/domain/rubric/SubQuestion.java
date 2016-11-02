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
    
    public void setMarkSetTotalMarks(int totalMarks, int markSetIndex){
        markSets.get(markSetIndex).setMarks(totalMarks);
    }

    void addMarkSetElementZone(String zoneLabel, String zoneValue, String zoneColor, int markSetIndex, int markSetDataIndex) {
        markSets.get(markSetIndex).addElementZone(zoneLabel, zoneValue, zoneColor, markSetDataIndex);
    }

    void addMarkSetElementSet(String setLabel, int markSetIndex, int markSetDataIndex) {
        markSets.get(markSetIndex).addMarkSetElementSet(setLabel, markSetDataIndex);
    }

	public int getNoOfMarkSets() {
		return markSets.size();
	}

	public int getNoOfMarkSetDataElements(int markSetIndex, int markSetDataIndex) {
		return markSets.get(markSetIndex).getNoOfElements(markSetDataIndex);
	}

	public String getMarkSetDataMethod(int markSetIndex, int markSetDataIndex) {
		return markSets.get(markSetIndex).getDataMethod(markSetDataIndex);
	}

	public ArrayList<MarkData> getMarkDataSets(int markSetIndex, int markSetDataIndex) {
		return markSets.get(markSetIndex).getMarkDataSets(markSetDataIndex);
	}
	
	public int getMarkSetTotalMarks(int markSetIndex){
		return markSets.get(markSetIndex).getTotalMarks();
	}
}
