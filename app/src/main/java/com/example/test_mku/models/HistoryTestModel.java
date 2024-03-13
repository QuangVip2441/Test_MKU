package com.example.test_mku.models;

import com.google.type.DateTime;

public class HistoryTestModel {
    private String Testid;
    private String UserId;
    private int Results;
    private String Score;
    private DateTime Time;

    public HistoryTestModel() {
    }

    public HistoryTestModel(String testid, String userId, int results, String score, DateTime time) {
        Testid = testid;
        UserId = userId;
        Results = results;
        Score = score;
        Time = time;
    }

    public HistoryTestModel(String userId, int results, String score, DateTime time) {
        UserId = userId;
        Results = results;
        Score = score;
        Time = time;
    }

    public String getTestid() {
        return Testid;
    }

    public void setTestid(String testid) {
        Testid = testid;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getResults() {
        return Results;
    }

    public void setResults(int results) {
        Results = results;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public DateTime getTime() {
        return Time;
    }

    public void setTime(DateTime time) {
        Time = time;
    }
}
