package com.muktalabs.em.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.muktalabs.em.dao.UserDao;
import com.muktalabs.em.service.UserService;


@RestController
@RequestMapping("/user/")
public class UserController 
{
	@Autowired
	UserDao userDao;

	@Autowired
	UserService userService;

	private static final Logger logger = Logger.getLogger(UserController.class.getName());

}
