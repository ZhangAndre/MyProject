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
import com.muke.util.HTMLReplace;
import com.muke.util.IPUtil;
import com.muke.util.Page;
import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageCriteria.OrderRuleEnum;
import com.muke.pojo.MessageInfo;
import com.muke.pojo.Reply;
import com.muke.pojo.ReplyInfo;
import com.muke.pojo.User;
import com.muke.service.IMessageService;
import com.muke.service.impl.MessageServiceImpl;

/**
 * Servlet implementation class MessageServlet
 */
@WebServlet("/user/message.json")
public class UserMessageServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2839628403990091956L;
	
	IMessageService iMessageService = new MessageServiceImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action");
		if(action.equals("add")) {
			add(request, response);//添加主题贴
		} else if(action.equals("replyMsg")){
			replyMsg(request, response);	// 回帖
		} else if(action.equals("getMyMsg")){
			getMyMsg(request, response);
		}
	}

	private void getMyMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pageNum = request.getParameter("pageNum");
		Page page = new Page();
		if (pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		page.setCurPage(Integer.parseInt(pageNum));
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		MessageCriteria messageCriteria = new MessageCriteria();
		messageCriteria.setUserid(user.getUserid());
		messageCriteria.setOrderRule(OrderRuleEnum.ORDER_BY_MSG_TIME);
		
		Page resPage = iMessageService.search(messageCriteria, page);
		
		Gson gson = new GsonBuilder().setDateFormat("yy-MM-dd").create();
		String dataJSON = gson.toJson(resPage);
		System.out.println(dataJSON);
		
		response.getWriter().print("{\"res\": 1, \"data\":"+dataJSON+"}");
	}

	private void replyMsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgId = request.getParameter("msgId");
		
		if (msgId == null || msgId.equals("")){
			return;
		}
		
		String replyContent = request.getParameter("replyContent");
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		replyContent = HTMLReplace.replace(replyContent);
		
		Reply reply = new Reply();
		reply.setUserid(user.getUserid());
		reply.setMsgid(Integer.parseInt(msgId));
		reply.setReplycontents(replyContent);
		reply.setReplyip(IPUtil.getIP(request));
		
		int res = iMessageService.replyMsg(reply);
		
		response.getWriter().print("{\"res\":" + res + "}");
	}

	private void add(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String msgtopic = request.getParameter("msgtopic");
		String theid = request.getParameter("theid");
		String msgcontents = request.getParameter("msgcontents");
		
		if (theid == null && theid.trim().length() <= 0){
			theid = "-1";
		}
		
		System.out.println(msgtopic + theid + msgcontents);
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");	

		msgcontents = HTMLReplace.replace(msgcontents);
		
		MessageInfo messageInfo = new MessageInfo();
		messageInfo.setUserid(user.getUserid());
		messageInfo.setMsgtopic(msgtopic);
		messageInfo.setTheid(Integer.parseInt(theid));
		messageInfo.setMsgcontents(msgcontents);
		messageInfo.setMsgip(IPUtil.getIP(request));
		
		int res = iMessageService.addMsg(messageInfo);
		
		response.getWriter().print("{\"res\":\"" + res + "\"}");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
