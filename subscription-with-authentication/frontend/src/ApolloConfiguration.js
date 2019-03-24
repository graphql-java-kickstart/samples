import { split } from 'apollo-link';
import { HttpLink } from 'apollo-link-http';
import { WebSocketLink } from 'apollo-link-ws';
import { getMainDefinition } from 'apollo-utilities';
import { ApolloClient } from 'apollo-client'
import { InMemoryCache } from 'apollo-cache-inmemory'

// Create an http link:
const httpLink = new HttpLink({
  uri: 'http://localhost:9000/graphql'
})

const wsLink = new WebSocketLink({
  uri: `ws://localhost:9000/subscriptions`,
  options: {
    reconnect: true,
    connectionParams: {
      authToken: 'foobar',
    }
  }
});

// using the ability to split links, you can send data to each link
// depending on what kind of operation is being sent
const link = split(
    // split based on operation type
    ({ query }) => {
      const { kind, operation } = getMainDefinition(query);
      return kind === 'OperationDefinition' && operation === 'subscription';
    },
    wsLink,
    httpLink,
)

const client = new ApolloClient({
  link,
  cache: new InMemoryCache()
})

export default client
