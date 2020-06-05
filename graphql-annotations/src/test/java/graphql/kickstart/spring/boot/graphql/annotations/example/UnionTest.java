package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnionTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Unions should work")
    void testUnionQuery() throws IOException {
        // WHEN
        final GraphQLResponse actual = graphQLTestTemplate.postForResource("union-query.graphql");
        // THEN
        assertThat(actual.get("$.data.pets[0].woof")).isEqualTo("Woof, woof!");
        assertThat(actual.get("$.data.pets[1].meow")).isEqualTo("Meow, meow!");
    }
}
