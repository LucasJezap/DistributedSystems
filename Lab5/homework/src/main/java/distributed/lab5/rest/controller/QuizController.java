package distributed.lab5.rest.controller;

import distributed.lab5.rest.domain.FullForm;
import distributed.lab5.rest.domain.UrlList;
import distributed.lab5.rest.message.ErrorMessage;
import distributed.lab5.rest.service.QuizService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

@RestController
public class QuizController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoggerFactory.class);
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(value = "/getQuiz")
    public String getQuiz(@ModelAttribute("url") UrlList urlList) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InterruptedException {
        return quizService.getQuiz(urlList);
    }

    @PostMapping(value = "/addForm")
    public String addForm(@ModelAttribute("form") FullForm fullForm, BindingResult result) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        if (result.hasErrors()) {
            return ErrorMessage.INCORRECT_FORM.asJson();
        }
        return quizService.addFormHTML(fullForm);
    }
}
