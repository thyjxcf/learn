package net.zdsoft.eis.base.data.service.impl;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.MailServerService;
import net.zdsoft.eis.base.common.service.OrderService;
import net.zdsoft.eis.base.common.service.StudentService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.impl.UserServiceImpl;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.constant.UCenterConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.dao.BaseTeacherDao;
import net.zdsoft.eis.base.data.dao.BaseUserDao;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.service.BaseStudentFamilyService;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.data.service.PassportAccountService;
import net.zdsoft.eis.base.event.UserEvent;
import net.zdsoft.eis.base.event.impl.UserEventDispatcher;
import net.zdsoft.eis.base.subsystemcall.service.StusysSubsystemService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.eis.system.frame.web.ucenter.UcWebServiceClient;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.keelcnet.util.GeneralUtil;
import net.zdsoft.leadin.exception.BusinessErrorException;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.exception.SendMailException;
import net.zdsoft.leadin.mail.Email;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.leadin.util.UUIDGenerator;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;
import net.zdsoft.smsplatform.client.ZDConstant;
import net.zdsoft.smsplatform.client.ZDUtils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atlassian.mail.MailException;
import com.atlassian.mail.server.SMTPMailServer;

/**
 * @author yanb
 * 
 */
public class BaseUserServiceImpl extends UserServiceImpl implements
		BaseUserService {
	private static Logger log = LoggerFactory
			.getLogger(BaseUserServiceImpl.class);

	private UnitIniService unitIniService;
	private UserRoleRelationService userRoleRelationService;
	private PassportAccountService passportAccountService;
	private MailServerService mailServerService;
	private SystemIniService systemIniService;
	private StudentService studentService;
	private StusysSubsystemService stusysSubsystemService;
	private UnitService unitService;
	private RoleService roleService;
	private BaseUserDao baseUserDao;
	private BaseTeacherDao baseTeacherDao;
	private BaseStudentFamilyService baseStudentFamilyService;
	private BaseTeacherService baseTeacherService;
	private UserEventDispatcher userEventDispatcher;
	private SystemVersionService systemVersionService;
	private OrderService orderService;
	
	public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void setSystemVersionService(
			SystemVersionService systemVersionService) {
		this.systemVersionService = systemVersionService;
	}

	public void setMailServerService(MailServerService mailServerService) {
		this.mailServerService = mailServerService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setStusysSubsystemService(
			StusysSubsystemService stusysSubsystemService) {
		this.stusysSubsystemService = stusysSubsystemService;
	}

	public void setBaseUserDao(BaseUserDao baseUserDao) {
		this.baseUserDao = baseUserDao;
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setPassportAccountService(
			PassportAccountService passportAccountService) {
		this.passportAccountService = passportAccountService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public void setBaseStudentFamilyService(
			BaseStudentFamilyService baseStudentFamilyService) {
		this.baseStudentFamilyService = baseStudentFamilyService;
	}

	public void setUserEventDispatcher(UserEventDispatcher userEventDispatcher) {
		this.userEventDispatcher = userEventDispatcher;
	}

	// ================以上为set方法====================================

	public boolean saveUser(User user, Integer userMark)
			throws BusinessErrorException {
		boolean result = false;
		if (user == null || user.getTeacherid() == null)
			return result;
		User existsNameUser = getUserByUserName(user.getName());
		if (existsNameUser == null) {
			if (user.getTeacherid() != null
					&& user.getTeacherid().trim().length() > 0) {
				BaseTeacher tch = baseTeacherService.getBaseTeacher(user
						.getTeacherid());
				if (tch == null) {
					throw new ItemExistsException("该职工暂时不可用!");
				}
				user.setEmail(tch.getEmail());
			} else {
				user.setTeacherid(null);
			}
			user.setMark(userMark);
			user = unitIniService.getUserPass(user);
			user = initUser(user);
			baseUserDao.insertUser(user);
			user.setId(user.getId());
			// 设置新增用户为默认角色
			Role role = roleService.getRole(user.getUnitid(),
					Role.ROLE_IDENTIFIER_DEFAULT);
			if (role != null) {
				// 得到此新增用户的id
				User userTemp = getUserByUserName(user.getName());
				String[] userids = new String[] { userTemp.getId() };
				// 得到用户所在单位的默认角色id
				String[] roleids = new String[] { role.getId() };
				userRoleRelationService.saveUserRolesFromUser(userids, roleids, user.getUnitid());
			}
			if (user.getEventSourceValue() == EventSourceType.LOCAL.getValue()) {
				// 数字校园的话，新增用户同时，更新sequence字段
				passportAccountService.addAccount(user);
			}
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public void addUsersFromMq(List<User> userList) {
	    // 不用再从passport取密码，现在mquser中已增加密码，且已加密
        // passportAccountService.setPasswordFromPassport(userList);
		Map<String, List<String>> userMap = new HashMap<String, List<String>>();
		Set<String> unitIdSet = new HashSet<String>();
		for (int i = 0; i < userList.size(); i++) {
			User u = userList.get(i);
			unitIdSet.add(u.getUnitid());
			List<String> userIdList = userMap.get(u.getUnitid()) ;
			if (null != userIdList) {
				userIdList.add(u.getId());
			} else {
				userIdList = new ArrayList<String>();
				userIdList.add(u.getId());
			}
			userMap.put(u.getUnitid(), userIdList);
		}

		baseUserDao.insertUsers(userList.toArray(new User[0]));
		for (String unitId : unitIdSet) {
			// 设置新增用户为默认角色
			Role role = roleService.getRole(unitId,
					Role.ROLE_IDENTIFIER_DEFAULT);
			if (role != null) {
				// 得到此新增用户的id
				String[] userids = userMap.get(unitId).toArray(new String[0]);
				// 得到用户所在单位的默认角色id
				String[] roleids = new String[] { role.getId() };
				userRoleRelationService.saveUserRolesFromUser(userids, roleids, unitId);
			}
		}
	}
	
	public void updateUsersFromMq(List<User> userList) {
	    // 不用再从passport取密码，现在mquser中已增加密码，且已加密
        // passportAccountService.setPasswordFromPassport(userList);
		baseUserDao.updateUsers(userList.toArray(new User[0]));
	}

	private User initUser(User user) {
		if (StringUtils.isBlank(user.getRegion())) {
			Unit unit = unitService.getUnit(user.getUnitid());
			if (unit != null) {
				user.setRegion(unit.getRegion());
			}
		}
		if (StringUtils.isBlank(user.getAccountId())) {
			user.setAccountId(UUIDGenerator.getUUID());
		}
		if (user.getOwnerType() == null)
			user.setOwnerType(User.TEACHER_LOGIN);
		if (user.getCreationTime() == null)
			user.setCreationTime(new Date());
		return user;
	}

	public void saveUserByOtherObject(User user, boolean saveWithoutPassport)
			throws Exception {
		user = initUser(user);
		baseUserDao.insertUser(user);
		if (!saveWithoutPassport) {
			passportAccountService.addAccount(user);
		}
	}

	public void saveUserByTeacher(User user, BaseTeacher teacher)
			throws Exception {
		if (null != user) {
			user = initUser(user);
			baseUserDao.insertUser(user);

			passportAccountService.addAccount(user, teacher);
		}
	}

	public boolean saveUserRegister(User user, Integer userMark,
			String contextURL) throws Exception {
		if (user == null) {
			return false;
		}
		user.setOrderid(getAvailableOrder(user.getUnitid()));

		SystemIni systemIniDto = systemIniService
				.getSystemIni(BasedataConstants.FPF_USER_REGISTER_ACTIVE);
		int registerActive = -1;
		if (systemIniDto != null) {
			if (StringUtils.isNotBlank(systemIniDto.getNowValue())) {
				registerActive = Integer.valueOf(systemIniDto.getNowValue());
			}
		}
		if (registerActive == BasedataConstants.FPF_USER_REGISTER_ACTIVE_IMM) {
			userMark = User.USER_MARK_NORMAL;
		}

		boolean result = true;//saveUser(user, userMark);
		if (result) {

			if (systemIniDto != null) {

				switch (registerActive) {
				case BasedataConstants.FPF_USER_REGISTER_ACTIVE_ADMIN:
					break;
				case BasedataConstants.FPF_USER_REGISTER_ACTIVE_EMAIL:
					this.sendEmail(user, contextURL);
					break;
				case BasedataConstants.FPF_USER_REGISTER_ACTIVE_IMM:
					break;
				default:
					break;
				}
			}

			// passport不用再写，saveUser中已通知
		}
		return result;
	}

	/**
	 * 发送邮件
	 * 
	 * @param contextURL
	 * 
	 * @throws MailException
	 * 
	 */
	private void sendEmail(User userDto, String contextURL) {
		try {
			String productName = systemVersionService.getSystemVersion()
					.getName();
			if (userDto == null || userDto.getEmail() == null
					|| userDto.getEmail().trim().length() == 0) {
				return;
			}
			Email email = new Email(userDto.getEmail());

			email.setEncoding(GeneralUtil.getCharacterEncoding());
			email.setSubject("[" + userDto.getName() + "]账号激活邮件");
			email.setMimeType("text/html");
			email.setFromName(productName);

			StringBuffer body = new StringBuffer();
			body.append("您好：<br>");
			body.append("   欢迎您使用" + productName + "<br>");
			body.append("   恭喜您注册账号[" + userDto.getName() + "]成功！<br>");
			body.append("   该账号尚未激活，仍不能正常使用，请点击下面链接激活该账号！<br>");
			StringBuffer link = new StringBuffer(contextURL
					+ "/system/userActivation.action?guid=" + userDto.getId());
			body.append("<a href='" + link + "' target='_blank'>" + link
					+ "</a>");
			email.setBody(body.toString());

			email.setFrom(mailServerService.getMailServerInfo()
					.getDisplayaddress());
			if (email.getFrom() == null || email.getFrom().trim().length() == 0) {
				email.setFrom("mail_server@zdsoft.net");
			}

			SMTPMailServer mailServer;
			mailServer = mailServerService.getSMTPMailServer();
			mailServer.setPrefix("统一平台提醒您：");
			mailServer.send(email);
		} catch (Exception e) {
			throw new SendMailException("发送邮件失败：" + e.getMessage());
		}

	}

	public void saveUsers(User[] users) throws Exception {
		for (User user : users) {
			// 现在accountid就用用户id（原来 如果部署了数字校园，则new一个id）
			user.setAccountId(user.getId());
		}

		baseUserDao.insertUsers(users);

		passportAccountService.addAccounts(users);
	}

	public String[] deleteUsers(String[] ids, EventSourceType eventSource)
			throws BusinessErrorException {
		if (ids.length <= 0)
			return null;

		List<User> list = getUsers(ids);
		Set<String> accountIdSet = new HashSet<String>();
		for (User u : list) {
			String accountId = u.getAccountId();
			if (StringUtils.isNotBlank(accountId))
				accountIdSet.add(u.getAccountId());
		}

		if (ids.length > 0) {
			userRoleRelationService.deleteUserRole(ids);
		}
		baseUserDao.deleteUsers(ids, eventSource);

		UserEvent userEvent = new UserEvent(ids, UserEvent.USER_DELETE);
		userEventDispatcher.dispatchEvent(userEvent);
		String[] ret = null;
		if (eventSource.getValue() == EventSourceType.LOCAL.getValue())
			ret = passportAccountService.removeAccounts(accountIdSet
					.toArray(new String[0]));
		return ret;
	}

	public Set<String> deleteUsersByOwner(int ownerType,String[] ownerIds) throws BusinessErrorException {
	    Set<String> noDeleteOwnerIdSet = new HashSet<String>();// 存在定购关系无法删除的owner
	    
        List<User> users = getUsersByOwner(ownerType,ownerIds);
        if (null == users || users.size() == 0)
            return noDeleteOwnerIdSet;

        String[] userIds = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userIds[i] = users.get(i).getId();
        }
        
        // 取定购关系
        Map<String, Boolean> orderMap = orderService.getOrdersByUser(userIds);

        Set<String> deleteUserIdSet = new HashSet<String>();// 可以删除的用户id        
        for (User user : users) {
            String userId = user.getId();
            Boolean isOrder = orderMap.get(userId);
            if (isOrder == null || isOrder == false) {
                deleteUserIdSet.add(userId);
            } else {
                noDeleteOwnerIdSet.add(user.getTeacherid());
            }
        }
        if (deleteUserIdSet.size() > 0) {
            deleteUsers(deleteUserIdSet.toArray(new String[0]), EventSourceType.LOCAL);
        }
        
        return noDeleteOwnerIdSet;       
    }
	
	public void deleteUsers(String[] ids) {
		baseUserDao.deleteUsers(ids);
	}

	public boolean updateUser(User user, boolean saveWithoutPassport)
			throws BusinessErrorException {
		boolean result = false;
		User usero = getUser(user.getId());
		if (usero != null) {
			if (!usero.getName().equals(user.getName())) {// 用户登录名是否更改
				User existsNameUser = getUserByUserName(user.getName());
				if (existsNameUser != null) {
					return result;
				}
			}
//			if (StringUtils.isNotBlank(user.getTeacherid())) {
//				user = updateUserChangeTeacher(user);
//			} else {
//				user.setTeacherid(null);
//			}
			String region = user.getRegion();
			if (StringUtils.isBlank(region)) {
				Unit unit = unitService.getUnit(user.getUnitid());
				if (unit != null) {
					user.setRegion(unit.getRegion());
				}
			}
			if (StringUtils.isBlank((user.getAccountId()))) {
				user.setAccountId(UUIDGenerator.getUUID());
			}
			baseUserDao.updateUser(user);
			if (!saveWithoutPassport) {
				passportAccountService.modifyAccount(user, null);
			}
			result = true;
		}
		return result;
	}

	/**
	 * 更新用户的真实姓名和单位id，emial
	 * 
	 * @author "yangk" Aug 23, 2010 4:08:41 PM
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unused")
	private User updateUserChangeTeacher(User user) {
		if (user.getTeacherid() == null
				|| user.getTeacherid().trim().length() == 0) {
			// userDto.setDeptid(GlobalConstant.VIRTURAL_GROUP_GUID);
			// userDto.setDeptintid(Long
			// .valueOf(GlobalConstant.VIRTURAL_GROUP_LONGID));
			user.setRealname(null);
			user.setEmail(null);
			return user;
		}
		BaseTeacher tch = baseTeacherService
				.getBaseTeacher(user.getTeacherid());

		if (tch != null) {
			user.setUnitid(tch.getUnitid());
			if (user.getRealname() == null || user.getRealname().equals("")) {
				user.setRealname(tch.getName());
			}
			user.setEmail(tch.getEmail());
		}
		return user;
	}

	public boolean updateUser(User user) throws BusinessErrorException {
		return updateUser(user, false);
	}

	public void updateUserByPassport(User user) throws Exception {
		baseUserDao.updateUser(user);

	}

	public void updateUsersAudit(String[] ids) throws Exception {
		List<User> list = getUsers(ids);

		baseUserDao.updateUserMark(ids, User.USER_MARK_NORMAL);
		passportAccountService.modifyAccountsMark(list, User.USER_MARK_NORMAL);
	}

	public void updateSynchronizeUser4Teacher(BaseTeacher teacher)
			throws Exception {
		if (teacher == null) {
			return;
		}
		List<User> list = getUsersByOwner(teacher.getId());
		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		User user = new User();
		User[] users = new User[list.size()];
		for (int i = 0; i < list.size(); i++) {
			user = (User) list.get(i);
			user.setDeptid(teacher.getDeptid());
			user.setUnitid(teacher.getUnitid());
			user.setEmail(teacher.getEmail());
			user.setRealname(teacher.getName());
			user.setChargeNumber(teacher.getChargeNumber());
			users[i] = user;
		}
		baseUserDao.updateUsers(users);
		passportAccountService.modifyAccounts(users, null);

	}

	public void updateUsersLock(String[] ids) throws Exception {
	    List<User> list = getUsers(ids);
		baseUserDao.updateUserMark(ids, User.USER_MARK_LOCK);
		passportAccountService.modifyAccountsMark(list, User.USER_MARK_LOCK);
	}

	public void updateUsersUnlock(String[] ids) throws Exception {
		List<User> list = getUsers(ids);
		baseUserDao.updateUserMark(ids, User.USER_MARK_NORMAL);
		passportAccountService.modifyAccountsMark(list, User.USER_MARK_NORMAL);
	}

	public void updateUserPasswordReset(String[] ids, UnitIni unitIni)
			throws Exception {
		// 1.根据密码默认规则初始化用户密码GlobalConstant
		// 0;// 如不输入密码,则为空
		// 1;// 如不输入密码,则为登录名
		// 2;// 如不输入密码,则为本单位默认密码
		Integer defaultValue = 0;
		if (unitIni.getDefaultValue() != null)
			defaultValue = Integer.parseInt(unitIni.getNowValue());
		if (BaseConstant.PASSWORD_GENERIC_RULE_NULL == defaultValue
				|| defaultValue.equals(BaseConstant.PASSWORD_GENERIC_RULE_NULL)) {
			PWD pwd = new PWD(BaseConstant.PASSWORD_INIT);
			String passwordInit = pwd.encode();
			baseUserDao.updateUserPassword(ids, passwordInit);
			List<User> list = getUsers(ids);
			passportAccountService.modifyAccountsPassword(list,
					BaseConstant.PASSWORD_INIT);
		} else {
			String passwordInit = unitIni.getFlag();
			baseUserDao.updateUserPassword(ids, passwordInit);
			List<User> list = getUsers(ids);

			passportAccountService.modifyAccountsPasswordResetUnification(list,
					passwordInit);
		}
	}

	private Map<String, String> getUserNameMapByIds(String[] ids) {
		List<User> list = getUsers(ids);

		User user;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			user = (User) list.get(i);
			if (user.getName() != null && user.getName().length() >= 4){
			    PWD pwd = new PWD(user.getName());
				map.put(user.getId(), pwd.encode());
			}
		}
		return map;
	}

	public Map<String, String> updateUserPasswordResetUsername(String[] ids)
			throws Exception {
		// 1;// 如不输入密码,则为登录名
		Map<String, String> map = getUserNameMapByIds(ids);
		baseUserDao.updateUserPassword(map);
		passportAccountService.modifyAccountsPasswordResetUsername(map);
		return map;
	}

	public void updateUserAuditByTeacher(String teacherId) throws Exception {
		baseUserDao.updateUserMarkByOwner(new String[] { teacherId },
				User.USER_MARK_NORMAL);

		List<User> list = getUsersByOwner(teacherId);
		passportAccountService.modifyAccountsMark(list, User.USER_MARK_NORMAL);
	}

	public void updateUserDimissionLockByTeacher(String[] teacherIds) throws Exception {
        baseUserDao.updateUserMarkByOwner(teacherIds, User.USER_MARK_OUT);
        List<User> userList = getUsersByOwner(User.TEACHER_LOGIN, teacherIds);

        passportAccountService.modifyAccountsMark(userList, User.USER_MARK_OUT);
    }

	public void updateUserMarkByOwner(String[] ownerIds, int mark) throws BusinessErrorException {
        baseUserDao.updateUserMarkByOwner(ownerIds, mark);
        List<User> list = getUsersByOwner(User.TEACHER_LOGIN, ownerIds);
        passportAccountService.modifyAccountsMark(list, mark);
    }
	
	public void updateUserActivation(String userGuid) throws Exception {
		User user = getUser(userGuid);
		if (user == null) {
			throw new ItemExistsException(userGuid + "用户不存在");
		}
		if (user.getMark() == User.USER_MARK_NOT_APPLY) {
			user.setMark(User.USER_MARK_NORMAL);
			// configUserPassword(user);
			baseUserDao.updateUser(user);
		} else {
			throw new ItemExistsException(userGuid + "用户状态不正常或已激活");
		}
		passportAccountService.modifyAccount(user, new String[] { "mark" });
	}

	public void updateUserLockInUnit(String unitId) {
		baseUserDao.updateUserLock(unitId);
	}

	public void updateUsers(User[] users, String[] unitIds) {
		baseUserDao.updateUsers(users);
		passportAccountService.modifyAccounts(users, null);
	}
	
	public void updateUserWithoutPassport(User user) {
		User u = getUserByUserName(user.getName());
		u.setChargeNumber(user.getChargeNumber());
		baseUserDao.updateUser(u);
	}

	public boolean updateUserRealInfo(User user, boolean coPassportInfo) {
		Integer ownerType = user.getOwnerType();
		if (ownerType == null)
			ownerType = User.TEACHER_LOGIN;
		String teacherId = user.getTeacherid();
		if (StringUtils.isBlank(teacherId))
			return false;

		if (User.TEACHER_LOGIN == ownerType) {
			BaseTeacher info = baseTeacherService.getBaseTeacher(teacherId);
			if (info == null) {
				return false;
			}
			info.setEmail(user.getEmail());
			info.setPersonTel(user.getMobile());
			info.setLinkPhone(user.getHomePhone());
			info.setOfficeTel(user.getOfficePhone());
			info.setHomepage(user.getHomepage());
			baseTeacherService.updateTeacher(info);
		} else if (User.STUDENT_LOGIN == ownerType) {
			Student info = (Student) studentService.getStudent(teacherId);
			if (info == null) {
				return false;
			}
			if (null != stusysSubsystemService) {
				stusysSubsystemService.updateStudentSimple(info);
			}
		} else if (User.FAMILY_LOGIN == ownerType) {
			Family info = baseStudentFamilyService.getFamily(teacherId);
			if (info == null) {
				return false;
			}
			info.setEmail(user.getEmail());
			info.setMobilePhone(user.getMobile());
			info.setLinkPhone(user.getHomePhone());
			info.setOfficeTel(user.getOfficePhone());
			info.setHomepage(user.getHomepage());
			baseStudentFamilyService.updateFamily(info);
		}
		if (StringUtils.isNotBlank(user.getId())) {
			User oUser = getUser(user.getId());
			if (oUser != null) {
				oUser.setEmail(user.getEmail());
				baseUserDao.updateUser(oUser);
			}
		}

		if (coPassportInfo) {
			passportAccountService.modifyAccount(user, null);
		}
		return true;
	}

	public void updatePassword(String id, String oldPassword,
			String newPassword, boolean coPassportInfo) throws Exception {
		User user = getUser(id);
		if (user == null) {
			throw new Exception("找不到用户信息！");
		}
		String password = user.findClearPassword();
		if (!oldPassword.equals(password)) {
			throw new Exception("输入的原密码错误！");
		}
		user.setPassword(newPassword);
		baseUserDao.updateUser(user);
		ucPwdModify(user.getOwnerType(), user.getName(), oldPassword, newPassword);
		if (coPassportInfo) {
			passportAccountService.modifyAccount(user,
					new String[] { "password" });
		}
	}
	
	/**
	 * uc修改用户密码
	 * @param ownerType
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	private void ucPwdModify(int ownerType, String username, String oldPassword, String newPassword){
		// 是否连接uc
		boolean ucSwitch = systemIniService.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH);
		if(!ucSwitch ||
				(ucSwitch &&
				 !StringUtils.contains(systemIniService.getValue(UCenterConstant.SYSTEM_UCENTER_OWNERTYPE), ownerType + ""))){
			return ;
		}
		//TODO
//		username = "bj_qjc134522";
//		oldPassword = "134522";
//		newPassword = "134522";
		
		String ucHost;
		String ucToken;
		Map<String, String> pas;
		String res;
		String acToken;
		SAXReader saxreader;
		Document doc;
		Element re;
		try {
			ucHost = systemIniService.getValue(UCenterConstant.SYSTEM_UCENTER_URL);
			ucToken = systemIniService.getValue(UCenterConstant.SYSTEM_UCENTER_UCTOKEN);
			String actUrl = ucHost + UCenterConstant.GET_ACCESSTOKEN_PATH;
			pas = new HashMap<String, String>();
			pas.put("Token", ucToken);
			pas.put("Username", username);
			res = ZDUtils.readContent(actUrl, ZDConstant.METHOD_POST, pas, null, "UTF-8");
			acToken = "";
			saxreader = new SAXReader();  
			doc = saxreader.read(new ByteArrayInputStream(res.getBytes("UTF-8")));
			re = doc.getRootElement();
			if("string".equals(re.getName())){
				acToken = re.getText();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("uc对接密码修改失败：获取用户的accessToken失败！");
		}
		if(StringUtils.isEmpty(acToken)){
			throw new RuntimeException("uc对接密码修改失败：无法获取用户的accessToken！");
		}
		
		String result;
		try {
			pas.clear();
			pas.put("Token", ucToken);
			pas.put("AccessToken", acToken);
			pas.put("PassWord", oldPassword);
			pas.put("NewPassWord", PWD.decode(newPassword));
			String cpsUrl = ucHost + UCenterConstant.CHANGE_PASSWORD_PATH;
			res = ZDUtils.readContent(cpsUrl, ZDConstant.METHOD_POST, pas, null, "UTF-8");
			doc = saxreader.read(new ByteArrayInputStream(res.getBytes("UTF-8")));
			re = doc.getRootElement();
			result = "";
			if("string".equals(re.getName())){
				result = re.getText();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("uc对接密码修改失败");
		}
		if(!UCenterConstant.RESULT_CODE_SUC.equals(result)){
			throw new RuntimeException("uc对接密码修改失败：" 
					+ UCenterConstant.getErrorResultMsg(result) + "！");
		}
	}

	public void updateUsersByOwner(int ownerType,String[] ownerIds, String[] schoolIds) {
        List<User> users = new ArrayList<User>();
        Map<String, User> userMap = getUserMapByOwner(ownerType,ownerIds);
        for (int i = 0; i < ownerIds.length; i++) {
            String ownerId = ownerIds[i];
            String schoolId = schoolIds[i];
            User user = userMap.get(ownerId);
            if (null == user)
                continue;

            user.setUnitid(schoolId);
            users.add(user);
        }

        if (users.size() == 0)
            return;

        baseUserDao.updateUsers(users.toArray(new User[0]));
        passportAccountService.modifyAccountsSchoolId(users);
    }
	
	public static void main(String[] args) {
		//TODO
		String actUrl = "http://sjpt.bjqjyj.cn:8081/" + UCenterConstant.CHANGE_PASSWORD_PATH;
		Map<String, String> pas = new HashMap<String, String>();
		pas.put("Token", "70AF0CE93F8ADA82F097775837920B580B83EB8B7CA988739074805B1FDBB37EA28A1047DA53B5C46FBAA5BE640D697E4774477D60DCF55A9A30FA6AE292B160");
//		pas.put("Username", "bj_qjc134522");
		pas.put("AccessToken", "A1CAFA819687ED21AA5B19FE86E5F98010AA00CDA9093DF7384BA67B169E018AEBADB6BFA74EE8950F3E003C8C29A81B");
		pas.put("PassWord", "134522");
		pas.put("NewPassWord", "134522");
		try {
			String res = ZDUtils.readContent(actUrl, ZDConstant.METHOD_POST, pas, null, "UTF-8");
			System.out.println(res);
			SAXReader saxreader = new SAXReader();  
			Document doc = saxreader.read(new ByteArrayInputStream(res.getBytes("UTF-8")));
			Element re = doc.getRootElement();
			if("string".equals(re.getName())){
				System.out.println("accesstoken="+re.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExistsEmail(String email) {
		return baseUserDao.isExistsEmail(email);
	}

	public Map<String, Integer> getCountsByUserNames(String[] userNames) {
		return baseUserDao.getUserNameCount(userNames);
	}

	public int getUserCount(String unitId, int ownerType, int userType) {
		return baseUserDao.getUserCount(unitId, ownerType, userType);
	}

	public Map<String, User> getUnitAdmins(String[] unitIds) {
		Map<String, User> map = new HashMap<String, User>();
		List<User> list = baseUserDao.getAdminUsers(unitIds);
		for (User user : list) {
			if (!map.containsKey(user.getUnitid())) {
				map.put(user.getUnitid(), user);
			}
		}
		return map;
	}

	public String getUserNameByIds(String[] ids) {
		List<User> list = getUsers(ids);

		User user;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < list.size(); i++) {
			user = (User) list.get(i);
			sb.append(user.getName());
			if (i != list.size() - 1) {
				sb.append("、");
			}
		}
		return sb.toString();
	}

	public String getAdviceUserName4Register(String userName) {
		if (userName == null) {
			return null;
		}
		String symbol = "_";
		Integer sysCount = baseUserDao.getUserNameCount(userName);// 数据库中该用户名存在数量
		if (sysCount == 0) {
			return userName;
		}

		StringBuffer sb = null;
		int addon = 1;
		while (sysCount > 0) {
			sb = new StringBuffer();
			sb.append(userName).append(symbol).append(addon);
			sysCount = baseUserDao.getUserNameCount(sb.toString());
			addon++;
		}
		return sb.toString();
	}

	public List<User> getUserSearchName(String userName, String unitId,
			boolean restrict) {
		List<User> list = null;
		if (restrict) {
			list = baseUserDao.getUsersFaintness(userName, unitId);
		} else {
			list = baseUserDao.getUsersFaintness(userName);
		}
		return list;
	}

	public List<User> getUserByNameRealName(String unitId, int ownerType, String userTypes,
            String userName, String realName, Pagination page) {
        return baseUserDao.getUsersFaintness(unitId, ownerType, userTypes, userName, realName, page);
    }

	public List<String> initAccountId() {
		// 得到accountId为null的用户列表
		List<User> userList = baseUserDao.getUsersWithAccountNull();

		// 如果没有accountId为null的用户
		if (userList == null || userList.isEmpty())
			return new ArrayList<String>();

		// 得到id的列表，在数字校园1.0中，用户的id与passport中的account_id相同
		List<String> idList = new ArrayList<String>();
		for (User user : userList) {
			String userId = user.getId();
			idList.add(userId);
		}

		Account[] accountList = null;
		try {
			// 得到passport上的account信息列表
			accountList = PassportClient.getInstance().queryAccounts(
					idList.toArray(new String[0]));
		} catch (PassportException e) {
			throw new RuntimeException(e);
		}

		// 如果从passport上取得的Account信息为空，则不做任何操作
		if (accountList == null || accountList.length == 0) {
			log.debug("下面ID的用户在Passport中没有相应的Account信息:"
					+ StringUtils.join(idList.iterator(), ","));
			return idList;
		}

		// 得到以accountId为key,Account为value的Map
		Map<String, Account> accountMap = new HashMap<String, Account>();
		for (Account account : accountList) {
			if (account != null)
				accountMap.put(account.getId(), account);
		}

		// 在passport上没有Account信息的用户ID列表
		List<String> idsWithNoAccount = new ArrayList<String>();

		for (User user : userList) {
			Account account = accountMap.get(user.getId());
			if (account == null) {// 如果该用户没有对应的Account信息
				idsWithNoAccount.add(user.getId());
				continue;
			}

			// 设置accountId
			// user.setAccountId(account.getId().toUpperCase());
			user.setAccountId(account.getId());

			// 设置sequence
			user.setSequence((long) account.getSequence());

			// 设置regionCode
			user.setRegion(account.getRegionId());

			if (user.getOwnerType() == null) {
				user.setOwnerType(User.TEACHER_LOGIN);
			}
		}

		return idsWithNoAccount;
	}

	public User getUserRealInfoByUserIds(boolean coPassportInfo, String userId) {
		User user = getUser(userId);
		return getUserRealInfo(coPassportInfo, user);
	}

	private User getUserRealInfo(boolean coPassportInfo, User user) {
		Integer ownerType = user.getOwnerType();
		if (ownerType == null)
			ownerType = User.TEACHER_LOGIN;

		if (User.TEACHER_LOGIN == ownerType) {
			BaseTeacher info = baseTeacherService.getBaseTeacher(user
					.getTeacherid());

			user.setSex(info.getSex());
			user.setInteName(info.getName());
			user.setUnitid(info.getUnitid());
			user.setEmail(info.getEmail());
			user.setMobilePhone(info.getPersonTel());
			user.setHomePhone(info.getLinkPhone());
			user.setOfficePhone(info.getOfficeTel());
			user.setHomepage(info.getHomepage());
			user.setBirthday(info.getBirthday());

			Unit unit = unitService.getUnit(info.getUnitid());
			if (unit != null) {
				user.setUnitName(unit.getName());
			}

		} else if (User.STUDENT_LOGIN == ownerType) {
			user = baseUserDao.getStudentUsers(user.getTeacherid()).get(0);

			Unit unit = unitService.getUnit(user.getUnitid());
			if (unit != null) {
				user.setUnitName(unit.getName());
			}

		} else if (User.FAMILY_LOGIN == ownerType) {
			Family info = baseStudentFamilyService.getFamily(user
					.getTeacherid());

			user.setSex(info.getSex());
			user.setInteName(info.getName());
			user.setUnitid(info.getSchoolId());
			user.setEmail(info.getEmail());
			user.setMobilePhone(info.getMobilePhone());
			user.setHomePhone(info.getLinkPhone());
			user.setOfficePhone(info.getOfficeTel());
			user.setHomepage(info.getHomepage());
			user.setBirthday(info.getBirthday());

			Unit unit = unitService.getUnit(info.getSchoolId());
			if (unit != null) {
				user.setUnitName(unit.getName());
			}
		}

		if (coPassportInfo) {
			if (StringUtils.isNotBlank(user.getAccountId())) {
				String accountId = user.getAccountId();
				try {
					Account account = PassportClient.getInstance()
							.queryAccount(accountId);
					user.setMsn(account.getMsn());
					user.setQq(account.getQq());
				} catch (PassportException e) {
					log.error("取passport信息失败，信息：" + e.getMessage());
				}
			}
		}
		return user;
	}

	public Map<String, User> takeUnitAdmins(String[] unitids) {
		return baseUserDao.getUnitAdmins(unitids);
	}

	public List<User> getUsersByUserNames(String[] userNames) {
		return baseUserDao.getUsersByUserNames(userNames);
	}

	// ===================与用户对应的学生、教师、家长信息=============
	public List<User> getStudentUsers(String... studentIds) {
		return baseUserDao.getStudentUsers(studentIds);
	}
	
	@Override
	public void updateUsersPinYin(List<User> users) {
		baseUserDao.updateUsersPinYin(users);
	}

	@Override
	public void updateUsersMobilePhoneByOwnerId(String familyMobPho, String ownerId) {
		baseUserDao.updateUsersMobilePhoneByOwnerId(familyMobPho,ownerId);
	}

    @Override
    public boolean updateUserWithTea(User user, BaseTeacher tea, Student stu, Family fam, boolean withPhone)
            throws BusinessErrorException {
        if (user.getOwnerType() == User.TEACHER_LOGIN && tea != null) {
            baseTeacherDao.updateTeacher(tea);
            if(StringUtils.isNotBlank(tea.getName())) 
                user.setRealname(tea.getName());
            if(StringUtils.isNotBlank(tea.getDeptid()))
                user.setDeptid(tea.getDeptid());
            if(StringUtils.isNotBlank(tea.getSex()))
                user.setSex(tea.getSex());
            if(StringUtils.isNotBlank(tea.getPersonTel()))
                user.setMobilePhone(tea.getPersonTel());
        } else if (user.getOwnerType() == User.STUDENT_LOGIN && stu != null) {
            stusysSubsystemService.updateStudentSimple(stu);
            if(StringUtils.isNotBlank(stu.getStuname())) 
                user.setRealname(stu.getStuname());
            if(StringUtils.isNotBlank(stu.getClassid()))
                user.setDeptid(stu.getClassid());
            if(StringUtils.isNotBlank("" + stu.getSex()))
                user.setSex("" + stu.getSex());
            if(StringUtils.isNotBlank(stu.getMobilePhone()))
                user.setMobilePhone(stu.getMobilePhone());
        } else if (user.getOwnerType() == User.FAMILY_LOGIN && fam != null) {
            baseStudentFamilyService.updateFamily(fam);
            if(StringUtils.isNotBlank(fam.getName())) 
                user.setRealname(fam.getName());
            if(StringUtils.isNotBlank(fam.getSex()))
                user.setSex(fam.getSex());
            if(StringUtils.isNotBlank(fam.getMobilePhone()))
                user.setMobilePhone(fam.getMobilePhone());
        }
        if(withPhone){
        	ucModify(user);
        }
        return this.updateUser(user);
    }
    
    private void ucModify(User user){
    	boolean ucSwitch = systemIniService.getBooleanValue(UCenterConstant.SYSTEM_UCENTER_SWITCH);
    	if(!ucSwitch){
    		return ;
    	}
    	UcWebServiceClient client = UcWebServiceClient.getInstance();
    	client.updateTel(user.getName(), user.getMobilePhone());
    }

    public void setBaseTeacherDao(BaseTeacherDao baseTeacherDao) {
        this.baseTeacherDao = baseTeacherDao;
    }

	@Override
	public Map<String , User> getUserByName(String unitId, int ownerType,
			String userName) {
		// TODO Auto-generated method stub
		return baseUserDao.getUserByName(unitId, ownerType, userName);
	}
	@Override
	public void updateUserMobilePhone(String... ownerIds) {
		baseUserDao.updateUserMobilePhone(ownerIds);
	}

	@Override
	public Set<String> deleteDgUsersByOwner(int ownerType, String[] ownerIds)
			throws BusinessErrorException {
        Set<String> noDeleteOwnerIdSet = new HashSet<String>();// 存在定购关系无法删除的owner
	    
        List<User> users = getUsersByOwner(ownerType,ownerIds);
        if (null == users || users.size() == 0)
            return noDeleteOwnerIdSet;

        String[] userIds = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            userIds[i] = users.get(i).getId();
        }
        
        // 取定购关系
        Map<String, Boolean> orderMap = orderService.getOrdersByUser(userIds);

        Set<String> deleteUserIdSet = new HashSet<String>();// 可以删除的用户id        
        for (User user : users) {
            String userId = user.getId();
            Boolean isOrder = orderMap.get(userId);
            if (isOrder == null || isOrder == false) {
                deleteUserIdSet.add(userId);
            } else {
                noDeleteOwnerIdSet.add(user.getTeacherid());
            }
        }
        if (deleteUserIdSet.size() > 0) {
            //deleteUsers(deleteUserIdSet.toArray(new String[0]), EventSourceType.LOCAL);
        }
        
        return noDeleteOwnerIdSet; 
	}

	@Override
	public int getUserNameMaxCodeByClassCode(String unitId, String classCode) {
		// TODO Auto-generated method stub
		return baseUserDao.getUserNameMaxCodeByClassCode(unitId, classCode);
	}
}
