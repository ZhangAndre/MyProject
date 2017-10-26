package com.muke.dao;

import java.util.Date;
import java.util.List;

import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageInfo;
import com.muke.util.Page;

public interface IMessageDao {
	int add(MessageInfo msg);			// 添加一条
	int delete(int msgid);				// 删除一条
	int update(MessageInfo msg);		// 更新一条
	int updateState(int msgid, int state);		// 更新状态
	MessageInfo get(int msgid);			// 获取指定帖
	Page query(MessageCriteria messageCriteria, Page page);		// 复合条件查询
	Page queryNew(Page page);					// 查询最新的
	Page queryHot(Page page);					// 查询最热的
	Page queryTheme(Page page);					// 查询最热主题
	long queryCountByDate(Date startDate, Date endDate);	// 根据时间查数量
}
