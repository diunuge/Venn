package exameval.domain.coordinate;

/**
 *
 * @author Diunuge
 */
public class Coordinate2D {
    
    private double x;
    private double y;
    
    public Coordinate2D(double x, double y){
        this.x = x;
        this.y = y;
    }
    
    public double getX(){
        return this.x;
    }
    
    public double getY(){
        return this.y;
    }
    
    public double getDistance(Coordinate2D point){
        return Math.sqrt( Math.pow(this.x-point.getX(), 2.0)+ Math.pow(this.y-point.getY(), 2.0));
    }
    
    public double getDistance(double x, double y){
        return Math.sqrt( Math.pow(this.x-x, 2.0)+ Math.pow(this.y-y, 2.0));
    }
    
    public static double getDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt( Math.pow(x1-x2, 2.0)+ Math.pow(y1-y2, 2.0));
    }
    
    public void move(double x, double y){
        this.x+=x;
        this.y+=y;
    }
}
