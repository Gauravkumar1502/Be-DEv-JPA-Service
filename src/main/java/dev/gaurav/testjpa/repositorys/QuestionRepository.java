package dev.gaurav.testjpa.repositorys;

import dev.gaurav.testjpa.DTO.CustomQuestion;
import dev.gaurav.testjpa.models.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    @Modifying
    @Transactional
    @Query(value = "update Questions q set q.title = ?1, q.difficulty = ?2, q.tags = ?3, q.companies = ?4, q.description = ?5, q.constraints = ?6, q.java_boilerplate_code = ?7, q.c_boilerplate_code = ?8, q.cpp_boilerplate_code = ?9, q.python_boilerplate_code = ?10, q.default_inputs = ?11 , q.extra_info = ?12 where q.id = ?13",
        nativeQuery = true)
    void update(String title,
        String difficulty,
        String tags,
        String companies,
        String description,
        String constraints,
        String javaBoilerplateCode,
        String c11BoilerplateCode,
        String cppBoilerplateCode,
        String pythonBoilerplateCode,
        String defaultInputs,
        String extraInfo,
        int id);
//    get only the specific columns status(solved by user or not), id, title, difficulty
    @Query(value = "SELECT q.id AS id, q.title AS title, q.difficulty AS difficulty FROM Questions q", nativeQuery = true)
    List<CustomQuestion> getQuestionTitles();
}
