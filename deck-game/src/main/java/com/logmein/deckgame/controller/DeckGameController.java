package com.logmein.deckgame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping(value = "/deck-game")
public class DeckGameController {

	@GetMapping("/game")
	public String runGame() {
	return "game.xhtml";
	}

}
