package com.knowledgepoker.controller.advice;

import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.service.currentuser.CurrentUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);

    private CurrentUserDetailsService currentUserDetailsService;

    @Autowired
    public void setCurrentUserDetailsService(CurrentUserDetailsService currentUserDetailsService) {
        this.currentUserDetailsService = currentUserDetailsService;
    }

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
        return (CurrentUser) authentication.getPrincipal();
    }

}
