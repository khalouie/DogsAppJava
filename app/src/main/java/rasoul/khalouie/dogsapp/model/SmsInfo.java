package rasoul.khalouie.dogsapp.model;

public class SmsInfo {

    private String destination;
    private String text;
    private String imageUrl;

    public SmsInfo(String destination, String text, String imageUrl) {
        this.destination = destination;
        this.text = text;
        this.imageUrl = imageUrl;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
