package domain;

import java.io.Serializable;

public class HasPics implements Serializable {
    private String post_id;
    private String picpath;

    public HasPics() {
        super();
    }

    public HasPics(String post_id, String picpath) {
        super();
        this.post_id = post_id;
        this.picpath = picpath;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPicpath() {
        return picpath;
    }

    public void setPicpath(String picpath) {
        this.picpath = picpath;
    }

    @Override
    public String toString () {
        return "HasPics [post_id=" + post_id + ", picpath=" + picpath + "]";
    }
}
