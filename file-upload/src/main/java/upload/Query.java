package upload;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;

@Service
class Query implements GraphQLQueryResolver {

   // This query is necessary because the graphql-java-tool requires at least one query
   public String getHello() {
      return "Hello World";
   }
}
