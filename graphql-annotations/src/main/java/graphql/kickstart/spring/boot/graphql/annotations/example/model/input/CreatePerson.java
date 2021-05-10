package graphql.kickstart.spring.boot.graphql.annotations.example.model.input;

import graphql.annotations.annotationTypes.GraphQLConstructor;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(onConstructor_ = @GraphQLConstructor)
public class CreatePerson {

  @GraphQLField @GraphQLNonNull private String firstName;

  @GraphQLField @GraphQLNonNull private String lastName;

  @GraphQLField private LocalDate dateOfBirth;
}
