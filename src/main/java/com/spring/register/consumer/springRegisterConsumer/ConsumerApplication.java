package com.spring.register.consumer.springRegisterConsumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.support.BasicAuthorizationInterceptor;
import org.springframework.web.client.RestTemplate;

/**
 * 服务消费者启动类，服务消费者和服务提供者是相对的。
 * @author tangke
 *
 */
@SpringBootApplication
@EnableEurekaClient
public class ConsumerApplication {
	
	/**
	 * 访问注册中心的用户名。
	 */
	@Value("${security.user.name}")
	private String userName;
	
	/**
	 * 访问注册中心的密码。
	 */
	@Value("${security.user.password}")
	private String passWord;
	
	//注入restTemplate
		@Bean
		@LoadBalanced
		@Scope("singleton")
		RestTemplate restTemplate() {
			RestTemplate restTemplate = new RestTemplate();
			//RestTemplate访问Basic加密的eureka
			restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor(userName, passWord));
		    return restTemplate;
		}
	
	/**
	 * 主函数入口。
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}
}
