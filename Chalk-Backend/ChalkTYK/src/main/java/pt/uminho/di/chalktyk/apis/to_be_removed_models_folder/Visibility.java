package pt.uminho.di.chalktyk.apis.to_be_removed_models_folder;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.annotation.JsonCreator;

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
