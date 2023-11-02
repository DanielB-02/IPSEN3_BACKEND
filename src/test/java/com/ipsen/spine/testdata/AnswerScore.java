package com.ipsen.spine.testdata;

public class AnswerScore {
    private final int score;
    private final String text;

    public AnswerScore(int score, String text) {
        this.score = score;
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public String getText() {
        return text;
    }
}
