package il.ac.tau.cs.sw1.ex8.tfIdf;

import java.io.File;
import java.io.IOException;
import java.util.*;


import il.ac.tau.cs.sw1.ex8.histogram.HashMapHistogram;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class FileIndex {
	
	private boolean isInitialized = false;
	private Map<String, HashMapHistogram<String>> dict_of_files;
	private Map<String, ArrayList<Map.Entry<String,Double>>> sorted_tfidf;
	

	/*
	 * @pre: the directory is no empty, and contains only readable text files
	 * @pre: isInitialized() == false;
	 */
  	public void indexDirectory(String folderPath) { //Q1
  		this.dict_of_files = new HashMap<>();
		File folder = new File(folderPath);
		File[] listFiles = folder.listFiles();
		for (File file : listFiles) {
			if (file.isFile()) {
				HashMapHistogram<String> hist = new HashMapHistogram<>();
				try {
					List<String> tokens = FileUtils.readAllTokens(file);
					for(String token : tokens) {
						hist.addItem(token);
					}
				}
				catch (IOException exception){
					exception.printStackTrace();
				}
				/*******************/
				this.dict_of_files.put(file.getName(), hist);			
				/*******************/
			}
		}
		/*******************/
		this.sorted_tfidf = new HashMap<>();
		Set<Map.Entry<String,HashMapHistogram<String>>> set_of_files_and_their_histogram = this.dict_of_files.entrySet();
		for(Map.Entry<String,HashMapHistogram<String>> entries: set_of_files_and_their_histogram) {
			String file = entries.getKey();
			HashMapHistogram<String> hist = entries.getValue();
			ArrayList<String> words = new ArrayList<>(hist.getItemsSet());
			ArrayList<Double> tfidf = new ArrayList<>();
			for(String word:  words) {
				try {
					double num = getTFIDF(word.toLowerCase(),file);
					tfidf.add(num);

				} catch (FileIndexException expected) {
					expected.printStackTrace();
				}
			}
			
			Map<String, Double> pairs_of_words_tfidf = new HashMap<>();
			for(int i =0; i<tfidf.size(); i++) {
				pairs_of_words_tfidf.put(words.get(i), tfidf.get(i));
			}
			ArrayList<Map.Entry<String,Double>> lst_of_pairs_of_words_tfidf = new ArrayList<>(pairs_of_words_tfidf.entrySet());
			Collections.sort(lst_of_pairs_of_words_tfidf, new Comparator<Map.Entry<String, Double>>() {
	            @Override
	            public int compare(Map.Entry<String, Double> pair1, Map.Entry<String, Double> pair2) {
	            	int compVal = pair2.getValue().compareTo(pair1.getValue());
	            	if (compVal == 0) 
	                    return pair1.getKey().compareTo(pair2.getKey());
	                return compVal;
	            }
	        });
			this.sorted_tfidf.put(file, lst_of_pairs_of_words_tfidf);
		}
		/*******************/
		isInitialized = true;
	}
	
	
	
	// Q2
  	
	/* @pre: isInitialized() */
	public int getCountInFile(String word, String fileName) throws FileIndexException{ 
		if(this.dict_of_files.containsKey(fileName)) {
			return this.dict_of_files.get(fileName).getCountForItem(word.toLowerCase());
		}
		throw new FileIndexException("File is not in directory: " + fileName); 
	}
	
	/* @pre: isInitialized() */
	public int getNumOfUniqueWordsInFile(String fileName) throws FileIndexException{ 
		if(this.dict_of_files.containsKey(fileName)) {
			return this.dict_of_files.get(fileName).getItemsSet().size();
		}
		throw new FileIndexException("File is not in directory: " + fileName);
	}
	
	/* @pre: isInitialized() */
	public int getNumOfFilesInIndex(){
		return this.dict_of_files.size();
	}

	
	/* @pre: isInitialized() */

	public double getTF(String word, String fileName) throws FileIndexException{ // Q3
		if(this.dict_of_files.containsKey(fileName)) {
			return calcTF(getCountInFile(word, fileName), this.dict_of_files.get(fileName).getCountsSum());
		}
		throw new FileIndexException("File is not in directory: " + fileName);
	}
	
	
	
	/* @pre: isInitialized() 
	 * @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getIDF(String word){ //Q4
		int files_with_word = 0;
		for (String file : this.dict_of_files.keySet()) {
			if(this.dict_of_files.get(file).getCountForItem(word.toLowerCase()) != 0)
				files_with_word ++;
		}
		return calcIDF(this.dict_of_files.size(), files_with_word); 
	}
	
	
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfUniqueWordsInFile(fileName)
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKMostSignificantWords(String fileName, int k) throws FileIndexException{ //Q5
		if(this.dict_of_files.containsKey(fileName)) {
			return this.sorted_tfidf.get(fileName).subList(0, k);
		}
		throw new FileIndexException("File is not in directory: " + fileName); 
	}
	
	/* @pre: isInitialized() */
	public double getCosineSimilarity(String fileName1, String fileName2) throws FileIndexException{ //Q6
		if(this.dict_of_files.containsKey(fileName1) && this.dict_of_files.containsKey(fileName2)) {
			List<Map.Entry<String, Double>> vector1 = getTopKMostSignificantWords(fileName1, getNumOfUniqueWordsInFile(fileName1));
			List<Map.Entry<String, Double>> vector2 = getTopKMostSignificantWords(fileName2, getNumOfUniqueWordsInFile(fileName2));
			double bottomLeft = 0;
			double bottomRight = 0;
			double top = 0;
			for(int i=0; i<vector1.size(); i++) {
				bottomLeft = bottomLeft + Math.pow(vector1.get(i).getValue(), 2);
			}
			for(int i=0; i<vector2.size(); i++) {
				bottomRight = bottomRight + Math.pow(vector2.get(i).getValue(), 2);
			}
			for(int i=0; i<vector1.size(); i++) {
				for(int j=0; j<vector2.size(); j++) {
					if(vector1.get(i).getKey().equals(vector2.get(j).getKey())) {
						top = top + vector1.get(i).getValue()*vector2.get(j).getValue();
						
					}
				}
			}
			return top/(Math.sqrt(bottomLeft*bottomRight));
		
		}
		throw new FileIndexException("one of this files is not in directory: " + fileName1 +" or "+ fileName2); //replace this with the correct value
	}
	
	/*
	 * @pre: isInitialized()
	 * @pre: 0 < k <= getNumOfFilesInIndex()-1
	 * @post: $ret.size() = k
	 * @post for i in (0,k-2):
	 * 		$ret[i].value >= $ret[i+1].value
	 */
	public List<Map.Entry<String, Double>> getTopKClosestDocuments(String fileName, int k) throws FileIndexException{ //Q6
		if(this.dict_of_files.containsKey(fileName)){
			Set<String> files = this.dict_of_files.keySet();
			Map<String, Double> files_cosine_similiarity_map = new HashMap<>();
			for(String file : files) {
				if(!file.equals(fileName)) {
				files_cosine_similiarity_map.put(file, getCosineSimilarity(fileName, file));
				}
			}
	        List<Map.Entry<String, Double>> entries_of_list = new ArrayList<>(files_cosine_similiarity_map.entrySet());
			Collections.sort(entries_of_list, new Comparator<Map.Entry<String, Double>>() {
				
				 public int compare(Map.Entry<String, Double> one, Map.Entry<String, Double> two) {
					 return two.getValue().compareTo(one.getValue());
				 }
			});
			return entries_of_list.subList(0, k);
		}
		throw new FileIndexException("File is not in directory: " + fileName); 
	}

	

	
	/*************************************************************/
	/********************* Don't change this ********************/
	/*************************************************************/
	
	public boolean isInitialized(){
		return this.isInitialized;
	}
	
	/* @pre: exist fileName such that getCountInFile(word) > 0*/
	public double getTFIDF(String word, String fileName) throws FileIndexException{
		return this.getTF(word, fileName)*this.getIDF(word);
	}
	
	private static double calcTF(int repetitionsForWord, int numOfWordsInDoc){
		return (double)repetitionsForWord/numOfWordsInDoc;
	}
	
	private static double calcIDF(int numOfDocs, int numOfDocsContainingWord){
		return Math.log((double)numOfDocs/numOfDocsContainingWord);
	}
	
}
