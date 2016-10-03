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
        System.out.println("\n\n"+this.name + "Venn Diagram\n");
        
        System.out.println("No of sets: "+ this.noOfSets);
        
        for (int i = 0; i < sets.size(); i++){
            sets.get(i).print();
        }
        
        System.out.println("Venn zones:");
        
        for (int i = 0; i < zones.size(); i++){
            zones.get(i).print();
        }
    }
    
    public void printDebug(){
        System.out.println("\n\n"+this.name + "Venn Diagram\n");
        
        System.out.println("No of sets: "+ this.noOfSets);
        
        for (int i = 0; i < sets.size(); i++){
            sets.get(i).print();
        }
        
        System.out.println("Venn zones:");
        
        for (int i = 0; i < zones.size(); i++){
            zones.get(i).printDebug();
        }
    }
}
