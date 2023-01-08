##Description

This project is implemented by using the following techs:

- Java, Spring, Redis, Docker

####LookUp Service

This service is used to get a list of URLs and their corresponding values from a Redis database. It uses caching to improve performance.

####How it Works

The getUrls method takes in a set of keys, and uses a stream to map each key to a MutablePair object that contains the key and its corresponding value in the Redis database. If the value cannot be found in the cache, the findUrl method is called to search for the value in the Redis database.

The findUrl method first checks if the given URL is present in the Redis database. If it is not found, it will start removing the last parameter from the URL and checking the modified URL in the Redis database until a value is found or the URL no longer contains any parameters.

##How to run

```
docker-compose up -d
```

##Open Discussion

- I could implement URL validation in LookUpService. 
- URLs that cannot be found in the first redis query can be added to redis after evaluation of the given parameter to reduce the time to find the corresponding value when the same url is researched.
- Adding unit tests to ensure that the code is correct and maintainable.
- Assuming we have n clusters, each one of them



