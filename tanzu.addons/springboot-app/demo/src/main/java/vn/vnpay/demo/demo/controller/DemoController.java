package vn.vnpay.demo.demo.controller;

import java.sql.Connection;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hungtq
 */
@RestController
public class DemoController {

    private static final Logger LOG = LogManager.getLogger(DemoController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public DemoController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @GetMapping(value = "/ping")
    public String ping() {
        try (org.springframework.amqp.rabbit.connection.Connection rabbitConnection = rabbitTemplate
                .getConnectionFactory().createConnection()) {
            if (!rabbitConnection.isOpen()) {
                LOG.info("Have no rabbitmq connection");
            }
            LOG.info("Connect to rabbitmq success");
        } catch (Exception e) {
            LOG.error("Connection to rabbitmq has exception", e);
        }
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        if (null != factory) {
            if (factory.getSentinelConnection().isOpen()) {
                String ping = factory.getConnection().ping();
                LOG.info("Redis sentinel connection success with return ping: {}", ping);
            }
            LOG.info("Create factory success but can get sentinel connection");
        } else {
            LOG.info("Have no redis connection");
        }
        return "pong";
    }

}
