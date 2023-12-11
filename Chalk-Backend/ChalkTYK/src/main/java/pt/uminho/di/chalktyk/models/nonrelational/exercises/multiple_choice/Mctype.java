package pt.uminho.di.chalktyk.models.nonrelational.exercises.multiple_choice;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.Setter;

/*
    -1X -> multiple choice
    -2X -> true or false

    -X0 -> no justification
    -X1 -> justify all items
    -X2 -> justify false/unmarked items
    -X3 -> justify true/marked items
*/
@Getter
public enum Mctype {
    MULTIPLE_CHOICE_NO_JUSTIFICATION(10),
    MULTIPLE_CHOICE_JUSTIFY_ALL(11),
    MULTIPLE_CHOICE_JUSTIFY_FALSE_UNMARKED(12),
    MULTIPLE_CHOICE_JUSTIFY_TRUE_MARKED(13),
    TRUE_FALSE_NO_JUSTIFICATION(20),
    TRUE_FALSE_JUSTIFY_ALL(21),
    TRUE_FALSE__JUSTIFY_FALSE_UNMARKED(22),
    TRUE_FALSE_JUSTIFY_TRUE_MARKED(23);
    private int code;
    Mctype(int code) {
        this.code = code;
    }
}