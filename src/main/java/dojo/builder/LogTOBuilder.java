/**
 * 
 */
package dojo.builder;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dojo.bo.LogTO;
import dojo.entity.Arma;
import dojo.entity.Jogador;
import dojo.entity.Partida.TipoLogEnum;

/**
 * @author ravelino
 *
 */
public class LogTOBuilder implements BuilderTO<String, LogTO> {
	
	@Override
	public LogTO build(String linha) {
		final Pattern pattern = Pattern.compile("^((\\d{2}\\/){2}\\d{4} (\\d{2}:){2}\\d{2}) - .+(has started|has ended|using .+|by .+)$");
		final Matcher matcher = pattern.matcher(linha.trim());

		final LogTO logTO = new LogTO();
		try {
			if (matcher.find()){
				
				final String data = matcher.group(1);
					logTO.setDataPartida(data);
				
				setNovaPartidaIf(linha, logTO);
				setMortesIf(linha, logTO);
				setFimPartidaIf(linha, logTO);
			}  else {
				logTO.setTipoLogEnum(TipoLogEnum.LINHA_INVALIDA);
			}
			
			logTO.setLinha(linha.trim());
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return logTO;
	}
	
	private void setNovaPartidaIf(String linha, LogTO logTO){
		final Pattern pattern = Pattern.compile("^.+ - New match (\\d+).+$");
		final Matcher matcher = pattern.matcher(linha.trim());
		
		if (matcher.find()){
			logTO.setNumPartida(Long.valueOf(matcher.group(1)));
			logTO.setTipoLogEnum(TipoLogEnum.NOVA_PARTIDA);
		}
	}
	
	private void setFimPartidaIf(String linha, LogTO logTO){
		final Pattern pattern = Pattern.compile("^.+ - Match (\\d+) has ended$");
		final Matcher matcher = pattern.matcher(linha.trim());
		
		if (matcher.find()){
			logTO.setNumPartida((Long.valueOf(matcher.group(1))));
			logTO.setTipoLogEnum(TipoLogEnum.FIM_PARTIDA);
		}
	}
	
	private void setMortesIf(String linha, LogTO logTO) throws ParseException{
		final Pattern pattern = Pattern.compile("^.+ - (.+) killed (.+) (using|by) (.+)$");
		final Matcher matcher = pattern.matcher(linha.trim());
		
		final boolean encontrou = matcher.find();
		
		if (encontrou) {
			final String jogadorMatou = matcher.group(1);
			final String jogadorMorreu = matcher.group(2);
			final String arma = matcher.group(4);
			
			logTO.setJogadorMatou(new Jogador(jogadorMatou));
			logTO.setJogadorMorreu(new Jogador(jogadorMorreu));
			logTO.setArma(new Arma(arma));
			logTO.setTipoLogEnum(TipoLogEnum.MORTE);
		}
	}
}
