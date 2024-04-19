package dev.gaurav.testjpa.controllers;

import dev.gaurav.testjpa.DTO.CustomQuestion;
import dev.gaurav.testjpa.exceptions.AlreadyExistsException;
import dev.gaurav.testjpa.exceptions.DoesNotExistException;
import dev.gaurav.testjpa.models.Question;
import dev.gaurav.testjpa.services.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/question")
@CrossOrigin
public class QuestionController {
    private final QuestionService questionService;

    QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test successful");
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        try {
            questionService.addQuestion(question);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Question added successfully");
    }
    @PostMapping("/add-all")
    public ResponseEntity<String> addAllQuestions(@RequestBody List<Question> questions) {
        try {
            for (Question question : questions) {
                questionService.addQuestion(question);
            }
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok("Questions added successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        try {
            questionService.updateQuestion(question);
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Question updated successfully");
    }

    @GetMapping("/find")
    public ResponseEntity<Question> getQuestionById(@RequestParam(value = "id") int id) {
        Optional<Question> questionById = questionService.getQuestionById(id);
        return ResponseEntity.ok(questionById.orElse(null));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/titles")
    public ResponseEntity<List<CustomQuestion>> getQuestionsTitleDifficulty() {
        List<CustomQuestion> questions = questionService.getQuestionTitles();
        return ResponseEntity.ok(questions);
    }
    @GetMapping("/random")
    public ResponseEntity<Question> getRandomQuestion() {
        Optional<Question> question = questionService.getRandomQuestion();
        return ResponseEntity.ok(question.orElse(null));
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteQuestion(@RequestParam(value = "id") int id) {
        try {
            questionService.deleteQuestionById(id);
        } catch (DoesNotExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok("Question deleted successfully");
    }
}
