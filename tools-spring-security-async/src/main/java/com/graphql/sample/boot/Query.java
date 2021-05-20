package com.graphql.sample.boot;

import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class Query implements GraphQLQueryResolver {

  CompletableFuture<Post> getPost(Long id) {
    log.info("Authentication: {}", SecurityContextHolder.getContext().getAuthentication());
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
