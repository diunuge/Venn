package exameval.algorithm.nlp;

public class StringCheck {
	
	public static double getSimilarity(String str1, String str2){
		double totalSimilarity = 0;
		double similarity = 0.0;
		
		String[] tokens1 = str1.trim().split("\\s+|-|\\.");
		String[] tokens2 = str2.trim().split("\\s+|-|\\.");
		
		int n=0, m=0;
		for(String s1: tokens1){
			
			for(String s2: tokens2){
				
				int maxLength = Math.max(s1.length(), s2.length());
				int distance = LevenshteinDistance.getLevenshteinDistance(s1,s2);
				similarity = (maxLength - distance)/(double)maxLength;
				totalSimilarity+=similarity;
				if(similarity > 0.5)
					n++;
				m++;
			}
		}
		if(n==0)
			totalSimilarity/=m;
		else
			totalSimilarity/=n;
		
		return totalSimilarity;
	}
}
