package dojo.dao.impl;

import javax.enterprise.context.RequestScoped;

import org.hibernate.criterion.Restrictions;

import dojo.dao.ArmaDAO;
import dojo.entity.Arma;

@RequestScoped
public class ArmaDAOImpl extends GenericDAOImpl<Long, Arma> implements ArmaDAO {
	
	private static final long serialVersionUID = -1591236885226930848L;
	
	public ArmaDAOImpl() {
		super(Arma.class);
	}

	@Override
	public Arma buscaPorNome(String nome) {
		return (Arma) getCriteria().add(Restrictions.eq("nome",nome)).uniqueResult();
	}	
}
