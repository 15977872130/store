package cn.tedu.store.service;

import cn.tedu.store.entity.Order;

/**
 * 处理订单数据和订单商品数据的业务层接口
 */
public interface IOrderService {

	/**
	 * 创建订单
	 * @param aid 收货地址的数据id
	 * @param cids 即将购买的购物车数据id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 * @return 新创建的订单数据
	 */
	Order createOrder(Integer aid, Integer[] cids, 
			Integer uid, String username);

}




