
public class Assignment02Q03 {

	public static void main(String[] args) {
		int numOfOdd = 0;
		int n = -1;
		// *** your code goes here below ***
		n = Integer.parseInt(args[0]);
		System.out.println("The first "+ n +" Fibonacci numbers are:");
		// *** your code goes here below ***
		String newline = System.lineSeparator();
		int firstFibNum = 0;
		int secondFibNum = 1;
		for (int i =0; i<n; i++) {
			if (secondFibNum % 2 != 0) {
				numOfOdd +=1;
			}
			System.out.print(secondFibNum + " ");
			secondFibNum = firstFibNum + secondFibNum;
			firstFibNum = secondFibNum - firstFibNum;
		}
		System.out.println(newline + "The number of odd numbers is: "+numOfOdd);

	}

}
