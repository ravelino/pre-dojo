/**
 * 
 */
package dojo.dao.impl;

import javax.enterprise.context.RequestScoped;

import org.hibernate.criterion.Restrictions;

import dojo.dao.JogadorDAO;
import dojo.entity.Jogador;

/**
 * @author ravelino
 *
 */

@RequestScoped
public class JogadorDAOImpl extends GenericDAOImpl<Long, Jogador> implements JogadorDAO {

	private static final long serialVersionUID = 6428527969148177636L;

	public JogadorDAOImpl(){
		super(Jogador.class);
	}
	
	@Override
	public Jogador buscaPorNome(String nome) {
		return (Jogador) getCriteria().add(Restrictions.eq("nome",nome)).uniqueResult();
	}

}
