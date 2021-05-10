package graphql.kickstart.spring.boot.graphql.annotations.example.resolvers;

import graphql.annotations.annotationTypes.GraphQLDataFetcher;
import graphql.annotations.annotationTypes.GraphQLField;
import graphql.annotations.annotationTypes.GraphQLNonNull;
import graphql.kickstart.graphql.annotations.GraphQLSubscriptionResolver;
import lombok.extern.slf4j.Slf4j;

/**
 * Same restrictions/considerations apply as for the query resolver.
 * @see Queries
 */
@GraphQLSubscriptionResolver
@Slf4j
public class Subscriptions {

    /**
     * The subscription resolver should *not* return a {@link org.reactivestreams.Publisher} by itself. Instead,
     * it should specify a {@link graphql.schema.DataFetcher} implementation using the
     * {@link GraphQLDataFetcher} annotations.
     *
     * This method will never be called and the return value is ignored. It is fine to return
     * null here, even if the resolver is marked as non-null.
     *
     * @return dummy value, actual value provided by {@link TimerFetcher}.
     */
    @GraphQLField
    @GraphQLNonNull
    @GraphQLDataFetcher(TimerFetcher.class)
    public static Long timer() {
        log.info("This method will never be called.");
        return null;
    }
}
