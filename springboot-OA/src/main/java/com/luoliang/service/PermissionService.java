package com.luoliang.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoliang.RespStat;
import com.luoliang.entity.Account;
import com.luoliang.entity.Permission;
import com.luoliang.entity.Role;
import com.luoliang.mapper.PermissionExample;
import com.luoliang.mapper.PermissionMapper;

@Service
public class PermissionService {
	@Autowired
	private PermissionMapper perMapper;

	/**
	 * 权限列表-分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Permission> findByPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PermissionExample example = new PermissionExample();
		List<Permission> list = perMapper.selectByExample(example);
		return new PageInfo<>(list);
	}

	/**
	 * 根据id查找权限
	 * 
	 * @param id
	 * @return
	 */
	public Permission findbyId(int id) {
		return perMapper.selectByPrimaryKey(id);
	}

	/**
	 * 更新权限
	 * 
	 * @param permission
	 */
	public void update(Permission permission, HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		for (Role role : account.getRoleList()) {
			if (role.getId() == 1) {
				PermissionExample example = new PermissionExample();
				perMapper.updateByPrimaryKeySelective(permission);
			}
		}
	}

	/**
	 * 添加权限
	 * 
	 * @param permission
	 */
	public void add(Permission permission, HttpServletRequest request) {
		Account account = (Account) request.getSession().getAttribute("account");
		for (Role role : account.getRoleList()) {
			if (role.getId() == 1) {
				PermissionExample example = new PermissionExample();
				perMapper.insert(permission);
			}
		}

	}

	/**
	 * 查询所有权限
	 * 
	 * @return
	 */
	public List<Permission> findAll() {
		PermissionExample example = new PermissionExample();
		return perMapper.selectByExample(example);
	}

	public RespStat deleteById(int id) {
		int result = perMapper.deleteByPrimaryKey(id);
		if (result == 1) {
			return RespStat.build(200, "成功");
		}
		return RespStat.build(500, "失败");
	}

}
