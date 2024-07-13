package com.Quiz.QuizApp.Service;

import com.Quiz.QuizApp.model.Question;
import com.Quiz.QuizApp.repository.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionService
{
    @Autowired
    private QuestionRepo questionRepo;
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
        if(questionRepo.findById(id).isPresent())
            questionRepo.deleteById(id);
    }
}
