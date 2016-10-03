/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.question;

/**
 *
 * @author Diunuge
 */
public class SubQuestion {
    String id;
    String textContent;
    
    public SubQuestion(String id, String textContent){
      this.id = id;
      this.textContent = textContent;
    }
    
    String getTextContent(){
        return this.textContent;
    }
}
