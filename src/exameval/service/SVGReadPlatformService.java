package exameval.service;

import exameval.domain.svg.SVGImage;

/**
 *
 * @author Diunuge
 */
public interface SVGReadPlatformService {
    
    public void parse(SVGImage svgImage, String svgfileIn);
    
    public void printSVG(String svgFileIn);
}
