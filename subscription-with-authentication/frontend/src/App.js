import React, { Component } from 'react'
import logo from './logo.svg'
import './App.css'
import gql from 'graphql-tag'
import { ApolloProvider, Subscription } from "react-apollo"
import client from './ApolloConfiguration'

const HELLO_SUBSCRIPTION = gql`
    subscription onHelloIncremented {
        hello
    }
`

class App extends Component {
  render() {
    return (
        <ApolloProvider client={client}>
          <div className="App">
            <header className="App-header">
              <img src={logo} className="App-logo" alt="logo"/>
              <Subscription
                  subscription={HELLO_SUBSCRIPTION}
              >
                {({ data, loading }) => {
                  if (!loading && data && data.hello) {
                    return <p>{data.hello}</p>
                  } else {
                    return <p>Loading...</p>
                  }
                }}
              </Subscription>
              <a
                  className="App-link"
                  href="https://reactjs.org"
                  target="_blank"
                  rel="noopener noreferrer"
              >
                Learn React
              </a>
            </header>
          </div>
        </ApolloProvider>
    )
  }
}

export default App
