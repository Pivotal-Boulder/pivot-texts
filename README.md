# Pivot Texts

## Dependencies

- java sdk 1.8
- postgres

## Setup

* Install with homebrew, then follow instructions on how to have it launch at startup.

- Create a postgres database super user admin with password 'admin' ```createuser admin --superuser --password```
- Create postgres databases: ```createdb pivot-texts && createdb pivot-texts-test```
- Create app configuration
  -- Copy deployment/*/src/resources/application.yml.example to application.yml

## Building

    ./gradlew
    
