package subscription;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

@Service
class QueryResolver implements GraphQLQueryResolver {

  String hello() {
    return "world";
  }
}
