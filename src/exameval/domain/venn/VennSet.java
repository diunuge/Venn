/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.domain.venn;

import exameval.domain.svg.SVGObject;

/**
 *
 * @author Diunuge
 */
public class VennSet {
    private String name;
    private String svgType;
    private SVGObject svgObject;
    private VennValue value;
    
    public VennSet(String name, String svgType, SVGObject svgObject){
        
        this.name = name;
        this.svgType = svgType;
        this.svgObject = svgObject;
        this.value = null;
    }
    
    public VennSet(String name, String svgType, SVGObject svgObject, String value){
        
        this.name = name;
        this.svgType = svgType;
        this.svgObject = svgObject;
        this.value = new VennValue(value);
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSVGType(){
        return this.svgType;
    }
    
    public SVGObject getSVGObject(){
        return this.svgObject;
    }
    
    public String getValue(){
        return this.value.getValue();
    }
    
    public void setValue(String value){
        this.value = new VennValue(value);
    }

    void print() {
        System.out.println(this.name);
    }
}
