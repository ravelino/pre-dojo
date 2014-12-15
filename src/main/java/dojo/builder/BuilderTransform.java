package dojo.builder;


/**
 * @author ravelino
 *
 */
public class BuilderTransform {
	
	private BuilderTransform(){}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <B extends BuilderTO, In, Out> Out transform(B b, In in) {
		return (Out) b.build(in);
	}
}
