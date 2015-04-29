package name.azzurite.baseproject;

import name.azzurite.baseproject.config.constants.BuildConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.SimpleCommandLinePropertySource;

/**
 * Main application class
 */
@SpringBootApplication
public class BaseProject {

	private static final Logger log = LogManager.getLogger(BaseProject.class);

	private static final String SPRING_PROFILE_ARG = "spring.profiles.active";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(BaseProject.class);

		if (!isProfileSet(args)) {
			addDevProfile(app);
		}

		app.run(args);
	}

	private static void addDevProfile(SpringApplication app) {
		log.warn("Using default profile: {}", BuildConstants.SPRING_PROFILE_DEV);
		app.setAdditionalProfiles(BuildConstants.SPRING_PROFILE_DEV);
	}

	private static boolean isProfileSet(String[] args) {
		SimpleCommandLinePropertySource cmdLine = new SimpleCommandLinePropertySource(args);
		return cmdLine.containsProperty(SPRING_PROFILE_ARG);
	}

}
