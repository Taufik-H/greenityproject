package project.greetiny.adapter;

public class model {

    String subject;
    String userId;
    String type;
    String websiteUrl;
    String key;

    public model() {
    }

    public model(String subject, String userId, String type, String websiteUrl, String key) {
        this.subject = subject;
        this.userId = userId;
        this.type = type;
        this.websiteUrl = websiteUrl;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
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
}
