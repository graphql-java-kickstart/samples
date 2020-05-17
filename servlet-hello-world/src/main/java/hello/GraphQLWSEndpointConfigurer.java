package hello;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class GraphQLWSEndpointConfigurer extends ServerEndpointConfig.Configurator {

  private final SubscriptionEndpoint endpoint;

  public GraphQLWSEndpointConfigurer() {
    endpoint = new SubscriptionEndpoint();
  }

  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    endpoint.modifyHandshake(sec, request, response);
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T getEndpointInstance(Class<T> endpointClass) {
    return (T) this.endpoint;
  }

}
