package directives;

import graphql.kickstart.tools.boot.SchemaDirective;
import graphql.schema.idl.SchemaDirectiveWiring;
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

//  @Bean
//  public SchemaDirective rangeDirective() {
//    return new SchemaDirective("range", new RangeDirective());
//  }

  @Bean
  public SchemaDirectiveWiring directiveWiring() {
    return new RangeDirective();
  }

//  @Bean
//  public SchemaDirectiveWiring validationSchemaDirective() {
//    ValidationRules validationRules = ValidationRules.newValidationRules()
//        .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
//        .build();
//    return new ValidationSchemaWiring(validationRules);
//  }

}
