package name.azzurite.baseproject.test.rest.service;

import name.azzurite.baseproject.config.constants.BuildConstants;
import name.azzurite.baseproject.test.config.ServiceTestConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceTestConfig.class)
@ActiveProfiles(BuildConstants.SPRING_PROFILE_DEV)
public abstract class AbstractServiceTest {
}
