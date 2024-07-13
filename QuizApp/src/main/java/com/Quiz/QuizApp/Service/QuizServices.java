package com.Quiz.QuizApp.Service;

import com.Quiz.QuizApp.model.Response;
import com.Quiz.QuizApp.model.Question;
import com.Quiz.QuizApp.model.QuestionWrapper;
import com.Quiz.QuizApp.model.Quiz;
import com.Quiz.QuizApp.repository.QuestionRepo;
import com.Quiz.QuizApp.repository.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizServices
{
    @Autowired
    QuizRepo quizRepo;
    @Autowired
    QuestionRepo questionRepo;
    public ResponseEntity<String> createQuiz(String category, int numQ, String title)
    {
        List<Question> questions = questionRepo.findRandomQuestionByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizRepo.save(quiz);
        return new ResponseEntity<>("Successfully created quiz question",HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id)
    {
        Optional<Quiz> quizList = quizRepo.findById(id);
        List<Question> questionsFromDB = quizList.get().getQuestion();
        List<QuestionWrapper> questionForUsers = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper wrapper = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionForUsers.add(wrapper);
        }
        return new ResponseEntity<>(questionForUsers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculatedResult(int id, List<Response> responses)
    {
        Quiz quiz = quizRepo.findById(id).get();
        List<Question> questionsFromDB = quiz.getQuestion();
        int right = 0;
        int i = 0;
        for(Response r : responses) {
            if (r.getResponse().equals(questionsFromDB.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
