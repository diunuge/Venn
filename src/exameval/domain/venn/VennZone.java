/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.venn;

import exameval.domain.coordinate.Coordinate2D;

/**
 *
 * @author Diunuge
 */
public class VennZone {
    
    private String name;
    private int pixels;     //no of dots in the allocated area
    private Coordinate2D centroid;
    private boolean isValid;
    private VennValue value;
    private String color;
    
    public VennZone(String name){
        this.name = name;
        this.pixels = 0;
        this.centroid = null;
        this.isValid = false;
        this.color = null;
    }
    
    public String getName(){
        return this.name;
    }
    
    public VennValue getVennValue(){
        return this.value;
    }
    
    public int numOfPixels(){
        return this.pixels;
    }
    
    public Coordinate2D getCentroid(){
        return this.centroid;
    }
    
    public void addPixel(int x, int y){
        if(centroid==null){
            centroid = new Coordinate2D(x, y);
            pixels++;
        }else{
            double changeX = (x-centroid.getX())/pixels;
            double changeY = (y-centroid.getY())/pixels;
            centroid.move(changeX, changeY);
            pixels++;
        }
    }
    
    public void validate(){
        if(this.pixels>100)
            this.isValid = true;
        else
            this.isValid = false;
    }
    
    public void setValue(String value){
        this.value = new VennValue(value);
    }
    
    public void setValidity(boolean validity){
        this.isValid = validity;
    }
    
    public boolean isValid(){
        return this.isValid;
    }

    void print() {
        System.out.print("Name: " + this.name + "\tValue: ");
        if(this.value!=null)
            System.out.print(this.value.getValue());
        else
            System.out.print("Null");
        System.out.print("\tColor: ");
        if(this.color!=null)
            System.out.print(this.color);
        else
            System.out.print("Null");
        System.out.println();
    }
    
    void printDebug() {
        System.out.print("Name: " + this.name + "\tValue: ");
        if(this.value!=null)
            System.out.print(this.value.getValue());
        else
            System.out.print("Null");
        System.out.print("\tColor: ");
        if(this.color!=null)
            System.out.print(this.color);
        else
            System.out.print("Null");
        
        System.out.print("\tPixels: "+this.pixels);
        
        if(this.centroid!=null)
            System.out.print("\tCentroid: ("+this.centroid.getX()+","+this.centroid.getY()+")");
        
        System.out.print("\tValidity: "+this.isValid);
        
        System.out.println();
    }

    public void setName(String name) {
        this.name = name;
    }
}
