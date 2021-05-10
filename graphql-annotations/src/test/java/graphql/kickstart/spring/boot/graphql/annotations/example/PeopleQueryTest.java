package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.type.Person;
import graphql.kickstart.spring.boot.graphql.annotations.example.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PeopleQueryTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return all people.")
    void testPeopleQuery() throws IOException {
        // GIVEN
        final Person person1 = personRepository.save(Person.builder()
            .id(generateId())
            .firstName("John")
            .lastName("Doe")
            .dateOfBirth(LocalDate.parse("1900-01-02"))
            .build());
        final Person person2 = personRepository.save(Person.builder()
            .id(generateId())
            .firstName("Jane")
            .lastName("Doe")
            .dateOfBirth(LocalDate.parse("1900-02-01"))
            .build());
        // WHEN
        graphQLTestTemplate.postForResource("people.graphql")
            .assertThatField("$.data.people").asListOf(Person.class)
                .usingRecursiveFieldByFieldElementComparator().containsExactly(person1, person2)
            .and().assertThatField("$.data.people[*].fullName").asListOf(String.class)
                .containsExactly("Doe, John", "Doe, Jane");
    }

    private String generateId() {
        return String.valueOf(UUID.randomUUID()).toUpperCase(Locale.ENGLISH);
    }
}
