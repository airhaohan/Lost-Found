package domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Post implements Serializable {
    private String post_id;
    private String post_title;
    private List<String> post_pictures;
    private List<String> tags;
    private String detail;
    private boolean post_type;      // 0 招领 1 寻物
    private Date post_date;

    public Post() {
        super();
    }

    public Post(String post_id, String post_title, List<String> post_pictures, String detail, boolean post_type, Date post_date) {
        this.post_id = post_id;
        this.post_title =  post_title;
        this.post_pictures = post_pictures;
        this.detail = detail;
        this.post_type = post_type;
        this.post_date = post_date;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public List<String> getPost_pictures() {
        return post_pictures;
    }

    public void setPost_pictures(List<String> post_pictures) {
        this.post_pictures = post_pictures;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean getPost_type() {
        return this.post_type;
    }

    public void setPost_type(boolean post_type) {
        this.post_type = post_type;
    }

    public Date getPost_date() {
        return post_date;
    }

    public void setPost_date(Date post_date) {
        this.post_date = post_date;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
          String pic = "[";
          for (int i = 0; i != post_pictures.size(); ++i) {
              pic += "\"" + post_pictures.get(i) + "\"";
              if (i != post_pictures.size() - 1) {
                  pic += ",";
              }
          }
          pic += "]";

        String tag = "[";
        for (int i = 0; i != tags.size(); ++i) {
              tag += "\"" + tags.get(i) + "\"";
              if (i != tags.size() - 1) {
                  tag += ",";
              }
          }
          tag += "]";

          return "{\"post_id\":\"" + post_id +
                  "\", \"post_title\":\"" + post_title +
                  "\", \"post_pictures\":" + pic +
                  ", \"tags\":" + tag +
                  ", \"detail\":\"" + detail +
                  "\", \"post_type\":" + post_type +
                  ", \"post_date\":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(post_date) + "\"}";
    }
}