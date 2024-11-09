package com.example.flashcardproject;

public class Flashcard {
    private String question;
    private String answer;

    // Default constructor for Firebase or other database usage
    public Flashcard() {
    }

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
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
}