package name.azzurite.baseproject.domain.entity.transfer;

public class CustomerTO {

	public CustomerTO() {
	}
	public CustomerTO(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	private String uniqueName;
	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}
	public String getUniqueName() {
		return uniqueName;
	}
}
