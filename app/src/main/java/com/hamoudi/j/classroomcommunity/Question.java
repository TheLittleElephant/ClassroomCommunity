package com.hamoudi.j.classroomcommunity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Jonathan on 23/02/2018.
 */

public class Question {

    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String label;
    @SerializedName("answers")
    private List<Answer> answers = new ArrayList<>();


    public Question(int id, List<Answer> answers, String label) {
        this.id = id;
        this.answers = answers;
        this.label = label;
    }

    public Question() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Answer getRightAnswer() {
        for(Answer answer : answers) {
            if(answer.isCorrect()) {
                return answer;
            }
        }
        return null;
    }

    public static Question getQuestionFromJson(String json) {
        Gson gson = new Gson();
        Question question = gson.fromJson(json, Question.class);
        return question;
    }


    public static List<Question> getQuestionsFromJson(String json) {
        Gson gson = new Gson();
        Type type = new TypeToken< List<Question>>(){}.getType();
        List<Question> questions = gson.fromJson(json, type);
        return questions;
    }
}
