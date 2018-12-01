package com.app.filemanager.controller;

import java.io.IOException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.filemanager.exception.UserFileNotFoundException;
import com.app.filemanager.model.FileDetails;
import com.app.filemanager.service.FileManagerService;

/***
 * Rest Controller to handle page requests, storing/retrieving files.
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
@RestController
@RequestMapping("/userfm")
public class FileManagerRestController {
	
	private FileManagerService fileManagerService;

	@PostMapping("/uploadFile")
	public FileDetails uploadUserFile(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes){
		return fileManagerService.storeFile(file);
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadUserFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        Resource resource = fileManagerService.downloadFile(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            throw new UserFileNotFoundException("Exception in file download", ex);
        }

        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
	}
	
	@GetMapping("/userFiles")
	public List<FileDetails> getUserFiles() {
		return fileManagerService.listOfFiles();
	}
	
	@Autowired
	public FileManagerRestController(FileManagerService fileManagerService) {
		this.fileManagerService = fileManagerService;
	}
	
}
