/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.svg;

/**
 *
 * @author Diunuge
 */
public class SVGText  extends SVGObject {
    
    private double x;
    private double y;
    private String text;
    private boolean isNumeric;
    private Double numericValue;
    
    //private String xmlSpace;
    //private String textAnchor;
    //private String fontFamily;
    //private String fontSize;
    
    
    public SVGText(double x, double y, String text){
        this.x = x;
        this.y = y;
        this.text = text;
        
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
        return this.x;
    }
    
    public double getY(){
        return this.y;
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
