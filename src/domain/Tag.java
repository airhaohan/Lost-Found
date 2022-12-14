package domain;

import java.io.Serializable;

public class Tag implements Serializable {
    private String tag_id;
    private String tag_name;

    public Tag() {
        super();
    }

    public Tag(String tag_id, String tag_name) {
        super();
        this.tag_id = tag_id;
        this.tag_name = tag_name;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    @Override
    public String toString() {
        return "{\"tag_id\":\"" + tag_id +
                "\", \"tag_name\":\"" + tag_name + "\"}";
    }
}
