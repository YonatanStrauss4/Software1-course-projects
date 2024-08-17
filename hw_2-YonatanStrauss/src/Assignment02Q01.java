
public class Assignment02Q01 {

	public static void main(String[] args) {
		for (int i = 0; i < args.length; i++) {
			if (args[i].charAt(0) % 5 == 0){
				System.out.println(args[i].charAt(0));			
			}		
		}
	}

}
