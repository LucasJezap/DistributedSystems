package distributed.lab5.rest.util;

import distributed.lab5.rest.domain.Form;
import distributed.lab5.rest.domain.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class HtmlCreator {
    public int count = new Random().nextInt(10000) + new Random().nextInt(1000);

    public String getHtmlAddForm(Form form) {
        return "<br> Number of questions: " + form.getAmount() + "<br>" +
                "Category: " + form.getCategory() + "<br>" +
                "Difficulty: " + form.getDifficulty() + "<br>" +
                "Type: " + form.getType() + "<br>";
    }

    public String getHtmlFormUrl(String url, int i) {
        return "<label for=\"url" + i + "\"></label>" + "<input type=hidden id=\"url" + i + "\" name=\"url" + i + "\" value=\"" + url + "\">";
    }

    private String getHtmlDivAnswer(Question question, String answer) {
        return "  <div id='block-" + count + "' style='padding: 10px;'>\n" +
                "    <label for='option-" + count + "' style=' padding: 5px; font-size: 2.5rem;'>\n" +
                "      <input type='radio' name='option' value='" + answer + "' id='option-" + count + "' style='transform: scale(1.6); margin-right: 10px; vertical-align: middle; margin-top: -2px;' />\n" +
                "      " + answer + "</label>\n" +
                "    <span id='result-" + count++ + "'></span>\n" +
                "  </div>\n" +
                "  <hr />\n" +
                "\n";
    }

    private Set<String> getQuestions(Question question) {
        List<String> questions = new ArrayList<>();
        questions.add(question.getCorrect_answer());
        questions.addAll(question.getIncorrect_answers());
        return questions.stream().map(s -> getHtmlDivAnswer(question, s)).collect(Collectors.toSet());
    }

    private String getMultipleQuestion() {
        return "<button type='button' onclick='displayAnswer" + count + "()' style='width: 100px;" +
                " height: 40px; border-radius: 3px; background-color: lightblue; " +
                "font-weight: 700;'>Submit</button>\n" + "</div>\n" + "<a id='showanswer" + count + "'></a>\n" +
                "<script>\n" + "  //    The function evaluates the answer and displays result\n" + " " +
                " function displayAnswer" + count + "() {\n" + "    if (document.getElementById('option-" + (count - 4) + "').checked) " +
                "{\n" + "      document.getElementById('block-" + (count - 4) + "').style.border = '3px solid limegreen'\n" +
                "      document.getElementById('result-" + (count - 4) + "').style.color = 'limegreen'\n" + "      " +
                "document.getElementById('result-" + (count - 4) + "').innerHTML = 'Correct!'\n" + "    }\n" + "   " +
                " if (document.getElementById('option-" + (count - 3) + "').checked) {\n" + "     " +
                " document.getElementById('block-" + (count - 3) + "').style.border = '3px solid red'\n" + "     " +
                " document.getElementById('result-" + (count - 3) + "').style.color = 'red'\n" + "     " +
                " document.getElementById('result-" + (count - 3) + "').innerHTML = 'Incorrect!'\n" + "    " +
                "  showCorrectAnswer" + count + "()\n" + "    }\n" + "    if (document.getElementById('option-" + (count - 2) + "').checked) " +
                "{\n" + "      document.getElementById('block-" + (count - 2) + "').style.border = '3px solid red'\n" + "    " +
                "  document.getElementById('result-" + (count - 2) + "').style.color = 'red'\n" + "     " +
                " document.getElementById('result-" + (count - 2) + "').innerHTML = 'Incorrect!'\n" + "     " +
                " showCorrectAnswer" + count + "()\n" + "    }\n" + "    if (document.getElementById('option-" + (count - 1) + "').checked) {\n" + "  " +
                "    document.getElementById('block-" + (count - 1) + "').style.border = '3px solid red'\n" + "  " +
                "    document.getElementById('result-" + (count - 1) + "').style.color = 'red'\n" + "     " +
                " document.getElementById('result-" + (count - 1) + "').innerHTML = 'Incorrect!'\n" + "    " +
                "  showCorrectAnswer" + count + "()\n" + "    }\n" + "  }\n" + " " +
                " function showCorrectAnswer" + count + "() {\n" + "    " +
                "let showAnswer" + count + " = document.createElement('p')\n" + " " +
                "   showAnswer" + count + ".innerHTML = 'Show Correct Answer'\n" + " " +
                "   showAnswer" + count + ".style.position = 'relative'\n" + " " +
                "   showAnswer" + count + ".style.top = '100px'\n" + "    " +
                "showAnswer" + count + ".style.fontSize = '1.75rem'\n" + " " +
                "   document.getElementById('showAnswer" + count + "').appendChild(showAnswer" + count + ")\n" + "   " +
                " showAnswer" + count + ".addEventListener('click', () => {\n" + "    " +
                "  document.getElementById('block-" + (count - 4) + "').style.border = '3px solid limegreen'\n" + "   " +
                "   document.getElementById('result-" + (count - 4) + "').style.color = 'limegreen'\n" + "     " +
                " document.getElementById('result-" + (count - 4) + "').innerHTML = 'Correct!'\n" + "   " +
                "   document.getElementById('showAnswer" + count + "').removeChild(showAnswer" + count + ")\n" + "   " +
                " })\n" + "  }\n" + "</script>";
    }

    private String getBooleanQuestion() {
        return "<button type='button' onclick='displayAnswer" + count + "()' style='width: 100px;" +
                " height: 40px; border-radius: 3px; background-color: lightblue; " +
                "font-weight: 700;'>Submit</button>\n" + "</div>\n" + "<a id='showanswer" + count + "'></a>\n" +
                "<script>\n" + "  //    The function evaluates the answer and displays result\n" + " " +
                " function displayAnswer" + count + "() {\n" + "if (document.getElementById('option-" + (count - 2) + "').checked) " +
                "{\n" + "      document.getElementById('block-" + (count - 2) + "').style.border = '3px solid limegreen'\n" + "    " +
                "  document.getElementById('result-" + (count - 2) + "').style.color = 'limegreen'\n" + "     " +
                " document.getElementById('result-" + (count - 2) + "').innerHTML = 'Correct!'\n" +
                "    }\n" + "    if (document.getElementById('option-" + (count - 1) + "').checked) {\n" + "  " +
                "    document.getElementById('block-" + (count - 1) + "').style.border = '3px solid red'\n" + "  " +
                "    document.getElementById('result-" + (count - 1) + "').style.color = 'red'\n" + "     " +
                " document.getElementById('result-" + (count - 1) + "').innerHTML = 'Incorrect!'\n" + "    " +
                "  showCorrectAnswer" + count + "()\n" + "    }\n" + "  }\n" + " " +
                " function showCorrectAnswer" + count + "() {\n" + "    " +
                "let showAnswer" + count + " = document.createElement('p')\n" + " " +
                "   showAnswer" + count + ".innerHTML = 'Show Correct Answer'\n" + " " +
                "   showAnswer" + count + ".style.position = 'relative'\n" + " " +
                "   showAnswer" + count + ".style.top = '100px'\n" + "    " +
                "showAnswer" + count + ".style.fontSize = '1.75rem'\n" + " " +
                "   document.getElementById('showAnswer" + count + "').appendChild(showAnswer" + count + ")\n" + "   " +
                " showAnswer" + count + ".addEventListener('click', () => {\n" + "    " +
                "  document.getElementById('block-" + (count - 2) + "').style.border = '3px solid limegreen'\n" + "   " +
                "   document.getElementById('result-" + (count - 2) + "').style.color = 'limegreen'\n" + "     " +
                " document.getElementById('result-" + (count - 2) + "').innerHTML = 'Correct!'\n" + "   " +
                "   document.getElementById('showAnswer" + count + "').removeChild(showAnswer" + count + ")\n" + "   " +
                " })\n" + "  }\n" + "</script>";
    }

    public String getHtmlQuestion(Question question) {
        Set<String> divs = getQuestions(question);
        StringBuilder html = new StringBuilder("<div style='transform: scale(1); position: relative;'>\n" +
                "  <h3>" + question.getQuestion() + "</h3>\n" +
                "  <p>Category: " + question.getCategory() + "</p>\n" +
                "  <p>Difficulty: " + question.getDifficulty() + "\n" + "</p>\n" +
                "  <p>Choose 1 answer</p>\n" +
                "  <hr />\n" +
                "\n");
        for (String div : divs)
            html.append(div);

        if (question.getType().equals("multiple"))
            html.append(getMultipleQuestion());
        else
            html.append(getBooleanQuestion());

        return html.toString();
    }
}
