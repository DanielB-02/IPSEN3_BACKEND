package com.ipsen.spine.controller.vo;

import jakarta.validation.constraints.NotNull;

public class AnswerForm {
    @NotNull
    public String textAnswer;
    @NotNull
    public Long questionId;

    @NotNull
    public int score;
}
