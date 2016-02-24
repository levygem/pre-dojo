package br.com.amil.predojo.model;

/**
 * Cria um jogador com base no nome
 * @author Levy
 *
 */
public class PlayerFactory {
	
	/**
	 * Cria um novo jogador com base no nome
	 * @param name
	 * @return
	 */
	public Player create(String name) { 
		if (WorldPlayer.DEFAULT_WORLD_NAME.equals(name)) {
			return new WorldPlayer(name); 
		} else {
			return new NormalPlayer(name);
		} 
	}
}
