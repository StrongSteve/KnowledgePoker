package com.knowledgepoker.controller;

import com.knowledgepoker.domain.Role;
import com.knowledgepoker.service.exception.NoMoreQuestionException;
import com.knowledgepoker.service.user.SocialConnectionService;
import com.knowledgepoker.transfer.CurrentUser;
import com.knowledgepoker.domain.Game;
import com.knowledgepoker.domain.Question;
import com.knowledgepoker.domain.User;
import com.knowledgepoker.service.question.QuestionService;
import com.knowledgepoker.service.game.GameService;
import com.knowledgepoker.service.user.UserService;
import com.knowledgepoker.transfer.GameCreateForm;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by starke on 14.02.2016.
 */
@Controller
public class GameController {

    private GameService gameService;
    private QuestionService questionService;
    private UserService userService;
    private SocialConnectionService socialConnectionService;

    @Autowired
    public void setGameService(GameService gameService) {
        this.gameService = gameService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) { this.questionService = questionService; };

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSocialConnectionService(SocialConnectionService socialConnectionService) {
        this.socialConnectionService = socialConnectionService;
    }

    @RequestMapping(value = "/games", method = RequestMethod.GET)
    public String list(Model model, @AuthenticationPrincipal CurrentUser user){
        model.addAttribute("games", gameService.getAllAvailableGames(user));
        return "games";
    }

    @PreAuthorize("@gameServiceImpl.canAccessGame(principal, #id)")
    @RequestMapping("/game/remove/{id}")
    public String remove(@PathVariable Long id, Model model){
        gameService.deleteGame(id);
        return "redirect:/games/";
    }

    @PreAuthorize("@gameServiceImpl.canAccessGame(principal, #gameId)")
    @RequestMapping("/game/{gameId}/nextquestion")
    public String showRandomQuestion(Model model, @PathVariable Long gameId){
        Game g = gameService.getGameById(gameId);

        //Get next question
        Question q;
        try {
            //Users can only see their own questions
            if (Role.USER.equals(userService.getUserById(g.getCreatedByUserId()).get().getRole())) {
                q = questionService.getRandomQuestionFromUserAndExcludingQuestionIds(g.getCreatedByUserId(), g.getPlayedQuestionIds());
            }
            else {
                /*//Determine who is the next dealer and present one of his questions
                List<Long> playerIds = g.getPlayerIds();
                if (playerIds.size() > 0) {
                    long questionsPlayed = g.getPlayedQuestionIds().size();
                    long currentDealerId = -1L;
                    currentDealerId = playerIds.get((int) questionsPlayed % playerIds.size());
                    q = questionService.getRandomQuestionFromUserAndExcludingQuestionIds(currentDealerId, g.getPlayedQuestionIds());
                }
                else {
                    q = questionService.getRandomQuestionExcludingUserAndQuestionIds(g.getPlayerIds(), g.getPlayedQuestionIds());
                }*/
                q = questionService.getRandomQuestionExcludingUserAndQuestionIds(g.getPlayerIds(), g.getPlayedQuestionIds());
            }
        } catch (NoMoreQuestionException nmqex) {
            g.setFinished(true);
            gameService.saveGame(g);
            return "nomorequestions";
        }
        //Update game
        g.addPlayedQuestion(q);
        gameService.saveGame(g);

        model.addAttribute("question", q);
        model.addAttribute("gameid", g.getId());
        Optional<User> user = userService.getUserById(q.getSubmittedByUserId());
        if (user.isPresent() && user.get().getSocialUser()) {
            model.addAttribute("questionSubmittedBySocialConnection", socialConnectionService.getSocialConnectionForUserWithName(user.get().getUserName()));
        }
        else if (user.isPresent()) {
            model.addAttribute("questionSubmittedByUser", user.get());
        }
        return "gameshowquestion";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping("/game/new")
    public String newGame(Model model, @AuthenticationPrincipal CurrentUser user){
        GameCreateForm g = new GameCreateForm();
        g.setCreatedByUserId(user.getId());
        model.addAttribute("game", g);
        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        users.addAll(userService.getAllUsers());
        model.addAttribute("players", users);
        return "gameform";
    }

    @RequestMapping("/game/startQuickGame")
    public String quickGame(Model model, @AuthenticationPrincipal CurrentUser user){
        Game g = new Game();
        g.setName("Schnelles Spiel " +new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        g.setCreatedByUserId(user.getId());
        g = gameService.saveGame(g);
        return "redirect:/game/" +g.getId() +"/nextquestion";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "game", method = RequestMethod.POST)
    public String saveGame(@Valid @ModelAttribute(value = "game") GameCreateForm game, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()) {
            model.addAttribute("players", userService.getAllUsers());
            return "gameform";
        }

        Game g = new Game();
        g.setName(game.getName());
        g.setCreatedByUserId(game.getCreatedByUserId());

        for (Long userId : game.getSelectedPlayers()) {
            if (userId != null) {
                User u = new User();
                u.setId(userId);
                g.addPlayer(u);
            }
        }

        g = gameService.saveGame(g);
        return "redirect:/game/" +g.getId() +"/nextquestion";
    }
}
