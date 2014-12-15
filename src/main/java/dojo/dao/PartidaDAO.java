/**
 * 
 */
package dojo.dao;

import java.util.List;

import dojo.entity.Partida;

/**
 * @author ravelino
 *
 */
public interface PartidaDAO extends GenericDAO<Long, Partida>{

	public void salvarLista(List<Partida> partidas);
	
	public List<Partida> getPorNumPartida(Long numPartida);
	
	public List<Long> getNumPartidas();

}
