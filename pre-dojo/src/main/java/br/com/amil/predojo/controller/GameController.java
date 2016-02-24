package br.com.amil.predojo.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.amil.predojo.model.Game;

/**
 * Controlador abstrato dos jogos
 * @author Levy
 *
 */
public abstract class GameController {

	protected List<Game> games;
	
	protected Game activeGame;
	
	public GameController() {
		super();
		games = new ArrayList<Game>();
	}

	public abstract void execute() throws Exception;

	public void showRanking() {
		for (Game game : games) {
			game.printRanking();
			System.out.println();
		}
	}

	public List<Game> getGames() {
		return games;
	}
}