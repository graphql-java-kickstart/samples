# Error handling using ExceptionHandler annotation

Spring MVC allows you to customize HTTP error responses
using the `@ExceptionHandler` annotation. We've added support
for that annotation as well to convert exceptions into valid
`GraphQLError` objects (singular or a `List`).

To use this functionality you need to add the annotation
to a method with the correct contract, which is one of
the following:

```java
public GraphQLError singleError(Exception e);
public GraphQLError singleError(Exception e, ErrorContext ctx);
public Collection<GraphQLError> singleError(Exception e);
public Collection<GraphQLError> singleError(Exception e, ErrorContext ctx);
```

When returning a collection of errors you can return any type implementing the `Collection`, e.g. `List` or `Set`, etc.

```java
@ExceptionHandler(value = Exception.class)
public List<GraphQLError> handleCustomException(Exception e) {
  // returning an empty list obviously doesn't make sense here
  return emptyList();
}
```
Please note that contrary to Spring Boot we do require you
to specifically identify the exception using the `value`
parameter of the annotation. If you omit that parameter
the annotation will be ignored and won't work.

See the [HelloQuery](src/main/java/com/graphql/kickstart/sample/HelloQuery.java)
for a working example.

## Error context

GraphQL errors should contain certain meta data such as locations and the path.
That's the main benefit of using the optional `ErrorContext` parameter. You can extract that
information from that object as shown in this example:

```java
@ExceptionHandler(value = IllegalStateException.class)
public GraphQLError toCustomError(IllegalStateException e, ErrorContext errorContext) {
    Map<String, Object> extensions = Optional.ofNullable(errorContext.getExtensions()).orElseGet(HashMap::new);
    extensions.put("my-custom-code", "some-custom-error");
    return GraphqlErrorBuilder.newError()
        .message(e.getMessage())
        .extensions(extensions)
        .errorType(errorContext.getErrorType())
        .locations(errorContext.getLocations())
        .path(errorContext.getPath())
        .build();
    }
```
