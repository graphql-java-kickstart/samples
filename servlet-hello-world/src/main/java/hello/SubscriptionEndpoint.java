package hello;

import graphql.kickstart.servlet.GraphQLWebsocketServlet;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/graphql")
public class SubscriptionEndpoint extends GraphQLWebsocketServlet {

  public SubscriptionEndpoint() {
    super(GraphQLConfigurationProvider.getInstance().getConfiguration());
  }

}
