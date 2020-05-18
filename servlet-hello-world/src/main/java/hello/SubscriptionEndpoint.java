package hello;

import graphql.kickstart.servlet.GraphQLWebsocketServlet;

public class SubscriptionEndpoint extends GraphQLWebsocketServlet {

  public SubscriptionEndpoint() {
    super(GraphQLConfigurationProvider.getInstance().getConfiguration());
  }

}
