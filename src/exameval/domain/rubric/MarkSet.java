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
public class MarkSet {
    
    int id;
    int totalMarks;
    ArrayList<Data> data;

    public MarkSet(int id) {
        this.id = id;
        data = new ArrayList<>();
    }

    public void setMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
    
    public void addData(int id) {
        if(data == null)
        	data = new ArrayList<>();
            
        data.add(new Data(id));        
    }

    void addElementZone(String zoneLabel, String zoneValue, String zoneColor, int markSetDataIndex) {
    	
    	data.get(markSetDataIndex).addElementZone(zoneLabel, zoneValue, zoneColor);
    }

    void addMarkSetElementSet(String setLabel, int markSetDataIndex) {

    	data.get(markSetDataIndex).addMarkSetElementSet(setLabel);
    }

	public int getNoOfElements(int markSetDataIndex) {
		return data.get(markSetDataIndex).markDataSets.size();
	}

	public ArrayList<Data> getMarkSetData() {
		return this.data;
	}
	
	public int getTotalMarks(){
		return this.totalMarks;
	}

	public String getDataMethod(int markSetDataIndex) {
		return data.get(markSetDataIndex).getMethod();
	}


	public ArrayList<MarkData> getMarkDataSets(int markSetDataIndex) {
		return data.get(markSetDataIndex).getMarkDataSets();
	}
}
