package com.luoliang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.luoliang.RespStat;
import com.luoliang.entity.Account;
import com.luoliang.mapper.AccountExample;
import com.luoliang.mapper.AccountMapper;

@Service
public class AccountService {

	@Autowired
	AccountMapper accMapper;

	/**
	 * 登录验证
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	public Account findByLoginNameAndPassword(String loginName, String password) {

//		AccountExample example = new AccountExample();
//		example.createCriteria().andLoginNameEqualTo(loginName).andPasswordEqualTo(password);
//
//		// password
//		// 1. 没有
//		// 2. 有一条
//		// 3. 好几条 X
//		List<Account> list = accMapper.selectByExample(example);

		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		Account account = accMapper.findByLoginNameAndPassword(loginName, md5Password);
		return account;
	}

	/**
	 * 账号列表-分页
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageInfo<Account> findByPage(int pageNum, int pageSize) {

		List<Account> alist = accMapper.selectByPermission();

		AccountExample example = new AccountExample();

		// 分页
		PageHelper.startPage(pageNum, pageSize);
		// 不设置example条件，查全部.
		List<Account> list = accMapper.selectByExample(example);
		return new PageInfo<Account>(list, 5); // 2为显示最大页数

	}

	/**
	 * 删除账号
	 * 
	 * @param id
	 * @return
	 */
	public RespStat deleteById(int id) {

		// 1、要提示用户
		// 2、通过删除标记，数据永远删不掉 /update 只做增，不直接改表内容。

		int key = accMapper.deleteByPrimaryKey(id);
		if (key == 1) {
			return RespStat.build(200, "删除成功");
		} else {
			return RespStat.build(500, "删除出错");
		}
	}

	/**
	 * 更新账户信息
	 * 
	 * @param account
	 */
	public void update(Account account) {
		accMapper.updateByPrimaryKeySelective(account);
	}

	/**
	 * 注册账号
	 * 
	 * @param loginName
	 * @param password
	 * @param nickName
	 * @param age
	 * @return
	 */
	public int registerAccount(String loginName, String password, String nickName, Integer age) {
		Account account = new Account();
		account.setLoginName(loginName);
		String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
		account.setPassword(md5Password);
		account.setRole("user");
		if (nickName != null && nickName != "") {
			account.setNickName(nickName);
		}
		if (nickName != null) {
			account.setAge(age);
		}
		return accMapper.insert(account);
	}

	/**
	 * 检查用户名
	 * 
	 * @param loginName
	 * @return
	 */
	public boolean checkName(String loginName) {
		AccountExample example = new AccountExample();
		example.createCriteria().andLoginNameEqualTo(loginName);
		List<Account> accounts = accMapper.selectByExample(example);
		if (accounts.size() == 0) {
			// 通过check
			return true;
		}
		return false;
	}
}
