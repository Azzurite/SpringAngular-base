package name.azzurite.baseproject.domain.repository;

import name.azzurite.baseproject.domain.entity.Customer;
import name.azzurite.baseproject.domain.entity.component.UniqueName;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends TypeSafeJPARepository<Customer, UniqueName> {

}
