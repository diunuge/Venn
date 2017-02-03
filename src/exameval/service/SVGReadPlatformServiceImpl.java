package exameval.service;

import exameval.ExamEval;
import exameval.domain.svg.SVGEllipse;
import exameval.domain.svg.SVGImage;
import exameval.domain.svg.SVGLine;
import exameval.domain.svg.SVGPath;
import exameval.domain.svg.SVGRectangle;
import exameval.domain.svg.SVGText;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Diunuge
 */
public class SVGReadPlatformServiceImpl implements SVGReadPlatformService{

    @Override
    public void parse(SVGImage svgImage, String svgfileIn){
                
        String svgFile = null;
		try {
			svgFile = readSVGFile(svgfileIn);
		} catch (IOException e) {
			Logger.getLogger(ExamEval.class.getName()).log(Level.SEVERE, null, e);
		}
        
        InputSource source = new InputSource(new StringReader(svgFile));

        XPath xPath = XPathFactory.newInstance().newXPath();

        NodeList list;
        try {
            
            list = (NodeList)xPath.evaluate("//line", source, XPathConstants.NODESET);
            
            List<Element> lines = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
            	lines.add((Element)list.item(i));
            }
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//ellipse", source, XPathConstants.NODESET);
            
            List<Element> ellipses = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                ellipses.add((Element)list.item(i));
            }
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//rect", source, XPathConstants.NODESET);
            
            List<Element> rectangles = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                rectangles.add((Element)list.item(i));
            }
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//text", source, XPathConstants.NODESET);
            
            List<Element> texts = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
                texts.add((Element)list.item(i));
            }
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("//path", source, XPathConstants.NODESET);
            
            List<Element> paths = new ArrayList<>(list.getLength());
            for (int i = 0; i < list.getLength(); i++)
            {
            	paths.add((Element)list.item(i));
            }
            
            source = new InputSource(new StringReader(svgFile));
            list = (NodeList)xPath.evaluate("/svg", source, XPathConstants.NODESET);
            Element svg = (Element)list.item(0);
            svgImage.setSize(
                    Integer.parseInt(svg.getAttribute("height")),
                    Integer.parseInt(svg.getAttribute("width")));
            
            
            //System.out.println("No of Rectangles: " + rectangles.size());
            //System.out.println("No of Ellipses: " + ellipses.size());
            //System.out.println("No of Texts: " + texts.size());
            
            //svgImage = new SVGImage();
            for (int i = 0; i < lines.size(); i++)
            {
                Element lineElement = lines.get(i);
                //System.out.println(rectangles.get(i).getAttribute("width"));
                //System.out.println(rectangles.get(i).getTagName());
                
                SVGLine line = new SVGLine(Double.parseDouble(lineElement.getAttribute("x1")),
                        Double.parseDouble(lineElement.getAttribute("y1")),
                        Double.parseDouble(lineElement.getAttribute("x2")),
                        Double.parseDouble(lineElement.getAttribute("y2")));
                
                svgImage.addLine(line);
            }
            
            
            for (int i = 0; i < rectangles.size(); i++)
            {
                Element rectangleElement = rectangles.get(i);
                //System.out.println(rectangles.get(i).getAttribute("width"));
                //System.out.println(rectangles.get(i).getTagName());
                
                SVGRectangle rectangle = new SVGRectangle(Double.parseDouble(rectangleElement.getAttribute("x")),
                        Double.parseDouble(rectangleElement.getAttribute("y")),
                        Double.parseDouble(rectangleElement.getAttribute("height")),
                        Double.parseDouble(rectangleElement.getAttribute("width")));
                
                svgImage.addRectangle(rectangle);
            }
            
            
            for (int i = 0; i < ellipses.size(); i++)
            {
                Element ellipseElement = ellipses.get(i);
                //System.out.println(rectangles.get(i).getAttribute("width"));
                //System.out.println(rectangles.get(i).getTagName());
                
                SVGEllipse ellipse = new SVGEllipse(Double.parseDouble(ellipseElement.getAttribute("cx")),
                        Double.parseDouble(ellipseElement.getAttribute("cy")),
                        Double.parseDouble(ellipseElement.getAttribute("rx")),
                        Double.parseDouble(ellipseElement.getAttribute("ry")));
                
                svgImage.addEllipse(ellipse);
            }
            
            for (int i = 0; i < texts.size(); i++)
            {
                Element textElement = texts.get(i);
                //System.out.println(rectangles.get(i).getAttribute("width"));
                //System.out.println(texts.get(i).getTextContent());
                
                SVGText text = new SVGText(Double.parseDouble(textElement.getAttribute("x")),
                        Double.parseDouble(textElement.getAttribute("y")),
                        textElement.getTextContent(),
                        Integer.parseInt(textElement.getAttribute("font-size")));
                
                svgImage.addText(text);
            }
            
            for (int i = 0; i < paths.size(); i++)
            {
                Element pathElement = paths.get(i);
                //System.out.println(rectangles.get(i).getAttribute("width"));
                //System.out.println(texts.get(i).getTextContent());
                
                SVGPath path = new SVGPath(pathElement.getAttribute("d"),
                		pathElement.getAttribute("fill"));
                
                svgImage.addPath(path);
            }
            
        } catch (XPathExpressionException ex) {
            Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void printSVG(String svgfileIn){
        
        String svgFile;
        try {
            svgFile = readSVGFile(svgfileIn);
            System.out.println(svgFile);
        } catch (IOException ex) {
            
            Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String readSVGFile (String svgfileIn) throws IOException {
        
        String svgFile=null;
        
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(svgfileIn));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            int lineIndex=0;
            while (line != null) {
            	
            	if(lineIndex<1 || lineIndex>8){
            		sb.append(line);
            		sb.append(System.lineSeparator());
            	}
                line = br.readLine();
                lineIndex++;
            }
            svgFile = sb.toString();
            
            svgFile = svgFile.replace(" xmlns=\"http://www.w3.org/2000/svg\"", "");
            //svgFile = svgFile.replaceAll("(?s)(?<=<g>\n)(.*?)(?=\n</g>)", "REPLACE");
            
            //String str = "sfd\nsdfsdf<g>\nthis it to be\n replaced\n</g>sdfsdf";
            //System.out.println(str.replaceAll("(?s)(?<=<g>\n)(.*?)(?=\n</g>)", "replacement"));
            
            System.out.println(svgFile);
        } 
        catch(java.io.IOException ex){
        	Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
//            try {
//                br.close();
//            } catch (IOException ex) {
//                Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
           
        return svgFile;
    }
}
