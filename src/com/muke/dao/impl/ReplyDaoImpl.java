package com.muke.dao.impl;

import java.util.Date;
import java.util.Map;

import com.muke.dao.IReplyDao;
import com.muke.pojo.Reply;
import com.muke.pojo.ReplyInfo;
import com.muke.util.DBUtil;
import com.muke.util.Page;

public class ReplyDaoImpl implements IReplyDao {
	DBUtil dbutil = new DBUtil();

	@Override
	public int replyMsg(Reply reply) {
		String sql = "INSERT INTO reply (msgid, userid, replycontents, replyip) VALUES "
				+ "(?, ?, ?, ?)";
		
		Object[] params = {reply.getMsgid(), reply.getUserid(), reply.getReplycontents(), reply.getReplyip()};

		int res = -1;
		try {
			res = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	@Override
	public Page queryBymsgid(int msgid, Page page) {
		String sql = "SELECT replyid, msgid, replycontents, replytime, replyip, "
					+ "a.userid, username, photo, realname, sex, city "
					+ "FROM reply a, user b "
					+ "WHERE a.userid = b.userid "
					+ "AND msgid = ? "
					+ "ORDER BY replytime";
		
		Object[] params = {msgid};
		
		Page resPage = null;
		
		resPage = dbutil.getQueryPage(ReplyInfo.class, sql, params, page);
		
		return resPage;
	}

	@Override
	public long queryCountByDate(Date startDate, Date endDate) {
		String sql = "SELECT COUNT(*) AS count FROM reply WHERE replytime > ? AND replytime < ?";
		Object[] params = {startDate, endDate};
		Map map = null;
		try {
			map = dbutil.getObject(sql, params);
			long count = (Long) map.get("count");
			
			return count;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
