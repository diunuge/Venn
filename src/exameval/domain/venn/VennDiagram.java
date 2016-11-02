package exameval.domain.venn;

import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class VennDiagram {
    
    private String name;
    private int noOfSets;
    private ArrayList<VennSet> sets;
    private ArrayList<VennZone> zones;
    
    public VennDiagram(String name){
        this.name = name;
        this.noOfSets = 0;
        
        sets = new ArrayList<>();
        zones = new ArrayList<>();
    }
    
    public String getName(){
        return this.name;
    }
    
    public int getNoOfSets(){
        return this.noOfSets;
    }
    
    public void addSet(VennSet set){
        this.sets.add(set);
        this.noOfSets++;
    }
    
    public void addZone(VennZone zone){
        this.zones.add(zone);
    }
    
    public int getNoOfZones(){
    	return this.zones.size();
    }
    
    public VennZone getZone(int zoneIndex){
    	return this.zones.get(zoneIndex);
    }
    
    public String getZoneIdentifire(int zoneIndex){
    	return this.zones.get(zoneIndex).getIdentifire();
    }
    
    public ArrayList<String> getSetLabels(){
    	ArrayList<String> setLabels = new ArrayList<>();
    	
    	for(VennSet set: sets){
    		setLabels.add(set.getName());
    	}
    	
    	return setLabels;
    }
    
    public void print(){
        System.out.println("\n\n"+this.name + " Venn Diagram\n");
        
        System.out.println("No of sets: "+ this.noOfSets);
        
        for (int i = 0; i < sets.size(); i++){
            sets.get(i).print();
        }
        
        System.out.println("Venn zones:");
        
        for (int i = 0; i < zones.size(); i++){
            zones.get(i).print();
        }
        
        System.out.println("\n\n");
    }
    
    public void printDebug(){
        System.out.println("\n\n"+this.name + " Venn Diagram\n");
        
        System.out.println("No of sets: "+ this.noOfSets);
        
        for (int i = 0; i < sets.size(); i++){
            sets.get(i).print();
        }
        
        System.out.println("Venn zones:");
        
        for (int i = 0; i < zones.size(); i++){
            zones.get(i).printDebug();
        }
        
        System.out.println("\n\n");
    }
    
    
    public String toString(){
    	String result = "";
    	
    	result += "<infographic>\n";

    	result += "\t<type>Venn Diagram</type>\n";
    	result += "\t<title>"+this.name+"</title>\n";
    	result += "\t<no_of_sets>"+this.noOfSets+"</no_of_sets>\n";
    	
    	result += "\t<sets>\n";
    	for (int i = 0; i < sets.size(); i++){
    		result += "\t\t<set>"+this.sets.get(i).getName()+"</set>\n";
        }
    	result += "\t</sets>\n";

    	result += "\t<data_set>\n";
    	for (int i = 0; i < zones.size(); i++){
    		
    		result += "\t\t<zone>";
    		
    		result += "<label>";
    		result +=zones.get(i).getIdentifire()!=null?zones.get(i).getIdentifire():"none";
    		result += "</label>";
    		
    		result += "<value>";
    		result +=zones.get(i).getValue()!=null?zones.get(i).getValue():"none";
    		result += "</value>";
    		
    		result += "<color>";
    		result +=zones.get(i).getColor();
    		result += "</color>";
    		
    		result += "</zone>\n";
        }
    	result += "\t</data_set>\n";

    	result += "</infographic>\n";
    	
    	return result;
    }
    
}
