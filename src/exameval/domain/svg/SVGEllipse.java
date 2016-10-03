/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.svg;

import exameval.domain.coordinate.Coordinate2D;

/**
 *
 * @author Diunuge
 */


// TODO SVGEllipse the generic code ellipse
public class SVGEllipse extends SVGObject {
    
    private double cx;
    private double cy;
    private double rx;
    private double ry;
    
    private Coordinate2D f1;
    private Coordinate2D f2;
    private double f;
    
    private static double distanceTol = 20;    
    
    public SVGEllipse(double cx, double cy, double rx, double ry){
        this.cx = cx;
        this.cx = cy;
        this.rx = rx;
        this.ry = ry;
        
        f = Math.sqrt(rx*rx - ry*ry);
        f1 = new Coordinate2D(cx-f, cy);
        f2 = new Coordinate2D(cx+f, cy);
    }
    
    public double getCX(){
        return this.cx;
    }
    
    public double getCY(){
        return this.cy;
    }
    
    public double getRX(){
        return this.rx;
    }
    
    public double getRY(){
        return this.ry;
    }

    @Override
    public boolean isOnBoundry(double x, double y) {
        double focalDis = f1.getDistance(x, y) + f2.getDistance(x, y);
        if(Math.abs(2*rx-focalDis) < distanceTol)
            return true;
        else
            return false;
    }
    
    public boolean isCloseToBoundry(double x, double y, double tolerance) {
        double focalDis = f1.getDistance(x, y) + f2.getDistance(x, y);
        if(Math.abs(2*rx-focalDis) < tolerance*2)
            return true;
        else
            return false;
    }
    
    @Override
    public void print(){
        System.out.println("{cx=" + this.cx + " ,cy=" + this.cy + " ,rx=" + this.rx + " ,ry=" + this.ry + "}" );
    }

    @Override
    public boolean isIn(double x, double y) {
        double focalDis = f1.getDistance(x, y) + f2.getDistance(x, y);
        if( (2*rx-focalDis) >= 0)
            return true;
        else
            return false;
    }
}
