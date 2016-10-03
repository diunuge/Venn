package exameval.domain.svg;

/**
 *
 * @author Diunuge
 */
public abstract class SVGObject {
    
    private String id;
    private String stroke;
    private String strokeOpacity;
    private String strokeWidth;
    private String fill;
    private String fillOpacity;
    
    public final String getID(){
        return this.id;
    }
    
    public final String getStroke(){
        return this.stroke;
    }
    
    public final String getStrokeOpacity(){
        return this.strokeOpacity;
    }
    
    public final String getStrokeWitdh(){
        return this.strokeWidth;
    }
    
    public final String getFill(){
        return this.fill;
    }
    
    public final String getFillOpacity(){
        return this.fillOpacity;
    }
    
    public abstract boolean isOnBoundry(double x, double y);
    
    public abstract boolean isIn(double x, double y);
    
    public abstract void print();
}
