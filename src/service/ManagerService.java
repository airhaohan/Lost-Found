package service;

import java.util.*;

import domain.*;

public interface ManagerService {
    Administrator login(String admin_id, String password);

    void managerPassword(Administrator admin);  //

    boolean newAdmin(String admin_id, String password);

    List<Administrator> getAllAdmins();

    // User
    List<User> getTenUsers(int userBeginIdx);

    List<User> getTenSearchUsers(int userBeginIdx, String searchword);

    void deleteUser(String useremail);

    // Tags
    void addTag(String tag_name);

    List<Tag> findAllTags();

    Tag findTagByTagName(String tag_name);

    Tag findTagByTagId(String tag_id);

    boolean editTag(Tag tag);

    void deleteTag(String tag_id);    //

    List<Tag> findTagsByPostId(String post_id);

    // Post
    List<Post> getTagPost(List<String> tag_ids);

    void deletePost(String post_id);

    List<String> getPicturesByPostid(int post_id);

    User findUserByPostid(String post_id);

    List<Post> getTenPosts(int postBeginIdx);   // none filter

    List<Post> getTenTagsPost(int postBeginIdx, List<String> tag_ids);  // tags filter

    List<Post> getTenSearchTagsPost(int postBeginIdx, String search, List<String> tag_ids); //search filter

    List<Post> getTenTypePosts(int postBeginIdx, int type);

    List<Post> getTenTypeTagsPost(int postBeginIdx, int type, List<String> tag_ids);  // tags filter

    List<Post> getTenTypeSearchTagsPost(int postBeginIdx, int type, String search, List<String> tag_ids); //search filter

    Post findPostById(int post_id);

    // announcement
    List<Announce> getTenAnnounces(int annBeginIdx);

    Announce findAnnounceById (String ann_id);

    int addAnnounce(Announce ann);

    void deleteAnnounce(String ann_id);

    void editAnnounce(Announce ann);
}
