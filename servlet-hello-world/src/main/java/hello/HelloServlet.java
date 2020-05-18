package hello;

import graphql.kickstart.servlet.GraphQLConfiguration;
import graphql.kickstart.servlet.GraphQLHttpServlet;

public class HelloServlet extends GraphQLHttpServlet {

  @Override
  protected GraphQLConfiguration getConfiguration() {
    return GraphQLConfigurationProvider.getInstance().getConfiguration();
  }

}
