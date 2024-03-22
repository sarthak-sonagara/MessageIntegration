package com.sms.send.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public interface UniversalController {
    ResponseEntity<String> getMyDetailsReddit(HttpServletRequest request);

}
