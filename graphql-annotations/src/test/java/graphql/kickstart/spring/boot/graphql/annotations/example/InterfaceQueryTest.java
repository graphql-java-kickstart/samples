package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Car;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Truck;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterfaceQueryTest {

    private static final String INTERFACE_QUERY = "interface-query.graphql";

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Test query that uses interface as return type.")
    void testInterfaceQuery() throws IOException {
        // WHEN
        final GraphQLResponse actual = graphQLTestTemplate.postForResource(INTERFACE_QUERY);
        // THEN
        assertThat(actual.get("$.data.vehicles[0]", Car.class))
            .usingRecursiveComparison().ignoringAllOverriddenEquals()
            .isEqualTo(Car.builder().numberOfSeats(4).registrationNumber("ABC-123").build());
        assertThat(actual.get("$.data.vehicles[1]", Truck.class))
            .usingRecursiveComparison().ignoringAllOverriddenEquals()
            .isEqualTo(Truck.builder().cargoWeightCapacity(12).registrationNumber("CBA-321").build());
    }
}
