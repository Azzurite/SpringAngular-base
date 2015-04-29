package name.azzurite.baseproject.test.config.base;

import name.azzurite.baseproject.config.DozerConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DozerConfig.class})
public class ImportMainConfigs {
}
