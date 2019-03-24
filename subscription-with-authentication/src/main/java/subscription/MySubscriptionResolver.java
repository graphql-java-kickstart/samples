package subscription;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class MySubscriptionResolver implements GraphQLSubscriptionResolver {

  private static final Logger log = LoggerFactory.getLogger(MySubscriptionResolver.class);

  private MyPublisher publisher = new MyPublisher();

  Publisher<Integer> hello() {
    log.info("Subscribe");
    return publisher;
  }

}
