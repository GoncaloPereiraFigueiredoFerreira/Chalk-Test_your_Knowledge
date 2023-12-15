package pt.uminho.di.chalktyk.models.relational;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Gets or Sets Visibility
 */
public enum VisibilitySQL {
	PUBLIC("public"),
	NOT_LISTED("not_listed"),
	PRIVATE("private"),
	COURSE("course"),
	INSTITUTION("institution"),
	DELETED("deleted"),
	TEST("test");

	private String value;

	VisibilitySQL(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static VisibilitySQL fromValue(String text) {
		for (VisibilitySQL b : VisibilitySQL.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}
}