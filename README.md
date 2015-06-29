# Pivot Texts

This application stores and retrieves test messages from pivots. Stored messages include the pivot id and name.
Please visit /swagger-ui.html for api docs.

## Dependencies

- java sdk 1.8
- postgres

## Setup

* Install with homebrew, then follow instructions on how to have it launch at startup.

- Create a postgres database super user admin with password 'admin' ```createuser admin --superuser --password```
- Create postgres databases: ```createdb pivot-texts && createdb pivot-texts-test```
- Create app configuration
  -- Copy deployment/*/src/resources/application.yml.example to application.yml

## Building & Running tests

    ./gradlew clean test
    
## Deploying

Push to cloud foundry with the following environment:

- SECURITY_USER_NAME
- SECURITY_USER_PASSWORD
- PIVOTS_URL
- PIVOTS_EMAIL
- PIVOTS_AUTHTOKEN
