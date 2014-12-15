/**
 * 
 */
package dojo.business;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import dojo.bo.RankingTO;
import dojo.entity.Jogador;
import dojo.entity.Partida;

/**
 * @author ravelino
 *
 */
public interface RankingBusiness extends Serializable{

	public HashMap<Jogador, RankingTO> getRanking(final List<Partida> listaPartidas);
	
	public HashMap<Jogador, RankingTO> getRanking(Long numeroPartida);
	
}
