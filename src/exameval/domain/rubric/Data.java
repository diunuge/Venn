package exameval.domain.rubric;

import java.util.ArrayList;

public class Data {

	int id;
    int marks;
    String method;
    ArrayList<MarkData> markDataSets;

    public Data(int id) {
        this.id = id;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    void setMethod(String method) {
        this.method = method;                
    }

    void addElementZone(String zoneLabel, String zoneValue, String zoneColor) {
        if(markDataSets==null)
            markDataSets = new ArrayList<>();
        
        markDataSets.add(new MarkDataZone(zoneLabel, zoneValue, zoneColor));
    }

    void addMarkSetElementSet(String setLabel) {
        if(markDataSets==null)
            markDataSets = new ArrayList<>();
        
        markDataSets.add(new MarkDataSet(setLabel));
    }

	public int getNoOfElements() {
		return markDataSets.size();
	}

	public String getMethod() {
		return this.method;
	}

	public ArrayList<MarkData> getMarkDataSets() {
		return this.markDataSets;
	}
	
	public int getMarks(){
		return this.marks;
	}
}
