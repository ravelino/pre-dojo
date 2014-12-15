/**
 * 
 */
package dojo.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.caelum.vraptor.observer.upload.DefaultUploadedFile;
import br.com.caelum.vraptor.observer.upload.UploadedFile;
import dojo.business.impl.GravarLogBusinessImpl;
import dojo.dao.PartidaDAO;
import dojo.entity.Partida;
import dojo.utils.UploadUtils;
/**
 * @author ravelino
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class GravarLogBusinessTest {
	
	private UploadedFile uploadedFile;
	
	private GravarLogBusinessImpl gravarLogBusiness;
	
	private static final String LINHA0 = "23/04/2013 15:34:22 - New match 11348965 has started";
	private static final String LINHA1 = "23/04/2013 15:36:04 - Roman killed Nick using M16";
	private static final String LINHA2 = "23/04/2013 15:36:33 - &lt;WORLD&gt; killed Nick by DROWN";
	private static final String LINHA3 = "23/04/2013 15:39:22 - Match 11348965 has ended";
	
	@Mock
	private PartidaDAO partidaDAO;
	
	@Before
	public void setup() throws FileNotFoundException {
		
		final String pastaLogTeste = "logs" + UploadUtils.CAMINHO_SEPARADOR + "log1.log";
		final String nomoArquivo = Paths.get(".").toAbsolutePath().normalize().toString() + UploadUtils.CAMINHO_SEPARADOR + pastaLogTeste;
		File arquivo = new File(nomoArquivo);
		FileInputStream is = new FileInputStream(arquivo);
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		uploadedFile = new DefaultUploadedFile(is, nomoArquivo, mimeTypesMap.getContentType(arquivo), arquivo.length());

		//partidaDAO = Mockito.mock(PartidaDAO.class);
		gravarLogBusiness = new GravarLogBusinessImpl();
		gravarLogBusiness.setPartidaDAO(partidaDAO);
	}
	

	@Test
	public void gravarLogTest() throws IOException, ParseException{
		gravarLogBusiness.gravar(uploadedFile);
		Assert.assertNotNull(gravarLogBusiness.getFile());
	}
	
	@Test
	public void gravarLogReaderTest() throws IOException, ParseException{
		gravarLogBusiness.gravar(uploadedFile);
		Assert.assertNotNull(gravarLogBusiness.getReader());
	}
	
	@Test
	public void gravarLogListaLogBOTest() throws IOException, ParseException{
		final List<Partida> partidas = gravarLogBusiness.gravar(uploadedFile);
		Assert.assertTrue(partidas.size() == 6);
		Assert.assertEquals(partidas.get(0).getLinha(), LINHA0);
		Assert.assertEquals(partidas.get(1).getLinha(), LINHA1);
		Assert.assertEquals(partidas.get(2).getLinha(), LINHA1);
		Assert.assertEquals(partidas.get(3).getLinha(), LINHA2);
		Assert.assertEquals(partidas.get(4).getLinha(), LINHA2);
		Assert.assertEquals(partidas.get(5).getLinha(), LINHA3);
	}

}
