package name.azzurite.baseproject.web.rest.error;

import java.util.HashMap;
import java.util.Map;

public class Error {

	private final String errorKey;

	private final Map<String, Object> additionalData;

	/**
	 * Creates a new Error with the given errorKey.
	 *
	 * @param errorKey identifies the error
	 */
	Error(String errorKey) {
		this.errorKey = errorKey;
		additionalData = new HashMap<>();
	}

	/**
	 * Creates a new Error with the given errorKey and initial data
	 *
	 * @param errorKey identifies the error
	 * @param additionalData additional data
	 */
	Error(String errorKey, Map<String, Object> additionalData) {
		this.errorKey = errorKey;
		this.additionalData = additionalData;
	}

	/**
	 * Adds data to the Error
	 *
	 * @param data additional data
	 */
	public void addData(String key, Object data) {
		additionalData.put(key, data);
	}

	public String getErrorKey() {
		return errorKey;
	}
	public Map<String, Object> getAdditionalData() {
		return additionalData;
	}
}
