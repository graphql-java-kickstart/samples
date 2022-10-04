package graphql.servlet.examples.dataloader.requestscope;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import graphql.kickstart.execution.context.GraphQLKickstartContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

  private final CustomerRepository customerRepository;

  public CustomGraphQLContextBuilder(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public GraphQLKickstartContext build(HttpServletRequest request, HttpServletResponse response) {
    Map<Object, Object> map = new HashMap<>();
    map.put(HttpServletRequest.class, request);
    map.put(HttpServletResponse.class, response);
    return GraphQLKickstartContext.of(buildDataLoaderRegistry(), map);
  }

  @Override
  public GraphQLKickstartContext build() {
    return GraphQLKickstartContext.of(buildDataLoaderRegistry());
  }

  @Override
  public GraphQLKickstartContext build(Session session, HandshakeRequest handshakeRequest) {
    Map<Object, Object> map = new HashMap<>();
    map.put(Session.class, session);
    map.put(HandshakeRequest.class, handshakeRequest);
    return GraphQLKickstartContext.of(buildDataLoaderRegistry(), map);
  }

  private DataLoaderRegistry buildDataLoaderRegistry() {
    DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
    DataLoader<Integer, String> customerLoader =
        DataLoaderFactory.newDataLoader(
            customerIds -> supplyAsync(() -> customerRepository.getUserNamesForIds(customerIds)));
    dataLoaderRegistry.register("customerDataLoader", customerLoader);
    return dataLoaderRegistry;
  }
}
