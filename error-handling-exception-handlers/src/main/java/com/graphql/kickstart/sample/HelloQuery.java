package com.graphql.kickstart.sample;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.kickstart.spring.error.ErrorContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Component
class HelloQuery implements GraphQLQueryResolver {

  public String hello() {
    throw new IllegalStateException("Test exception");
  }

  @ExceptionHandler(value = IllegalStateException.class)
  public GraphQLError toCustomError(IllegalStateException e, ErrorContext errorContext) {
    Map<String, Object> extensions = Optional
        .ofNullable(errorContext.getExtensions()).orElseGet(HashMap::new);
    extensions.put("my-custom-code", "some-custom-error");
    return GraphqlErrorBuilder.newError()
        .message(e.getMessage())
        .extensions(extensions)
        .errorType(errorContext.getErrorType())
        .locations(errorContext.getLocations())
        .path(errorContext.getPath())
        .build();
  }

}
