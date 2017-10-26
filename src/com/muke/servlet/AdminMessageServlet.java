package com.muke.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muke.util.DateUtil;
import com.muke.util.Page;
import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageInfo;
import com.muke.pojo.Reply;
import com.muke.pojo.ReplyInfo;
import com.muke.pojo.User;
import com.muke.pojo.MessageCriteria.OrderRuleEnum;
import com.muke.service.IMessageService;
import com.muke.service.impl.MessageServiceImpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/admin/message.json")
public class AdminMessageServlet extends HttpServlet {
	       
    /**
	 * 
	 */
	private static final long serialVersionUID = -6329581478430273613L;
	
	IMessageService iMessageService = new MessageServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action");
		if(action.equals("searchMsg")) {
			searchMsg(request, response);
		} else if(action.equals("getMsgCount")) {
			getMsgCount(request, response);
		} else if(action.equals("getReplyCount")) {
			getReplyCount(request, response);
		} else if(action.equals("deleteMsg")){
			deleteMsg(request, response);
		} else if(action.equals("restoreMsg")){
			restoreMsg(request, response);
		} else if(action.equals("toTop")){
			toTop(request, response);
		} else if(action.equals("wonderful")){
			wonderful(request, response);
		}
	}

	private void wonderful(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgid = request.getParameter("msgid");
		if (msgid == null || msgid.equals("")){
			msgid = "-1";
		}
		
		int res = iMessageService.wonderfulMsg(Integer.parseInt(msgid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"加精成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"加精失败\"}");
		}
	}

	private void toTop(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgid = request.getParameter("msgid");
		if (msgid == null || msgid.equals("")){
			msgid = "-1";
		}
		
		int res = iMessageService.toTopMsg(Integer.parseInt(msgid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"置顶成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"置顶失败\"}");
		}
	}

	private void searchMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String key = request.getParameter("key");
		String username = request.getParameter("username");
		String theid = request.getParameter("theid");
		String pageNum = request.getParameter("pageNum");
		
		if (theid == null || theid.equals("")){
			theid = "-1";
		}
		
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}

		Page page = new Page();
		page.setCurPage(Integer.parseInt(pageNum));
		
		MessageCriteria messageCriteria = new MessageCriteria();
		messageCriteria.setKey(key);
		messageCriteria.setUsername(username);
		messageCriteria.setTheid(Integer.parseInt(theid));
		messageCriteria.setOrderRule(OrderRuleEnum.ORDER_BY_MSG_TIME);
		
		Page resPage = iMessageService.search(messageCriteria, page);
		
		Gson gson = new GsonBuilder().setDateFormat("MM-dd HH:mm").create();
		String dataJSON = gson.toJson(resPage);
		//System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
	}

	private void restoreMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgid = request.getParameter("msgid");
		if (msgid == null || msgid.equals("")){
			msgid = "-1";
		}
		
		int res = iMessageService.restoreMsg(Integer.parseInt(msgid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"恢复成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"恢复失败\"}");
		}
	}

	private void deleteMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String msgid = request.getParameter("msgid");
		if (msgid == null || msgid.equals("")){
			msgid = "-1";
		}
		
		int res = iMessageService.deleteMsg(Integer.parseInt(msgid));
		
		if (res == 1){
			response.getWriter().print("{\"res\": 1, \"info\":\"删除成功\"}");
		}
		else {
			response.getWriter().print("{\"res\": "+res+", \"info\":\"删除失败\"}");
		}
	}

	private void getReplyCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date date = new Date();
		
		// 本日回复量
		long today = iMessageService.queryReplyCountByDate(DateUtil.getToday(date), DateUtil.getTomorrow(date));
		long week = iMessageService.queryReplyCountByDate(DateUtil.getWeekAgo(date), DateUtil.getTomorrow(date));
		long month = iMessageService.queryReplyCountByDate(DateUtil.getMonthAgo(date), DateUtil.getTomorrow(date));
		
		//		
		response.getWriter().print("{\"res\": 1, \"data\": {\"today\": "+today+", \"week\": "+week+", \"month\": "+month+"}}");	
	}

	private void getMsgCount(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date date = new Date();
		
		// 本日发贴量
		long today = iMessageService.queryMsgCountByDate(DateUtil.getToday(date), DateUtil.getTomorrow(date));
		long week = iMessageService.queryMsgCountByDate(DateUtil.getWeekAgo(date), DateUtil.getTomorrow(date));
		long month = iMessageService.queryMsgCountByDate(DateUtil.getMonthAgo(date), DateUtil.getTomorrow(date));
		
		//		
		response.getWriter().print("{\"res\": 1, \"data\": {\"today\": "+today+", \"week\": "+week+", \"month\": "+month+"}}");	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
