/**
 * 
 */
package net.zdsoft.eis.base.data.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.exception.PassportException;

/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AccountService.java, v 1.0 2007-7-18 下午03:41:10 zhaosf Exp $
 */

public interface PassportAccountService {

	/**
	 * 批量增加用户，用于将学籍中的现有用户初始化到passport中
	 * @throws OperationNotAllowedException
	 */
	public void addAccounts() throws OperationNotAllowedException;

	/**
	 * 增加用户
	 * @param user
	 * @return 
	 * @throws PassportException
	 */
	public User addAccount(User user) throws OperationNotAllowedException;

	/**
	 * 增加用户
	 * @param user 用户
	 * @param employee 教职工
	 * @return 
	 * @throws OperationNotAllowedException
	 */
	public User addAccount(User user, BaseTeacher teacher)
			throws OperationNotAllowedException;

	/**
	 * 批量增加用户
	 * @param users
	 * @return 
	 * @throws PassportException
	 */
	public User[] addAccounts(User[] users) throws OperationNotAllowedException;

	/**
	 * 修改用户
	 * @param user
	 * @param fieldNames
	 * @throws PassportException
	 */
	public void modifyAccount(User user, String[] fieldNames)
			throws OperationNotAllowedException;

	/**
	 * 修改用户
	 * @param user
	 * @param teacher
	 * @param fieldNames
	 * @throws OperationNotAllowedException
	 */
	public void modifyAccount(BaseTeacher teacher, String[] fieldNames)
			throws OperationNotAllowedException;

	/**
	 * 批量修改用户
	 * @param users
	 * @param fieldNames
	 * @throws PassportException
	 */
	public void modifyAccounts(User[] users, String[] fieldNames)
			throws OperationNotAllowedException;

	/**
	 * 批量修改用户状态
	 * @param users
	 * @param mark 
	 * @throws PassportException
	 */
	public void modifyAccountsMark(List<User> users, int mark)
			throws OperationNotAllowedException;

	/**
	 * 批量修改用户密码
	 * @param users
	 * @param mark 
	 * @throws PassportException
	 */
	public void modifyAccountsPassword(List<User> users, String password)
			throws OperationNotAllowedException;

	/**
     * 批量修改用户所属单位
     * @param users
     * @throws PassportException
     */
    public void modifyAccountsSchoolId(List<User> users) throws OperationNotAllowedException;
    
	/**
     * 批量修改用户统一密码
     * 
     * @param users
     * @param mark
     * @throws PassportException
     */
	public void modifyAccountsPasswordResetUnification(List<User> users,
			String password) throws OperationNotAllowedException;

	/**
	 * 批量修改用户密码为用户名
	 * @param map
	 * @throws PassportException
	 */
	public void modifyAccountsPasswordResetUsername(Map<String, String> map)
			throws OperationNotAllowedException;

	/**
	 * 删除用户
	 * @param id accountId
	 * @throws PassportException
	 */
	public boolean removeAccount(String accountId)
			throws OperationNotAllowedException;

	/**
	 * 根据accountid 得到passport那边的acount信息
	 *
	 *@author "yangk"
	 * Sep 2, 2010 3:12:12 PM
	 * @param accountIds
	 * @return
	 */
	public Account[] queryAccounts(String[] accountIds)throws OperationNotAllowedException;

	/**
	 * 批量删除用户
	 * @param ids
	 * @throws PassportException
	 */
	public String[] removeAccounts(String[] ids)
			throws OperationNotAllowedException;
	
	/**
	 * 批量删除用户
	 * @param ids
	 * @throws PassportException
	 */
	public String[] removeAccounts(List<User> users)
			throws OperationNotAllowedException;

	public Account queryAccountByUsername(String username);
}
