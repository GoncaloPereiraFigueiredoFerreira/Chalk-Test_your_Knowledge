package pt.uminho.di.chalktyk.models.miscellaneous;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Visibility {
	PUBLIC("public"),
	NOT_LISTED("not_listed"),
	PRIVATE("private"),
	COURSE("course"),
	INSTITUTION("institution"),
	DELETED("deleted"),
	TEST("test");

	private String value;

	Visibility(String value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public String toString() {
		return String.valueOf(value);
	}

	@JsonCreator
	public static Visibility fromValue(String text) {
		for (Visibility b : Visibility.values()) {
			if (String.valueOf(b.value).equals(text)) {
				return b;
			}
		}
		return null;
	}

	public boolean isValid(){
		if (Arrays.stream(Visibility.values()).anyMatch(this::equals))
			return true;
		else
			return false;
	}
}