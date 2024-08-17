

public class StringUtils {

    public static String findSortedSequence(String str) {
    	if(str.isBlank())  
    		return "";
    	if(str.length() == 0) 
    		return "";
       	if(str.length() == 1)
    		return str;
        String[] words = str.split(" ");
        int counter = 0;
        int maxCounter = 0;
        String bestStr = "";
        String currStr = "";
        for(int i = 0; i<words.length-1; i++) {
            
            if(words[i].compareTo(words[i+1]) <= 0) {
                counter ++;
                currStr += (" "+ words[i]);
            }
            
            else {
            	if(currStr != ""){
            		currStr += (" "+ words[i]);
            		counter ++;
            		}
            	if(counter >= maxCounter) {
                
            		maxCounter = counter;
            		counter = 0;
            		bestStr = currStr;
            		currStr = "";
            	}
            }
        }

        if(words[words.length-2].compareTo(words[words.length-1]) <=0 ) {
            counter ++;
            currStr += (" "+ words[words.length-1]);
            if(counter >= maxCounter) {
                bestStr = currStr;
                maxCounter = counter;
            }
            
        }
        if(maxCounter ==1 || maxCounter ==0) {
             bestStr =  " " + words[words.length-1];
        }
                    
        return bestStr.substring(1); //Replace this with the correct returned value

    }

    
    
	
	public static boolean isEditDistanceOne(String a, String b){
		
		if(a.equalsIgnoreCase(b))
			return true;
		if(Math.abs(a.length()-b.length()) > 1)
			return false;
		
			
		int diffCount = 0;
		String bigger;
		String smaller;
		int i = 0;
		int j = 0;
		
		if(a.length() >= b.length()) {
			bigger = a;
			smaller = b;
		}
		else {
			bigger = b;
			smaller = a;
		}
		
		while(i<smaller.length() && j<bigger.length() ) {
			if(diffCount > 1)
				return false;
			
			if(bigger.charAt(j) == smaller.charAt(i)) {
				i++;
				j++;
				continue;
			}
			
			else {
				if(smaller.length() < bigger.length() || j-i<=1) {
					if(bigger.charAt(j+1) == smaller.charAt(i)) {
						j++;
						diffCount++;
						continue;
					}
					
					else
						return false;	
				}
				
				else {
					i++;
					j++;
					diffCount++;
					continue;
				}
				
			}
		
		}
		
		
		if(diffCount > 1)
			return false;
		return true; //Replace this with the correct returned value
	}

	public static void main(String [] args) {
		System.out.println(isEditDistanceOne(args[0], args[1]));
		
	}

	
}

