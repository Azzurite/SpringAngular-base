package name.azzurite.baseproject.config;

import name.azzurite.baseproject.config.constants.WebConstants;
import name.azzurite.baseproject.web.LocationRewriteFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class WebConfigurer {


	@Bean
	public Filter rewriteLocations() {
		return new LocationRewriteFilter(WebConstants.APP_LOCATIONS);
	}
}
