/*
 * @Author - Euan Boyle
 */


package CodifySolution;

import java.io.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class Solution {
	
	/*
	 * @param args - Contains the name of the text file to be loaded into the program.
	 */
	public static void main(String[] args) throws FileNotFoundException  {
		
		System.out.println("\nFrequency Analysis Programming Challenge\nby Euan Boyle\n\n");
		
		Scanner scConsole = new Scanner(System.in);
		try {
			File textFile = new File(args[0]);
			boolean fileExists = textFile.exists();
			
			if(!fileExists) {
				System.out.println("Could not find " + args[0] + ". Please make sure you have correctly typed in the name of the text file\n and made sure "
						+ "it is in the correct location before running this program again.");
			}
		
			while(fileExists) {
			
				Scanner sc = new Scanner(textFile);
				
				// prompt user
				System.out.println("Do you want to include case sensitivity? \n1 - Yes\n2 - No\n3 - Exit Application");
			
				String userInput = scConsole.nextLine();
			
				Solution sol = new Solution();
			
				if (userInput.equals("1") || userInput.equals("2")) {
					sol.calculate(sc, userInput);
				}else if(userInput.equals("3")) {
					scConsole.close();
					System.exit(0);
				}else {
					System.out.println("Please select a valid option.");
				}
			}
		}catch(FileNotFoundException e) {
		e.printStackTrace();
		}
		
		
	}
	
	/*
	 * @param sc - holds the text loaded in from the text file.
	 * @param userInput - holds the user selection. 1 for case sensitive, 2 for non case sensitive.
	 */
	public void calculate(Scanner sc, String userInput){			
		int charCount = 0;
		
		boolean charExists;
		LinkedList<Characters> linkedListCharacters = new LinkedList<>();
		
		//read in characters to string
		while (sc.hasNextLine()) {
			String text = sc.nextLine();
			
			//convert string to lower case if not case sensitive
			if(userInput.equals("2")) {
				text = text.toLowerCase();
			}
					
			//break string into char array, check each char is a letter
			char[] characters = text.toCharArray();
			
			//check whether current character is a letter, if it is increment charCount by 1. Else ignore the character and move on to the next one.
			for(char c : characters) {
				if(Character.isLetter(c)) {
						
					charExists = false;
					charCount += 1;
						
					//check if character is in linkedlist, increment value by 1 if true, else add character to linkedlist and set value to 1.
					for(Characters charac: linkedListCharacters) {
						if(charac.getKey().equals(c)) {
							charac.incrementValue();
							charExists = true;
						}
					}
					
					if(!charExists) {
						linkedListCharacters.add(new Characters(c, 1));
					}
					
				}
			}
		}
		
		Collections.sort(linkedListCharacters);
		
		//display results of text file.
		System.out.println("\nTotal Characters: " + charCount);
		
		//used to iterate backwards through linked list as .sort() sorts from lowest to highest numerical.
		ListIterator<Characters> it = linkedListCharacters.listIterator(linkedListCharacters.size());
		
		for (int i = 0; i < 10; i++) {
			if(it.hasPrevious()) {
				System.out.println(it.previous().toString());
			}
		}
		
		System.out.println("\n");
	}
}

/*
 * This class is used to create objects that hold a key and value pairing.
 */
class Characters implements Comparable<Characters>{

	private Character key;
	private Integer value;
	
	public Characters(Character key, Integer value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public int compareTo(Characters otherCharacter) {
		
		return this.value - otherCharacter.value;
	}
	
	public String toString() {
		return key + " (" + value + ")";
	}
	
	public void incrementValue() {
		value++;
	}
	
	public Character getKey() {
		return key;
	}
}