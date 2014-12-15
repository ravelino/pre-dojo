/**
 * 
 */
package dojo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author ravelino
 *
 */

@Entity
@Table(name="partida")
public class Partida implements Serializable {

	private static final long serialVersionUID = -7799601443486705805L;
	
	public enum TipoLogEnum {

		NOVA_PARTIDA(0, "Nova Partida"),
		FIM_PARTIDA(1, "Fim da Partida"),
		MORTE(2, "Morte"),
		WOLD_MATOU(3,"WOLD"),
		MATOU(4, "Matou"),
		MORREU(5, "Morreu"),
		LINHA_INVALIDA(6, "Linha Invalida");
		
		private int tipo;
		private String descricao;
		
		TipoLogEnum(int tipo, String descricao){
			this.tipo = tipo;
			this.descricao = descricao;
		}

		public int getTipo() {
			return tipo;
		}

		public String getDescricao() {
			return descricao;
		}
	}
	
	public Partida() {}
	
	public Partida(Date data, Long numPartida, TipoLogEnum tipoLog,
			Arma arma, Jogador jogador) {
		
		this.data = data;
		this.numPartida = numPartida;
		this.tipoLogEnum = tipoLog;
		this.arma = arma;
		this.jogador = jogador;
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="num_partda")
	private Long numPartida;
	
	@Column(name="data")
	private Date data;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_jogador", referencedColumnName="id")
	private Jogador jogador;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id_arma", referencedColumnName="id")
	private Arma arma;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="tipo_log")
	private TipoLogEnum tipoLogEnum;
	
	private String linha;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumPartida() {
		return numPartida;
	}

	public void setNumPartida(Long numPartida) {
		this.numPartida = numPartida;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public Arma getArma() {
		return arma;
	}

	public void setArma(Arma arma) {
		this.arma = arma;
	}

	public TipoLogEnum getTipoLogEnum() {
		return tipoLogEnum;
	}

	public void setTipoLogEnum(TipoLogEnum tipoLogEnum) {
		this.tipoLogEnum = tipoLogEnum;
	}

	public String getLinha() {
		return linha;
	}

	public void setLinha(String linha) {
		this.linha = linha;
	}
}
