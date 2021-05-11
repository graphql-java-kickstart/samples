package subscription;

import graphql.kickstart.execution.subscriptions.SubscriptionSession;
import graphql.kickstart.execution.subscriptions.apollo.ApolloSubscriptionConnectionListener;
import graphql.kickstart.execution.subscriptions.apollo.OperationMessage;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
class AuthenticationConnectionListener implements ApolloSubscriptionConnectionListener {

  private static final Logger log = LoggerFactory.getLogger(AuthenticationConnectionListener.class);

  public void onConnect(SubscriptionSession session, OperationMessage message) {
    log.debug("onConnect with payload {}", message.getPayload().getClass());
    String token = ((Map<String, String>) message.getPayload()).get("authToken");
    log.info("Token: {}", token);
    Authentication authentication = new UsernamePasswordAuthenticationToken(token, null);
    session.getUserProperties().put("CONNECT_TOKEN", authentication);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }
}
