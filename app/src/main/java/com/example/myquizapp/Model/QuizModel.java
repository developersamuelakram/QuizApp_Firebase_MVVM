package com.example.myquizapp.Model;

import com.google.firebase.firestore.DocumentId;

public class QuizModel {


    @DocumentId
    String quizid;
    String image, quizname, visibility, level, desc;
    Long question;


    // empty constructor for firebase
    public QuizModel() {
    }

    public QuizModel(String quizid, String image, String quizname, String visibility, String level, String desc, Long question) {
        this.quizid = quizid;
        this.image = image;
        this.quizname = quizname;
        this.visibility = visibility;
        this.level = level;
        this.desc = desc;
        this.question = question;
    }


    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getQuizname() {
        return quizname;
    }

    public void setQuizname(String quizname) {
        this.quizname = quizname;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Long getQuestion() {
        return question;
    }

    public void setQuestion(Long question) {
        this.question = question;
    }
}
