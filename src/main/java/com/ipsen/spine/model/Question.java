package com.ipsen.spine.model;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String textQuestion;

    @ManyToOne
    private Platform platform;

    public Long getId() {
        return id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

}
