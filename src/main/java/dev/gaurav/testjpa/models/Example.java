package dev.gaurav.testjpa.models;

import jakarta.persistence.*;
import lombok.*;

/*
    CREATE TABLE examples (
        id INT AUTO_INCREMENT PRIMARY KEY,
        question_id INT,
        input TEXT NOT NULL,
        output TEXT NOT NULL,
        explanation TEXT NOT NULL,
        FOREIGN KEY (question_id) REFERENCES questions(id)
    );
 */
@Entity
@Table(name = "examples")
@Data
public class Example{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false, nullable = false, columnDefinition = "int")
        int id;
        @Column(name = "input", nullable = false, columnDefinition = "Text")
        String input;
        @Column(name = "output", nullable = false, columnDefinition = "Text")
        String output;
        @Column(name = "explanation", nullable = true,  columnDefinition = "Text")
        String explanation;
}
