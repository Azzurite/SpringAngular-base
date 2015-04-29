package name.azzurite.baseproject.test;

import name.azzurite.baseproject.test.rest.error.ErrorsTest;
import name.azzurite.baseproject.test.runner.PackageSuite;
import name.azzurite.baseproject.test.runner.SuitePackages;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(PackageSuite.class)
@SuitePackages(basePackage = "name.azzurite.baseproject.test",
		packages = {"rest.service"})
@SuiteClasses(ErrorsTest.class)
public class UnitTests {
}
