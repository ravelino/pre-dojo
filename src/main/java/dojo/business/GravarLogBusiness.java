/**
 * 
 */
package dojo.business;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.vraptor.observer.upload.UploadedFile;
import dojo.entity.Partida;

/**
 * @author ravelino
 *
 */
public interface GravarLogBusiness extends Serializable {

	public List<Partida> gravar(UploadedFile upload);
	
}
