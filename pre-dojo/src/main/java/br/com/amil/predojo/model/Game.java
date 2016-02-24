package br.com.amil.predojo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.com.amil.predojo.util.DojoUtil;

/**
 * Representa uma partida do jogo
 * @author Levy
 */
public class Game {

	/**
	 * Id do jogo
	 */
	private String id;
	/**
	 * Data e hora do inicio do jogo
	 */
	private Date start;
	/**
	 * Data e hora do fim do jogo
	 */
	private Date end;
	/**
	 * Contém a lista de jogadores
	 */
	private Map<String, Player> players;
	/**
	 * Contém o ranking do jogo após o mesmo ter sido finalizado
	 */
	private List<Player> ranking;
	
	private Player winner;
	
	private Weapon winnerMostUserWeapon;
	
	public Game(String id, Date start) {
		super();
		players = new HashMap<String, Player>();
		this.id = id;
		this.start = start;
	}

	/**
	 * Indica que ocorreu um assassinato
	 * @param murder
	 */
	public void happen(Murder murder) {
		if (isActive()) {
			murder.getKiller().killed(murder);
		} else {
			throw new IllegalStateException("The game is not active!");
		}
	}

	/**
	 * finaliza a partida
	 * @param date
	 */
	public void finish(Date date) {
		this.end = date;
		
		//ordenaa lista
		ranking = new ArrayList<Player>(players.values());
		Collections.sort(ranking);
		
		//remove o world da lista pois deve ser desconsiderado no ranking
		ranking.remove(players.get(WorldPlayer.DEFAULT_WORLD_NAME));
		
		//registra o vencedor
		if (!ranking.isEmpty()) {
			winner = ranking.get(0);
					
			winnerMostUserWeapon = mostUserWeapon(winner.getMurders());
			
			if (winner.getDeaths().isEmpty()) {
				winner.plusAward();
			}
		}
	}
	
	/**
	 * Dada a lista de assassinatos retorna a arma mais utilizada
	 * @param murders
	 * @return
	 */
	private Weapon mostUserWeapon(List<Murder> murders) {
		Map<Weapon, Integer> weapons = new HashMap<Weapon, Integer>();
		
		for (Murder murder : murders) {
			if (murder instanceof WeaponMurder) {
				WeaponMurder weaponMurder = (WeaponMurder) murder;
				if (weapons.containsKey(weaponMurder.getWeapon())) {
					Integer value = weapons.get(weaponMurder.getWeapon());
					weapons.put(weaponMurder.getWeapon(), ++value);
				} else {
					weapons.put(weaponMurder.getWeapon(), 1);
				}
			}
		}
		
		List<Entry<Weapon, Integer>> sorted = DojoUtil.sortMapByValues(weapons);
		
		if (!sorted.isEmpty()) {
			return sorted.get(0).getKey();
		}
		
		return null;
	}

	/**
	 * Obtém o jogador da partida pelo nome, retorna nulo caso o mesmo não exista
	 * na partida
	 * @param name
	 */
	public Player getPlayer(String name) {
		return players.get(name);
	}
	
	/**
	 * Retorna se apartida está ativa
	 * @return
	 */
	public boolean isActive() {
		return end == null;
	}

	/**
	 * Cria um jogador na partida,
	 * caso o mesmo já exista retorna a instancia do mesmo
	 * @param player
	 */
	public Player createPlayer(String name) {
		if (players.containsKey(name)) {
			return getPlayer(name);
		} else {
			Player newPlayer = new PlayerFactory().create(name);
			players.put(name, newPlayer);
			return newPlayer;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Game other = (Game) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public Date getStart() {
		return start;
	}

	public Date getEnd() {
		return end;
	}
	
	/**
	 * Exibe o ranking de uma partida finalizada
	 */
	public void printRanking() {
		if (isActive()) {
			throw new IllegalStateException("The game is active!");
		} else {
			//título
			System.out.println("Ranking for game " + this.getId() + " start on " + DojoUtil.format(this.getStart()) + " finish on "  + DojoUtil.format(this.getEnd()));
			System.out.println();
			if (!ranking.isEmpty()) {
				//cabeçalho da tabela
				System.out.format("%-10s%-15s%-10s%-10s%n", "Position", "Player", "Murders", "Deaths");
				System.out.println("=============================================");
				int rankingNumber = 1;
				for (Player player : ranking) {
					System.out.format ("%-10d%-15s%-10d%-10d%n", rankingNumber++, player.getName(), player.getTotalOfMurders(), player.getTotalOfDeaths());
				}
				System.out.println("---------------------------------------------");
				//winner.getMurders().
				System.out.println(":: Game Statistics ::");
				System.out.println("Winner: " + winner.getName());
				System.out.println("Winner most used weapon: " + winnerMostUserWeapon.getName());
				System.out.println("Streak: " + winner.getStreak());
				System.out.println("Award: " + winner.getAward());
				
				
			} else {
				System.out.println("empy ranking");
			}
			
		}
	}

	public Player getWinner() {
		return winner;
	}

	public Weapon getWinnerMostUserWeapon() {
		return winnerMostUserWeapon;
	}
	
	
}