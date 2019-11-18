package webflux;

import graphql.GraphQL;
import graphql.kickstart.execution.GraphQLInvoker;
import graphql.kickstart.execution.GraphQLObjectMapper;
import graphql.kickstart.execution.config.GraphQLBuilder;
import graphql.schema.GraphQLSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GraphQLConfig {

  @Bean
  public GraphQLInvoker graphQLInvoker(GraphQLSchema schema) {
    GraphQL graphQL = new GraphQLBuilder().build(schema);
    return new GraphQLInvoker(graphQL);
  }

  @Bean
  public GraphQLObjectMapper graphQLObjectMapper() {
    return GraphQLObjectMapper.newBuilder().build();
  }

  @Bean
  public GraphQLInvocationInputFactory graphQLInvocationInputFactory() {
    return GraphQLInvocationInputFactory.builder().build();
  }

}
