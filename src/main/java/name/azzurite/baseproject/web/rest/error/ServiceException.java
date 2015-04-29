package name.azzurite.baseproject.web.rest.error;

import org.springframework.http.HttpStatus;

import java.util.Optional;

/**
 * Signals an error while processing in a Service. Uses HttpStatus as a convenient predefined error category.
 */
public class ServiceException extends RuntimeException {

	private final HttpStatus errorCategory;

	private final Optional<Error> error;

	/**
	 * Creates a new exception with an undefined error.
	 *
	 * @param errorCategory the error type
	 */
	public ServiceException(HttpStatus errorCategory) {
		if (!errorCategory.is4xxClientError() && !errorCategory.is5xxServerError()) {
			throw new IllegalArgumentException("It makes no sense to create a service exception with a code that doesn't signal an error.");
		}
		this.errorCategory = errorCategory;
		error = Optional.empty();
	}

	/**
	 * Creates a new exception with a specific error.
	 *
	 * @param errorCategory the error type
	 * @param error the error
	 */
	public ServiceException(HttpStatus errorCategory, Error error) {
		this.errorCategory = errorCategory;
		this.error = Optional.of(error);
	}

	/**
	 * @return the category of the error
	 */
	public HttpStatus getErrorCategory() {
		return errorCategory;
	}

	/**
	 * @return the actual error raised
	 */
	public Optional<Error> getError() {
		return error;
	}
}
