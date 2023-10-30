package com.ipsen.spine.controller.vo;

import jakarta.validation.constraints.NotNull;

public class QuestionForm {
    @NotNull
    public String textQuestion;
    @NotNull
    public Long platformId;
}
