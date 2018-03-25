package com.hamoudi.j.classroomcommunity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jonathan on 23/02/2018.
 */

class Answer {

    @SerializedName("id")
    private int id;
    @SerializedName("text")
    private String label;
    @SerializedName("right")
    private boolean isCorrect;

    public Answer(int id, String label, boolean isCorrect) {
        this.id = id;
        this.label = label;
        this.isCorrect = isCorrect;
    }

    public Answer() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
