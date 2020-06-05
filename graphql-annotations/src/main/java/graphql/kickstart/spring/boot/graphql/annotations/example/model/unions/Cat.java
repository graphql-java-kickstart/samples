package graphql.kickstart.spring.boot.graphql.annotations.example.model.unions;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Cat implements Pet {

    @GraphQLField
    @GraphQLNonNull
    public String meow() {
        return "Meow, meow!";
    }
}
