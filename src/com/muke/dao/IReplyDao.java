package com.muke.dao;

import java.util.Date;

import com.muke.pojo.Reply;
import com.muke.util.Page;

public interface IReplyDao {
	int replyMsg(Reply reply);
	Page queryBymsgid(int msgid, Page page);		// 根据ID分页查询
	long queryCountByDate(Date startDate, Date endDate);	// 根据时间查数量
}
