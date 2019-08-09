package com.luoliang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoliang.entity.Role;
import com.luoliang.mapper.RoleExample;
import com.luoliang.mapper.RoleMapper;

@Service
public class RoleService {

	@Autowired
	private RoleMapper roleMapper;

	/**
	 * 角色列表-分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Role> findByPage(int pageNum, int pageSize) {
		RoleExample example = new RoleExample();
		PageHelper.startPage(pageNum, pageSize);
		List<Role> list = roleMapper.selectByExample(example);
		return new PageInfo<Role>(list);
	}

	/**
	 * 根据id查找角色
	 * 
	 * @param id
	 * @return
	 */
	public Role findById(int id) {
		return roleMapper.findById(id);
	}

	/**
	 * 添加权限
	 * 
	 * @param id
	 * @param permissions
	 */
	public void addPermission(int id, int[] permissions) {
		// TODO Auto-generated method stub

//		for (int p : permissions) {
//			
//			roleMapper.addPermission(id,p);
//		}

		roleMapper.addPermissions(id, permissions);

	}
}
