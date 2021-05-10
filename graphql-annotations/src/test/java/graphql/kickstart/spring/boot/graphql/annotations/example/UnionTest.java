package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UnionTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Unions should work")
    void testUnionQuery() throws IOException {
        // WHEN - THEN
        graphQLTestTemplate.postForResource("union-query.graphql")
            .assertThatField("$.data.pets[0].woof").asString().isEqualTo("Woof, woof!")
            .and().assertThatField("$.data.pets[1].meow").asString().isEqualTo("Meow, meow!");
    }
}
