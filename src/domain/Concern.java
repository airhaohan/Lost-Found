package domain;

import java.io.Serializable;

public class Concern implements Serializable {
    private User user;
    private Post post;

    public Concern() {
        super();
    }

    public Concern(User user, Post post) {
        super();
        this.user = user;
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Concern [ user=" + user + ", post=" + post + "]";
    }
}
