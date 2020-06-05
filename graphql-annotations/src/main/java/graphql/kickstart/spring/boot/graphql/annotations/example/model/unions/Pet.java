package graphql.kickstart.spring.boot.graphql.annotations.example.model.unions;

import graphql.annotations.annotationTypes.GraphQLUnion;

@GraphQLUnion(possibleTypes = {Cat.class, Dog.class})
public interface Pet {
}
