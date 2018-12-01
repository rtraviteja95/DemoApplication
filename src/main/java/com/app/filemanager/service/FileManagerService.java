package com.app.filemanager.service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.app.filemanager.model.FileDetails;

public interface FileManagerService {

	public FileDetails storeFile(MultipartFile file);

	public Resource downloadFile(String fileName) ;

	public List<FileDetails> listOfFiles();
	
}
