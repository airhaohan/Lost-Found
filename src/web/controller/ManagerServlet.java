package web.controller;

import com.google.gson.Gson;
import domain.*;
import service.ManagerService;
import service.impl.ManagerServiceImpl;

import javax.lang.model.element.AnnotationMirror;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ManagerServlet", value = "/ManagerServlet")
public class ManagerServlet extends HttpServlet {
    private final ManagerService service = new ManagerServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String op = request.getParameter("op");
        if (op != null && !op.equals("")) {
            if (op.equals("aloginin")) {
                aloginin(request, response);
            }
            if (op.equals("gettenmoreposts")) {
                gettenmoreposts(request, response);
            }
            if (op.equals("gettentypemoreposts")) {
                gettentypemoreposts(request, response);
            }
            if (op.equals("postdetail")) {
                postdetail(request, response);
            }
            if (op.equals("afoundpost")) {
                afoundpost(request, response);
            }
            if (op.equals("alostpost")) {
                alostpost(request, response);
            }
            if (op.equals("othersinfo")) {
                othersinfo(request, response);
            }
            if (op.equals("viewpost")) {
                viewpost(request, response);
            }
            if (op.equals("apostlist")) {
                apostlist(request, response);
            }
            if (op.equals("deletepost")) {
                deletepost(request, response);
            }
            if (op.equals("selsearch")) {
                selsearch(request, response);
            }
            if (op.equals("seltags")) {
                seltags(request, response);
            }
            if (op.equals("getseltags")) {
                getseltags(request, response);
            }
            if (op.equals("viewannounce")) {
                viewannounce(request, response);
            }
            if (op.equals("gettenmoreanns")) {
                gettenmoreanns(request, response);
            }
            if (op.equals("editann")) {
                editann(request, response);
            }
            if (op.equals("editannounce")) {
                try {
                    editannounce(request, response);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if (op.equals("newann")) {
                newann(request, response);
            }
            if (op.equals("newannounce")) {
                newannounce(request, response);
            }
            if (op.equals("deleteannounce")) {
                deleteannounce(request, response);
            }
            if (op.equals("newadmin")) {
                newadmin(request, response);
            }
            if (op.equals("editpassword")) {
                editpassword(request, response);
            }
            if (op.equals("taglist")) {
                taglist(request, response);
            }
            if (op.equals("edittag")) {
                edittag(request, response);
            }
            if (op.equals("addtag")) {
                addtag(request, response);
            }
            if (op.equals("deletetag")) {
                deletetag(request, response);
            }
            if (op.equals("userlist")) {
                userlist(request, response);
            }
            if (op.equals("searchusername")) {
                searchusername(request, response);
            }
            if (op.equals("gettenmoreusers")) {
                gettenmoreusers(request, response);
            }
            if (op.equals("deleteuser")) {
                deleteuser(request, response);
            }
            if (op.equals("adminlist")) {
                adminlist(request, response);
            }
        } else {
        }

    }

    private void visitor(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("left", "unloginin");
        session.setAttribute("right", "viewpost");

//        List<Post> initposts = service.getTenPosts(0);
//        session.setAttribute("initposts", initposts);

        response.setHeader("refresh", "0;URL=client.jsp");
//        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void aloginin (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String adminid = request.getParameter("adminid");
        String apassword = request.getParameter("apassword");
        Administrator admin = service.login(adminid, apassword);
        if (admin != null && !admin.getAdmin_id().equals("")) {
            request.setAttribute("message", "登录成功！");
            session.setAttribute("left", "aloginin");
            session.setAttribute("right", "apostlist");
            session.setAttribute("loginin", true);
            session.setAttribute("admin", admin);

//            List<Post> initposts = service.getTenPosts(0);
//            session.setAttribute("initposts", initposts);
//            // init filter
//            session.setAttribute("searchword", null);
//            session.setAttribute("tagsselected", null);

            request.getRequestDispatcher("/abetween.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "登录失败！请您检查ID或密码是否正确");
            request.getRequestDispatcher("/abetween.jsp").forward(request, response);
        }
    }

    private void gettenmoreposts (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int postBeginIdx = Integer.parseInt(request.getParameter("postBeginIdx"));
        List<Post> posts = new ArrayList<>();
        List<String> tagsselected = (List<String>) session.getAttribute("tagsselected");
        String searchword = (String) session.getAttribute("searchword");
        if ((tagsselected == null || tagsselected.size() == 0) && (searchword == null || searchword.equals(""))) {
            posts = service.getTenPosts(postBeginIdx);
        } else if (searchword == null || searchword.equals("")) {
            posts = service.getTenTagsPost(postBeginIdx, tagsselected);
        } else {
            posts = service.getTenSearchTagsPost(postBeginIdx, searchword, tagsselected);
        }

        String postListStr = "[";
        for (int i = 0; i != posts.size(); ++i) {
            postListStr += posts.get(i).toString();
            if (i != posts.size() - 1)
                postListStr += ",";
        }
        postListStr += "]";

        Gson gson = new Gson();
        String json = gson.toJson(postListStr);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void gettentypemoreposts (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int postBeginIdx = Integer.parseInt(request.getParameter("postBeginIdx"));
        String right = (String) session.getAttribute("right");
        int type = right.equals("afoundpost") ? 0 : 1;
        List<Post> posts = new ArrayList<>();
        List<String> tagsselected = (List<String>) session.getAttribute("tagsselected");
        String searchword = (String) session.getAttribute("searchword");
        if ((tagsselected == null || tagsselected.size() == 0) && (searchword == null || searchword.equals(""))) {
            posts = service.getTenTypePosts(postBeginIdx, type);
        } else if (searchword == null || searchword.equals("")) {
            posts = service.getTenTypeTagsPost(postBeginIdx, type, tagsselected);
        } else {
            posts = service.getTenTypeSearchTagsPost(postBeginIdx, type, searchword, tagsselected);
        }

        String postListStr = "[";
        for (int i = 0; i != posts.size(); ++i) {
            postListStr += posts.get(i).toString();
            if (i != posts.size() - 1)
                postListStr += ",";
        }
        postListStr += "]";

        Gson gson = new Gson();
        String json = gson.toJson(postListStr);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void postdetail (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String post_id = request.getParameter("postid");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User("visitor@lost'found", "visitor", "img/unloginin-profile.webp", "", "visitor");
            session.setAttribute("user", user);
        }

        if (post_id == null && session.getAttribute("nowpost") != null) {
            post_id = ((Post)session.getAttribute("nowpost")).getPost_id();
        }
        Post nowpost = service.findPostById(Integer.parseInt(post_id));
        User entity = service.findUserByPostid(post_id);
        List<Tag> tags = service.findTagsByPostId(post_id);

        session.setAttribute("nowentity", entity);
        session.setAttribute("nowpost", nowpost);
        session.setAttribute("nowposttags", tags);

        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "apostdetail");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void afoundpost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<Post> initposts = service.getTenTypePosts(0, 0);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "afoundpost");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void alostpost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<Post> initposts = service.getTenTypePosts(0, 1);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "alostpost");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void othersinfo (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        User entity = (User) session.getAttribute("nowentity");

        String searchusername = entity.getUsername();
        List<User> inituser = service.getTenSearchUsers(0, searchusername);
        session.setAttribute("inituser", inituser);
        session.setAttribute("searchusername", searchusername);
        session.setAttribute("right", "userlist");
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }

    private void viewpost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "apostlist");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void apostlist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        viewpost(request, response);
    }

    private void deletepost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String post_idstr = ((Post)session.getAttribute("nowpost")).getPost_id();
        service.deletePost(post_idstr);

        session.setAttribute("nowentity", null);
        session.setAttribute("nowpost", null);
        session.setAttribute("nowposttags", null);
        session.setAttribute("right", session.getAttribute("rbackpage"));

        List<Post> initposts;
        String right = (String) session.getAttribute("right");
        if (right.equals("apostlist")) {
            initposts = service.getTenPosts(0);
        } else if (right.equals("afoundpost")) {
            initposts = service.getTenTypePosts(0, 0);
        } else {
            initposts = service.getTenTypePosts(0, 1);
        }
        session.setAttribute("initposts", initposts);

        request.setAttribute("message", "删除成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void selsearch (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<String> tagsselected = (List<String>) session.getAttribute("tagsselected");
        String searchword = request.getParameter("searchword");
        session.setAttribute("searchword", searchword);

        // 初始化
        String right = (String) session.getAttribute("right");
        List<Post> initposts = new ArrayList<>();
        if (right.equals("apostlist")) {
            initposts = service.getTenSearchTagsPost(0, searchword, tagsselected);
        } else if (right.equals("afoundpost")) {
            initposts = service.getTenTypeSearchTagsPost(0, 0, searchword, tagsselected);
        } else {
            initposts = service.getTenTypeSearchTagsPost(0, 1, searchword, tagsselected);
        }

        session.setAttribute("initposts", initposts);
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }

    private void seltags (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String[] tags_id = request.getParameterValues("tags");
        List<String> tagsselected = new ArrayList<String>();
        if (tags_id != null && tags_id.length > 0) {
            tagsselected.addAll(Arrays.asList(tags_id));
        }
        session.setAttribute("tagsselected", tagsselected);
        session.setAttribute("searchword", null);
        // 初始化
        String right = (String) session.getAttribute("right");
        List<Post> initposts = new ArrayList<>();
        if (right.equals("apostlist")) {
            initposts = service.getTenTagsPost(0, tagsselected);
        } else if (right.equals("afoundpost")) {
            initposts = service.getTenTypeTagsPost(0, 0, tagsselected);
        } else {
            initposts = service.getTenTypeTagsPost(0, 1, tagsselected);
        }

        session.setAttribute("initposts", initposts);
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }

    private void getseltags (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<String> tagsselected = (List<String>) session.getAttribute("tagsselected");
        String seltags = "[";
        for (int i = 0; i != tagsselected.size(); ++i) {
            seltags += "\"" + tagsselected.get(i) + "\"";
            if (i != tagsselected.size() - 1)
                seltags += ",";
        }
        seltags += "]";

        Gson gson = new Gson();
        String json = gson.toJson(seltags);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void viewannounce (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Announce> initanns =  service.getTenAnnounces(0);
        session.setAttribute("initanns", initanns);
        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "aviewannounce");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void gettenmoreanns (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int annBeginIdx = Integer.parseInt(request.getParameter("annBeginIdx"));
        List<Announce> anns = service.getTenAnnounces(annBeginIdx);
        String annListStr = "[";
        for (int i = 0; i != anns.size(); ++i) {
            annListStr += anns.get(i).toString();
            if (i != anns.size() - 1)
                annListStr += ",";
        }
        annListStr += "]";
        Gson gson = new Gson();
        String json = gson.toJson(annListStr);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void editann (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String annid = request.getParameter("annid");
        Announce announce = service.findAnnounceById(annid);
        session.setAttribute("nowannounce", announce);

        session.setAttribute("rbackpage", "viewannounce");
        session.setAttribute("right", "editannounce");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void editannounce (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        Announce announce = (Announce) session.getAttribute("nowannounce");

        String title = request.getParameter("title");
        title = title.replaceAll("[\n\t]", "");

        String content = request.getParameter("content");
        content = content.replaceAll("[\n\t]", "");

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        String datestr = request.getParameter("date");
        Date anndate = formatter.parse(datestr);

        announce.setTitle(title);
        announce.setContent(content);
        announce.setDate(anndate);

        service.editAnnounce(announce);

        List<Announce> initanns =  service.getTenAnnounces(0);
        session.setAttribute("initanns", initanns);

        session.setAttribute("right", "aviewannounce");
        request.setAttribute("message", "修改公告成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void newann (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        session.setAttribute("rbackpage", (String) session.getAttribute("right"));
        session.setAttribute("right", "newannounce");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void newannounce (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        Announce announce = new Announce();
        Administrator admin = (Administrator) session.getAttribute("admin");

        String title = request.getParameter("title");
        title = title.replaceAll("[\n\t]", "");

        String content = request.getParameter("content");
        content = content.replaceAll("[\n\t]", "");

        Date anndate = new Date();

        announce.setTitle(title);
        announce.setContent(content);
        announce.setDate(anndate);
        announce.setAdmin_id(admin.getAdmin_id());

        service.addAnnounce(announce);

        List<Announce> initanns =  service.getTenAnnounces(0);
        session.setAttribute("initanns", initanns);

        session.setAttribute("right", "aviewannounce");
        request.setAttribute("message", "发布公告成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void deleteannounce (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Announce announce = (Announce) session.getAttribute("nowannounce");
        service.deleteAnnounce(announce.getAnn_id());

        List<Announce> initanns =  service.getTenAnnounces(0);
        session.setAttribute("initanns", initanns);

        session.setAttribute("right", "aviewannounce");
        request.setAttribute("message", "删除公告成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void newadmin (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String adminid = request.getParameter("adminid");
        String password = request.getParameter("apassword");

        boolean succ = service.newAdmin(adminid, password);

        if (succ) {
            List<Administrator> adminlist = service.getAllAdmins();
            session.setAttribute("adminlist", adminlist);

            request.setAttribute("message", "添加成功！~");
        } else {
            request.setAttribute("message", "添加失败！~");
        }
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void editpassword (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        Administrator admin = (Administrator) session.getAttribute("admin");
        String newpassword = request.getParameter("password");
        admin.setPassword(newpassword);
        service.managerPassword(admin);
        session.setAttribute("admin", admin);

        request.setAttribute("message", "修改成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);

    }

    private void taglist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<Tag> taglist = service.findAllTags();
        session.setAttribute("taglist", taglist);
        session.setAttribute("right", "taglist");
        response.setHeader("refresh", "0;URL=manager.jsp");
    }

    private void edittag (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        Tag tag = new Tag();
        String tagid = request.getParameter("tagid");
        String tagname = request.getParameter("tagname");
        tag.setTag_id(tagid);
        tag.setTag_name(tagname);
        boolean succ = service.editTag(tag);

        if (succ) {
            List<Tag> tags = service.findAllTags();
            session.setAttribute("tags", tags);
            session.setAttribute("taglist", tags);
            request.setAttribute("message", "修改成功！~");
        } else {
            request.setAttribute("message", "修改失败！请检查是否重名~");
        }
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void addtag (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String tagname = request.getParameter("tagname");
        service.addTag(tagname);

        List<Tag> tags = service.findAllTags();
        session.setAttribute("tags", tags);
        session.setAttribute("taglist", tags);

        request.setAttribute("message", "添加成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void deletetag (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String tagid = request.getParameter("tagid");
        service.deleteTag(tagid);

        List<Tag> tags = service.findAllTags();
        session.setAttribute("tags", tags);
        session.setAttribute("taglist", tags);

        request.setAttribute("message", "删除成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void userlist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<User> inituser = service.getTenUsers(0);
        session.setAttribute("inituser", inituser);
        session.setAttribute("searchusername", null);
        session.setAttribute("right", "userlist");

        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }

    private void searchusername (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String searchusername = request.getParameter("searchword");
        List<User> inituser = service.getTenSearchUsers(0, searchusername);
        session.setAttribute("inituser", inituser);
        session.setAttribute("searchusername", searchusername);
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }

    private void gettenmoreusers (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String searchusername = (String) session.getAttribute("searchusername");
        int userBeginIdx = Integer.parseInt(request.getParameter("userBeginIdx"));
        List<User> userlist = new ArrayList<>();
        if (searchusername == null || searchusername.equals("")) {
            userlist = service.getTenUsers(userBeginIdx);
        } else {
            userlist = service.getTenSearchUsers(userBeginIdx, searchusername);
        }

        String userListStr = "[";
        for (int i = 0; i != userlist.size(); ++i) {
            userListStr += userlist.get(i).toString();
            if (i != userlist.size() - 1)
                userListStr += ",";
        }
        userListStr += "]";

        Gson gson = new Gson();
        String json = gson.toJson(userListStr);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void deleteuser (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        String deleteuseremail = request.getParameter("deleteuseremail");
        service.deleteUser(deleteuseremail);

        String searchusername = (String) session.getAttribute("searchusername");
        List<User> userlist = new ArrayList<>();
        if (searchusername == null || searchusername.equals("")) {
            userlist = service.getTenUsers(0);
        } else {
            userlist = service.getTenSearchUsers(0, searchusername);
        }

        request.setAttribute("message", "删除成功！~");
        request.getRequestDispatcher("/abetween.jsp").forward(request, response);
    }

    private void adminlist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        List<Administrator> adminlist = service.getAllAdmins();
        session.setAttribute("adminlist", adminlist);

        session.setAttribute("right", "adminlist");
        request.getRequestDispatcher("/manager.jsp").forward(request, response);
    }



    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
