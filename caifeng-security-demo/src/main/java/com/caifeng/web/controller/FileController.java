package com.caifeng.web.controller;

import java.io.File;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.caifeng.dto.FileInfo;

@RestController
@RequestMapping("/file")
public class FileController {
	
	/**
	 * @author Ethan
	 * @desc 文件上传
	 */
	@PostMapping
	public FileInfo upload(MultipartFile file) throws Exception{
		//测试file的基本信息
		System.out.println(file.getName());
		System.out.println(file.getOriginalFilename());
		System.out.println(file.getSize());
		//实际应用中dfs服务
		String filePath = "D:\\Eclipse-kepler.4.3.1\\workspace\\caifeng-security-demo\\src\\main\\java\\com\\caifeng\\web\\controller";
		
		File localFile = new File(filePath,new Date().getTime()+".txt");
		//将传输内容进行转换
		file.transferTo(localFile);
		
		return new FileInfo(localFile.getAbsolutePath());
	}
}
