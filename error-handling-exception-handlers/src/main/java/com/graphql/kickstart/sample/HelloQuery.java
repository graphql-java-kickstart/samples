package com.graphql.kickstart.sample;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
class HelloQuery implements GraphQLQueryResolver {

  public String hello() {
    throw new IllegalStateException("Test exception");
  }

  @ExceptionHandler(value = IllegalStateException.class)
  public GraphQLError toCustomError(IllegalStateException e) {
    return GraphqlErrorBuilder.newError()
        .message(e.getMessage())
        .extensions(Map.of("code", "my-custom-exception-code"))
        .build();
  }

}
