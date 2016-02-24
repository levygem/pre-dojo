package br.com.amil.predojo.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Reprsenta um jogador
 * @author Levy
 */
public abstract class Player implements Comparable<Player> {
	/**
	 * Contagem de assassinatos dentro do limte de minutos a contar
	 */
	private static final Integer MINUTE_AWARD_COUNT = 5;
	/**
	 * Total de minutos a verificar
	 */
	private static final Integer MINUTES_RUSH = 1;
	/**
	 * nome do jogador
	 */
	protected String name;
	/**
	 * Lista de assassinatos do jogador
	 */
	private List<Murder> murders;
	/**
	 * Lista das mortes que ocorreram com o jogador
	 */
	private List<Murder> deaths;
	/**
	 * Utilizado para contabilizar os streaks de cada assassinato até a morte
	 */
	private int streakCount;
	/**
	 * Sequencia de assassinatos do jogador
	 * Identificar a maior sequência de assassinatos efetuadas por um jogador (streak) sem morrer
	 */
	private int streak;
	
	/**
	 * Jogadores que vencerem uma partida sem morrerem devem ganhar um "award";
	 * Jogadores que matarem 5 vezes em 1 minuto devem ganhar um "award".
	 */
	private int award;
	
	private int minuteAwardCount;
	
	private LocalDateTime minuteAwardLimit;
	
	public Player(String name) {
		murders = new ArrayList<Murder>();
		deaths = new ArrayList<Murder>();
		this.name = name;
		this.streak = 0;
		this.streakCount = 0;
		minuteAwardCount = 0;
	}
	
	/**
	 * Retorna o total de assassinatos
	 * @return
	 */
	public Integer getTotalOfMurders(){
		return murders.size();
	}
	
	public Integer getTotalOfDeaths() {
		return deaths.size();
	}

	/**
	 * Indica que o jogador cometeu um assassinato
	 * @param victim
	 */
	public void killed(Murder murder) {
		murders.add(murder);
		
		calculateStreak();
		
		calculateMinuteRushAward(murder);
		
		murder.getVictim().died(murder);
	}

	private void calculateMinuteRushAward(Murder murder) {
		if (minuteAwardLimit == null) {
			Instant instant = Instant.ofEpochMilli(murder.getDate().getTime());
			LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			minuteAwardLimit = datetime.plusMinutes(MINUTES_RUSH);
			minuteAwardCount = 1;
		} else {
			Instant instant = Instant.ofEpochMilli(murder.getDate().getTime());
			LocalDateTime datetime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
			if (datetime.isBefore(minuteAwardLimit)) {
				minuteAwardCount++;
				
				if (minuteAwardCount == MINUTE_AWARD_COUNT) {
					plusAward();
					resetMinuteRush();
				}
			} else {
				resetMinuteRush();
			}
		}
	}

	private void resetMinuteRush() {
		minuteAwardLimit = null;
		minuteAwardCount = 0;
	}

	/**
	 * Faz o calculo para o streak
	 */
	private void calculateStreak() {
		streakCount++;
		if (streakCount > streak) {
			streak = streakCount;
		}
	}

	/**
	 * Indica que o jogador
	 * @param killer
	 */
	public void died(Murder murder) {
		deaths.add(murder);
		streakCount = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Murder> getMurders() {
		return murders;
	}

	public void setMurders(List<Murder> murders) {
		this.murders = murders;
	}

	public List<Murder> getDeaths() {
		return deaths;
	}

	public void setDeaths(List<Murder> deaths) {
		this.deaths = deaths;
	}
	
	public int compareTo(Player other) {
		//ordenação de forma descendente pelo total de assassinatos
		return other.getTotalOfMurders().compareTo(this.getTotalOfMurders());
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public int getStreak() {
		return streak;
	}
	
	public int getAward() {
		return award;
	}
	
	public void plusAward() {
		award++;
	}
}