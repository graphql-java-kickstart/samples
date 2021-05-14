package graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.annotations.annotationTypes.GraphQLTypeResolver;
import graphql.kickstart.annotations.GraphQLInterfaceTypeResolver;

@GraphQLTypeResolver(GraphQLInterfaceTypeResolver.class)
public interface Vehicle {

  @GraphQLField
  @GraphQLNonNull
  String getRegistrationNumber();
}
