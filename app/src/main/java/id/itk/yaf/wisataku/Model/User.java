package id.itk.yaf.wisataku.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private int id_user;
    private String website;
    private String firstname;
    private String lastname;
    private String email;
    private String username;
    private String encrypted_password;
    private String profile_picture;

    public User(int id_user, String website, String firstname, String lastname, String email, String username, String encrypted_password, String profile_picture){
        this.id_user = id_user;
        this.website = website;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.encrypted_password = encrypted_password;
        this.profile_picture = profile_picture;
    }

    //GETTER
    public int getId_user() {
        return id_user;
    }

    public String getWebsite() {
        return website;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
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

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id_user);
        dest.writeString(this.website);
        dest.writeString(this.firstname);
        dest.writeString(this.lastname);
        dest.writeString(this.email);
        dest.writeString(this.username);
        dest.writeString(this.encrypted_password);
        dest.writeString(this.profile_picture);
    }

    protected User(Parcel in) {
        this.id_user = in.readInt();
        this.website = in.readString();
        this.firstname = in.readString();
        this.lastname = in.readString();
        this.email = in.readString();
        this.username = in.readString();
        this.encrypted_password = in.readString();
        this.profile_picture = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
