package com.app.filemanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/***
 * Controller to navigate between pages
 * 
 * @author Ravi Teja Pasarakonda
 *
 */
@Controller
public class FileManagerController {

	@GetMapping("/")
    public String defaultPage() {
        return "home";
    }
	
	@GetMapping("/home")
    public String homePage() {
        return "home";
    }
	
	@GetMapping("/login")
    public String login() {
        return "login";
    }

}
