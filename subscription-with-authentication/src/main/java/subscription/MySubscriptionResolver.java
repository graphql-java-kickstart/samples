package subscription;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.servlet.context.GraphQLWebSocketContext;
import graphql.servlet.core.ApolloSubscriptionConnectionListener;
import java.util.Optional;
import javax.websocket.Session;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
class MySubscriptionResolver implements GraphQLSubscriptionResolver {

  private static final Logger log = LoggerFactory.getLogger(MySubscriptionResolver.class);

  private MyPublisher publisher = new MyPublisher();

  Publisher<Integer> hello(DataFetchingEnvironment env) {
    GraphQLWebSocketContext context = env.getContext();
    Optional<String> token = Optional.ofNullable(context.getSession())
        .map(Session::getUserProperties)
        .map(props -> props.get(ApolloSubscriptionConnectionListener.CONNECT_RESULT_KEY))
        .map(String.class::cast);
    log.info("Subscribe to publisher with token: {}", token);
    log.info("Security context principal: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    return publisher;
  }

}
