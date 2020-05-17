package hello;

import graphql.kickstart.servlet.GraphQLWebsocketServlet;

//@ServerEndpoint(value = "/subscriptions", configurator = GraphQLWSEndpointConfigurer.class)
public class SubscriptionEndpoint extends GraphQLWebsocketServlet {

  public SubscriptionEndpoint() {
    super(GraphQLConfigurationProvider.getInstance().getConfiguration());
  }

//  @Override
//  @OnOpen
//  public void onOpen(Session session, EndpointConfig endpointConfig) {
//    super.onOpen(session, endpointConfig);
//  }
//
//  @Override
//  @OnClose
//  public void onClose(Session session, CloseReason closeReason) {
//    super.onClose(session, closeReason);
//  }
//
//  @Override
//  @OnError
//  public void onError(Session session, Throwable thr) {
//    super.onError(session, thr);
//  }

}
