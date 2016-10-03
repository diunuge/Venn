/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.rubric;

/**
 *
 * @author Diunuge
 */
public abstract class MarkData {
    protected String type; //Zone or Set
    
    String getType(){
        return this.type;
    }
}
