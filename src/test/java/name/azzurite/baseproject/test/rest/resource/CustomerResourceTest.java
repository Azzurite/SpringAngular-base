package name.azzurite.baseproject.test.rest.resource;

import name.azzurite.baseproject.config.constants.WebConstants;
import name.azzurite.baseproject.test.AbstractIntegrationTest;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerResourceTest extends AbstractIntegrationTest {

	@Test
	public void GetCustomers_ShouldReturnAllCustomers() throws Exception {
		mockMvc.perform(get("/rest/customers"))
			   .andExpect(jsonPath("[0].uniqueName", is("TEST_CUSTOMER")));
	}

	@Test
	public void PostCustomers_ShouldSaveCstomer() throws Exception {
		mockMvc.perform(post("/rest/customers").contentType(WebConstants.MEDIA_TYPE_JSON)
											   .content("{\"uniqueName\": \"TEST_CUSTOMER_2\"}"))
			   .andExpect(jsonPath("$.uniqueName", is("TEST_CUSTOMER_2")));

		mockMvc.perform(get("/rest/customers"))
			   .andExpect(jsonPath(".", hasSize(2)))
			   .andExpect(jsonPath("[1].uniqueName", is("TEST_CUSTOMER_2")));
	}

}
