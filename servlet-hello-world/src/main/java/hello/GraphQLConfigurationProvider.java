package hello;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.SubscriptionExecutionStrategy;
import graphql.kickstart.execution.GraphQLQueryInvoker;
import graphql.kickstart.execution.config.DefaultExecutionStrategyProvider;
import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import java.time.Duration;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

class GraphQLConfigurationProvider {

  private static GraphQLConfigurationProvider instance;

  private GraphQLConfiguration configuration;

  private GraphQLConfigurationProvider() {
    configuration = GraphQLConfiguration
        .with(createSchema())
        .with(GraphQLQueryInvoker.newBuilder()
            .withExecutionStrategyProvider(new DefaultExecutionStrategyProvider(
                new AsyncExecutionStrategy(),
                null,
                new SubscriptionExecutionStrategy()))
            .build())
        .build();
  }

  static GraphQLConfigurationProvider getInstance() {
    if (instance == null) {
      instance = new GraphQLConfigurationProvider();
    }
    return instance;
  }

  GraphQLConfiguration getConfiguration() {
    return configuration;
  }

  private GraphQLSchema createSchema() {
    String schema = "type Query{hello: String} "
        + "type Subscription{ping: String}";

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    DataFetcher<Publisher<String>> publisherDataFetcher =
        environment -> Flux.just("pong").repeat().delayElements(Duration.ofSeconds(1));

    RuntimeWiring runtimeWiring = newRuntimeWiring()
        .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
        .type("Subscription", builder -> builder.dataFetcher("ping", publisherDataFetcher))
        .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }

}
