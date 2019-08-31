package cn.tedu.store.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.service.ICartService;
import cn.tedu.store.util.JsonResult;
import cn.tedu.store.vo.CartVO;

/**
 * 处理购物车数据相关请求的控制器类
 */
@RestController
@RequestMapping("carts")
public class CartController extends BaseController {

	@Autowired
	private ICartService cartService;
	
	@RequestMapping("add")
	public JsonResult<Void> addToCart(
		Integer pid, Integer num, HttpSession session) {
		// 从session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务层对象执行增加
		cartService.addToCart(uid, pid, num, username);
		// 响应成功
		return new JsonResult<>(SUCCESS);
	}
	
	@GetMapping("/")
	public JsonResult<List<CartVO>> getByUid(HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 执行查询，获取结果
		List<CartVO> data = cartService.getByUid(uid);
		// 响应成功，及查询结果
		return new JsonResult<>(SUCCESS, data);
	}
	
	@RequestMapping("{cid}/add_num")
	public JsonResult<Integer> addNum(
		@PathVariable("cid") Integer cid, 
		HttpSession session) {
		// 从session中获取uid, username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 调用业务对象执行增加，并获取返回结果
		Integer data = cartService.addNum(cid, uid, username);
		// 返回成功，及操作结果
		return new JsonResult<>(SUCCESS, data);
	}
	
	@GetMapping("get_by_cids")
	public JsonResult<List<CartVO>> getByCids(
			Integer[] cids, HttpSession session) {
		// 从session中获取uid
		Integer uid = getUidFromSession(session);
		// 调用业务层对象查询数据
		List<CartVO> data = cartService.getByCids(cids, uid);
		// 响应成功，及查询结果
		return new JsonResult<>(SUCCESS, data);
	}
	
}









