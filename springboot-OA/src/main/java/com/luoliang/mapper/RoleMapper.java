package com.luoliang.mapper;

import org.springframework.stereotype.Repository;

import com.luoliang.entity.Role;

/**
 * RoleMapper继承基类
 */
@Repository
public interface RoleMapper extends MyBatisBaseDao<Role, Integer, RoleExample> {

	void addPermission(int id, int permission);

	void addPermissions(int id, int[] permissions);

	Role findById(int id);

}
