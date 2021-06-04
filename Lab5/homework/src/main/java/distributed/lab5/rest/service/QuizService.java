package distributed.lab5.rest.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import distributed.lab5.rest.domain.Form;
import distributed.lab5.rest.domain.FullForm;
import distributed.lab5.rest.domain.Question;
import distributed.lab5.rest.domain.UrlList;
import distributed.lab5.rest.util.HtmlCreator;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuizService {
    private final RestTemplate restTemplate;

    public QuizService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getUrl(Form form) {
        return "https://opentdb.com/api.php?amount=" + (form.getAmount() == null ? 0 : form.getAmount()) +
                (form.getCategory().equals("any") ? "" : "&category=" + form.getCategory()) +
                (form.getDifficulty().equals("any") ? "" : "&difficulty=" + form.getDifficulty()) +
                (form.getType().equals("any") ? "" : "&type=" + form.getType());
    }

    private List<Form> getForms(FullForm fullForm) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<Form> forms = new ArrayList<>();
        Class<?> c = Class.forName("distributed.lab5.rest.domain.FullForm");
        for (int i = 1; i <= 5; i++) {
            Form form = new Form();
            form.setAmount((Integer) c.getDeclaredMethod("getAmount" + i).invoke(fullForm));
            form.setCategory((String) c.getDeclaredMethod("getCategory" + i).invoke(fullForm));
            form.setDifficulty((String) c.getDeclaredMethod("getDifficulty" + i).invoke(fullForm));
            form.setType((String) c.getDeclaredMethod("getType" + i).invoke(fullForm));
            forms.add(form);
        }
        return forms;
    }

    public String addFormHTML(FullForm fullForm) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        List<Form> forms = getForms(fullForm);
        StringBuilder html = new StringBuilder("<html>\n" + "<header><title>Trivia Quiz</title></header>\n" +
                "<body>" + "Settings for quiz:<br>");
        HtmlCreator htmlCreator = new HtmlCreator();

        for (int i = 0; i < 5; i++) {
            html.append(htmlCreator.getHtmlAddForm(forms.get(i)));
        }
        html.append("<form method=\"POST\" action=\"/getQuiz\">");
        for (int i = 0; i < 5; i++) {
            String url = getUrl(forms.get(i));
            html.append(htmlCreator.getHtmlFormUrl(url, i + 1));
        }
        html.append("<input type=\"submit\"></form>").append("</body>\n").append("</html>");
        return html.toString();
    }

    private JsonObject getJsonQuiz(String url) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

        return gson.fromJson(restTemplate
                .exchange(url, HttpMethod.GET, entity, String.class)
                .getBody(), JsonObject.class);
    }

    private Question getQuestion(JsonElement question) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
        return gson.fromJson(question, Question.class);
    }

    public String getQuiz(UrlList urlList) throws ClassNotFoundException, InterruptedException {
        StringBuilder html = new StringBuilder("<html>\n" + "<header><title>Trivia Quiz</title></header>\n" +
                "<body>");

        List<Thread> threads = new ArrayList<>();

        Class<?> c = Class.forName("distributed.lab5.rest.domain.UrlList");
        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            threads.add(new Thread(() -> {
                JsonObject jsonObject = null;
                try {
                    jsonObject = getJsonQuiz((String) c.getDeclaredMethod("getUrl" + finalI).invoke(urlList));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                assert jsonObject != null;
                HtmlCreator htmlCreator = new HtmlCreator();
                for (var question : jsonObject.get("results").getAsJsonArray()) {
                    String q = htmlCreator.getHtmlQuestion(getQuestion(question));
                    html.append(q);
                }
            }));
            threads.get(i - 1).start();
        }

        for (int i = 0; i < 5; i++) {
            threads.get(i).join();
        }

        html.append("</body>\n" + "</html>");

        return html.toString();
    }
}
