package dojo.builder;




/**
 * @author ravelino
 *
 */
public interface BuilderTO<In, Out> {
	
	public Out build(In in);

}
