package sample.model;

import com.google.gson.internal.LinkedTreeMap;

import java.util.UUID;

public class Exercise {

    private String name;
    private String question;
    private String correct;

    public Exercise() {

    }

    public Exercise(LinkedTreeMap map)
    {
        this.name = (String)map.get("name");
        this.question = (String)map.get("question");
        this.correct = (String)map.get("correct");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }
}