package il.ac.tau.cs.sw1.ex4;

import java.util.Scanner;

import java.util.Arrays;

import java.util.Random;

public class WordPuzzle {
	public static final char HIDDEN_CHAR = '_';
	
	/*
	 * @pre: template is legal for word
	 */
	public static char[] createPuzzleFromTemplate(String word, boolean[] template) { // Q - 1 //simple iteration over both word and template
		char[] Puzzle = new char[word.length()];  
		for(int i=0; i<word.length(); i++) {
			if(!template[i])
				Puzzle[i] = word.charAt(i);
			else
				Puzzle[i] = HIDDEN_CHAR;
		}
		return Puzzle;
	}

	public static boolean checkLegalTemplate(String word, boolean[] template) { // Q - 2  
		if(word.length() != template.length)
			return false;
		StringBuilder visible = new StringBuilder();  //saving all the visible chars
		StringBuilder hidden = new StringBuilder();   //saving all the hidden chars
		
		for(int i=0; i<word.length(); i++) {          
			if(!template[i]) {                        // if the char needs to be visible
				if(contains(hidden, word.charAt(i))){  //checks if its already been hidden
					return false;
				}
				visible.append(word.charAt(i));          //if its not already been hidden, save it to the visible string builder
			}
			else {                                      //same idea for if the char needs to be hidden
				if(contains(visible, word.charAt(i))){
					return false;
				}
				hidden.append(word.charAt(i));
			}
			
		}
		if(hidden.length()==word.length() || visible.length()==word.length()) //to check if there is at least one hidden and one visible characters
			return false;
		
		return true;
	}
	
	public static boolean contains(StringBuilder word, char c) { //method to check if a char is in a string builder
		for(int i=0; i<word.length();i++) {
			if(word.charAt(i) == c)
				return true;
		}
		return false;
	}
	
	/*
	 * @pre: 0 < k < word.lenght(), word.length() <= 10
	 */
	public static boolean[][] getAllLegalTemplates(String word, int k){  // Q - 3
		int wordL = word.length();                                      //saving length of word for further use
		int j = 0;                                                       //will help us to know where to enter the legal template in the final array
		boolean[] currTemplate = new boolean[wordL];                      //because we don't want to calculate getATemplate twice (see later in the code)
		int counter = 0;                                                 //for reducing the array later
		int maxPerm = (int) Math.pow(2, word.length());                  //calculating maximum options of templates for further use
		String binStr = "";
		boolean[][] array = new boolean[maxPerm][wordL];                  //initializing an array sized n^2,n to save the good templates, at the end it will be reduced to exact size
		
		for(int i=0; i<maxPerm; i++) {                                     //loop to chack good templates                         
			binStr = String.format("%0"+ Integer.toString(wordL) + "d", Integer.parseInt(Integer.toBinaryString(i)));    //converting i to string of binary representation and then padding extra zeroes so that the template we create from the binary string will be the size of word
			if(Integer.bitCount(Integer.parseInt(binStr,2)) == k) {                                      //checking if we have k ones. if we do, we can run the checks. if not, its not what we are looking for (1 represent true wich means hidden char)
				currTemplate = getATemplate(binStr);                                                   //getting a template from the binary representation
				if(checkLegalTemplate(word,currTemplate)) {                                            //checking if template is legal
					counter ++;                                                                        //adding 1 to the counter which will help us reduce the array at the end
					array[j] = currTemplate;                                                           //inserting the template in the right place (automatically by order because of the for loop)
					j++;                                                                               //adding 1 to j for next insertion
				}
	
			}
	
		}
		
		return Arrays.copyOf(array,counter);     //deleting unnecessary cells in array and returning 
	}
	
	
	public static boolean[] getATemplate(String binStr){    //method to creat a boolean array from a binary string where 1=true and 0=false
		boolean[] template = new boolean[binStr.length()];
		for(int i=0; i<binStr.length(); i++) {
			if(binStr.charAt(i) == '1')
				template[i] = true;
			else
				template[i] = false;
		}
		return template;
		
	}
	
	
	/*
	 * @pre: puzzle is a legal puzzle constructed from word, guess is in [a...z]
	 */
	public static int applyGuess(char guess, String word, char[] puzzle) { // Q - 4
		if(word.indexOf(guess) == -1)              //check if the guess exists in the word
			return 0;
		
		int counter = 0;                          //will count the numbers of chars that flipped
		
		for(int i=0; i<word.length(); i++) {      //loop to check if guess already been guessed or is visible
			if(puzzle[i] == guess)
				return 0;
		}
			
		for(int j=0; j<word.length(); j++) {      //loop to flip the guessed char and add counts to counters
			if(word.charAt(j) == guess) {
				counter ++;
				puzzle[j] = guess;
			}
		}
		return counter;
	}
	

	/*
	 * @pre: puzzle is a legal puzzle constructed from word
	 * @pre: puzzle contains at least one hidden character. 
	 * @pre: there are at least 2 letters that don't appear in word, and the user didn't guess
	 */
	public static char[] getHint(String word, char[] puzzle, boolean[] already_guessed) { // Q - 5
		Random rnd1 = new Random();                        //for random selection of chars
		Random rnd2 = new Random();                        //for random selection of chars

		char[] hint = new char[2];                         //initializing the hint array
		
		StringBuilder hidden = new StringBuilder();
		StringBuilder newAbc1 = new StringBuilder();
		StringBuilder newAbc2 = new StringBuilder();
		StringBuilder abc = new StringBuilder("abcdefghijklmnopqrstuvwxyz");      //from this we will create the newAbc above from which we will choose later randomly the incorrect char
		
		for(int i=0; i<word.length(); i++) {                                      //building string of hidden chars, from which we will later choose the correct char randomly
			if(puzzle[i] == '_')
				hidden.append(word.charAt(i));
		}
		for(int j=0; j<already_guessed.length; j++) {  //loop that checks what chars have not been guessed yet, and puts them in the newAbc1 string
			if(!already_guessed[j])
				newAbc1.append(abc.charAt(j));
		}

		for(int n=0; n<newAbc1.length(); n++) {             //loop that appends to newAbc2 the chars from newAbc1 without chars that are in word, because we want an incorrect char
			if(word.indexOf(newAbc1.charAt(n)) == -1) 
				newAbc2.append(newAbc1.charAt(n));				
		}
		
		//now we will use random 
	

		char goodGuess = hidden.charAt(rnd1.nextInt(hidden.length()));         //random good guess
		char badGuess = newAbc2.charAt(rnd2.nextInt(newAbc2.length()));        //random bad guess
		
		if(goodGuess >= badGuess) {      //organizing the guesses in alphabetical order
			hint[0] = badGuess;
			hint[1] = goodGuess;
		}
			
		else {                          //organizing the guesses in alphabetical order
			hint[0] = goodGuess;
			hint[1] = badGuess;
		}
		
		
		
		return hint;
	}

	

	public static char[] mainTemplateSettings(String word, Scanner inputScanner) { // Q - 6   
		Random rnd = new Random();         //use to select random templates from legal ones
		boolean[] isLegal;                 //template from user
		boolean[][] array;                 //all the legal templates
		boolean whileStopper = true;       
		printSettingsMessage();
		while(whileStopper) {
			printSelectTemplate();
			if(inputScanner.nextInt() == 1) {
				printSelectNumberOfHiddenChars();
				array = getAllLegalTemplates(word, inputScanner.nextInt());     //get all the legal templates
				if(array.length != 0) {                                         //if there is legal templates
					return createPuzzleFromTemplate(word, array[rnd.nextInt(array.length)]);   //create a puzzle from random legal template
				}
				else {
					printWrongTemplateParameters();       
					continue;
				}
			
			}
		
			else {
				printEnterPuzzleTemplate();
				isLegal = fromTypingToTemplate(inputScanner.next());         //change the template from user to true/false template
				if(checkLegalTemplate(word, isLegal)) {                      //checks if template from user is legal
					return createPuzzleFromTemplate(word, isLegal);          //if legal, creates a puzzle 
				}
				else {
					printWrongTemplateParameters();
					continue;
				}
			}
		}
		return null;
	}
	
	
	public static boolean[] fromTypingToTemplate(String typing) {    //method that take a template that the user enters ("X,_,X....) and turns it into a boolean template
		String newTyping;
		newTyping = typing.replace(",", "");
		boolean[] template = new boolean[newTyping.length()];
		
		for(int i=0; i<newTyping.length(); i++) {
			if(newTyping.charAt(i) == 'X') 
				template[i] = false;
			else
				template[i] = true;
		}
		return template;
	}
	
	
	public static int getNumberOfTries(char[] puzzle) {  //method to calculate the original number of tries
		int num = 0;
		for(int i=0; i<puzzle.length; i++) {
			if(puzzle[i] == '_')
				num++;
		}
		return num+3;
		
	}
	
	public static boolean checksIfHiddens(char[] puzzle, String c) {     //method to check if a char is hidden in a puzzle (we know it is in the original word)
		for(int i=0; i<puzzle.length; i++) {
			if(puzzle[i] == c.charAt(0))
				return false;
		}
		return true;
	}

	public static boolean checksIfThereAreNoUnderlines(char[] puzzle) {     //method to check if the use got the full answer right (no underlines)
		for(int i=0; i<puzzle.length; i++) {
			if(puzzle[i] == '_')
				return false;
		}
		return true;
	}
	
	public static void mainGame(String word, char[] puzzle, Scanner inputScanner){ // Q - 7
		int numOfTries = getNumberOfTries(puzzle);     //initialize number of tries for the game
		char[] hints;                                  //this is how the user going to get the hints
		boolean[] alreadyGuessed = new boolean[26];    //already guessed array size: length of abc
		String abc = "abcdefghijklmnopqrstuvwxyz";     //dictionary to help us arrange the already guessed array correctly
		boolean whileStopper = true;                   
		String guessed;                                //saves the guessed char for further use
		printGameStageMessage();
		while(whileStopper) {
			printPuzzle(puzzle);
			printEnterYourGuessMessage();
			guessed = inputScanner.next();
			if(guessed.equals("H")) {                //the user wants a hint
				hints = getHint(word, puzzle, alreadyGuessed);
				printHint(hints);
				continue;
			}
			else {                                          //the user guessed
				alreadyGuessed[abc.indexOf(guessed)] = true;     //saving the guessed char with true or false in the right position according to the abc
				if(word.indexOf(guessed) != -1 && checksIfHiddens(puzzle, guessed)) {    //if the guessed char is hidden 
					applyGuess(guessed.charAt(0), word, puzzle);     //apply the guess (no need to save how many letters has been revealed
					if(checksIfThereAreNoUnderlines(puzzle)){        //if the word is uncovered
						printWinMessage();                           //win!
						break;
					}
					
					else {
						numOfTries--;                               //reducing number of tries by 1
						printCorrectGuess(numOfTries);
						if(numOfTries != 0)                         //there are more tries to play
							continue;
						else
							break;                                  //no tries left
					}
				}
				
				else {                                              //the guess was no good
					alreadyGuessed[abc.indexOf(guessed)] = true;
					numOfTries--;                                   //reducing number of tries by 1
					printWrongGuess(numOfTries); 
					if(numOfTries != 0)                             //there are more tries to play
						continue;
					else
						break;                                      //no tries left
				}
				
			}

		}
		if(numOfTries == 0)
			printGameOver();
	}
			
/*************************************************************/
/********************* Don't change this ********************/
/*************************************************************/

	public static void main(String[] args) throws Exception { 
		if (args.length < 1){
			throw new Exception("You must specify one argument to this program");
		}
		String wordForPuzzle = args[0].toLowerCase();
		if (wordForPuzzle.length() > 10){
			throw new Exception("The word should not contain more than 10 characters");
		}
		Scanner inputScanner = new Scanner(System.in);
		char[] puzzle = mainTemplateSettings(wordForPuzzle, inputScanner);
		mainGame(wordForPuzzle, puzzle, inputScanner);
		inputScanner.close();
	}


	public static void printSettingsMessage() {
		System.out.println("--- Settings stage ---");
	}

	public static void printEnterWord() {
		System.out.println("Enter word:");
	}
	
	public static void printSelectNumberOfHiddenChars(){
		System.out.println("Enter number of hidden characters:");
	}
	public static void printSelectTemplate() {
		System.out.println("Choose a (1) random or (2) manual template:");
	}
	
	public static void printWrongTemplateParameters() {
		System.out.println("Cannot generate puzzle, try again.");
	}
	
	public static void printEnterPuzzleTemplate() {
		System.out.println("Enter your puzzle template:");
	}


	public static void printPuzzle(char[] puzzle) {
		System.out.println(puzzle);
	}


	public static void printGameStageMessage() {
		System.out.println("--- Game stage ---");
	}

	public static void printEnterYourGuessMessage() {
		System.out.println("Enter your guess:");
	}

	public static void printHint(char[] hist){
		System.out.println(String.format("Here's a hint for you: choose either %s or %s.", hist[0] ,hist[1]));

	}
	public static void printCorrectGuess(int attemptsNum) {
		System.out.println("Correct Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWrongGuess(int attemptsNum) {
		System.out.println("Wrong Guess, " + attemptsNum + " guesses left.");
	}

	public static void printWinMessage() {
		System.out.println("Congratulations! You solved the puzzle!");
	}

	public static void printGameOver() {
		System.out.println("Game over!");
	}

}
