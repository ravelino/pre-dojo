/**
 * 
 */
package dojo.dao;

import dojo.entity.Jogador;

/**
 * @author ravelino
 *
 */
public interface JogadorDAO extends GenericDAO<Long, Jogador>{

	public Jogador buscaPorNome(final String nome);
	
}
