package exameval.algorithm.nlp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;

public class LSAImplementation {

	private NLP nlpObject;
	private ArrayList<String> inputs;
	private ArrayList<String> modelAnswers;
	private ArrayList<String> allStringList;
	private String testSubject;
	private List<String> bagOfWords;
	private double[][] matrix;
	private double[][] similarityMatrix;
	private double[][] tf_idf_matrix;
	private int toleranceValue = 1;

	public LSAImplementation() {

	}

	public double[][] getTFIDFSimilarity() {

		return tfIdfCalculator();

	}

	/**
	 * @return the inputs
	 */
	public ArrayList<String> getInputs() {
		return inputs;
	}

	/**
	 * @param inputs
	 *            the inputs to set
	 */
	public void setInputs(ArrayList<String> inputs) {
		this.inputs = inputs;
	}

	/**
	 * @return the modelAnswers
	 */
	public ArrayList<String> getModelAnswers() {
		return modelAnswers;
	}

	/**
	 * @param modelAnswers
	 *            the modelAnswers to set
	 */
	public void setModelAnswers(ArrayList<String> modelAnswers) {
		this.modelAnswers = modelAnswers;
	}

	/**
	 * @return the allStringList
	 */
	public ArrayList<String> getAllStringList() {
		return allStringList;
	}

	/**
	 * @param allStringList
	 *            the allStringList to set
	 */
	public void setAllStringList(ArrayList<String> allStringList) {
		this.allStringList = allStringList;
	}

	/**
	 * @return the testSubject
	 */
	public String getTestSubject() {
		return testSubject;
	}

	/**
	 * @param testSubject
	 *            the testSubject to set
	 */
	public void setTestSubject(String testSubject) {
		this.testSubject = testSubject;
	}

	/**
	 * @return the bagOfWords
	 */
	public List<String> getBagOfWords() {
		return bagOfWords;
	}

	/**
	 * @param bagOfWords
	 *            the bagOfWords to set
	 */
	public void setBagOfWords(List<String> bagOfWords) {
		this.bagOfWords = bagOfWords;
	}

	/**
	 * @return the matrix
	 */
	public double[][] getMatrix() {
		return matrix;
	}

	/**
	 * @param matrix
	 *            the matrix to set
	 */
	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}

	/**
	 * @return the tf_idf_matrix
	 */
	public double[][] getTf_idf_matrix() {
		return tf_idf_matrix;
	}

	/**
	 * @param tf_idf_matrix
	 *            the tf_idf_matrix to set
	 */
	public void setTf_idf_matrix(double[][] tf_idf_matrix) {
		this.tf_idf_matrix = tf_idf_matrix;
	}

	/**
	 * @return the nlpObject
	 */
	public NLP getNlpObject() {
		return nlpObject;
	}

	/**
	 * @param nlpObject
	 *            the nlpObject to set
	 */
	public void setNlpObject(NLP nlpObject) {
		this.nlpObject = nlpObject;
	}

	/**
	 * @return the similarityMatrix
	 */
	public double[][] getSimilarityMatrix() {
		return similarityMatrix;
	}

	/**
	 * @param similarityMatrix
	 *            the similarityMatrix to set
	 */
	public void setSimilarityMatrix(double[][] similarityMatrix) {
		this.similarityMatrix = similarityMatrix;
	}

	/**
	 * @return the toleranceValue
	 */
	public int getToleranceValue() {
		return toleranceValue;
	}

	/**
	 * @param toleranceValue
	 *            the toleranceValue to set
	 */
	public void setToleranceValue(int toleranceValue) {
		this.toleranceValue = toleranceValue;
	}

	public boolean setBagOfWords(ArrayList<String> inputs) {

		if (inputs != null && inputs.size() > 0) {

			this.inputs = inputs;

			ArrayList<String> tempBagOfWords = new ArrayList<String>();
			ArrayList<Integer> tempcount = new ArrayList<Integer>();

			nlpObject = new NLP();

			bagOfWords = new ArrayList<String>();

			ArrayList<String[]> wordTokenize = wordTokenize(inputs);

			for (int i = 0; i < wordTokenize.size(); i++) {

				for (int j = 0; j < wordTokenize.get(i).length; j++) {

					String word = wordTokenize.get(i)[j];

					if (!tempBagOfWords.contains(word)) {

						tempBagOfWords.add(word);

						tempcount.add(1);

					} else {

						int indexOf = tempBagOfWords.indexOf(word);
						Integer integer = tempcount.get(indexOf);

						tempcount.set(indexOf, integer + 1);

					}

				}

			}

			for (int k = 0; k < tempBagOfWords.size(); k++) {

				if (tempcount.get(k) > toleranceValue) {

					bagOfWords.add(tempBagOfWords.get(k));
				}

			}

			bagOfWords.remove(" ");
			bagOfWords.remove("");

			nlpObject.setBagOfWords(bagOfWords);
			return true;

		} else {

			return false;
		}

	}

	public double[][] getSimilarity(String testSubject) {

		this.testSubject = testSubject;
		nlpObject.setTestingString(testSubject);

		nlpObject.setMatrix(createLSAMatrix());

		getCosineSimilarityResult(nlpObject.getMatrix());

		nlpObject.setSimilarityMatrix(similarityMatrix);

		return nlpObject.getSimilarityMatrix();

	}

	public boolean getMostSimilarityValue(double[][] matrix) {

		int maxIndex = 0;
		double maxValue = 0;

		if (matrix != null) {

			for (int i = 1; i < matrix[0].length; i++) {

				if (matrix[0][i] > maxValue) {

					maxValue = matrix[0][i];
					maxIndex = i;
				}
			}

			nlpObject.setMaxSimValue(maxValue);
			if(maxIndex!=0)
			nlpObject.setSelectedModel(modelAnswers.get(maxIndex - 1));
			return true;
		} else {

			return false;
		}

	}

	public double[][] createLSAMatrix() {

		if (!testSubject.equals("") && bagOfWords != null && modelAnswers != null) {
			int count = 0;

			allStringList = new ArrayList<String>();

			allStringList.add(testSubject);

			for (String test : modelAnswers) {

				allStringList.add(test);
			}

			matrix = new double[bagOfWords.size()][allStringList.size()];

			for (int i = 0; i < bagOfWords.size(); i++) {

				for (int j = 0; j < allStringList.size(); j++) {

					count = 0;

					String[] test = wordTokenize(allStringList.get(j));

					for (int k = 0; k < test.length; k++) {

						if (test[k].equals(bagOfWords.get(i))) {

							count++;
						}
					}

					matrix[i][j] = count;

				}

			}

		}
		return matrix;

	}

	public double cosineSimilarity(double[] docVector1, double[] docVector2) {
		double dotProduct = 0.0;
		double magnitude1 = 0.0;
		double magnitude2 = 0.0;
		double cosineSimilarity = 0.0;

		for (int i = 0; i < docVector1.length; i++) // docVector1 and docVector2
													// must be of same length
		{
			dotProduct += docVector1[i] * docVector2[i]; // a.b
			magnitude1 += Math.pow(docVector1[i], 2); // (a^2)
			magnitude2 += Math.pow(docVector2[i], 2); // (b^2)
		}

		magnitude1 = Math.sqrt(magnitude1);// sqrt(a^2)
		magnitude2 = Math.sqrt(magnitude2);// sqrt(b^2)

		if ((magnitude1 != 0.0) && (magnitude2 != 0.0)) {
			cosineSimilarity = dotProduct / (magnitude1 * magnitude2);
		} else {
			return 0.0;
		}
		return cosineSimilarity;
	}

	private double tfCalculator(String[] totalterms, String termToCheck) {
		double count = 0; // to count the overall occurrence of the term
							// termToCheck
		for (String s : totalterms) {
			if (s.equalsIgnoreCase(termToCheck)) {
				count++;
			}
		}
		return count / totalterms.length;
	}

	private double idfCalculator(List<String[]> allTerms, String termToCheck) {
		double count = 0;
		for (String[] ss : allTerms) {
			for (String s : ss) {
				if (s.equalsIgnoreCase(termToCheck)) {
					count++;
					break;
				}
			}
		}

		if (count != 0) {
			return Math.log(allTerms.size() / count);

		} else {

			return 0;
		}

	}

	public double[][] tfIdfCalculator() {

		double tf; // term frequency
		double idf; // inverse document frequency
		double tfidf; // term frequency inverse document frequency

		if (testSubject != null && bagOfWords != null && modelAnswers != null) {

			tf_idf_matrix = new double[bagOfWords.size()][allStringList.size()];

			for (int i = 0; i < bagOfWords.size(); i++) {

				for (int j = 0; j < allStringList.size(); j++) {

					tf = tfCalculator(wordTokenize(allStringList.get(j)), bagOfWords.get(i));
					idf = idfCalculator(wordTokenize(allStringList), bagOfWords.get(i));
					tfidf = tf * idf;

					tf_idf_matrix[i][j] = tfidf;

				}

			}

		}

		nlpObject.setTf_idf_matrix(tf_idf_matrix);
		return tf_idf_matrix;

	}

	public ArrayList<String[]> wordTokenize(ArrayList<String> sentences) {

		ArrayList<String[]> tokenizedTerms = new ArrayList<String[]>();

		for (int i = 0; i < sentences.size(); i++) {

			String[] tempTerms = wordTokenize(sentences.get(i)); // to
			tokenizedTerms.add(tempTerms);

		}

		return tokenizedTerms;
	}
	
	

	public String[] wordTokenize(String sentences) {

		String[] tempTerms = sentences.toString().split(" "); // to

		return tempTerms;
	}

	public void printTF_IDF_Result(double[][] matrix) {

		NumberFormat formatter = new DecimalFormat("#0.00");

		String text = "	";
		System.out.println("----------Similarity Matrix Printing----------");
		if (allStringList != null && bagOfWords != null) {

			for (int k = 0; k < allStringList.size(); k++) {

				text = text + "S-" + (k + 1) + "	";

			}
			System.out.println(text);

			for (int i = 0; i < bagOfWords.size(); i++) {
				text = bagOfWords.get(i);
				for (int j = 0; j < allStringList.size(); j++) {

					text = text + "	" + formatter.format(matrix[i][j]);

				}
				System.out.println(text);
			}
		}
		System.out.println("---------Over----------");
	}

	public void printCosineSimilarityResult(Matrix matrix) {

		NumberFormat formatter = new DecimalFormat("#0.00");

		String text = "	";

		System.out.println("---------Cosine similarity Printing---------");
		if (allStringList != null && bagOfWords != null) {

			for (int k = 0; k < allStringList.size(); k++) {

				text = text + "S-" + (k + 1) + "	";

			}
			System.out.println(text);

			for (int i = 0; i < allStringList.size(); i++) {
				text = "S-" + (i + 1);
				for (int j = 0; j < allStringList.size(); j++) {

					double[] columnPackedCopy1 = matrix.getMatrix(0, matrix.getRowDimension() - 1, i, i)
							.getColumnPackedCopy();
					double[] columnPackedCopy2 = matrix.getMatrix(0, matrix.getRowDimension() - 1, j, j)
							.getColumnPackedCopy();

					text = text + "	" + formatter.format(cosineSimilarity(columnPackedCopy1, columnPackedCopy2));

				}
				System.out.println(text);
			}
		}
		System.out.println("---------Over----------");
	}

	public void getCosineSimilarityResult(double[][] lsaMatrix) {

		if (allStringList != null && bagOfWords != null && lsaMatrix != null) {

			similarityMatrix = new double[allStringList.size()][allStringList.size()];
			Matrix matrix = new Matrix(lsaMatrix);

			for (int i = 0; i < allStringList.size(); i++) {

				for (int j = 0; j < allStringList.size(); j++) {

					double[] columnPackedCopy1 = matrix.getMatrix(0, matrix.getRowDimension() - 1, i, i)
							.getColumnPackedCopy();
					double[] columnPackedCopy2 = matrix.getMatrix(0, matrix.getRowDimension() - 1, j, j)
							.getColumnPackedCopy();

					similarityMatrix[i][j] = cosineSimilarity(columnPackedCopy1, columnPackedCopy2);

				}

			}
		}

	}

}
