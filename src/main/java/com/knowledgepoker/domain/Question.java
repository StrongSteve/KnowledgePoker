package com.knowledgepoker.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * Created by starke on 11.02.2016.
 */
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "question_seq_gen")
    @SequenceGenerator(name="question_seq_gen", sequenceName="seq_question")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotEmpty(message = "Deine Frage fehlt!")
    @Column(name = "question", nullable = false)
    private String questionText;

    @NotEmpty(message = "Deine Antwort fehlt!")
    @Column(name = "answer", nullable = false)
    private String answer;

    @NotEmpty(message = "Dein erster Hinweis fehlt!")
    @Column(name = "firsthint", nullable = false)
    private String firstHint;

    @NotEmpty(message = "Dein zweiter Hinweis fehlt!")
    @Column(name = "secondhint", nullable = false)
    private String secondHint;

    @Column(name = "submittedbyuserid")
    private Long submittedByUserId;

    public Question() {}

    public Question(String questionText, String answer) {
        this.questionText = questionText;
        this.answer = answer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmittedByUserId() {
        return submittedByUserId;
    }

    public void setSubmittedByUserId(Long submittedByUserId) {
        this.submittedByUserId = submittedByUserId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getFirstHint() {
        return firstHint;
    }

    public void setFirstHint(String firstHint) {
        this.firstHint = firstHint;
    }

    public String getSecondHint() {
        return secondHint;
    }

    public void setSecondHint(String secondHint) {
        this.secondHint = secondHint;
    }

    @Override
    public String toString() {
        return String.format("Question[id=%d, questionText='%s', answer='%s']", id, questionText, answer);
    }
}
