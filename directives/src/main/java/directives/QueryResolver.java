package directives;

import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver implements GraphQLQueryResolver {

  public String hello(String value) {
    return value;
  }

  public double limitedValue(double value) {
    return value;
  }

  public int limitedInt(int value) {
    return value;
  }

  public double withInput(InputObject input) {
    return input.getValue();
  }
}
