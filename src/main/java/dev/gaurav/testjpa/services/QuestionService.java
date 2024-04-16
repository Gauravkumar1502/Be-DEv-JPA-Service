package dev.gaurav.testjpa.services;

import dev.gaurav.testjpa.DTO.CustomQuestion;
import dev.gaurav.testjpa.exceptions.AlreadyExistsException;
import dev.gaurav.testjpa.exceptions.DoesNotExistException;
import dev.gaurav.testjpa.models.Question;
import dev.gaurav.testjpa.repositorys.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final int MAX_QUESTIONS = 9;
    QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Optional<Question> getQuestionById(int id) {
        return questionRepository.findById(id);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void addQuestion(Question question) throws AlreadyExistsException {
        if (questionRepository.existsById(question.getId())) {
            throw new AlreadyExistsException("Question with id " + question.getId() + " already exists");
        }
        questionRepository.save(question);
    }
    public void updateQuestion(Question question) throws DoesNotExistException {
        if (!questionRepository.existsById(question.getId())) {
            throw new DoesNotExistException("Question with id " + question.getId() + " does not exist");
        }
        questionRepository.update(
            question.getTitle(),
            question.getDifficulty().getValue().toUpperCase(),
            question.getTags(),
            question.getCompanies(),
            question.getDescription(),
            question.getConstraints(),
            question.getJavaBoilerplateCode(),
            question.getC11BoilerplateCode(),
            question.getCppBoilerplateCode(),
            question.getPythonBoilerplateCode(),
            question.getDefaultInputs(),
            question.getExtraInfo(),
            question.getId());
    }
    public void deleteQuestionById(int id) throws DoesNotExistException {
        if (!questionRepository.existsById(id)) {
            throw new DoesNotExistException("Question with id " + id + " does not exist");
        }
        questionRepository.deleteById(id);
    }

    public List<CustomQuestion> getQuestionTitles() {
        return questionRepository.getQuestionTitles();
    }

    public Optional<Question> getRandomQuestion() {
        int randomId = (int) (Math.random() * MAX_QUESTIONS) + 1;
        return questionRepository.findById(randomId);
    }
}
