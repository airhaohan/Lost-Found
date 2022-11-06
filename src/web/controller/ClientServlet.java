package web.controller;

import com.google.gson.Gson;
import domain.User;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import domain.*;
import javafx.geometry.Pos;
import javafx.util.Pair;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ClientService;
import service.ManagerService;
import service.impl.ClientServiceImpl;
import service.impl.ManagerServiceImpl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ClientServlet", value = "/ClientServlet")
@MultipartConfig
public class ClientServlet extends HttpServlet {
    private final ClientService service = new ClientServiceImpl();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String op = request.getParameter("op");
        if (op != null && !op.equals("")) {
            if (op.equals("visitor")) {
                visitor(request, response);
            }
            if (op.equals("loginin")) {
                loginin(request, response);
            }
            if (op.equals("register")) {
                register(request, response);
            }
            if (op.equals("viewpost")) {
                viewpost(request, response);
            }
            if (op.equals("gettenmoreposts")) {
                gettenmoreposts(request, response);
            }
            if (op.equals("gettentypemoreposts")) {
                gettentypemoreposts(request, response);
            }
            if (op.equals("viewannounce")) {
                viewannounce(request, response);
            }
            if (op.equals("gettenmoreanns")) {
                gettenmoreanns(request, response);
            }
            if (op.equals("usercenter")) {
                usercenter(request, response);
            }
            if (op.equals("editpassword")) {
                editpassword(request, response);
            }
            if (op.equals("editselfintro")) {
                editselfintro(request, response);
            }
            if (op.equals("editusername")) {
                editusername(request, response);
            }
            if (op.equals("editprofile")) {
                editprofile(request, response);
            }
            if (op.equals("newpost")) {
                newpost(request, response);
            }
            if (op.equals("postpost")) {
                postpost(request, response);
            }
            if (op.equals("postdetail")) {
                postdetail(request, response);
            }
            if (op.equals("editpost")) {
                editpost(request, response);
            }
            if (op.equals("selectedtags")) {
                selectedtags(request, response);
            }
            if (op.equals("savepost")) {
                savepost(request, response);
            }
            if (op.equals("deletepost")) {
                deletepost(request, response);
            }
            if (op.equals("deletepostpic")) {
                deletepostpic(request, response);
            }
            if (op.equals("otherscenter")) {
                otherscenter(request, response);
            }
            if (op.equals("sendmsg")) {
                sendmsg(request, response);
            }
            if (op.equals("userpostlist")) {
                userpostlist(request, response);
            }
            if (op.equals("msglist")) {
                msglist(request, response);
            }
            if (op.equals("deletemsg")) {
                deletemsg(request, response);
            }
            if (op.equals("addconcern")) {
                addconcern(request, response);
            }
            if (op.equals("deleteconcern")) {
                deleteconcern(request, response);
            }
            if (op.equals("concernlist")) {
                concernlist(request, response);
            }
            if (op.equals("foundpost")) {
                foundpost(request, response);
            }
            if (op.equals("lostpost")) {
                lostpost(request, response);
            }
            if (op.equals("seltags")) {
                seltags(request, response);
            }
            if (op.equals("selsearch")) {
                selsearch(request, response);
            }
            if (op.equals("getseltags")) {
                getseltags(request, response);
            }
            if (op.equals("deleteaccount")) {
                deleteaccount(request, response);
            }
        }
        else {
        }
    }

    private void visitor(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("left", "unloginin");
        session.setAttribute("right", "viewpost");
        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        response.setHeader("refresh", "0;URL=client.jsp");
//        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void loginin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        if (session.getAttribute("user") != null && !((User) session.getAttribute("user")).getEmail().equals("visitor@lost'found")) {
            session.setAttribute("left", "loginin");
            response.setHeader("refresh", "0;URL=client.jsp");
            return;
        }
        String useremail = request.getParameter("useremail");
        String password = request.getParameter("password");
        User user = service.login(useremail, password);
        if (user.getUsername() != null && !user.getUsername().equals("")) {
            request.setAttribute("message", "登录成功！");
            session.setAttribute("left", "loginin");
            session.setAttribute("right", "viewpost");
            session.setAttribute("loginin", true);
            if (user.getProfile() == null || user.getProfile().equals("")) {
                user.setPorfile("img/unloginin-profile.webp");
            }
            session.setAttribute("user", user);
            request.getRequestDispatcher("/between.jsp").forward(request, response);
        } else {
            request.setAttribute("message", "登录失败！请您检查邮箱或密码是否正确");
            request.getRequestDispatcher("/between.jsp").forward(request, response);
        }

    }

    private void register (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String useremail = request.getParameter("useremail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        boolean hasBeenReg = service.register(useremail, username, password);
        if (hasBeenReg) {
            request.setAttribute("message", "该邮箱已被注册！");
            request.getRequestDispatcher("/between.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            User user = service.login(useremail, password);
            session.setAttribute("user", user);
            if (user.getProfile() == null || user.getProfile().equals("")) {
                user.setPorfile("img/unloginin-profile.webp");
            }
            request.setAttribute("message", "注册成功！");
            session.setAttribute("left", "loginin");
            session.setAttribute("right", "viewpost");

            List<Post> initposts = service.getTenPosts(0);
            session.setAttribute("initposts", initposts);

            request.getRequestDispatcher("/between.jsp").forward(request, response);
        }
    }

    private void viewpost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession();

        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "viewpost");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void gettenmoreposts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        int type = right.equals("foundpost") ? 0 : 1;
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

    private void viewannounce(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<Announce> initanns =  service.getTenAnnounces(0);
        session.setAttribute("initanns", initanns);
        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "viewannounce");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void gettenmoreanns(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    private void usercenter (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("left", "usercenter");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void editpassword (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String newpassword  = request.getParameter("password");
        if (newpassword == null || newpassword.equals("")) {
            request.setAttribute("message", "密码不能为空，更改失败！");
            request.getRequestDispatcher("/between.jsp").forward(request, response);
            return;
        }
        User user = (User) session.getAttribute("user");
        service.personPassword(user.getEmail(), newpassword);
        user = service.login(user.getEmail(), user.getPassword());
        session.setAttribute("user", user);
        request.setAttribute("message", "修改密码成功！");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void editselfintro (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String newselfintro = request.getParameter("selfintro");
        User user = (User) session.getAttribute("user");
        service.personInformation(user.getEmail(), user.getUsername(), user.getProfile(), newselfintro);
        user = service.login(user.getEmail(), user.getPassword());
        session.setAttribute("user", user);
        request.setAttribute("message", "修改个人介绍成功！");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void editusername (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String newusername = request.getParameter("username");
        User user = (User) session.getAttribute("user");
        service.personInformation(user.getEmail(), newusername, user.getProfile(), user.getSelfintro());
        user = service.login(user.getEmail(), user.getPassword());
        session.setAttribute("user", user);
        request.setAttribute("message", "修改昵称成功！");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void editprofile (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
            // 检测是否为多媒体上传
            if (!ServletFileUpload.isMultipartContent(request)) {
                // 如果不是则停止
                request.setAttribute("message", "非文件上传！");
                request.getRequestDispatcher("/between.jsp").forward(request, response);
                return;
            }
            // 配置上传参数
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 中文处理
            upload.setHeaderEncoding("UTF-8");
            String uploadPath = "F:\\LAF\\image";

            // 如果目录不存在则创建
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            try {
                // 解析请求的内容提取文件数据
                @SuppressWarnings("unchecked")
                List<FileItem> formItems = upload.parseRequest(request);
                if (formItems != null && formItems.size() > 0) {
                    // 迭代表单数据
                    for (FileItem item : formItems) {
                        // 处理不在表单中的字段
                        if (!item.isFormField()) {
                            String curTime = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
                            String fileName = curTime + new File(item.getName()).getName();
                            String filePath = uploadPath + File.separator + fileName;
                            File storeFile = new File(filePath);
                            // 保存文件到硬盘
                            item.write(storeFile);
                            request.setAttribute("message", "文件上传成功!");
                            //更新用户信息
                            HttpSession session = request.getSession();
                            User user = (User) session.getAttribute("user");
                            service.personInformation(user.getEmail(),
                                    user.getUsername(),
                                    "/myimage/" + fileName,
                                    user.getSelfintro());
                            user = service.login(user.getEmail(), user.getPassword());
                            session.setAttribute("user", user);
                        }
                    }
                }
            } catch (Exception e) {
                request.setAttribute("message", "错误信息: " + e.getMessage());
            }
            request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void newpost (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("tags") == null) {
            List<Tag> tags = service.findAllTags();
            session.setAttribute("tags", tags);
        }

        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "newpost");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void postpost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");

        String[] tags_id = request.getParameterValues("tags");
        List<String> tag_ids = new ArrayList<String>();
        if (tags_id != null && tags_id.length > 0) {
            tag_ids.addAll(Arrays.asList(tags_id));
        }

        String title = request.getParameter("title");
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(title);
        title = m.replaceAll("");

        String content = request.getParameter("content");
        Matcher m2 = p.matcher(content);
        content = m2.replaceAll("");

//        String title = request.getParameter("title");
//        title = title.replaceAll("[\n\t]", "<br/>");
//
//        String content = request.getParameter("content");
//        content = content.replaceAll("[\n\t]", "<br/>");

        String typestr = request.getParameter("type");
        boolean type = typestr.equals("lost");

        int post_id = service.newPost(user.getEmail(), title, content, type, tag_ids);
        Post nowpost = service.findPostById(post_id);
        User entity = service.findUserByPostid(Integer.toString(post_id));
        List<Tag> tags = service.findTagsByPostId(Integer.toString(post_id));
        boolean concerned = service.findConcern(user.getEmail(), nowpost.getPost_id());

        session.setAttribute("nowentity", entity);
        session.setAttribute("nowpost", nowpost);
        session.setAttribute("nowposttags", tags);
        session.setAttribute("concerned", concerned);
        session.setAttribute("right", "postdetail");

        request.setAttribute("message", "发布成功！可以在详情页添加更多图片描述~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void postdetail(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
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
        boolean concerned = service.findConcern(user.getEmail(), nowpost.getPost_id());

        session.setAttribute("nowentity", entity);
        session.setAttribute("nowpost", nowpost);
        session.setAttribute("nowposttags", tags);
        session.setAttribute("concerned", concerned);

        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "postdetail");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void editpost (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("tags") == null) {
            List<Tag> tags = service.findAllTags();
            session.setAttribute("tags", tags);
        }

        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "editpost");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void selectedtags (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession();
        List<Tag> selectedtags = service.findTagsByPostId(((Post)session.getAttribute("nowpost")).getPost_id());
        String selectedTagsStr = "[";
        for (int i = 0; i != selectedtags.size(); ++i) {
            selectedTagsStr = selectedTagsStr + selectedtags.get(i).toString();
            if (i != selectedtags.size() - 1)
                selectedTagsStr += ",";
        }
        selectedTagsStr += "]";
        Gson gson = new Gson();
        String json = gson.toJson(selectedTagsStr);
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(json);
    }

    private void savepost (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");
        String post_idstr = ((Post)session.getAttribute("nowpost")).getPost_id();

        String[] tags_id = request.getParameterValues("tags");
        List<String> tag_ids = new ArrayList<String>();
        tag_ids.addAll(Arrays.asList(tags_id));

        String title = request.getParameter("title");
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(title);
        title = m.replaceAll("");

        String content = request.getParameter("content");
        Matcher m2 = p.matcher(content);
        content = m2.replaceAll("");

//        String title = request.getParameter("title");
//        title = title.replaceAll("[\n\t]", "<br/>");
//
//        String content = request.getParameter("content");
//        content = content.replaceAll("[\n\t]", "<br/>");

        String typestr = request.getParameter("type");
        boolean type = typestr.equals("lost");

        service.editPost(post_idstr, title, content, type, tag_ids);

        Post nowpost = service.findPostById(Integer.parseInt(post_idstr));
        User entity = service.findUserByPostid(post_idstr);
        List<Tag> tags = service.findTagsByPostId(post_idstr);

        session.setAttribute("nowentity", entity);
        session.setAttribute("nowpost", nowpost);
        session.setAttribute("nowposttags", tags);
        session.setAttribute("right", "postdetail");

        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        session.setAttribute("rbackpage", "viewpost");

        request.setAttribute("message", "修改成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
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
        if (right.equals("viewpost")) {
            initposts = service.getTenPosts(0);
        } else if (right.equals("foundpost")) {
            initposts = service.getTenTypePosts(0, 0);
        } else {
            initposts = service.getTenTypePosts(0, 1);
        }
        session.setAttribute("initposts", initposts);

        request.setAttribute("message", "删除成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void deletepostpic (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        String post_idstr = ((Post)session.getAttribute("nowpost")).getPost_id();

        String picpath = request.getParameter("picpath");

        service.deletePictureFromPost(post_idstr, picpath);

        Post nowpost = service.findPostById(Integer.parseInt(post_idstr));

        session.setAttribute("nowpost", nowpost);
        session.setAttribute("right", "postdetail");

        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        session.setAttribute("rbackpage", "viewpost");

        request.setAttribute("message", "删除成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void otherscenter (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");
        User entity = (User) session.getAttribute("nowentity");

        if (user.getEmail().equals(entity.getEmail())) {
            session.setAttribute("left", "usercenter");
        } else {
            session.setAttribute("left", "otherscenter");
        }

        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void sendmsg (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");
        User entity = (User) session.getAttribute("nowentity");

        String msgcontent = request.getParameter("msgcontent");
        service.sendMessage(user.getEmail(), entity.getEmail(), msgcontent);

        request.setAttribute("message", "发送成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void userpostlist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");
        List<Post> userposts = service.findPostByUserEamil(user.getEmail());
        session.setAttribute("userposts", userposts);

        session.setAttribute("rbackpage", session.getAttribute("right"));
        session.setAttribute("right", "userpostlist");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void msglist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        User user = (User) session.getAttribute("user");

        List<Pair<Messages, User>> msglist = service.getAllMsgs(user.getEmail());
        session.setAttribute("msglist", msglist);

        session.setAttribute("left", "msglist");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void deletemsg (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");
        List<Pair<Messages, User>> msglist = (List<Pair<Messages, User>>) session.getAttribute("msglist");
        String delMsgId = request.getParameter("delmsgid");
        service.deleteMsg(delMsgId);
        for (int i = 0; i < msglist.size(); i++) {
            if (msglist.get(i).getKey().getMsg_id().equals(delMsgId)) {
                msglist.remove(i);
                break;
            }
        }
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void addconcern (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        User user = (User) session.getAttribute("user");
        Post nowpost = (Post) session.getAttribute("nowpost");
        service.addConcern(user.getEmail(), nowpost.getPost_id());
        session.setAttribute("concerned", true);

        List<Concern> concernlist = service.findConcernByUserEmail(user);
        session.setAttribute("concernlist", concernlist);

        request.setAttribute("message", "添加关注成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void deleteconcern (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        User user = (User) session.getAttribute("user");
        Post nowpost = (Post) session.getAttribute("nowpost");
        service.deleteConcern(user.getEmail(), nowpost.getPost_id());
        session.setAttribute("concerned", false);

        List<Concern> concernlist = service.findConcernByUserEmail(user);
        session.setAttribute("concernlist", concernlist);

        request.setAttribute("message", "取消关注成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }

    private void concernlist (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        User user = (User) session.getAttribute("user");
        List<Concern> concernlist = service.findConcernByUserEmail(user);
        session.setAttribute("concernlist", concernlist);

        session.setAttribute("left", "concernlist");

        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void foundpost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<Post> initposts = service.getTenTypePosts(0, 0);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "foundpost");
        response.setHeader("refresh", "0;URL=client.jsp");
    }

    private void lostpost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        List<Post> initposts = service.getTenTypePosts(0, 1);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        session.setAttribute("right", "lostpost");
        response.setHeader("refresh", "0;URL=client.jsp");
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
        if (right.equals("viewpost")) {
            initposts = service.getTenTagsPost(0, tagsselected);
        } else if (right.equals("foundpost")) {
            initposts = service.getTenTypeTagsPost(0, 0, tagsselected);
        } else {
            initposts = service.getTenTypeTagsPost(0, 1, tagsselected);
        }

        session.setAttribute("initposts", initposts);
        request.getRequestDispatcher("/client.jsp").forward(request, response);
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
        if (right.equals("viewpost")) {
            initposts = service.getTenSearchTagsPost(0, searchword, tagsselected);
        } else if (right.equals("foundpost")) {
            initposts = service.getTenTypeSearchTagsPost(0, 0, searchword, tagsselected);
        } else {
            initposts = service.getTenTypeSearchTagsPost(0, 1, searchword, tagsselected);
        }

        session.setAttribute("initposts", initposts);
        request.getRequestDispatcher("/client.jsp").forward(request, response);
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

    private void deleteaccount (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        service.deleteaccount(user.getEmail());
        session.setAttribute("user", null);

        session.setAttribute("left", "unloginin");
        session.setAttribute("right", "viewpost");

        List<Post> initposts = service.getTenPosts(0);
        session.setAttribute("initposts", initposts);
        // init filter
        session.setAttribute("searchword", null);
        session.setAttribute("tagsselected", null);

        request.setAttribute("message", "注销成功！~");
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
