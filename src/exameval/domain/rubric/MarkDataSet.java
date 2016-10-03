/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.rubric;

/**
 *
 * @author Diunuge
 */
public class MarkDataSet extends MarkData {
    private String setName;
    
    public MarkDataSet(String setName){
        super.type = "set";
        this.setName = setName;
    }
    
    public String getSetName(){
        return this.setName;
    }
}
