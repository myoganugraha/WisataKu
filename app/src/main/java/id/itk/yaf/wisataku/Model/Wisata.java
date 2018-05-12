package id.itk.yaf.wisataku.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Wisata implements Parcelable {

    @SerializedName("id_wisata")
    private String id_wisata;

    @SerializedName("category")
    private String category;

    @SerializedName("id_user")
    private String id_user;

    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("image")
    private String image;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("longitude")
    private String longitude;

    public Wisata(String id_wisata, String category, String id_user, String title, String description, String image, String latitude, String longitude) {
        this.id_wisata = id_wisata;
        this.category = category;
        this.id_user = id_user;
        this.title = title;
        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    //GETTER

    public String getId_wisata() {
        return id_wisata;
    }

    public String getCategory() {
        return category;
    }

    public String getId_user() {
        return id_user;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    //SETTER

    public void setId_wisata(String id_wisata) {
        this.id_wisata = id_wisata;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id_wisata);
        dest.writeString(this.category);
        dest.writeString(this.id_user);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
    }

    protected Wisata(Parcel in) {
        this.id_wisata = in.readString();
        this.category = in.readString();
        this.id_user = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    public static final Creator<Wisata> CREATOR = new Creator<Wisata>() {
        @Override
        public Wisata createFromParcel(Parcel source) {
            return new Wisata(source);
        }

        @Override
        public Wisata[] newArray(int size) {
            return new Wisata[size];
        }
    };
}
