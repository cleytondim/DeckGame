package com.logmein.gameservice.entities;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private List<Card> cards;
	
	public Hand() {
		setCards(new ArrayList<>());
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	
}
