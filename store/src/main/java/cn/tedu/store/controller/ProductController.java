package cn.tedu.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.tedu.store.entity.Product;
import cn.tedu.store.service.IProductService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理商品数据相关请求的控制器
 */
@RestController
@RequestMapping("products")
public class ProductController extends BaseController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("hot")
	public JsonResult<List<Product>> getHotList() {
		// 调用业务层对象查询数据
		List<Product> data = productService.getHotList();
		// 响应成功，和数据
		return new JsonResult<>(SUCCESS, data);
	}

	@GetMapping("{id}/details")
	public JsonResult<Product> getById(
		@PathVariable("id") Integer id) {
		// 执行查询
		Product data = productService.getById(id);
		// 响应成功，及查询结果
		return new JsonResult<>(SUCCESS, data);
	}
	
}










