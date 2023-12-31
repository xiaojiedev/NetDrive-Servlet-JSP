package dev.xiaojie.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dev.xiaojie.bean.User;
import dev.xiaojie.bean.UserFile;
import dev.xiaojie.dao.UserDao;
import dev.xiaojie.dao.UserFileDao;
import dev.xiaojie.util.Const;

@WebServlet("/user/*")
public class UserServlet extends HttpServlet {

    private static final long serialVersionUID = 2146748539648197507L;
    private UserDao userDao = new UserDao();
    private UserFileDao userFileDao = new UserFileDao();

    public UserServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        User user = (User) request.getSession().getAttribute(Const.SESSION_USER);

        //根君action判断当前用户的操作
        String action = request.getParameter("action");
        //跳转到主页，所有共享
        if (action == null || action.equals("") || action.equals("index")) {
            String filename = request.getParameter("filename");
            List<UserFile> fileList;
            if (filename != null && !filename.equals("")) {
                fileList = userFileDao.findSharedFileWithName(filename);
            } else {
                fileList = userFileDao.findSharedFile();
            }
            request.setAttribute("fileList", fileList);
            request.getRequestDispatcher("/jsp/user/index.jsp").forward(request, response);

        } else if (action.equals("myshare")) {
            //跳转到 我的共享
            List<UserFile> fileList = userFileDao.findMySharedFile(user.getId());
            request.setAttribute("fileList", fileList);
            request.getRequestDispatcher("/jsp/user/myshare.jsp").forward(request, response);

        } else if (action.equals("mydisk")) {
            //跳转到 我的网盘
            List<UserFile> fileList = userFileDao.findFileListByOwnerId(user.getId());
            request.setAttribute("fileList", fileList);
            request.getRequestDispatcher("/jsp/user/mydisk.jsp").forward(request, response);

        } else if (action.equals("share")) {
            //根据file_ID分享文件
            int id = Integer.parseInt(request.getParameter("id"));
            UserFile userFile = userFileDao.findUserFileById(id);
            //判断文件的所有者,文件主人才能分享
            if (user.getId() == userFile.getOwnerId()) {
                userFile.setIsShared(1);
                userFileDao.update(userFile);
            }
            response.sendRedirect("user?action=mydisk");

        } else if (action.equals("cancelShare")) {
            //根据file_ID分享文件
            int id = Integer.parseInt(request.getParameter("id"));
            UserFile userFile = userFileDao.findUserFileById(id);
            //判断文件的所有者,文件主人才能分享
            if (user.getId() == userFile.getOwnerId()) {
                userFile.setIsShared(0);
                userFileDao.update(userFile);
            }
            response.sendRedirect("user?action=myshare");

        } else if (action.equals("delete")) {

            //将字符串数组转为int 数组
            String[] vs = request.getParameterValues("ids");
            int[] ids = strArr2intArr(vs);
            userFileDao.deleteByIds(ids);
            response.sendRedirect("user?action=mydisk");
        } else if (action.equals("edit")) {
            request.getRequestDispatcher("/jsp/user/edit.jsp").forward(request, response);
        } else if (action.equals("editSubmit")) {

            String ori_psw = request.getParameter("password");
            String new_psw = request.getParameter("password1");

            if (ori_psw.equals(user.getPassword())) {
                user.setPassword(new_psw);
                userDao.update(user);
                request.setAttribute("msgSuccess", "密码修改成功！");
                request.getRequestDispatcher("/jsp/user/edit.jsp").forward(request, response);
            } else {
                request.setAttribute("msgFail", "原密码错误！！修改失败！！");
                request.getRequestDispatcher("/jsp/user/edit.jsp").forward(request, response);
            }
        }

    }


    public int[] strArr2intArr(String[] arr) {
        if (arr == null || arr.length == 0)
            return null;
        int[] intArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            intArr[i] = Integer.parseInt(arr[i]);
        }
        return intArr;
    }
}
