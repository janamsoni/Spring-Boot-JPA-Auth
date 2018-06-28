package com.croods.springbootjpaauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Home 
{
	@RequestMapping(value= {"/","/home"})
	public String mainhome() 
	{
		return "home";		
	}
	@RequestMapping("/hello")
	public String hello() 
	{
		return "hello";		
	}
	@RequestMapping("/hellouser")
	public String hellouser() 
	{
		return "hellouser";		
	}
	@RequestMapping("/login")
	public String login() 
	{
		return "login";		
	}
	@RequestMapping("/403")
	public String accessdenied() 
	{
		return "403";		
	}
	
	

}
