package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
import java.util.NoSuchElementException;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	

	
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
		

	printList(deckRear);
	countCut();
	printList(deckRear);
		
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		CardNode temp = deckRear;
		do {
			temp = temp.next;
			if(temp.cardValue==27){
				CardNode nextCard = temp.next;
				temp.cardValue = nextCard.cardValue;
				temp.next.cardValue = 27;
				
				return;
			}
		}while(temp!=deckRear);
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    CardNode temp = deckRear;
	    do{
	    	temp=temp.next;
	    	if(temp.cardValue == 28){
	    	
	    		CardNode firstNextCard = temp.next;
	    		CardNode secNextCard = temp.next.next;
	    		temp.cardValue = firstNextCard.cardValue;
	    		temp.next.cardValue = secNextCard.cardValue;
	    		temp.next.next.cardValue = 28;
	    		return;
	    	}
	    } while(temp!=deckRear);
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		CardNode temp = deckRear;
		if (temp.next.cardValue == 27 || temp.next.cardValue == 28){
		
			CardNode firstJoker = temp.next;
			temp = deckRear.next;
			do {
				temp=temp.next;
				if (temp.next.cardValue == 27 || temp.next.cardValue == 28){
					CardNode secondJoker = temp.next;
					CardNode endSecond = deckRear;
					CardNode afterSecond = temp.next.next;
					
					
					endSecond.next = firstJoker;
					deckRear = secondJoker;
					deckRear.next = afterSecond;
					
					
			}
				
			} while (temp!=deckRear);
		}
		
		if (deckRear.cardValue == 28 || deckRear.cardValue == 27){
			
			do{
				temp = temp.next;
				if (temp.next.cardValue == 27 || temp.next.cardValue == 28){
					CardNode beforefirst = deckRear.next;
					CardNode firstJoker = temp.next;
					CardNode endFirst = temp;
					CardNode secondJoker = deckRear;
					
					
							
							deckRear.next = firstJoker;
							secondJoker.next = beforefirst;
							deckRear = endFirst;
							
					
					
				}
			} while (temp!= deckRear);
		}
		
		else{
		do {
			temp = temp.next;
			if (temp.next.cardValue == 27 || temp.next.cardValue == 28){
				CardNode beforeFirst = deckRear.next;
				CardNode endFirst = temp;
				CardNode firstJoker = temp.next;
				
				temp = firstJoker.next;
				do {
					temp =temp.next;
				
					
					if (temp.cardValue == 27 || temp.cardValue == 28){
						CardNode secondJoker = temp;
						CardNode afterSecond = temp.next;
						CardNode endSecond = deckRear;
						
						endSecond.next = firstJoker;
						endFirst.next = afterSecond;
						secondJoker.next = beforeFirst;
						deckRear = endFirst;
						return;
					}
						
					
				} while (temp!=deckRear);
			}
		} while (temp!=deckRear);
		
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {	
		
		int n = deckRear.cardValue;
		
		
		if (n==28){
			n = 27;
			CardNode temp = deckRear;
			CardNode begin = deckRear.next;
			int i = 0;
			do{
				temp = temp.next;
				CardNode end = temp;
				i++;
			
				if (i==n){
					
					temp = deckRear;
					do{
						
						temp = temp.next;
						if (temp.next==deckRear){
							CardNode beforeRear = temp;
							beforeRear.next = begin;
							deckRear.next = end.next;
							end.next = deckRear;
							
							return;
						}
					} while (temp!=deckRear);
					
				}
			} while (temp!=deckRear);
		}
		
		else {
		CardNode temp = deckRear;
		CardNode begin = deckRear.next;
		int i = 0;
		do{
			temp = temp.next;
			CardNode end = temp;
			i++;
		
			if (i==n){
				
				temp = deckRear;
				do{
					
					temp = temp.next;
					if (temp.next==deckRear){
						CardNode beforeRear = temp;
						beforeRear.next = begin;
						deckRear.next = end.next;
						end.next = deckRear;
						return;
					}
				} while (temp!=deckRear);
				
			}
		} while (temp!=deckRear);}
		
		
		
	}
	
        /**
         * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
         * counts down based on the value of the first card and extracts the next card value 
         * as key, but if that value is 27 or 28, repeats the whole process until a value
         * less than or equal to 26 is found, which is then returned.
         * 
         * @return Key between 1 and 26
         */
	int getKey() {
		CardNode target = deckRear;
		do{
		jokerA();
		jokerB();
		tripleCut();
		countCut();
		
		
		CardNode temp = deckRear;
		int i=0;
		
		
		if (temp.next.cardValue == 28){
			do{
				temp = temp.next;
				i++;
				target = temp;
				
			}while(i!=27);
		
		
		}
		
		if (deckRear.next.cardValue!=28){
			int n = deckRear.next.cardValue;
			
			do{
				temp = temp.next;
				
				i++;
			
				if (i==n){
					target = temp;
				}
				
			}while(temp!=deckRear);
			
			
		}
		
		}while(target.next.cardValue==28 || target.next.cardValue==27);
		return target.next.cardValue;
		
		
		
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}
	
	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message)
	throws NoSuchElementException{	
	
		if (message.length()==0){
			throw new NoSuchElementException();}
		
		
		String messageU ="";
		for (int i = 0; i<message.length(); i++){
			messageU += Character.toUpperCase(message.charAt(i));
			
		}
		
		String encrypted="";
		char letter;
		for (int i = 0; i<messageU.length(); i++){
			char ch = messageU.charAt(i);
			if (!Character.isLetter(ch)){
				continue;
			}
			else{
				int key = getKey();
				int alpha = ch - 'A' + 1;
				int sum = key + alpha;
				if (sum>26){
					sum = sum - 26;
					letter = (char)(sum - 1 + 'A');
					encrypted += letter;
					
				}
				else if (sum<=26){
					letter = (char)(sum-1+'A');
					encrypted += letter;
				}
				
				
			}
			}
		
		
		return encrypted;
		
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) 
	throws NoSuchElementException{	
		if (message.length()==0){
			throw new NoSuchElementException();}
		
		String messageU = "";
		for (int i = 0; i<message.length(); i++){
			messageU += Character.toUpperCase(message.charAt(i));
		}
		
	
		
		String decrypted = "";
		
		for (int i = 0; i<messageU.length(); i++){
			char ch = messageU.charAt(i);
			int c = ch -'A'+1;
			int key = getKey();
			int diff;
			
			if (c>key){
				diff = c-key;
			}
			else{
				diff = (c+26)-key;
			}
			char letter = (char)(diff - 1 +'A');
			decrypted = decrypted + letter;}
		
		return decrypted;
	}
}
