package graphql.kickstart.spring.boot.graphql.annotations.example.resolvers;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

/**
 * The GraphQL Annotations library will create the instance itself. Dependency injection is not supported.
 * This class must have a public parameterless constructor. To access Spring Beans, similar ticks are needed as in case
 * of resolvers.
 *
 * @see Queries
 * @see Subscriptions
 */
public class TimerFetcher implements DataFetcher<Publisher<Long>> {

    private final Publisher<Long> timer = Flowable.interval(1, TimeUnit.SECONDS).map(i -> i + 1);

    @Override
    public Publisher<Long> get(final DataFetchingEnvironment environment) {
        return timer;
    }
}
