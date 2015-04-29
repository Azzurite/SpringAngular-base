package name.azzurite.baseproject.test;

import name.azzurite.baseproject.test.rest.error.ServiceErrorHandlerTest;
import name.azzurite.baseproject.test.runner.PackageSuite;
import name.azzurite.baseproject.test.runner.SuitePackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(PackageSuite.class)
@Suite.SuiteClasses({ServiceErrorHandlerTest.class})
@SuitePackages(basePackage = "name.azzurite.baseproject.test",
		packages = {
				"rest.resource",
				"domain.repository"
		})
public class IntegrationTests {
}
