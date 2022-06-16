# What it can do

Its provide DecorateIntegrationException which helps to catch all exceptions to marked methods and decorate them with
common IntegrationException. It uses to minimize efforts of handling various types different exceptions in external
calls to single one for further processing.

Second, it parses initial exceptions in order to gather data for further reporting.

# Quick start

Attach module to your pom and add property into your Spring context

```app.blocks.exception.wrapper.enable: true ```

# How to extend

In order to add new type of exception handling follow the steps:

1. Create a new Exception class with predecessor of `IntegrationException`. Don't forget to add new fields if needed.
2. Create a new Exception Builder which extends `IntegrationExceptionBuilder` and override methods:
   1. `supportedExceptions()` with enumeration of exception which will be processed here.
   2. `build()` with logic of creating new `IntegrationException` from source exception.
3. Register your Exception Builder in Spring context with **`@Order` higher than default**. Its crucial since builders
   utilized by its order and only one with top priority will be called.
