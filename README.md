# Pivot Texts

This application stores and retrieves test messages from pivots. Stored messages include the pivot id and name.
Please visit /swagger-ui.html for api docs.

## Dependencies

- java sdk 1.8
- postgres

## Setup

* Install postgres with homebrew, then follow instructions on how to have it launch at startup.

- Create a postgres database super user admin with password 'admin' ```createuser admin --superuser --password```
- Create postgres databases: ```createdb pivot-texts && createdb pivot-texts-test```
- Create app configuration
  -- Copy deployment/*/src/resources/application.yml.example to application.yml

## Building & Running tests

    ./gradlew :deployment/pivot-texts-api:test
    ./gradlew clean test

Run the application tests first to migrate the database. The component tests expect the database to be in a good state.
    
## Deploying

Push to cloud foundry with the following environment:

- SECURITY_USER_NAME
- SECURITY_USER_PASSWORD
- PIVOTS_URL
- PIVOTS_EMAIL
- PIVOTS_AUTHTOKEN

## TODO

It would be nice to have an example of a component based front end web application build.
