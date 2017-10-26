package com.muke.pojo;

import java.util.Date;

/**
 * 
 * @Description: 帖子总体信息（联查）
 * @author:Zheng Yanbo
 * @time:2017年7月24日 下午3:24:43
 *
 */
public class MessageInfo {

	private int msgid;				// 帖子id
	private String msgtopic;		// 帖子标题
	private String msgcontents;		// 内容
	private Date msgtime;			// 时间
	private String msgip;			// 创建主题帖的人的IP
	private int state;				// 帖子状态
	
	private int theid;				// 主题ID
	private String thename;			// 主题名
	
	private int userid;				// 用户ID
	private String username;		// 用户名
	private String realname;		// 用户昵称
	private String sex;				// 性别
	private String city;			// 城市
	private String photo;			// 头像
	
	private int accessCount;		// 浏览量
	private int replyCount;			// 回复量
	private Date replytime;			// 最新回复时间
	
	public int getMsgid() {
		return msgid;
	}
	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}
	public String getMsgtopic() {
		return msgtopic;
	}
	public void setMsgtopic(String msgtopic) {
		this.msgtopic = msgtopic;
	}
	public String getMsgcontents() {
		return msgcontents;
	}
	public void setMsgcontents(String msgcontents) {
		this.msgcontents = msgcontents;
	}
	public Date getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(Date msgtime) {
		this.msgtime = msgtime;
	}
	public String getMsgip() {
		return msgip;
	}
	public void setMsgip(String msgip) {
		this.msgip = msgip;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getTheid() {
		return theid;
	}
	public void setTheid(int theid) {
		this.theid = theid;
	}
	public String getThename() {
		return thename;
	}
	public void setThename(String thename) {
		this.thename = thename;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getAccessCount() {
		return accessCount;
	}
	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}
	public int getReplyCount() {
		return replyCount;
	}
	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}
	public Date getReplytime() {
		return replytime;
	}
	public void setReplytime(Date replytime) {
		this.replytime = replytime;
	}
	
	@Override
	public String toString() {
		return "MessageInfo [msgid=" + msgid + ", msgtopic=" + msgtopic + ", msgcontents=" + msgcontents + ", msgtime="
				+ msgtime + ", msgip=" + msgip + ", state=" + state + ", theid=" + theid + ", thename=" + thename
				+ ", userid=" + userid + ", username=" + username + ", realname=" + realname + ", sex=" + sex
				+ ", city=" + city + ", accessCount=" + accessCount + ", replyCount=" + replyCount + ", replytime="
				+ replytime + "]";
	}	
}
