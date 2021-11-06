package com.logmein.gameservice.entities;

import java.util.ArrayList;
import java.util.List;

public class Game {

	private GameDeck gameDeck;
	private List<Player> players;
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
		players = new ArrayList<>();
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

	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Deck> getDecks() {
		return decks;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
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

	public void checkDeckOnGame(Integer id) throws Exception {
		for (Deck deck : decks) {
			if(deck.getId().equals(id))
				return;
		}
		throw new Exception("Deck already on the game");
	}
	
	public void checkDeckOnGameDeck(Integer id) throws Exception {
		for (Deck deck : decks) {
			if(deck.getId().equals(id)&&deck.getCards().isEmpty())
				throw new Exception("Deck already on the game deck");
		}
	}
	
	public Integer findLastPlayerId() {
		int biggerId = 0;
		for (Player p : players) {
			if(p.getId()>=biggerId)
				biggerId=p.getId();
		}
		return biggerId;
	}
	
	
}
