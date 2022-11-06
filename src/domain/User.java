package domain;

import java.io.Serializable;

public class User implements Serializable {
    private String email;       // primary key
    private String username;
    private String profile;
    private String selfintro;
    private String password;    // max 16

    public User() {
        super();
    }

    public User(String email, String username, String password, String profile, String selfintro) {
        super();
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.selfintro = selfintro;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setPorfile(String profile) {
        this.profile = profile;
    }

    public String getSelfintro() {
        return selfintro;
    }

    public void setSelfintro(String selfintro) {
        this.selfintro = selfintro;
    }

    @Override
    public String toString() {
//        return "User [email=" + email + ", username=" + username + ", password=" + password + ", profile=" + profile + ", selfintro="
//                + selfintro + "]";

        return "{ \"email\":\"" + email
                + "\", \"username\":\"" + username
                + "\", \"password\":\"" + password
                + "\", \"profile\":\"" + profile
                + "\", \"selfintro\":\"" + selfintro
                + "\" }";
    }
}
