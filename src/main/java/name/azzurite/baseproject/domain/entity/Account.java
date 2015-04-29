package name.azzurite.baseproject.domain.entity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class Account {

	public final String name;

	public Account(String name) {
		this.name = name;
	}

	public static Optional<Account> getCurrent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return Optional.empty();
		}

		return Optional.of(new Account(authentication.getName()));
	}

	public String getName() {
		return name;
	}
}
