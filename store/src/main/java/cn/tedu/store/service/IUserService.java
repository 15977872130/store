package cn.tedu.store.service;

import cn.tedu.store.entity.User;

/**
 * 处理用户数据的业务层接口
 */
public interface IUserService {
	
	/**
	 * 用户注册
	 * @param user 新用户数据
	 */
	void reg(User user);
	
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 成功登录的用户的数据
	 */
	User login(String username, String password);
	
	/**
	 * 修改密码
	 * @param uid 用户id
	 * @param username 用户名
	 * @param oldPassword 原密码
	 * @param newPassword 新密码
	 */
	void changePassword(Integer uid, String username,
			String oldPassword, String newPassword);
	
	/**
	 * 修改头像
	 * @param uid 用户id
	 * @param username 用户名
	 * @param avatar 新头像的路径
	 */
	void changeAvatar(Integer uid, String username, 
			String avatar);
	
	/**
	 * 修改个人资料
	 * @param uid 用户id
	 * @param username 用户名
	 * @param user 新的个人资料
	 */
	void changeInfo(Integer uid, String username, User user);
	
	/**
	 * 根据用户id查询用户数据
	 * @param uid 用户id
	 * @return 匹配的用户数据，如果没有匹配的数据，则返回null
	 */
	User getByUid(Integer uid);
	
}





