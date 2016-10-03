/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.rubric;

/**
 *
 * @author Diunuge
 */
public class MarkDataZone extends MarkData{
    String zoneIdentifire;
    String label;
    String color;
    
    public MarkDataZone(String zoneIdentifire, String label, String color){
        super.type = "zone";
        this.zoneIdentifire = zoneIdentifire;
        this.label = label;
        this.color = color;
    }
    
    public String getZoneIdentifire(){
    	return this.zoneIdentifire;
    }
    
    public String getLabel(){
    	return this.label;
    }
    
    public String getColor(){
    	return this.color;
    }
}
