package name.azzurite.baseproject.web.rest.error;

import name.azzurite.baseproject.config.constants.WebConstants;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
public class DefaultErrorHandler implements ErrorController {

	private static final String ERROR_PATH = "/error";

	@RequestMapping(value = ERROR_PATH, produces = WebConstants.MEDIA_TYPE_JSON)
	public Map<String, Object> handle(HttpServletRequest request, HttpServletResponse response) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", request.getAttribute("javax.servlet.error.status_code"));
		map.put("reason", request.getAttribute("javax.servlet.error.message"));

		return map;
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
