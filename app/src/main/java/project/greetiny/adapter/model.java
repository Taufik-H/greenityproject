package project.greetiny.adapter;

public class model {

    String subject;
    String userId;

    public model() {
    }

    public model(String subject, String userId) {
        this.subject = subject;
        this.userId = userId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
