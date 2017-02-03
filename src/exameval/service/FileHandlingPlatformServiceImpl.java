package exameval.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.json.XML;

public class FileHandlingPlatformServiceImpl implements FileHandlingPlatformService {

	@Override
	public String fileToString(String filePath){
		
		String fileStr=null;
        
        BufferedReader br=null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            fileStr = sb.toString();
        } 
        catch(java.io.IOException ex){
        	Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(SVGReadPlatformServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
           
        return fileStr;
	}

	@Override
	public String xmlToJson(String xmlStr) {
		
		JSONObject jsonObject = XML.toJSONObject(xmlStr);
		return jsonObject.toString();
	}

	@Override
	public String xmlToJson(String xmlStr, int indentFactor) {
		
		JSONObject jsonObject = XML.toJSONObject(xmlStr);
		return jsonObject.toString(indentFactor);
	}

	@Override
	public String jsonToXML(String jsonStr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void stringToFile(String content, String filePath) {

		try {

			File file = new File(filePath);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
