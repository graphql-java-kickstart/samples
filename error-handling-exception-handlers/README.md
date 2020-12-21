# Error handling using ExceptionHandler annotation

Spring MVC allows you to customize HTTP error responses
using the `@ExceptionHandler` annotation. We've added support
for that annotation as well to convert exceptions into valid
`GraphQLError` objects (singular or a `List`).

To use this functionality you need to add the annotation
to a method with the correct contract, which is one of
the following:

```java
@ExceptionHandler(value = Exception.class)
public GraphQLError handleCustomException(Exception e) {
  return null;
}

@ExceptionHandler(value = Exception.class)
public List<GraphQLError> handleCustomException(Exception e) {
  return emptyList();
}
```

See the [HelloQuery](src/main/java/com/graphql/kickstart/sample/HelloQuery.java)
for a working example.

Please note that contrary to Spring Boot we do require you
to specifically identify the exception using the `value`
parameter of the annotation. If you omit that parameter
the annotation will be ignored and won't work.
