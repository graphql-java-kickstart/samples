package directives;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RangeDirective implements SchemaDirectiveWiring {

  @Override
  public GraphQLFieldDefinition onField(
      SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    boolean applies = appliesTo(environment.getElement());

    if (applies) {
      replaceDataFetcher(environment);
    }
    return environment.getElement();
  }

  private boolean appliesTo(GraphQLFieldDefinition fieldDefinition) {
    return fieldDefinition.getArguments().stream()
        .anyMatch(
            it -> {
              if (appliesTo(it)) {
                return true;
              }
              GraphQLInputType unwrappedInputType = Util.unwrapNonNull(it.getType());
              if (unwrappedInputType instanceof GraphQLInputObjectType) {
                GraphQLInputObjectType inputObjType = (GraphQLInputObjectType) unwrappedInputType;
                return inputObjType.getFieldDefinitions().stream().anyMatch(this::appliesTo);
              }
              return false;
            });
  }

  private boolean appliesTo(GraphQLDirectiveContainer container) {
    return container.getDirective("range") != null;
  }

  private void replaceDataFetcher(
      SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    DataFetcher originalFetcher = environment.getFieldDataFetcher();
    DataFetcher newFetcher = createDataFetcher(originalFetcher);
    environment
        .getCodeRegistry()
        .dataFetcher(
            environment.getFieldsContainer(), environment.getFieldDefinition(), newFetcher);
  }

  private DataFetcher createDataFetcher(DataFetcher originalFetcher) {
    return (env) -> {
      List<GraphQLError> errors = new ArrayList<>();
      env.getFieldDefinition().getArguments().stream()
          .forEach(
              it -> {
                if (appliesTo(it)) {
                  errors.addAll(apply(it, env, env.getArgument(it.getName())));
                }

                GraphQLInputType unwrappedInputType = Util.unwrapNonNull(it.getType());
                if (unwrappedInputType instanceof GraphQLInputObjectType) {
                  GraphQLInputObjectType inputObjType = (GraphQLInputObjectType) unwrappedInputType;
                  inputObjType.getFieldDefinitions().stream()
                      .filter(this::appliesTo)
                      .forEach(
                          io -> {
                            Map<String, Object> value = env.getArgument(it.getName());
                            errors.addAll(apply(io, env, value.get(io.getName())));
                          });
                }
              });

      Object returnValue = originalFetcher.get(env);
      if (errors.isEmpty()) {
        return returnValue;
      }
      return Util.mkDFRFromFetchedResult(errors, returnValue);
    };
  }

  private List<GraphQLError> apply(
      GraphQLDirectiveContainer it, DataFetchingEnvironment env, Object value) {
    GraphQLDirective directive = it.getDirective("range");
    double min = (double) directive.getArgument("min").getArgumentValue().getValue();
    double max = (double) directive.getArgument("max").getArgumentValue().getValue();

    if (value instanceof Double && ((double) value < min || (double) value > max)) {
      return singletonList(
          GraphqlErrorBuilder.newError(env)
              .message(
                  String.format(
                      "Argument value %s is out of range. The range is %s to %s.", value, min, max))
              .build());
    }
    return emptyList();
  }
}
