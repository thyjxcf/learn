package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.Mcode;
import net.zdsoft.eis.base.common.service.McodeService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.PinYinUtil;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.exception.SendMailException;
import net.zdsoft.leadin.util.ExportUtil;
import net.zdsoft.leadin.util.PWD;
import net.zdsoft.passport.entity.Account;
import net.zdsoft.passport.exception.PassportException;
import net.zdsoft.passport.service.client.PassportClient;

import org.apache.commons.collections.CollectionUtils;

import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends PageAction implements ModelDriven<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -847509578439644351L;
	private String passwordViewable = BaseConstant.PASSWORD_VIEWABLE;
	private Integer nameMaxLength = User.NAME_MAX_LENGTH;
	private Integer passwordMaxLength = User.PASSWORD_MAX_LENGTH;
	private Integer passwordMinLength = User.PASSWORD_MIN_LENGTH;
	private String modID = "SYS005";
//	private String userNameFieldTip = User.NAME_ALERT;
//	private String userPasswordFieldTip = User.PASSWORD_ALERT;
	private Integer userstatus_natural = User.USER_MARK_NORMAL;
	private Integer userstatus_noaudit = User.USER_MARK_NOT_APPLY;
	private String userMarkMcode = "DM-YHZT";// 用户状态微代码
	private Integer userRealNameLength = User.REALNAME_LENGTH;

	private String password_init = BaseConstant.PASSWORD_INIT;

	// 单位密码生成规则
	private Integer PASSWORD_GENERIC_NULL_RULE = -1;// 未设置单位密码规则
	private Integer PASSWORD_GENERIC_RULE_NULL = BaseConstant.PASSWORD_GENERIC_RULE_NULL;
	private Integer PASSWORD_GENERIC_RULE_NAME = BaseConstant.PASSWORD_GENERIC_RULE_NAME;
	private Integer PASSWORD_GENERIC_RULE_UNIONIZE = BaseConstant.PASSWORD_GENERIC_RULE_UNIONIZE;

	// 自注册用户激活方式
	private Integer FPF_USER_REGISTER_ACTIVE_ADMIN = BasedataConstants.FPF_USER_REGISTER_ACTIVE_ADMIN;
	private Integer FPF_USER_REGISTER_ACTIVE_EMAIL = BasedataConstants.FPF_USER_REGISTER_ACTIVE_EMAIL;
	private Integer FPF_USER_REGISTER_ACTIVE_IMM = BasedataConstants.FPF_USER_REGISTER_ACTIVE_IMM;

	public User user = new User();
	private BaseUnit unit;
	public List<User> userList;
	private List<BaseTeacher> teacherList;
	private List<Dept> deptList;
	private List<Role> roleList;

	private BaseUnitService baseUnitService;
	private RoleService roleService;
	private BaseTeacherService baseTeacherService;
	private UserRoleRelationService userRoleRelationService;
	private McodeService mcodeService;
	private UnitIniService unitIniService;
	private SystemIniService systemIniService;
	private BaseUserService baseUserService;
	private SystemDeployService systemDeployService;
	private UserService userService;

	private LoginInfo loginInfo;
	private String[] roleids;
	private String[] userids;
	private UnitIni unitIni;
	private Integer activationResult;
	private String activationMessage;

	private String xtreeScript;
	private String treeName;
	private String searchName;

	private String queryUserName;
	private String queryUserRlName;
	private String unitId;

	private String userId;

	private Account account;

    private String defaultRoleId;
    private String new_password;
    private String new_password_c;
    private String old_password;
    private String pwdStart;
    
    //用于页面接受strs字符
    private String arrIdsStr;
    
	public String getArrIdsStr() {
		return arrIdsStr;
	}

	public void setArrIdsStr(String arrIdsStr) {
		this.arrIdsStr = arrIdsStr;
	}

	public final String getSearchName() {
		return searchName;
	}

	public final void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setRoleids(String[] roleids) {
		this.roleids = roleids;
	}

	private String getUserAdminUrl() {
		return "userAdmin.action?moduleID=" + this.getModuleID();
	}

	/*
	 * public String getPersonalInformation(){ userDto =
	 * userService.getUserDtoByGuid(userId); // if (userDto == null){ // userDto =
	 * new UserDto(); // } // PassportAccountService passportAccountService =
	 * (PassportAccountService)ContainerManager.getComponent("passportAccountService"); //
	 * User user = new User(); // UserDto.toEntity(userDto, user); // account =
	 * passportAccountService.toAccount(user); try { account =
	 * PassportClient.queryAccount(userDto.getAccountId()); } catch
	 * (PassportException e) { e.printStackTrace(); } if (account.getBirthday() ==
	 * null) account.setBirthday(new Date());
	 * account.setNickname(userDto.getRealname()); return SUCCESS; }
	 */

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setUserids(String[] userids) {
		this.userids = userids;
	}

	// 替换上面的方法 start 2009-1-8
	public String getPersonalInformation() {
		userId = getLoginInfo().getUser().getId();
		user = baseUserService.getUserRealInfoByUserIds(true, userId);
		if (user != null) {
			String accountId = user.getAccountId();
			try {
				Account account = PassportClient.getInstance().queryAccount(
						accountId);
				if (account != null) {
					user.setQq(account.getQq());
					user.setMsn(account.getMsn());
				}
			} catch (PassportException e) {
				e.printStackTrace();
			}
		} else {
			account = new Account();
			return SUCCESS;
		}
		return SUCCESS;
	}

	// 新增内容 start 2009-1-8
	@Deprecated
	public Reply savePersonalInformation(String mobile, String homePhone,
			String officePhone, String homepage, String email, String msn,
			String qq) {
		Reply reply = new Reply();
		User user = baseUserService.getUser(getLoginInfo().getUser().getId());
		if (user == null) {
			reply.addActionError("找不到该用户的基本信息！");
			return reply;
		}
		user.setMobile(mobile);
		user.setHomePhone(homePhone);
		user.setOfficePhone(officePhone);
		user.setEmail(email);
		user.setHomepage(homepage);
		user.setQq(qq);
		user.setMsn(msn);
		boolean ret = baseUserService.updateUserRealInfo(user, true);
		if (ret) {
			reply.addActionMessage("个人信息保存成功！");
		} else {
			reply.addActionError("个人信息保存失败！");
		}
		return reply;
	}

	public Reply updateUserPassword(String oldPassword, String newPassword) {
		Reply reply = new Reply();
		try {
			baseUserService.updatePassword(getLoginInfo().getUser().getId(),
					oldPassword, newPassword, true);
		} catch (Exception e) {
			reply.addActionError(e.getMessage());
			return reply;
		}
		reply.addActionMessage("密码修改成功！");
		return reply;
	}
	
	 /**
     * 密码修改
     * 
     * @return
     */
    public String personal() {
        // 取默认的角色
        defaultRoleId = String.valueOf(userRoleRelationService.getDefaultRoleId(getLoginInfo()
                .getUser().getId()));

        // getLoginUserCount();
        return SUCCESS;
    }

    /**
     * 保存个人设置
     * 
     * @return
     */
    @SuppressWarnings("static-access")
    public String savePersonal() {
        User userDto = baseUserService.getUser(getLoginInfo().getUser().getId());
        String resultStr = "修改个人信息成功！";
        String password = userDto.getPassword();
        PWD pwdUtil = new PWD();
        password = pwdUtil.decode(password);//解密为明文
        if (password == null)
            password = "";
        if (!new_password.equals("")) {
            if (!old_password.equals(password)) {
                addActionError("输入的原密码不正确");
                old_password = "";
                return SUCCESS;
            }
            if (!new_password.equals(new_password_c)) {
                addActionError("两次输入的新密码不相同");
                new_password_c = "";
                return SUCCESS;
            }
        }

        if (new_password.equals("")) {
            if (!old_password.equals("") && !old_password.equals(password)) {
                addActionError("输入的原密码不正确");
                old_password = "";
                return SUCCESS;
            }
            if (!new_password.equals(new_password_c)) {
                addActionError("两次输入的新密码不相同");
                new_password_c = "";
                return SUCCESS;
            }

        }

        if (old_password.equals("") && new_password.equals("")) {
            userDto.setPassword(userDto.getPassword());

        }
        else {
            pwdUtil.setPassword(new_password);
            // new_password=pwdUtil.encode();//加密
            userDto.setPassword(new_password);
        }
        try {
            baseUserService.updatePassword(getLoginInfo().getUser().getId(), old_password,
                    new_password, true);
            // 保存默认角色
            if (this.defaultRoleId != null) {
                userRoleRelationService.saveDefaultUserRole(getLoginInfo().getUser().getId(), String
                        .valueOf(this.defaultRoleId));
            }
            addActionMessage(resultStr);
        }
        catch (Exception e) {
            addActionError("修改个人信息出错：" + e.getMessage());
        }

        return SUCCESS;
    }
    
	// end 2009-1-8

	public String doUserList() {
		loginInfo = getLoginInfo();
		if (org.apache.commons.lang.StringUtils.isBlank(queryUserName)
				&& org.apache.commons.lang.StringUtils.isBlank(queryUserRlName)) {

			userList = baseUserService.getUsers(loginInfo.getUnitID(),getPage());
		} else {
			String _queryUserName = queryConvert(queryUserName);
			String _queryUserRlName = queryConvert(queryUserRlName);
			userList = baseUserService.getUserByNameRealName(loginInfo.getUnitID(),
                    User.TEACHER_LOGIN, null, _queryUserName, _queryUserRlName, getPage());
		}
		SystemIni systemIni = systemIniService.getSystemIni("SYSTEM.PWDINIT");//密码初始化按钮显示：1显示，0不显示
		pwdStart = systemIni != null ? systemIni.getNowValue() : "1";
		return SUCCESS;
	}

	// 将oracle中使用的单个和多个模糊查询符号加上转义符
    private String queryConvert(String str) {
        if (str == null || str.equals("")) {
            return str;
        }

        if (str.indexOf("%") != -1) {
            str = str.replace("%", "\\%");
        } 
        if (str.indexOf("_") != -1) {
            str = str.replace("_", "\\_");
        }
        if (str.indexOf("'") != -1) {
            str = str.replace("'", "''");
        }
        return str;
    }


	public String doUserNew() {
		loginInfo = getLoginInfo();
		String unitId = loginInfo.getUser().getUnitid();
		user = baseUserService.getUserNew(unitId);
		// userDto.setGuid(loginInfo.getUser().getGuid());
		teacherList = getFreeTeacher(unitId, user.getId());
		return SUCCESS;
	}

	public String doUserEdit() {
		loginInfo = getLoginInfo();
		user = baseUserService.getUser(user.getId());
		String unitId = user.getUnitid();
		unit = baseUnitService.getBaseUnit(unitId);
		teacherList = getFreeTeacher(unitId, user.getId());

		doUserRoleList();
		return SUCCESS;
	}

	private List<BaseTeacher> getFreeTeacher(String unitId, String userId) {
		if (userId == null)
			userId = "";
		List<BaseTeacher> teacherList = new ArrayList<BaseTeacher>();
		List<BaseTeacher> listOfTeacher = baseTeacherService.getBaseTeachers(unitId);

		List<User> listOfUser = baseUserService.getUsers(unitId);
		String teacherId;
		Map<String, String> mapOfUserTaecherId = new HashMap<String, String>();
		for (User user : listOfUser) {
			teacherId = user.getTeacherid();
			if (null != teacherId && !"".equals(teacherId)) {
				mapOfUserTaecherId.put(teacherId, user.getId());
			}
		}
		for (BaseTeacher tea : listOfTeacher) {
			teacherId = tea.getId();
			if (mapOfUserTaecherId.get(teacherId) == null
					|| userId.equals(mapOfUserTaecherId.get(teacherId))) {
				teacherList.add(tea);
			}
		}
		return teacherList;
	}

	public String doUserRoleList() {
		if (user == null) {
			return ERROR;
		}
		roleList = roleService.getRoleListByUserId(user.getId());
		return SUCCESS;
	}

	public String doUserInsert() {
		// 如果没有指定用户类型，默认是教师
		if (null == user.getOwnerType()) {
			user.setOwnerType(User.TEACHER_LOGIN);
		}

		loginInfo = getLoginInfo();
		String unitId = loginInfo.getUser().getUnitid();
		if (!userValidate(user, true).getOperateSuccess()) {
			return PROMPTMSG;
		}
		
		promptMessageDto = new PromptMessageDto();
		try {
			user.setType(User.TYPE_NORMAL);
			if (user.getTeacherid() == null || user.getTeacherid().equals("")) {
				if (user.getMark() != null && user.getMark() == 1) {
					addFieldError("mark", "没有关联的职工的用户状态不能为正常！");
					teacherList = getFreeTeacher(unitId, user.getId());
					return INPUT;
				}
			}
			// if (!userService.saveUser(userDto,
			// GlobalConstant.USER_STATUS_NATRUAL)) {
			if (!baseUserService.saveUser(user, user.getMark())) {
				addFieldError("name", "该账号已存在！");
				teacherList = getFreeTeacher(unitId, user.getId());
				roleList = roleService.getRoles(unitId);
				return INPUT;
			}

		} catch (Exception e) {
			log.error("add user error!", e);
			addActionError("新增用户出错:" + e.getMessage());
			teacherList = baseTeacherService.getBaseTeachers(unitId);
			roleList = roleService.getRoles(unitId);
			return INPUT;
		}
		promptMessageDto.setPromptMessage("新增" + user.getName() + "用户成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定", getUserAdminUrl() });
		SystemLog.log(modID, "新增" + user.getName() + "用户信息成功！");
		return PROMPTMSG;
	}

	private String returnUpdateError(String unitId, String userId) {
		User user = baseUserService.getUser(userId);
		teacherList = getFreeTeacher(unitId, user.getId());
		unit = baseUnitService.getBaseUnit(unitId);
		doUserRoleList();
		return INPUT;
	}

	public String doUserUpdate() {
		loginInfo = getLoginInfo();
		String unitId = user.getUnitid();
		promptMessageDto=userValidate(user, false);
		if (!promptMessageDto.getOperateSuccess()) {
			return SUCCESS;
		}

		if (unit == null)
			unit = baseUnitService.getBaseUnit(unitId);

		if (user.getMark() == User.USER_MARK_NORMAL)
			if (unit != null) {
				Integer limitTeacher = unit.getLimitTeacher();
				if (limitTeacher != null && limitTeacher != 0) {
					List<User> userList = baseUserService.getUsers(unitId,
							new String[] { String
									.valueOf(User.USER_MARK_NORMAL) });
					if (CollectionUtils.isNotEmpty(userList)) {
						if (userList.size() > limitTeacher) {
							promptMessageDto.setPromptMessage("本单位最多只能开通"
									+ limitTeacher + "个普通教师账号，目前已经存在"
									+ (userList.size()) + "个状态正常的普通账号，不能再增加！");
							promptMessageDto.setOperateSuccess(false);
							SystemLog.log(modID, "修改" + user.getName()
									+ "状态失败！");
							return SUCCESS;
						}
					}
				}
			}

		promptMessageDto = new PromptMessageDto();
		try {
			if (user.getTeacherid() == null || user.getTeacherid().equals("")) {
				if (user.getMark() != null
						&& user.getMark() == User.USER_MARK_NORMAL) {
					promptMessageDto.setPromptMessage("没有关联的职工的用户状态不能为正常！");
					promptMessageDto.setOperateSuccess(false);
					return SUCCESS;
				}
			}
			User _user = baseUserService.getUser(user.getId());
			user.setPassword(_user.getPassword());
			if (!baseUserService.updateUser(user)) {
				promptMessageDto.setPromptMessage("该账号已存在！");
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
		} catch (Exception e) {
			log.error("修改用户信息出错", e);
			promptMessageDto.setPromptMessage("修改用户信息出错:" + e.getMessage());
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "修改用户信息失败！");
			return SUCCESS;
		}
		String promptMessage = "";
		if (user.getId().equals(getLoginInfo().getUser().getId())) {
			promptMessage = "当前登陆用户" + user.getName() + "已成功修改，请重新登陆";
		} else {
			promptMessage = "修改" + user.getName() + "用户成功！";
		}
		promptMessageDto.setPromptMessage(promptMessage);
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "修改" + user.getName() + "用户信息成功！");
		return SUCCESS;
	}

	public String doUserDelete() {
		promptMessageDto = new PromptMessageDto();
		String[] ids = user.getArrayIds();
		loginInfo = getLoginInfo();
		// 控制用户不能删除自己
		for (int i = 0; i < ids.length; i++) {
			if (String.valueOf(loginInfo.getUser().getId()).equals(ids[i])) {
				promptMessageDto.setErrorMessage(loginInfo.getUser().getName()
						+ "用户不能删除自己！");
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.addOperation(new String[] { "确定",
						getUserAdminUrl() });
				return PROMPTMSG;
			}
		}
		StringBuffer userName = new StringBuffer();
		try {
			List<User> userList = baseUserService.getUsers(ids);
			if (userList == null) {
				throw new NullPointerException();
			}

			for (int i = 0; i < userList.size(); i++) {
				userName.append(((User) userList.get(i)).getName());
				if (i != userList.size() - 1) {
					userName.append("、");
				}
			}

			baseUserService.deleteUsers(user.getArrayIds(), EventSourceType.LOCAL);
		} catch (NullPointerException e) {

		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("删除" + userName.toString()
					+ "用户时出错！错误信息：" + e.getMessage());
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "确定",
					getUserAdminUrl() });
			SystemLog.log(modID, "删除" + userName.toString() + "用户信息失败！错误信息："
					+ e.getMessage());
			return PROMPTMSG;
		}
		promptMessageDto.setPromptMessage("删除" + userName.toString() + "用户成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定", getUserAdminUrl() });
		SystemLog.log(modID, "删除" + userName.toString() + "用户信息成功！");
		return PROMPTMSG;
	}

	public String doUserLock() {
		promptMessageDto = new PromptMessageDto();
		String[] ids = user.getArrayIds();
		String userName = "";
		try {
			userName = baseUserService.getUserNameByIds(ids);
			baseUserService.updateUsersLock(ids);
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("锁定" + userName + "用户时出错！");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "锁定" + userName + "用户状态失败！");
			return SUCCESS;
		}
		promptMessageDto.setPromptMessage("锁定" + userName + "用户成功！");
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "锁定" + userName + "用户状态成功！");
		return SUCCESS;
	}

	public String doUserUnlock() {
		promptMessageDto = new PromptMessageDto();
		String userName = "";
		String[] ids = user.getArrayIds();
		String cannotOpeName = "";
		List<String> cannotOpeIds = new ArrayList<String>();
		List<String> canOpeIds = new ArrayList<String>();
		try {
			List<User> listOfUser = baseUserService.getUsers(ids);
			for (User user : listOfUser) {
				if (user.getTeacherid() == null
						|| user.getTeacherid().equals("")) {
					cannotOpeIds.add(String.valueOf(user.getId()));
				} else {
					canOpeIds.add(String.valueOf(user.getId()));
				}
			}
			if (cannotOpeIds.size() > 0)
				cannotOpeName = baseUserService.getUserNameByIds(cannotOpeIds
						.toArray(new String[0]));
			if (canOpeIds.size() > 0) {
				userName = baseUserService.getUserNameByIds(canOpeIds
						.toArray(new String[0]));
				baseUserService.updateUsersUnlock(canOpeIds
						.toArray(new String[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("解锁" + userName + "用户时出错！");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "解锁" + userName + "用户状态失败！");
			return SUCCESS;
		}

		if (!cannotOpeName.equals("")) {
			if (!userName.equals(""))
				promptMessageDto.setPromptMessage("解锁" + userName + "用户成功，其中"
						+ cannotOpeName + "没有关联职工，不能设置为正常！");
			else {
				promptMessageDto.setPromptMessage(cannotOpeName
						+ "没有关联职工，不能设置为正常！");
			}
		} else
		promptMessageDto.setPromptMessage("解锁" + userName + "用户成功！");
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "解锁" + userName + "用户状态成功！");
		return SUCCESS;
	}

	public String doUserAudit() {
		promptMessageDto = new PromptMessageDto();
		String[] ids = user.getArrayIds();
		String userName = "";
		String cannotOpeName = "";
		List<String> cannotOpeIds = new ArrayList<String>();
		List<String> canOpeIds = new ArrayList<String>();
		try {
			List<User> listOfUser = baseUserService.getUsers(ids);
			for (User user : listOfUser) {
				if (user.getTeacherid() == null
						|| user.getTeacherid().equals("")) {
					cannotOpeIds.add(String.valueOf(user.getId()));
				} else {
					canOpeIds.add(String.valueOf(user.getId()));
				}
			}
			if (cannotOpeIds.size() > 0)
				cannotOpeName = baseUserService.getUserNameByIds(cannotOpeIds
						.toArray(new String[0]));
			if (canOpeIds.size() > 0) {
				userName = baseUserService.getUserNameByIds(canOpeIds
						.toArray(new String[0]));
				baseUserService.updateUsersAudit(canOpeIds
						.toArray(new String[0]));
			}
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("审核" + userName + "用户时出错！");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "审核" + userName + "用户状态失败！");
			return SUCCESS;
		}
		if (!cannotOpeName.equals("")) {
			if (!userName.equals(""))
				promptMessageDto.setPromptMessage("审核" + userName + "用户成功，其中"
						+ cannotOpeName + "没有关联职工，不能设置为正常！");
			else {
				promptMessageDto.setPromptMessage(cannotOpeName
						+ "没有关联职工，不能设置为正常！");
			}
		} else
			promptMessageDto.setPromptMessage("审核" + userName + "用户成功！");
		promptMessageDto.setOperateSuccess(true);
		SystemLog.log(modID, "审核" + userName + "用户状态成功！");
		return SUCCESS;
	}

	public String doUserRole() {
		if(!"".equals(arrIdsStr))
			user.setArrayIds(arrIdsStr.split(","));
		loginInfo = getLoginInfo();
		roleList = roleService.getRoles(loginInfo.getUnitID());
		userList = baseUserService.getUsers(user.getArrayIds());
		List<UserRoleRelation> relations = userRoleRelationService
				.getAllUserRoleRelaction(user.getArrayIds());
		Set<String> container = new HashSet<String>();
		for (UserRoleRelation userRole : relations) {
			container.add(userRole.getRoleid());
		}
		Role role = null;
		for (int i = 0; i < roleList.size(); i++) {
			role = (Role) roleList.get(i);
			if (container.contains(role.getId())) {
				role.setChecked(true);
			}
		}
		return SUCCESS;
	}

	public String saveUserRole() {
		promptMessageDto = new PromptMessageDto();
		String userName = "";
		try {
			userName = baseUserService.getUserNameByIds(userids);
			userRoleRelationService.saveUserRolesFromUser(userids, roleids, getLoginInfo().getUnitID());
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setErrorMessage("委派" + userName + "角色时出错！");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "委派" + userName + "用户角色失败！");
			return SUCCESS;
		}
		promptMessageDto.setPromptMessage("委派" + userName + "角色成功！");
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;
	}

	public String userRegisterNew() {
		user = new User();
		Unit unitDto = baseUnitService.getTopEdu();
		user.setUnitid(unitDto.getId());
		user.setUnitName(unitDto.getName());
		return SUCCESS;
	}

	public List<BaseTeacher> getTeacherByUnit(String unitId) {
		List<BaseTeacher> list = baseTeacherService.getBaseTeachers(unitId);
		return list;
	}

	public String userRegister() {
		try {
			if (user.getUnitid() != null) {
				teacherList = baseTeacherService.getBaseTeachers(user.getUnitid());
			}
			//需要调整
			if (!userValidate(user, true).getOperateSuccess()) {
				return  PROMPTMSG;
			}
			if (getRegisterActive().equals(FPF_USER_REGISTER_ACTIVE_EMAIL)) {
				if (user.getEmail() == null
						|| user.getEmail().trim().length() == 0) {
					addFieldError("email", "请填写邮箱地址");
					return INPUT;
				}
			}
			if (user.getEmail() != null && user.getEmail().trim().length() > 0) {
				if (baseUserService.isExistsEmail(user.getEmail())) {
					addFieldError("email", "该邮箱已注册");
					return INPUT;
				}
			}
			user.setType(User.USER_TYPE_COMMON);

			if (!baseUserService.saveUserRegister(user,
					User.USER_MARK_NOT_APPLY, getContextURL())) {
				addFieldError("name", "该账号已存在");
				return INPUT;
			} else {
				SystemLog.log(modID, "新用户" + user.getName() + "注册成功");
				addActionMessage("用户注册成功，请等待管理员审核！");
				return SUCCESS;
			}
		} catch (SendMailException e) {
			e.printStackTrace();
			addActionError(e.getMessage());
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			addActionError("注册用户时出错：" + e.getMessage());
			return INPUT;
		}
	}

	private String getContextURL() {
		try {
			request = getRequest();
			String requestURI = request.getRequestURI();
			String contextPath = request.getContextPath();
			requestURI = requestURI.substring(contextPath.length());
			String requestURL = request.getRequestURL().toString();

			return requestURL.substring(0, requestURL.length()
					- requestURI.length());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String userAccredit() {
		if(!"".equals(arrIdsStr))
			user.setArrayIds(arrIdsStr.split(","));
		userList = baseUserService.getUsers(user.getArrayIds());
		return SUCCESS;
	}

	public User getModel() {
		return this.user;
	}	

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
        this.baseTeacherService = baseTeacherService;
    }

    public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public PromptMessageDto userValidate(User user, boolean isNew) {
		promptMessageDto =new PromptMessageDto();
		if (user == null) {
			promptMessageDto.setOperateSuccess(true);
			return promptMessageDto;
		}

		if (user.getTeacherid() != null
				&& user.getTeacherid().trim().length() > 0) {
		    BaseTeacher teacher = baseTeacherService.getBaseTeacher(user.getTeacherid());
			if (teacher == null) {
				promptMessageDto.setErrorMessage("该职工已删除或者暂时不可用,请重新选择");
				return promptMessageDto;
			}
		}

		if (org.apache.commons.lang.StringUtils.isBlank(user.getName())) {
			promptMessageDto.setErrorMessage("请输入账号");
			return promptMessageDto;
		}
		if (!user.getName().matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_NAME_ALERT));
			return promptMessageDto;
		}

		if (org.apache.commons.lang.StringUtils.isBlank(user.getRealname())) {
			promptMessageDto.setErrorMessage( "请输入用户姓名");
			return promptMessageDto;
		}
		if (StringUtils.getRealLength(user.getRealname()) > User.REALNAME_LENGTH) {
			promptMessageDto.setErrorMessage("请确认用户姓名不超过" + User.REALNAME_LENGTH
					+ "，一个中文算两个字符");
			return promptMessageDto;
		}

		if (user.getPassword() != null && user.getConfirmPassword() != null
				&& user.getPassword().trim().length() > 0
				&& user.getConfirmPassword().trim().length() > 0) {
			if (!user.getPassword().equals(user.getConfirmPassword())) {
				promptMessageDto.setErrorMessage("请确认密码输入一致");
				return promptMessageDto;
			} else if (!user.getPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_EXPRESSION))) {
				promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
				return promptMessageDto;
			}else {
				if(user.findClearPassword().matches(systemIniService.getValue(User.SYSTEM_PASSWORD_STRONG))){
					promptMessageDto.setErrorMessage(systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT));
					return promptMessageDto;
				}
			}
		}

		if (user.getEmail() != null && user.getEmail().trim().length() > 0) {
			if (!Validators.isEmail(user.getEmail())) {
				addFieldError("email", "请输入正确的邮箱地址");
				return promptMessageDto;
			}
		}
		promptMessageDto.setOperateSuccess(true);
		return promptMessageDto;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public List<BaseTeacher> getTeacherList() {
		return teacherList;
	}

	public Object[] getTeacherByDeptId(String deptId) {
		if (org.apache.commons.lang.StringUtils.isBlank(deptId)) {
			return null;
		}
		teacherList = baseTeacherService.getBaseTeachersByDeptId(deptId);
		return teacherList.toArray(new Object[] {});
	}

	public String passwordReset() {
		setPromptMessageDto(new PromptMessageDto());
		String[] ids = user.getArrayIds();
		String userNames = "";
		String notuserNames = "";
		String[] notIds = null;
		try {
			loginInfo = getLoginInfo();
			unitIni = unitIniService.getUnitOption(loginInfo.getUnitID(),
					UnitIni.UNIT_PASSWORD_CONFIG);
			if (unitIni == null) {
				unitIniService.saveUnitPasswordOption(loginInfo.getUnitID());
				unitIni = unitIniService.getUnitOption(loginInfo.getUnitID(),
						UnitIni.UNIT_PASSWORD_CONFIG);

			}
			Integer defaultValue = 0;
			if (unitIni.getDefaultValue() != null)
				defaultValue = Integer.parseInt(unitIni.getNowValue());
			if (defaultValue.equals(PASSWORD_GENERIC_RULE_NAME)
					|| defaultValue.equals(PASSWORD_GENERIC_RULE_NAME)) {
				Map<String, String> map = baseUserService
						.updateUserPasswordResetUsername(ids);
				Set<String> userSet = map.keySet();
				for (String userId : userSet) {
					User user = baseUserService.getUser(userId);
					userNames += "," + user.getName();
				}
				if(org.apache.commons.lang.StringUtils.isNotBlank(userNames)){ 
					userNames = userNames.substring(1);
				}
				int notIdsSize = ids.length - map.size();
				int j = 0;
				if (notIdsSize > 0) {
					notIds = new String[notIdsSize];
					for (int i = 0; i < ids.length; i++) {
						if (!map.containsKey(ids[i])) {
							notIds[j] = ids[i];
							j++;
						}
					}
					notuserNames = baseUserService.getUserNameByIds(notIds);
				}
			} else {
				baseUserService.updateUserPasswordReset(ids, unitIni);
				userNames = baseUserService.getUserNameByIds(ids);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setPromptMessage("初始化用户密码时出错：" + e.getMessage());
			return SUCCESS;
		}
		

		if (notIds != null) {
			promptMessageDto.setPromptMessage("初始化" + userNames
					+ "用户密码成功.  初始化" + notuserNames + "用户密码失败！因其账号少于四位");
		} else {
			promptMessageDto.setPromptMessage("初始化" + userNames + "用户密码成功");
		}
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;
	}

	/**
	 * 验证用户名称的可用性 true存在,false不存在
	 * 
	 * @param userName
	 * @return
	 */
	public String validateUserNameAvaliable(String userName) {
		if (userName == null) {
			return "请输入账号";
		}
		if (!userName.matches(systemIniService.getValue(User.SYSTEM_NAME_EXPRESSION))) {
			return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
		}
		if (baseUserService.getUserNameCount(userName) <= 0) {
			return "";
		} else {
			return "该账号已存在";
		}
	}

	/**
	 * 根据登录名，搜索用户
	 * 
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object[] searchUserName(String userName) {
		loginInfo = getLoginInfo();
		if (userName == null) {
			return null;
		}

		// 顶级管理员搜索不限制所在单位
		boolean restrict = !(loginInfo.getUser().getType() == User.USER_TYPE_TOPADMIN);
		userList = baseUserService.getUserSearchName(userName, loginInfo
				.getUnitID(), restrict);
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < userList.size(); i++) {
			user = (User) userList.get(i);
			if (!set.contains(user.getUnitid())) {
				set.add(user.getUnitid());
			}
		}
		Map<String, BaseUnit> map = baseUnitService.getBaseUnitMap(set
				.toArray(new String[] {}));
		for (int i = 0; i < userList.size(); i++) {
			user = (User) userList.get(i);
			unit = map.get(user.getUnitid());
			if (unit != null) {
				user.setUnitName(unit.getName());
			}
		}
		return userList.toArray(new Object[] {});
	}

	public void exportUser() {
		loginInfo = this.getLoginInfo();

		unit = baseUnitService.getBaseUnit(loginInfo.getUnitID());
		userList = baseUserService.getUsers(loginInfo.getUnitID());
		Mcode mcode = mcodeService.getMcode(userMarkMcode);

		// String[] accountids = new String[userDtoList.size()];
		Set<String> accountIdSet = new HashSet<String>();
		for (int i = 0; i < userList.size(); i++) {
			user = (User) userList.get(i);
			user.setMarkName(mcode.get(String.valueOf(user.getMark())));
			user.setPassword(user.findClearPassword());
			if (org.apache.commons.lang.StringUtils.isBlank(user.getAccountId()))
				continue;
			accountIdSet.add(user.getAccountId());
			// accountids[i] = userDto.getAccountId();
		}

		String[] fieldTitles = null;
		String[] propertyNames = null;
		// if (isConnectPassport()) { 账号占不显示
		// fieldTitles = new String[] { "账号", "用户名", "关联职工", "用户状态", "创建日期",
		// "登录密码" };
		// propertyNames = new String[] { "sequence", "name", "realname",
		// "markName",
		// "creationTime", "password" };
		// } else {

		if (isShowPassword()) {
			fieldTitles = new String[] { "账号", "关联职工", "用户状态", "创建日期", "登录密码" };
			propertyNames = new String[] { "name", "realname", "markName", "creationTime", "password" };
		} else {
			fieldTitles = new String[] { "账号", "关联职工", "用户状态", "创建日期"};
			propertyNames = new String[] { "name", "realname", "markName", "creationTime" };
		}
		// }

		Map<String, List<User>> map = new HashMap<String, List<User>>();
		map.put("用户信息", userList);
		String fileName = "UserInformation";
		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, map, fileName);
		// 上面的方法已经调用了response.getOutputStream()方法，不能再进行输出，否则会报java.lang.IllegalStateException
		// return SUCCESS;
	}

	/**
	 * 判断导出是否显示密码
	 *
	 * @return
	 */
	public boolean isShowPassword() {
		boolean isShow = false;
		String value = systemIniService.getValue("SYSTEM.PASSWORD.ISSHOW");
		if ("Y".equals(value)) {
			isShow = true;
		}
		return isShow;
	}
	/**
	 * 用户激活链接
	 * 
	 * @return
	 */
	public String userActivation() {
		try {
			baseUserService.updateUserActivation(user.getId());
			activationResult = 1;
		} catch (ItemExistsException e) {
			activationResult = -1;
			activationMessage = e.getMessage();
		} catch (Exception e) {
			activationResult = -1;
			activationMessage = e.getMessage();
		}
		return SUCCESS;
	}
	
	/**
	 * TODO 手动同步拼音
	 */
	public String synchronousPinYin() {
		try {
			/**
			 * 为所有用户都设置拼音（包括简拼，全拼，混拼，如果这些总长度超过2000，
			 * 那么只设置一种全拼和简拼，姓名也包括在内，方便使用）
			 */
			new Thread(){
				public void run() {
					log.info("It's time to set PinYin Begin----------");
					System.out.println("---------开始转换拼音---------------");
					List<User> userList = userService.getUsersWithOutPinYin();
					if(CollectionUtils.isNotEmpty(userList)){
						for(User user:userList){
							String pinyin = user.getRealname()+","+PinYinUtil.getPinYinSet(user.getRealname());
							if(org.apache.commons.lang.StringUtils.length(pinyin)<=2000){
								user.setPinyinAll(pinyin);
							}else{
								String[] pinyins = PinYinUtil.getPinYin(user.getRealname());
								//只存一种全拼简拼
								user.setPinyinAll(user.getRealname()+","+pinyins[0]+","+pinyins[1]);
							}
						}
						baseUserService.updateUsersPinYin(userList);
					}
					System.out.println("---------拼音转换结束---------------");
					log.info("-----------set PinYin end.");
				};
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
			jsonError = "同步失败";
		}
		return SUCCESS;
	}

	public UnitIni getUnitIniDto() {
		return unitIni;
	}

	public String getPasswordViewable() {
		return passwordViewable;
	}

	public Integer getNameMaxLength() {
		return nameMaxLength;
	}

	public Integer getPasswordMaxLength() {
		return passwordMaxLength;
	}

	public Integer getPasswordMinLength() {
		return passwordMinLength;
	}

	public String getUserNameFieldTip() {
		return systemIniService.getValue(User.SYSTEM_NAME_ALERT);
	}

	public String getUserPasswordFieldTip() {
		return systemIniService.getValue(User.SYSTEM_PASSWORD_ALERT);
	}

	public String getTreeName() {
		return treeName;
	}

	public String getXtreeScript() {
		return xtreeScript;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    public Integer getUserstatus_natural() {
		return userstatus_natural;
	}

	public Integer getUserstatus_noaudit() {
		return userstatus_noaudit;
	}

	public BaseUnit getUnitDto() {
		return unit;
	}

	public void setMcodeService(McodeService mcodeService) {
		this.mcodeService = mcodeService;
	}

	public Integer getPASSWORD_GENERIC_RULE_NAME() {
		return PASSWORD_GENERIC_RULE_NAME;
	}

	public Integer getPASSWORD_GENERIC_RULE_NULL() {
		return PASSWORD_GENERIC_RULE_NULL;
	}

	public Integer getPASSWORD_GENERIC_RULE_UNIONIZE() {
		return PASSWORD_GENERIC_RULE_UNIONIZE;
	}

	public String getPwdGenericRule() {
		if (user == null) {
			return null;
		}
		if (user.getUnitid() == null || user.getUnitid().trim().length() == 0) {
			return null;
		}
		return unitIniService.getUnitOptionValue(user.getUnitid(),
				UnitIni.UNIT_PASSWORD_CONFIG);
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public Integer getRegisterActive() {
		SystemIni systemIni = systemIniService
				.getSystemIni(BasedataConstants.FPF_USER_REGISTER_ACTIVE);
		if (systemIni == null) {
			return null;
		}
		if (org.apache.commons.lang.StringUtils
				.isBlank(systemIni.getNowValue())) {
			return -1;
		}
		return Integer.valueOf(systemIni.getNowValue());
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public Integer getFPF_USER_REGISTER_ACTIVE_ADMIN() {
		return FPF_USER_REGISTER_ACTIVE_ADMIN;
	}

	public Integer getFPF_USER_REGISTER_ACTIVE_EMAIL() {
		return FPF_USER_REGISTER_ACTIVE_EMAIL;
	}

	public Integer getFPF_USER_REGISTER_ACTIVE_IMM() {
		return FPF_USER_REGISTER_ACTIVE_IMM;
	}

	public Integer getActivationResult() {
		return activationResult;
	}

	public String getActivationMessage() {
		return activationMessage;
	}

	public Integer getUserRealNameLength() {
		return userRealNameLength;
	}

	public Integer getPASSWORD_GENERIC_NULL_RULE() {
		return PASSWORD_GENERIC_NULL_RULE;
	}

	public String getPassword_init() {
		return password_init;
	}

	public void setPassword_init(String password_init) {
		this.password_init = password_init;
	}

	public String getQueryUserName() {
		return queryUserName;
	}

	public void setQueryUserName(String queryUserName) {
		this.queryUserName = queryUserName;
	}

	public String getQueryUserRlName() {
		return queryUserRlName;
	}

	public void setQueryUserRlName(String queryUserRlName) {
		this.queryUserRlName = queryUserRlName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<User> getUserList() {
		return userList;
	}

	public BaseUnit getUnit() {
		return unit;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public boolean isConnectPassport() {
		return systemDeployService.isConnectPassport();
	}

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password_c() {
        return new_password_c;
    }

    public void setNew_password_c(String new_password_c) {
        this.new_password_c = new_password_c;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getDefaultRoleId() {
        return defaultRoleId;
    }

    public void setDefaultRoleId(String defaultRoleId) {
        this.defaultRoleId = defaultRoleId;
    }

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getPwdStart() {
		return pwdStart;
	}

	public void setPwdStart(String pwdStart) {
		this.pwdStart = pwdStart;
	}
	
	

}
