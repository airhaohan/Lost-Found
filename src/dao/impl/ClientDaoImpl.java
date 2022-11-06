package dao.impl;

import dao.ClientDao;
import domain.*;
import javafx.geometry.Pos;
import javafx.util.Pair;
import util.JDBCUtil;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientDaoImpl implements ClientDao {

    // Login logic
    @Override
    public User login(String useremail, String password) {
        User user = new User();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email=? and password=?");
            preparedStatement.setString(1, useremail);
            preparedStatement.setString(2, password);
            ResultSet executeQuery = preparedStatement.executeQuery();
            if (executeQuery.next()) {
                user.setEmail(executeQuery.getString("email"));
                user.setPassword(executeQuery.getString("password"));
                user.setUsername(executeQuery.getString("name"));
                user.setPorfile(executeQuery.getString("profile"));
                user.setSelfintro(executeQuery.getString("self_intro"));
            } else {
            }
        } catch (Exception e) {
        }
        return user;
    }

    @Override
    public boolean register(User user) {
        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(
                    "select * from user where email=?;"
            );
            preparedStatement.setString(1, user.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                try {
                    PreparedStatement ps = conn.prepareStatement(
                            "insert into user value (?,?,?,?,?);");
                    ps.setString(1, user.getEmail());
                    ps.setString(2, user.getUsername());
                    ps.setString(3, user.getProfile());
                    ps.setString(4, user.getSelfintro());
                    ps.setString(5, user.getPassword());
                    ps.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    @Override
    public void deleteaccount(String useremail) {
        try {
            Connection connection = JDBCUtil.getConnection();
            List<Post> postslist = findPostByUserEamil(useremail);
            for (int i = 0; i < postslist.size(); ++i) {
                deletePost(postslist.get(i).getPost_id());
            }

            // concern
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "delete from concern where user_email = ?;"
            );
            preparedStatement1.setString(1, useremail);
            preparedStatement1.executeUpdate();

            // message
            PreparedStatement preparedStatement2 = connection.prepareStatement(
                    "delete from messages where receiver_email = ? or sender_email = ?;"
            );
            preparedStatement2.setString(1, useremail);
            preparedStatement2.setString(2, useremail);
            preparedStatement2.executeUpdate();

            // user
            PreparedStatement preparedStatement4 = connection.prepareStatement(
                    "delete from user where email = ?;"
            );
            preparedStatement4.setString(1, useremail);
            preparedStatement4.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Post post
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
    public int editPost(String post_idstr, String title, String details, boolean type, List<String> tag_ids) {
        try {
            // uodate new post
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update post set title=?, details=?, type=? where id = ?;"
            );
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, details);
            preparedStatement.setInt(3, type ? 1 : 0);
            preparedStatement.setInt(4, Integer.parseInt(post_idstr));
            preparedStatement.executeUpdate();

            // delete old has_tags
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "delete from has_tags where post_id=?;"
            );
            preparedStatement1.setString(1, post_idstr);
            preparedStatement1.executeUpdate();

            // insert new has_tags
            if (tag_ids != null) {
                for (int i = 0; i < tag_ids.size(); ++i) {
                    PreparedStatement preparedStatement2 = connection.prepareStatement(
                            "insert into has_tags (post_id, tag_id) value (?, ?);"
                    );
                    preparedStatement2.setString(1, post_idstr);
                    preparedStatement2.setString(2, tag_ids.get(i));
                    preparedStatement2.executeUpdate();
                }
            }

            return Integer.parseInt(post_idstr);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Integer.parseInt(post_idstr);
    }

    @Override
    public int newPost(String user_email, String title, String details, boolean type, List<String> tag_ids) {
        try {

            // insert new post
            Connection connection = JDBCUtil.getConnection();
            Statement smt = connection.createStatement();
            ResultSet rs = smt.executeQuery("select max(id) + 1 next_id from post");
            int post_id = 1;
            if (rs.next()) {
                String spost_id = rs.getString("next_id");
                if (spost_id != null && !spost_id.equals("null"))
                    post_id = Integer.parseInt(spost_id);
            }
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into post(id, title, details, type, post_date) value (?,?,?,?,?);"
            );
            preparedStatement.setInt(1, post_id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, details);
            preparedStatement.setInt(4, type ? 1 : 0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            preparedStatement.setString(5, format.format(new Date()));
            preparedStatement.executeUpdate();

            // insert new user_post
            PreparedStatement preparedStatement1 = connection.prepareStatement(
                    "insert into user_post value (?,?);"
            );
            preparedStatement1.setString(1, user_email);
            preparedStatement1.setInt(2, post_id);
            preparedStatement1.executeUpdate();

            // insert new has_tags
            for (int i = 0; i < tag_ids.size(); ++i) {
                PreparedStatement preparedStatement2 = connection.prepareStatement(
                        "insert into has_tags (post_id, tag_id) value (?, ?);"
                );
                preparedStatement2.setString(1, String.valueOf(post_id));
                preparedStatement2.setString(2, tag_ids.get(i));
                preparedStatement2.executeUpdate();
            }
            return post_id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void editTitle(String post_id, String title) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update post set title=? where ID=?;"
            );
            preparedStatement.setString(1, title);
            preparedStatement.setString(2, post_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editDetail(String post_id, String detail) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "update post set details=? where ID=?;"
            );
            preparedStatement.setString(1, detail);
            preparedStatement.setString(2, post_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPictureToPost(String post_id, String picpath) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into has_pics value (?,?);"
            );
            preparedStatement.setString(1, post_id);
            preparedStatement.setString(2, picpath);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePictureFromPost(String post_id, String picpath) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from has_pics where post_id=? and picpath=?;"
            );
            preparedStatement.setString(1, post_id);
            preparedStatement.setString(2, picpath);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Personal information
    @Override
    public void personInformation(String useremail, String username, String profile, String selfintro) {
        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "update user set name=?, profile=?, self_intro=? where email=?");
            ps.setString(1, username);
            ps.setString(2, profile);
            ps.setString(3, selfintro);
            ps.setString(4, useremail);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void personPassword(String useremail, String password) {
        try {
            Connection conn = JDBCUtil.getConnection();
            PreparedStatement ps = conn.prepareStatement("update user set password=? where email=?");
            ps.setString(1, password);
            ps.setString(2, useremail);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // View posts
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
    public List<Post> findPostByUserEamil(String user_email) {
        List<Post> list = new ArrayList<Post>();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select post_id from user_post where user_email = ?;"
            );
            preparedStatement.setString(1, user_email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(findPostById(rs.getInt("post_id")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(list);
        return list;
    }

    private List<Post> getPosts(Connection connection, PreparedStatement preparedStatement) throws SQLException, ParseException {
        ResultSet rs = preparedStatement.executeQuery();
        List<Post> list = new ArrayList<Post>();
        while (rs.next()) {
            Post post = new Post();
            post.setPost_id(rs.getString("id"));
            post.setPost_title(rs.getString("title"));
            post.setDetail(rs.getString("details"));
            post.setPost_type(rs.getInt("type") != 0);
            post.setPost_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("post_date")));

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

            list.add(post);
        }
        return list;
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

    //View Ann
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

    // Concern

    @Override
    public List<Concern> findConcernByUserEmail(User user) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from concern where user_email=?;"
            );
            preparedStatement.setString(1, user.getEmail());
            ResultSet rs = preparedStatement.executeQuery();
            List<Concern> list = new ArrayList<Concern>();
            while (rs.next()) {
                Concern concern = new Concern();
                concern.setUser(user);
                Post post = findPostById(rs.getInt("post_id"));
                concern.setPost(post);
                list.add(concern);
            }
            Collections.reverse(list);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addConcern(String user_email, String post_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into concern value (?,?);"
            );
            preparedStatement.setString(1, user_email);
            preparedStatement.setString(2, post_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean findConcern(String user_email, String post_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from concern where user_email=? and post_id=?;"
            );
            preparedStatement.setString(1, user_email);
            preparedStatement.setString(2, post_id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    @Override
    public void deleteConcern(String user_email, String post_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from concern where user_email=? and post_id=?;"
            );
            preparedStatement.setString(1, user_email);
            preparedStatement.setString(2, post_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Tag
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
    public void addTag(String post_id, String tag_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into has_tags value (?,?);"
            );
            preparedStatement.setString(1, post_id);
            preparedStatement.setString(2, tag_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTag(String post_id, String tag_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from has_tags where post_id=? and tag_id=?;"
            );
            preparedStatement.setString(1, post_id);
            preparedStatement.setString(2, tag_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String user_email, String receiver_email, String content) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into messages(sender_email, receiver_email, content, msg_date) value (?,?,?,?)"
            );
            preparedStatement.setString(1, user_email);
            preparedStatement.setString(2, receiver_email);
            preparedStatement.setString(3, content);
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            preparedStatement.setString(4, formatter.format(date));
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Pair<Messages, User>> getAllMsgs(String receiver_email) {
        List<Pair<Messages, User>> list = new ArrayList<>();
        try {
            List<Messages> msgs = new ArrayList<>();
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "select * from messages where receiver_email=? order by msg_date desc;"
            );
            preparedStatement.setString(1, receiver_email);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Messages messages = new Messages();
                messages.setMsg_id(rs.getString("id"));
                messages.setSender_email(rs.getString("sender_email"));
                messages.setReceiver_email(rs.getString("receiver_email"));
                messages.setContent(rs.getString("content"));

                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                messages.setDate(formatter.parse(rs.getString("msg_date")));

                msgs.add(messages);
            }

            for (int i = 0; i != msgs.size(); ++i) {
                User sender = new User();
                try {
                    PreparedStatement preparedStatement1 = connection.prepareStatement("select * from user where email=?");
                    preparedStatement1.setString(1, msgs.get(i).getSender_email());
                    ResultSet executeQuery = preparedStatement1.executeQuery();
                    if (executeQuery.next()) {
                        sender.setEmail(executeQuery.getString("email"));
                        sender.setPassword(executeQuery.getString("password"));
                        sender.setUsername(executeQuery.getString("name"));
                        sender.setPorfile(executeQuery.getString("profile"));
                        sender.setSelfintro(executeQuery.getString("self_intro"));
                    }
                    list.add(new Pair<>(msgs.get(i), sender));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteMsg(String msg_id) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "delete from messages where ID = ?;"
            );
            preparedStatement.setString(1, msg_id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
