package graphql.servlet.examples.dataloader.requestscope;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import graphql.kickstart.execution.context.GraphQLKickstartContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

  private final CustomerRepository customerRepository;

  public CustomGraphQLContextBuilder(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  public GraphQLKickstartContext build(HttpServletRequest req, HttpServletResponse response) {
    return GraphQLKickstartContext.of(buildDataLoaderRegistry());
  }

  @Override
  public GraphQLKickstartContext build() {
    return GraphQLKickstartContext.of(buildDataLoaderRegistry());
  }

  @Override
  public GraphQLKickstartContext build(Session session, HandshakeRequest request) {
    return GraphQLKickstartContext.of(buildDataLoaderRegistry());
  }

  private DataLoaderRegistry buildDataLoaderRegistry() {
    DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
    DataLoader<Integer, String> customerLoader =
        new DataLoader<>(
            customerIds -> supplyAsync(() -> customerRepository.getUserNamesForIds(customerIds)));
    dataLoaderRegistry.register("customerDataLoader", customerLoader);
    return dataLoaderRegistry;
  }
}
