package directives;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Component;

@Component
public class QueryResolver implements GraphQLQueryResolver {

  public String hello(String value) {
    return value;
  }

  public double limitedValue(double value) {
    return value;
  }
}
