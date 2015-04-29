package name.azzurite.baseproject.domain.entity;

import name.azzurite.baseproject.domain.entity.component.UniqueName;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

	public Customer() {
	}

	public Customer(UniqueName uniqueName) {
		this.uniqueName = uniqueName;
	}

	@Id
	@Embedded
	private UniqueName uniqueName;
	public UniqueName getUniqueName() { return uniqueName; }
	public void setUniqueName(UniqueName uniqueName) { this.uniqueName = uniqueName; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Customer customer = (Customer) o;

		return uniqueName.equals(customer.uniqueName);

	}
	@Override
	public int hashCode() {
		return uniqueName.hashCode();
	}
}
