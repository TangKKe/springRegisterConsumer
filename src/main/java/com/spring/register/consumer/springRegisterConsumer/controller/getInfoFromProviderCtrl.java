package com.spring.register.consumer.springRegisterConsumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.spring.register.consumer.springRegisterConsumer.redisCluster.RedisUtil;

/**
 * 获取服务提供者发布的服务。
 * 
 * @author tangke
 *
 */
@RestController
public class getInfoFromProviderCtrl {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(getInfoFromProviderCtrl.class);

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${providerUrl}")
	private String preUrl;


	/**
	 * 从redis集群获取信息。
	 * 
	 * @return String
	 */
	@RequestMapping("/getInfo")
	@ResponseBody
	public String getInfo() {
		String info = redisUtil.get("name");
		return info;
	}

	/**
	 * 从provider获取信息。
	 * 
	 * @return String
	 */
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
	@RequestMapping("/getString")
	@ResponseBody
	public String getStringByConsumer() {
		return "this a string created by consumer!";
	}
}
