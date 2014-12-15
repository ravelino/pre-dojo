/**
 * 
 */
package dojo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

import org.apache.commons.io.IOUtils;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

/**
 * @author ravelino
 *
 */
public class UploadUtils {

	public static final String NOME_PROJETO = "pre-dojo";

	public static final String CAMINHO_SEPARADOR = FileSystems.getDefault()
			.getSeparator();

	public static final String PASTA_UPLOAD = "upload" + CAMINHO_SEPARADOR;

	public static File gravar(UploadedFile arquivo) {
		
		final File arquivoUpload = new File(getCaminhoUpload(), "upload_log.log");
		final File diretorio = new File(getCaminhoUpload());
		
		if (!diretorio.isDirectory()){
			diretorio.mkdirs();
		}
		
	    try {
	      IOUtils.copy(arquivo.getFile(), new FileOutputStream(arquivoUpload));
	    } catch (IOException e) {
	      throw new RuntimeException("Erro ao copiar imagem", e);
	    }
	    
	    return arquivoUpload;
	}

	public static String getCaminhoUpload() {

		final String caminho = Paths.get(".").toAbsolutePath().normalize().toString();

		return caminho + CAMINHO_SEPARADOR + PASTA_UPLOAD;
	}

}
