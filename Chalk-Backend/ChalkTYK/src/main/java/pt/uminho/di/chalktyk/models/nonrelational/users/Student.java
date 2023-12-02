package pt.uminho.di.chalktyk.models.nonrelational.users;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.uminho.di.chalktyk.models.nonrelational.subscriptions.Subscription;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "users")
@JsonTypeName("student")
@TypeAlias("student")
public class Student extends User {

    public Student(String id, String name, String photoPath, String email, String description, Subscription subscription) {
        super(id, name, photoPath, email, description, subscription);
    }

    /**
     * Check if the subscription is valid.
     *
     * @return 'null' if the subscription is valid, or a string mentioning the error.
     */
    @Override
    public String checkSubscription() {
        //TODO - checkSubscription
        return null;
    }

    @Override
    public String toString() {
        return "Student{" +
                    super.toString()
                + "}";
    }
}