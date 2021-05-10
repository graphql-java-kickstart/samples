package graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces;

import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Truck implements Vehicle {

  /** Note that you have to repeat the annotations from the interface method! */
  @GraphQLField @GraphQLNonNull private String registrationNumber;

  @GraphQLField @GraphQLNonNull private int cargoWeightCapacity;
}
