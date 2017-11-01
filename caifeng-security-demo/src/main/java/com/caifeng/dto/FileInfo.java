package com.caifeng.dto;

public class FileInfo {
	
	private String filePath;
	
	public FileInfo(String filePath){
		this.setFilePath(filePath);
	}

	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
