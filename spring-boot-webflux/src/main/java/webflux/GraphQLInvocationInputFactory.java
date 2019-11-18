package webflux;

import graphql.kickstart.execution.DefaultGraphQLRootObjectBuilder;
import graphql.kickstart.execution.GraphQLRequest;
import graphql.kickstart.execution.GraphQLRootObjectBuilder;
import graphql.kickstart.execution.context.DefaultGraphQLContextBuilder;
import graphql.kickstart.execution.context.GraphQLContextBuilder;
import graphql.kickstart.execution.input.GraphQLInvocationInput;
import graphql.kickstart.execution.input.GraphQLSingleInvocationInput;
import java.util.Map;
import java.util.function.Supplier;
import lombok.Builder;
import lombok.Builder.Default;

@Builder
class GraphQLInvocationInputFactory {

  @Default
  private Supplier<GraphQLContextBuilder> contextBuilderSupplier = DefaultGraphQLContextBuilder::new;
  @Default
  private Supplier<GraphQLRootObjectBuilder> rootObjectBuilderSupplier = DefaultGraphQLRootObjectBuilder::new;

  GraphQLSingleInvocationInput create(String query,
      String operationName,
      Map<String, Object> variables) {
    return new GraphQLSingleInvocationInput(
        new GraphQLRequest(query, variables, operationName),
        null,
        contextBuilderSupplier.get().build(),
        rootObjectBuilderSupplier.get()
    );
  }

}
