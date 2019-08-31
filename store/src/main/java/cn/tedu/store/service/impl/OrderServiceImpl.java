package cn.tedu.store.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.store.entity.Address;
import cn.tedu.store.entity.Order;
import cn.tedu.store.entity.OrderItem;
import cn.tedu.store.mapper.OrderMapper;
import cn.tedu.store.service.IAddressService;
import cn.tedu.store.service.ICartService;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.service.ex.AccessDeniedException;
import cn.tedu.store.service.ex.InsertException;
import cn.tedu.store.vo.CartVO;

/**
 * 处理订单数据和订单商品数据的业务层实现类
 */
@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private IAddressService addressService;
	@Autowired
	private ICartService cartService;
	
	@Override
	@Transactional
	public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
		// 通过参数aid查询收货地址数据
		Address address = addressService.getByAid(aid);
		// 检查数据归属是否正确
		if (!address.getUid().equals(uid)) {
			throw new AccessDeniedException("非法访问收货地址数据");
		}

		// 通过参数cids查询购物车数据，得到List<CartVO>
		List<CartVO> carts = cartService.getByCids(cids, uid);
		// 遍历List<CartVO>，计算商品总价
		Long totalPrice = 0L;
		for (CartVO cart : carts) {
			totalPrice += cart.getRealPrice() * cart.getNum();
		}

		// 创建当前时间对象now
		Date now = new Date();

		// 创建Order对象
		Order order = new Order();
		// 封装Order属性：uid
		order.setUid(uid);
		// 封装Order属性：recv_???
		order.setRecvName(address.getName());
		order.setRecvProvince(address.getProvinceName());
		order.setRecvCity(address.getCityName());
		order.setRecvArea(address.getAreaName());
		order.setRecvAddress(address.getAddress());
		order.setRecvPhone(address.getPhone());
		// 封装Order属性：total_price(以上计算的商品总价)
		order.setTotalPrice(totalPrice);
		// 封装Order属性：status(0)
		order.setStatus(0);
		// 封装Order属性：order_time(now)
		order.setOrderTime(now);
		// 封装Order属性：pay_type(null), pay_time(null)
		// 封装Order属性：4项日志
		order.setCreatedUser(username);
		order.setCreatedTime(now);
		order.setModifiedUser(username);
		order.setModifiedTime(now);
		// 向t_order表中插入数据：saveOrder(Order order)
		saveOrder(order);

		// 遍历List<CartVO>
		for (CartVO cart : carts) {
			// -- 创建OrderItem对象
			OrderItem item = new OrderItem();
			// -- 封装OrderItem属性：oid(order.getOid())
			item.setOid(order.getOid());
			// -- 封装OrderItem属性：pid,title,image,price,num
			item.setPid(cart.getPid());
			item.setTitle(cart.getTitle());
			item.setImage(cart.getImage());
			item.setPrice(cart.getRealPrice());
			item.setNum(cart.getNum());
			// -- 封装OrderItem属性：4项日志
			item.setCreatedUser(username);
			item.setCreatedTime(now);
			item.setModifiedUser(username);
			item.setModifiedTime(now);
			// -- 向t_order_item表中插入若干条数据：saveOrderItem(OrderItem orderItem)
			saveOrderItem(item);
		}
		
		// 删除购物车中对应的数据
		// 销库存
		// 设计如何在指定时间之后关闭订单并归还库存

		// 返回order对象，返回之前可以将部分数据设置为null
		order.setCreatedUser(null);
		order.setCreatedTime(null);
		order.setModifiedUser(null);
		order.setModifiedTime(null);
		return order;
	}

	/**
	 * 插入订单数据
	 * @param order 订单数据
	 */
	private void saveOrder(Order order) {
		Integer rows = orderMapper.saveOrder(order);
		if (rows != 1) {
			throw new InsertException("插入订单数据时出现未知错误");
		}
	}

	/**
	 * 插入订单商品数据
	 * @param orderItem 订单商品数据
	 */
	private void saveOrderItem(OrderItem orderItem) {
		Integer rows = orderMapper.saveOrderItem(orderItem);
		if (rows != 1) {
			throw new InsertException("插入订单商品数据时出现未知错误");
		}
	}
	
}








