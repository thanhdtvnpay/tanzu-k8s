package vn.vnpay.demo.demo.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 *
 * @author hungtq
 */
@Configuration
public class RedisConfig {

    private static final Logger LOG = LogManager.getLogger(RedisConfig.class);

    private static final String SPLIT = ":";

    private RedisDataSource redisDataSource;

    private RedisSentinelConfig redisSentinel;

    @Bean
    RedisConnectionFactory jedisConnectionFactory(RedisDataSource redisDataSource, RedisSentinelConfig redisSentinel) {
        this.redisDataSource = redisDataSource;
        this.redisSentinel = redisSentinel;
        List<String> hosts = new ArrayList<>();
        Map<String, Integer> hostAndPorts = new HashMap<>();
        redisSentinel.getNodes().forEach((nodes) -> {
            String[] value = nodes.split(SPLIT);
            hosts.add(value[0]);
            hostAndPorts.put(value[0], Integer.valueOf(value[1]));
        });

        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master(redisSentinel.getMaster());
        hosts.forEach((host) -> {
            sentinelConfig.sentinel(host, hostAndPorts.get(host));
        });
        sentinelConfig.setPassword(redisDataSource.getPassword());
        sentinelConfig.setDatabase(redisDataSource.getDatabase());

        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder()
                .connectTimeout(Duration.ofSeconds(redisDataSource.getExpire()));

        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(redisDataSource.getMaxActive());
        poolConfig.setMaxIdle(redisDataSource.getMaxIdle());
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);

        JedisClientConfiguration clientConfig = builder.usePooling().poolConfig(poolConfig).build();
        return new JedisConnectionFactory(sentinelConfig, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setDefaultSerializer(new StringRedisSerializer());
        try {
            template.setConnectionFactory(jedisConnectionFactory(redisDataSource, redisSentinel));
        } catch (Exception e) {
            LOG.error("Error in redis template: ", e);
        }
        template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
        return template;
    }

}
