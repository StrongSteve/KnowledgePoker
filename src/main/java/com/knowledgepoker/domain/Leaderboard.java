package com.knowledgepoker.domain;

import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by mrchampel on 20.02.2016.
 */

@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @Column(name = "username")
    private String userName;

    @Column(name = "socialusername")
    private String socialUserName;

    @Column(name = "questionsamount")
    private int questionsAmount;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSocialUserName() {
        return socialUserName;
    }

    public void setSocialUserName(String socialUserName) {
        this.socialUserName = socialUserName;
    }

    public int getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(int questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public String getDisplayName() {
        if (!StringUtils.isEmpty(socialUserName)) {
            return socialUserName;
        }
        else
        {
            return userName;
        }
    }
}
