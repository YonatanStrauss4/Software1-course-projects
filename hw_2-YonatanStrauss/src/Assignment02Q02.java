

public class Assignment02Q02 {

	public static void main(String[] args) {
		// do not change this part below
		double piEstimation = 0.0;
		double add = 0.0;
		int counter = 0;
		int num = Integer.parseInt(args[0]);
		for (int i=1; i<num*2; i+=2) {
			if (counter % 2 ==0) {
				add = add + 1.0/i;
			}
			else {
				add = add - 1.0/i;
			}
			 counter ++;
			
		}
		piEstimation = 4*add;
		// do not change this part below
		System.out.println(piEstimation + " " + Math.PI);

	}

}
