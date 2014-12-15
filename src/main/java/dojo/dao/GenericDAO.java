/**
 * 
 */
package dojo.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author ravelino
 *
 */
public interface GenericDAO<PK, T> extends Serializable {

	public Long adiciona(T obj);

	public void remove(T obj);

	public void atualiza(T obj);

	public List<T> listaTodos();

	public T buscaPorPK(PK pk);
}
