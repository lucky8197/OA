package com.luoliang.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luoliang.RespStat;
import com.luoliang.service.RoleService;

@RestController
@RequestMapping("/api/v1/manager/rolePermission")
public class RoleManagerRestController {

	@Autowired
	private RoleService roleService;

	/**
	 * 权限添加
	 * 
	 * @param permissions
	 * @param id
	 * @return
	 */
	@RequestMapping("add")
	public RespStat permissionadd(@RequestParam(required = false) int[] permissions, @RequestParam int id) {
		roleService.addPermission(id, permissions);
		return RespStat.build(200, "修改成功");
	}
}
