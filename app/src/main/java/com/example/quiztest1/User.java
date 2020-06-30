package com.example.quiztest1;

public class User {

    public String userName;
    public int score;
    public int ranking;

    public User() {
    }

    public User(String userName, int score, int ranking) {
        this.userName = userName;
        this.score = score;
        this.ranking = ranking;
    }


    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}