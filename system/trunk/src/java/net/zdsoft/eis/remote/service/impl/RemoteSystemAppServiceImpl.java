package net.zdsoft.eis.remote.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.constant.WeikeAppConstant;
import net.zdsoft.eis.base.subsystemcall.service.OfficedocSubsystemService;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.frame.util.RemoteCallUtils;
import net.zdsoft.eis.remote.enums.WeikeAppEnum;
import net.zdsoft.eis.remote.service.RemoteSystemAppService;
import net.zdsoft.eis.system.data.entity.UserRoleRelation;
import net.zdsoft.eis.system.data.service.RolePermService;
import net.zdsoft.eis.system.data.service.UserRoleRelationService;

public class RemoteSystemAppServiceImpl implements RemoteSystemAppService {
	private static final String OFFCIE_PARAM = "office_mobile";
	
	private UserService userService;
	private UnitService unitService;
	private ModuleService moduleService;
	private UserRoleRelationService userRoleRelationService;
	private RolePermService rolePermService;
	private OfficedocSubsystemService officedocSubsystemService;

	@Override
	public String officeAuth(String remoteParam) {
		try {
			String userId = RemoteCallUtils.getParamValue(remoteParam, "userId");
//			userId = "4028801055234FF80155238815CB0025";
			if(StringUtils.isBlank(userId)){
				return RemoteCallUtils.returnResultError("userId不能为空");
			}
			
			User user = userService.getUser(userId);
			if(user == null){
				return RemoteCallUtils.returnResultError("用户信息不存在或非教师登录！");
			}
			
			Unit unit = unitService.getUnit(user.getUnitid());
			List<Module> moduleFirstList = moduleService.getModulesForMobile(Long.valueOf(-1), unit.getUnitclass(),unit.getUnittype(), OFFCIE_PARAM);
			List<Module> firstModelList = new ArrayList<Module>();
			Set<Integer> modeSet = new HashSet<Integer>();
			Set<Integer> activeSubSytem = moduleService.getCacheSubsytem();
			List<UserRoleRelation> relaList = userRoleRelationService.getUserRoles(new String[] { userId });
			List<Role> roleList = new ArrayList<Role>();
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
			
			JSONObject returnJson = new JSONObject();
			//顶部应用
			List<WeikeAppEnum> topApps = WeikeAppEnum.getTopApps();
			JSONArray topArray = new JSONArray();
			String domain = RedisUtils.get("EIS.BASE.PATH.V6");
			for(WeikeAppEnum ent : topApps){
				JSONObject json = new JSONObject();
				String iconPath = domain+ent.getIconPath();
				json.put("code", ent.getCode());
				json.put("name", ent.getName());
				json.put("iconPath", iconPath);
				json.put("url", domain+ent.getUrl());
				json.put("subApps", "[]");
				topArray.add(json);
			}
			returnJson.put("topApps", topArray);
			
			
			JSONArray officeArray = new JSONArray();
			//公文应用
			Map<String, List<String>> subMap = officedocSubsystemService.officedocSubModelIds(userId);
			List<String> sendList = null;
			List<String> receiveList = null;
			if(subMap!=null){
				if(subMap.containsKey("moduleSendIds")){//发文
					sendList = subMap.get("moduleSendIds");
				}
				if(subMap.containsKey("moduleReceiveIds")){//收文
					receiveList = subMap.get("moduleReceiveIds");
				}
			}
			List<WeikeAppEnum> officedocApps = WeikeAppEnum.getOfficedocApps();
			for(WeikeAppEnum ent : officedocApps){
				JSONObject json = new JSONObject();
				if(sendList!=null && sendList.size()>0
						&& ent.getCode().equals(WeikeAppConstant.OFFICEDOC_SEND)){//发文
					String iconPath = domain+ent.getIconPath();
					Set<String> subs = WeikeAppConstant.getOfficedocSubApps(sendList);
					if(subs!=null && subs.size()>0){
						json.put("code", ent.getCode());
						json.put("name", ent.getName());
						json.put("iconPath", iconPath);
						json.put("url", "");
						json.put("subApps", "['"+StringUtils.join(subs.toArray(new String[0]), "','")+"']");
						officeArray.add(json);
					}
				}
				if(receiveList!=null && receiveList.size()>0
						&& ent.getCode().equals(WeikeAppConstant.OFFICEDOC_RECEIVE)){//发文
					String iconPath = domain+ent.getIconPath();
					Set<String> subs = WeikeAppConstant.getOfficedocSubApps(receiveList);
					if(subs!=null && subs.size()>0){
						json.put("code", ent.getCode());
						json.put("name", ent.getName());
						json.put("iconPath", iconPath);
						json.put("url", "");
						json.put("subApps", "['"+StringUtils.join(subs.toArray(new String[0]), "','")+"']");
						officeArray.add(json);
					}
				}
			}
			
			//办公应用
			for (Module pm : firstModelList) {
				List<Module> secondModelList = moduleService.getModulesForMobile(pm.getId(), unit.getUnitclass(), unit.getUnittype(), OFFCIE_PARAM);
				JSONObject obj = new JSONObject();
				for (Module m : secondModelList) {
					if (modeSet != null && modeSet.contains(m.getId().intValue())) {
						JSONObject json = new JSONObject();
						String iconPath = domain+m.getPicture();
						json.put("code", String.valueOf(m.getId()));
						json.put("name", m.getName());
						json.put("iconPath", iconPath);
						if(StringUtils.isBlank(m.getUrl())){
							json.put("url", "");
						}else{
							json.put("url", domain+m.getUrl());
						}
						json.put("subApps", "[]");
						officeArray.add(json);
					}
				}
			}
			returnJson.put("officeApps", officeArray);
//			System.out.println(returnJson);
			return RemoteCallUtils.returnResultJson(returnJson);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
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

	public void setOfficedocSubsystemService(
			OfficedocSubsystemService officedocSubsystemService) {
		this.officedocSubsystemService = officedocSubsystemService;
	}
}
