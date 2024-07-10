package com.Quiz.QuizApp.Service;

import com.Quiz.QuizApp.Model.Question;
import com.Quiz.QuizApp.Repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionService
{
    @Autowired
    QuestionRepo questionRepo;
    public List<Question> getAllQuestions() {
        return questionRepo.findAll();
    }

    public List<Question> getAllQuestionsByCategory(String categoryType)
    {
        return questionRepo.findByCategory(categoryType);
    }

    public Question addQuestion(Question question)
    {
        questionRepo.save(question);
        return question;
    }

    public void deleteQuestionById(int id)
    {
        questionRepo.deleteById(id);
    }
}
