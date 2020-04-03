package webflux;

import graphql.kickstart.tools.GraphQLQueryResolver;
import java.util.concurrent.CompletableFuture;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
class HelloQuery implements GraphQLQueryResolver {

  public CompletableFuture<String> hello() {
    return Mono.just("Hello world").toFuture();
  }

}
