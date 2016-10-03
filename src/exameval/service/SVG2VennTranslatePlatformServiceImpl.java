/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.coordinate.Coordinate2D;
import exameval.domain.svg.SVGEllipse;
import exameval.domain.svg.SVGImage;
import exameval.domain.svg.SVGText;
import exameval.domain.venn.VennZone;
import exameval.domain.venn.VennDiagram;
import exameval.domain.venn.VennSet;
import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class SVG2VennTranslatePlatformServiceImpl implements SVG2VennTranslatePlatformService {

    @Override
    public void translate(VennDiagram vennDiagram, SVGImage svgImage) {
        
        int numOfSets = svgImage.getNumOfEllipses();
        int numOfVennAreas = (int)Math.pow(2, numOfSets);
        
        //only considering Ellipses as sets
        ArrayList<SVGEllipse> sets = svgImage.getEllipses();
        ArrayList<SVGText> texts = svgImage.getTexts();
        
        ArrayList<SVGText> nominalTexts = new ArrayList<>();
        
        for (int i = 0; i < texts.size(); i++)
        {
            if(!texts.get(i).isNumeric())
                nominalTexts.add(texts.get(i));
        }
        
        //construct venn areas
        ArrayList<VennSet> vennSets = new ArrayList<>();
        ArrayList<VennZone> vennZones = new ArrayList<>();
        
        if(numOfSets==2){
            for (int i=0; i<2; i++){
                for (int j=0; j<2; j++){
                    String name = i+"."+j;
                    vennZones.add(new VennZone(name));
                }
            }
            
            //Find Set Labels
            int tolerence = 30;
            
            SVGText[] SetLabels = new SVGText[numOfSets];
            
            for (int setNo=0; setNo<numOfSets; setNo++){
                for (int j=0; j<nominalTexts.size(); j++){
                    
                    SVGText text = nominalTexts.get(j);
                    if(sets.get(setNo).isCloseToBoundry(text.getX(), text.getY(), tolerence))
                    {
                        boolean valid = true;
                        for (int k=0; k<numOfSets; k++){
                            if(k==setNo)
                                continue;
                            
                            if(sets.get(k).isCloseToBoundry(text.getX(), text.getY(), tolerence)){
                                valid = false;
                                break;
                            }
                        }
                        
                        // TODO fix for override
                        if(valid){ 
                            SetLabels[setNo] = text;
                            break;
                        }
                    }
                }
            }
            
            for (int i=0; i<2; i++){
                for (int j=0; j<2; j++){
                    String name = "";
                    if (i == 0) {
                        name = name.concat("~" + SetLabels[0].getText());
                    } else {
                        name = name.concat(SetLabels[0].getText());
                    }

                    if (j == 0) {
                        name = name.concat(".~" + SetLabels[1].getText());
                    } else {
                        name = name.concat("." + SetLabels[1].getText());
                    }

                    vennZones.get(i * 2 + j).setName(name);
                }
            }
            
            for (int i=0; i<numOfSets; i++){
                vennSets.add(new VennSet(SetLabels[i].getText(),"Ellipse",sets.get(i)));
            }
            
            //venn area centroid calculations
            for (int i=0; i<svgImage.getWidth(); i++){
                for (int j=0; j<svgImage.getHeight(); j++){
                    
                    if(!sets.get(0).isIn(i, j)){
                        if(!sets.get(1).isIn(i, j)){
                            vennZones.get(0).addPixel(i, j);
                        }else{
                            vennZones.get(1).addPixel(i, j);
                        }
                    }else{
                        if(!sets.get(1).isIn(i, j)){
                            vennZones.get(2).addPixel(i, j);
                        }else{
                            vennZones.get(3).addPixel(i, j);
                        }
                    }
                    
                }
            }
            
            //validate vennAreas
            // At least 100 pixels
            for (int i=0; i<numOfVennAreas; i++){
                vennZones.get(i).validate();
                
                //debug
                if(vennZones.get(i).isValid())
                    System.out.println("Name : "+vennZones.get(i).getName()+"\tpixels : "+vennZones.get(i).numOfPixels()
                            +"\tcentroid : ("+vennZones.get(i).getCentroid().getX()+","+vennZones.get(i).getCentroid().getY()+")");
            }
            
            //Filter text labels with values
            //Devide them to
            ArrayList<SVGText> textRemains = new ArrayList<>(texts);
        
            for (int i=0; i<numOfSets; i++){
                for (int j=0; j<textRemains.size(); j++){
                    if(SetLabels[i].equals(textRemains.get(j))){
                        textRemains.remove(j);
                        break;
                    }
                }
            }
            
            ArrayList<SVGText> textsWithinZones = new ArrayList<>();
            ArrayList<SVGText> textsOnBoundaries = new ArrayList<>();
            
            for (int i=0; i<textRemains.size(); i++){
                
                SVGText text = textRemains.get(i);
                if(sets.get(0).isOnBoundry(text.getX(), text.getY())
                        || sets.get(1).isOnBoundry(text.getX(), text.getY()))
                {
                    textsOnBoundaries.add(text);
                }else
                {
                    textsWithinZones.add(text);
                }
            }
            
            //Text Association building
            for (int i=1; i<vennZones.size(); i++){ //Exclude the first venn area 0.0.0
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 30 ) //10 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
            
            // TODO Fix
            for (int i=1; i<vennZones.size(); i++){ //Exclude the first venn area 0.0.0
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                if(vennZone.getVennValue()!=null)
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 200 ) //30 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
            
            // TODO Fix
            for (int i=0; i<vennZones.size(); i++){
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                if(vennZone.getVennValue()!=null)
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 1000 ) //30 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
                  
            // 
            for (int i=0; i<textsOnBoundaries.size(); i++){
                
                SVGText text = textsOnBoundaries.get(i);
                
                if(vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    vennSets.get(0).setValue(text.getText());
                }
                else if(!vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    vennSets.get(1).setValue(text.getText());
                }
                else if(vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    String zoneName = SetLabels[0].getText() + "." + SetLabels[1].getText();
                    VennZone vennZone = new VennZone(zoneName);
                    vennZone.setValue(text.getText());
                    vennZone.setValidity(true);
                    vennZones.add(vennZone);
                }
            }
            
            for (int i=0; i<vennSets.size(); i++){
                vennDiagram.addSet(vennSets.get(i));
            }
            
            for (int i=0; i<vennZones.size(); i++){
                vennDiagram.addZone(vennZones.get(i));
            }            
            
        } // End of set 2 implementation
        
        if(numOfSets==3){
            for (int i=0; i<2; i++){
                for (int j=0; j<2; j++){
                    for (int k=0; k<2; k++){
                        String name = i+"."+j+"."+k;
                        vennZones.add(new VennZone(name));
                    }
                }
            }
            
            
            //Find Set Labels
            int tolerence = 30;
            
            SVGText[] SetLabels = new SVGText[numOfSets];
            
            for (int i=0; i<numOfSets; i++){
                for (int j=0; j<nominalTexts.size(); j++){
                    
                    SVGText text = nominalTexts.get(j);
                    if(sets.get(i).isCloseToBoundry(text.getX(), text.getY(), tolerence))
                    {
                        boolean valid = true;
                        for (int k=0; k<numOfSets; k++){
                            if(k==i)
                                continue;
                            
                            if(sets.get(k).isCloseToBoundry(text.getX(), text.getY(), tolerence)){
                                valid = false;
                                break;
                            }
                        }
                        
                        if(valid){
                            SetLabels[i] = text;
                            break;
                        }
                    }
                }
            }
            
            for (int i=0; i<2; i++){
                for (int j=0; j<2; j++){
                    for (int k=0; k<2; k++){
                        String name="";
                        if(i==0)
                            name = name.concat("~"+SetLabels[0].getText());
                        else
                            name = name.concat(SetLabels[0].getText());
                        
                        if(j==0)
                            name = name.concat(".~"+SetLabels[1].getText());
                        else
                            name = name.concat("."+SetLabels[1].getText());
                        
                        
                        if(k==0)
                            name = name.concat(".~"+SetLabels[2].getText());
                        else
                            name = name.concat("."+SetLabels[2].getText());
                        
                        vennZones.get(i*4+j*2+k).setName(name);
                    }
                }
            }
            
            for (int i=0; i<numOfSets; i++){
                vennSets.add(new VennSet(SetLabels[i].getText(),"Ellipse",sets.get(i)));
            }
            
            //venn area centroid calculations
            for (int i=0; i<svgImage.getWidth(); i++){
                for (int j=0; j<svgImage.getHeight(); j++){
                    
                    if(!sets.get(0).isIn(i, j)){
                        if(!sets.get(1).isIn(i, j)){
                            if(!sets.get(2).isIn(i, j)){
                                vennZones.get(0).addPixel(i, j);
                            }else{
                                vennZones.get(1).addPixel(i, j);
                            }
                        }else{
                            if(!sets.get(2).isIn(i, j)){
                                vennZones.get(2).addPixel(i, j);
                            }else{
                                vennZones.get(3).addPixel(i, j);
                            }
                        }
                    }else{
                        if(!sets.get(1).isIn(i, j)){
                            if(!sets.get(2).isIn(i, j)){
                                vennZones.get(4).addPixel(i, j);
                            }else{
                                vennZones.get(5).addPixel(i, j);
                            }
                        }else{
                            if(!sets.get(2).isIn(i, j)){
                                vennZones.get(6).addPixel(i, j);
                            }else{
                                vennZones.get(7).addPixel(i, j);
                            }
                        }
                    }
                    
                }
            }
            
            //validate vennAreas
            // At least 100 pixels
            for (int i=0; i<numOfVennAreas; i++){
                vennZones.get(i).validate();
                
                //debug
                if(vennZones.get(i).isValid())
                    System.out.println("Name : "+vennZones.get(i).getName()+"\tpixels : "+vennZones.get(i).numOfPixels()
                            +"\tcentroid : ("+vennZones.get(i).getCentroid().getX()+","+vennZones.get(i).getCentroid().getY()+")");
            }
            
            //Filter text labels with values
            //Devide them to
            ArrayList<SVGText> textRemains = new ArrayList<>(texts);
        
            for (int i=0; i<numOfSets; i++){
                for (int j=0; j<textRemains.size(); j++){
                    if(SetLabels[i].equals(textRemains.get(j))){
                        textRemains.remove(j);
                        break;
                    }
                }
            }
            
            ArrayList<SVGText> textsWithinZones = new ArrayList<>();
            ArrayList<SVGText> textsOnBoundaries = new ArrayList<>();
            
            for (int i=0; i<textRemains.size(); i++){
                
                SVGText text = textRemains.get(i);
                if(sets.get(0).isOnBoundry(text.getX(), text.getY())
                        || sets.get(1).isOnBoundry(text.getX(), text.getY())
                        || sets.get(2).isOnBoundry(text.getX(), text.getY()))
                {
                    textsOnBoundaries.add(text);
                }else
                {
                    textsWithinZones.add(text);
                }
            }
            
            //Text Association building
            for (int i=1; i<vennZones.size(); i++){ //Exclude the first venn area 0.0.0
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 30 ) //10 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
            
            // TODO Fix
            for (int i=1; i<vennZones.size(); i++){ //Exclude the first venn area 0.0.0
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                if(vennZone.getVennValue()!=null)
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 200 ) //30 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
            
            // TODO Fix
            for (int i=0; i<vennZones.size(); i++){
                VennZone vennZone = vennZones.get(i);
                
                if(!vennZone.isValid())
                    continue;
                
                if(vennZone.getVennValue()!=null)
                    continue;
                
                for (int j=0; j<textsWithinZones.size(); j++){
                    
                    SVGText text = textsWithinZones.get(j);
                    if(Coordinate2D.getDistance(text.getX(), text.getY(),
                            vennZone.getCentroid().getX(), vennZone.getCentroid().getY()) < 1000 ) //30 pixels from centroid
                    {
                        vennZone.setValue(text.getText());
                        textsWithinZones.remove(j);
                        break;                    
                    }
                }
            }
                        
            for (int i=0; i<textsOnBoundaries.size(); i++){
                
                SVGText text = textsOnBoundaries.get(i);
                
                if(vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    vennSets.get(0).setValue(text.getText());
                }
                else if(!vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    vennSets.get(1).setValue(text.getText());
                }
                else if(!vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    vennSets.get(2).setValue(text.getText());
                }
                else if(!vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    VennZone vennZone = new VennZone("B.C");
                    vennZone.setValue(text.getText());
                    vennZone.setValidity(true);
                    vennZones.add(vennZone);
                }
                else if(vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    VennZone vennZone = new VennZone("A.C");
                    vennZone.setValue(text.getText());
                    vennZone.setValidity(true);
                    vennZones.add(vennZone);
                }
                else if(vennSets.get(0).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && vennSets.get(1).getSVGObject().isOnBoundry(text.getX(), text.getY())
                        && !vennSets.get(2).getSVGObject().isOnBoundry(text.getX(), text.getY()))
                {
                    VennZone vennZone = new VennZone("A.B");
                    vennZone.setValue(text.getText());
                    vennZone.setValidity(true);
                    vennZones.add(vennZone);
                }
                
                
                
            }
            
            for (int i=0; i<vennSets.size(); i++){
                vennDiagram.addSet(vennSets.get(i));
            }
            
            for (int i=0; i<vennZones.size(); i++){
                vennDiagram.addZone(vennZones.get(i));
            }
            
        }       // implementation for 3 sets
        
        
        /*********** Find Set Labels ***********/
        
        
        //VennSet vennSet = new VennSet()
    }
}
