package com.app.filemanager.model;

import java.util.Date;
/***
 * POJO Class to store file details
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
public class FileDetails {

	private String fileName;
	
	private String filePath;
	
	private Date storedDate;
	
	public FileDetails(String fileName, String filePath, Date storedDate) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.storedDate = storedDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getStoredDate() {
		return storedDate;
	}

	public void setStoredDate(Date storedDate) {
		this.storedDate = storedDate;
	}
	
}
