package name.azzurite.baseproject.web.rest.resource;

import name.azzurite.baseproject.domain.entity.component.UniqueName;
import name.azzurite.baseproject.domain.entity.transfer.CustomerTO;
import name.azzurite.baseproject.web.rest.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/rest/customers")
public class CustomerResource {

//	private static final Logger log = LogManager.getLogger(CustomerResource.class);

	private final CustomerService customerService;


	@Inject
	public CustomerResource(CustomerService customerService) {
		this.customerService = customerService;

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody CustomerTO customer) {
		HttpStatus statusCode;
		if (customerService.find(customer.getUniqueName()).isPresent()) {
			statusCode = HttpStatus.OK;
		} else {
			statusCode = HttpStatus.CREATED;
		}
		return new ResponseEntity<>(customerService.save(customer), statusCode);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<CustomerTO> customers = customerService.getAll();
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}

	@RequestMapping(value = "/{uniqueName}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomer(@PathVariable UniqueName uniqueName) {
		customerService.delete(uniqueName);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
