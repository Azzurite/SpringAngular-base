package name.azzurite.baseproject.web.rest.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ServiceExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Error> handleServiceException(ServiceException e) {
		if (e.getError().isPresent()) {
			return new ResponseEntity<Error>(e.getError().get(), e.getErrorCategory());
		}
		return new ResponseEntity<Error>(e.getErrorCategory());
	}
}
