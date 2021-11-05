package com.logmein.gameservice.resources;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.logmein.gameservice.entities.Deck;
import com.logmein.gameservice.entities.Game;

@RestController
@RequestMapping(value = "/game")
public class GameServiceResource {

	private List<Game> games = new ArrayList<>();

	
	@GetMapping(value = "/list")
	public ResponseEntity<List<Game>> listGames() {
		return ResponseEntity.ok(games);
	}	
	
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
	
	@GetMapping(value = "/{id}/count-suits")
	public ResponseEntity<Map<String, Integer>> countSuits(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game.getGameDeck().getCountsPerSuit());
	}
	
	@GetMapping(value = "/{id}/count-cards")
	public ResponseEntity<Map<String, Integer>> countCards(@PathVariable Integer id) {
		Game game;
		try {
			game = findGameById(id);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game.getGameDeck().getCounts());
	}
	
	@PostMapping(value = "/create")
	public ResponseEntity<Game> createGame() {
		Game game = new Game(findLastGameId()+1);
		games.add(game);
		return ResponseEntity.ok(game);
	}		
	
	@PostMapping(value = "/delete/{id}")
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
		return ResponseEntity.ok(decks.get(decks.size()-1));
	}
	
	
	
	@PostMapping(value = "/{idGame}/add-deck/{idDeck}")
	public ResponseEntity<Game> createDeck(@PathVariable Integer idGame, @PathVariable Integer idDeck) {
		Game game;
		try {
			game = findGameById(idGame);
			game.addDecktoGameDeck(idDeck);
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(game);
	}
	
	private Integer findLastGameId() {
		int biggerId = 0;
		for (Game game : games) {
			if(game.getIdGame()>=biggerId)
				biggerId=game.getIdGame();
		}
		return biggerId;
	}
	
	private Game findGameById(Integer id) throws Exception {
		for (Game game : games) {
			if(game.getIdGame().equals(id))
				return game;
		}
		throw new Exception("Game not found");
	}
	
	private Integer findGameIndexById(Integer id) throws Exception {
		int count = -1;
		for (Game game : games) {
			count++;
			if(game.getIdGame().equals(id))
				return count;
		}
		throw new Exception("Game not found");
	}
	
}
