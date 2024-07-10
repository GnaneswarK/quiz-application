package com.Quiz.QuizApp.Controller;

import com.Quiz.QuizApp.Model.Question;
import com.Quiz.QuizApp.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question/")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions")
    public List<Question> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{categoryType}")
    public List<Question> getAllQuestionsByCategory(@PathVariable String categoryType) {
        return questionService.getAllQuestionsByCategory(categoryType);
    }

    @PostMapping("add")
    public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
        return new ResponseEntity<>(questionService.addQuestion(question), HttpStatus.OK);
    }

    @DeleteMapping("delete/{Id}")
    public ResponseEntity<?> deleteQuestionById(@PathVariable int Id) {
        questionService.deleteQuestionById(Id);
        return ResponseEntity.ok().build();
    }

}
