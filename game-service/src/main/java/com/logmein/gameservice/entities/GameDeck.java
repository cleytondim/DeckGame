package com.logmein.gameservice.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameDeck {

	private static final List<String> suits = Arrays.asList("heart", "spade", "club", "diamond");
	private static final List<String> faces =  Arrays.asList("ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king");
	private List<Card> cards;
	
	public GameDeck() {
		cards = new ArrayList<>();
	}
	
	/*public GameDeck(Deck startingDeck) {
		super();
		cards = new ArrayList<>();
		cards.addAll(startingDeck.getCards());
		startingDeck.getCards().clear();
	}*/
	
	public void addDeck(Deck newDeck) {
		cards.addAll(newDeck.getCards());
		newDeck.getCards().clear();
	}
	
	public Card dealCard() throws Exception {
		 if(!cards.isEmpty())
		 {
			 shuffleGameDeck();
			 Card cardToDeal = cards.get(0);
			 cards.remove(0);
			 return cardToDeal;
		 }
		 else
			 throw new Exception("Empty GameDeck");
			 
	 }
	 
	 public void shuffleGameDeck() {
		 Random random = new Random();
		    for (int i = cards.size() - 1; i > 0; i--)
		    {
		      int index = random.nextInt(i + 1);
		      Card card = cards.get(index);
		      cards.set(index, cards.get(i));
		      cards.set(i, card);
		    }
	 }
	 
	 public Map<String, Integer> getCountsPerSuit(){
		 Map<String, Integer> counts = new LinkedHashMap<>();
		 for(String suit : suits)
			 counts.put(suit, getCountSuit(suit));
		 return counts;
	 }
	 
	/* public Map<String, Integer> getCounts(){
		 Map<String, Integer> counts = new LinkedHashMap<>();
		 for(String suit : suits)
			 counts.put(suit, getCountSuit(suit));
		 for(int i=13;i>=1;i--) {
			 counts.put(String.valueOf(faces.get(i-1)), getCountFace(faces.get(i-1)));
		 }
		 return counts;
	 }
	 

	 
	 private int getCountFace(String face) {
		 int count=0;
		 for (Card card : cards) {
			if(card.getFace().equals(face)) {
				count++;
			}
		}
		return count;
	 }
	 
	 */
	 
	 
	 public Map<String, Integer> getCounts(){
		 Map<String, Integer> counts = new LinkedHashMap<>();
		 for(String suit : suits)
			 for(int i=13;i>=1;i--) {
				 counts.put(suit+"s"+" - "+String.valueOf(faces.get(i-1)), getCountEach(suit, faces.get(i-1)));
			 }
		 return counts;
	 }
	 
	 
	 private int getCountEach(String suit, String face) {
		 int count=0;
		 for (Card card : cards) {
			if(card.getFace().equals(face) && card.getSuit().equals(suit)) {
				count++;
			}
		}
		return count;
	 }
	 
	 private int getCountSuit(String suit) {
		 int count=0;
		 for (Card card : cards) {
			if(card.getSuit().equals(suit))
				count++;
		}
		return count;
	 }
		 
	
	
	
	 
	 public int getAmmountCardsLeft() {
		 return cards.size();
	 }
}
