package com.muke.service.impl;

import java.util.Date;
import java.util.List;

import com.muke.dao.ICountDao;
import com.muke.dao.IMessageDao;
import com.muke.dao.IReplyDao;
import com.muke.dao.impl.CountDaoImpl;
import com.muke.dao.impl.MessageDaoImpl;
import com.muke.dao.impl.ReplyDaoImpl;
import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageInfo;
import com.muke.pojo.Reply;
import com.muke.service.IMessageService;
import com.muke.util.Page;

public class MessageServiceImpl implements IMessageService {

	IMessageDao iMessageDao = new MessageDaoImpl();
	IReplyDao iReplyDao = new ReplyDaoImpl();
	ICountDao iCountDao = new CountDaoImpl();
	
	@Override
	public int addMsg(MessageInfo messageInfo) {
		return iMessageDao.add(messageInfo);
	}

	@Override
	public int replyMsg(Reply reply) {
		// TODO Auto-generated method stub
		return iReplyDao.replyMsg(reply);
	}

	@Override
	public MessageInfo getMsg(int msgid) {
		// 获取数据
		MessageInfo msg = iMessageDao.get(msgid);
		
		// 更新浏览记录
		iCountDao.updateAccessCount(msgid);
		
		return msg;
	}

	@Override
	public Page getReply(int msgid, Page page) {
		return iReplyDao.queryBymsgid(msgid, page);
	}

	@Override
	public Page queryNew(Page page) {
		return iMessageDao.queryNew(page);
	}

	@Override
	public Page queryHot(Page page) {
		return iMessageDao.queryHot(page);
	}

	@Override
	public Page queryTheme(Page page) {
		return iMessageDao.queryTheme(page);
	}

	@Override
	public int deleteMsg(int msgid) {
		return iMessageDao.updateState(msgid, -1);
	}
	@Override
	public int restoreMsg(int msgid) {
		return iMessageDao.updateState(msgid, 0);
	}

	@Override
	public int wonderfulMsg(int msgid) {
		return iMessageDao.updateState(msgid, 10);
	}

	@Override
	public int toTopMsg(int msgid) {
		return iMessageDao.updateState(msgid, 5);
	}
	@Override
	public int updateMsg(MessageInfo messageInfo) {
		return 0;
	}

	@Override
	public Page search(MessageCriteria messageCriteria, Page page) {
		return iMessageDao.query(messageCriteria, page);
	}

	@Override
	public long queryMsgCountByDate(Date startDate, Date endDate) {
		return iMessageDao.queryCountByDate(startDate, endDate);
	}

	@Override
	public long queryReplyCountByDate(Date startDate, Date endDate) {
		return iReplyDao.queryCountByDate(startDate, endDate);
	}

}
