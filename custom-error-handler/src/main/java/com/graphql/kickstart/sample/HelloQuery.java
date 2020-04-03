package com.graphql.kickstart.sample;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
class HelloQuery implements GraphQLQueryResolver {

  public String hello() {
    throw new IllegalStateException("Test exception");
  }

}
