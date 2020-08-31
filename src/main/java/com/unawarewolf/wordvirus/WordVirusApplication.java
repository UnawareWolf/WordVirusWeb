package com.unawarewolf.wordvirus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

//@RestController
//@Controller
@SpringBootApplication
public class WordVirusApplication {

	public static void main(String[] args) {
		SpringApplication.run(WordVirusApplication.class, args);
	}

//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
//		return String.format("Hello %s!", name);
//	}

//	@GetMapping("/greeting")
//	public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
//		model.addAttribute("name", name);
//		return "greeting";
//	}

//	@RequestMapping(value="/action", method= RequestMethod.POST)
//	public ModelAndView onClickAction() {
//		ModelAndView mav = new ModelAndView();
//		return null;
//	}

}
