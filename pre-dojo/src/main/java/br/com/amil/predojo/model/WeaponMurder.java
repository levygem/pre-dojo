package br.com.amil.predojo.model;

import java.util.Date;

/**
 * Representa um assassinato com arma
 * @author Levy
 */
public class WeaponMurder extends Murder {
	
	/**
	 * Representa a arma utilizado no assassinato
	 */
	private Weapon weapon;
	
	public WeaponMurder(Player killer, Player victim, Date date, Weapon weapon) {
		super(killer, victim, date);
		this.weapon = weapon;
	}

	public Weapon getWeapon() {
		return weapon;
	}
}