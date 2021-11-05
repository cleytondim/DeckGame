package com.logmein.playerservice.entities;

import java.util.ArrayList;
import java.util.List;

public class Hand {

	private List<Card> hand;
	
	public Hand() {
		setHand(new ArrayList<>());
	}

	public List<Card> getHand() {
		return hand;
	}

	public void setHand(List<Card> hand) {
		this.hand = hand;
	}
	
}
