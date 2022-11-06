package domain;

import java.io.Serializable;

public class HasTags implements Serializable {
    private String user_email;
    private String tag_id;

    public HasTags() {
        super();
    }

    public HasTags(String user_email, String tag_id) {
        super();
        this.user_email = user_email;
        this.tag_id = tag_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    @Override
    public String toString() {
        return "HasTags [" + user_email + ", tag_id=" + tag_id + "]";
    }
}
