package com.debajoy.shortner.controllers;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.debajoy.shortner.service.ShortnerService;
import com.debajoy.shortner.service.ShortnerService.IllegalURLException;
import com.debajoy.shortner.service.ShortnerService.ShortKeyNotFoundException;

@Controller
@RequestMapping(path="/")
public class ShortnerController {

	final Logger logger = Logger.getLogger(ShortnerController.class);
	
	@Autowired
	ShortnerService shortnerService;

	@RequestMapping(path="")
	public String index() {
		return "index";
	}
	
	@RequestMapping("shorten")
	public String shorten(
			@RequestParam String longURL,
			ModelMap modelMap,
			RedirectAttributes redirectAttributes) {
		logger.info("/shorten");
		
		try {
			redirectAttributes.addFlashAttribute("shortend", shortnerService.shorten(longURL));
		} catch (MalformedURLException ex) {
			redirectAttributes.addFlashAttribute("error", "URL is not valid");
		} catch (IllegalURLException e) {
			redirectAttributes.addFlashAttribute("error", "URL not allowed");
		}
		
		return "redirect:/";
		
	}

	@RequestMapping(path="{key:[a-zA-Z0-9]+}")
	public String unshorten(@PathVariable String key,
							ModelMap modelMap,
							RedirectAttributes redirectAttributes,
							HttpServletResponse resp) {
		try {
			resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
			resp.setHeader("Location", shortnerService.unshorten(key).getLongURL());
			resp.setHeader("Connection", "close");
			return null;
		} catch (ShortKeyNotFoundException ex) {
			modelMap.addAttribute("error", "No record for short URL found");
			return "index";
		}
	}
	
}
