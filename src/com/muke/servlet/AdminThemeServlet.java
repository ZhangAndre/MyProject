package com.muke.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muke.pojo.Theme;
import com.muke.service.IThemeService;
import com.muke.service.impl.ThemeServiceImpl;
import com.muke.util.Page;

/**
 * Servlet implementation class ThemeServlet
 */
@WebServlet("/admin/theme.json")
public class AdminThemeServlet extends HttpServlet {
       
	/**
	 * 
	 */
	private static final long serialVersionUID = 8842598432643138669L;
	
	IThemeService iThemeService = new ThemeServiceImpl();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public AdminThemeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals("add")) {
			add(request, response);
		} else if(action.equals("delete")) {
			delete(request, response);
		} else if(action.equals("update")) {
			update(request, response);
		}else if(action.equals("getAll")) {
			getAll(request, response);
		}else if(action.equals("searchTheme")) {
			searchTheme(request, response);
		}
	}

	private void searchTheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String key = request.getParameter("key");
		String pageNum = request.getParameter("pageNum");
		
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}

		Page page = new Page();
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iThemeService.query(key, page);
		
		Gson gson = new Gson();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
		
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List list = iThemeService.getAll();
		
		Gson gson = new Gson();
		String dataJSON = gson.toJson(list);
		
		response.getWriter().print(dataJSON);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String theid = request.getParameter("theid");
		if (theid == null || theid.equals("")){
			theid = "-1";
		}
		
		int res = iThemeService.delete(Integer.parseInt(theid));

		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"删除成功11\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"删除失败\"}");
		}
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String thename = request.getParameter("thename");
		if (thename == null || thename.equals("")){
			response.getWriter().print("{\"res\": -1, \"info\":\"添加失败\"}");
			return;
		}
		
		Theme theme = new Theme();
		theme.setThename(thename);
		int res = iThemeService.add(theme);

		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"添加成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"添加成功\"}");
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
