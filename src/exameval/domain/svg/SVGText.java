/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.svg;

import org.apache.commons.lang3.StringEscapeUtils;

/**
 *
 * @author Diunuge
 */
public class SVGText  extends SVGObject {
    
    private double x;
    private double y;
    private String text;
    private int fontSize;
    private boolean isNumeric;
    private Double numericValue;
    
    //private String xmlSpace;
    //private String textAnchor;
    //private String fontFamily;
    //private String fontSize;
    
    
    public SVGText(double x, double y, String text, int fontSize){
        this.x = x;
        this.y = y;
        this.text = text;
        //this.text = StringEscapeUtils.unescapeHtml4(text);
        //System.out.println("Text :"+text+" Converted: "+this.text);
        this.fontSize = fontSize;
        
        if(_isNumeric()){
            this.isNumeric = true;
            this.numericValue = Double.parseDouble(this.text);
        }
        else{
            this.isNumeric = false;
            this.numericValue = null;
        }
    }
    
    public double getX(){
    	//Average length of a character is a 13 with font size 24 with "Helvetica" font family

    	double correction = fontSize*text.length()*0.54/2;
        return this.x + correction;
    }
    
    public double getY(){
    	//Average correction is a 11.5/24 with font size 24 with "Helvetica" font family
    	double correction = fontSize*0.48;
        return this.y - correction;
    }
    
    public double getHeight(){
    	return fontSize/0.48;
    }
    
    public double getWitdh(){
    	return fontSize*text.length()*0.54;
    }
    
    public String getText(){
        return this.text;
    }
    
    public boolean isNumeric(){
        return this.isNumeric;
    }
    
    public Double getNumericValue(){
        return this.numericValue;
    }

    @Override
    public boolean isOnBoundry(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isIn(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void print(){
        System.out.println("{x=" + this.x + " ,y=" + this.y + " ,text=" + this.text + "}" );
    }
    
    private final boolean _isNumeric(){
        try  
        {  
            Double.parseDouble(this.text);  
        }  
        catch(NumberFormatException nfex)  
        {  
            return false;  
        }  
        return true; 
    }
    
}
