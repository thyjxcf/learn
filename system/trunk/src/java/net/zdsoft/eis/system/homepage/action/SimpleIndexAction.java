package net.zdsoft.eis.system.homepage.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SimpleModule;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.leadin.dataimport.subsystemcall.LoginUser;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @since 1.0
 * @version $Id: stuPlatformIndexAction.java, v 1.0 2007-11-9 下午03:33:05 zhaosf
 *          Exp $
 */
public class SimpleIndexAction extends BaseAction {

	private static final long serialVersionUID = -178536062105480375L;

	private static long parentId = -1;// -1表示顶层模块

	private List<SimpleModule> topModules;// 返回模块第一层的目录
	public List<SimpleModule> modelList = new ArrayList<SimpleModule>();//
	public List<SimpleModule> firstModelList = new ArrayList<SimpleModule>();//
	private Map<String, List<SimpleModule>> secondCommonModelMap = new HashMap<String, List<SimpleModule>>();
	private Map<String, List<SimpleModule>> secondModelMap = new HashMap<String, List<SimpleModule>>();
	private SimpleModuleService simpleModuleService;// 模块
	private SubSystemService subSystemService;
	private SerialManager serialManager;
	private SystemVersionService systemVersionService;
	private ServerService serverService;
	private String modID;
	private SubSystem subSystem;
	private String subsystemName;
	private String appLevel;

	public String getModID() {
		return modID;
	}

	public void setModID(String modID) {
		this.modID = modID;
	}

	public String execute() {
		subSystem = subSystemService.getSubSystem(appId);
		if (systemDeployService.isConnectPassport()) {// 从passpor
			if (subSystem != null) {
				Server server = serverService.getServerByServerCode(subSystem
						.getCode());
				if (server != null)
					subSystem.setName(server.getName());
			}
		}
		if (subSystem != null)
			subsystemName = subSystem.getName();
		return SUCCESS;
	}

	public String model() throws Exception {
		subSystem = subSystemService.getSubSystem(appId);
		// 取系统管理的子模块
		firstModelList = getFilterModules(appId, parentId);
		// 取二级模块
		for (Iterator<SimpleModule> iter = firstModelList.iterator(); iter
				.hasNext();) {
			SimpleModule simpleModule = (SimpleModule) iter.next();
			List<SimpleModule> secondModelList = getFilterModules(appId,
					simpleModule.getId());
			List<SimpleModule> validSecondCommonModelList = new ArrayList<SimpleModule>();
			List<SimpleModule> validSecondModelList = new ArrayList<SimpleModule>();
			for (SimpleModule m : secondModelList) {
				m.setParentModName(m.getName());
				modelList.add(m);
				if ("common".equals(m.getWin())) {// 重要模块
					validSecondCommonModelList.add(m);
				} else {
					validSecondModelList.add(m);
				}
			}
			secondModelMap.put(simpleModule.getMid(), validSecondModelList);
			secondCommonModelMap.put(simpleModule.getMid(),
					validSecondCommonModelList);
		}
		LoginUser loginUser = (LoginUser) getSession(BaseConstant.SESSION_LOGINUSER);
		if (loginUser != null) {
			String skin = userSetService.getSkinByUserId(getServletContext(),
					getLoginInfo().getUser().getId(), getLoginInfo().getUser().getOwnerType(), true);
			loginUser.setSkin(skin);
			setSession(BaseConstant.SESSION_LOGINUSER, loginUser);
		}
		
		if("1".equals(appLevel)){
			return "modelLeft";
		} else if("2".equals(appLevel)){
			return "modelLeftSide";
		}
		return SUCCESS;
	}

	/**
	 * 取得指定父节点的module的列表，过滤掉不上的子系统
	 * 
	 * @param subsystem
	 * @param parentId
	 * @return
	 */
	private List<SimpleModule> getFilterModules(int subsystem, Long parentId) {
		List<SimpleModule> modules = new ArrayList<SimpleModule>();
		List<SimpleModule> list = simpleModuleService.getModules(platform,
				subsystem, parentId);

		// 取启用了哪些子系统
		Set<Integer> subsystemSet = serialManager.getSubsystems();
		if (!isEisDeploy()) {
			if (subSystem != null) {
				Server server = serverService.getServerByServerCode(subSystem
						.getCode());
				if (server != null)
					subSystem.setName(server.getName());
			}
		}
		// 过滤掉没有上子系统的模块
		for (SimpleModule m : list) {
			String dss = m.getDataSubsystems();
			boolean find = true;
			if (StringUtils.isNotBlank(dss)) {
				String[] subsystemIds = m.getDataSubsystems().split(",");
				for (String subsystemId : subsystemIds) {
					if (!(subsystemSet.contains(Integer.valueOf(subsystemId
							.trim())))) {
						find = false;
						break;
					}
				}
			}
			if (find) {
				modules.add(m);
			}
		}
		return modules;
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

	public String main() throws Exception {
		subSystem = subSystemService.getSubSystem(appId);
		return SUCCESS;
	}

	public String getSkin() {
		return userSetService.getSkinByUserId(getServletContext(),
				getLoginInfo().getUser().getId(), getLoginInfo().getUser().getOwnerType(), true);
	}

	public Date getCurrentDate() {
		return new Date();
	}

	public String getPlatFormName() {
		return systemVersionService.getSystemVersion().getName();
	}

	public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
		this.simpleModuleService = simpleModuleService;
	}

	public List<SimpleModule> getTopModules() {
		return topModules;
	}

	public SubSystem getSubSystem() {
		return subSystem;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public void setSystemVersionService(
			SystemVersionService systemVersionService) {
		this.systemVersionService = systemVersionService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

	public List<SimpleModule> getModelList() {
		return modelList;
	}

	public Map<String, List<SimpleModule>> getSecondCommonModelMap() {
		return secondCommonModelMap;
	}

	public void setSecondCommonModelMap(
			Map<String, List<SimpleModule>> secondCommonModelMap) {
		this.secondCommonModelMap = secondCommonModelMap;
	}

	public Map<String, List<SimpleModule>> getSecondModelMap() {
		return secondModelMap;
	}

	public void setSecondModelMap(Map<String, List<SimpleModule>> secondModelMap) {
		this.secondModelMap = secondModelMap;
	}

	public List<SimpleModule> getFirstModelList() {
		return firstModelList;
	}

	public String getSubsystemName() {
		return subsystemName;
	}

	public String getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}
	
}
