package hello.resolvers;

import graphql.kickstart.tools.GraphQLMutationResolver;
import hello.types.Human;
import java.util.Map;
import java.util.UUID;

public class Mutation implements GraphQLMutationResolver {

  public Human createHuman(Map<String, String> createHumanInput) {
    String name = null;
    if (createHumanInput.containsKey("name")) {
      name = createHumanInput.get("name");
    }
    String homePlanet = "Jakku";
    if (createHumanInput.containsKey("homePlanet")) {
      homePlanet = createHumanInput.get("homePlanet");
    }
    return new Human(UUID.randomUUID().toString(), name, null, homePlanet);
  }
}
