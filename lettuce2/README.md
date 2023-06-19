Certainly! Here's an example of configuring SSL for Redis in Spring Boot using Lettuce with a `.crt` file:

```java
import io.lettuce.core.ClientOptions;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SslOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.ssl.key-store}")
    private String keyStorePath;

    @Value("${spring.redis.ssl.key-store-password}")
    private String keyStorePassword;

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        config.setPassword(RedisPassword.none());

        RedisURI redisURI = RedisURI.builder()
                .withHost(config.getHostName())
                .withPort(config.getPort())
                .withSsl(true)
                .build();

        SslOptions sslOptions = SslOptions.builder()
                .keystore(keyStorePath)
                .build();

        ClientOptions clientOptions = ClientOptions.builder()
                .sslOptions(sslOptions)
                .build();

        RedisClient redisClient = RedisClient.create(redisURI);
        redisClient.setOptions(clientOptions);

        return new LettuceConnectionFactory(redisClient, config);
    }
}
```

Make sure to set the properties `spring.redis.host`, `spring.redis.port`, `spring.redis.ssl.key-store`, and `spring.redis.ssl.key-store-password` in your `application.properties` or `application.yml` file according to your Redis server's configuration.

In the configuration above, the `keyStorePath` variable should point to the path of the `.crt` file in the `redis-ssl` directory.
