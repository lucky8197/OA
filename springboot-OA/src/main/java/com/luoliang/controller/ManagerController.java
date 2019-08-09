package com.luoliang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.luoliang.entity.Account;
import com.luoliang.entity.Permission;
import com.luoliang.entity.Role;
import com.luoliang.service.AccountService;
import com.luoliang.service.PermissionService;
import com.luoliang.service.RoleService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private AccountService accService;

	@Autowired
	private PermissionService PermissionService;

	@Autowired
	private RoleService roleService;

	/**
	 * 用户列表
	 * 
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("accountList")
	public String accountList(Model model, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {
		PageInfo<Account> page = accService.findByPage(pageNum, pageSize);
		model.addAttribute("page", page);
		return "manager/accountList";

	}

	/**
	 * 角色列表
	 * 
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("roleList")
	public String roleList(Model model, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {
		PageInfo<Role> page = roleService.findByPage(pageNum, pageSize);
		model.addAttribute("page", page);
		return "manager/roleList";

	}

	/**
	 * 角色添加/修改权限
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("rolePermission/{id}")
	public String rolePermission(Model model, @PathVariable int id) {
		Role role = roleService.findById(id);
		List<Permission> perList = PermissionService.findAll();
		model.addAttribute("role", role);
		model.addAttribute("perList", perList);
		return "manager/rolePermission";
	}

	/**
	 * 权限列表
	 * 
	 * @param model
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("permissionList")
	public String permissionList(Model model, @RequestParam(defaultValue = "1") int pageNum,
			@RequestParam(defaultValue = "5") int pageSize) {
		PageInfo<Permission> page = PermissionService.findByPage(pageNum, pageSize);
		model.addAttribute("page", page);
		return "manager/permissionList";

	}

	/**
	 * 跳转修改权限页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("permissionModify")
	public String permissionModify(Model model, @RequestParam int id) {

		Permission p = PermissionService.findbyId(id);
		model.addAttribute("p", p);
		return "manager/permissionModify";
	}

	/**
	 * 跳转添加权限页面
	 * 
	 * @return
	 */
	@RequestMapping("permissionAdd")
	public String permissionAddList() {
		return "manager/permissionModify";
	}

}
