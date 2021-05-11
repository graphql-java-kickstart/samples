package com.graphql.kickstart.sample;

import graphql.GraphQLError;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class CustomGraphQLErrorHandler implements GraphQLErrorHandler {

  @Override
  public List<GraphQLError> processErrors(List<GraphQLError> list) {
    log.info("Handle errors: {}", list);
    return list;
  }
}
