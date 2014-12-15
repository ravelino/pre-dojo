/**
 * 
 */
package dojo.dao.impl;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import dojo.dao.ArmaDAO;
import dojo.dao.JogadorDAO;
import dojo.dao.PartidaDAO;
import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida;

/**
 * @author ravelino
 *
 */

@RequestScoped
public class PartidaDAOImpl extends GenericDAOImpl<Long, Partida> implements PartidaDAO {

	private static final long serialVersionUID = -6393872068740589634L;
	
	@Inject
	JogadorDAO jogadorDAO;
	
	@Inject
	ArmaDAO armaDAO;
	
	public PartidaDAOImpl(){
		super(Partida.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getNumPartidas(){
		String hql = "select p.numPartida FROM Partida p group by p.numPartida";
		Query query = getSession().createQuery(hql);
		return (List<Long>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Partida> getPorNumPartida(Long numPartida) {
		return getCriteria().add(Restrictions.eq("numPartida", numPartida)).list();
	}

	@Override
	public void salvarLista(List<Partida> partidas) {
		for (Partida partida : partidas){
			
			Jogador jogador = partida.getJogador();
			Arma arma = partida.getArma();
			
			persisteJogador(partida, jogador);
			persisteArma(partida, arma);
			
			getSession().save(partida);
		}
	}
	
	private void persisteArma(Partida partida, Arma arma) {
		if (arma != null && arma.getId() == null) {
			final Arma armaBase = armaDAO.buscaPorNome(arma.getNome());
			
			if (armaBase == null){
				partida.getArma().setId(armaDAO.adiciona(arma));
			} else {
				partida.setArma(armaBase);
			}
		}
	}

	private void persisteJogador(Partida partida, Jogador jogador) {
		if (jogador != null && jogador.getId() == null){
			final Jogador jogadorBase = jogadorDAO.buscaPorNome(jogador.getNome());
			
			if (jogadorBase == null){
				partida.getJogador().setId(jogadorDAO.adiciona(jogador));
			} else {
				partida.setJogador(jogadorBase);
			}
		}
	}
}
