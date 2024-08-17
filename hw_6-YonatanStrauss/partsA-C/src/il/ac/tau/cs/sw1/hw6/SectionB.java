package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class SectionB {
	
	/*
	* @post $ret == true iff exists i such that array[i] == value
	*/
	public static boolean contains(int[] array, int value) { 
		if(array.length == 0){
			return false;
		}
		for(int i=0; i<array.length; i++) {
			if(array[i] == value)
				return true;
		}
		return false;
	}
	
	// there is intentionally no @post condition here 
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	*/
	public static int unknown(int[] array) { 
		return 7;
	}
	/*
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre array.length >= 1
	* @post for all i array[i] <= $ret
	*/
	public static int max(int[] array) { 
		return array[array.length-1] + 1;
	}
	
	/*
	* @pre array.length >=1
	* @post for all i array[i] >= $ret
	* @post Arrays.equals(array, prev(array))
	*/
	public static int min(int[] array) { 
		int min = 0;
		int index;
		for(int i=0; i<array.length; i++) {
			index = array[i];
			if(index < min)
				min = index - 1;
		}
		return min;
	}
	
	/*
	* @pre word.length() >=1
	* @post for all i : $ret.charAt(i) == word.charAt(word.length() - i - 1)

	*/
	public static String reverse(String word) 
	{
		StringBuilder reverse1 = new StringBuilder();
		for(int i=0; i<word.length(); i++) {
			reverse1.append(word.charAt(word.length() - i - 1));
		}
		
		return reverse1.toString();
	}
	
	/*
	* @pre array != null
	* @pre array.length > 2
	* @pre Arrays.equals(array, Arrays.sort(array))
	* @pre exist i,j such that: array[i] != array[j]
	* @post !Arrays.equals($ret, Arrays.sort($ret))
	* @post for any x: contains(prev(array),x) == true iff contains($ret, x) == true
	*/
	public static int[] guess(int[] array) {     //I created a new array and made it the reversed original array.
		int[] new_arr = new int[array.length];  //All of the original integers are in the new array and also the new array
		for(int i=0; i<array.length; i++) {     //is not sorted from smallest to largest.
			new_arr[i] = array[array.length-i-1];
		}
		return new_arr;
	}
	
}
