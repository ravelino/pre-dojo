/**
 * 
 */
package dojo.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import dojo.bo.RankingTO;
import dojo.business.impl.RankingBusinessImpl;
import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida;
import dojo.entity.Partida.TipoLogEnum;

/**
 * @author ravelino
 *
 */
public class RankingBusinessTest {

	private RankingBusinessImpl rankingBusiness;
	private List<Partida> partidas;
	private static final String FORMATO_DATA = "yyyy-MM-dd HH:mm:ss";
	
	private static final int QTD_JOGADORES_PARTIDA = 3;
	private static final int QTD_ASSASSINATOS_JOGADOR1 = 3;
	private static final int QTD_FALECIDAS_JOGADOR3 = 3;
	private static final int QTD_MORTES_SEGUIDAS_JOGADOR1 = 2;
	
	private static final int QTD_ARMAS_USADAS_JOGADOR1 = 2;
	private static final int QTD_ARMAS_PREFERIDAS_JOGADOR2 = 2;
	
	final Jogador jogador1 = new Jogador(1L, "JOGADOR1");
	final Jogador jogador2 = new Jogador(2L, "JOGADOR2");
	final Jogador jogador3 = new Jogador(3L, "JOGADOR3");

	final Arma arma1 = new Arma(1L, "arma1");
	final Arma arma2 = new Arma(2L, "arma2");
	final Arma arma3 = new Arma(3L, "arma3");

	@Before
	public void setup() {
		rankingBusiness = new RankingBusinessImpl();
		
		partidas = Lists.newArrayList();

		Partida partida1 = new Partida(formataData("2013-04-23 15:34:22"),
				11348965L, TipoLogEnum.NOVA_PARTIDA, null, null);
		
		Partida partida2 = new Partida(formataData("2013-04-23 15:34:23"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador1);
		
		Partida partida3 = new Partida(formataData("2013-04-23 15:34:24"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador2);
		
		Partida partida10 = new Partida(formataData("2013-04-23 15:34:24"),
				11348965L, TipoLogEnum.MATOU, arma2, jogador2);
		
		Partida partida4 = new Partida(formataData("2013-04-23 15:34:25"),
				11348965L, TipoLogEnum.MATOU, arma2, jogador1);
		
		Partida partida5 = new Partida(formataData("2013-04-23 15:34:27"),
				11348965L, TipoLogEnum.MORREU, arma1, jogador3);
		
		Partida partida6 = new Partida(formataData("2013-04-23 15:34:28"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador1);
		
		Partida partida7 = new Partida(formataData("2013-04-23 15:34:29"),
				11348965L, TipoLogEnum.MORREU, arma1, jogador3);
		
		Partida partida8 = new Partida(formataData("2013-04-23 15:34:30"),
				11348965L, TipoLogEnum.FIM_PARTIDA, arma1, null);
		
		Partida partida9 = new Partida(formataData("2013-04-23 15:34:26"),
				11348965L, TipoLogEnum.MORREU, arma2, jogador1);
		
		Partida partida11 = new Partida(formataData("2013-04-23 15:34:51"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida12 = new Partida(formataData("2013-04-23 15:34:52"),
				11348965L, TipoLogEnum.MORREU, arma1, jogador3);
		Partida partida13 = new Partida(formataData("2013-04-23 15:34:53"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida14 = new Partida(formataData("2013-04-23 15:37:54"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida15 = new Partida(formataData("2013-04-23 15:37:55"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida16 = new Partida(formataData("2013-04-23 15:37:56"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida17 = new Partida(formataData("2013-04-23 15:37:58"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		Partida partida18 = new Partida(formataData("2013-04-23 15:37:59"),
				11348965L, TipoLogEnum.MATOU, arma1, jogador3);
		
		partidas.add(partida1);
		partidas.add(partida2);
		partidas.add(partida3);
		partidas.add(partida9);
		partidas.add(partida4);
		partidas.add(partida5);
		partidas.add(partida6);
		partidas.add(partida7);
		partidas.add(partida10);
		partidas.add(partida8);
		partidas.add(partida11);
		partidas.add(partida12);
		partidas.add(partida13);
		partidas.add(partida14);
		partidas.add(partida15);
		partidas.add(partida16);
		partidas.add(partida17);
		partidas.add(partida18);

	}

	@Test
	public void tamanhoListTest(){
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertNotNull(listaRankingTO);
		Assert.assertTrue(listaRankingTO.size() == QTD_JOGADORES_PARTIDA);
	}
	
	@Test
	public void quantidadeMatouTest() {
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertTrue(listaRankingTO.get(jogador1).getQtdAssassinatos() == QTD_ASSASSINATOS_JOGADOR1);
	}
	
	@Test
	public void quantidadeMorreuTest() {
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertTrue(listaRankingTO.get(jogador3).getQtdMortes() == QTD_FALECIDAS_JOGADOR3);
	}
	
	@Test
	public void armaJogador1QtdArmasUsadasTest() {
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertTrue(listaRankingTO.get(jogador1).getArmas().size() == QTD_ARMAS_USADAS_JOGADOR1);
	}
	
	@Test
	public void armaPreferidaJogador1Test() {
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertTrue(listaRankingTO.get(jogador1).getArmasPreferidas().get(0).equals(arma1));
	}
	
	@Test
	public void jogador2ArmasPreferidasSizeTest() {
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertTrue(listaRankingTO.get(jogador2).getArmasPreferidas().size() == QTD_ARMAS_PREFERIDAS_JOGADOR2);
	}
	
	@Test
	public void jogador1QtdAssassinatosSeguidosTest(){
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		
		Assert.assertTrue(listaRankingTO.get(jogador1).getQtdAssassinatosSequencia() == QTD_MORTES_SEGUIDAS_JOGADOR1);
	}
	
	@Test
	public void jogador1Matou5EmUmMinutoTest(){
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		
		Assert.assertTrue(listaRankingTO.get(jogador3).isMatou5Em1Minuto());
	}
	
	@Test
	public void jogadorVencedorTest(){
		final HashMap<Jogador, RankingTO> listaRankingTO = rankingBusiness.getRanking(partidas);
		Assert.assertFalse(listaRankingTO.get(jogador2).getVencerdor() != null);
		Assert.assertTrue(listaRankingTO.get(jogador3).getVencerdor().equals(jogador3));
	}
	
	private Date formataData(String data) {
		Date date = null;
		try {
			date = new SimpleDateFormat(FORMATO_DATA).parse(data);
		} catch (ParseException e) {
		}
		return date;
	}

}
