package dojo.business.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import br.com.caelum.vraptor.observer.upload.UploadedFile;
import dojo.bo.LogTO;
import dojo.builder.LogTOBuilder;
import dojo.business.GravarLogBusiness;
import dojo.dao.PartidaDAO;
import dojo.entity.Partida;
import dojo.entity.Partida.TipoLogEnum;
import dojo.utils.UploadUtils;

@RequestScoped
public class GravarLogBusinessImpl implements GravarLogBusiness {

	private static final long serialVersionUID = -7866814191135088839L;

	@Inject
	private PartidaDAO partidaDAO;

	private BufferedReader reader;

	private File file;

	private List<LogTO> listaLogTO = new ArrayList<LogTO>();
	
	private Long numPartida = null;

	@Override
	public List<Partida> gravar(UploadedFile upload) {
		file = UploadUtils.gravar(upload);

		setReader();
		
		final List<Partida> partidas = new ArrayList<Partida>();

		try {

			lerArquivo();
			
			for (LogTO logTO : listaLogTO) {

				Partida partida = new Partida();
				
				if (TipoLogEnum.NOVA_PARTIDA.equals(logTO.getTipoLogEnum())) {

					partida.setNumPartida(logTO.getNumPartida());
					partida.setData(logTO.getDataPartida());
					partida.setTipoLogEnum(TipoLogEnum.NOVA_PARTIDA);
					numPartida = logTO.getNumPartida();
					
				} else if (TipoLogEnum.MORTE.equals(logTO.getTipoLogEnum())) {

					final Partida partida2 = new Partida();
					partida2.setArma(logTO.getArma());
					partida2.setJogador(logTO.getJogadorMatou());
					partida2.setTipoLogEnum(TipoLogEnum.MATOU);
					partida2.setData(logTO.getDataPartida());
					partida2.setNumPartida(numPartida);
					partida2.setLinha(logTO.getLinha());
					partidas.add(partida2);
					
					partida.setJogador(logTO.getJogadorMorreu());
					partida.setTipoLogEnum(TipoLogEnum.MORREU);
					partida.setData(logTO.getDataPartida());
					partida.setNumPartida(numPartida);
					
				} else if (TipoLogEnum.FIM_PARTIDA.equals(logTO.getTipoLogEnum())) {
					partida.setNumPartida(logTO.getNumPartida());
					partida.setData(logTO.getDataPartida());
					partida.setTipoLogEnum(TipoLogEnum.FIM_PARTIDA);

				} else if (TipoLogEnum.LINHA_INVALIDA.equals(logTO.getTipoLogEnum())) {
					partida.setTipoLogEnum(TipoLogEnum.LINHA_INVALIDA);
				}
				
				partida.setLinha(logTO.getLinha());
				partidas.add(partida);
			}
			
			partidaDAO.salvarLista(partidas);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return partidas;
	}

	private void lerArquivo() throws IOException, ParseException {
		String linha = null;

		while ((linha = reader.readLine()) != null) {
			listaLogTO.add(new LogTOBuilder().build(linha));
		}

		reader.close();
	}

	private BufferedReader setReader() {
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return reader;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public File getFile() {
		return file;
	}

	public List<LogTO> getListaLogTO() {
		return listaLogTO;
	}

	public void setListaLogTO(List<LogTO> listaLogTO) {
		this.listaLogTO = listaLogTO;
	}

	public void setPartidaDAO(PartidaDAO partidaDAO) {
		this.partidaDAO = partidaDAO;
	}
}
