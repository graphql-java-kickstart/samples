package hello;

import javax.websocket.server.ServerEndpointConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

class HttpMain {

  private static final int PORT = 8080;

  public static void main(String[] args) throws Exception {
    Server server = new Server();
    ServerConnector connector = new ServerConnector(server);
    connector.setPort(PORT);
    server.addConnector(connector);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(HelloServlet.class, "/graphql");
    server.setHandler(context);

    WebSocketServerContainerInitializer.configure(
        context,
        (servletContext, serverContainer) -> {
          serverContainer.addEndpoint(
              ServerEndpointConfig.Builder.create(SubscriptionEndpoint.class, "/subscriptions")
                  .configurator(new GraphQLWSEndpointConfigurer())
                  .build());
        });

    server.setHandler(context);

    server.start();
    server.dump(System.err);
    server.join();
  }
}
