package name.azzurite.baseproject.domain.entity.conversion;

import org.dozer.DozerConverter;

public abstract class AutoConfiguredDozerConverter<A, B> extends DozerConverter<A, B> {
	private Class<A> classA;
	private Class<B> classB;
	public AutoConfiguredDozerConverter(Class<A> classA, Class<B> classB) {
		super(classA, classB);
		this.classA = classA;
		this.classB = classB;
	}

	public Class<A> getClassA() {
		return classA;
	}
	public Class<B> getClassB() {
		return classB;
	}
}
