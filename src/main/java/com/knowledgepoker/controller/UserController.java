package com.knowledgepoker.controller;

import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.service.question.QuestionService;
import com.knowledgepoker.service.user.UserService;
import com.knowledgepoker.transfer.ChangePasswordForm;
import com.knowledgepoker.transfer.UserCreateForm;
import com.knowledgepoker.transfer.UserProfileForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final QuestionService questionService;

    @Autowired
    public UserController(UserService userService, QuestionService questionService) {
        this.userService = userService;
        this.questionService = questionService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/user/remove/{id}")
    public String removeUser(@PathVariable Long id){
        userService.removeUser(id);
        return "redirect:/users";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/user/new")
    public String newUser(Model model){
        model.addAttribute("usercreateform", new UserCreateForm());
        return "userform";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "user", method = RequestMethod.POST)
    public String saveUser(@Valid @ModelAttribute(value = "usercreateform") UserCreateForm userCreateForm, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            return "userform";
        }

        userService.saveUser(userCreateForm);
        return "redirect:/users";
    }

    @RequestMapping("/user/changePasswordForm")
    public String changePassword(Model model){
        model.addAttribute("changepasswordform", new ChangePasswordForm());
        return "userchangepassword";
    }

    @RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
    public String savePassword(@Valid @ModelAttribute(value = "changepasswordform") ChangePasswordForm changepasswordform, BindingResult bindingResult, Model model, @AuthenticationPrincipal CurrentUser user){
        if (bindingResult.hasErrors()) {
            return "userchangepassword";
        }

        userService.changePassword(user.getId(), changepasswordform.getPassword());
        return "redirect:/index";
    }

    @RequestMapping("/user/profile")
    public String showProfile(Model model, @AuthenticationPrincipal CurrentUser user) {
        model.addAttribute("numberOfQuestions", questionService.countQuestionsByUser(user));
        model.addAttribute("overallAmountOfQuestions", questionService.countQuestions());
        return "userprofile";
    }

}
