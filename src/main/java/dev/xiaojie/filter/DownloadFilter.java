package dev.xiaojie.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import dev.xiaojie.bean.User;
import dev.xiaojie.bean.UserFile;
import dev.xiaojie.dao.UserFileDao;
import dev.xiaojie.util.Const;

@WebFilter("/download/*")
public class DownloadFilter implements Filter{

	UserFileDao userFileDao = new UserFileDao();
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		User user = (User)((HttpServletRequest)request).getSession().getAttribute(Const.SESSION_USER);
		int id = Integer.parseInt(request.getParameter("id"));
		UserFile userFile = userFileDao.findUserFileById(id);
		
		//如果是 管理员，文件所有者，共享文件 -> 可以下载 ，放行
		if(user.getIsAdmin() == 1||userFile.getOwnerId() == user.getId()||userFile.getIsShared() == 1){
			request.setAttribute("userFile", userFile);
			chain.doFilter(request, response);
		}
		else{
			response.setContentType("text/html; charset=utf8");
			response.getWriter().print("无下载权限！");
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

}
