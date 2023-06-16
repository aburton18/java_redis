import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisSslConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.ssl.key-store-type}")
    private String keyStoreType;

    @Value("${spring.redis.ssl.key-store}")
    private String keyStorePath;

    @Value("${spring.redis.ssl.key-store-password}")
    private String keyStorePassword;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        JedisSslConnectionFactory sslConnectionFactory = new JedisSslConnectionFactory(
                config,
                JedisClientConfiguration.builder()
                        .usePooling()
                        .poolConfig(poolConfig)
                        .and()
                        .useSsl()
                        .keyStoreType(keyStoreType)
                        .keyStorePath(keyStorePath)
                        .keyStorePassword(keyStorePassword.toCharArray())
                        .build()
        );

        return sslConnectionFactory;
    }
}
