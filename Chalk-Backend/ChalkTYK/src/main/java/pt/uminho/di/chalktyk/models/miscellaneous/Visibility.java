package pt.uminho.di.chalktyk.models.miscellaneous;

import java.io.IOException;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = Visibility.CaseInsensitiveEnumDeserializer.class)
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
		return value;
	}

	@JsonCreator
	public static Visibility fromValue(String s) {
		if(s == null)
			throw new IllegalArgumentException();
		s = s.toUpperCase();
		return Visibility.valueOf(s);
	}

	public boolean isValid(){
        return Arrays.asList(Visibility.values()).contains(this);
	}

	public static class CaseInsensitiveEnumDeserializer extends JsonDeserializer<Visibility> {
		@Override
		public Visibility deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException {
			String value = p.getValueAsString().toUpperCase(); // Convert to uppercase for case-insensitivity
			return Visibility.valueOf(value);
		}
	}
}