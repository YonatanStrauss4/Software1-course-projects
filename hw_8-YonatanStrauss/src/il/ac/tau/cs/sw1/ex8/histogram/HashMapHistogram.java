package il.ac.tau.cs.sw1.ex8.histogram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**************************************
 *  Add your code to this class !!!   *
 **************************************/
public class HashMapHistogram<T extends Comparable<T>> implements IHistogram<T>{

	HashMap<T, Integer> dict;
	int sum_of_all;
	
	//add constructor here, if needed

	
	public HashMapHistogram(){
		this.dict = new HashMap<>();
		this.sum_of_all = 0;
	}
	
	@Override
	
	public void addItem(T item) {
		if(this.dict.containsKey(item)) {
			Integer value =  this.dict.get(item);
			value = value + 1;
			this.dict.put(item, value);
		}
		else {
			if(item != null) 
				this.dict.put(item, 1);
		}
		this.sum_of_all ++;
	}
	
	@Override
	public boolean removeItem(T item)  {
		if(this.dict.containsKey(item)) {
			this.sum_of_all--;
			Integer value =  this.dict.get(item);
			value = value--;
			if(value == 0) {
				this.dict.remove(item);
				return true;
			}
			else {
				this.dict.put(item, value);
			}
		}
		return false; //replace this with the correct value
	}
	
	@Override
	public void addAll(Collection<T> items) {
		for(T t : items) {
			addItem(t);
		}
	}

	@Override
	public int getCountForItem(T item) {
		if(this.dict.containsKey(item))
			return this.dict.get(item);
		return 0; //replace this with the correct value
	}

	@Override
	public void clear() {
		this.dict.clear();
		this.sum_of_all = 0;
	}

	@Override
	public Set<T> getItemsSet() {
		return this.dict.keySet(); 
	}
	
	@Override
	public int getCountsSum() {
		return this.sum_of_all; 
	}

	@Override
	public Iterator<Map.Entry<T, Integer>> iterator() {
		ArrayList<Map.Entry<T, Integer>> EntreyList = new ArrayList<>(this.dict.entrySet());
		return new HashMapHistogramIterator<>(EntreyList);
	}
	
}
