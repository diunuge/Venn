package exameval.algorithm.set;

import java.util.ArrayList;

public class SetNameCheck {
	
	public static boolean isSameSetName(String setName, ArrayList<String> setLabelsModel, ArrayList<String> setLabelsAnswerArranged){
		
		boolean check = true;
		
		for(int i=0; i<setLabelsModel.size(); i++){
			
			if(setName.equals(setLabelsModel.get(i))){
				if(setLabelsAnswerArranged.get(i)=="null" && setLabelsAnswerArranged.get(i)=="Unlabeled"){
					check = false;
					break;
				}
			}
			
			if(i==setLabelsModel.size()-1){
				//Found no match in model set labels
				check = false;
				break;
			}
		}
		
		return check;
	}
	
	public static boolean isSameZone(String zoneLabelModel, String zoneLabelAnswer, ArrayList<String> setLabelsModel, ArrayList<String> setLabelAnswerArranged){
		
		boolean check = true;
		
//		String test1 = "B.~A.C";
//		String test2 = "~Cat.Dog.Mouse";
//		
//		zoneLabel1 = test1;
//		zoneLabel2 = test2;
//		
//		ArrayList<String> testLabels1 = new ArrayList<>();
//		testLabels1.add("A");
//		testLabels1.add("B");
//		testLabels1.add("C");
//		setLabels1 = testLabels1;
//		
//		ArrayList<String> testLabels2 = new ArrayList<>();
//		testLabels2.add("Cat");
//		testLabels2.add("Dog");
//		testLabels2.add("Mouse");
//		setLabels2 = testLabels2;
		
		String[] labelPartsModel = zoneLabelModel.split("\\.");
		for(int i=0; i<setLabelsModel.size(); i++){
			
			for(int j=0; j<setLabelsModel.size(); j++){
				if (labelPartsModel[i].equals(setLabelsModel.get(j))){
					labelPartsModel[i] = setLabelAnswerArranged.get(j);
					continue;
				}
				if (labelPartsModel[i].equals("~"+setLabelsModel.get(j))){
					labelPartsModel[i] = "~"+setLabelAnswerArranged.get(j);
					continue;
				}
			}
		}

		String[] labelPartsAnswer = zoneLabelAnswer.split("\\.");
		
		for(int i=0; i<setLabelsModel.size(); i++){
			
			for(int j=0; j<setLabelsModel.size(); j++){
				
				if(labelPartsModel[i].equals(labelPartsAnswer[j])){
					break;
				}
				
				if(j == setLabelsModel.size()-1){
					check = false;
				}
			}
		}
		
//		if(check){
//			check = true;
//			System.out.println("Match");
//		}
//		else{
//			System.out.println("Don't Match");
//		}
		
		return check;
	}
}
