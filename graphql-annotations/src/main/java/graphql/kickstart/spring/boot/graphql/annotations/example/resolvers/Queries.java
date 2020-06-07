package graphql.kickstart.spring.boot.graphql.annotations.example.resolvers;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.graphql.annotations.GraphQLQueryResolver;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Car;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Truck;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Vehicle;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.type.Person;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.unions.Cat;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.unions.Dog;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.unions.Pet;
import graphql.kickstart.spring.boot.graphql.annotations.example.repository.PersonRepository;
import graphql.kickstart.spring.boot.graphql.annotations.example.services.HelloService;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This is our query resolver. Unfortunately, GraphQL Java Annotations does not support dependency injection. It either
 * requires query resolvers to be static, or if instance methods are used - which must be annotated with
 * {@link graphql.annotations.annotationTypes.GraphQLInvokeDetached} - it creates the instance itself. For this, it
 * requires that our class has a parameterless constructor. Thus, we have to store our beans in static variables.
 *
 * Furthermore, all query resolvers must be implemented in a single class.
 */
@Service
@NoArgsConstructor
@GraphQLQueryResolver
public class Queries implements ApplicationContextAware {

    private static HelloService helloService;
    private static PersonRepository personRepository;

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Say hello to anybody, or the whole world!")
    public static String hello(final @Nullable String who) {
        Objects.requireNonNull(helloService, "Must only be called after spring context is initialized.");
        return helloService.sayHello(Optional.ofNullable(who).orElse("World"));
    }

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Returns all people in the database.")
    public static List<Person> people() {
        return personRepository.findAll();
    }

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Returns pets")
    public static List<Pet> pets() {
        return List.of(new Dog(), new Cat());
    }

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Returns vehicles")
    public static List<Vehicle> vehicles() {
        return List.of(new Car("ABC-123", 4), new Truck("CBA-321", 12));
    }

    @Override
    public void setApplicationContext(final @NonNull ApplicationContext applicationContext) throws BeansException {
        helloService = applicationContext.getBean(HelloService.class);
        personRepository = applicationContext.getBean(PersonRepository.class);
    }
}
