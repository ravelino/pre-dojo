/**
 * 
 */
package dojo.dao;

import dojo.entity.Arma;

/**
 * @author ravelino
 *
 */
public interface ArmaDAO extends GenericDAO<Long, Arma>{
	public Arma buscaPorNome(final String nome);
}
