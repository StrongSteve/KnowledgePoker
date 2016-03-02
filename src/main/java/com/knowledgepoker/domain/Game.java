package com.knowledgepoker.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.util.*;

/**
 * Created by starke on 14.02.2016.
 */
@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq_gen")
    @SequenceGenerator(name="game_seq_gen", sequenceName="seq_game")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "creationtimestamp")
    private Date creationTimeStamp;

    @Column(name = "lastmodifiedtimestamp")
    private Date lastModifiedTimeStamp;

    //TODO: NotEmpty wird nicht korrekt ausgewertet
    @NotEmpty(message = "Der Name des Spiels fehlt!")
    @Column(name = "name")
    private String name;

    @Column(name = "createdbyuserid")
    private Long createdByUserId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="GAME2PLAYER",
            joinColumns=@JoinColumn(name="gameid", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="playerid", referencedColumnName="ID"))
    private Set<User> players;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="GAME2QUESTION",
            joinColumns=@JoinColumn(name="gameid", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="questionid", referencedColumnName="ID"))
    private Set<Question> playedQuestions;

    @Column(name = "finished")
    private boolean finished;

    public Game() {
        players = new HashSet<>();
        playedQuestions = new HashSet<>();
        finished = false;
    }

    @PreUpdate
    @PrePersist
    public void setDates() {
        if (creationTimeStamp == null) {
            creationTimeStamp = new Date();
        }
        lastModifiedTimeStamp = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public Date getLastModifiedTimeStamp() {
        return lastModifiedTimeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public Set<User> getPlayers() {
        return players;
    }

    public void addPlayer(User player) {
        players.add(player);
    }

    public Set<Question> getPlayedQuestions() {
        return playedQuestions;
    }

    public void addPlayedQuestion(Question question) {
        playedQuestions.add(question);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getPlayerNames() {
        List<String> names = new ArrayList<>();
        for (User player : getPlayers()) {
            names.add(player.getDisplayName());
        }
        return StringUtils.join(names, "; ");
    }

    public List<Long> getPlayerIds() {
        List<Long> ids = new ArrayList<>();
        for (User player : getPlayers()) {
            ids.add(player.getId());
        }
        return ids;
    }

    public List<Long> getPlayedQuestionIds() {
        List<Long> ids = new ArrayList<>();
        for (Question question : getPlayedQuestions()) {
            ids.add(question.getId());
        }
        return ids;
    }

}
