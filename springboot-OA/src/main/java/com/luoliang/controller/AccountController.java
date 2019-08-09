package com.luoliang.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.github.tobato.fastdfs.domain.fdfs.MetaData;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.luoliang.RespStat;
import com.luoliang.entity.Account;
import com.luoliang.entity.Config;
import com.luoliang.service.AccountService;

/**
 * 用户账户相关
 * 
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	FastFileStorageClient fc;

	@Autowired
	AccountService accountSrv;

	@Autowired
	Config config;

	/**
	 * 跳转到登录界面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("login")
	public String login(Model model) {

		model.addAttribute("config", config);
		return "account/login";
	}

	/**
	 * 跳转到注册页面
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("register")
	public String register(Model model) {
		model.addAttribute("config", config);
		return "account/register";
	}

	/**
	 * 用户登录异步校验
	 * 
	 * @param loginName
	 * @param password
	 * @return success 成功
	 */

	/**
	 * /Login 1. 如果首次打开（没有任何参数），展示静态的HTML 2. 如果有post请求，验证账号密码是否正确
	 * 
	 * @param loginName
	 * @param password
	 * @param request
	 * @return
	 */
	@RequestMapping("validataAccount")
	@ResponseBody
	public String validataAccount(String loginName, String password, HttpServletRequest request) {

		// 1. 直接返回是否登录成功的结果
		// 2. 返回 Account对象，对象是空的 ，在controller里做业务逻辑
		// 在公司里 统一写法

		// 让service返回对象，如果登录成功 把用户的对象
		Account account = accountSrv.findByLoginNameAndPassword(loginName, password);

		if (account == null) {
			return "登录失败";
		} else {
			// 登录成功
			// 写到Session里
			// 在不同的controller 或者前端页面上 都能使用
			// 当前登录用户的Account对象

			request.getSession().setAttribute("account", account);
			return "success";

		}
	}

	/**
	 * 注册账号
	 * 
	 * @param loginName
	 * @param password
	 * @param pickName
	 * @param age
	 * @return
	 */
	@RequestMapping("registerAccount")
	@ResponseBody
	public String registerAccount(String loginName, String password, String nickName,
			@RequestParam("age") Integer age) {
		System.out.println(nickName);
		System.out.println(age);
		int result = accountSrv.registerAccount(loginName, password, nickName, age);

		if (result == 1) {
			return "success";
		}
		return "注册失败";
	}

	/**
	 * 验证用户名有没有被注册
	 * 
	 * @param loginName
	 * @return
	 */
	@RequestMapping("checkLoginName")
	@ResponseBody
	public String checkLoginName(String loginName) {
		boolean result = accountSrv.checkName(loginName);
		if (result) {
			return "success";
		}
		return "用户名已存在";
	}

	/**
	 * 退出
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request) {

		request.getSession().removeAttribute("account");
		return "index";
	}

	/**
	 * 员工列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "5") int pageSize,
			Model model) {

		PageInfo<Account> page = accountSrv.findByPage(pageNum, pageSize);

		model.addAttribute("page", page);
		return "/account/list";
	}

	/**
	 * 删除账号
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteById")
	@ResponseBody
	public RespStat deleteById(int id) {
		// 标记一下 是否删除成功？ status
		RespStat stat = accountSrv.deleteById(id);

		return stat;
	}

	/**
	 * 跳转到个人信息页面
	 * 
	 * @return
	 */
	@RequestMapping("/profile")
	public String profile() {
		return "account/profile";
	}

	/**
	 * 下载文件
	 * 
	 * @param resp
	 * @return
	 */
	@RequestMapping("/down")
	@ResponseBody
	public ResponseEntity<byte[]> down() {

		DownloadByteArray cb = new DownloadByteArray();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "aaa.xx"); // xx为后缀名
		// 下载文件
		byte[] bs = fc.downloadFile("group1", "M00/00/00/wKj0Ql1CM5yAcIeYAAAV7P6rE08258_200x200.png" + "", cb);

		return new ResponseEntity<>(bs, headers, HttpStatus.OK);
	}

	/**
	 * 上传文件
	 * 
	 * @param filename
	 * @param password
	 * @return
	 */
	@RequestMapping("/fileUploadController")
	public String fileUpload(MultipartFile filename, String password, HttpServletRequest request) {

		Account account = (Account) request.getSession().getAttribute("account");

		try {

			// 当前项目的路径
//			File path = new File(ResourceUtils.getURL("classpath:").getPath());
//			File upload = new File(path.getAbsolutePath(), "static/uploads/");

			// 指定系统存放文件的目录

			// 文件转存
			// 文件重名
			// filename.transferTo(new File("c:/dev/uploads" + "/" +
			// filename.getOriginalFilename()));

			// 元数据
			Set<MetaData> metaDataSet = new HashSet<MetaData>();
			metaDataSet.add(new MetaData("Author", "yimingge"));
			metaDataSet.add(new MetaData("CreateDate", "2016-01-05"));

			try {
				StorePath uploadFile = null;
				// uploadFile = fc.uploadFile(filename.getInputStream(), filename.getSize(),
				// FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);

				uploadFile = fc.uploadImageAndCrtThumbImage(filename.getInputStream(), filename.getSize(),
						FilenameUtils.getExtension(filename.getOriginalFilename()), metaDataSet);

				System.out.println("saddasdada" + uploadFile.getPath());

				account.setPassword(password);
				account.setLocation(uploadFile.getPath());

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			accountSrv.update(account);

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "account/profile";
	}

}
