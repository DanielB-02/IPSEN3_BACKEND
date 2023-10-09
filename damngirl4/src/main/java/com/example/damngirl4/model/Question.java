package com.example.damngirl4.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String textQuestion;
//    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
//    @JsonManagedReference
//    private Answer answer;

    public Long getId() {
        return id;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion) {
        this.textQuestion = textQuestion;
    }

//    public Answer getAnswer() {
//        return answer;
//    }

//    public void setAnswer(Answer answer) {
//        this.answer = answer;
//    }

}
