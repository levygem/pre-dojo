package br.com.amil.predojo.controller;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.amil.predojo.model.Game;

public class LogFileGameControllerTest {

	@Test
	public void dojolog01Test() throws Exception {
		File file = new File("sample/dojolog01.txt");
		file.getAbsolutePath();
		GameController controller = new LogFileGameController(file);
		controller.execute();

		List<Game> games = controller.getGames();
		Assert.assertEquals(games.size(), 1);

		Game game = games.get(0);
		Assert.assertEquals("Roman", game.getWinner().getName());
		Assert.assertEquals(1, game.getWinner().getTotalOfMurders().intValue());
		Assert.assertEquals(0, game.getWinner().getTotalOfDeaths().intValue());
		Assert.assertEquals("M16", game.getWinnerMostUserWeapon().getName());
		Assert.assertEquals(1, game.getWinner().getStreak());
		Assert.assertEquals(1, game.getWinner().getAward());
	}

	@Test
	public void dojolog02Test() throws Exception {
		File file = new File("sample/dojolog02.txt");
		file.getAbsolutePath();
		GameController controller = new LogFileGameController(file);
		controller.execute();

		List<Game> games = controller.getGames();
		Assert.assertEquals(games.size(), 1);

		Game game = games.get(0);
		Assert.assertEquals("Roman", game.getWinner().getName());
		Assert.assertEquals(5, game.getWinner().getTotalOfMurders().intValue());
		Assert.assertEquals(2, game.getWinner().getTotalOfDeaths().intValue());
		Assert.assertEquals("M16", game.getWinnerMostUserWeapon().getName());
		Assert.assertEquals(3, game.getWinner().getStreak());
		Assert.assertEquals(0, game.getWinner().getAward());
	}
	
	@Test
	public void dojolog03Test() throws Exception {
		File file = new File("sample/dojolog03.txt");
		file.getAbsolutePath();
		GameController controller = new LogFileGameController(file);
		controller.execute();

		List<Game> games = controller.getGames();
		Assert.assertEquals(games.size(), 1);

		Game game = games.get(0);
		Assert.assertEquals("Nick", game.getWinner().getName());
		Assert.assertEquals(3, game.getWinner().getTotalOfMurders().intValue());
		Assert.assertEquals(1, game.getWinner().getTotalOfDeaths().intValue());
		Assert.assertEquals("M17", game.getWinnerMostUserWeapon().getName());
		Assert.assertEquals(3, game.getWinner().getStreak());
		Assert.assertEquals(0, game.getWinner().getAward());
	}
	
	@Test
	public void dojolog04Test() throws Exception {
		File file = new File("sample/dojolog04.txt");
		file.getAbsolutePath();
		GameController controller = new LogFileGameController(file);
		controller.execute();

		List<Game> games = controller.getGames();
		Assert.assertEquals(games.size(), 1);

		Game game = games.get(0);
		Assert.assertEquals("Nick", game.getWinner().getName());
		Assert.assertEquals(9, game.getWinner().getTotalOfMurders().intValue());
		Assert.assertEquals(1, game.getWinner().getTotalOfDeaths().intValue());
		Assert.assertEquals("M17", game.getWinnerMostUserWeapon().getName());
		Assert.assertEquals(9, game.getWinner().getStreak());
		Assert.assertEquals(1, game.getWinner().getAward());
	}
}
