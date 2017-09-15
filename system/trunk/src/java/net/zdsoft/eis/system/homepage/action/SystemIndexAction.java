package net.zdsoft.eis.system.homepage.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Server;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.entity.UserSet;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SysOptionService;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.subsystemcall.entity.DesktopAppDto;
import net.zdsoft.eis.base.subsystemcall.service.DesktopSubsystemService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.system.data.entity.ExternalApp;
import net.zdsoft.eis.system.data.service.ExternalAppService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import sun.misc.BASE64Encoder;

public class SystemIndexAction extends BaseAction {

	private static final long serialVersionUID = -5162979792439750524L;

	private SubSystemService subSystemService;
	private TeacherService teacherService;
	private ModuleService moduleService;
	private DesktopSubsystemService desktopSubsystemService;
	private SysOptionService sysOptionService;
	private ExternalAppService externalAppService;
	private ServerService serverService;

	private List<AppRegistry> userSystemList = new ArrayList<AppRegistry>();

	private String layout = UserSet.LAYOUT_UP;

	private String sortType;
	private String sortCode;
	private String sortName;

	private String fileUrl;

	private Module module;

	private String divId;

	public String execute() throws Exception {
		if (getModuleID() != 0) {
			module = moduleService.getModuleByIntId(getModuleID());
		}
		userSystemList = getUserSystemListByDataType("catalog");
		if ("tianchang".equals(getSystemDeploySchool())) {
			return "left";
		}
		if (UserSet.LAYOUT_LEFT.equals(layout)) {
			return "left";
		}
		return SUCCESS;
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public String subsystemApp() throws Exception {
		if (StringUtils.isNotBlank(divId))
			sortCode = divId;
		// 获取APP的名称桌面显示
		if (StringUtils.isNotBlank(sortCode)) {
			DesktopAppDto desktopAppDto = desktopSubsystemService
					.getDesktopApp(BaseConstant.ZERO_GUID, getLoginInfo()
							.getUnitClass(), sortCode);
			if (desktopAppDto != null) {
				sortName = desktopAppDto.getName();
			}
		}

		// 获取文件的路径
		fileUrl = sysOptionService.getValue("FILE.URL");
		userSystemList = new ArrayList<AppRegistry>();
		List<AppRegistry> tempUserSystemList = getUserSystemListByDataType("subsystem");
		for (AppRegistry app : tempUserSystemList) {
			if (StringUtils.isNotBlank(app.getSortType())
					&& app.getSortType().equals(sortType)) {
				userSystemList.add(app);
			}
		}
		if (getLoginInfo().getUnitClass() == Unit.UNIT_CLASS_SCHOOL) {
			int prefix = 100000;
			List<ExternalApp> externalAppList = externalAppService
					.getExternalAppListByUnitId(getUnitId(),
							NumberUtils.toInt(sortType), 0, false);
			for (ExternalApp app : externalAppList) {
				AppRegistry appRegistry = new AppRegistry();
				appRegistry.setXurl(app.getAppUrl());
				appRegistry.setImage(app.getDownloadPath());
				appRegistry.setType("1");
				appRegistry.setAppname(app.getAppName());
				appRegistry.setSource("99");
				appRegistry.setId(sortType + (prefix++));
				userSystemList.add(appRegistry);
			}
		}
		return SUCCESS;
	}

	public List<AppRegistry> getUserSystemListByDataType(String dataType)
			throws Exception {
		List<AppRegistry> subSystemList = new ArrayList<AppRegistry>();// 子系统
		LoginInfo loginInfo = this.getLoginInfo();
		String contextPath = getRequest().getContextPath();
		if (null == contextPath) {
			contextPath = "";
		}
		// 城域内部子系统
		Set<Integer> orignalIdSet = loginInfo.getAllSubSystemSet();
		Set<Integer> idSet = new HashSet<Integer>();
		for (Integer id : orignalIdSet) {
			idSet.add(id);
		}
		if (appId != 0) {
			if (idSet.contains(appId)) {
				idSet.clear();
				idSet.add(appId);
			} else {
				idSet.clear();
			}
		} else {
			if (loginInfo.getUser().getOwnerType() == User.TEACHER_LOGIN) {
				List<SubSystem> thirdPartList = subSystemService
						.getThirdPartSubSystems(SubSystem.SOURCE_THIRD_PART_NORMAL);
				for (SubSystem subsystem : thirdPartList) {
					if (CollectionUtils.isEmpty(idSet))
						idSet = new HashSet<Integer>();
					idSet.add(subsystem.getId().intValue());
				}
			}
		}
		Map<String, List<AppRegistry>> subSystemMap = new HashMap<String, List<AppRegistry>>();
		Map<String, Server> serverMap = serverService.getServerMapByCode();
		if (serverMap == null) {
			serverMap = new HashMap<String, Server>();
		}
		if (idSet.size() > 0) {
			List<SubSystem> subList = subSystemService
					.getSpecialSubSystems(idSet);
			for (SubSystem subsystem : subList) {
				if (subsystem.getParentId() != -1) {
					idSet.add(subsystem.getParentId());
				}
			}
			List<SubSystem> allSubList = subSystemService
					.getSpecialSubSystems(idSet);
			for (SubSystem subsystem : allSubList) {
				AppRegistry dto = new AppRegistry();
				dto.setAppSystemType(SubSystem.APP_SYSTEM_TYPE_LOCAL);
				dto.setId(String.valueOf(subsystem.getId()));
				dto.setAppcode(subsystem.getCode());
				dto.setParentId(String.valueOf(subsystem.getParentId()));
				dto.setAppname(subsystem.getName());
				dto.setType(String.valueOf(subsystem.getType()));
				if ("1".equals(subsystem.getSource())) {// 内部系统
					dto.setXurl(contextPath + subsystem.getIndexUrl());
				} else {// 外部系统
					if ("2".equals(subsystem.getSource())
							&& BaseConstant.SYS_DEPLOY_SCHOOL_NBZX
									.equalsIgnoreCase(getSystemDeploySchool())) {
						BASE64Encoder encode = new BASE64Encoder();
						Teacher teacher = teacherService
								.getTeacher(getLoginInfo().getUser()
										.getTeacherid());
						String base64code = encode.encode(teacher.getName()
								.getBytes("GBK"));
						// String base64code = encode.encode("傅卫东".getBytes());
						dto.setXurl(subsystem.getIndexUrl() + "?param="
								+ base64code);
					} else {
						// 内外网判断
						if (serverMap.containsKey(subsystem.getCode())) {
							Server server = serverMap.get(subsystem.getCode());
							if (isSecondUrl())
								dto.setXurl(server.getSecondUrl()
										+ server.getIndexUrl());
							else
								dto.setXurl(server.getUrl()
										+ server.getIndexUrl());
						} else {
							dto.setXurl(subsystem.getIndexUrl());
						}
					}
				}
				dto.setImage(subsystem.getImage());
				dto.setDisplayorder(subsystem.getOrderid());
				dto.setSource(subsystem.getSource());
				dto.setSortType(subsystem.getSortType());
				if ("catalog".equals(dataType)) {
					if (subsystem.getParentId() == -1) {
						subSystemList.add(dto);
					} else {
						List<AppRegistry> tempList = subSystemMap.get(subsystem
								.getParentId().toString());
						if (CollectionUtils.isEmpty(tempList))
							tempList = new ArrayList<AppRegistry>();
						tempList.add(dto);
						subSystemMap.put(subsystem.getParentId().toString(),
								tempList);
					}
				} else {
					if (subsystem.getType() == 1) {
						subSystemList.add(dto);
					}
				}
			}
		}

		if ("catalog".equals(dataType)) {
			for (AppRegistry app : subSystemList) {
				List<AppRegistry> tempsubList = subSystemMap.get(app.getId());
				if (CollectionUtils.isNotEmpty(tempsubList)) {
					Collections.sort(tempsubList,
							new Comparator<AppRegistry>() {
								@Override
								public int compare(AppRegistry o1,
										AppRegistry o2) {
									int order1 = o1.getDisplayorder() == null ? 0
											: o1.getDisplayorder().intValue();
									int order2 = o2.getDisplayorder() == null ? 0
											: o2.getDisplayorder().intValue();
									return order1 - order2;
								}
							});
					app.setSubAppList(tempsubList);
				}
			}
		}
		Collections.sort(subSystemList, new Comparator<AppRegistry>() {
			@Override
			public int compare(AppRegistry o1, AppRegistry o2) {
				int order1 = o1.getDisplayorder() == null ? 0 : o1
						.getDisplayorder().intValue();
				int order2 = o2.getDisplayorder() == null ? 0 : o2
						.getDisplayorder().intValue();
				return order1 - order2;
			}
		});
		return subSystemList;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public List<AppRegistry> getUserSystemList() {
		return userSystemList;
	}

	public String getLayout() {
		return layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setDesktopSubsystemService(
			DesktopSubsystemService desktopSubsystemService) {
		this.desktopSubsystemService = desktopSubsystemService;
	}

	public void setSysOptionService(SysOptionService sysOptionService) {
		this.sysOptionService = sysOptionService;
	}

	public void setExternalAppService(ExternalAppService externalAppService) {
		this.externalAppService = externalAppService;
	}

	public void setServerService(ServerService serverService) {
		this.serverService = serverService;
	}

}
