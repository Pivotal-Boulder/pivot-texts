# Pivot Texts

This application stores and retrieves test messages from pivots. Stored messages include the pivot id and name.
Please visit `/swagger-ui.html` for api docs.

## Dependencies

- [Java SDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Postgres](http://www.postgresql.org/download/)

## Setup


1. Install postgres: `brew install postgres`
1. Create a postgres database super user admin with password 'admin': `createuser admin --superuser --password`
1. Create postgres databases: `createdb pivot-texts && createdb pivot-texts-test
1. `cp deployment/*/src/resources/application.yml.example application.yml`

## Building & Running tests

```
./gradlew :deployment/pivot-texts-api:test
./gradlew clean test
```

Run the application tests first to migrate the database. The component tests expect the database to be in a good state.
    
## Deploying to Cloud Foundry

1. Create a postgres service
1. Modify `manifest.yml` with correct postgres service name, environment variable values
1. `cf push`

## TODO

It would be nice to have an example of a component based front end web application build.
