package com.example.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Credentials;

@Controller
public class WelcomeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);

	@RequestMapping(value={"/login"},method = RequestMethod.GET)
    public String loginPage(HttpServletRequest request){
		LOGGER.info("Received request for login page with id - " + request.getSession().getId());
		String uniqueKey = "1234567891234567";
	    request.getSession().setAttribute("key", uniqueKey);
	    return "index";
    }

    @RequestMapping(value={"/login"},method = RequestMethod.POST)
    public @ResponseBody String login(@RequestBody Credentials credentials) {
        String decryptedPassword =  new String(java.util.Base64.getDecoder().decode(credentials.getPassword()));
        AesUtil aesUtil = new AesUtil(128, 1000);
        if (decryptedPassword != null && decryptedPassword.split("::").length == 3) {
            LOGGER.info("Password decrypted successfully for username - " + credentials.getUserName());
            String password = aesUtil.decrypt(decryptedPassword.split("::")[1], decryptedPassword.split("::")[0], "1234567891234567", decryptedPassword.split("::")[2]);
            System.out.println(password);
        }

        return "success";
    }

}
