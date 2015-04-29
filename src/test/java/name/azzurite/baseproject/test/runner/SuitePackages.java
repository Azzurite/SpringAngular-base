package name.azzurite.baseproject.test.runner;

import java.lang.annotation.*;

/**
 * Needed by {@link PackageSuite} to determine the packages that should be scanned
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SuitePackages {
	/**
	 * List all the packages that should be scanned. All packages scanned will include subpackages.
	 */
	String[] packages();

	/**
	 * The base package that should be prepended to all packages.
	 */
	String basePackage() default "";
}
