import io.lettuce.core.RedisURI;
import io.lettuce.core.ssl.SslOptions;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.DefaultLettucePool;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.SslClientConfiguration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.redis.lettuce.ssl.key-store-type}")
    private String keyStoreType;

    @Value("${spring.redis.lettuce.ssl.key-store}")
    private String keyStorePath;

    @Value("${spring.redis.lettuce.ssl.key-store-password}")
    private String keyStorePassword;

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(ClientResources clientResources) {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(redisHost, redisPort);

        RedisURI redisUri = RedisURI.Builder.redis(config.getHostName())
                .withPort(config.getPort())
                .withSsl(true)
                .withSslOptions(SslOptions.builder()
                        .keystore(keyStorePath)
                        .keystorePassword(keyStorePassword.toCharArray())
                        .build())
                .build();

        SslClientConfiguration sslClientConfiguration = SslClientConfiguration.builder()
                .keyStoreType(keyStoreType)
                .build();

        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(sslClientConfiguration)
                .clientResources(clientResources)
                .build();

