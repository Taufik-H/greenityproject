package project.greetiny.adapter;

public class model {

    String subject;
    String userId;
    String type;
    String gambar;

    public model() {
    }

    public model(String subject, String userId) {
        this.subject = subject;
        this.userId = userId;
        this.type = type;
        this.gambar = gambar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
