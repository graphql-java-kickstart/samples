package directives;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLCodeRegistry;
import graphql.schema.GraphQLDirective;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.GraphQLInputObjectField;
import graphql.schema.GraphQLInputObjectType;
import graphql.schema.GraphQLInputType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class RangeDirective implements SchemaDirectiveWiring {

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    environment.getElement().getArguments().forEach(it -> {
      GraphQLInputType unwrappedInputType = Util.unwrapNonNull(it.getType());

      GraphQLDirective directive = it.getDirective("range");
      if (directive != null) {
        applyDirective(directive, environment.getFieldDataFetcher(), environment.getCodeRegistry(),
            environment.getFieldsContainer(), environment.getFieldDefinition());
      }

      if (unwrappedInputType instanceof GraphQLInputObjectType) {
        GraphQLInputObjectType inputObjType = (GraphQLInputObjectType) unwrappedInputType;

        for (GraphQLInputObjectField inputField : inputObjType.getFieldDefinitions()) {
          directive = inputField.getDirective("range");
          if (directive != null) {
            applyDirective(directive, environment.getFieldDataFetcher(), environment.getCodeRegistry(),
                environment.getFieldsContainer(), environment.getFieldDefinition());
          }
        }
      }
    });
    return environment.getElement();
  }

  private void applyDirective(GraphQLDirective directive, DataFetcher fieldDataFetcher, GraphQLCodeRegistry.Builder codeRegistry,
      GraphQLFieldsContainer fieldsContainer, GraphQLFieldDefinition fieldDefinition) {
    double min = (double) directive.getArgument("min").getValue();
    double max = (double) directive.getArgument("max").getValue();

    DataFetcher originalFetcher = fieldDataFetcher;
    DataFetcher dataFetcher = DataFetcherFactories
        .wrapDataFetcher(originalFetcher, ((dataFetchingEnvironment, value) -> {
          if (value instanceof Double && ((double) value < min || (double) value > max)) {
            throw new IllegalArgumentException(
                String.format("Argument value %s is out of range. The range is %s to %s.", value, min, max)
            );
          }
          return value;
        }));

    codeRegistry.dataFetcher(
        fieldsContainer,
        fieldDefinition,
        dataFetcher
    );
  }

}
