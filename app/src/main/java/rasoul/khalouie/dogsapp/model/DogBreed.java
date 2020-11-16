package rasoul.khalouie.dogsapp.model;

import com.google.gson.annotations.SerializedName;

public class DogBreed {

    @SerializedName("id")
    private String breedId;

    @SerializedName("name")
    private String dogBreed;

    @SerializedName("life_span")
    private String lifeSpan;

    @SerializedName("breed_group")
    private String breedGroup;

    @SerializedName("bred_for")
    private String bredFor;

    @SerializedName("temperament")
    private String temperament;

    @SerializedName("url")
    private String imageUrl;


    private int uui;


    public DogBreed(String breedId, String dogBreed, String lifeSpan, String breedGroup, String bredFor, String temperament, String imageUrl) {
        this.breedId = breedId;
        this.dogBreed = dogBreed;
        this.lifeSpan = lifeSpan;
        this.breedGroup = breedGroup;
        this.bredFor = bredFor;
        this.temperament = temperament;
        this.imageUrl = imageUrl;
    }

    public String getBreedId() {
        return breedId;
    }

    public void setBreedId(String breedId) {
        this.breedId = breedId;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public void setLifeSpan(String lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    public String getBreedGroup() {
        return breedGroup;
    }

    public void setBreedGroup(String breedGroup) {
        this.breedGroup = breedGroup;
    }

    public String getBredFor() {
        return bredFor;
    }

    public void setBredFor(String bredFor) {
        this.bredFor = bredFor;
    }

    public String getTemperament() {
        return temperament;
    }

    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUui() {
        return uui;
    }

    public void setUui(int uui) {
        this.uui = uui;
    }
}
