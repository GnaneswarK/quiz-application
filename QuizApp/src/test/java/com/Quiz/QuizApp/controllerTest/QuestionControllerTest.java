package com.Quiz.QuizApp.controllerTest;

import com.Quiz.QuizApp.Service.QuestionService;
import com.Quiz.QuizApp.controller.QuestionController;
import com.Quiz.QuizApp.model.Question;
import com.Quiz.QuizApp.repository.QuestionRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(QuestionController.class) // Assuming your controller is named QuestionController
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionRepo questionRepo;

    private Question question;

    @InjectMocks
    QuestionController questionController;
    @BeforeEach
    public void setUp() {
        List<Question> questions = new ArrayList<>(Arrays.asList(
                new Question(1, "What is the correct syntax to print a message in Java?", "System.out.println(\"Hello World\");", "print(\"Hello World\");", "echo \"Hello World\";", "console.log(\"Hello World\");", "System.out.println(\"Hello World\");", "Easy", "Java"),
                new Question(2, "Which keyword is used to create a class in Python?", "class", "def", "new", "object", "class", "Easy", "Python"),
                new Question(3, "How do you declare a variable in Kotlin?", "var name: String = \"Kotlin\"", "String name = \"Kotlin\";", "let name = \"Kotlin\"", "declare name = \"Kotlin\"", "var name: String = \"Kotlin\"", "Easy", "Kotlin")
        ));

        List<Question> javaQuestions = questions.stream()
                .filter(question -> "Java".equals(question.getCategory()))
                .toList();

        question = new Question(25, "What is the correct syntax to print a message in Java?", "System.out.println(\"Hello World\");", "print(\"Hello World\");", "echo \"Hello World\";", "console.log(\"Hello World\");", "System.out.println(\"Hello World\");", "Easy", "Java");

        Mockito.when(questionRepo.findAll()).thenReturn(questions);
        Mockito.when(questionService.getAllQuestions()).thenReturn(questions);
        Mockito.when(questionRepo.findByCategory("Java")).thenReturn(javaQuestions);
        Mockito.when(questionService.getAllQuestionsByCategory("Java")).thenReturn(javaQuestions);
        Mockito.when(questionRepo.save(any(Question.class))).thenReturn(question);
        Mockito.when(questionService.addQuestion(any(Question.class))).thenReturn(question);
    }

    @Test
    void getAllQuestionsTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/question/allQuestions")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].category").value("Kotlin"));
    }
    @Test
    void getAllQuestionsByCategoryTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/question/category/Java")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
    @Test
    void AddQuestionsByCategoryTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/question/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(25))
                .andExpect(jsonPath("$.category").value("Java"));
    }
}
