package hello;

import static graphql.schema.idl.RuntimeWiring.newRuntimeWiring;

import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.SubscriptionExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.servlet.DefaultExecutionStrategyProvider;
import graphql.servlet.DefaultGraphQLSchemaProvider;
import graphql.servlet.ExecutionStrategyProvider;
import graphql.servlet.GraphQLInvocationInputFactory;
import graphql.servlet.GraphQLObjectMapper;
import graphql.servlet.GraphQLQueryInvoker;
import graphql.servlet.SimpleGraphQLHttpServlet;
import javax.servlet.annotation.WebServlet;

@WebServlet(name = "HelloServlet", urlPatterns = {"graphql"}, loadOnStartup = 1)
public class HelloServlet extends SimpleGraphQLHttpServlet {

  public HelloServlet() {
    super(invocationInputFactory(), queryInvoker(), objectMapper(), null, false);
  }

  private static GraphQLInvocationInputFactory invocationInputFactory() {
    return GraphQLInvocationInputFactory
        .newBuilder(new DefaultGraphQLSchemaProvider(createSchema()))
        .build();
  }

  private static GraphQLSchema createSchema() {
    String schema = "type Query{hello: String}";

    SchemaParser schemaParser = new SchemaParser();
    TypeDefinitionRegistry typeDefinitionRegistry = schemaParser.parse(schema);

    RuntimeWiring runtimeWiring = newRuntimeWiring()
        .type("Query", builder -> builder.dataFetcher("hello", new StaticDataFetcher("world")))
        .build();

    SchemaGenerator schemaGenerator = new SchemaGenerator();
    return schemaGenerator.makeExecutableSchema(typeDefinitionRegistry, runtimeWiring);
  }

  private static GraphQLQueryInvoker queryInvoker() {
    return GraphQLQueryInvoker.newBuilder()
        .withExecutionStrategyProvider(executionStrategyProvider())
        .build();
  }

  private static ExecutionStrategyProvider executionStrategyProvider() {
    return new DefaultExecutionStrategyProvider(new AsyncExecutionStrategy(), null,
        new SubscriptionExecutionStrategy());
  }

  private static GraphQLObjectMapper objectMapper() {
    return GraphQLObjectMapper.newBuilder().build();
  }
}
