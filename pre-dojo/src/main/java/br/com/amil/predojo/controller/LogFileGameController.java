package br.com.amil.predojo.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.amil.predojo.model.DrownMurder;
import br.com.amil.predojo.model.Game;
import br.com.amil.predojo.model.Player;
import br.com.amil.predojo.model.Weapon;
import br.com.amil.predojo.model.WeaponMurder;
import br.com.amil.predojo.util.DojoUtil;

/**
 * Controlador do jogo via arquivo de log
 * @author Levy
 *
 */
public class LogFileGameController extends GameController {
	
	private static final String NEW_MATCH_REGEX = "New match ([0-9]+) has started";
	
	private static final Pattern NEW_MATCH_PATTERN = Pattern.compile(NEW_MATCH_REGEX);
	
	private static final String END_MATCH_REGEX = "Match ([0-9]+) has ended";
	
	private static final Pattern END_MATCH_PATTERN = Pattern.compile(END_MATCH_REGEX);
	
	private static final String KILLED_USING_WEAPON_REGEX = "(.+) killed (.+) using (.+)";
	
	private static final Pattern KILLED_USING_WEAPON_PATTERN = Pattern.compile(KILLED_USING_WEAPON_REGEX);
	
	private static final String KILLED_BY_DROWN_REGEX = "(.+) killed (.+) by DROWN";
	
	private static final Pattern KILLED_BY_DROWN_PATTERN = Pattern.compile(KILLED_BY_DROWN_REGEX);

	/**
	 * Arquivo que será utilizado para leitura
	 */
	private File file;
	
	public LogFileGameController(File file) {
		super();
		this.file = file;
	}

	@Override
	public void execute() throws IOException {
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new FileReader(file)); 

			String line;
			int lineNumber = 1;
			while ((line = buffer.readLine()) != null) {
				//ignora linhas vazias
				if (!line.isEmpty()) {
					String[] splitted = line.split(" - ");
					Date date = DojoUtil.parse(splitted[0]);
					String logEntry = splitted[1];
					if (logEntry.matches(NEW_MATCH_REGEX)){
						//new match
						newMatch(logEntry, lineNumber, date, NEW_MATCH_PATTERN);
					} else {
						if (logEntry.matches(KILLED_USING_WEAPON_REGEX)){
							//killed using weapon
							killedUsingWeapon(logEntry, lineNumber, date);
						} else {
							if (logEntry.matches(KILLED_BY_DROWN_REGEX)){
								//killed by drown
								killedByDrown(logEntry, lineNumber, date);
							} else {
								if (logEntry.matches(END_MATCH_REGEX)){
									//end match
									endMatch(logEntry, lineNumber, date, END_MATCH_PATTERN);
								} else {
									System.err.println("Can't recognize log entry at line " + lineNumber);
								}
							}
						}
					}
				}
				lineNumber++;
			}
		} finally {
			if (buffer != null) {
				buffer.close();
			}
		}
	}

	/**
	 * Registra um assassinato por afogamento
	 * @param line
	 * @param lineNumber
	 * @param date
	 */
	private void killedByDrown(String line, int lineNumber, Date date) {
		if (activeGame != null) {
			Matcher matcher = KILLED_BY_DROWN_PATTERN.matcher(line);
			matcher.find();
			
			Player killer = activeGame.createPlayer(matcher.group(1));
			Player victim = activeGame.createPlayer(matcher.group(2));
			
			activeGame.happen(new DrownMurder(killer, victim, date));
		} else {
			throw new IllegalStateException("Can not register the murder, there is not a active game! line number " + lineNumber);
		}
	}

	/**
	 * registra um assassinato com arma
	 * @param line
	 * @param lineNumber
	 * @param date
	 */
	private void killedUsingWeapon(String line, int lineNumber, Date date) {
		if (activeGame != null) {
			Matcher matcher = KILLED_USING_WEAPON_PATTERN.matcher(line);
			matcher.find();
			
			Player killer = activeGame.createPlayer(matcher.group(1));
			Player victim = activeGame.createPlayer(matcher.group(2));
			Weapon weapon = new Weapon(matcher.group(3));
			
			activeGame.happen(new WeaponMurder(killer, victim, date, weapon));
		} else {
			throw new IllegalStateException("Can not register the murder, there is not a active game! line number " + lineNumber);
		}
	}

	/**
	 * Finaliza um jogo em ação
	 * @param line
	 * @param lineNumber
	 * @param pattern
	 */
	private void endMatch(String line, int lineNumber, Date date, Pattern pattern) {
		String gameId = getGameId(line, pattern);
		if (activeGame != null && activeGame.getId().equals(gameId)) {
			activeGame.finish(date);
			games.add(activeGame);
			activeGame = null;
		} else {
			throw new IllegalStateException("Can not end the match! line number " + lineNumber);
		}
	}

	/**
	 * Cria um novo jogo
	 * @param line
	 * @param lineNumber
	 * @param date
	 * @param pattern
	 */
	private void newMatch(String line, int lineNumber, Date date, Pattern pattern) {
		//obtém o id do jogo
		if (activeGame == null) {
			activeGame = new Game(getGameId(line, pattern), date);
		} else {
			throw new IllegalStateException("Can not start a new match! line number " + lineNumber);
		}
	}

	/**
	 * Dado uma linha que faz macth com o padrão e retorna o id do jogo
	 * @param line
	 * @return
	 */
	private String getGameId(String line, Pattern pattern) {
		Matcher matcher = pattern.matcher(line);
		matcher.find();
		return matcher.group(1);
	}
}