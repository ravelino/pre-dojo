/**
 * 
 */
package dojo.controller;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Result;

/**
 * @author ravelino
 *
 */

@Controller
public class BaseController {
	
	@Inject
	private Result result;
	
	public Result getResult() {
		return result;
	}
}
