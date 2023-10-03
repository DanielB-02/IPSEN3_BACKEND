package com.example.damngirl4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "answer")

public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String textAnswer;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JsonBackReference
//    private Question question;

    public Long getId() {
        return id;
    }

    public String getTextAnswer() {
        return textAnswer;
    }

    public void setTextAnswer(String textAnswer) {
        this.textAnswer = textAnswer;
    }

//    public Question getQuestion() {
//        return question;
//    }

//    public void setQuestion(Question question) {
//        this.question = question;
//    }



}
