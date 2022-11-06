package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Announce implements Serializable {
    private String ann_id;
    private String admin_id;
    private String title;
    private String content;
    private Date date;

    public Announce() {
        super();
    }

    public Announce(String ann_id, String admin_id, String title, String content, Date date) {
        super();
        this.ann_id = ann_id;
        this.admin_id = admin_id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getAnn_id() {
        return ann_id;
    }

    public void setAnn_id(String ann_id) {
        this.ann_id = ann_id;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "{\"ann_id\":\"" + ann_id
                + "\", \"admin_id\":\"" + admin_id
                + "\", \"title\":\"" + title
                + "\", \"content\":\"" + content
                + "\", \"date\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) + "\"}";
    }
}
