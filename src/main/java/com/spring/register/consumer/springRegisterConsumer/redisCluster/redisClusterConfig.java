package com.spring.register.consumer.springRegisterConsumer.redisCluster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Configuration可理解为用spring的时候xml里面的<beans>标签 配置redisTemplate
 * 
 * @author tangke
 * 
 */
@Configuration
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class redisClusterConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(redisClusterConfig.class);
	@Bean
	public RedisTemplate<String, Object> redisCacheTemplate(LettuceConnectionFactory redisConnectionFactory) {
		LOGGER.info("init RedisTemplate start.....");
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(redisConnectionFactory);
		LOGGER.info("init RedisTemplate end.....");
		return template;
		}
}
