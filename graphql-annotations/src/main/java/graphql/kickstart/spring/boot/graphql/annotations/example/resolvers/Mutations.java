package graphql.kickstart.spring.boot.graphql.annotations.example.resolvers;

import graphql.annotations.annotationTypes.GraphQLDescription;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.graphql.annotations.GraphQLMutationResolver;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.input.CreatePerson;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.type.Person;
import graphql.kickstart.spring.boot.graphql.annotations.example.repository.PersonRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Same restrictions / considerations apply as for the query resolver.
 * @see Queries
 */
@Service
@NoArgsConstructor
@GraphQLMutationResolver
@Slf4j
public class Mutations implements ApplicationContextAware {

    private static PersonRepository personRepository;

    @GraphQLField
    @GraphQLNonNull
    @GraphQLDescription("Creates a new person.")
    public static Person createPerson(final @GraphQLNonNull CreatePerson createPerson) {
        log.info("Create person input: {}", createPerson);
        final Person newPerson = Person.builder()
            .firstName(createPerson.getFirstName())
            .lastName(createPerson.getLastName())
            .dateOfBirth(createPerson.getDateOfBirth())
            .id(String.valueOf(UUID.randomUUID()))
            .build();
        log.info("Saving new person: {}", newPerson);
        return personRepository.save(newPerson);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        personRepository = applicationContext.getBean(PersonRepository.class);
    }
}
