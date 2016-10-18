/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.svg;

/**
 *
 * @author Diunuge
 */
public class SVGRectangle extends SVGObject {
    
    private double x;
    private double y;
    private double height;
    private double width;
    
    public SVGRectangle(double x, double y, double height, double width){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public double getHeight(){
        return this.height;
    }
    
    public double getWidth(){
        return this.width;
    }

    @Override
    public boolean isOnBoundry(double x, double y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isIn(double x, double y) {
        boolean check = false;
        if(x>=this.x && x<=this.x+this.width){
        	if(y>=this.y && y<=this.y+this.height){
        		check = true;
        	}
        }
        return check;
    }
    
    @Override
    public void print(){
        System.out.println("{x=" + this.x + " ,y=" + this.y + " ,height=" + this.height + " ,width=" + this.width + "}" );
    }
}
