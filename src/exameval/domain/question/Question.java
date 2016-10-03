/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.question;

import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class Question {
    String id;
    String type;
    String textContent;
    ArrayList<SubQuestion> subQuestions;
    
    public Question(){
        this.id = null;
        this.type = null;
        this.textContent = null;
    }
    
    public Question(String id, String type){
        this.id = id;
        this.type = type;
    }
    
    public Question(String id, String type, String textContent){
        this.id = id;
        this.type = type;
        this.textContent = textContent;
    }
    
    public void setID(String id){
        this.id = id;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public void setTextContent(String textContent){
        this.textContent = textContent;
    }
    
    public void addSubQuestion(String id, String textContent){
        if(subQuestions == null)
            subQuestions = new ArrayList<>();
        
        subQuestions.add(new SubQuestion(id, textContent));
    }
    
    public String getId(){
    	return this.id;
    }
    
    public String getCompleteTextContent(){
        
        String text = this.textContent;
        
        for (SubQuestion subQuestion: subQuestions){
            text = text + " " +subQuestion.getTextContent();
        }
        
        return text;
    }
}
