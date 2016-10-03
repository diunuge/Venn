/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exameval.service;

import exameval.domain.svg.SVGImage;
import exameval.domain.venn.VennDiagram;

/**
 *
 * @author Diunuge
 */
public interface SVG2VennTranslatePlatformService {
    public void translate(VennDiagram vennDiagram, SVGImage svgImage);
}
