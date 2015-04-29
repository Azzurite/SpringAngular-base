package name.azzurite.baseproject.web.rest.error;

import java.util.HashSet;
import java.util.Set;

public class Errors {

	public static class ErrorBuilder {
		private final Error error;

		public ErrorBuilder(String key) {
			this.error = new Error(key);
		}

		public ErrorBuilder addData(String key, Object data) {
			error.addData(key, data);
			return this;
		}

		public Error get() {
			return error;
		}
	}

	private static final Set<String> errors = new HashSet<>();

	public static Error create(String key) {
		errors.add(key);
		return new Error(key);
	}

	public static ErrorBuilder createWithData(String key) {
		errors.add(key);
		return new ErrorBuilder(key);
	}
}
