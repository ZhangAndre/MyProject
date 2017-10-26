package com.muke.service;

import java.util.Date;

import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageInfo;
import com.muke.pojo.Reply;
import com.muke.util.Page;

public interface IMessageService {
	
	int addMsg(MessageInfo messageInfo);
	int replyMsg(Reply reply);
	MessageInfo getMsg(int msgid);
	Page getReply(int msgid, Page page);
	Page queryNew(Page page);					// 查询最新的
	Page queryHot(Page page);					// 查询最热的
	Page queryTheme(Page page);					// 查询最热主题
	int deleteMsg(int msgid);					// 删除
	int restoreMsg(int msgid);					// 恢复
	int wonderfulMsg(int msgid);
	int toTopMsg(int msgid);
	int updateMsg(MessageInfo messageInfo);
	Page search(MessageCriteria messageCriteria, Page page);
	long queryMsgCountByDate(Date startDate, Date endDate);	// 根据时间查发贴数量
	long queryReplyCountByDate(Date startDate, Date endDate);	// 根据时间查发贴数量
}
