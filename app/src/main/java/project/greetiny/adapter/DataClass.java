package project.greetiny.adapter;


public class DataClass {
    private String dataTitle,dataType,dataImage,key;

    public String getDataTitle() {
        return dataTitle;
    }

    public void setDataTitle(String dataTitle) {
        this.dataTitle = dataTitle;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataImage() {
        return dataImage;
    }

    public void setDataImage(String dataImage) {
        this.dataImage = dataImage;
    }

    public DataClass(String dataTitle, String dataType, String dataImage, String key) {
        this.dataTitle = dataTitle;
        this.dataType = dataType;
        this.dataImage = dataImage;
        this.key = key;
    }

    public DataClass(){

    }
}
