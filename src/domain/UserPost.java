package domain;

import java.io.Serializable;

public class UserPost implements Serializable {
    private String user_email;
    private String post_id;

    public UserPost() {
        super();
    }

    public UserPost(String user_email, String post_id) {
        super();
        this.user_email = user_email;
        this.post_id = post_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    @Override
    public String toString() {
        return "HasTags [" + user_email + ", post_id=" + post_id + "]";
    }
}
