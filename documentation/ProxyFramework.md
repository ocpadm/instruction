# RestEasy Proxy Framework

Mirrors the server side JAX-RS resource classes. The framework seamlessly builds the HTTP requests behind the scenes and hides away all the JAX-RS specification details. For the implementer it almost seems like implementing a call to a local class instance. 

* [ResteasyClientBuilder](../frontend-web/src/main/java/com/nocom/inst/util/Resources.java) get constructed through CDI producer, this class is responsible for building up the connection between REST client and server.
* Through fluent API it registers the [MemberResourceRESTService](../frontend-web/src/main/java/com/nocom/inst/rest/MemberResourceRESTService.java) RestEasy Client Proxy interface containing all Rest annotated method signatures to inquire the REST endpoint [MemberResourceRESTService](../backend-web/src/main/java/com/nocom/inst/rest/MemberResourceRESTService.java).
* Client gets authenticated through the JAX-RS Client request filter [BearerTokenFilter](../common/src/main/java/com/nocom/inst/keycloak/BearerTokenFilter.java), this filter propagates the Keycloak OpenID connect token over the HTTP headers on the REST request.