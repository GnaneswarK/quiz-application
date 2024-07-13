package com.Quiz.QuizApp.repository;

import com.Quiz.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer>
{
       List<Question> findByCategory(String category);

       @Query(value = "Select * from Question q where q.category = :category ORDER BY Random() LIMIT :numQ ",nativeQuery = true)
       List<Question> findRandomQuestionByCategory(String category, int numQ);
}
