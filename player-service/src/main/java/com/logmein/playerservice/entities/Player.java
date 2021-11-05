package com.logmein.playerservice.entities;

import java.util.stream.Collectors;

public class Player {

	private Integer id;
	
	private Hand hand; 
	
	public Player(){
	}
	
	public Player(Integer id, Hand hand) {
		super();
		this.id = id;
		this.hand = hand;
	}
	
	public Integer getId() {
		return id;
	}

	public Hand getHand() {
		return hand;
	}
	
	public void receiveCard(Card card) {
		hand.getHand().add(card);
	}
	
	public Integer getCardsValuesSum() {
		int sum = hand.getHand().stream().map(x -> x.getNumber()).collect(Collectors.toList())
				.stream().reduce(0, (subtotal, number) -> subtotal + number);
		
		return sum;
	}

}
