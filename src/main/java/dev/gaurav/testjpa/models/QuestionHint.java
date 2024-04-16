package dev.gaurav.testjpa.models;

import jakarta.persistence.*;
import lombok.*;

/*
    CREATE TABLE question_hints (
        id INT AUTO_INCREMENT PRIMARY KEY,
        question_id INT not null,
        hint TEXT not null,
        FOREIGN KEY (question_id) REFERENCES questions(id)
    );
 */
@Entity
@Table(name = "question_hints")
@Data
public class QuestionHint{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false, nullable = false, columnDefinition = "int")
        int id;

        @Column(name = "hint", nullable = false, columnDefinition = "Text")
        String hint;
}