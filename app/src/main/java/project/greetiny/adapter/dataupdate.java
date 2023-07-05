package project.greetiny.adapter;

public class dataupdate {
    String cardId,updateSubject,updateTanggal,updateImage,updateUcapan;

    public dataupdate(String cardId, String updateSubject, String updateTanggal, String updateImage, String updateUcapan) {
        this.cardId = cardId;
        this.updateSubject = updateSubject;
        this.updateTanggal = updateTanggal;
        this.updateImage = updateImage;
        this.updateUcapan = updateUcapan;
    }

    public dataupdate() {

    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUpdateSubject() {
        return updateSubject;
    }

    public void setUpdateSubject(String updateSubject) {
        this.updateSubject = updateSubject;
    }

    public String getUpdateTanggal() {
        return updateTanggal;
    }

    public void setUpdateTanggal(String updateTanggal) {
        this.updateTanggal = updateTanggal;
    }

    public String getUpdateImage() {
        return updateImage;
    }

    public void setUpdateImage(String updateImage) {
        this.updateImage = updateImage;
    }

    public String getUpdateUcapan() {
        return updateUcapan;
    }

    public void setUpdateUcapan(String updateUcapan) {
        this.updateUcapan = updateUcapan;
    }
}
