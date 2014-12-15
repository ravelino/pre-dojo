/**
 * 
 */
package dojo.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida.TipoLogEnum;

/**
 * @author ravelino
 *
 */
public class RankingTO implements Serializable {

	private static final long serialVersionUID = -6692675613402717061L;
	
	private Integer qtdAssassinatos = 0;
	
	private Integer qtdMortes = 0;
	
	private HashMap<Arma, Integer> armas = new HashMap<Arma, Integer>();
	
	private List<Arma> armasPreferidas = new ArrayList<Arma>();
	
	private Jogador jogador;
	
	private Integer qtdArmaPreferida;
	
	private Integer qtdAssassinatosSequencia;
	
	private Boolean venceuSemMorrer;
	
	private Jogador vencerdor;
	
	private Boolean matou5Em1Minuto;
	
	private List<Date> dataMortes = new ArrayList<Date>();
	
	private List<TipoLogEnum> listaTipoLogEnum = new ArrayList<TipoLogEnum>();

	public HashMap<Arma, Integer> getArmas() {
		return armas;
	}

	public void setArmas(HashMap<Arma, Integer> armas) {
		this.armas = armas;
	}

	public List<Date> getDataMortes() {
		return dataMortes;
	}

	public void setDataMortes(List<Date> dataMortes) {
		this.dataMortes = dataMortes;
	}

	public List<TipoLogEnum> getListaTipoLogEnum() {
		return listaTipoLogEnum;
	}

	public void setListaTipoLogEnum(List<TipoLogEnum> listaTipoLogEnum) {
		this.listaTipoLogEnum = listaTipoLogEnum;
	}

	public Boolean isMatou5Em1Minuto() {
		return matou5Em1Minuto;
	}

	public void setMatou5Em1Minuto(Boolean matou5Em1Minuto) {
		this.matou5Em1Minuto = matou5Em1Minuto;
	}

	public Integer getQtdAssassinatosSequencia() {
		return qtdAssassinatosSequencia;
	}

	public void setQtdAssassinatosSequencia(Integer qtdAssassinatosSequencia) {
		this.qtdAssassinatosSequencia = qtdAssassinatosSequencia;
	}

	public List<Arma> getArmasPreferidas() {
		return armasPreferidas;
	}

	public void setArmasPreferidas(List<Arma> armasPreferidas) {
		this.armasPreferidas = armasPreferidas;
	}

	public Integer getQtdArmaPreferida() {
		return qtdArmaPreferida;
	}

	public void setQtdArmaPreferida(Integer qtdArmaPreferida) {
		this.qtdArmaPreferida = qtdArmaPreferida;
	}

	public Integer getQtdAssassinatos() {
		return qtdAssassinatos;
	}

	public void setQtdAssassinatos(Integer qtdAssassinatos) {
		this.qtdAssassinatos = qtdAssassinatos;
	}

	public Integer getQtdMortes() {
		return qtdMortes;
	}

	public void setQtdMortes(Integer qtdMortes) {
		this.qtdMortes = qtdMortes;
	}

	public Jogador getVencerdor() {
		return vencerdor;
	}

	public void setVencerdor(Jogador vencerdor) {
		this.vencerdor = vencerdor;
	}

	public Boolean getVenceuSemMorrer() {
		return venceuSemMorrer;
	}

	public void setVenceuSemMorrer(Boolean venceuSemMorrer) {
		this.venceuSemMorrer = venceuSemMorrer;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Boolean getMatou5Em1Minuto() {
		return matou5Em1Minuto;
	}
}
