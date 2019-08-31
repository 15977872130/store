package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.entity.Address;

/**
 * 处理收货地址数据的业务层接口
 */
public interface IAddressService {
	
	/**
	 * 每个用户最多允许创建的收货地址数据的数量
	 */
	int MAX_COUNT = 20;

	/**
	 * 增加新的收货地址数据
	 * @param address 客户端提交的收货地址数据
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 */
	void addnew(Address address, Integer uid, String username);
	
	/**
	 * 查询某用户的收货地址列表
	 * @param uid 用户id
	 * @return 该用户的收货地址列表
	 */
	List<Address> getByUid(Integer uid);
	
	/**
	 * 将指定的收货地址设置为默认
	 * @param aid 收货地址数据的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 */
	void setDefault(Integer aid, Integer uid, String username);
	
	/**
	 * 删除收货地址数据
	 * @param aid 被删除的收货地址数据的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 */
	void delete(Integer aid, Integer uid, String username);
	
	/**
	 * 根据收货地址数据的id查询详情
	 * @param aid 收货地址数据的id
	 * @return 匹配的收货地址详情，如果没有匹配的数据，则抛出AddressNotFoundException
	 */
	Address getByAid(Integer aid);
	
}









