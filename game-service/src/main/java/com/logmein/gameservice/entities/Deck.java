package com.logmein.gameservice.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Deck {
	
	private static final List<String> suits = Arrays.asList("heart", "spade", "club", "diamond");
	private List<Card> cards;
	private Integer id;

	public Deck() {
		
	}
	
	public Deck(Integer id) {
		this.id = id;
		populateDeck(); 
	 }
	 
	 
	private void populateDeck() {
		 cards = new ArrayList<>();
		 for(int i=1;i<=13;i++)
			 for (String suit : suits) {
				cards.add(new Card(i, suit));
			}	 
	 }
	 
	public List<Card> getCards() {
			return cards;
	}


	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	 
	public Integer getId() {
		return this.id;
	}
}
