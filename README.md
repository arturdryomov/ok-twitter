# OkTwitter

Just a sample Twitter application developed using Kotlin, RxJava
and other semi-hipster thingies.

## Building

You will need JDK 1.7+ and Android SDK installed.
Gradle and all dependencies will be downloaded automatically.

Also, [create a Twitter application](https://apps.twitter.com/).
Then generate consumer key and access token.
Put them into a file and you are good to go.

```
$ vim twitter.properties
```

```properties
consumer.key = CONSUMER_KEY
consumer.secret = CONSUMER_SECRET
access.token = ACCESS_TOKEN
access.token-secret = ACCESS_TOKEN_SECRET
```

Finally you can build an application.

```
$ ./gradlew clean assembleDebug
```
