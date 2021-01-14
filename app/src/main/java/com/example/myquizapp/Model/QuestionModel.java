package com.example.myquizapp.Model;

import com.google.firebase.firestore.DocumentId;

public class QuestionModel {

    @DocumentId
    String questionid;
    String question, answer, optiona, optionb, optionc;
    Long timer;


    // for firebase
    public QuestionModel() {
    }

    public QuestionModel(String questionid, String question, String answer, String optiona, String optionb, String optionc, Long timer) {
        this.questionid = questionid;
        this.question = question;
        this.answer = answer;
        this.optiona = optiona;
        this.optionb = optionb;
        this.optionc = optionc;
        this.timer = timer;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOptiona() {
        return optiona;
    }

    public void setOptiona(String optiona) {
        this.optiona = optiona;
    }

    public String getOptionb() {
        return optionb;
    }

    public void setOptionb(String optionb) {
        this.optionb = optionb;
    }

    public String getOptionc() {
        return optionc;
    }

    public void setOptionc(String optionc) {
        this.optionc = optionc;
    }

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}

