package com.muke.dao.impl;

import com.muke.util.DBUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.muke.dao.IMessageDao;
import com.muke.pojo.MessageCriteria;
import com.muke.pojo.MessageInfo;
import com.muke.util.Page;

public class MessageDaoImpl implements IMessageDao {
	DBUtil dbutil = new DBUtil();
	
	@Override
	public int add(MessageInfo msg) {
		String sql = "insert into message(userid,msgtopic,msgcontents,msgtime,msgip,theid)"
				+ " values(?, ?, ?, ?, ?, ?)";
		Object[] params = {msg.getUserid(), msg.getMsgtopic(), msg.getMsgcontents(), msg.getMsgtime(), msg.getMsgip(), (msg.getTheid()>0?msg.getTheid():null)};
		
		int result = 0;
		
		try {
			result =  dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int delete(int msgid) {
		String sql = "delete from message where msgid=?";		
		Object[] params = {msgid};
		
		int result = 0;
		
		try {
			result = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public int updateState(int msgid, int state) {
		String sql = "update message set state=? where msgid=?";
		Object[] params = {state, msgid};
		
		int result = 0;
		
		try {
			result = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int update(MessageInfo msg) {
		String sql = "update message set msgtopic=?,msgcontents=?,theid=?,state=? where msgid=?";
		Object[] params = { msg.getMsgtopic(), msg.getMsgcontents(), msg.getTheid(), msg.getState(), msg.getMsgid() };
		
		int result = 0;
		
		try {
			result = dbutil.execute(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public MessageInfo get(int msgid) {
		String sql = "select a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
					+ "a.theid, c.thename,  "
					+ "a.userid, username, photo, realname, sex, city, "
					+ "d.accessCount, d.replyCount, "
					+ "max(e.replytime) as replytime "
					+ "  from "
					+ "message a "
					+ "LEFT JOIN user b ON a.userid = b.userid "
					+ "LEFT JOIN theme c ON a.theid = c.theid "
					+ "LEFT JOIN count d ON a.msgid = d.msgid "
					+ "LEFT JOIN reply e on a.msgid = e.msgid "
					+ "WHERE a.msgid = ? "
					+ "GROUP BY a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
					+ "a.theid, c.thename, "
					+ "a.userid, username, realname, sex, city, "
					+ "d.accessCount, d.replyCount "
					+ "ORDER BY d.accessCount desc";
		Object[] params = {msgid};
		
		MessageInfo messageInfo = null;
		
		try {
			messageInfo = (MessageInfo) dbutil.getObject(MessageInfo.class, sql, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return messageInfo;
	}

	@Override
	public Page query(MessageCriteria messageCriteria, Page page) {
		String sql = "select a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename,  "
				+ "a.userid, username, realname, sex, city, "
				+ "d.accessCount, d.replyCount, "
				+ "max(e.replytime) as replytime "
				+ "  from "
				+ "message a "
				+ "LEFT JOIN user b ON a.userid = b.userid "
				+ "LEFT JOIN theme c ON a.theid = c.theid "
				+ "LEFT JOIN count d ON a.msgid = d.msgid "
				+ "LEFT JOIN reply e on a.msgid = e.msgid "
				+ "WHERE 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		
		if (messageCriteria != null){
			// 根据参数情况调整条件
			int userId = messageCriteria.getUserid();
			if (userId > 0) {
				sql += " and a.userid = ? ";
				params.add(userId);
			}
			
			String username = messageCriteria.getUsername();
			if (username != null && username.trim().length() > 0){
				sql += " and b.realname like ? ";
				params.add("%"+username+"%");
			}
			
			int theid = messageCriteria.getTheid();
			if (theid > 0){
				sql += " and a.theid = ? ";
				params.add(theid);
			}
			
			int state = messageCriteria.getState();
			if (state >= -1){
				sql += " and a.state >= ? ";
				params.add(state);
			}
			
			String key = messageCriteria.getKey();
			if (key != null && key.trim().length() > 0){
				sql += " and a.msgtopic like ? ";
				params.add("%"+key+"%");
			}
		}
		
		sql += " GROUP BY a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename, "
				+ "a.userid, username, realname, sex, city, "
				+ "d.accessCount, d.replyCount ";
				
		
		// 查询条件
		switch (messageCriteria.getOrderRule()) {
		case ORDER_BY_ACCESS_COUNT:
			sql += "ORDER BY d.accessCount ";
			break;
		case ORDER_BY_MSG_TIME:
			sql += "ORDER BY msgtime ";
			break;
		default:
			break;
		}
		
		// 升降序
		if (!messageCriteria.isAsc()){
			sql += "DESC";
		}
		
		Page resPage = null;
		
		resPage = dbutil.getQueryPage(MessageInfo.class, sql, params.toArray(), page);
		
		return resPage;
	}

	@Override
	public Page queryNew(Page page) {
		String sql = "select a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename,  "
				+ "a.userid, username, realname, sex, city, "
				+ "d.accessCount, d.replyCount, "
				+ "max(e.replytime) as replytime "
				+ "  from "
				+ "message a "
				+ "LEFT JOIN user b ON a.userid = b.userid "
				+ "LEFT JOIN theme c ON a.theid = c.theid "
				+ "LEFT JOIN count d ON a.msgid = d.msgid "
				+ "LEFT JOIN reply e on a.msgid = e.msgid "
				+ "WHERE a.state >= 0 "
				+ "GROUP BY a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename, "
				+ "a.userid, username, realname, sex, city, "
				+ "d.accessCount, d.replyCount "
				+ "ORDER BY a.state desc, a.msgtime desc ";
		
		Page resPage = null;

		try {
			resPage = dbutil.getQueryPage(MessageInfo.class, sql, null, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resPage;
	}

	@Override
	public Page queryHot(Page page) {
		String sql = "select a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename,  "
				+ "a.userid, username, realname,sex, city, "
				+ "d.accessCount, d.replyCount, "
				+ "max(e.replytime) as replytime "
				+ "  from "
				+ "message a "
				+ "LEFT JOIN user b ON a.userid = b.userid "
				+ "LEFT JOIN theme c ON a.theid = c.theid "
				+ "LEFT JOIN count d ON a.msgid = d.msgid "
				+ "LEFT JOIN reply e on a.msgid = e.msgid "
				+ "WHERE a.state >= 0 "
				+ "GROUP BY a.msgid, msgtopic, msgcontents, msgtime, msgip, a.state, "
				+ "a.theid, c.thename, "
				+ "a.userid, username, realname, sex, city, "
				+ "d.accessCount, d.replyCount "
				+ "ORDER BY d.replyCount desc ";
		
		Page resPage = null;

		try {
			resPage = dbutil.getQueryPage(MessageInfo.class, sql, null, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resPage;
	}

	@Override
	public Page queryTheme(Page page) {
		String sql = "SELECT b.msgid, msgtopic, msgcontents, msgtime, msgip, b.state, "
						+ "b.theid, a.thename, "
						+ "b.userid, username, realname, sex, city, "
						+ "d.accessCount, d.replyCount, "
						+ "max(e.replytime) as replytime "
						+ "FROM theme a "
						+ "LEFT JOIN message b ON a.theid = b.theid "
						+ "LEFT JOIN user c ON b.userid = c.userid "
						+ "LEFT JOIN count d ON b.msgid = d.msgid "
						+ "LEFT JOIN reply e on b.msgid = e.msgid "
						+ "WHERE b.state >= 0 AND b.msgtime IN "
						+ "( SELECT MAX(msgtime) "
						+ "FROM "
						+ "message f "
						+ "WHERE "
						+ "f.state >= 0 AND "
						+ "b.theid = f.theid) "
						+ "GROUP BY "
						+ "b.msgid, msgtopic, msgcontents, msgtime, msgip, b.state, "
						+ "b.theid, a.thename, a.count, "
						+ "b.userid, username, realname, sex, city, "
						+ "d.accessCount, d.replyCount "
						+ "ORDER BY a.count DESC ";
		
		Page resPage = null;

		try {
			resPage = dbutil.getQueryPage(MessageInfo.class, sql, null, page);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return resPage;
	}

	@Override
	public long queryCountByDate(Date startDate, Date endDate) {
		String sql = "SELECT COUNT(*) AS count FROM message WHERE msgtime > ? AND msgtime < ?";
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
