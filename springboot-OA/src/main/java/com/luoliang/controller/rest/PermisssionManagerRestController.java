package com.luoliang.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luoliang.RespStat;
import com.luoliang.entity.Permission;
import com.luoliang.service.PermissionService;

/**
 * 只和用户交换数据
 * 
 * @author Admin
 *
 */
@RestController
@RequestMapping("/api/v1/manager/permission")
public class PermisssionManagerRestController {

	@Autowired
	private PermissionService permissionSer;

	/**
	 * 权限添加
	 * 
	 * @param permission
	 * @return
	 */
	@RequestMapping("add")
	public RespStat add(@RequestBody Permission permission, HttpServletRequest request) {

		if (permission.getId() == null) {
			permissionSer.add(permission, request);

		} else {
			permissionSer.update(permission, request);
		}
		return RespStat.build(200, "成功");
	}

	@RequestMapping("deleteById")
	public RespStat deleteById(int id) {
		return permissionSer.deleteById(id);
	}

}
