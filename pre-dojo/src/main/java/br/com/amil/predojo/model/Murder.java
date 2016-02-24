package br.com.amil.predojo.model;

import java.util.Date;

/**
 * Indica a ocorrência de um assassinato o assassino e a vitima
 * @author Levy
 */
public abstract class Murder {

	/**
	 * Representa o assassino
	 */
	protected Player killer;
	/**
	 * Representa a vítima
	 */
	protected Player victim;
	/**
	 * Momento do assassinato
	 */
	protected Date date;
	
	public Murder(Player killer, Player victim, Date date) {
		super();
		this.killer = killer;
		this.victim = victim;
		this.date = date;
	}
	
	public Player getKiller() {
		return killer;
	}

	public Player getVictim() {
		return victim;
	}

	public Date getDate() {
		return date;
	}
}