package com.muke.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import com.google.gson.Gson;
import com.muke.pojo.User;
import com.muke.service.IUserService;
import com.muke.service.impl.UserServiceImpl;
import com.muke.util.FileUpload;

/**
 * Servlet implementation class UserCenterServlet
 */
@WebServlet("/user/center.json")
public class UserCenterServlet extends HttpServlet {

	IUserService iUserService = new UserServiceImpl();
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 3237993440804039238L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserCenterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("getUser")) {
			getUser(request, response);
		} else if(action.equals("update")) {
			update(request, response);
		} else if("updatePW".equals(action)){
			updatePW(request, response);
		} else if("uploadPhoto".equals(action)){
			uploadPhoto(request, response);
		}
	}

	private void uploadPhoto(HttpServletRequest request, HttpServletResponse response) throws IOException {

		FileUpload fileUpload = new FileUpload(request);
		// 设置上传目录
		fileUpload.setPath("upload");
		// 设置文件类型
		fileUpload.setTypeReg("[.]jpg|png|jpeg|gif$");
		
		// 初始化表单参数
		int res = fileUpload.initFormData();
		
		if (res == 1){
			String file = fileUpload.getParameter("file");
			
			// 获取当前用户对象
			HttpSession session = request.getSession();
			User sessionUser = (User) session.getAttribute("user");
			User user = sessionUser.clone();
			user.setPhoto(file);
			
			// 更新
			res = iUserService.update(user);
			
			if (res > 0){
				// 更新Session
				session.setAttribute("user", user);
				response.getWriter().print("{\"res\": 1, \"data\":\"头像上传成功\"}");
			}
			else{
				response.getWriter().print("{\"res\": "+ res +", \"info\":\"数据库操作失败!\"}");
			}
			
		}
		else if (res == -2){
			response.getWriter().print("{\"res\": "+ res +", \"info\":\"文件类型不允许!\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+ res +", \"info\":\"文件上传失败!\"}");
		}
	}

	private void updatePW(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		// 获取当前用户对象
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("user");
		User user = sessionUser.clone();
		
		// 旧密码不对
		if (!user.getPassword().equals(oldPassword)){
			response.getWriter().print("{\"res\": 0, \"info\":\"原密码不对,修改失败!\"}");
			return;
		}
		
		user.setPassword(newPassword);
		
		int res = iUserService.update(user);
		
		if (res == 1){
			session.removeAttribute("user");
			
			response.getWriter().print("{\"res\": "+res+", \"info\":\"修改成功, 请重新登录!\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"修改失败!\"}");
		}
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获取参数
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");
		String hobbys = request.getParameter("hobbys");
		String birthday = request.getParameter("birthday");
		String city = request.getParameter("city");
		String email = request.getParameter("email");
		String qq = request.getParameter("qq");

		// 获取当前用户对象
		HttpSession session = request.getSession();
		User sessionUser = (User) session.getAttribute("user");
		User user = sessionUser.clone();
		
		// 重设其值
		if (realname != null && realname.trim().length()>0){
			user.setRealname(realname);
		}
		if (sex != null && sex.trim().length()>0){
			user.setSex(sex);
		}
		if (hobbys != null && hobbys.trim().length()>0){
			user.setHobbys(hobbys);
		}
		if (birthday != null && birthday.trim().length()>0){
			user.setBirthday(birthday);
		}
		if (city != null && city.trim().length()>0){
			user.setCity(city);
		}
		if (email != null && email.trim().length()>0){
			user.setEmail(email);
		}
		if (qq != null && qq.trim().length()>0){
			user.setQq(qq);
		}
		
		// 更新
		int res = iUserService.update(user);
		
		if (res == 1){	// 更新成功
			// 获取更新后的数据
			user = iUserService.userLogin(user.getUsername(), user.getPassword());

			// 更新Session
			session.setAttribute("user", user);
			
			Gson gson = new Gson();
			String dataJSON = gson.toJson(user);
			
			response.getWriter().print("{\"res\": 1, \"data\":" + dataJSON + "}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"修改失败!\"}");
		}
	}

	private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 登录成功
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		Gson gson = new Gson();
		String dataJSON = gson.toJson(user);
		response.getWriter().print("{\"res\": 1, \"data\":" + dataJSON + "}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
