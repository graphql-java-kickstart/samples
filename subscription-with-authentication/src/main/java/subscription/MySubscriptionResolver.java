package subscription;

import com.coxautodev.graphql.tools.GraphQLSubscriptionResolver;
import java.util.concurrent.Flow.Publisher;
import org.springframework.stereotype.Service;

@Service
class MySubscriptionResolver implements GraphQLSubscriptionResolver {

  private MyPublisher publisher = new MyPublisher();

  Publisher<Integer> hello() {
    return publisher;
  }

}
