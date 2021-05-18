package distributed.lab5.rest.message;

public enum ErrorMessage {
    INCORRECT_FORM("The form is provided incorrectly.");

    private final String text;

    ErrorMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return "error: " + text;
    }

    public String asJson() {
        return "{ \"error\": \"" + text + "\" }";
    }

}
