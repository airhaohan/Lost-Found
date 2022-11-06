package service.impl;

import dao.ClientDao;
import dao.impl.ClientDaoImpl;
import domain.*;
import javafx.util.Pair;
import service.ClientService;

import java.util.List;

public class ClientServiceImpl implements ClientService {

    private final ClientDao dao = new ClientDaoImpl();

    // Login logic
    @Override
    public User login(String useremail, String password) {
        return dao.login(useremail, password);
    }

    @Override
    public boolean register(String useremail, String username, String password) {
        return dao.register(new User(useremail, username, password, "img/unloginin-profile.webp", ""));
    }

    @Override
    public void deleteaccount(String useremail) {
        dao.deleteaccount(useremail);
    }

    // Post post


    @Override
    public void deletePost(String post_idstr) {
        dao.deletePost(post_idstr);
    }

    @Override
    public int editPost(String post_idstr, String title, String details, boolean type, List<String> tag_ids) {
        return dao.editPost(post_idstr, title, details, type, tag_ids);
    }

    @Override
    public int newPost(String user_email, String title, String details, boolean type, List<String> tag_ids) {
        return dao.newPost(user_email,  title, details, type, tag_ids);
    }

    @Override
    public void editTitle(String post_id, String title) {
        dao.editTitle(post_id, title);
    }

    @Override
    public void editDetail(String post_id, String detail) {
        dao.editDetail(post_id, detail);
    }

    @Override
    public void addPictureToPost(String post_id, String picpath) {
        dao.addPictureToPost(post_id, picpath);
    }

    @Override
    public void deletePictureFromPost(String post_id, String picpath) {
        dao.deletePictureFromPost(post_id, picpath);
    }

    // Personal information
    @Override
    public void personInformation(String useremail, String username, String profile, String self_intro) {
        dao.personInformation(useremail, username, profile, self_intro);
    }

    @Override
    public void personPassword(String useremail, String password) {
        dao.personPassword(useremail, password);
    }

    // View posts
    @Override
    public List<Post> getTenPosts(int postBeginIdx) {
        return dao.getTenPosts(postBeginIdx);
    }

    @Override
    public List<Post> getTenTypePosts(int postBeginIdx, int type) {
        return dao.getTenTypePosts(postBeginIdx, type);
    }

    @Override
    public User findUserByPostid(String post_id) {
        return dao.findUserByPostid(post_id);
    }

    @Override
    public List<Post> findPostByUserEamil(String user_email) {
        return dao.findPostByUserEamil(user_email);
    }

    @Override
    public List<Post> getTenTagsPost(int postBeginIdx, List<String> tag_ids) {
        return dao.getTenTagsPost(postBeginIdx, tag_ids);
    }

    @Override
    public List<Post> getTenSearchTagsPost(int postBeginIdx, String search, List<String> tag_ids) {
        return dao.getTenSearchTagsPost(postBeginIdx, search, tag_ids);
    }

    @Override
    public List<Post> getTenTypeTagsPost(int postBeginIdx, int type, List<String> tag_ids) {
        return dao.getTenTypeTagsPost(postBeginIdx, type, tag_ids);
    }

    @Override
    public List<Post> getTenTypeSearchTagsPost(int postBeginIdx, int type, String search, List<String> tag_ids) {
        return dao.getTenTypeSearchTagsPost(postBeginIdx, type, search, tag_ids);
    }

    @Override
    public Post findPostById(int post_id) {
        return dao.findPostById(post_id);
    }

    // View anns
    @Override
    public List<Announce> getTenAnnounces(int annBeginIdx) {
        return dao.getTenAnnounces(annBeginIdx);
    }

    // Concern
    @Override
    public List<Concern> findConcernByUserEmail(User user) {
        return dao.findConcernByUserEmail(user);
    }

    @Override
    public void addConcern(String user_email, String post_id) {
        dao.addConcern(user_email, post_id);
    }

    @Override
    public boolean findConcern(String user_email, String post_id) {
        return dao.findConcern(user_email, post_id);
    }

    @Override
    public void deleteConcern(String user_email, String post_id) {
        dao.deleteConcern(user_email, post_id);
    }

    // Tag
    @Override
    public List<Tag> findTagsByPostId(String post_id) {
        return dao.findTagsByPostId(post_id);
    }

    @Override
    public List<Tag> findAllTags() {
        return dao.findAllTags();
    }

    @Override
    public void addTag(String post_id, String tag_id) {
        dao.addTag(post_id, tag_id);
    }

    @Override
    public void deleteTag(String post_id, String tag_id) {
        dao.deleteTag(post_id, tag_id);
    }

    // Message
    @Override
    public void sendMessage(String user_email, String receiver_email, String content) {
        dao.sendMessage(user_email, receiver_email, content);
    }

    @Override
    public List<Pair<Messages, User>> getAllMsgs(String receiver_email) {
        return dao.getAllMsgs(receiver_email);
    }

    @Override
    public void deleteMsg(String msg_id) {
        dao.deleteMsg(msg_id);
    }
}
