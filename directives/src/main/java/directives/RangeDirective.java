package directives;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class RangeDirective implements SchemaDirectiveWiring {

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    environment.getElement().getArguments().forEach(it -> {
      apply(it, environment);

      GraphQLInputType unwrappedInputType = Util.unwrapNonNull(it.getType());
      if (unwrappedInputType instanceof GraphQLInputObjectType) {
        GraphQLInputObjectType inputObjType = (GraphQLInputObjectType) unwrappedInputType;

        for (GraphQLInputObjectField inputField : inputObjType.getFieldDefinitions()) {
          apply(inputField, environment);
        }
      }
    });
    return environment.getElement();
  }

  private void apply(GraphQLDirectiveContainer directiveContainer, SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    GraphQLDirective directive = directiveContainer.getDirective("range");
    if (directive != null) {
      applyDirective(directive, environment);
    }
  }

  private void applyDirective(GraphQLDirective directive, SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

    double min = (double) directive.getArgument("min").getValue();
    double max = (double) directive.getArgument("max").getValue();

    DataFetcher originalFetcher = environment.getFieldDataFetcher();
    DataFetcher dataFetcher = DataFetcherFactories
        .wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
          if (value instanceof Double && ((double) value < min || (double) value > max)) {
            throw new IllegalArgumentException(
                String.format("Argument value %s is out of range. The range is %s to %s.", value, min, max)
            );
          }
          return value;
        }));

    environment.getCodeRegistry().dataFetcher(
        environment.getFieldsContainer(),
        environment.getFieldDefinition(),
        dataFetcher
    );
  }

}
