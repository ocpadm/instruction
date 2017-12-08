# RestEasy JAX-RS Resource Endpoints

* Root resource class [JaxRsActivator](../backend-web/src/main/java/com/nocom/inst/rest/JaxRsActivator.java) is annotated with @Path, this annotation specifies the main entry point URL path for all resource lookups. 
* [MemberResourceRESTService](../backend-web/src/main/java/com/nocom/inst/rest/MemberResourceRESTService.java) is decorated with JAX-RS annotations to define resources and the actions that can be preformed on these resources. 
