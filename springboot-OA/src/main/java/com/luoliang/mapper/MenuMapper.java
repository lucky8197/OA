package com.luoliang.mapper;

import org.springframework.stereotype.Repository;

import com.luoliang.entity.Menu;

/**
 * MenuMapper继承基类
 */
@Repository
public interface MenuMapper extends MyBatisBaseDao<Menu, Integer, MenuExample> {
}