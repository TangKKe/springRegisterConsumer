package com.spring.register.consumer.springRegisterConsumer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;



/**
 * 测试类。
 * @author tangke
 *
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class testCase1 {
	
	
	protected MockMvc mockMvc;
	
	@Autowired
    protected WebApplicationContext wac;

    @Before()  //这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();  //初始化MockMvc对象
    }
	
	/**
	 * 测试从Provoder获取信息。
	 * @throws Exception 
	 */
	@Test
	public void getStringFromProvider() throws Exception {
		
		String responseString = mockMvc.perform(
                get("/getStringFromProvider") //请求的url,请求的方法是get
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)) //数据格式
				.andExpect(status().isOk())    //返回的状态是200
                .andDo(print())         //打印出请求和相应的内容
                .andReturn().getResponse().getContentAsString(); 
		
		System.out.println("----返回结果为：" + responseString);
	
	}
}
