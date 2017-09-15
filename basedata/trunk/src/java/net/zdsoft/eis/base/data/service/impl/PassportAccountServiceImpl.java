/**
 * 
 */
package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.StudentFamilyService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.UserAccount;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.leadin.exception.OperationNotAllowedException;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注意：调用此类的增加Account方法时，应在方法的最后调用，便于出错时同时删除已增加的account
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: PassportAccountServiceImpl.java, v 1.0 2007-7-19 下午02:05:42
 *          zhaosf Exp $
 */

public class PassportAccountServiceImpl implements PassportAccountService {
	private static final Logger log = LoggerFactory
			.getLogger(PassportAccountServiceImpl.class);

	private BaseTeacherService baseTeacherService;// 教职工信息
	private UserService userService;// 用户信息
	private BaseUserDao baseUserDao;
	private UnitService unitService;// 单位信息
	private StudentFamilyService studentFamilyService;
	private SystemDeployService systemDeployService;

	private boolean isPassportTurnOn() {
		return systemDeployService.isConnectPassport();
	}

	@SuppressWarnings("unchecked")
	public void addAccounts() throws OperationNotAllowedException {
		List userList = null;// userService.getAllUsers();屏蔽掉
		if (null == userList || userList.size() == 0)
			return;

		User[] users = (User[]) userList.toArray(new User[] {});
		addAccounts(users);
	}

	public User addAccount(User user) throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return user;

		try {
			Account account = toAccount(user);
			Account retAccount = PassportClient.getInstance().addAccount(
					account);
			if (retAccount != null) {
				user.setSequence(Long.valueOf(retAccount.getSequence()));
			}

		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}

		updateUserForPassport(user);
		return user;
	}

	/**
	 * 增加passport账户后同时更新eis中的sequence等信息，如果失败，则删除passport账户
	 * 
	 * @param user
	 */
	private void updateUserForPassport(User... users)
			throws OperationNotAllowedException {
		// 更新
		try {
			baseUserDao.updateUserForPassport(users);
		} catch (Exception e) {
			log.error("add user to passport and update sequence error!", e);

			String[] accounts = new String[users.length];
			for (int i = 0; i < users.length; i++) {
				User user = users[i];
				accounts[i] = user.getAccountId();
			}
			removeAccounts(accounts);
			throw new OperationNotAllowedException(e.getMessage());
		}
	}

	public User addAccount(User user, BaseTeacher teacher)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return user;

		Account account = toAccount2(user,
				transferToEmployee(teacher, User.TEACHER_LOGIN));
		try {
			Account retAccount = PassportClient.getInstance().addAccount(
					account);
			if (null != retAccount) {
				user.setSequence(Long.valueOf(retAccount.getSequence()));
			}
		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}

		updateUserForPassport(user);
		return user;
	}

	@SuppressWarnings("unchecked")
	public User[] addAccounts(User[] users) throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return new User[] {};

		if (null == users || users.length == 0)
			return new User[] {};

		int ownerType = User.TEACHER_LOGIN;
		if (users[0].getOwnerType() != null) {
			ownerType = users[0].getOwnerType();
		}

		List<Map> list = getMaps(users, ownerType);
		Map<String, UserAccount> employeeMap = list.get(0); // 关联的教职工信息
		Map<String, String> regionMap = list.get(1);// 行政区划
		Map<String, String> etohSchoolIdMap = list.get(2);// 家校互联id

		Account[] accounts = new Account[users.length];
		for (int i = 0; i < users.length; i++) {
			User user = users[i];
			user.setRegion(regionMap.get(user.getUnitid()));
			user.setEtohSchoolId(etohSchoolIdMap.get(user.getUnitid()));
			String teacherId = user.getTeacherid();
			Account account = null;
			if (null == teacherId || teacherId.trim().length() == 0) {
				account = toAccount(user);
			} else {
				account = toAccount2(user, employeeMap.get(teacherId));
			}
			accounts[i] = account;
		}
		try {
			Account[] retAccounts = PassportClient.getInstance().addAccounts(
					accounts);
			int i = 0;
			for (Account a : retAccounts) {
				users[i].setSequence(Long.valueOf(a.getSequence()));
				i++;
			}
		} catch (PassportException e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}

		updateUserForPassport(users);
		return users;
	}

	public void modifyAccount(User user, String[] fieldNames)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		Account account = toAccount(user, fieldNames);
		try {
			PassportClient.getInstance().modifyAccount(account,
					packModifiedFields(fieldNames));

		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}

	}

	public void modifyAccount(BaseTeacher teacher, String[] fieldNames)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		// 一个教职工可以对应多个用户
		List<User> userList = userService.getUsersByOwner(teacher.getId());
		if (null == userList || userList.size() == 0)
			return;

		Account[] accounts = new Account[userList.size()];
		for (int i = 0; i < userList.size(); i++) {
			User user = (User) userList.get(i);
			Account account = toAccount2(user,
					transferToEmployee(teacher, User.TEACHER_LOGIN));
			accounts[i] = account;
		}
		modifyAccounts(accounts, packModifiedFieldsByEmployee(fieldNames));
	}

	@SuppressWarnings("unchecked")
	public void modifyAccounts(User[] users, String[] fieldNames)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == users || users.length == 0)
			return;

		int ownerType = User.TEACHER_LOGIN;
		if (users[0].getOwnerType() != null) {
			ownerType = users[0].getOwnerType();
		}

		List<Map> list = getMaps(users, ownerType);
		Map<String, UserAccount> employeeMap = list.get(0); // 关联的教职工信息
		Map<String, String> regionMap = list.get(1);// 行政区划
		Map<String, String> etohSchoolIdMap = list.get(2);// 家校互联id

		Account[] accounts = new Account[users.length];
		for (int i = 0; i < users.length; i++) {
			User user = users[i];
			user.setRegion(regionMap.get(user.getUnitid()));
			user.setEtohSchoolId(etohSchoolIdMap.get(user.getUnitid()));
			String teacherId = user.getTeacherid();
			Account account = null;
			if (null == teacherId || teacherId.trim().length() == 0) {
				account = toAccount(user, fieldNames);
			} else {
				account = toAccount2(user, employeeMap.get(teacherId));
			}
			accounts[i] = account;
		}

		modifyAccounts(accounts, packModifiedFields(fieldNames));

	}

	public void modifyAccountsMark(List<User> users, int mark)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == users || users.size() == 0)
			return;

		String[] fieldNames = new String[] { "mark" };
		Account[] accounts = new Account[users.size()];
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			user.setMark(mark);
			Account account = toAccount(user, fieldNames);
			accounts[i] = account;
		}

		modifyAccounts(accounts, packModifiedFields(fieldNames));

	}

	public void modifyAccountsPassword(List<User> users, String password)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == users || users.size() == 0)
			return;

		String[] fieldNames = new String[] { "password" };
		Account[] accounts = new Account[users.size()];
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			user.setPassword(password);
			Account account = toAccount(user, fieldNames);
			accounts[i] = account;
		}

		modifyAccounts(accounts, packModifiedFields(fieldNames));

	}

	public void modifyAccountsSchoolId(List<User> users)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == users || users.size() == 0)
			return;

		String[] fieldNames = new String[] { "unitid" };
		Account[] accounts = new Account[users.size()];
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			Account account = toAccount(user, fieldNames);
			accounts[i] = account;
		}

		modifyAccounts(accounts, packModifiedFields(fieldNames));
	}

	public void modifyAccountsPasswordResetUnification(List<User> users,
			String password) throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == users || users.size() == 0)
			return;
		String[] fieldNames = new String[] { "password" };
		Account[] accounts = new Account[users.size()];
		for (int i = 0; i < users.size(); i++) {
			User user = (User) users.get(i);
			user.setPassword(password);
			Account account = toAccount(user, fieldNames);
			accounts[i] = account;
		}
		modifyAccounts(accounts, packModifiedFields(fieldNames));
	}

	public void modifyAccountsPasswordResetUsername(Map<String, String> map)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return;

		if (null == map || map.size() == 0)
			return;
		Set<String> userSet = map.keySet();
		String[] fieldNames = new String[] { "password" };
		Account[] accounts = new Account[map.size()];
		int i = 0;
		for (String userId : userSet) {
			User user = userService.getUser(userId);
			user.setPassword(user.getName());
			Account account = toAccount(user, fieldNames);
			accounts[i] = account;
			i++;
		}
		modifyAccounts(accounts, packModifiedFields(fieldNames));
	}

	public boolean removeAccount(String accountId)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return true;

		try {
			// PassportClient.removeService(UUIDUtil.toUUID(id));
			return PassportClient.getInstance().removeAccount(accountId);
		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}
	}

	public String[] removeAccounts(String[] accountIds)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return new String[] {};

		if (null == accountIds || accountIds.length == 0)
			return new String[] {};
		List<String> notRemoveAccountIdList = new ArrayList<String>();
		try {
			// PassportClient.removeServices(uuids);
			//removeAccounts 改成 deleteAccounts 
			PassportClient.getInstance().deleteAccounts(
					accountIds);
//			int i = 0;
//			for (boolean b : ret) {
//				if (!b) {
//					notRemoveAccountIdList.add(accountIds[i]);
//				}
//				i++;
//			}
		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}
		return notRemoveAccountIdList.toArray(new String[0]);
	}

	public String[] removeAccounts(List<User> users)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn())
			return new String[] {};

		if (null == users || users.size() == 0)
			return new String[] {};
		List<String> notRemoveAccountIdList = new ArrayList<String>();
		List<String> accountIds = new ArrayList<String>();
		for (int i = 0; i < users.size(); i++) {
			String guid = ((User) users.get(i)).getAccountId();
			if (StringUtils.isNotBlank(guid))
				accountIds.add(guid);
		}
		if (CollectionUtils.isNotEmpty(accountIds)) {
			try {
				// PassportClient.removeServices(uuids);
				//removeAccounts 改成 deleteAccounts 
				PassportClient.getInstance().deleteAccounts(
						accountIds.toArray(new String[0]));
//				int i = 0;
//				for (boolean b : ret) {
//					if (!b) {
//						notRemoveAccountIdList.add(accountIds.get(i));
//					}
//					i++;
//				}
			} catch (PassportException e) {
				throw new OperationNotAllowedException(e.getMessage());
			} catch (Exception e) {
				e.printStackTrace();
				throw new OperationNotAllowedException(e.getMessage());
			}
		}
		return notRemoveAccountIdList.toArray(new String[0]);
	}

	public Account queryAccountByUsername(String username) {
		if (!isPassportTurnOn() || StringUtils.isBlank(username)) {
			return null;
		}
		try {
			return PassportClient.getInstance()
					.queryAccountByUsername(username);

		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}
	}

	public Account[] queryAccounts(String[] accountIds)
			throws OperationNotAllowedException {
		if (!isPassportTurnOn()) {
			return null;
		}

		if (accountIds == null || accountIds.length == 0) {
			return new Account[0];
		}

		try {
			return PassportClient.getInstance().queryAccounts(accountIds);
		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}
	}

	/**
	 * 批量修改账号
	 * 
	 * @param accounts
	 * @param fieldNames
	 * @throws OperationNotAllowedException
	 */
	private void modifyAccounts(Account[] accounts, String[] fieldNames)
			throws OperationNotAllowedException {
		try {
			PassportClient.getInstance().modifyAccounts(accounts, fieldNames);
		} catch (PassportException e) {
			throw new OperationNotAllowedException(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new OperationNotAllowedException(e.getMessage());
		}
	}

	/**
	 * 将用户信息转化为账号信息
	 * 
	 * @param user
	 * @return
	 */
	private Account toAccount(User user) {
		return toAccount2(user, null);
	}

	private Account toAccount(User user, String[] fields) {
		Account account = null;
		if (null == fields || fields.length == 0) {
			account = toAccount(user);
		} else {
			account = new Account();
			account.setId(user.getAccountId());
			// 由于目前主要是状态和密码，考虑到效率，暂时判断，不用invoke
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				if ("mark".equals(field)) {
					account.setState(convertMark(user.getMark()));
				} else if ("password".equals(field)) {
					String pwd = getAccountPassword(user);
					account.setPassword(pwd);
					user.setPassword(pwd);
				} else if ("unitid".equals(field)) {
					setSchoolRelated(user, account);
				}
			}
		}
		account.setMsn(user.getMsn());
		account.setQq(user.getQq());
		return account;
	}

	/**
	 * 设置与学校相关的属性
	 * 
	 * @param user
	 * @param account
	 */
	private void setSchoolRelated(User user, Account account) {
		String region = user.getRegion();
		String etohSchoolId = user.getEtohSchoolId();

		Unit unit = unitService.getUnit(user.getUnitid());
		if (null != unit) {
			Map<String, String> map = account.getProperties();
			if (map == null)
				map = new HashMap<String, String>();
			// 设置任职学校
			map.put("currentSchoolName", unit.getName());
			account.setProperties(map);
			if (StringUtils.isBlank(region)) {
				region = unit.getRegion();
			}
			if (StringUtils.isBlank(etohSchoolId)) {
				etohSchoolId = unit.getEtohSchoolId();
			}
		}
		if (region == null)
			region = "";
		account.setRegionId(region);// 教职工所在单位（学校或教育局）的行政区划

		if (etohSchoolId == null)
			etohSchoolId = "";
		account.setSchoolId(etohSchoolId);// 目前passport只支持9位

	}

	@SuppressWarnings("unchecked")
	private Account toAccount2(User user, UserAccount userAccount) {

		if (null == user) {
			log.error("user is null");
			return null;
		}

		Integer ownerType = user.getOwnerType();

		Account account = new Account();
		int type = 12; // 用户类型:11：中小学生、12：在职教师、13：退休教师、14：在校大学生、15：家长
		int fixedType = 2;// 1：学生、2：教师、3：家长
		if (null == userAccount) {
			// 教职工信息
			// 可以暂不考虑（否则，须在修改教职工信息时，也要通知passport修改用户信息。且教职工信息不一定已保存到数据库中，须传递过来）
			String teacherId = user.getTeacherid();
			if (null != teacherId && !"".equals(teacherId.trim())) {
				if (ownerType == User.STUDENT_LOGIN) {
					List<User> userList = baseUserDao
							.getStudentUsers(new String[] { teacherId });
					if (CollectionUtils.isNotEmpty(userList)) {
						User info = userList.get(0);
						userAccount = transferToEmployee(info, ownerType);
					}
				} else if (ownerType == User.FAMILY_LOGIN) {
					Family family = studentFamilyService.getFamily(teacherId);
					userAccount = transferToEmployee(family, ownerType);
				} else {
					BaseTeacher teacher = baseTeacherService
							.getBaseTeacher(teacherId);
					userAccount = transferToEmployee(teacher, ownerType);
				}

			}
		}

		if (null != userAccount) {
			type = Integer.valueOf(userAccount.getType());
			String phone = userAccount.getMobilePhone();
			if (null != phone) {
				if (phone.length() > 20)
					phone = phone.substring(0, 20);
			}
			account.setPhone(phone);// varchar(40) --> varchar(20)
//			account.setEmail(userAccount.getEmail());
			account.setSex(userAccount.getSex());
			account.setBirthday(userAccount.getBirthday());
			account.setHomepage(userAccount.getHomepage());
			account.setExtraId(userAccount.getExtraId());// passport专用
		}

		setSchoolRelated(user, account);

		account.setType(type);// 默认在职教师
		if (user.getOwnerType() == null || user.getOwnerType() == 0) {
			account.setFixedType(fixedType);
		} else {
			account.setFixedType(user.getOwnerType());
		}
		// 现在accountId 是重新生成，不再和userid共用
		account.setId(user.getAccountId());
		account.setUsername(user.getName());
		account.setPassword(getAccountPassword(user));

		// account.setEmail(user.getEmail());

		String realName = user.getRealname();
		if (null != realName) {
			if (realName.length() > 20)
				realName = realName.substring(0, 20);
		}
		account.setRealName(realName);// varchar(150) --> varchar(20)

		account.setRegisterTime(user.getCreationTime());
		if (null != user.getMark()) {
			account.setState(convertMark(user.getMark()));
		} else {
			account.setState(0);
		}
		// 因为个人设置中需要设置到QQ和MSN，但是用户和教职工保存的时候，没有这个字段，导致每次更新都被清除掉，所以再次取得
		if (StringUtils.isNotBlank(user.getId())
				&& StringUtils.isNotBlank(user.getAccountId())) {
			try {
				Account a = PassportClient.getInstance().queryAccount(
						user.getAccountId());
				if (a != null) {
					account.setQq(a.getQq());
					account.setMsn(a.getMsn());
				}
			} catch (PassportException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return account;

	}

	private int convertMark(int mark) {
		int state = 0;// 状态，1：正常、0：不正常，默认不正常
		if (mark == User.USER_MARK_NORMAL) {// 0-未审核 1-正常 2-锁定 3-离职锁定
			state = 1;
		}
		return state;
	}

	@SuppressWarnings("unused")
	private String convertPassword(String password) {
		// varchar(100)，不作截取操作，由于截断后会密码会不同
		if (null == password)
			password = "";
		password = password.trim();
		if (!"".equals(password.trim())) {
			password = PWD.decode(password);
		}
		return password;
	}

	private String[] packModifiedFields(String[] fields) {
		String[] rtnFields = null;
		// 为空时，表示修改所有相关字段
		if (null == fields || fields.length == 0) {
			rtnFields = new String[] { "msn", "qq", "username", "password",
					"email", "realName", "state", "phone", "sex", "birthday",
					"homepage","extraid" };

		} else {
			// 目前主要是状态和密码
			rtnFields = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				if ("mark".equals(field)) {
					rtnFields[i] = "state";
				} else if ("password".equals(field)) {
					rtnFields[i] = "password";
				} else if ("name".equals(field)) {
					rtnFields[i] = "username";
				} else if ("realname".equals(field)) {
					rtnFields[i] = "realName";
				} else if ("unitid".equals(field)) {
					rtnFields[i] = "schoolId";
				}
			}
		}
		return rtnFields;
	}

	private String[] packModifiedFieldsByEmployee(String[] fields) {
		String[] rtnFields = null;
		// 为空时，表示修改所有相关字段
		if (null == fields || fields.length == 0) {
			rtnFields = new String[] { "type", "phone", "sex", "birthday" };
			// rtnFields = new
			// String[]{"username","password","email","realName","state","phone","sex","birthday",
			// "homepage"};

		} else {
			rtnFields = fields;
		}
		return rtnFields;
	}

	private UserAccount transferToEmployee(Object o, int ownerType) {
		if (o == null) {
			return null;
		}

		UserAccount userAccount = new UserAccount();
		if (ownerType == User.STUDENT_LOGIN) {
			User info = (User) o;

			userAccount.setId(info.getId());
			userAccount.setMobilePhone(info.getMobile());
			userAccount.setBirthday(info.getBirthday());
			if (org.apache.commons.lang.StringUtils.isBlank(info.getSex())) {
				info.setSex("0");
			}
//			userAccount.setEmail(info.getEmail());
			userAccount.setSex(Integer.valueOf(info.getSex()));
			userAccount.setHomepage(info.getHomepage());
			userAccount.setExtraId(info.getExtraId());// passport专用
		} else if (ownerType == User.FAMILY_LOGIN) {
			Family family = (Family) o;
			userAccount.setId(family.getId());
			userAccount.setMobilePhone(family.getMobilePhone());
			userAccount.setBirthday(family.getBirthday());
			if (org.apache.commons.lang.StringUtils.isBlank(family.getSex())) {
				family.setSex("0");
			}
//			userAccount.setEmail(family.getEmail());
			userAccount.setSex(Integer.valueOf(family.getSex()));
			userAccount.setHomepage(family.getHomepage());
		} else {
			BaseTeacher teacher = (BaseTeacher) o;
			userAccount.setId(teacher.getId());
			userAccount.setMobilePhone(teacher.getPersonTel());
			userAccount.setBirthday(teacher.getBirthday());
			if (org.apache.commons.lang.StringUtils.isBlank(teacher.getSex())) {
				teacher.setSex("0");
			}
//			userAccount.setEmail(teacher.getEmail());
			userAccount.setSex(Integer.valueOf(teacher.getSex()));
			userAccount.setHomepage(teacher.getHomepage());
			userAccount.setExtraId(teacher.getTchId());// passport专用
		}
		userAccount.setOwnerType(ownerType);
		return userAccount;
	}

	@SuppressWarnings("unchecked")
	private List<Map> getMaps(User[] users, int ownerType) {
		Set<String> teacherIdSet = new HashSet<String>();
		Set<String> unitIdSet = new HashSet<String>();// 单位id
		for (int i = 0; i < users.length; i++) {
			String employeeId = users[i].getTeacherid();
			if (null == employeeId || employeeId.trim().length() == 0)
				continue;
			teacherIdSet.add(employeeId);
			unitIdSet.add(users[i].getUnitid());
		}

		Map<String, UserAccount> teacherMap = new HashMap<String, UserAccount>();
		if (teacherIdSet.size() > 0) {
			List teacherList;
			if (ownerType == User.STUDENT_LOGIN) {
				teacherList = baseUserDao
						.getStudentUsers((String[]) teacherIdSet
								.toArray(new String[0]));
			} else if (ownerType == User.FAMILY_LOGIN) {
				teacherList = studentFamilyService
						.getFamilies((String[]) teacherIdSet
								.toArray(new String[0]));
			} else {
				teacherList = baseTeacherService.getBaseTeachers(teacherIdSet
						.toArray(new String[] {}));
			}
			for (int i = 0; i < teacherList.size(); i++) {
				UserAccount userAccount;
				if (ownerType == User.STUDENT_LOGIN) {
					User info = (User) teacherList.get(i);
					userAccount = transferToEmployee(info, ownerType);
				} else if (ownerType == User.FAMILY_LOGIN) {
					Family family = (Family) teacherList.get(i);
					userAccount = transferToEmployee(family, ownerType);
				} else {
					BaseTeacher employee = (BaseTeacher) teacherList.get(i);
					userAccount = transferToEmployee(employee, ownerType);
				}
				teacherMap.put(userAccount.getId(), userAccount);
			}
		}

		Map<String, String> regionMap = new HashMap<String, String>();// 行政区划
		Map<String, String> etohSchoolIdMap = new HashMap<String, String>();
		if (unitIdSet.size() > 0) {
			List<Unit> unitList = unitService.getUnits(unitIdSet
					.toArray(new String[0]));
			for (Object object : unitList) {
				Unit unit = (Unit) object;
				regionMap.put(unit.getId(), unit.getRegion());
				etohSchoolIdMap.put(unit.getId(), unit.getEtohSchoolId());
			}
		}

		List<Map> list = new ArrayList<Map>();
		list.add(teacherMap);
		list.add(regionMap);
		list.add(etohSchoolIdMap);
		return list;
	}

	private String getAccountPassword(User user) {
		return user.findEncodePassword();
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.userService = baseUserService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setStudentFamilyService(
			StudentFamilyService studentFamilyService) {
		this.studentFamilyService = studentFamilyService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public void setBaseUserDao(BaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

}
