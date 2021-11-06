package com.logmein.gameservice.resources;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logmein.gameservice.entities.Deck;
import com.logmein.gameservice.entities.Game;
import com.logmein.gameservice.entities.Hand;
import com.logmein.gameservice.entities.Player;
import com.logmein.gameservice.util.ValueComparator;


@RestController
@RequestMapping(value = "/games")
public class GameServiceResource {


	
	private List<Game> games = new ArrayList<>();

	
	// Lists all the games running
	@GetMapping(value = "/list")
	public ResponseEntity<List<Game>> listGames() {
		return ResponseEntity.ok(games);
	}	
	
	// Return an specific running game from it's ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<Game> findById(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game);
	}
	
	
	// Lists all the players in a game ordered by the sum values of the card's face
	@GetMapping(value = "/{id}/players")
	public ResponseEntity<Map<Integer, Integer>> listPlayers(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		List<Player> players = game.getPlayers();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (Player player : players) {
			map.put(player.getId(), player.getCardsValuesSum());
		}
		ValueComparator comparator = new ValueComparator(map);

	    Map<Integer, Integer> newMap = new TreeMap<Integer, Integer>(comparator);
	    newMap.putAll(map);
		return ResponseEntity.ok(newMap);
	
	}
	
	// Lists the cards of an specific player
	@GetMapping(value = "/{idGame}/{idPlayer}/cards")
	public ResponseEntity<Hand> getPlayerCards(@PathVariable Integer idGame, @PathVariable Integer idPlayer) {
		Player player;
		Game game;
		try {
			game = findGameById(idGame);
			player = findPlayerById(idPlayer, game);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(player.getHand());
	}
		
	// Add a new player to a specific game. When that occurs, the player need to receive the number of cards equivalent to the round counts
	@PostMapping(value = "/{id}/add-player")
	public ResponseEntity<Player> addPlayer(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		
		if(game.getRound()>game.getGameDeck().getAmmountCardsLeft())
		{
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		int newId = game.findLastPlayerId()+1;
		Hand hand = new Hand();
		game.getGameDeck().shuffleGameDeck();
		for(int i=0;i<game.getRound();i++) {
			try {
				hand.addCard(game.getGameDeck().dealCard());
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		Player newPlayer = new Player(newId, hand, game.getIdGame());
		game.addPlayer(newPlayer);
		
		
		return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
	}
	
	
	// When removing a player, we retrieve his cards and put again into the GameDeck
	@DeleteMapping(value = "/{idGame}/remove-player/{idPlayer}")
	public ResponseEntity<Player> removePlayer(@PathVariable Integer idGame, @PathVariable Integer idPlayer) {
		Game game;
		Player player;
		try {
			game = findGameById(idGame);
			player = findPlayerById(idPlayer, game);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		game.removePlayer(player);
		try {
			game.getGameDeck().getCards().addAll(player.getHand().getCards());
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(HttpStatus.OK).body(player);
		
	}
	
	// At any time we can shuffle a specific game by it's ID
	@PostMapping(value = "/shuffle/{id}")
	public ResponseEntity<Game> shuffle(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		game.getGameDeck().shuffleGameDeck();
		return ResponseEntity.ok().build();
	}
	
	// Return the amount of cards of each suit
	@GetMapping(value = "/{id}/count-suits")
	public ResponseEntity<Map<String, Integer>> countSuits(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game.getGameDeck().countsPerSuit());
	}
	
	// Return the amount of cards of suit-face, ordered by suit and decreasing face value
	@GetMapping(value = "/{id}/count-cards")
	public ResponseEntity<Map<String, Integer>> countCards(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game.getGameDeck().counts());
	}
	
	// Creates a new game, generating the ID by the biggest previous one
	@PostMapping(value = "/create")
	public ResponseEntity<Game> createGame() {
		Game game = new Game(findLastGameId()+1);
		games.add(game);
		return ResponseEntity.status(HttpStatus.CREATED).body(game);
	}		
	
	// Deletes a game. When that happen, we have to delete the players associated to the game
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Game> deleteGame(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
			games.remove(game);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game);
	}		
	
	// Creates a new deck into the specified game. The deck stays in stand-by and may or not be added to the gamedeck
	@PostMapping(value = "/{id}/create-deck")
	public ResponseEntity<Deck> createDeck(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
			game.createDeck();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		List<Deck> decks = game.getDecks();
		return ResponseEntity.status(HttpStatus.CREATED).body(decks.get(decks.size()-1));
	}
	
	
	// If a deck is in stand-by, this method add it to the gamedeck of the specified game
	@PostMapping(value = "/{idGame}/add-deck/{idDeck}")
	public ResponseEntity<Game> addDeckToGameDeck(@PathVariable Integer idGame, @PathVariable Integer idDeck) {
		Game game;
		try {
			game = findGameById(idGame);
			game.checkDeckOnGame(idDeck);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		try {
			game.checkDeckOnGameDeck(idDeck);
			game.addDecktoGameDeck(idDeck);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		return ResponseEntity.ok(game);
	}
	
	@PostMapping(value = "/{id}/deal")
	public ResponseEntity<Game> dealCards(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		int nplayers = game.getPlayers().size();
		int cardsLeft = game.getGameDeck().getAmmountCardsLeft();
		if(nplayers > cardsLeft)
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		
		game.getGameDeck().shuffleGameDeck();
		for (Player player : game.getPlayers()) {
			try {
				player.getHand().getCards().add(game.getGameDeck().dealCard());
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
			}
		}
		game.setRound(game.getRound()+1);
		return ResponseEntity.ok(game);
	}
	
	// Support function to get the biggest game ID
	private Integer findLastGameId() {
		int biggerId = 0;
		for (Game game : games) {
			if(game.getIdGame()>=biggerId)
				biggerId=game.getIdGame();
		}
		return biggerId;
	}
	
	// Support function to get a game object from the gamelist based on it's id
	private Game findGameById(Integer id) throws Exception {
		for (Game game : games) {
			if(game.getIdGame().equals(id))
				return game;
		}
		throw new Exception("Game not found");
	}
	
	// Support function to get a player object from the player list based on it's id
	private Player findPlayerById(Integer idPlayer, Game game) throws Exception {
		for (Player player : game.getPlayers()) {
			if(player.getId().equals(idPlayer))
				return player;
		}
		throw new Exception("Game not found");
	}
	
	
	
}
