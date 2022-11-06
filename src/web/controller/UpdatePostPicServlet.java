package web.controller;

import domain.Post;
import domain.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ClientService;
import service.impl.ClientServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UpdatePostPicServlet", value = "/UpdatePostPicServlet")
public class UpdatePostPicServlet extends HttpServlet {
    private final ClientService service = new ClientServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload fileUpload = new ServletFileUpload(factory);
            fileUpload.setHeaderEncoding("UTF-8");
            try {
                List<FileItem> list = fileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) {
                        System.out.println("上传的name值：" + fileItem.getFieldName());
                        System.out.println("对应的value值：" + fileItem.getString("utf-8"));
                    } else {
                        System.out.println("上传的文件名：" + fileItem.getName());
                        String curTime = new SimpleDateFormat("yyyyMMddHHmmssSSS") .format(new Date() );
                        String fileName = curTime + new File(fileItem.getName()).getName();
                        File file = new File("F:\\LAF\\image\\" + fileName);
                        System.out.println("保存路径：" + file);
                        fileItem.write(file);

                        //update post
                        HttpSession session = request.getSession();
                        Post nowpost = (Post)session.getAttribute("nowpost");
                        service.addPictureToPost(nowpost.getPost_id(), "/myimage/" + fileName);
                        nowpost = service.findPostById(Integer.parseInt(nowpost.getPost_id()));
                        session.setAttribute("nowpost", nowpost);
                    }
                }
            } catch (Exception e) {
                request.setAttribute("message", "文件上传失败！");
                request.getRequestDispatcher("/between.jsp").forward(request, response);
                return;
            }
            request.setAttribute("message", "文件上传成功！");
        } else {
            // 如果不是则停止
            request.setAttribute("message", "非文件上传！");
        }
        request.getRequestDispatcher("/between.jsp").forward(request, response);
    }
}
