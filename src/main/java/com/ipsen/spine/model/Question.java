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
