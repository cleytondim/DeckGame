package com.logmein.playerservice.entities;

public class Card {

	private Integer number;
	private String face;
	private String suit;
	
	public Card() {
		
	}

	public Card(Integer number, String suit) {
		super();
		this.number = number;
		this.face = generateFace(number);
		this.suit = suit;
	}
	
	private String generateFace(Integer number) throws IllegalArgumentException {
		if(number >=2 && number <= 10)
			return number.toString();
		else
		{
			switch (number) {
				case 1: 
					return "ace";
				case 11:
					return "jack";
				case 12:
					return "queen";
				case 13: 
					return "king";
			}
			throw new IllegalArgumentException("number must be between 1 and 13");
		}
	}
	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}
}
