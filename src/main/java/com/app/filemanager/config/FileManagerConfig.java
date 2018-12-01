package com.app.filemanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/***
 * To read the configuration values from the YML File
 * 
 *  @author Ravi Teja Pasarakonda
 *
 */
@Configuration
public class FileManagerConfig {

	@Value("${filemanager.file-directory}")
	private String fileDirectory;

	public String getFileDirectory() {
		return fileDirectory;
	}
	
}
