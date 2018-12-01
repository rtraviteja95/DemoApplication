package com.app.filemanager.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.app.filemanager.config.FileManagerConfig;
import com.app.filemanager.exception.FileStorageException;
import com.app.filemanager.exception.UserFileNotFoundException;
import com.app.filemanager.model.FileDetails;
/***
 * Service layer that stores/retrieves files.
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
@Service
public class FileManagerServiceImp implements FileManagerService{

	private final Path filetStoragePath;

	private List<FileDetails> files;

	@Override
	public FileDetails storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		Path targetLocation = this.filetStoragePath.resolve(fileName);
		try {
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new FileStorageException("Error storing file"+ file.getName());
		}
		FileDetails fileDetails = new FileDetails(fileName, targetLocation.toString(), Calendar.getInstance().getTime());
		files.add(fileDetails);
		return fileDetails;
	}

	@Override
	public Resource downloadFile(String fileName) {
		try {
			Path filePath = this.filetStoragePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
			if(resource.exists()) {
				return resource;
			} else {
				throw new UserFileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new UserFileNotFoundException("File not found " + fileName, ex);
		}
	}

	@Override
	public List<FileDetails> listOfFiles() {
		return getFiles();
	}

	@Autowired
	public FileManagerServiceImp(FileManagerConfig fileManagerConfig){
		this.filetStoragePath = Paths.get(fileManagerConfig.getFileDirectory()).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.filetStoragePath);
		} catch (IOException e) {
			throw new FileStorageException("Error creating user directory");
		}
		this.files = new LinkedList<FileDetails>();

		try(Stream<Path> path = Files.walk(this.filetStoragePath.toAbsolutePath().normalize())){
			path.forEach(filePath -> {
				File file = new File(filePath.toAbsolutePath().toString());
				if(!file.isDirectory()) {
					BasicFileAttributes view;
					try {
						view = Files.getFileAttributeView( filePath, BasicFileAttributeView.class)
								.readAttributes();
					} catch (IOException e) {
						throw new FileStorageException("Error reading file attributes");
					}
					this.files.add(new FileDetails(filePath.getFileName().toString(), filePath.toAbsolutePath().toString(), new Date(view.creationTime().toMillis())));
				}
			});
		} catch (IOException e) {
			throw new FileStorageException("Error retrieving files from Directory");
		}

	}

	public List<FileDetails> getFiles() {
		return files;
	}

}
