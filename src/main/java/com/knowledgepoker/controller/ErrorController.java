package com.knowledgepoker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by mrchampel on 12.02.2016.
 */
@Controller
public class ErrorController {

    @ExceptionHandler({RuntimeException.class})
    public String error() {
        return "error";
    }

}