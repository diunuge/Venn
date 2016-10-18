package exameval.domain.svg;

import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class SVGImage {
    
    private int height;
    private int width;
    
    private ArrayList<SVGRectangle> rectangles;
    private ArrayList<SVGEllipse> ellipses;
    private ArrayList<SVGCircle> circles;
    private ArrayList<SVGLine> lines;
    private ArrayList<SVGText> texts;
    
    public SVGImage(){
        
        this.height = 0;
        this.width = 0;
        
        this.rectangles = new ArrayList<>();
        this.ellipses = new ArrayList<>();
        this.circles = new ArrayList<>();
        this.lines = new ArrayList<>();
        this.texts = new ArrayList<>();
    }
    
    public void setSize(int height, int width){
        this.height = height;
        this.width = width;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public void addRectangle(SVGRectangle rectangle){
        this.rectangles.add(rectangle);
    }
    
    public void addEllipse(SVGEllipse ellipse){
        this.ellipses.add(ellipse);
    }
    
    public void addCircle(SVGCircle circle){
        this.circles.add(circle);
    }
    
    public void addLine(SVGLine line){
        this.lines.add(line);
    }
    
    public void addText(SVGText text){
        this.texts.add(text);
    }
    
    public int getNumOfRectangles(){
        return this.rectangles.size();
    }
    
    public int getNumOfEllipses(){
        return this.ellipses.size();
    }
    
    public int getNumOfCircles(){
        return this.circles.size();
    }
    
    public int getNumOfLines(){
        return this.lines.size();
    }
    
    public int getNumOfTexts(){
        return this.texts.size();
    }
    
    public ArrayList<SVGRectangle> getRectangles(){
        return this.rectangles;
    }
    
    public ArrayList<SVGEllipse> getEllipses(){
        return this.ellipses;
    }
    
    public ArrayList<SVGCircle> getCircles(){
        return this.circles;
    }
    
    public ArrayList<SVGLine> getLines(){
        return this.lines;
    }
    
    public ArrayList<SVGText> getTexts(){
        return this.texts;
    }
    
    public void print(){
        
        System.out.println("SVG Size: (" + this.height + "," + this.width + ")" );
        
        System.out.println("No of Rectangles: " + rectangles.size());
        for (int i = 0; i < rectangles.size(); i++){
            rectangles.get(i).print();
        }
        
        System.out.println("No of Ellipses: " + ellipses.size());
        for (int i = 0; i < ellipses.size(); i++){
            ellipses.get(i).print();
        }
        
        System.out.println("No of Lines: " + lines.size());
        for (int i = 0; i < lines.size(); i++){
            lines.get(i).print();
        }
        
        System.out.println("No of Texts: " + texts.size());
        for (int i = 0; i < texts.size(); i++){
            texts.get(i).print();
        }
    }
}
