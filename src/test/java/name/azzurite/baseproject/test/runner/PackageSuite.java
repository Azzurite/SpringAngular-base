package name.azzurite.baseproject.test.runner;

import com.google.common.base.Strings;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.RunnerBuilder;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.*;
import java.util.stream.Stream;

/**
 * A suite runner which scans packages for test classes.
 * <p>
 * The class run with this runner must be annotated with {@link SuitePackages}
 */
public class PackageSuite extends ParentRunner<Runner> {

	private final List<Runner> runners;

	/**
	 * Called by JUnit
	 *
	 * @param suiteClass the class that should be run
	 * @param builder the builder
	 * @throws InitializationError
	 */
	public PackageSuite(Class<?> suiteClass, RunnerBuilder builder) throws InitializationError {
		this(suiteClass, builder, getAnnotatedTestClasses(suiteClass));
	}

	/**
	 * Get all the Test Classes annotated for this Suite.
	 *
	 * @param suiteClass the suite class
	 * @return all test classes that should be run
	 * @throws InitializationError
	 */
	private static Class<?>[] getAnnotatedTestClasses(Class<?> suiteClass) throws InitializationError {
		Stream<Class<?>> packageTestClasses = getAnnotatedPackageTestClasses(suiteClass);
		Stream<Class<?>> explicitTestClasses = getExplicitlyAnnotatedTestClasses(suiteClass);

		Class<?>[] allTestClasses = Stream.concat(explicitTestClasses, packageTestClasses).toArray(Class<?>[]::new);

		if (allTestClasses.length == 0) {
			throw new InitializationError("There were no test classes added to the suite. Use @SuiteClasses or @SuitePackages to add some.");
		}

		return allTestClasses;
	}

	/**
	 * Get all the Test Classes declared in the {@link SuiteClasses} annotation.
	 *
	 * @param suiteClass the suite class
	 * @return all explicit tests
	 */
	private static Stream<Class<?>> getExplicitlyAnnotatedTestClasses(Class<?> suiteClass) {
		SuiteClasses suiteClassAnnotation = suiteClass.getAnnotation(SuiteClasses.class);
		if (suiteClassAnnotation == null) {
			return Stream.empty();
		}

		return Arrays.stream(suiteClassAnnotation.value());
	}
	/**
	 * Get all the Test Classes declared in the {@link SuitePackages} annotation.
	 *
	 * @param suiteClass the suite class
	 * @return all package tests
	 * @throws InitializationError
	 */
	private static Stream<Class<?>> getAnnotatedPackageTestClasses(Class<?> suiteClass) {
		SuitePackages suitePackages = suiteClass.getAnnotation(SuitePackages.class);
		if (suitePackages == null) {
			return Stream.empty();
		}
		String basePackage = makeEndWithDot(Strings.nullToEmpty(suitePackages.basePackage()));
		return (Stream<Class<?>>) Arrays.stream(suitePackages.packages())
										.map(PackageSuite::makeNotStartWithDot)
										.map((pack) -> basePackage + pack)
										.map((pack) -> getTestClassesInPackage(pack, suiteClass))
										.flatMap(Collection::stream);
	}

	/**
	 * Gets all the Test classes in the given package.
	 *
	 * @param packName package name, may end with .* to signal scanning of subpackages
	 * @return test classes
	 */
	private static Collection<Class<?>> getTestClassesInPackage(String packName, Class<?> suiteClass) {
		try {
			ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
			provider.addIncludeFilter((metadata, factory) -> metadata.getClassMetadata().getClassName().endsWith("Test"));
			provider.addExcludeFilter((metadata, factory) -> metadata.getClassMetadata().hasEnclosingClass());
			provider.addExcludeFilter((metadata, factory) -> !metadata.getClassMetadata().isConcrete());

			Set<BeanDefinition> testCandidates = provider.findCandidateComponents(packName);

			Collection<Class<?>> testClasses = new ArrayList<>();
			for (BeanDefinition def : testCandidates) {
				testClasses.add(Class.forName(def.getBeanClassName()));
			}
			return testClasses;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Could not find test class found by package scanning", e);
		}
	}

	private static String makeNotStartWithDot(String s) {
		if (s.isEmpty() || s.charAt(0) != '.') {
			return s;
		}
		if (s.length() == 1) {
			return "";
		}
		return s.substring(1);
	}
	private static String makeEndWithDot(String s) {
		if (s.isEmpty() || s.charAt(s.length() - 1) == '.') {
			return s;
		}
		return s + '.';
	}

	/**
	 * Called by this class and subclasses once the classes making up the suite have been determined
	 *
	 * @param suiteClass the root of the suite
	 * @param builder builds runners for classes in the suite
	 * @param testClasses the classes in the suite
	 */
	protected PackageSuite(Class<?> suiteClass, RunnerBuilder builder, Class<?>[] testClasses) throws InitializationError {
		this(suiteClass, builder.runners(suiteClass, testClasses));
	}

	/**
	 * Called by this class and subclasses once the runners making up the suite have been determined
	 *
	 * @param suiteClass root of the suite
	 * @param runners for each class in the suite, a {@link Runner}
	 */
	protected PackageSuite(Class<?> suiteClass, List<Runner> runners) throws InitializationError {
		super(suiteClass);
		this.runners = runners;
	}

	@Override
	protected List<Runner> getChildren() {
		return runners;
	}
	@Override
	protected Description describeChild(Runner child) {
		return child.getDescription();
	}
	@Override
	protected void runChild(Runner child, RunNotifier notifier) {
		child.run(notifier);
	}
}
