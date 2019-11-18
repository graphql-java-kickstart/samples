package webflux;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
class HelloQuery implements GraphQLQueryResolver {

  public String hello() {
    return "Hello world";
  }

}
