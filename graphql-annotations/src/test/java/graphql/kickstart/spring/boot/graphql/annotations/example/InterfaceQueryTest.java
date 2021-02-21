package graphql.kickstart.spring.boot.graphql.annotations.example;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Car;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Truck;
import graphql.kickstart.spring.boot.graphql.annotations.example.model.interfaces.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InterfaceQueryTest {

    @Autowired
    private GraphQLTestTemplate graphQLTestTemplate;

    @Test
    @DisplayName("Test query that uses interface as return type.")
    void testInterfaceQuery() throws IOException {
        // GIVEN
        final Vehicle car = Car.builder().numberOfSeats(4).registrationNumber("ABC-123").build();
        final Vehicle truck = Truck.builder().cargoWeightCapacity(12).registrationNumber("CBA-321").build();
        // WHEN - THEN
        graphQLTestTemplate.postForResource("interface-query.graphql")
            .assertThatField("$.data.vehicles[0]").as(Car.class).isEqualTo(car)
            .and().assertThatField("$.data.vehicles[1]").as(Truck.class).isEqualTo(truck);
    }
}
