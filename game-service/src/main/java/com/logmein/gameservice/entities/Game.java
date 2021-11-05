package com.logmein.gameservice.entities;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private GameDeck gameDeck;
	private List<Integer> idPlayers;
	private List<Deck> decks;
	private Integer round;
	private Integer idGame;

	public Game() {
	}
	
	public Game(Integer idGame) {
		super();
		decks = new ArrayList<>();
		round = 0;
		gameDeck = new GameDeck();
		idPlayers = new ArrayList<>();
		this.idGame = idGame;
	}
	
	public void addDecktoGameDeck(Integer id) throws Exception {
		gameDeck.addDeck(findDeckById(id));
	}
	
	public void createDeck() {
		decks.add(new Deck(findLastDeckId()+1));
	}

	public GameDeck getGameDeck() {
		return gameDeck;
	}

	public List<Integer> getIdPlayers() {
		return idPlayers;
	}
	
	public List<Deck> getDecks() {
		return decks;
	}

	public void addPlayer(Integer id) {
		idPlayers.add(id);
	}
	
	public void removePlayer(Integer id) {
		idPlayers.remove(id);
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getIdGame() {
		return idGame;
	}
	
	private Integer findLastDeckId() {
		int biggerId = 0;
		for (Deck deck : decks) {
			if(deck.getId()>=biggerId)
				biggerId=deck.getId();
		}
		return biggerId;
	}
	
	private Deck findDeckById(Integer id) throws Exception {
		for (Deck deck : decks) {
			if(deck.getId()==id)
				return deck;
		}
		throw new Exception("Deck not found");
	}

	
	
	
}
