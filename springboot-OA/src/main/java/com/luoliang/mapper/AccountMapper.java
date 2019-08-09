package com.luoliang.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.luoliang.entity.Account;

/**
 * AccountMapper继承基类
 */
@Repository
public interface AccountMapper extends MyBatisBaseDao<Account, Integer, AccountExample> {

	List<Account> selectByPermission();

	Account findByLoginNameAndPassword(String loginName, String password);
}