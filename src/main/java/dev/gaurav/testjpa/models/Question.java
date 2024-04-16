package dev.gaurav.testjpa.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.List;

/*
    Questions:
        CREATE TABLE questions (
            id INT PRIMARY KEY,
            title VARCHAR(255) NOT NULL,
            difficulty ENUM('Easy', 'Medium', 'Hard') NOT NULL,
            tags TEXT NOT NULL,
            companies TEXT NOT NULL,
            description TEXT NOT NULL,
            constraints TEXT NOT NULL,
            java_boilerplate_code TEXT NOT NULL,
            c_boilerplate_code TEXT NOT NULL,
            cpp_boilerplate_code TEXT NOT NULL,
            python_boilerplate_code TEXT NOT NULL,
            default_inputs TEXT NOT NULL,
            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
        );

 */
@Entity
@Table(name = "questions")
@Data
public class Question{
    public enum Difficulty {
        EASY("Easy"), MEDIUM("Medium"), HARD("Hard");
        private final String value;
        Difficulty(String value) {
            this.value = value;
        }
        public String getValue() {
            return value;
        }
    }
    @Id
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "int")
    private int id;
    @Column(name = "title", nullable = false, columnDefinition = "varchar(255)")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    Difficulty difficulty;
    @Column(name = "tags", nullable = false, columnDefinition = "Text")
    private String tags;
    @Column(name = "companies", nullable = false, columnDefinition = "Text")
    private String companies;
    @Column(name = "description", nullable = false, columnDefinition = "Text")
    private String description;
    @Column(name = "constraints", nullable = false, columnDefinition = "Text")
    private String constraints;
    @Column(name = "java_boilerplate_code", nullable = false, columnDefinition = "Text")
    private String javaBoilerplateCode;
    @Column(name = "c_boilerplate_code", nullable = false, columnDefinition = "Text")
    private String c11BoilerplateCode;
    @Column(name = "cpp_boilerplate_code", nullable = false, columnDefinition = "Text")
    private String cppBoilerplateCode;
    @Column(name = "python_boilerplate_code", nullable = true, columnDefinition = "Text")
    private String pythonBoilerplateCode;
    @Column(name = "default_inputs", nullable = true, columnDefinition = "Text")
    private String defaultInputs;
    @Column(name = "java_code", nullable = false, columnDefinition = "Text")
    private String javaCode;
    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp updatedAt;
    @Column(name = "extra_info", columnDefinition = "Text")
    private String extraInfo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<QuestionHint> hints;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Example> examples;
    public Question() {
    }
    @PrePersist
    protected void onCreate() {
        createdAt = new Timestamp(System.currentTimeMillis());
    }
    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }
}