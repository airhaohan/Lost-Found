package dao;

import java.util.*;

import domain.*;
import javafx.geometry.Pos;
import javafx.util.Pair;

public interface ClientDao {
    // Login logic
    User login(String useremail, String password);

    boolean register (User user);

    void deleteaccount(String useremail);

    // Post post
    void deletePost(String post_idstr);

    int editPost(String post_idstr, String title, String details, boolean type, List<String> tag_ids);

    int newPost(String user_email, String title, String details, boolean type, List<String> tag_ids);

    void editTitle(String post_id, String title);

    void editDetail(String post_id, String detail);

    void addPictureToPost (String post_id, String picpath);

    void deletePictureFromPost (String post_id, String picpath);

    // Personal information
    void personInformation(String useremail, String username, String profile, String selfintro);

    void personPassword(String useremail, String password);

    // View posts
    List<Post> getTenPosts(int postBeginIdx);   // none filter

    List<Post> getTenTypePosts(int postBeginIdx, int type);

    List<String> getPicturesByPostid(int post_id);

    User findUserByPostid(String post_id);

    List<Post> findPostByUserEamil(String user_email);

    List<Post> getTenTagsPost(int postBeginIdx, List<String> tag_ids);  // tags filter

    List<Post> getTenSearchTagsPost(int postBeginIdx, String search, List<String> tag_ids); //search filter

    List<Post> getTenTypeTagsPost(int postBeginIdx, int type, List<String> tag_ids);  // tags filter

    List<Post> getTenTypeSearchTagsPost(int postBeginIdx, int type, String search, List<String> tag_ids); //search filter

    Post findPostById(int post_id);

    //View Ann
    List<Announce> getTenAnnounces(int annBeginIdx);

    // Concern
    List<Concern> findConcernByUserEmail(User user);

    void addConcern(String user_email, String post_id);

    boolean findConcern(String user_email, String post_id);

    void deleteConcern(String user_email, String post_id);

    // Tag
    List<Tag> findTagsByPostId(String post_id);

    List<Tag> findAllTags();

    void addTag(String post_id, String tag_id);

    void deleteTag(String post_id, String tag_id);

    // Message
    void sendMessage(String user_email, String receiver_email, String content);

    List<Pair<Messages, User>> getAllMsgs(String receiver_email);

    void deleteMsg(String msg_id);
}
