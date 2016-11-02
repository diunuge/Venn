package exameval.service;

import java.io.IOException;

public interface FileHandlingPlatformService {
	
	public String fileToString(String filePath);
	
	public void stringToFile(String str, String filePath);
	
	public String xmlToJson(String xmlStr, int indentFactor);
	
	public String xmlToJson(String xmlStr);
	
	public String jsonToXML(String jsonStr);
}
