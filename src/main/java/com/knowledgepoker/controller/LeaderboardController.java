package com.knowledgepoker.controller;

import com.knowledgepoker.service.leaderboard.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by mrchampel on 20.02.2016.
 */

@Controller
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @RequestMapping(value = "/leaderboard", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("leaderboard", leaderboardService.getAll());
        return "leaderboard";
    }

}
