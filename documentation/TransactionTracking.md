# Transaction Tracking

A common way to track transactions across service calls is through correlation id's. At the beginning of a transaction, a unique transaction id is assigned. That ID is then logged with the transaction for all log entries.

* A servlet filter to ensure that each transaction has a correlation id, see [RequestCorrelationFilter](../common/src/main/java/com/nocom/inst/correlate/RequestCorrelationFilter.java).
* Secondly, the log format needs to include the correlation id with all log entries, see [logging.cli](scripts/logging.cli).
* Thirdly, all outgoing service calls need to have the correlation id in the header, see [BaererTokenFilter](../common/src/main/java/com/nocom/inst/keycloak/BaererTokenFilter.java).

## Logger injected with CDI

* Adding logging to your application is now even easier with simple injection of a SLF4J logger object into any CDI bean. Simply annotate a org.slf4j.Logger type member with the @Logger qualifier annotation and an appropriate logger object will be injected into any instance of the bean, see [CDI Logging producer](../common/src/main/java/com/nocom/inst/logging/LoggingProducer.java).
* Implementing different type of loggers can be accomplished through CDI Qualifiers like [this one](../common/src/main/java/com/nocom/inst/logging/AuditTrackLogger.java).