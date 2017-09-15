package net.zdsoft.eis.system.homepage.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.ProductParam;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.ProductParamService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.action.BaseSemesterAction;
import net.zdsoft.eis.frame.client.LoginInfo;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public class SubsystemIndexAction extends BaseSemesterAction {
	private static final long serialVersionUID = 3512830521594240641L;

	private static long parentId = -1L;// -1表示顶层模块

	private int moduleParentId = -1; // 模块所属模块的parentid
	private Module module;
	private SubSystem subSystem;
	private Map<String, Module> mapOfGuideAction;
	public List<Module> modelList = new ArrayList<Module>();//
	public List<Module> firstModelList = new ArrayList<Module>();//
	public List<Module> smallModelList = new ArrayList<Module>();//
	private Map<String, List<Module>> secondCommonModelMap = new HashMap<String, List<Module>>();
	private Map<String, List<Module>> secondModelMap = new HashMap<String, List<Module>>();
	private ModuleService moduleService;
	private SubSystemService subSystemService;
	private ProductParamService productParamService;
	private ServerService serverService;
	private String appLevel;// 不同布局时模块显示样式，1左右布局首页显示样式，2左右布局内页样式，其他都为上下布局样式

	private LoginInfo init() throws Exception {
		LoginInfo loginInfo = this.getLoginInfo();
		// LoginUser loginUser = (LoginUser)
		// getSession(BaseConstant.SESSION_LOGINUSER);
		// String skin = userSetService.getSkinByUserId(getServletContext(),
		// getLoginInfo().getUser().getId(), true);
		// loginUser.setSkin(skin);
		// setSession(BaseConstant.SESSION_LOGINUSER, loginUser);
		return loginInfo;
	}

	public String execute() throws Exception {
		subSystem = subSystemService.getSubSystem(appId);
		if (null == subSystem)
			return SUCCESS;
		if (systemDeployService.isConnectPassport()) {// 从passpor
			if (subSystem != null) {
				Server server = serverService.getServerByServerCode(subSystem
						.getCode());
				if (server != null)
					subSystem.setName(server.getName());
			}
		}
		// 没有权限访问
		if (!getLoginInfo().getAllSubSystemSet().contains(
				subSystem.getId().intValue())
				|| (getModuleID() > 0 && !getLoginInfo().validateAllModel(
						getModuleID()))) {
			return BaseAction.NOPERMISSION;
		}
		return SUCCESS;
	}

	public String model() throws Exception {
		// 如果是学生登录，则跳转到学生平台
		LoginInfo loginInfo = getLoginInfo();
		if (loginInfo.getUser().getOwnerType() == User.STUDENT_LOGIN
				|| loginInfo.getUser().getOwnerType() == User.FAMILY_LOGIN) {
			String contextPath = getRequest().getContextPath();
			if (null == contextPath) {
				contextPath = "";
			}
			if (loginInfo.getUser().getOwnerType() == User.STUDENT_LOGIN)
				platform = BaseConstant.PLATFORM_STUPLATFORM;
			else
				platform = BaseConstant.PLATFORM_FAMPLATFORM;
			getResponse().sendRedirect(
					contextPath + "/stuplatform/login/model.action?appId="
							+ String.valueOf(appId) + "&platform="
							+ String.valueOf(platform) + "&moduleID="
							+ getModuleID() + "&appLevel="
							+ StringUtils.trimToEmpty(appLevel));
		}
		subSystem = subSystemService.getSubSystem(appId);
		Set<Integer> modeSet = getLoginInfo().getAllModSet();
//		List<Module> moduleFirstList = moduleService.getModulesForPC(appId,
//				loginInfo.getUnitClass(), loginInfo.getUnitType(), parentId);
//		for (Module pm : moduleFirstList) {
//			// int count = 0;
//			if (modeSet != null && modeSet.contains(pm.getId().intValue())) {
//				firstModelList.add(pm);
//			}
//		}
		//权限过滤方式调整说明---适应场景（用户有二级模块权限却没有对应的一级模块权限，比如把原有的二级模块调整到新增的一个一级模块里面,这种情况用之前的方式过滤权限用户是看不到调整之后的二级模块的）
		//权限过滤方法：遍历二级模块权限的同时，判断该用户是否有该一级模块的权限（若该一级模块下的所有二级模块都没有，则说明该用户没有该一级模块的权限，过后则移除该一级模块的权限）
		//这里一级模块的权限 不再根据sys_role_perm表里存储的一级模块权限来判断。而是通过是否拥有旗下二级模块权限的有无来判断。
		//modify by like 2016-08-22
		firstModelList = moduleService.getModulesForPC(appId,
				loginInfo.getUnitClass(), loginInfo.getUnitType(), parentId);
		int count = 0;
		Set<Module> removeFirstModels = new HashSet<Module>();
		for (Module pm : firstModelList) {
			// int count = 0;
			List<Module> secondModelList = moduleService.getModules(appId,
					loginInfo.getUnitClass(), pm.getId());
			List<Module> validSecondCommonModelList = new ArrayList<Module>();
			List<Module> validSecondModelList = new ArrayList<Module>();
			
			boolean isAuth = false;
			for (Module m : secondModelList) {
				if (modeSet != null && modeSet.contains(m.getId().intValue())) {
					//有二级模块权限就意味着有一级模块的权限
					isAuth = true;
					
					m.setParentModName(pm.getName());
					modelList.add(m);
					if ("common".equals(m.getWin())) {// 重要模块
						validSecondCommonModelList.add(m);
					} else {
						validSecondModelList.add(m);
					}
					// 设置收缩后的展示模块
					if (1 == m.getUselevel()) {
						smallModelList.add(m);
					}
				}
			}
			if(!isAuth){
				//没有该一级模块权限，则firstModelList中移除该一级模块
				removeFirstModels.add(pm);
				continue;
			}
			secondModelMap.put(pm.getMid(), validSecondModelList);
			secondCommonModelMap.put(pm.getMid(), validSecondCommonModelList);
			//如果没有特定设置的话 按照先大后小的方式取前八个模块
			if (CollectionUtils.isEmpty(smallModelList)) {
				for (Module m : validSecondCommonModelList) {
					if (++count <= 8) {
						smallModelList.add(m);
					} else {
						break;
					}
				}
				for (Module m : validSecondModelList) {
					if (++count <= 8) {
						smallModelList.add(m);
					} else {
						break;
					}
				}
			}
		}
		
		//移除没有权限的一级模块
		if(CollectionUtils.isNotEmpty(removeFirstModels)){
			firstModelList.removeAll(removeFirstModels);
		}
		
		if("1".equals(appLevel)){
			return "modelLeft";
		} else if("2".equals(appLevel)){
			return "modelLeftSide";
		}
		return SUCCESS;
	}

	public String main() throws Exception {
		LoginInfo loginInfo = init();

		if (null == subSystem)
			return SUCCESS;

		if (platform == BaseConstant.PLATFORM_STUPLATFORM
				|| platform == BaseConstant.PLATFORM_BACKGROUND) {
			return SUCCESS;
		} else {
			// 没有权限访问
			if (!loginInfo.getAllSubSystemSet().contains(
					subSystem.getId().intValue())
					|| (getModuleID() > 0 && !loginInfo
							.validateAllModel(getModuleID()))) {
				return BaseAction.NOPERMISSION;
			}
		}
		return SUCCESS;
	}

	public String getSystemVersion() {
		String contextRoot = getServletContext().getRealPath("/");
		File libDir = new File(new File(contextRoot), "META-INF/MANIFEST.MF");
		String line = "";
		String version = "";
		try {
			BufferedReader fr = new BufferedReader(new FileReader(libDir));
			while ((line = fr.readLine()) != null) {
				version += line + "\r\n";
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return version;
	}

	public Module remoteGetModule(int moduleId) {
		LoginInfo loginInfo = getLoginInfo();
		if (loginInfo.validateAllModel(moduleId)) {
			return moduleService.getModuleByIntId(moduleId);
		} else {
			return null;
		}
	}
	
	public String subsystemHelp(){
		LoginInfo loginInfo = getLoginInfo();
		subSystem = subSystemService.getSubSystem(appId);
		firstModelList = moduleService.getModulesForPC(appId,
				loginInfo.getUnitClass(), loginInfo.getUnitType(), (long)-1);
		for (Module pm : firstModelList) {
			List<Module> secondModelList = moduleService.getModules(appId,
					loginInfo.getUnitClass(), pm.getId());
			secondModelMap.put(pm.getMid(), secondModelList);
		}
		return SUCCESS;
	}
	
	
	
	public Date getCurrentDate() {
		return new Date();
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public List<Module> getModelList() {
		return modelList;
	}

	public boolean isShowCompanyLogo() {
		boolean sign = true;
		String param = productParamService
				.getProductParamValue(ProductParam.SHOW_COMPANY_LOGO);
		if (StringUtils.isNotEmpty(param) && "0".equals(param)) {
			sign = false;
		}
		return sign;
	}

	public String getSkin() {
		return userSetService.getSkinByUserId(getServletContext(),
				getLoginInfo().getUser().getId(), getLoginInfo().getUser().getOwnerType(), true);
	}

	public List<Module> getFirstModelList() {
		return firstModelList;
	}

	public Map<String, List<Module>> getSecondCommonModelMap() {
		return secondCommonModelMap;
	}

	public Map<String, List<Module>> getSecondModelMap() {
		return secondModelMap;
	}

	public int getModuleParentId() {
		return moduleParentId;
	}

	public void setModuleParentId(int moduleParentId) {
		this.moduleParentId = moduleParentId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Map<String, Module> getMapOfGuideAction() {
		if (null == mapOfGuideAction)
			mapOfGuideAction = new HashMap<String, Module>();
		return mapOfGuideAction;
	}

	public SubSystem getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(SubSystem subSystem) {
		this.subSystem = subSystem;
	}

	public List<Module> getSmallModelList() {
		return smallModelList;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setProductParamService(ProductParamService productParamService) {
		this.productParamService = productParamService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public String getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}

}
