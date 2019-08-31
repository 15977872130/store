package cn.tedu.store.service;

import java.util.List;

import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据的业务层接口
 */
public interface ICartService {

	/**
	 * 将商品添加到购物车
	 * @param uid 当前登录的用户的id
	 * @param pid 商品的id
	 * @param num 添加到购物车中的数量
	 * @param username 操作执行人
	 */
	void addToCart(Integer uid, Integer pid, Integer num, String username);
	
	/**
	 * 查询某用户的购物车数据列表
	 * @param uid 用户的id
	 * @return 该用户的购物车数据列表
	 */
	List<CartVO> getByUid(Integer uid);
	
	/**
	 * 将购物车中指定数据的商品数量增加1
	 * @param cid 购物车数据的id
	 * @param uid 当前登录的用户的id
	 * @param username 当前登录的用户名
	 * @return 新的数量
	 */
	Integer addNum(Integer cid, Integer uid, String username);
	
	/**
	 * 根据若干个购物车数据id查询数据
	 * @param cids 若干个购物车数据id
	 * @param uid 当前登录的用户的id
	 * @return 匹配的购物车数据的列表
	 */
	List<CartVO> getByCids(Integer[] cids, Integer uid);
	
}







