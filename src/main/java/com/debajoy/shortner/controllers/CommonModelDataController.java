package com.debajoy.shortner.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.debajoy.shortner.service.ServerLinkUtil;

@Controller
@ControllerAdvice
public class CommonModelDataController {

	@ModelAttribute
	public void addContextPath(ModelMap modelMap, HttpServletRequest req) {
		if (ServerLinkUtil.isNotPopulated()) {
			ServerLinkUtil.scheme = req.getScheme();
			ServerLinkUtil.host = req.getServerName();
			ServerLinkUtil.port = req.getServerPort();
			ServerLinkUtil.url = ServerLinkUtil.scheme + "://" + ServerLinkUtil.host + ":" + ServerLinkUtil.port;
		}
		
		modelMap.addAttribute("baseURL", ServerLinkUtil.url + req.getRequestURI());
	}
}
