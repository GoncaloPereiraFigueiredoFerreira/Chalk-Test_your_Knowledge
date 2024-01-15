package pt.uminho.di.chalktyk.dtos;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import pt.uminho.di.chalktyk.models.exercises.ExerciseResolution;
import pt.uminho.di.chalktyk.models.users.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@JsonSerialize(using = ListPairStudentExerciseResolution.LPSERSerializer.class)
public class ListPairStudentExerciseResolution extends ArrayList<Pair<Student, ExerciseResolution>> {

    public ListPairStudentExerciseResolution() {
    }

    public ListPairStudentExerciseResolution(@NotNull Collection<? extends Pair<Student, ExerciseResolution>> c) {
        super(c);
    }

    public static class LPSERSerializer extends JsonSerializer<List<Pair<Student, ExerciseResolution>>> {
        @Override
        public void serialize(List<Pair<Student, ExerciseResolution>> pairs, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
            jsonGenerator.writeStartArray();

            for (Pair<Student, ExerciseResolution> pair : pairs) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeObjectField("student", pair.getLeft());
                jsonGenerator.writeObjectField("resolution", pair.getRight());
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
        }
    }
}
