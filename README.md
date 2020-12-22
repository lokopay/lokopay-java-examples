# lokopay-java-examples

This repo shows how to use `lokopay-java-client` to send API request to lokopay, and verify API request from lokopay.

## Set up API key and secret
Please sign up at lokopay to create a API key and secret.

- Staging Host: https://api.bleev.cloud
- Production Host: https://api.lokopay.cc

And then add `resources/config.properties` file in the project root folder.
```
api.key=<api_key>
api.secret=<api_secret>
api.host=https://api.bleev.cloud
```

## Add Lokopay java client dependency

Add `lokopay-java-client-1.0-SNAPSHOT.jar` in project class path.

## Run APIRequest Example

This example shows how to use `lokopay-java-client` to send api request to lokopay.

## Run APICallback Example

This example shows how to use `lokopay-java-client` to verify api request from lokopay.
