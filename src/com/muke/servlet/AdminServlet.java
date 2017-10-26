package com.muke.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.muke.pojo.Admin;
import com.muke.service.IAdminService;
import com.muke.service.impl.AdminServiceImpl;

/**
 * 
 * @Description:管理员用户Servlet
 * @author:Zhao JiaQiang
 * @time:2017年7月18日 下午5:23:15
 *
 */
@WebServlet("/admin.json")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	IAdminService iAdminService = new AdminServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		switch (action) {
		case "login":
			login(request, response);
			break;
		case "updatePW":
			updatePW(request, response);
			break;
		case "logout":
			logout(request, response);
			break;
		default:
			break;
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		
		session.removeAttribute("admin");

		response.getWriter().print("{\"res\":1, \"info\":\"退出成功！\"}");
	}

	private void updatePW(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		
		HttpSession session =  ((HttpServletRequest)request).getSession();
		Admin sessionAdmin = (Admin)session.getAttribute("admin");
		Admin admin = sessionAdmin.clone();
		
		// 旧密码不对
		if (!admin.getPwd().equals(oldPassword)){
			response.getWriter().print("{\"res\": 0, \"info\":\"原密码不对,修改失败!\"}");
			return;
		}
		
		int res=iAdminService.update(admin.getName(),newPassword);
		
		if (res == 1) {
			session.removeAttribute("admin");

			response.getWriter().print("{\"res\": "+res+", \"info\":\"修改成功, 请重新登录!\"}");
		} else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"修改失败!\"}");
		}
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || username.trim().length() < 6 || username.trim().length() > 16 || password == null
				|| password.trim().length() < 6 || password.trim().length() > 16) {
			// 信息有问题重新登录
			response.getWriter().print("{\"res\":-1, \"info\":\"登录信息填写有误，请不要带有非法字符！\"}");
		}
				
		Admin admin=iAdminService.login(username,password);
		
		if (admin == null){
			// 登录失败 用户名或密码错误
			response.getWriter().print("{\"res\":-1, \"info\":\"用户名或密码错误，请重新输入！\"}");
		}
		else{
			// 登录成功
			HttpSession session = request.getSession(); 
			session.setAttribute("admin", admin);
			
			Gson gson = new Gson();
			String dataJSON = gson.toJson(admin);
			//传值，输出{"res":1, "data":"dataJSON}
			response.getWriter().print("{\"res\":1, \"data\":" + dataJSON + "}");
		}
	}

}
