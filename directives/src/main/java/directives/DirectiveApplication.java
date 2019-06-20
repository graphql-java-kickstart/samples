package directives;

import com.oembedler.moon.graphql.boot.SchemaDirective;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DirectiveApplication {

  public static void main(String[] args) {
    SpringApplication.run(DirectiveApplication.class, args);
  }

  @Bean
  public SchemaDirective myCustomDirective() {
    return new SchemaDirective("uppercase", new UppercaseDirective());
  }

  @Bean
  public SchemaDirective rangeDirective() {
    return new SchemaDirective("range", new RangeDirective());
  }

}
