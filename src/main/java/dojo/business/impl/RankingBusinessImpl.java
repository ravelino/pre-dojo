/**
 * 
 */
package dojo.business.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.google.common.collect.Lists;

import dojo.bo.RankingTO;
import dojo.business.RankingBusiness;
import dojo.dao.PartidaDAO;
import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida;
import dojo.entity.Partida.TipoLogEnum;

/**
 * @author ravelino
 *
 */

@RequestScoped
public class RankingBusinessImpl implements RankingBusiness {

	private static final long serialVersionUID = 4170560662426138468L;

	private static int UM_MINUTO = 60;
	
	HashMap<Jogador, RankingTO> listaRankingTO = new HashMap<Jogador, RankingTO>();
	
	List<Partida> partidas = Lists.newArrayList();
	
	private HashMap<Jogador, RankingTO> mapJogadores;
	
	@Inject
	private PartidaDAO partidaDAO;
	
	@Override
	public HashMap<Jogador, RankingTO> getRanking(final List<Partida> listaPartidas) {
		
		partidas.addAll(listaPartidas);
		
		mapJogadores = new HashMap<Jogador, RankingTO>();
		
		for (Partida partida : partidas){
			Jogador jogador = partida.getJogador();
			if (jogador != null && !Jogador.JOGADOR_WOLD1.equals(jogador.getNome()) 
					&& !Jogador.JOGADOR_WOLD2.equals(jogador.getNome())){
				
				RankingTO rankingTO;
				
				if (mapJogadores.containsKey(jogador)){
					rankingTO = mapJogadores.get(jogador);
				} else {
					rankingTO = new RankingTO();
				}
				
				if (TipoLogEnum.MATOU.equals(partida.getTipoLogEnum())){
					setArmas(rankingTO.getArmas(), partida.getArma());
				}
				
				setMortes(partida, rankingTO);
				mapJogadores.put(jogador, rankingTO);
			}
		}
		
		setArmaSetData();
		verificaVencedor();
		
		return mapJogadores;
	}

	private void setArmaSetData(){
		
		for (Jogador jogador : mapJogadores.keySet()){
			RankingTO rankingTO = mapJogadores.get(jogador);
			
			setDatas(rankingTO);
			setArmaPreferida(rankingTO, jogador);
		}
	}
	
	private void setVencedor(Jogador jogadorVencedor){
		RankingTO rankingTO = null;
		for (Jogador jogador: mapJogadores.keySet()){
			if (jogador == jogadorVencedor){
				rankingTO = mapJogadores.get(jogador);
				rankingTO.setVencerdor(jogador);
				rankingTO.setVenceuSemMorrer(rankingTO.getQtdMortes() == 0);
				
				break;
			}
		}
		mapJogadores.put(jogadorVencedor, rankingTO);
	}
	
	private void verificaVencedor() {
		
		RankingTO rankingTOVencedor = null;
		Jogador jogadorVencedor = null;
		
		for (Jogador jogador : mapJogadores.keySet()){
			RankingTO rankingTO = mapJogadores.get(jogador);
			if (rankingTOVencedor == null || rankingTOVencedor.getQtdAssassinatos() < rankingTO.getQtdAssassinatos()){
				rankingTOVencedor = rankingTO;
				jogadorVencedor = jogador;
			} else if (rankingTOVencedor.getQtdAssassinatos() == rankingTO.getQtdAssassinatos()){//desempate por mortes
				if (rankingTOVencedor.getQtdMortes() < rankingTO.getQtdMortes()) {
					rankingTOVencedor = rankingTO;
					jogadorVencedor = jogador;
				} else if (rankingTOVencedor.getQtdMortes() == rankingTO.getQtdMortes()){
					if (rankingTOVencedor.getQtdAssassinatosSequencia() < rankingTO.getQtdAssassinatosSequencia()){//desempate por mortes seguidas
						rankingTOVencedor = rankingTO;
						jogadorVencedor = jogador;
					}
				}
			}
		}
		setVencedor(jogadorVencedor);
	}

	private void setDatas(RankingTO rankingTO){
		int cont = 0;
		int numeroMortesConsecutivas = 0;
		int maxNumeroMortesConsecutivas = 0;
		int mortesEm1Minuto = 0;
		
		List<Long> dataUltimosAssassinatos = new ArrayList<Long>();
		
		for(Date data : rankingTO.getDataMortes()){
			
			if (TipoLogEnum.MATOU.equals(rankingTO.getListaTipoLogEnum().get(cont))){
				
				int mortes = numeroMortesConsecutivas + 1;
				if (mortes > maxNumeroMortesConsecutivas){
					maxNumeroMortesConsecutivas = mortes;
				}
				
				mortesEm1Minuto = set5MortesEm1Minuto(mortesEm1Minuto, dataUltimosAssassinatos, data);
				
				mortesEm1Minuto++;
				numeroMortesConsecutivas++;
				
			} else if (TipoLogEnum.MORREU.equals(rankingTO.getListaTipoLogEnum().get(cont))) {
				numeroMortesConsecutivas = 0;
			}
			cont++;
		}
		
		rankingTO.setMatou5Em1Minuto(mortesEm1Minuto >= 5);
		rankingTO.setQtdAssassinatosSequencia(maxNumeroMortesConsecutivas);
	}
	
	private int set5MortesEm1Minuto(int mortesEm1Minuto, List<Long> dataUltimosAssassinatos, Date data) {
		dataUltimosAssassinatos.add(data.getTime());
		if (dataUltimosAssassinatos.size() > 1){
			
			List<Long> clone = new ArrayList<Long>(dataUltimosAssassinatos);
			
			for (int i = 0; i < clone.size(); i++){
				Long segundos = getDiferencaEmSegundos(data.getTime(), clone.get(i));
				
				if (segundos > UM_MINUTO){
					dataUltimosAssassinatos.remove(0);
					mortesEm1Minuto--;
				}
			}
		}
		return mortesEm1Minuto;
	}
	
	private Long getDiferencaEmSegundos(Long data1, Long data2){
		return ((data1 - data2) / 1000);
	}
	
	private void setArmaPreferida(RankingTO rankingTO, Jogador jogador){
		if (rankingTO.getArmas().size() >= 1){
			int qtdArmaPreferida = Collections.max(rankingTO.getArmas().values());
			
			List<Arma> armaPreferida = new ArrayList<Arma>();
			
			for (Arma arma : rankingTO.getArmas().keySet()){
				if (rankingTO.getArmas().get(arma) == qtdArmaPreferida){
					armaPreferida.add(arma);
				}
			}
			
			rankingTO.setArmasPreferidas(armaPreferida);
			rankingTO.setQtdArmaPreferida(qtdArmaPreferida);
		}
	}
	
	private void setArmas(HashMap<Arma, Integer> armas, Arma arma){
		if (armas.containsKey(arma)){
			armas.put(arma, armas.get(arma) + 1);
		} else {
			armas.put(arma, 1);
		}
	}
	
	private void setMortes(Partida partida, RankingTO rankingTO){
		if (TipoLogEnum.MATOU.equals(partida.getTipoLogEnum())){
			rankingTO.setQtdAssassinatos((rankingTO.getQtdAssassinatos() + 1));
			rankingTO.getListaTipoLogEnum().add(TipoLogEnum.MATOU);
		} else if (TipoLogEnum.MORREU.equals(partida.getTipoLogEnum())){
			rankingTO.setQtdMortes(rankingTO.getQtdMortes() + 1);
			rankingTO.getListaTipoLogEnum().add(TipoLogEnum.MORREU);
		}
		rankingTO.getDataMortes().add(partida.getData());
	}
	
	@Override
	public HashMap<Jogador, RankingTO>  getRanking(final Long numeroPartida) {
		HashMap<Jogador, RankingTO> ranking = null;
		final List<Partida> listaPartidas = partidaDAO.getPorNumPartida(numeroPartida);
		if (listaPartidas != null && listaPartidas.size() > 0){
			ranking = getRanking(listaPartidas);
		}
		return ranking;
	}
}
