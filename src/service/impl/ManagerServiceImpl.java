package service.impl;

import service.ManagerService;
import dao.ManagerDao;
import dao.impl.ManagerDaoImpl;
import domain.*;

import java.util.List;

public class ManagerServiceImpl implements ManagerService {
    private final ManagerDao dao = new ManagerDaoImpl();

    // Admin
    @Override
    public Administrator login(String admin_id, String password) {
        return dao.login(admin_id, password);
    }

    @Override
    public void managerPassword(Administrator admin) { //
        dao.managerPassword(admin);
    }

    @Override
    public boolean newAdmin(String admin_id, String password) {
        return dao.newAdmin(admin_id, password);
    }

    @Override
    public List<Administrator> getAllAdmins() {
        return dao.getAllAdmins();
    }

    // User

    @Override
    public List<User> getTenUsers(int userBeginIdx) {
        return dao.getTenUsers(userBeginIdx);
    }

    @Override
    public List<User> getTenSearchUsers(int userBeginIdx, String searchword) {
        return dao.getTenSearchUsers(userBeginIdx, searchword);
    }

    @Override
    public void deleteUser(String useremail) {
        dao.deleteUser(useremail);
    }

    // Tags
    @Override
    public void addTag(String tag_name) {
        dao.addTag(tag_name);
    }

    @Override
    public List<Tag> findAllTags() {
        return dao.findAllTags();
    }

    @Override
    public Tag findTagByTagName(String tag_name) {
        return dao.findTagByTagName(tag_name);
    }

    @Override
    public Tag findTagByTagId(String tag_id) {
        return dao.findTagByTagId(tag_id);
    }

    @Override
    public boolean editTag(Tag tag) {
        return dao.editTag(tag);
    }

    @Override
    public void deleteTag(String tag_id) {  //
        dao.deleteTag(tag_id);
    }

    @Override
    public List<Tag> findTagsByPostId(String post_id) {
        return dao.findTagsByPostId(post_id);
    }

    // Post
    @Override
    public List<Post> getTagPost(List<String> tag_ids) {
        return dao.getTagPost(tag_ids);
    }

    @Override
    public void deletePost(String post_id) {
        dao.deletePost(post_id);
    }

    @Override
    public List<String> getPicturesByPostid(int post_id) {
        return dao.getPicturesByPostid(post_id);
    }

    @Override
    public User findUserByPostid(String post_id) {
        return dao.findUserByPostid(post_id);
    }

    @Override
    public List<Post> getTenPosts(int postBeginIdx) {
        return dao.getTenPosts(postBeginIdx);
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
    public List<Post> getTenTypePosts(int postBeginIdx, int type) {
        return dao.getTenTypePosts(postBeginIdx, type);
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

    // Announce
    @Override
    public List<Announce> getTenAnnounces(int annBeginIdx) {
        return dao.getTenAnnounces(annBeginIdx);
    }

    @Override
    public Announce findAnnounceById(String ann_id) {
        return dao.findAnnounceById(ann_id);
    }

    @Override
    public int addAnnounce(Announce ann) {
        return dao.addAnnounce(ann);
    }

    @Override
    public void deleteAnnounce(String ann_id) {
        dao.deleteAnnounce(ann_id);
    }

    @Override
    public void editAnnounce(Announce ann) {
        dao.editAnnounce(ann);
    }
}
