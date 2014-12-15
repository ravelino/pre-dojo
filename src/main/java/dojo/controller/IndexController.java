/**
 * 
 */
package dojo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import dojo.bo.RankingTO;
import dojo.business.GravarLogBusiness;
import dojo.business.RankingBusiness;
import dojo.dao.PartidaDAO;
import dojo.entity.Jogador;
import dojo.entity.Partida;

/**
 * @author ravelino
 *
 */

@Controller
@Path("/")
public class IndexController extends BaseController {

	@Inject
	private GravarLogBusiness gravarLogBusiness;
	
	@Inject
	RankingBusiness rankingBusiness;
	
	@Inject
	PartidaDAO partidaDAO;
	
	@Path("/")
	public void index() {
		
		List<Long> numPartidas = partidaDAO.getNumPartidas();
		getResult()
		.include("numPartidas", numPartidas);
	}
	
	@Path("/upload")
	public void upload(UploadedFile arquivo) {
		
		List<Partida> partidas = gravarLogBusiness.gravar(arquivo);
		HashMap<Jogador, RankingTO> mapRankingTO = rankingBusiness.getRanking(partidas.get(0).getNumPartida());
		List<RankingTO> listRankingTO = reconstruirTO(mapRankingTO);
		getResult()
			.include("listRankingTO", listRankingTO)
			.include("aba",1)
			.include("numPartida", partidas.get(0).getNumPartida())
			.redirectTo(IndexController.class).index();
	}
	
	@Path("/lista/{numPartida}")
	public void lista(Long numPartida){
		HashMap<Jogador, RankingTO> mapRankingTO = rankingBusiness.getRanking(numPartida);
		List<RankingTO> listRankingTO = reconstruirTO(mapRankingTO);
		getResult()
			.include("listRankingTO", listRankingTO)
			.include("aba",1)
			.include("numPartida", numPartida)
			.redirectTo(IndexController.class).index();
	}
	
	private List<RankingTO> reconstruirTO(HashMap<Jogador, RankingTO> mapRankingTO){
		List<RankingTO> listRankingTO = new ArrayList<RankingTO>();
		
		for(Jogador jogador : mapRankingTO.keySet()){
			RankingTO rankingTO = mapRankingTO.get(jogador);
			rankingTO.setJogador(jogador);
			listRankingTO.add(rankingTO);
		}
		
		return listRankingTO;
	} 
	
}
