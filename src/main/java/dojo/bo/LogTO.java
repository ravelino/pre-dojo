/**
 * 
 */
package dojo.bo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida.TipoLogEnum;

/**
 * @author ravelino
 *
 */
public class LogTO implements Serializable {
	
	private static final long serialVersionUID = -1801040831717254761L;

	public static final String FORMATO_DATA = "dd/MM/yyyy HH:mm:ss";
	
	private TipoLogEnum tipoLogEnum;
	
	private Long numPartida;
	
	private Date dataPartida;
	
	private Jogador jogadorMatou;
	
	private Jogador jogadorMorreu;
	
	private Arma arma;
	
	private String linha;

	public TipoLogEnum getTipoLogEnum() {
		return tipoLogEnum;
	}

	public void setTipoLogEnum(TipoLogEnum tipoLogEnum) {
		this.tipoLogEnum = tipoLogEnum;
	}

	public Date getDataPartida() {
		return dataPartida;
	}

	public void setDataPartida(Date dataPartida) {
		this.dataPartida = dataPartida;
	}
	
	public void setDataPartida(String dataPartida) throws ParseException {
		this.dataPartida = new SimpleDateFormat(FORMATO_DATA).parse(dataPartida);
	}

	public Jogador getJogadorMatou() {
		return jogadorMatou;
	}

	public void setJogadorMatou(Jogador jogadorMatou) {
		this.jogadorMatou = jogadorMatou;
	}

	public Jogador getJogadorMorreu() {
		return jogadorMorreu;
	}

	public void setJogadorMorreu(Jogador jogadorMorreu) {
		this.jogadorMorreu = jogadorMorreu;
	}

	public Long getNumPartida() {
		return numPartida;
	}

	public void setNumPartida(Long numPartida) {
		this.numPartida = numPartida;
	}

	public Arma getArma() {
		return arma;
	}

	public void setArma(Arma arma) {
		this.arma = arma;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}
}
