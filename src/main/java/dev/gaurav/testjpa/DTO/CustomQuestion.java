package dev.gaurav.testjpa.DTO;

import dev.gaurav.testjpa.models.Question;

public interface CustomQuestion {
    int getId();
    String getTitle();
    Question.Difficulty getDifficulty();
}