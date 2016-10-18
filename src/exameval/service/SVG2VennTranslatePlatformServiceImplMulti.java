/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.coordinate.Coordinate2D;
import exameval.domain.svg.SVGEllipse;
import exameval.domain.svg.SVGImage;
import exameval.domain.svg.SVGLine;
import exameval.domain.svg.SVGRectangle;
import exameval.domain.svg.SVGText;
import exameval.domain.venn.VennZone;
import exameval.domain.venn.VennDiagram;
import exameval.domain.venn.VennSet;
import java.util.ArrayList;

/**
 *
 * @author Diunuge
 */
public class SVG2VennTranslatePlatformServiceImplMulti implements SVG2VennTranslatePlatformService {

    @Override
    public void translate(VennDiagram vennDiagram, SVGImage svgImage) {
        
        int numOfSets = svgImage.getNumOfEllipses();
        int numOfVennAreas = (int)Math.pow(2, numOfSets);
        
        //only considering Ellipses as sets
        ArrayList<SVGRectangle> universal_set = svgImage.getRectangles();
        ArrayList<SVGLine> arrows = svgImage.getLines();
        ArrayList<SVGEllipse> sets = svgImage.getEllipses();
        ArrayList<SVGText> texts = svgImage.getTexts();
        
        ArrayList<SVGText> nominalTexts = new ArrayList<>();
        ArrayList<SVGText> possibleArrowLabels = new ArrayList<>();
        
        for (int i = 0; i < texts.size(); i++)
        {
            if(!texts.get(i).isNumeric()){
                nominalTexts.add(texts.get(i));
                
                if(!universal_set.get(0).isIn(texts.get(i).getX(), texts.get(i).getY())){
                	possibleArrowLabels.add(texts.get(i));
                }
            }
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
            int tolerence = 20;
            SVGText[] SetLabels = new SVGText[numOfSets];
            ArrayList<SVGText> SetLabelsAssigned = new ArrayList<SVGText>();
            
            SVGLine [] associationArrows = new SVGLine [numOfSets];
            
            if(arrows.size()>0){
            	//Arrow labeled
            	for (int i=0; i<numOfSets; i++){
            		for (int j=0; j<arrows.size(); j++){
            			
            			SVGLine arrow = arrows.get(j);
                        
                        //System.out.println("Distance from "+text.getText()+" to Ellipse "+ i + "is :"+ sets.get(i).getDistance(text.getX(), text.getY()));
                        if(sets.get(i).isCloseToBoundry(arrow.getX1(), arrow.getY1(), tolerence)
                        		||sets.get(i).isCloseToBoundry(arrow.getX2(), arrow.getY2(), tolerence))
                        {
                        	associationArrows[i] = arrow;
                        }
            		}
            	}
            }
            
            int arrowTolerence = 50;
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]!=null)
            		continue;
            	
            	if(associationArrows[i]!=null){
            		//There is a associated arrow
            		if(sets.get(i).isCloseToBoundry(associationArrows[i].getX1(), associationArrows[i].getY1(), tolerence)){
            			//Find text near x2, y2
            			for (int j=0; j<possibleArrowLabels.size(); j++){
            				
            				SVGText text = possibleArrowLabels.get(j);
            				
            				if(associationArrows[i].isCloseToEnd(
            						text.getX(), 
            						text.getY(), 
            						arrowTolerence, 
            						1)){
            					SetLabels[i] = text;
                                SetLabelsAssigned.add(text);
            				}
            			}
            		}
            		else{
            			//Find text near x1, y1
            			for (int j=0; j<possibleArrowLabels.size(); j++){
            				
            				SVGText text = possibleArrowLabels.get(j);
            				
            				if(associationArrows[i].isCloseToEnd(
            						text.getX(), 
            						text.getY(), 
            						tolerence, 
            						0)){
            					SetLabels[i] = text;
                                SetLabelsAssigned.add(text);
            				}
            			}
            		}
            	}
            }

            arrowTolerence = 100;
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]!=null)
            		continue;
            	
            	if(associationArrows[i]!=null){
            		//There is a associated arrow
            		if(sets.get(i).isCloseToBoundry(associationArrows[i].getX1(), associationArrows[i].getY1(), tolerence)){
            			//Find text near x2, y2
            			for (int j=0; j<possibleArrowLabels.size(); j++){
            				
            				SVGText text = possibleArrowLabels.get(j);
            				
            				if(associationArrows[i].isCloseToEnd(
            						text.getX(), 
            						text.getY(), 
            						arrowTolerence, 
            						1)){
            					SetLabels[i] = text;
                                SetLabelsAssigned.add(text);
            				}
            			}
            		}
            		else{
            			//Find text near x1, y1
            			for (int j=0; j<possibleArrowLabels.size(); j++){
            				
            				SVGText text = possibleArrowLabels.get(j);
            				
            				if(associationArrows[i].isCloseToEnd(
            						text.getX(), 
            						text.getY(), 
            						tolerence, 
            						0)){
            					SetLabels[i] = text;
                                SetLabelsAssigned.add(text);
            				}
            			}
            		}
            	}
            }
            
            tolerence = 10;
            
            for (int i=0; i<numOfSets; i++){

            	if(SetLabels[i]!=null){
            		continue;
            	}
            	
                for (int j=0; j<nominalTexts.size(); j++){
                    
                    SVGText text = nominalTexts.get(j);
                    
                    //System.out.println("Distance from "+text.getText()+" to Ellipse "+ i + "is :"+ sets.get(i).getDistance(text.getX(), text.getY()));
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
                            SetLabelsAssigned.add(text);
                            break;
                        }
                    }
                }
            }
            

            ArrayList<SVGText> nominalTextsRemained = new ArrayList<SVGText>(nominalTexts);
            if(SetLabelsAssigned.size()<numOfSets){
            	for (int i=0; i<SetLabelsAssigned.size(); i++){
            		nominalTextsRemained.remove(SetLabelsAssigned.get(i));
            	}
            }
            
            //Increase the tolerence & search again
            tolerence = 15;
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]!=null){
            		continue;
            	}
            	
                for (int j=0; j<nominalTextsRemained.size(); j++){
                    
                    SVGText text = nominalTextsRemained.get(j);
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
                            SetLabelsAssigned.add(text);
                            break;
                        }
                    }
                }
            }
            
            //Assign generated label
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]==null){
            		SetLabels[i] = new SVGText(0, 0, "Unlabeled_"+i, 0);
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
                    System.out.println("Name : "+vennZones.get(i).getIdentifire()+"\tpixels : "+vennZones.get(i).numOfPixels()
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
            
            for (int j=0; j<textRemains.size(); j++){
                if(!universal_set.get(0).isIn(textRemains.get(j).getX(), textRemains.get(j).getY())){
                    textRemains.remove(j);
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
            int[][] textAssociationWithSets = new int[2][textsWithinZones.size()];
            
            for (int i=0; i<textsWithinZones.size(); i++){
            	SVGText text = textsWithinZones.get(i);
            	textAssociationWithSets[0][i] = sets.get(0).isIn(text.getX(),text.getY())?1:0;
            	textAssociationWithSets[1][i] = sets.get(1).isIn(text.getX(),text.getY())?1:0;
            }
            
            for (int i=0; i<textsWithinZones.size(); i++){
            	
            	VennZone vennZone = vennZones.get(2*textAssociationWithSets[0][i]+textAssociationWithSets[1][i]);
            	vennZone.setValue(textsWithinZones.get(i).getText());
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
        
        //========================= Set 3 implementation ======================
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
            int tolerence = 10;
            
            SVGText[] SetLabels = new SVGText[numOfSets];
            ArrayList<SVGText> SetLabelsAssigned = new ArrayList<SVGText>();
            
            for (int i=0; i<numOfSets; i++){
                for (int j=0; j<nominalTexts.size(); j++){
                    
                    SVGText text = nominalTexts.get(j);
                    
                    //System.out.println("Distance from "+text.getText()+" to Ellipse "+ i + "is :"+ sets.get(i).getDistance(text.getX(), text.getY()));
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
                            SetLabelsAssigned.add(text);
                            break;
                        }
                    }
                }
            }
            

            ArrayList<SVGText> nominalTextsRemained = new ArrayList<SVGText>(nominalTexts);
            if(SetLabelsAssigned.size()<numOfSets){
            	for (int i=0; i<SetLabelsAssigned.size(); i++){
            		nominalTextsRemained.remove(SetLabelsAssigned.get(i));
            	}
            }
            
            //Increase the tolerence & search again
            tolerence = 15;
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]!=null){
            		continue;
            	}
            	
                for (int j=0; j<nominalTextsRemained.size(); j++){
                    
                    SVGText text = nominalTextsRemained.get(j);
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
                            SetLabelsAssigned.add(text);
                            break;
                        }
                    }
                }
            }
            
            //Assign generated label
            for (int i=0; i<numOfSets; i++){
            	
            	if(SetLabels[i]==null){
            		SetLabels[i] = new SVGText(0, 0, "Unlabeled_"+i, 0);
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
                    System.out.println("Name : "+vennZones.get(i).getIdentifire()+"\tpixels : "+vennZones.get(i).numOfPixels()
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
            int[][] textAssociationWithSets = new int[3][textsWithinZones.size()];
            
            for (int i=0; i<textsWithinZones.size(); i++){
            	SVGText text = textsWithinZones.get(i);
            	textAssociationWithSets[0][i] = sets.get(0).isIn(text.getX(),text.getY())?1:0;
            	textAssociationWithSets[1][i] = sets.get(1).isIn(text.getX(),text.getY())?1:0;
            	textAssociationWithSets[2][i] = sets.get(2).isIn(text.getX(),text.getY())?1:0;
            }
            
            for (int i=0; i<textsWithinZones.size(); i++){
            	
            	VennZone vennZone = vennZones.get(4*textAssociationWithSets[0][i]+2*textAssociationWithSets[1][i]+textAssociationWithSets[2][i]);
            	vennZone.setValue(textsWithinZones.get(i).getText());
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
