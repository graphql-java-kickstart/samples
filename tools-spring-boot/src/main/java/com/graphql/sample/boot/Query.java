package com.graphql.sample.boot;

import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;

@Component
class Query implements GraphQLQueryResolver {

  CompletableFuture<Post> getPost(Long id) {
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return new Post(id);
        });
  }
}
