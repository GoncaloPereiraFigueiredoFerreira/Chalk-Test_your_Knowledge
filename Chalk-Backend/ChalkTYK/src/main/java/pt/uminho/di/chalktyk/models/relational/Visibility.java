package pt.uminho.di.chalktyk.models.relational;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

/**
 * Gets or Sets Visibility
 */
public enum Visibility {
	PUBLIC("public"),
	NOT_LISTED("not_listed"),
	PRIVATE("private"),
	COURSE("course"),
	INSTITUTION("institution");

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
}