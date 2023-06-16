import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisSslConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("your-redis-host", 6379);
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        JedisSslConnectionFactory sslConnectionFactory = new JedisSslConnectionFactory(
                config,
                JedisClientConfiguration.builder()
                        .usePooling()
                        .poolConfig(poolConfig)
                        .and()
                        .useSsl()
                        .keyStoreType("JKS")
                        .keyStore("path/to/your/keystore.jks")
                        .keyStorePassword("your-keystore-password".toCharArray())
                        .build()
        );

        return sslConnectionFactory;
    }
}
