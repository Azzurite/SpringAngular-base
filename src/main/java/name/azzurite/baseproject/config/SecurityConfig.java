package name.azzurite.baseproject.config;

import name.azzurite.baseproject.config.constants.SecurityConstants;
import name.azzurite.baseproject.config.constants.WebConstants;
import name.azzurite.baseproject.web.security.AddCsrfCookieFilter;
import name.azzurite.baseproject.web.security.CookieCsrfTokenRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;

import javax.servlet.Filter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("azzu").password("test").roles(SecurityConstants.ROLE_USER)
			.and()
			.withUser("test").password("test").roles(SecurityConstants.ROLE_USER);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// send 403 on authentication failure
		http.exceptionHandling()
			.authenticationEntryPoint(new Http403ForbiddenEntryPoint());

		// provide default form login endpoint
		http.formLogin()
			.loginProcessingUrl("/login")
			.failureHandler((request, response, exception) -> {
				response.sendError(403, exception.getMessage());
			})
			.successHandler((request, response, authentication) -> {
				response.setStatus(200);
			});

		// add cookie-based CSRF-protection
		http.csrf()
			.csrfTokenRepository(new CookieCsrfTokenRepository());

		// authorize angular locations
		http.authorizeRequests().antMatchers(WebConstants.APP_LOCATIONS).permitAll();

		// authorize app entry points and resources, rest needs authentication
		http.authorizeRequests()
			.antMatchers(
					"/",
					"/login",
					"/app/**",
					"/assets/**",
					"/bower_components/**",
					"/dist/**"
			).permitAll()
			.anyRequest().authenticated();
	}


	@Bean
	public Filter addCsrfCookieFilter() {
		return new AddCsrfCookieFilter();
	}
}
