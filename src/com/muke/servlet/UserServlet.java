package com.muke.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.muke.pojo.User;
import com.muke.service.IUserService;
import com.muke.service.impl.UserServiceImpl;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user.json")
public class UserServlet extends HttpServlet {
    IUserService iUserService = new UserServiceImpl();
    /**
	 * 
	 */
	private static final long serialVersionUID = 4122206234828080374L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("login")) {
			login(request, response);//登录
		} else if(action.equals("register")) {
			register(request, response);//查看主题帖及回复
		} else if("logout".equals(action)){
			logout(request, response);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession(); 
		session.removeAttribute("user");
		
		response.getWriter().print("{\"res\": 1, \"info\":\"退出成功！\"}");
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 参数
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String realname = request.getParameter("realname");
		String sex = request.getParameter("sex");

		String hobbys = request.getParameter("hobbys");
		String birthday = request.getParameter("birthday");
		String city = request.getParameter("city");
		String email = request.getParameter("email");
		String qq = request.getParameter("qq");
		
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setRealname(realname);
		user.setSex(sex);
		
		user.setHobbys(hobbys);
		user.setBirthday(birthday);
		user.setCity(city);
		user.setEmail(email);
		user.setQq(qq);
		
		int res = iUserService.userRegister(user);
		
		if (res == 1){
			// 自动登录
			user = iUserService.userLogin(username, password);
			
			// 登录成功
			HttpSession session = request.getSession();
			session.setAttribute("user", user);

			Gson gson = new Gson();
			String dataJSON = gson.toJson(user);
			response.getWriter().print("{\"res\": 1, \"data\":" + dataJSON + "}");
		}
		else if (res == -1){
			response.getWriter().print("{\"res\": " + res + ", \"info\":\"用户名已存在，注册失败\"}");
		}
		else {
			response.getWriter().print("{\"res\": " + res + ", \"info\":\"注册失败\"}");
		}
		
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username == null || username.trim().length() < 6 || username.trim().length() > 16 || password == null
				|| password.trim().length() < 6 || password.trim().length() > 16) {
			// 信息有问题重新登录
			response.getWriter().print("{\"res\": -1, \"info\":\"登录信息填写有误，请不要带有非法字符！\"}");
		}
		
		User user = iUserService.userLogin(username, password);
		
		if (user == null){
			// 登录失败 用户名或密码错误
			response.getWriter().print("{\"res\": -1, \"info\":\"用户名或密码错误，请重新输入！\"}");
		}
		else if(user.getState() == -1){
			// 登录失败 帐号被封
			response.getWriter().print("{\"res\": -1, \"info\":\"你的账号已被禁用！\"}");
		}
		else{
			// 用户登录重复判断
			
			// 登录成功
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			
			Gson gson = new Gson();		// 创建GSON对象
			String dataJSON = gson.toJson(user);	// 将user对象转换为JSON字符串
			response.getWriter().print("{\"res\": 1, \"data\":" + dataJSON + "}");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
