package com.spring.register.consumer.springRegisterConsumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.discovery.DiscoveryManager;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.register.consumer.springRegisterConsumer.redisCluster.RedisUtil;

/**
 * 获取服务提供者发布的服务。
 * 
 * @author tangke
 *
 */
@SuppressWarnings("deprecation")
@EnableCircuitBreaker
@RestController
public class getInfoFromProviderCtrl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(getInfoFromProviderCtrl.class);
	
    /**redis工具类访问redis数据 **/
	@Autowired
	private RedisUtil redisUtil;
	
	/**RestTemplate调用接口 **/
	@Autowired
	private RestTemplate restTemplate;
	
	/**RestTemplate调用接口的URL路**/
	@Value("${providerUrl}")
	private String preUrl;


	/**
	 * 从redis集群获取信息。
	 * 
	 * @return String
	 */
	@HystrixCommand(fallbackMethod = "failback")
	@RequestMapping("/getInfo")
	@ResponseBody
	public Object getInfo() {
		Object info = redisUtil.getObjectByKey("name");
		return info;
	}

	/**
	 * 从provider获取信息。
	 * 
	 * @return String
	 */
	@HystrixCommand(fallbackMethod = "failback")
	@RequestMapping("/getInfoFromProvider")
	@ResponseBody
	public String getInfoFromProvider() {
		LOGGER.info("ApplicationName -- preUrl:" + preUrl);
		return restTemplate.getForObject(preUrl + "/getInfo", String.class);
	}

	/**
	 * 从provider获取信息。
	 * 
	 * @return String
	 */
	@HystrixCommand(fallbackMethod = "failback")
	@RequestMapping("/getStringFromProvider")
	@ResponseBody
	public String getString() {
		LOGGER.info("ApplicationName :" + preUrl);
		return restTemplate.getForObject(preUrl + "/getString", String.class);
	}

	/**
	 * 测试。
	 * 
	 * @return
	 */
	@HystrixCommand(fallbackMethod = "failback")
	@RequestMapping("/getString")
	@ResponseBody
	public String getStringByConsumer() {
		return "this a string created by consumer!";
	}
	
	/**
	 * 关闭。
	 */
	@RequestMapping("/offline")
	@ResponseBody
	public void offline() {
		DiscoveryManager.getInstance().shutdownComponent();
	}
	
	/**
	 * 熔断方法。
	 * @return String
	 */
	public String failback() {
		return "服务器繁忙，请稍后再试！";
	}
}
