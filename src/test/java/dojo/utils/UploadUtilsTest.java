/**
 * 
 */
package dojo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

import javax.activation.MimetypesFileTypeMap;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.observer.upload.DefaultUploadedFile;
import br.com.caelum.vraptor.observer.upload.UploadedFile;

/**
 * @author ravelino
 *
 */
public class UploadUtilsTest {
	
	@Before
	public void initVars(){
	}
	
	@Test
	public void gravarTest(){
		
		try {
			
			final String pastaLogTeste = "logs" + UploadUtils.CAMINHO_SEPARADOR + "log1.log";
			
			final String nomoArquivo = Paths.get(".").toAbsolutePath().normalize().toString() + UploadUtils.CAMINHO_SEPARADOR + pastaLogTeste;

			File arquivo = new File(nomoArquivo);
			
			FileInputStream is = new FileInputStream(arquivo);
			
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
	
			UploadedFile upload = new DefaultUploadedFile(is, nomoArquivo, mimeTypesMap.getContentType(arquivo), arquivo.length());
		
			Assert.assertNotNull(UploadUtils.gravar(upload));
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
