package net.zdsoft.eis.remote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.subsystemcall.service.BaseDataSubsystemService;
import net.zdsoft.eis.frame.action.RemoteBaseAction;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.passport.entity.Account;

import org.apache.commons.lang.StringUtils;

public class RemoteAppLoginH5Action extends RemoteBaseAction {

	private static final long serialVersionUID = -3893796034499812413L;

	private ModuleService moduleService;

	private BaseDataSubsystemService baseDataSubsystemService;

	private UserRoleRelationService userRoleRelationService;

	private RolePermService rolePermService;

	private UserService userService;

	private UnitService unitService;
	
	//移动端参数
	private String parm;
	private String username;
	private String pwd;
	
	public void login() throws Exception {
		JSONObject json = new JSONObject();
		boolean permission=true;
		parm = getParamValue("parm");
		try {
			String ispermission = json.getString("permission");
			if(StringUtils.isNotBlank(ispermission) && "false".equals(ispermission)){
				permission=false;
			}
		} catch (Exception e) {
		}
		
		User user = null;
		Account account = null;
		String errorMsg = null;
		if (isEisDeploy()) {
			try {
				user = userService.getUserByUserName(username);
			} catch (Exception e) {
				errorMsg = "取用户信息出错: " + e.getMessage();
			}
		} else {
			account = baseDataSubsystemService.queryAccountByUsername(username);
			if (account != null) {
				String accountId = account.getId();
				user = userService.getUserByAccountId(accountId);
				user.setPassword(account.getPassword());
			}
		}
		String password = null;
		if (null != user) {
			/** password城域库中密码, pwd为用户输入密码 * */
			password = user.findClearPassword();
			if ("".equals(password)) {
				password = null;
			}
		}

		int result;// 1:用户名密码正确；-1：用户名不存在；-2：密码错误；-3:用户状态不正常
		if (null == user || user.getName() == null) {
			result = -1;
		} else if (user.getMark() == null
				|| user.getMark() != User.USER_MARK_NORMAL) {
			result = -3;// 用户状态不正常(如: 未审核，锁定等)
		} else if ((password == null && (StringUtils.isBlank(pwd)))
				|| pwd.equals(password)) {
			result = 1;
		} else {
			result = -2;
		}

		// 用户校验正常情况下还需校验其所属单位信息是否正常
		if (result == 1) {
			Unit unit = unitService.getUnit(user.getUnitid());
			if (unit == null || unit.getIsdeleted()) {
				errorMsg = "用户所属单位信息不存在或已经删除！";
			} else {
				int mark = unit.getMark().intValue();
				if (Unit.UNIT_MARK_NORAML != mark) {
					errorMsg = "用户所属单位信息未审核或已锁定！";
				}
				// 报送单位
				if (null == unit.getUsetype()) {
					errorMsg = "用户所属单位信息的报送类别为空！";
				}
			}
		} else if (result == -3) {
			errorMsg = "该账号未审核或已锁定，请联系单位管理员或上级单位管理员！";
		} else {
			errorMsg = "账号或密码错误，请重新输入！";
		}
		if (StringUtils.isBlank(errorMsg)) {
			AppLoginUser loginUser = initLoginUser(user,permission);
			json.put("result",1);
			json.put("unitId",loginUser.getUnitId());
			json.put("userId",loginUser.getUserId());
			json.put("userName",loginUser.getRealName());
		} else {
			json.put("result",0);
			json.put("msg", errorMsg);
		}
		responseJSON(json);
	}

	protected String responseJSON(Map<String, Object> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		for (String key : jsonMap.keySet()) {
			jsonObject.put(key, jsonMap.get(key));
		}
		try {
			ServletUtils.print(getResponse(), jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	private void getModules(AppLoginUser user) throws Exception {
		if(StringUtils.isBlank(parm))
			return;
		Unit unit = unitService.getUnit(user.getUnitId());
		List<Module> moduleFirstList = moduleService.getModulesForMobile(
				Long.valueOf(-1), unit.getUnitclass(), unit.getUnittype(), parm);
		List<Module> firstModelList = new ArrayList<Module>();
		Set<Integer> modeSet = new HashSet<Integer>();
		Map<String, List<Module>> moduleMap = new HashMap<String, List<Module>>();
		Set<Integer> activeSubSytem = moduleService.getCacheSubsytem();
		List<UserRoleRelation> relaList = userRoleRelationService
				.getUserRoles(new String[] { user.getUserId() });
		List<Role> roleList = null;
		if (!relaList.isEmpty()) {
			String[] roleIds = new String[relaList.size()];
			for (int i = 0; i < roleIds.length; i++) {
				roleIds[i] = relaList.get(i).getRoleid();
			}
			roleList = rolePermService.getCacheRoleList(roleIds);
		}
		for (Role role : roleList) {
			if (role.getModSet() != null) {
				for (Module m : role.getModSet()) {
					Integer subId = m.getSubsystem();
					if (activeSubSytem.contains(subId)) {
						modeSet.add(m.getId().intValue());
					}
				}
			}
		}

		for (Module pm : moduleFirstList) {
			if (modeSet != null && modeSet.contains(pm.getId().intValue())) {
				firstModelList.add(pm);
			}
		}
		for (Module pm : firstModelList) {
//			List<Module> secondModelList = moduleService.getModules(
//					SUBSYSTEM_INSPE, unit.getUnitclass(), pm.getId());
			List<Module> secondModelList = moduleService.getModulesForMobile(
					pm.getId(), unit.getUnitclass(), unit.getUnittype(), parm);
			List<Module> secondList = new ArrayList<Module>();
			for (Module m : secondModelList) {
				if (modeSet != null && modeSet.contains(m.getId().intValue())) {
					m.setParentModName(pm.getName());
					secondList.add(m);
				}
			}
			moduleMap.put(pm.getMid(), secondList);
		}
		user.setSecondModuleMap(moduleMap);
		user.setFirstModuleList(firstModelList);
	}

	// 初始化用户登录信息
	private AppLoginUser initLoginUser(User user,boolean permission) throws Exception {
		AppLoginUser loginUser = new AppLoginUser();
		Unit unit = unitService.getUnit(user.getUnitid());
		loginUser.setAccountId(user.getAccountId());
		loginUser.setUnitId(unit.getId());
		loginUser.setUserId(user.getId());
		loginUser.setRealName(user.getRealname());
		loginUser.setUnitName(unit.getName());
		loginUser.setUserType(user.getOwnerType());
		if(permission){
			// 设置权限
			getModules(loginUser);
		}
		return loginUser;
	}

	public class AppLoginUser {
		private String userId;
		private String unitId;
		private String accountId;
		private String realName;
		private String unitName;
		private int userType;
		private List<Module> firstModuleList = new ArrayList<Module>();
		private Map<String, List<Module>> secondModuleMap = new HashMap<String, List<Module>>();

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUnitId() {
			return unitId;
		}

		public void setUnitId(String unitId) {
			this.unitId = unitId;
		}

		public String getAccountId() {
			return accountId;
		}

		public void setAccountId(String accountId) {
			this.accountId = accountId;
		}

		public String getRealName() {
			return realName;
		}

		public void setRealName(String realName) {
			this.realName = realName;
		}

		public String getUnitName() {
			return unitName;
		}

		public void setUnitName(String unitName) {
			this.unitName = unitName;
		}

		public int getUserType() {
			return userType;
		}

		public void setUserType(int userType) {
			this.userType = userType;
		}

		public List<Module> getFirstModuleList() {
			return firstModuleList;
		}

		public void setFirstModuleList(List<Module> firstModuleList) {
			this.firstModuleList = firstModuleList;
		}

		public Map<String, List<Module>> getSecondModuleMap() {
			return secondModuleMap;
		}

		public void setSecondModuleMap(Map<String, List<Module>> secondModuleMap) {
			this.secondModuleMap = secondModuleMap;
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setUserRoleRelationService(
			UserRoleRelationService userRoleRelationService) {
		this.userRoleRelationService = userRoleRelationService;
	}

	public void setRolePermService(RolePermService rolePermService) {
		this.rolePermService = rolePermService;
	}

	public void setBaseDataSubsystemService(
			BaseDataSubsystemService baseDataSubsystemService) {
		this.baseDataSubsystemService = baseDataSubsystemService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
