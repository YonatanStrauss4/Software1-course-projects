
public class ArrayUtils {


	public static int[] shiftArrayCyclic(int[] array, int move, char direction) {
		if((direction != 'R' && direction != 'L') || move == 0 || array.length == 0){
			return array;
		}
		
		int tmp = 0;
		int endOfArray = array.length - 1;
		int lenOfArray = array.length;
		if((direction == 'R' && move > 0) || (direction == 'L' && move < 0)){
			if(move < 0){
				move = move * (-1);
			}
			int mod = move % array.length;
			for(int i=0; i<mod; i++){
				tmp = array[endOfArray];
				
				for(int j = lenOfArray-2; j>=0; j--){
					array[j+1] = array[j];
				}
				array[0] = tmp;
			}
		}
		
		else {
			if(move < 0){
				move = move * (-1);
			}
			int mod = move % array.length;
			for(int m = 0; m<mod; m++) {
				tmp = array[0];
				
				for(int n = 1; n<lenOfArray; n++){
					array[n-1] = array[n];
					
				}
				array[endOfArray] = tmp;
			}
			
		}
		return array; //Replace this with the correct returned value

	}



	
	public static int findShortestPath(int[][] m, int i, int j) {
		boolean[] visitMatrix = new boolean[m.length];
		int answer = findShortestPathRec(m, visitMatrix, i, j, 0);
		if(answer==2147483647 && m.length < 2147483647)
			answer = -1;
			return answer;

		 }
	
	public static int findShortestPathRec(int[][] array,boolean[] visitMatrix, int n, int m, int counter) {
		if(n==m) {
			return counter;
		}
		
		visitMatrix[n] = true;
		int shortestPath = Integer.MAX_VALUE;
		for(int k = 0; k<array.length; k++){
			if(array[n][k]==1 && !visitMatrix[k]){
				int currDistance = findShortestPathRec(array, visitMatrix, k, m, counter+1);
				if (currDistance < shortestPath) {
					shortestPath = currDistance;
				}
	
			}
		
		}
		visitMatrix[n] = false;
		return shortestPath; //Replace this with the correct returned value

	}

}
