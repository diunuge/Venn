package exameval.service;

public interface MarkingRubricExportPlatformService {
	
	public void produceXMLfromJSON(String jsonStr, String filePath);
}
