package com.graphql.sample.boot;

import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
class Query implements GraphQLQueryResolver {

  /**
   * By returning the non-blocking <tt>CompletableFuture</tt> the system can cancel this GraphQL
   * execution when a timeout occurs.
   *
   * @return non-blocking <tt>CompletableFuture</tt> that will return the <tt>String</tt> value
   */
  CompletableFuture<String> getHello() {
    log.info("Executing query");
    return CompletableFuture.supplyAsync(
        () -> {
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
          return "world";
        });
  }

  /**
   * This blocking resolver cannot be cancelled and will block the (main) thread until execution has
   * finished.
   *
   * @return <tt>String</tt> value
   */
  String getWorld() {
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return "hello";
  }
}
