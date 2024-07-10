package com.Quiz.QuizApp.Controller;

import com.Quiz.QuizApp.Model.QuestionWrapper;
import com.Quiz.QuizApp.Model.Response;
import com.Quiz.QuizApp.Service.QuizServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz/")
public class QuizController {
    @Autowired
    QuizServices quizServices;
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category,@RequestParam int numQ,@RequestParam String title){
        return quizServices.createQuiz(category,numQ,title);
    }
    @GetMapping("exam/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable int id){
        return quizServices.getQuizQuestions(id);
    }
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submittedQuestions(@PathVariable int id,@RequestBody List<Response> responses){
        return quizServices.calculatedResult(id,responses);
    }
}
