package com.muke.dao;

import com.muke.pojo.Admin;

/**
 * 
 * @Description:管理员用户数据访问接口
 * @author:Zhao JiaQiang
 * @time:2017年7月18日 下午5:21:41
 *
 */
public interface IAdminDao {
	// 管理员登录
	Admin login(String name, String pwd);

	// 管理员修改
	int update(String name ,String pwd);

}
