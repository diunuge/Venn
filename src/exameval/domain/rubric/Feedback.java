/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.rubric;

/**
 *
 * @author Diunuge
 */
public class Feedback {
    private String sucess;
    private String fail;
    
    public Feedback(String sucess, String fail){
        this.sucess = sucess;
        this.fail = fail;
    }
    
    String getSucess(){
        return this.sucess;
    }
    
    String getFail(){
        return this.fail;
    }
}
