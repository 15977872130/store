package cn.tedu.store.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Order;
import cn.tedu.store.service.IOrderService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理订单相关请求的控制器类
 */
@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
	
	@Autowired
	private IOrderService orderService;

	@RequestMapping("create")
	public JsonResult<Order> create(
	    Integer aid, Integer[] cids, HttpSession session) {
	    // 从session中取出uid, username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
	    // 调用业务层对象的方法执行创建订单，并获取返回值
		Order data = orderService.createOrder(aid, cids, uid, username);
	    // 返回操作成功，及操作结果
		return new JsonResult<>(SUCCESS, data);
	}
	
}










