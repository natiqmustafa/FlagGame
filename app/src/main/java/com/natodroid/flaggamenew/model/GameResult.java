package com.natodroid.flaggamenew.model;

/**
 * Created by natiqmustafa on 09.03.2017.
 */

public class GameResult {
    private int score;
    private int questionCount;
    private int level;
    private long time;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "GameResult{" +
                "score=" + score +
                ", questionCount=" + questionCount +
                ", level=" + level +
                ", time=" + time +
                '}';
    }
}
