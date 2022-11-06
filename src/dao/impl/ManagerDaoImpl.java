package dao.impl;

import dao.ManagerDao;
import domain.*;
import jdk.nashorn.internal.scripts.JD;
import util.JDBCUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ManagerDaoImpl implements ManagerDao {
    // Admin
    @Override
    public Administrator login(String admin_id, String password) {
        try {
            Administrator administrator = new Administrator();
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from administrator where id=? and password=?;"
            );
            preparedStatement.setString(1, admin_id);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                administrator.setAdmin_id(admin_id);
                administrator.setPassword(password);
                return administrator;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void managerPassword(Administrator admin) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update administrator set password=? where id=?;"
            );
            preparedStatement.setString(1, admin.getPassword());
            preparedStatement.setString(2, admin.getAdmin_id());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean newAdmin(String admin_id, String password) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "select * from administrator where id = ?;"
            );
            preparedStatement1.setString(1, admin_id);
            ResultSet rs = preparedStatement1.executeQuery();
            if (rs.next()) {
                return false;
            }

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into administrator value(?, ?);"
            );
            preparedStatement.setString(1, admin_id);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Administrator> getAllAdmins() {
        List<Administrator> list = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "select * from administrator;"
            );
            while (resultSet.next()) {
                Administrator administrator = new Administrator();
                administrator.setAdmin_id(resultSet.getString("id"));
                administrator.setPassword(resultSet.getString("password"));
                list.add(administrator);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // User
    @Override
    public List<User> getTenUsers(int userBeginIdx) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from user limit ?, 10;"
            );
            preparedStatement.setInt(1, userBeginIdx);
            ResultSet rs = preparedStatement.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("name"));
                user.setPorfile(rs.getString("profile"));
                user.setSelfintro(rs.getString("self_intro"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenUsers");
    }

    @Override
    public List<User> getTenSearchUsers(int userBeginIdx, String searchword) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from user where name like ? limit ?, 10;"
            );

            preparedStatement.setString(1, "%" + searchword + "%");
            preparedStatement.setInt(2, userBeginIdx);

            ResultSet rs = preparedStatement.executeQuery();
            List<User> users = new ArrayList<User>();
            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("name"));
                user.setPorfile(rs.getString("profile"));
                user.setSelfintro(rs.getString("self_intro"));
                user.setPassword(rs.getString("password"));
                users.add(user);
            }
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenUsers");
    }

    @Override
    public void deleteUser(String useremail) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from user where email=?;"
            );
            preparedStatement.setString(1, useremail);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tags
    @Override
    public List<Tag> findTagsByPostId(String post_id) {
        List<Tag> list = new ArrayList<Tag>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from tags where id in (select tag_id from has_tags where post_id = ?);"
            );
            preparedStatement.setString(1, post_id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTag_id(rs.getString("id"));
                tag.setTag_name(rs.getString("tag_name"));
                list.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void addTag(String tag_name) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into tags(tag_name) value (?)"
            );
            preparedStatement.setString(1, tag_name);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tag> findAllTags() {
        List<Tag> list = new ArrayList<Tag>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from tags order by id;"
            );
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTag_id(rs.getString("id"));
                tag.setTag_name(rs.getString("tag_name"));
                list.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Tag findTagByTagName(String tag_name) {
        Tag tag = new Tag();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from tags where tag_name=?;"
            );
            preparedStatement.setString(1, tag_name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                tag.setTag_id(rs.getString("id"));
                tag.setTag_name(tag_name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public Tag findTagByTagId(String tag_id) {
        Tag tag = new Tag();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from tags where id=?;"
            );
            preparedStatement.setString(1, tag_id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                tag.setTag_id(tag_id);
                tag.setTag_name(rs.getString("tag_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public boolean editTag(Tag tag) {
        boolean result = false;
        try {
            Connection connection = JDBCUtil.getConnection();

            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "select * from tags where tag_name = ? and id != ?;"
            );
            preparedStatement1.setString(1, tag.getTag_name());
            preparedStatement1.setString(2, tag.getTag_id());
            ResultSet resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                result = false;
            } else {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "update tags set tag_name=? where id=?;"
                );
                preparedStatement.setString(1, tag.getTag_name());
                preparedStatement.setString(2, tag.getTag_id());
                preparedStatement.executeUpdate();
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteTag(String tag_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "delete from has_tags where tag_id = ?;"
            );
            preparedStatement1.setString(1, tag_id);
            preparedStatement1.executeUpdate();

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from tags where id=?;"
            );
            preparedStatement.setString(1, tag_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Tag> searchTagsByTagName(String tag_name) {
        List<Tag> list = new ArrayList<>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from tags where tag_name like ?;"
            );
            preparedStatement.setString(1, "%" + tag_name + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTag_id(rs.getString("id"));
                tag.setTag_name(rs.getString("tag_name"));
                list.add(tag);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Post
    @Override
    public List<String> getPicturesByPostid(int post_id) {
        List<String> pics = new ArrayList<String>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select picpath from has_pics where post_id = ?;"
            );
            preparedStatement.setInt(1, post_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String picpath = resultSet.getString("picpath");
                pics.add(picpath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pics;
    }

    @Override
    public User findUserByPostid(String post_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select user_email from user_post where post_id = ?;"
            );
            preparedStatement.setString(1, post_id);
            ResultSet rs = preparedStatement.executeQuery();
            String user_email = null;
            if (rs.next()) {
                user_email = rs.getString("user_email");
            }
            if (user_email == null || user_email.equals("")) {
                return null;
            }
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "select * from user where email = ?;"
            );
            preparedStatement1.setString(1, user_email);
            User user = new User();
            ResultSet rs1 = preparedStatement1.executeQuery();
            if (rs1.next()) {
                user.setEmail(rs1.getString("email"));
                user.setPassword(rs1.getString("password"));
                user.setUsername(rs1.getString("name"));
                user.setPorfile(rs1.getString("profile"));
                user.setSelfintro(rs1.getString("self_intro"));
            }
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> getTenPosts(int postBeginIdx) {
        List<Post> postList = new ArrayList<Post>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from post order by post_date desc limit ?, 10;"
            );
            preparedStatement.setInt(1, postBeginIdx);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPost_id(rs.getString("id"));
                post.setPost_title(rs.getString("title"));
                post.setDetail(rs.getString("details"));
                post.setPost_type(rs.getInt("type") != 0);

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                post.setPost_date(formatter.parse(rs.getString("post_date")));

                List<String> pics = getPicturesByPostid(Integer.parseInt(post.getPost_id()));
                if (pics.size() == 0) {
                    if (post.getPost_type()) {
                        pics.add("/myimage/lost-section.jpg");
                    } else {
                        pics.add("/myimage/found-section.jpg");
                    }
                }
                post.setPost_pictures(pics);

                List<Tag> tags =  findTagsByPostId(post.getPost_id());
                List<String> tagsstr = new ArrayList<String>();
                for (int i = 0; i != tags.size(); ++i) {
                    tagsstr.add(tags.get(i).getTag_name());
                }
                post.setTags(tagsstr);

                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public List<Post> getTenTypePosts(int postBeginIdx, int type) {
        List<Post> postList = new ArrayList<Post>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from post where type = ? order by post_date desc limit ?, 10;"
            );
            preparedStatement.setInt(1, type);
            preparedStatement.setInt(2, postBeginIdx);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = new Post();
                post.setPost_id(rs.getString("id"));
                post.setPost_title(rs.getString("title"));
                post.setDetail(rs.getString("details"));
                post.setPost_type(rs.getInt("type") != 0);

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                post.setPost_date(formatter.parse(rs.getString("post_date")));

                List<String> pics = getPicturesByPostid(Integer.parseInt(post.getPost_id()));
                if (pics.size() == 0) {
                    if (post.getPost_type()) {
                        pics.add("/myimage/lost-section.jpg");
                    } else {
                        pics.add("/myimage/found-section.jpg");
                    }
                }
                post.setPost_pictures(pics);

                List<Tag> tags =  findTagsByPostId(post.getPost_id());
                List<String> tagsstr = new ArrayList<String>();
                for (int i = 0; i != tags.size(); ++i) {
                    tagsstr.add(tags.get(i).getTag_name());
                }
                post.setTags(tagsstr);

                postList.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

    @Override
    public List<Post> getTenTagsPost(int postBeginIdx, List<String> tag_ids) {
        try {
            Connection connection = JDBCUtil.getConnection();

            if (tag_ids == null || tag_ids.size() == 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from post order by post_date desc limit ?, 10;"
                );
                preparedStatement.setInt(1, postBeginIdx);
                return getPosts(connection, preparedStatement);
            } else {
                String tagliststr = "";
                for (int i = 0; i < tag_ids.size(); ++i) {
                    tagliststr += tag_ids.get(i);
                    if (i != tag_ids.size() - 1)
                        tagliststr += ",";
                }
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "select * from post where ID in (select post_id from has_tags where FIND_IN_SET(tag_id, ?)) order by post_date desc limit ?, 10;"
                );
//                PreparedStatement preparedStatement = connection.prepareStatement(
//                        "select * from post where ID in (select post_id from has_tags) order by post_date desc limit ?, 10;"
//                );
                preparedStatement.setString(1, tagliststr);
                preparedStatement.setInt(2, postBeginIdx);
//                preparedStatement.setInt(1, postBeginIdx);
                return getPosts(connection, preparedStatement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenTagsPost Error");
    }

    @Override
    public List<Post> getTenSearchTagsPost(int postBeginIdx, String search, List<String> tag_ids) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = null;
            if (tag_ids == null || tag_ids.size() == 0) {
                preparedStatement = connection.prepareStatement(
                        "select * from post where title like ? or details like ? order by post_date desc limit ?,10;"
                );
                preparedStatement.setString(1, "%" + search + "%");
                preparedStatement.setString(2, "%" + search + "%");
                preparedStatement.setInt(3, postBeginIdx);
            } else {
                String tagliststr = "";
                for (int i = 0; i < tag_ids.size(); ++i) {
                    tagliststr += tag_ids.get(i);
                    if (i != tag_ids.size() - 1)
                        tagliststr += ",";
                }
                preparedStatement = connection.prepareStatement(
                        "select * from post where ID in (select post_id from has_tags where FIND_IN_SET(tag_id, ?) and (title like ? or details like ?)) order by post_date desc limit ?, 10;"
                );
                preparedStatement.setString(1, tagliststr);
                preparedStatement.setString(2, "%" + search + "%");
                preparedStatement.setString(3, "%" + search + "%");
                preparedStatement.setInt(4, postBeginIdx);
            }

            return getPosts(connection, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenSearchTagsPost");
    }

    @Override
    public List<Post> getTenTypeTagsPost(int postBeginIdx, int type, List<String> tag_ids) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = null;

            if (tag_ids == null || tag_ids.size() == 0) {
                preparedStatement = connection.prepareStatement(
                        "select * from post where type = ? order by post_date desc limit ?, 10;"
                );
                preparedStatement.setInt(1, type);
                preparedStatement.setInt(2, postBeginIdx);
            } else {
                String taglist = "";
                for (int i = 0; i < tag_ids.size(); ++i) {
                    taglist += tag_ids.get(i);
                    if (i != tag_ids.size() - 1)
                        taglist += ",";
                }
                preparedStatement = connection.prepareStatement(
                        "select * from post where type = ? and ID in (select post_id from has_tags where FIND_IN_SET(tag_id, ?)) order by post_date desc limit ?, 10;"
                );
                preparedStatement.setInt(1, type);
                preparedStatement.setString(2, taglist);
                preparedStatement.setInt(3, postBeginIdx);
            }

            List<Post> posts = getPosts(connection, preparedStatement);
            return posts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenTypeTagsPost");
    }

    @Override
    public List<Post> getTenTypeSearchTagsPost(int postBeginIdx, int type, String search, List<String> tag_ids) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = null;
            if (tag_ids == null || tag_ids.size() == 0) {
                preparedStatement = connection.prepareStatement(
                        "select * from post where type = ? and title like ? or details like ? order by post_date desc limit ?, 10;"
                );
                preparedStatement.setInt(1, type);
                preparedStatement.setString(2, "%" + search + "%");
                preparedStatement.setString(3, "%" + search + "%");
                preparedStatement.setInt(4, postBeginIdx);
            } else {
                String taglist = "";
                for (int i = 0; i < tag_ids.size(); ++i) {
                    taglist += tag_ids.get(i);
                    if (i != tag_ids.size() - 1)
                        taglist += ",";
                }
                preparedStatement = connection.prepareStatement(
                        "select * from post where type = ?  and (title like ? or details like ?) and ID in (select post_id from has_tags where FIND_IN_SET(tag_id, ?)) order by post_date desc limit ?, 10;"
                );
                preparedStatement.setInt(1, type);
                preparedStatement.setString(2, taglist);
                preparedStatement.setString(3, "%" + search + "%");
                preparedStatement.setString(4, "%" + search + "%");
                preparedStatement.setInt(5, postBeginIdx);
            }

            return getPosts(connection, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("getTenTypeSearchTagsPost");
    }

    private List<Post> getPosts(Connection connection, PreparedStatement preparedStatement) throws SQLException, ParseException {
        ResultSet rs = preparedStatement.executeQuery();
        List<Post> list = new ArrayList<Post>();
        while (rs.next()) {
            Post post = new Post();
            post.setPost_id(rs.getString("id"));
            post.setPost_title(rs.getString("title"));
            post.setDetail(rs.getString("details"));
            Statement smt = connection.createStatement();
            ResultSet rs2 = smt.executeQuery("select picpath from has_pics where post_id=" + rs.getString("id"));
            List<String> pics = new ArrayList<String>();
            while (rs2.next()) {
                pics.add(rs2.getString("picpath"));
            }
            post.setPost_pictures(pics);
            post.setPost_type(Integer.parseInt(rs.getString("type")) == 0 ? false : true);
            post.setPost_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("post_date")));
            list.add(post);
        }
        return list;
    }

    @Override
    public List<Post> getTagPost(List<String> tag_ids) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = null;

            if (tag_ids == null || tag_ids.size() == 0) {
                preparedStatement = connection.prepareStatement(
                        "select * from post;"
                );
            } else {
                String taglist = "(";
                for (int i = 0; i < tag_ids.size(); ++i) {
                    taglist += tag_ids.get(i);
                    if (i != tag_ids.size() - 1)
                        taglist += ",";
                }
                taglist += ")";
                preparedStatement = connection.prepareStatement(
                        "select * from post where ID in (select post_id from has_tags where tag_id in ?);"
                );
                preparedStatement.setString(1, taglist);
            }

            return getPosts(connection, preparedStatement);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("?????????");
    }

    @Override
    public void deletePost(String post_idstr) {
        try {
            int post_id = Integer.parseInt(post_idstr);
            Connection connection = JDBCUtil.getConnection();

            // delete from user_post
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "delete from user_post where post_id=?;"
            );
            preparedStatement1.setInt(1, post_id);
            preparedStatement1.executeUpdate();
            // delete from has_tags
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "delete from has_tags where post_id=?;"
            );
            preparedStatement2.setInt(1, post_id);
            preparedStatement2.executeUpdate();
            //delete from post
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from post where ID = ?;"
            );
            preparedStatement.setInt(1, post_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post findPostById(int post_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from post where id=?;"
            );
            preparedStatement.setInt(1, post_id);
            ResultSet rs = preparedStatement.executeQuery();
            Post post = new Post();
            if (rs.next()) {
                post.setPost_id(rs.getString("id"));
                post.setPost_title(rs.getString("title"));
                post.setDetail(rs.getString("details"));
                post.setPost_type(rs.getInt("type") != 0);
                post.setPost_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("post_date")));

                PreparedStatement preparedStatement1 = connection.prepareStatement(
                        "select picpath from has_pics where post_id=?"
                );
                preparedStatement1.setInt(1, post_id);
                ResultSet rs2 = preparedStatement1.executeQuery();
                List<String> pics = new ArrayList<String>();
                while (rs2.next()) {
                    pics.add(rs2.getString("picpath"));
                }
                if (pics.size() == 0) {
                    if (post.getPost_type()) {
                        pics.add("/myimage/lost-section.jpg");
                    } else {
                        pics.add("/myimage/found-section.jpg");
                    }
                }
                post.setPost_pictures(pics);

            }
            return post;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("findPostById");
    }


    // announcement
    @Override
    public List<Announce> getTenAnnounces(int annBeginIdx) {
        List<Announce> list = new ArrayList<Announce>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from announce order by ann_date desc limit ?, 10;"
            );
            preparedStatement.setInt(1, annBeginIdx);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Announce announce = new Announce();
                announce.setAnn_id(rs.getString("id"));
                announce.setAdmin_id(rs.getString("admin_id"));
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                announce.setDate(formatter.parse(rs.getString("ann_date")));
                announce.setTitle(rs.getString("title"));
                announce.setContent(rs.getString("content"));
                list.add(announce);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Announce findAnnounceById(String ann_id) {
        try {
            Announce announce = new Announce();
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from announce where id = ?;"
            );
            preparedStatement.setString(1, ann_id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                announce.setAnn_id(ann_id);
                announce.setAdmin_id(rs.getString("admin_id"));
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                announce.setDate(formatter.parse(rs.getString("ann_date")));
                announce.setTitle(rs.getString("title"));
                announce.setContent(rs.getString("content"));
                return announce;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addAnnounce(Announce ann) {
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement statement = connection.createStatement();
            int ann_id = 1;
            ResultSet rs = statement.executeQuery("select max(id) + 1 nextannid from announce");
            if (rs.next()) {
                String sann_id = rs.getString("nextannid");
                if (sann_id != null && !sann_id.equals("null"))
                    ann_id = Integer.parseInt(sann_id);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into announce (id, admin_id, ann_date, title, content) value (?,?,?,?,?);"
            );
            preparedStatement.setInt(1, ann_id);
            preparedStatement.setString(2, ann.getAdmin_id());
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setString(3, formatter.format(new Date()));
            preparedStatement.setString(4, ann.getTitle());
            preparedStatement.setString(5, ann.getContent());
            preparedStatement.executeUpdate();

            return ann_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void deleteAnnounce(String ann_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from announce where id=?;"
            );
            preparedStatement.setInt(1, Integer.parseInt(ann_id));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editAnnounce(Announce ann) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update announce set ann_date=?, title=?, content=? where id=?;"
            );
            preparedStatement.setInt(4, Integer.parseInt(ann.getAnn_id()));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setString(1, formatter.format(ann.getDate()));
            preparedStatement.setString(2, ann.getTitle());
            preparedStatement.setString(3, ann.getContent());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
