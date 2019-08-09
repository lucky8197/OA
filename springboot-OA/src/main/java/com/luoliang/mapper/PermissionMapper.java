package com.luoliang.mapper;

import org.springframework.stereotype.Repository;

import com.luoliang.entity.Permission;

/**
 * PermissionMapper继承基类
 */
@Repository
public interface PermissionMapper extends MyBatisBaseDao<Permission, Integer, PermissionExample> {
}