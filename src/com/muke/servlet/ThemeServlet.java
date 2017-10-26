package com.muke.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.muke.service.IThemeService;
import com.muke.service.impl.ThemeServiceImpl;

/**
 * Servlet implementation class ThemeServlet
 */
@WebServlet("/theme.json")
public class ThemeServlet extends HttpServlet {
       
	/**
	 * 
	 */
	private static final long serialVersionUID = 8842598432643138669L;
	
	IThemeService iThemeService = new ThemeServiceImpl();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public ThemeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		
		if(action.equals("getAll")) {
			getAll(request, response);
		}
	}

	private void getAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List list = iThemeService.getAll();
		
		Gson gson = new Gson();
		String dataJSON = gson.toJson(list);
		
		response.getWriter().print(dataJSON);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
