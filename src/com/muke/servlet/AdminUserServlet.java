package com.muke.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muke.pojo.User;
import com.muke.service.IUserService;
import com.muke.service.impl.UserServiceImpl;
import com.muke.util.Page;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/admin/user.json")
public class AdminUserServlet extends HttpServlet {
    IUserService iUserService = new UserServiceImpl();
    /**
	 * 
	 */
	private static final long serialVersionUID = 4122206234828080374L;

	/**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUserServlet() {
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
		} else if(action.equals("deleteUser")) {
			deleteUser(request, response);
		} else if("restoreUser".equals(action)){
			restoreUser(request, response);
		}
	}

	private void restoreUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid = request.getParameter("userid");
		if (userid == null || userid.equals("")){
			userid = "-1";
		}
		
		int res = iUserService.restoreUser(Integer.parseInt(userid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"恢复成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"恢复失败\"}");
		}
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid = request.getParameter("userid");
		if (userid == null || userid.equals("")){
			userid = "-1";
		}
		
		int res = iUserService.deleteUser(Integer.parseInt(userid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"删除成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"删除失败\"}");
		}
	}

	private void getUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String pageNum = request.getParameter("pageNum");
		
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}

		Page page = new Page();
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iUserService.searchByName(username, page);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
