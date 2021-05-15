# Async timeout

The `graphql.servlet.async-timeout` property enables you to define a request level timeout to be enforced. This can help
in preventing DDoS attacks. This is only available when running the application in `servlet` mode. The value is the
timeout in milliseconds that should be enforced. If the GraphQL request has not finished within that timeout a GraphQL
error will be returned:

```json
{
  "errors": [
    {
      "message": "Execution canceled because timeout of 500 millis was reached",
      "locations": []
    }
  ],
  "data": null
}
```

It will also write a log message to clarify which query was being executed when the timeout occurs:

```log
2021-05-14 09:19:53.314  WARN 359165 --- [nio-9000-exec-3] g.k.servlet.HttpRequestInvokerImpl       : GraphQL execution canceled because timeout of 500 millis was reached. The following query was being executed when this happened:
query {
  hello
}
```

In this example the timeout has been configured at 500 milliseconds in
[application.yml](src/main/resources/application.yml).

```yaml
graphql.servlet.async-timeout: 500
```

Note that the property `graphql.servlet.async-mode-enabled` has not been set to `true` here, because that's it's enabled
by default already.

## GraphQL execution cancellation

The [Query](src/main/java/com/graphql/sample/boot/Query.java) class shows two examples of queries that will take 3000
milliseconds to finish, which is longer than the timeout.

When you run this application you can open GraphiQL at http://localhost:9000/graphiql. You can then run either:

```graphql
query {
    hello
}
```

or

```graphql
query {
    world
}
```

Either one will stop processing after 500ms because of the configured timeout.

The main difference between the two methods in the `Query` class however is the ability to also cancel the execution of
the GraphQL request. Ideally the GraphQL execution is cancelled as well, because otherwise that request is still taking
up resources and Thread(s) even though the HTTP request has stopped. An attacker could repeatedly call that particular
GraphQL query which now returns fast, and that would spawn a new Thread with long running execution within the
application itself. That could ultimately lead to an application that crashes because it runs out of memory for example.

To make the GraphQL requests themselves cancellable you only have to remember to always return a `CompletableFuture`
instead of the value directly. The `Query.getHello()` method is a simple example of a cancellable GraphQL request.
While `Query.getWorld()` just returns the `String` value after a certain amount of time and cannot be canceled. In that
case when the GraphQL request finally finishes it will log a warning:

```log
Cannot write GraphQL response, because the HTTP response is already committed. It most likely timed out.
```
