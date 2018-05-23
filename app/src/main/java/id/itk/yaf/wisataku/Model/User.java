package id.itk.yaf.wisataku.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("id_user")
    @Expose
    private int id_user;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("encrypted_password")
    @Expose
    private String encrypted_password;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    private List<User> user;

    /*public User(int id_user, String website, String name, String email, String username, String encrypted_password, String profile_picture){
        this.id_user = id_user;
        this.website = website;
        this.name = name;
        this.email = email;
        this.encrypted_password = encrypted_password;
        this.profile_picture = profile_picture;
    }*/

    //GETTER

    public int getId_user() {
        return id_user;
    }

    public String getWebsite() {
        return website;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public String getProfile_picture() {
        return profile_picture;
    }


    //SETTER


    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

}
