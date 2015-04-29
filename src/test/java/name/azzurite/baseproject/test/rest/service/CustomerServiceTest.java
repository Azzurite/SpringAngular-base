package name.azzurite.baseproject.test.rest.service;

import com.google.common.collect.Lists;
import name.azzurite.baseproject.domain.entity.Customer;
import name.azzurite.baseproject.domain.entity.component.UniqueName;
import name.azzurite.baseproject.domain.entity.transfer.CustomerTO;
import name.azzurite.baseproject.domain.repository.CustomerRepository;
import name.azzurite.baseproject.web.rest.service.CustomerService;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import java.util.List;

import static org.hamcrest.Matchers.*;

@ContextConfiguration(classes = {CustomerService.class, CustomerServiceTest.Config.class})
public class CustomerServiceTest extends AbstractServiceTest {

	@Configuration
	public static class Config {
		@Bean
		public CustomerRepository customerRepository() {
			CustomerRepository mock = Mockito.mock(CustomerRepository.class);
			return mock;
		}
	}

	@Inject
	private CustomerRepository mockRepository;

	@Inject
	private CustomerService customerService;

	@Inject
	private Mapper mapper;

	@Test
	public void GetAll_ShouldReturnListOfAllCustomers() {
		Mockito.when(mockRepository.findAll())
			   .thenReturn(Lists.newArrayList(new Customer(new UniqueName("testCustomer"))));

		List<CustomerTO> all = customerService.getAll();
		Assert.assertThat(all, hasSize(1));
		Assert.assertThat(all, hasItem(samePropertyValuesAs(new CustomerTO("testCustomer"))));
	}


}
