package name.azzurite.baseproject.web.rest.service;

import name.azzurite.baseproject.domain.entity.Customer;
import name.azzurite.baseproject.domain.entity.component.UniqueName;
import name.azzurite.baseproject.domain.entity.transfer.CustomerTO;
import name.azzurite.baseproject.domain.repository.CustomerRepository;
import org.dozer.Mapper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final Mapper mapper;

	@Inject
	public CustomerService(CustomerRepository customerRepository, Mapper mapper) {
		this.customerRepository = customerRepository;
		this.mapper = mapper;
	}

	public List<CustomerTO> getAll() {
		List<Customer> customers = customerRepository.findAll();
		return customers.stream().map((customer) -> mapper.map(customer, CustomerTO.class))
						.collect(Collectors.toList());
	}

	public Optional<CustomerTO> find(String uniqueName) {
		Optional<Customer> customer = customerRepository.findOne(new UniqueName(uniqueName));
		if (!customer.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(mapper.map(customer.get(), CustomerTO.class));
	}


	public CustomerTO save(CustomerTO customerTO) {
		Customer customer = mapper.map(customerTO, Customer.class);
		return mapper.map(customerRepository.saveAndFlush(customer), CustomerTO.class);
	}

	public void delete(UniqueName uniqueName) {
		customerRepository.delete(uniqueName);
	}
}
