package project.greetiny.ucapan;

public class data_ulangTahun {
    private String gambar;
    private String websiteUrl;
    private String subject;
    private String object;
    private String tanggal;
    private String ucapan;
    private String username;
    private String userId;
    private String type;

    private String key;

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUcapan() {
        return ucapan;
    }

    public void setUcapan(String ucapan) {
        this.ucapan = ucapan;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public data_ulangTahun() {
        this.gambar = gambar;
        this.websiteUrl = websiteUrl;
        this.subject = subject;
        this.object = object;
        this.tanggal = tanggal;
        this.ucapan = ucapan;
        this.username = username;
        this.userId = userId;
        this.type = type;
    }
}
