package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**************************************
 *  Add your code to this class !!!   *
 **************************************/

public class HashMapHistogramIterator<T extends Comparable<T>> implements Iterator<Map.Entry<T, Integer>>{
	
	private	final ArrayList<Map.Entry<T, Integer>> arrLst;
	private int curr;

	
	public HashMapHistogramIterator(ArrayList<Map.Entry<T, Integer>> arrLst) {
		this.arrLst = arrLst;
		Collections.sort(this.arrLst, new Comparator<Map.Entry<T, Integer>>() {
			 public int compare(Map.Entry<T, Integer> one, Map.Entry<T, Integer> two) {
				 return one.getKey().compareTo(two.getKey());
			 }
		});
		this.curr = 0;
	}
	@Override
	public boolean hasNext() {
		if(curr != this.arrLst.size())
			return true;
		return false; 
	}

	@Override
	public Map.Entry<T, Integer> next() {
		Map.Entry<T, Integer> next1 = this.arrLst.get(curr);
		curr ++;
		return next1; 
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}
	
	//add private methods here, if needed
}
