package name.azzurite.baseproject.domain.entity.component;

import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UniqueName implements Serializable {
	public UniqueName() {
	}
	public UniqueName(String uniqueName) {
		setUniqueName(uniqueName);
	}

	private String uniqueName;
	@JsonValue
	public String getUniqueName() {
		return uniqueName;
	}
	public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		UniqueName that = (UniqueName) o;

		return uniqueName.equals(that.uniqueName);
	}

	@Override
	public int hashCode() {
		return uniqueName.hashCode();
	}

	@Override
	public String toString() {
		return "UniqueName{" + uniqueName + '}';
	}
}
