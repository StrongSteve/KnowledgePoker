package com.knowledgepoker.controller;

import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.Question;
import com.knowledgepoker.service.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by starke on 08.02.2016.
 */
@Controller
public class QuestionsController {

    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model, @AuthenticationPrincipal CurrentUser user){
        model.addAttribute("questions", questionService.listAllQuestions(user));
        return "questions";
    }

    @PreAuthorize("@questionServiceImpl.canAccessQuestion(principal, #id)")
    @RequestMapping("/question/{id}")
    public String showQuestion(@PathVariable Long id, Model model){
        model.addAttribute("question", questionService.getQuestionById(id));
        return "questionshow";
    }

    @PreAuthorize("@questionServiceImpl.canAccessQuestion(principal, #id)")
    @RequestMapping("/question/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("question", questionService.getQuestionById(id));
        return "questionform";
    }

    @RequestMapping("/question/new")
    public String newQuestion(Model model){
        Question q = new Question();
        model.addAttribute("question", q);
        return "questionform";
    }

    @PreAuthorize("@questionServiceImpl.canAccessQuestion(principal, #id)")
    @RequestMapping("/question/remove/{id}")
    public String remove(@PathVariable Long id, Model model){
        questionService.deleteQuestion(id);
        return "redirect:/questions/";
    }

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public String saveQuestion(@Valid Question question, BindingResult bindingResult, Model model, @AuthenticationPrincipal CurrentUser user){
        if (bindingResult.hasErrors()) {
            return "questionform";
        }

        question.setSubmittedByUserId(user.getId());
        questionService.saveQuestion(question);
        return "redirect:/question/" + question.getId();
    }
}
