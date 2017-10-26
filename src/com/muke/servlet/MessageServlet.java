package com.muke.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muke.util.Page;
import com.muke.pojo.MessageInfo;
import com.muke.pojo.Reply;
import com.muke.pojo.ReplyInfo;
import com.muke.pojo.User;
import com.muke.service.IMessageService;
import com.muke.service.impl.MessageServiceImpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/message.json")
public class MessageServlet extends HttpServlet {
	       
    /**
	 * 
	 */
	private static final long serialVersionUID = -721972566003709121L;
	
	IMessageService iMessageService = new MessageServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action");
		if(action.equals("getMsg")) {
			getMsg(request, response);//查看主题帖及回复
		} else if(action.equals("getReply")) {
			getReply(request, response);//查询需要展示的内容
		} else if(action.equals("topNew")){
			topNew(request, response);	// 查询最新5贴
		} else if(action.equals("topHot")){
			topHot(request, response);	// 查询最热5帖
		} else if(action.equals("topTheme")){
			topTheme(request, response);	// 查询最热5主题的，最新帖
		}
	}

	private void topTheme(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String pageNum = request.getParameter("pageNum");
		Page page = new Page();
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iMessageService.queryTheme(page);
		
		Gson gson = new GsonBuilder().setDateFormat("M/d").create();
		String dataJSON = gson.toJson(resPage);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
	}

	private void topHot(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String pageNum = request.getParameter("pageNum");
		Page page = new Page();
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iMessageService.queryHot(page);
		
		Gson gson = new GsonBuilder().setDateFormat("M/d").create();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
		
	}

	private void topNew(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String pageNum = request.getParameter("pageNum");
		Page page = new Page();
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iMessageService.queryNew(page);
		
		Gson gson = new GsonBuilder().setDateFormat("M/d").create();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
		
	}

	private void getReply(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgId = request.getParameter("msgId");
		
		if (msgId == null || msgId.equals("")){
			msgId = "1";
		}
		
		String pageNum = request.getParameter("pageNum");
		Page page = new Page();
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		page.setCurPage(Integer.parseInt(pageNum));
		
		Page resPage = iMessageService.getReply(Integer.parseInt(msgId), page);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
	}

	private void getMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		String msgId = request.getParameter("msgId");
		
		if (msgId == null || msgId.equals("")){
			msgId = "1";
		}
		
		MessageInfo messageInfo = iMessageService.getMsg(Integer.parseInt(msgId));
				
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String dataJSON = gson.toJson(messageInfo);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
