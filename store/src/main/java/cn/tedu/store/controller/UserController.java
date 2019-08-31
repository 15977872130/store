package cn.tedu.store.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileTypeException;
import cn.tedu.store.controller.ex.FileUploadIOException;
import cn.tedu.store.controller.ex.FileUploadStateException;
import cn.tedu.store.entity.User;
import cn.tedu.store.service.IUserService;
import cn.tedu.store.util.JsonResult;

/**
 * 处理用户数据相关请求的控制器类
 */
@RestController
@RequestMapping("users")
public class UserController extends BaseController {
	
	@Autowired
	private IUserService userService;
	
	@PostMapping("reg")
	public JsonResult<Void> reg(User user) {
		userService.reg(user);
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("login")
	public JsonResult<User> login(String username, 
			String password, HttpSession session) {
		User data = userService.login(username, password);
		session.setAttribute("uid", data.getUid());
		session.setAttribute("username", data.getUsername());
		return new JsonResult<>(SUCCESS , data);
	}
	
	@RequestMapping(path="change_password", method=RequestMethod.POST)
	public JsonResult<Void> changePassword(
			@RequestParam("old_password") String oldPassword, 
			@RequestParam("new_password") String newPassword, 
			HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		userService.changePassword(uid, username, oldPassword, newPassword);
		return new JsonResult<>(SUCCESS);
	}
	
	@RequestMapping("change_info")
	public JsonResult<Void> changeInfo(User user, HttpSession session) {
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		userService.changeInfo(uid, username, user);
		return new JsonResult<>(SUCCESS);
	}
	
	@GetMapping("get_by_uid")
	public JsonResult<User> getByUid(HttpSession session) {
		Integer uid = getUidFromSession(session);
		User data = userService.getByUid(uid);
		return new JsonResult<>(SUCCESS, data);
	}
	
	/**
	 * 允许上传的文件的最大大小
	 */
	private static final long FILE_MAX_SIZE = 700 * 1024;
	/**
	 * 允许上传的文件的类型
	 */
	private static final List<String> FILE_TYPES = new ArrayList<>();
	
	static {
		FILE_TYPES.add("image/jpeg");
		FILE_TYPES.add("image/png");
	}
	
	// 基于SpringMVC的上传
	@PostMapping("change_avatar")
	public JsonResult<String> changeAvatar(
			@RequestParam("file") MultipartFile file, 
			HttpSession session) {
		// 检查：上传的文件是否为空
		if (file.isEmpty()) {
			// 是：没有选择文件，或选择的文件是0字节的
			throw new FileEmptyException("没有选择文件，或选择的文件是0字节的");
		}
		
		// 检查：上传的文件大小
		if (file.getSize() > FILE_MAX_SIZE) {
			throw new FileSizeException("不允许上传超过" + (FILE_MAX_SIZE / 1024) + "KB的文件");
		}
		
		// 检查：上传的文件类型
		if (!FILE_TYPES.contains(file.getContentType())) {
			throw new FileTypeException("仅支持上传" + FILE_TYPES + "类型的文件");
		}
		
		// 上传到的文件夹
		String dirPath = session.getServletContext().getRealPath("upload");
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		// 获取原始文件名
		String originalFilename = file.getOriginalFilename();
		// 扩展名
		String suffix = "";
		int index = originalFilename.lastIndexOf(".");
		if (index > 0) {
			suffix = originalFilename.substring(index);
		}
		// 文件全名
		String filename = UUID.randomUUID().toString() + suffix;
		
		// 保存到的文件
		File dest = new File(dir, filename);
		// 保存用户上传的头像
		try {
			file.transferTo(dest);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			throw new FileUploadStateException(
				"文件可能已经被移动或删除，不可访问到该文件");
		} catch (IOException e) {
			e.printStackTrace();
			throw new FileUploadIOException(
				"出现读写错误");
		}
		
		// 头像路径
		String avatar = "/upload/" + filename;
		// 从Session中获取uid和username
		Integer uid = getUidFromSession(session);
		String username = getUsernameFromSession(session);
		// 执行更新数据库
		userService.changeAvatar(uid, username, avatar);
		// 返回结果
		return new JsonResult<>(SUCCESS, avatar);
	}

}







