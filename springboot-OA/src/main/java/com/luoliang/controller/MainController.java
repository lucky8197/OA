package com.luoliang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	/**
	 * 默认页
	 * 
	 * @return
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	/**
	 * 主页
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index1() {
		return "index";
	}

	/**
	 * 错误页面
	 * 
	 * @return
	 */
	@RequestMapping("/errorPage")
	public String errorPage() {
		return "errorPage";
	}

}
