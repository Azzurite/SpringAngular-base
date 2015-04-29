package name.azzurite.baseproject.test.rest.error;

import name.azzurite.baseproject.web.rest.error.Errors;
import name.azzurite.baseproject.web.rest.error.ServiceException;
import name.azzurite.baseproject.web.rest.resource.CustomerResource;
import name.azzurite.baseproject.web.rest.service.CustomerService;
import name.azzurite.baseproject.test.AbstractIntegrationTest;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ServiceErrorHandlerTest extends AbstractIntegrationTest {

	@Mock
	public CustomerService customerService;

	@Test
	public void ServiceError_ShouldBeReturnedAsJson() throws Exception {
		replaceBeanFieldForTest(CustomerResource.class, customerService);

		when(customerService.getAll()).thenThrow(new ServiceException(HttpStatus.BAD_REQUEST, Errors.createWithData("test").addData("data", "more data").get()));

		mockMvc.perform(get("/rest/customers"))
			   .andExpect(status().is(400))
			   .andExpect(jsonPath("$.errorKey", is("test")))
			   .andExpect(jsonPath("$.additionalData.data", is("more data")));
	}
}
