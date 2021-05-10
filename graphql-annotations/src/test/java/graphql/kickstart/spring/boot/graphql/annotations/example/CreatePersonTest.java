package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.input.CreatePerson;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.type.Person;
import graphql.kickstart.spring.boot.graphql.annotations.example.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class CreatePersonTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.parse("1901-02-03");

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static Stream<Arguments> createPersonArguments() {
        return Stream.of(
            Arguments.of(
                CreatePerson.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(DATE_OF_BIRTH).build(),
                Person.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(DATE_OF_BIRTH).build()
            ),
            Arguments.of(
                CreatePerson.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(null).build(),
                Person.builder().firstName(FIRST_NAME).lastName(LAST_NAME).dateOfBirth(null).build()
            )
        );
    }

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @ParameterizedTest
    @MethodSource("createPersonArguments")
    @DisplayName("Should create a person using the provided arguments.")
    void testCreatePerson(
        final CreatePerson input,
        final Person expected
    ) throws IOException {
        // GIVEN
        final ObjectNode params = objectMapper.createObjectNode();
        params.set("createPersonInput", objectMapper.valueToTree(input));
        log.info("Create person variables: {}", params);
        // WHEN - THEN
        graphQLTestTemplate.perform("create-person.graphql", params)
            .assertThatNoErrorsArePresent()
            .assertThatField("$.data.createPerson.id").asString()
                .isEqualTo(personRepository.findAll().stream().findFirst().map(Person::getId).map(String::toUpperCase)
                    .orElseThrow())
            .and().assertThatField("$.data.createPerson").as(Person.class)
                .as("Should create the expected person.")
                .usingRecursiveComparison()
                .ignoringAllOverriddenEquals()
                .ignoringFields("id")
                .isEqualTo(expected)
                .isEqualTo(personRepository.findAll().stream().findFirst().orElseThrow());
        // THEN
        assertThat(personRepository.count()).as("Should create only one person.").isOne();
    }
}
