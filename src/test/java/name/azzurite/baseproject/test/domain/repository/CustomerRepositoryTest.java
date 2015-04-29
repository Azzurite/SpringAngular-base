package name.azzurite.baseproject.test.domain.repository;

import name.azzurite.baseproject.domain.entity.Customer;
import name.azzurite.baseproject.domain.entity.component.UniqueName;
import name.azzurite.baseproject.domain.repository.CustomerRepository;
import name.azzurite.baseproject.test.AbstractIntegrationTest;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class CustomerRepositoryTest extends AbstractIntegrationTest {

	@Inject
	private CustomerRepository customerRepository;

	@Test
	public void FindByUniqueName_ShouldReturnCustomer_IfCustomerExists() {
		Optional<Customer> customer = customerRepository.findOne(new UniqueName("TEST_CUSTOMER"));
		assertThat(customer.isPresent(), is(true));

		Customer expectedCustomer = new Customer(new UniqueName("TEST_CUSTOMER"));
		assertThat(customer.get(), is(expectedCustomer));
	}

	@Test
	public void FindByUniqueName_ShouldReturnEmpty_IfCustomerIsMissing() {
		assertThat(customerRepository.findOne(new UniqueName("TEST_CUSTOMER_2")), is(Optional.empty()));
	}

	@Test
	public void CustomerExists_ShouldReturnFalse_IfCustomerIsMissing() {
		assertThat(customerRepository.exists(new UniqueName("TEST_CUSTOMER_2")), is(false));
	}

	@Test
	public void CustomerExists_ShouldReturnTrue_IfCustomerExists() {
		assertThat(customerRepository.exists(new UniqueName("TEST_CUSTOMER")), is(true));
	}


	@Test
	public void Save_ShouldSaveEntity() {
		Customer customer = new Customer(new UniqueName("TEST_CUSTOMER_2"));
		customerRepository.saveAndFlush(customer);
		assertThat(customerRepository.exists(new UniqueName("TEST_CUSTOMER_2")), is(true));
	}

}
