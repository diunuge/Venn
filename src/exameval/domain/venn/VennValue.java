/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.venn;

/**
 *
 * @author Diunuge
 */
public class VennValue {
    private String value;
    private boolean isNumeric;
    
    public VennValue(String value){
        this.value = value;
        isNumeric = isNumeric(this.value);
    }
    
    public String getValue(){
        return this.value;
    }
    
    public boolean isNumeric(){
        return this.isNumeric;
    }
    
    private final boolean isNumeric(String value){
        try  
        {  
            Double.parseDouble(value);  
        }  
        catch(NumberFormatException nfex)  
        {  
            return false;  
        }  
        return true; 
    }
}
