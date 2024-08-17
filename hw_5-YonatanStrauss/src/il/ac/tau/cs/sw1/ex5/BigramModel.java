package il.ac.tau.cs.sw1.ex5;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class BigramModel {
	public static final int MAX_VOCABULARY_SIZE = 14500;
	public static final String VOC_FILE_SUFFIX = ".voc";
	public static final String COUNTS_FILE_SUFFIX = ".counts";
	public static final String SOME_NUM = "some_num";
	public static final int ELEMENT_NOT_FOUND = -1;
	
	String[] mVocabulary;
	int[][] mBigramCounts;
	
	// DO NOT CHANGE THIS !!! 
	public void initModel(String fileName) throws IOException{
		mVocabulary = buildVocabularyIndex(fileName);
		mBigramCounts = buildCountsArray(fileName, mVocabulary);
		
	}
	
	
	public static boolean isWordHasEnglishLetter(String word) {
		for(int i=0; i<word.length(); i++) {
			if((int)word.charAt(i) >= 65 && (int)word.charAt(i) <= 90 || (int)word.charAt(i) >= 97 && (int)word.charAt(i) <= 122) 
				return true;
		}
		
		return false;
	}
	
	public static boolean isWordANumber(String word) {
		if(word.equals(""))
			return false;
		for(int i=0; i<word.length(); i++) {
			if(!(Character.isDigit(word.charAt(i))))
				return false;
		}
		return true;
	
	}
	
	public static int getIndexFromVocabulary(String word, String[] vocab) {
		String vocabIndex;
		for(int i=0; i<vocab.length; i++) {
			vocabIndex = vocab[i];
			if(vocabIndex == null) 
				return -1;
			if(vocabIndex.equals(word.toLowerCase()))
				return i;
		}
		return -1;
	}


	

	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public String[] buildVocabularyIndex(String fileName) throws IOException{ // Q 1
		BufferedReader reader = new BufferedReader(new FileReader(fileName));        
		String line;
		int vocIndex = 0;
		String word;
		String[] words;
		String[] vocabulary = new String[MAX_VOCABULARY_SIZE];
		while((line = reader.readLine()) != null && vocIndex < MAX_VOCABULARY_SIZE) {
			words = line.split("\\s+");
			for(int i=0; i<words.length; i++) {   
				word = words[i];
				if(isWordHasEnglishLetter(word)) {
					if(getIndexFromVocabulary(word, vocabulary) == -1) {
						vocabulary[vocIndex] = word.toLowerCase();
						vocIndex ++;
					}
					else
						continue;			
				}
				
				else {
					if(isWordANumber(word)) {
						if(getIndexFromVocabulary(SOME_NUM, vocabulary) == -1) {
							vocabulary[vocIndex] = SOME_NUM;
							vocIndex ++;
						}
						else
							continue;
					}
					else
						continue;
				}
				
			}

		}
		
		reader.close();
		return Arrays.copyOf(vocabulary, vocIndex); 
	}
	
	
	
	/*
	 * @post: mVocabulary = prev(mVocabulary)
	 * @post: mBigramCounts = prev(mBigramCounts)
	 */
	public int[][] buildCountsArray(String fileName, String[] vocabulary) throws IOException{ // Q - 2
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
		String line;
		int[][] countsArray = new int[vocabulary.length][vocabulary.length];

		String[] words;
		String firstWord;
		String secondWord;
		int IndexFirstWord;
		int IndexSecondWord;
		
		
		while((line = reader.readLine())!= null) {
			words = line.split("\\s+");
			for(int i=0; i<words.length-1; i++) {
				firstWord = words[i];
				if(isWordANumber(firstWord))
					firstWord = SOME_NUM;
				secondWord = words[i+1];
				if(isWordANumber(secondWord))
					secondWord = SOME_NUM;
				IndexFirstWord = getIndexFromVocabulary(firstWord, vocabulary);
				IndexSecondWord = getIndexFromVocabulary(secondWord, vocabulary);
				if(IndexFirstWord != -1 && IndexSecondWord != -1) {
					countsArray[IndexFirstWord][IndexSecondWord] ++;
				}
			}
		
		}
		reader.close();
		return countsArray;
	}
	
	
	/*
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: fileName is a legal file path
	 */
	public void saveModel(String fileName) throws IOException{ // Q-3
		FileWriter fileVoc = new FileWriter(fileName + VOC_FILE_SUFFIX);
		FileWriter fileCounts = new FileWriter(fileName + COUNTS_FILE_SUFFIX);

		BufferedWriter bufferVoc = new BufferedWriter(fileVoc);
		BufferedWriter bufferCounts = new BufferedWriter(fileCounts);
		
		bufferVoc.write(mVocabulary.length + " words");
		bufferVoc.newLine();
		
		for(int i=0; i<mVocabulary.length; i++) {
			bufferVoc.write(i + "," + mVocabulary[i]);
			bufferVoc.newLine();
		}
		for(int i=0; i<mBigramCounts.length; i++) {
			for(int j=0; j<mBigramCounts.length; j++) {
				if(mBigramCounts[i][j] != 0) {
					bufferCounts.write(i + "," + j + ":" + mBigramCounts[i][j]);
					bufferCounts.newLine();
				}
				else
					continue;
			}
		}
		bufferVoc.close();
		bufferCounts.close();
		
	}
	
	
	
	/*
	 * @pre: fileName is a legal file path
	 */
	public void loadModel(String fileName) throws IOException{ // Q - 4
		BufferedReader readerVoc = new BufferedReader(new FileReader(fileName + VOC_FILE_SUFFIX));
		BufferedReader readerCounts = new BufferedReader(new FileReader(fileName + COUNTS_FILE_SUFFIX));
		String line;
		String[] words;
		line = readerVoc.readLine();
		words = line.split("\\s+");

		String[] newVocabulary = new String[Integer.parseInt(words[0])];
		int[][] newBigramCounts = new int[newVocabulary.length][newVocabulary.length];
		
		while((line = readerVoc.readLine())!= null ) {
			words = line.split(",", 2);
			newVocabulary[Integer.parseInt(words[0])] = words[1];
		}
		
		readerVoc.close();
		mVocabulary = newVocabulary;
		
		while((line = readerCounts.readLine())!= null ) {
			words = line.split("[,:]");
			newBigramCounts[Integer.parseInt(words[0])][Integer.parseInt(words[1])] = Integer.parseInt(words[2]);
		}
		readerCounts.close();
		mBigramCounts = newBigramCounts;

	}

	
	
	/*
	 * @pre: word is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = -1 if word is not in vocabulary, otherwise $ret = the index of word in vocabulary
	 */
	public int getWordIndex(String word){  // Q - 5
		if(isWordANumber(word)) 
			word = SOME_NUM;

		for(int i=0; i<mVocabulary.length; i++) {
			if(mVocabulary[i].equals(word))
				return i;
		}
		return ELEMENT_NOT_FOUND;	
	}
	
	
	
	/*
	 * @pre: word1, word2 are in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post: $ret = the count for the bigram <word1, word2>. if one of the words does not
	 * exist in the vocabulary, $ret = 0
	 */
	public int getBigramCount(String word1, String word2){ //  Q - 6
		int indexWord1 = getWordIndex(word1);
		int indexWord2 = getWordIndex(word2);
		if(indexWord1 == -1 || indexWord2 == -1)
			return 0;
		return mBigramCounts[indexWord1][indexWord2];
	}
	
	
	/*
	 * @pre word in lowercase, and is in mVocabulary
	 * @pre: the method initModel was called (the language model is initialized)
	 * @post $ret = the word with the lowest vocabulary index that appears most fequently after word (if a bigram starting with
	 * word was never seen, $ret will be null
	 */
	public String getMostFrequentProceeding(String word){ //  Q - 7
		int indexWord = getWordIndex(word);
		int max = 0;
		String maxPartner = null;
		if(indexWord == -1)
			return null;
		for(int i=0; i<mVocabulary.length; i++) {
			if(mBigramCounts[indexWord][i] > max) {
				max = mBigramCounts[indexWord][i];
				maxPartner = mVocabulary[i];
			}
		}
		if(max == 0)
			return null;
		return maxPartner;
	}
	
	
	/* @pre: sentence is in lowercase
	 * @pre: the method initModel was called (the language model is initialized)
	 * @pre: each two words in the sentence are are separated with a single space
	 * @post: if sentence is is probable, according to the model, $ret = true, else, $ret = false
	 */
	public boolean isLegalSentence(String sentence){  //  Q - 8
		int indexWord1;
		int indexWord2;
		
		String[] words = sentence.split("\\s+");
		if(words.length == 0)
			return true;
		if(words.length == 1) {
			if(getWordIndex(words[0]) != -1)
				return true;
			else
				return false;
		}
		
		for(int i=0; i<words.length-1; i++) {
			indexWord1 = getWordIndex(words[i]);
			indexWord2 = getWordIndex(words[i+1]);
			if(indexWord1 == -1 || indexWord2 == -1)
				return false;
			if(mBigramCounts[indexWord1][indexWord2] == 0)
				return false;
		}
		
		return true;
	}
	
	public static boolean isArrayAllZeros(int[] arr) {
		for(int i=0; i<arr.length; i++) {
			if(arr[i] != 0)
				return false;
		}
		return true;
	}
	
	/*
	 * @pre: arr1.length = arr2.legnth
	 * post if arr1 or arr2 are only filled with zeros, $ret = -1, otherwise calcluates CosineSim
	 */
	public static double calcCosineSim(int[] arr1, int[] arr2){ //  Q - 9
		if(isArrayAllZeros(arr1) || isArrayAllZeros(arr2))
			return -1;
		double top = 0;
		double downLeftBeforeSqrt = 0;
		double downRightBeforeSqrt = 0;
		
		for(int i=0; i<arr1.length; i++) 
			top = top + arr1[i]*arr2[i];
		for(int i=0; i<arr1.length; i++) 
			downLeftBeforeSqrt = downLeftBeforeSqrt + arr1[i]*arr1[i];
		for(int i=0; i<arr1.length; i++) 
			downRightBeforeSqrt = downRightBeforeSqrt + arr2[i]*arr2[i];
		
		return top/((Math.sqrt(downLeftBeforeSqrt))*(Math.sqrt(downRightBeforeSqrt)));
	}

	
	/*
	 * @pre: word is in vocabulary
	 * @pre: the method initModel was called (the language model is initialized), 
	 * @post: $ret = w implies that w is the word with the largest cosineSimilarity(vector for word, vector for w) among all the
	 * other words in vocabulary
	 */
	public String getClosestWord(String word){ //  Q - 10
		if(mVocabulary.length == 1)
			return mVocabulary[0];
		double max = 0.0;
		double dist;
		String mostSimilarWord = null;
		int indexWord = getWordIndex(word);
		for(int i=0; i<mVocabulary.length; i++) {
			if(i != indexWord) {
				dist = calcCosineSim(mBigramCounts[indexWord], mBigramCounts[i]);
				if(dist > max) {
					max = dist;
					mostSimilarWord = mVocabulary[i];
				}
			}
		
		}
		if(max == 0.0)
			if(indexWord == 0)
				mostSimilarWord = mVocabulary[1];
			else
				mostSimilarWord = mVocabulary[0];			
		return mostSimilarWord;
		}

	
	}
