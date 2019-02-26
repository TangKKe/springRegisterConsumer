package com.spring.register.consumer.springRegisterConsumer.redisCluster;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * redis工具类。
 * @author tangke
 *
 */
@Component
public class RedisUtil {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置缓存
	 * 
	 * @param key   缓存key
	 * @param value 缓存value
	 */
	public void setKeyAndValue(String key, String value) {
		redisTemplate.opsForValue().set(key, value);
		LOGGER.info("RedisUtil:set cache key={},value={}", key, value);
	}
	
	/**
	 * 设置缓存，并且自己指定过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expireTime 过期时间
	 */
	public void setWithExpireTime(String key, String value, int expireTime) {
		redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
		LOGGER.info("RedisUtil:setWithExpireTime cache key={},value={},expireTime={}", key, value, expireTime);
	}

	/**
	 * 设置缓存对象时间
	 * 
	 * @param key 缓存key
	 * @param expireTime 缓存时间
	 */
	public <T> void setKeyOfExpireTime(String key, int expireTime) {
		redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
	}

	/**
	 * 获取指定key的缓存
	 * 
	 * @param key;
	 */
	public Object getObjectByKey(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 判断当前key值 是否存在
	 *
	 * @param key
	 */
	public boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 删除指定key的缓存
	 * 
	 * @param key
	 */
	public void delete(String from, String key) {
		redisTemplate.delete(key);
		LOGGER.info("RedisUtil:delete " + from + " cache key={}", key);
	}
}
