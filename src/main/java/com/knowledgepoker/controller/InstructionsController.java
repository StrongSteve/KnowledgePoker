package com.knowledgepoker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by mrchampel on 13.02.2016.
 */

//TODO rename this class
@Controller
public class InstructionsController {

    @RequestMapping(value = "/instructions", method = RequestMethod.GET)
    public String instructions(HttpSession session) {
        session.setAttribute("profilePictureUrl", "https://graph.facebook.com/v2.5/1036956336370107/picture");
        return "instructions";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "about";
    }
}
