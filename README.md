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

Firstly, it use the apiSecret in `resources/config.properties` file to generate a signaure which is required to access lokopay API. 

Secondly, it assembly the API request and set up correct request headers to call lokopay API to generate a new invoice. 

## Run APICallback Example

This example shows how to use `lokopay-java-client` to verify api request from lokopay.

Firstly, it starts localhost server and listening at `localhost:8000`.

Seconldy, you can test the verfication by send the call back request to http:localhost:8000/test. Then in the `CallBackHandler`, it shows how the signaure in the API call back request will be verified.




