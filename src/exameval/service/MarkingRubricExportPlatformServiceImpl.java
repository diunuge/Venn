package exameval.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

public class MarkingRubricExportPlatformServiceImpl implements MarkingRubricExportPlatformService{

	@Override
	public void produceXMLfromJSON(String jsonStr, String filePath) {
		
		FileHandlingPlatformService fileHandlingPlatformService = new FileHandlingPlatformServiceImpl();
		//String jsonStr = fileHandlingPlatformService.fileToString("res/rubricjson.txt");
		String rubricXML = "";
		
		JSONObject jsonObject = new JSONObject(jsonStr);

		rubricXML += "<?xml version=\"1.0\"?>\n";
		rubricXML += "<rubric>\n";
		
		JSONObject jsonObjectRubric = jsonObject.getJSONObject("rubric");
		rubricXML += "\t<type>" + jsonObjectRubric.getString("type") + "</type>\n";
		rubricXML += "\t<mandatory></mandatory>\n";
		
		JSONObject jsonObjectQuestion = jsonObjectRubric.getJSONObject("question");
		rubricXML += "\t<question id=\""+jsonObjectQuestion.getString("id")+"\" totalMarks=\""+jsonObjectQuestion.getInt("totalMarks")+"\">\n";
		
		JSONArray jsonArraySubQuestions = jsonObjectQuestion.getJSONArray("subQuestion");
		
		for(int i=0; i<jsonArraySubQuestions.length(); i++){
			
			JSONObject jObjSubQ = jsonArraySubQuestions.getJSONObject(i);
			if(jObjSubQ==null)
				continue;
			else{
				rubricXML += "\t\t<subQuestion id=\""+jObjSubQ.getInt("id")+"\" modelDiagram=\""+jObjSubQ.getString("modelDiagram")+"\" totalMarks=\""+jObjSubQ.getInt("totalMarks")+"\">\n";
				
				JSONArray jsonArrayMarkSets = jObjSubQ.getJSONArray("markSet");
				
				for(int j=0; j<jsonArrayMarkSets.length();j++){
					JSONObject jObjMarkSet = jsonArrayMarkSets.getJSONObject(j);
					if(jObjMarkSet==null)
						continue;
					else{
						rubricXML += "\t\t\t<markSet id=\""+jObjMarkSet.getInt("id")+"\" totalMarks=\""+jObjMarkSet.getInt("totalMarks")+"\">\n";
						
						JSONArray jsonArrayData = jObjMarkSet.getJSONArray("data");
						for(int k=0; k<jsonArrayData.length(); k++){
							
							JSONObject jObjData = jsonArrayData.getJSONObject(k);
							
							rubricXML += "\t\t\t\t<data id=\""+jObjData.getInt("id")+"\" marks=\""+jObjData.getInt("marks")+"\" method=\""+jObjData.getString("method")+"\">\n";
							
							JSONArray jsonArrayZones = jObjData.getJSONArray("zone");
							for(int l=0; l<jsonArrayZones.length(); l++){
								
								JSONObject jObjZone = jsonArrayZones.getJSONObject(l);
								if(jObjZone==null)
									continue;
								else{
									rubricXML += "\t\t\t\t\t<zone><label>"+jObjZone.getString("label")+"</label><value>"+jObjZone.getString("value")+"</value><color>"+jObjZone.getString("color")+"</color></zone>\n";
								}
							}
								
							
							JSONArray jsonArraySets = jObjData.getJSONArray("set");
							for(int l=0; l<jsonArraySets.length(); l++){
								
								JSONObject jObjSet = jsonArraySets.getJSONObject(l);
								if(jObjSet==null)
									continue;
								else{
									rubricXML += "\t\t\t\t\t<set>"+jObjSet.getString("label")+"</set>\n";
								}
							}
							
							rubricXML += "\t\t\t\t</data>\n";
						}
						
						rubricXML += "\t\t\t</markSet>\n";
					}
				}

				JSONObject jObjFeedback = jObjSubQ.getJSONObject("feedback");
				rubricXML += "\t\t\t<feedback>\n";
				rubricXML += "\t\t\t\t<success>"+jObjFeedback.getString("success")+"</success>\n";
				rubricXML += "\t\t\t\t<fail>"+jObjFeedback.getString("fail")+"</fail>\n";
				rubricXML += "\t\t\t</feedback>\n";
				
				rubricXML += "\t\t</subQuestion>\n";
			}
		}

		rubricXML += "\t</question>\n";
		rubricXML += "</rubric>\n";
		
		System.out.println(rubricXML);		
		
		fileHandlingPlatformService.stringToFile(rubricXML, filePath);
		
	}

}
