package com.muktalabs.em.controller;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.muktalabs.em.dao.UserDao;
import com.muktalabs.em.service.LoginService;

@RestController
@RequestMapping("/login/")
public class LoginController {
	@Autowired
	UserDao userDao;

	@Autowired
	LoginService loginService;

	private static final Logger logger = Logger.getLogger(UserController.class
			.getName());

	@RequestMapping(value = "/loginuser", method = RequestMethod.POST)
	protected ModelAndView loginUser(@RequestParam String userid,
			@RequestParam String password, HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("username = " + userid);
		System.out.println("password = " + password);

		try {
			boolean result = loginService.authenticate(userid, password);
			System.out.println("login result = " + result);

			ModelAndView loginMAV = new ModelAndView("signin");
			loginMAV.addObject("msg", "Authentication Failed !");
			return loginMAV;
		} catch (Exception ex) {
			logger.info("Log In error - " + ex.getMessage());
			ModelAndView loginMAV = new ModelAndView("signin");
			loginMAV.addObject("msg", "Connection Failed !");
			return loginMAV;
		}
	}

	@RequestMapping(value = "/logoutUser", method = RequestMethod.GET)
	protected ModelAndView logoutUser(HttpServletRequest request) {
		try {
			request.getSession().removeAttribute("loggedin_user");
			return new ModelAndView("index");
		} catch (Exception ex) {
			ModelAndView homeMAV = new ModelAndView("index");
			// homeMAV.addObject("errorMsg", ex.getMessage());
			return homeMAV;
		}
	}

	// GUI Transfer Mapping
	@RequestMapping(value = "/customerhome", method = RequestMethod.GET)
	protected ModelAndView customerHome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customerHome");
		return mv;
	}

	@RequestMapping(value = "/resellerhome", method = RequestMethod.GET)
	protected ModelAndView dealerHome(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("resellerHome");
		return mv;
	}

	// GUI Transfer Mapping

}
