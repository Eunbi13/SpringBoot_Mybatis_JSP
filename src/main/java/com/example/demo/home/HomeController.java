package com.example.demo.home;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * 
 * JSP project
 *
 */
@Controller
public class HomeController {
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("message", "JSP project");
		model.addAttribute("user", "iu");
		model.addAttribute("msg", "하루");
		return "index";
	}
}
