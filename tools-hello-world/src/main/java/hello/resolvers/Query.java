package hello.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import hello.CharacterRepository;
import hello.types.Character;
import hello.types.Droid;
import hello.types.Episode;
import hello.types.Human;

public class Query implements GraphQLQueryResolver {

  private CharacterRepository characterRepository = new CharacterRepository();

  public Character hero(Episode episode) {
    return episode != null ? characterRepository.getHeroes().get(episode)
        : characterRepository.getCharacters()
            .get("1000");
  }

  public Human human(String id, DataFetchingEnvironment env) {
    return (Human) characterRepository.getCharacters()
        .values()
        .stream()
        .filter(character -> character instanceof Human && character.getId().equals(id))
        .findFirst()
        .orElseThrow(IllegalStateException::new);
  }

  public Droid droid(String id) {
    return (Droid) characterRepository.getCharacters()
        .values()
        .stream()
        .filter(character -> character instanceof Droid && character.getId().equals(id))
        .findFirst()
        .orElseThrow(IllegalStateException::new);
  }

  public Character character(String id) {
    return characterRepository.getCharacters().get(id);
  }
}
