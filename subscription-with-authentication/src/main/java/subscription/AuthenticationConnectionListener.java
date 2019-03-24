package subscription;

import graphql.servlet.ApolloSubscriptionConnectionListener;
import graphql.servlet.SubscriptionException;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class AuthenticationConnectionListener implements ApolloSubscriptionConnectionListener {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationConnectionListener.class);

  public Optional<Object> onConnect(Object payload) {
    log.debug("onConnect with payload {}", payload.getClass());
    String token = ((Map<String, String>) payload).get("authToken");
    log.info("Token: {}", token);
    return Optional.of(token);
  }

}
