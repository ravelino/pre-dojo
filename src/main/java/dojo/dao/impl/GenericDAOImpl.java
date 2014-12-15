/**
 * 
 */
package dojo.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Criteria;
import org.hibernate.Session;

import dojo.dao.GenericDAO;

/**
 * @author ravelino
 *
 */

public class GenericDAOImpl<PK, T> implements GenericDAO<PK, T> {
	
	private static final long serialVersionUID = 4741509270095584458L;
	
	@Inject
    private Session session;
	
	private Class<T> classe;
	
	public GenericDAOImpl(){
		
	}

	public GenericDAOImpl(Class<T> classe){
		this.classe = classe;
	}
	
	public Long adiciona(T obj) {
		return (Long) session.save(obj);
	}

	public void remove(T obj) {
		session.delete(obj);
	}

	public void atualiza(T obj) {
		session.merge(obj);
	}

	@SuppressWarnings("unchecked")
	public List<T> listaTodos() {
		return getCriteria().list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T buscaPorPK(PK pk) {
		return (T) session.get(classe,(Serializable) pk);
	}
	
	public Session getSession(){
		return session;
	}
	
	protected Criteria getCriteria(){
		return session.createCriteria(classe);
	}
}
