package hello;

import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.kickstart.tools.SchemaParser;
import graphql.schema.GraphQLSchema;
import hello.resolvers.Mutation;
import hello.resolvers.Query;

public class HelloTools {

  public static void main(String[] args) {
    GraphQLSchema graphQLSchema =
        SchemaParser.newParser()
            .file("swapi.graphqls")
            .resolvers(new Query(), new Mutation())
            .build()
            .makeExecutableSchema();

    GraphQL graphQL = GraphQL.newGraphQL(graphQLSchema).build();

    ExecutionResult executionResult = graphQL.execute("{ droid(id: \"2001\") { name } } ");

    System.out.println("Query result: ");
    System.out.println(executionResult.toSpecification());
  }
}
