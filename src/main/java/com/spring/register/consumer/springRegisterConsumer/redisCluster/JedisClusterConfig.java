package com.spring.register.consumer.springRegisterConsumer.redisCluster;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * 根据自定义的配置文件生成JedisCluster。
 * @Configuration可理解为用spring的时候xml里面的<beans>标签
 * @author tangke
 * 
 */
@Configuration
public class JedisClusterConfig {

	private final static Logger LOGGER = LoggerFactory.getLogger(JedisClusterConfig.class);
	
	/**
	 * redis 集群节点。
	 */
	@Value("${redis.nodes}")
	private String nodes;
	
    /**
     * redis操作的超时时间。
     */
	@Value("${redis.commandTimeout}")
	private int commandTimeout;
	
	/**
	 * redis 密码。
	 */
	@Value("${redis.pwd}")
	private String pwd;
	
	/**
	 * 最大连接数。
	 */
	@Value("${redis.maxTotal}")
	private int maxTotal;
	
	/**
	 * 最大空闲连接数。
	 */
	@Value("${redis.maxIdle}")
	private int maxIdle;
	
	/**
	 * 最小空闲连接数。
	 */
	@Value("${redis.minIdle}")
	private int minIdle;
	
	/**
	 * 获取连接最大等待时间 ms。
	 */
	@Value("${redis.maxWait}")
	private int maxWait;

	/**
	 * 返回单例的JedisCluster,交给spring管理。
	 * @Bean可理解为用spring的时候xml里面的<bean>标签
	 * @return JedisCluster
	 */
	@Bean
	@Scope("singleton")
	public JedisCluster getJedisCluster() {
		LOGGER.info("进入redis集群初始化方法：访问集群地址为：" + nodes);
		String[] serverArray = nodes.split(",");
		if(null == serverArray || serverArray.length < 0) {
			LOGGER.info("redis集群节点输入不正确");
			return null;
		}
		Set<HostAndPort> nodes = new HashSet<>();
		for (String ipPort : serverArray) {
			String[] ipPortPair = ipPort.split(":");
			nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
		}
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMaxIdle(maxIdle);
		config.setMinIdle(minIdle);// 设置最小空闲数
		config.setMaxWaitMillis(maxWait);
		// 在获取Jedis连接时，自动检验连接是否可用
		config.setTestOnBorrow(true);
		// 在将连接放回池中前，自动检验连接是否有效
		config.setTestOnReturn(true);
		// 自动测试池中的空闲连接是否都是可用连接
		config.setTestWhileIdle(true);
		// 连接耗尽时是否阻塞, false报异常,ture阻塞直到超时,默认true
		config.setBlockWhenExhausted(false);
		// 表示idle object evitor两次扫描之间要sleep的毫秒数
		config.setTimeBetweenEvictionRunsMillis(30000);
		// 表示idle object evitor每次扫描的最多的对象数
		config.setNumTestsPerEvictionRun(10);
		// 表示一个对象至少停留在idle状态的最短时间，然后才能被idle object
		// evitor扫描并驱逐；这一项只有在timeBetweenEvictionRunsMillis大于0时才有意义
		config.setMinEvictableIdleTimeMillis(60000);
		// 需要密码连接的创建对象方式
		// 参数依次是：集群地址，链接超时时间，返回值的超时时间，链接尝试次数，密码和配置文件
		return new JedisCluster(nodes, commandTimeout, 10000, 3, pwd, config);
	}
}
