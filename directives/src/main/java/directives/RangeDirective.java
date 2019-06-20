package directives;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetcherFactories;
import graphql.schema.GraphQLArgument;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class RangeDirective implements SchemaDirectiveWiring {

  @Override
  public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> environment) {
    double min = (double) environment.getDirective().getArgument("min").getValue();
    double max = (double) environment.getDirective().getArgument("max").getValue();

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
    return environment.getElement();
  }
}
