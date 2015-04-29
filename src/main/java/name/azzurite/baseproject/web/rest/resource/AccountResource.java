package name.azzurite.baseproject.web.rest.resource;

import name.azzurite.baseproject.domain.entity.Account;
import name.azzurite.baseproject.web.rest.error.Errors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("rest")
public class AccountResource {

	@RequestMapping("account")
	public ResponseEntity<?> account() {
		Optional<Account> account = Account.getCurrent();
		if (!account.isPresent()) {
			return new ResponseEntity<>(Errors.create("NO_LOGIN"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(account.get(), HttpStatus.OK);
	}
}
