package pt.uminho.di.chalktyk.models.tests;

public enum TestResolutionStatus {
	ONGOING("ongoing"),
	NOT_REVISED("not_revised"),
	REVISED("revised"),
	INVALIDATED("invalidated");

	private String value;

	TestResolutionStatus(String value) {
		this.value = value;
	}

	public String toString() {
		return String.valueOf(value);
	}
}