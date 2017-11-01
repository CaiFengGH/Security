package com.caifeng.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;
	
	//测试需要的伪装的mvc环境
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void whenQuerySuccess() throws Exception{
		//MockMvcRequestBuilders和MockMvcResultMatchers
		String res = mockMvc.perform(get("/user")
			   .param("username", "caifeng")
			   .param("age", "10")
			   .param("ageTo", "60")
//			   .param("size", "5")
			   .param("page", "2")
			   .param("sort", "age,desc")
			   .contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(status().isOk())
			   //jsonPath参考Github中的jsonPath示例
			   .andExpect(jsonPath("$.length()").value(3))
			   .andReturn().getResponse().getContentAsString();
		
		System.err.println(res);
	}
	
	@Test
	public void whenGetInfoSuccess() throws Exception{
		String res = mockMvc.perform(get("/user/1")
			   .contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.username").value("caifeng"))
			   .andReturn().getResponse().getContentAsString();
		System.out.println(res);
	}
	
	@Test
	public void whenGetInfoFail() throws Exception{
		mockMvc.perform(get("/user/u")
			   .contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(status().is4xxClientError());
	}
	
	@Test
	public void whenCreateSuccess() throws Exception{
		Date birthday = new Date();
		System.out.println(birthday.getTime());
		//json类型的字符串
		String content = "{\"username\":\"caifeng\",\"password\":null,\"birthday\":"+birthday.getTime()+"}";
		String res = mockMvc.perform(post("/user")
			   .contentType(MediaType.APPLICATION_JSON_UTF8)
			   .content(content))
			   .andExpect(status().isOk())
			   .andExpect(jsonPath("$.id").value("1"))
			   .andReturn().getResponse().getContentAsString();
		System.out.println(res);
	}

	@Test
	public void whenUpdateSuccess() throws Exception{
		//基于jdk8测试
		Date birthday = new Date();
		System.out.println(birthday.getTime());
		
		String content = "{\"id\":\"1\",\"username\":\"caifeng\",\"password\":null,\"birthday\":"+birthday.getTime()+"}";
		//更新时明确用户的id
		String res = mockMvc.perform(put("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value("1"))
				.andReturn().getResponse().getContentAsString();
		System.out.println(res);
	}
	
	@Test
	public void whenDeleteSuccess() throws Exception{
		mockMvc.perform(delete("/user/1")
			   .contentType(MediaType.APPLICATION_JSON_UTF8))
			   .andExpect(status().isOk());
	}
	
	@Test
	public void whenUploadSuccess() throws Exception{
		String res = mockMvc.perform(fileUpload("/file")
							.file(new MockMultipartFile("file", "demo.txt", "multipart/form-data", "hello".getBytes())))
							.andExpect(status().isOk())
							.andReturn().getResponse().getContentAsString();
		System.out.println(res);
	}
}
