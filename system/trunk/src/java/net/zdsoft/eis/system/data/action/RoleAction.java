/* 
 * @(#)RoleAction.java    Created on 2006-10-9
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header: f:/44cvsroot/stusys/stusys/src/net/zdsoft/stusys/system/usermanager/action/RoleAction.java,v 1.21 2007/01/16 10:12:44 luxm Exp $
 */
package net.zdsoft.eis.system.data.action;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Role;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.data.service.RoleService;
import net.zdsoft.eis.system.frame.service.ModuleTreeService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.tree.WebTree;

/**
 * @author luxingmu
 * @version $Revision: 1.21 $, $Date: 2007/01/16 10:12:44 $
 */
public class RoleAction extends BaseAction implements ModelDriven<Role> {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = -1172074719896242309L;

	private static final String RESULT_TYPE_LIST = "list";
	private static final String RESULT_TYPE_ADD = "add";
	private static final String RESULT_TYPE_MODIFY = "modify";
	private static final String RESULT_TYPE_DELETE = "delete";
	private static final String RESULT_TYPE_SAVE = "save";
    
	private String modID = "SYS006";
	
	public Role roleDto = new Role();

	public List<Role> roleList;

	private RoleService roleService;

	private ModuleTreeService moduleTreeService;

	private String operation;

	private String operationName;

	private String treeName;

	private String xtreeScript;

	private String[] ids;

//	private Long[] idi;

	private String roleids;

	private Long[] modids;

	private Long[] operids;

	private String[] extraids;
	
	//角色删除  idi为long型  json传值有问题 重新定义一个
	private String[]  idiJson;
	
	private String jsonString;

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String[] getIdiJson() {
		return idiJson;
	}

	public void setIdiJson(String[] idiJson) {
		this.idiJson = idiJson;
	}

	public String[] getExtraids() {
		return extraids;
	}

	public void setExtraids(String[] extraids) {
		this.extraids = extraids;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public Role getModel() {
		return roleDto;
	}

	public String execute() throws Exception {
		String oper = getOperation();
		if (org.apache.commons.lang.StringUtils.isEmpty(oper)) {
			oper = RESULT_TYPE_LIST;
		}
		LoginInfo info = getLoginInfo();
		//如果是教育局的用户，则需要设置成4，因为运营平台传送过来的单位是4
		int userType = info.getUser().getOwnerType();
		if (userType == User.TEACHER_LOGIN) {
			if (info.getUnitClass() == Unit.UNIT_CLASS_EDU) {
				userType = User.TEACHER_EDU_LOGIN;
			}
		}
		if (RESULT_TYPE_LIST.equals(oper)) {
			roleList = roleService.getRolesByNotThisType(info.getUnitID(),
					Role.ROLE_TYPE_UNITADMIN);
			for (Iterator<Role> iter = roleList.iterator(); iter.hasNext();) {
				Role _dto = (Role) iter.next();
				_dto.setName(StringUtils.htmlFilter(StringUtils.cutOut(_dto
						.getName(), 32, "...")));
				_dto.setDescription(StringUtils.htmlFilter(StringUtils.cutOut(
						_dto.getDescription(), 32, "...")));
			}
		} else if (RESULT_TYPE_ADD.equals(oper)) { // 新增角色
			WebTree tree = moduleTreeService.getModuleTree(info.getUnitID(),
					info.getUnitClass().intValue(), userType, info
							.getUnitType().intValue(), null, null, null);
			xtreeScript = tree.toString();
			//System.out.println(xtreeScript);
			treeName = tree.getNodeName();
			operationName = "新增角色";
			roleDto.setIsactive(true);
		} else if (RESULT_TYPE_MODIFY.equals(oper)) { // 修改角色
			roleDto = roleService.getRoleFullInfo(roleDto.getId(), info
					.getUnitClass());
			WebTree tree = moduleTreeService.getModuleTree(info.getUnitID(),
					info.getUnitClass().intValue(), userType, info
							.getUnitType().intValue(), roleDto
							.getSelectedModSet(), roleDto.getSelectedOperSet(),
					roleDto.getExtraSubSystemSet());
			xtreeScript = tree.toString();
			treeName = tree.getNodeName();
			operationName = "修改角色";
		} else if (RESULT_TYPE_SAVE.equals(oper)) { // 保存角色
			JSONObject json = JSONObject.fromObject(jsonString);
			roleDto = (Role) JSONObject.toBean(json, Role.class);
			Set<Long> selectedModSet = new HashSet<Long>();
			Set<Long> selectedOperSet = new HashSet<Long>();
			Set<String> selectedExtraSubSystemSet = new HashSet<String>();
			if (modids != null) {
				selectedModSet.addAll(Arrays.asList(modids));
			}
			if (operids != null) {
				selectedOperSet.addAll(Arrays.asList(operids));
			}
			if (extraids != null) {
				selectedExtraSubSystemSet.addAll(Arrays.asList(extraids));
			}
			if (roleDto.getUnitid() == null) {
				roleDto.setUnitid(info.getUnitID());
			}
			roleDto.setSelectedModSet(selectedModSet);
			roleDto.setSelectedOperSet(selectedOperSet);
			roleDto.setUnitClass(info.getUnitClass());
			roleDto.setExtraSubSystemSet(selectedExtraSubSystemSet);
			oper = saveRole(roleDto);
		} else if ("lock".equals(oper)) {
			if (roleids.endsWith(",")) {
				roleids = roleids.substring(0, roleids.lastIndexOf(","));
			}
			oper = activeOrLockUser(roleids, false);
		} else if ("activate".equals(oper)) {
			if (roleids.endsWith(",")) {
				roleids = roleids.substring(0, roleids.lastIndexOf(","));
			}
			oper = activeOrLockUser(roleids, true);
		} else if (RESULT_TYPE_DELETE.equals(oper)) { // 删除角色
			oper = deleteRole(ids, idiJson);
		}
		return oper;
	}

	/**
	 * 保存角色
	 * 
	 * @param roleDto
	 * @return
	 */
	private String saveRole(Role roleDto) {
		promptMessageDto = new PromptMessageDto();

		boolean isNewRecord = false;
		String logMsg = "";
		if (roleDto.getId() == null)
			logMsg = "新增";
		else
			logMsg = "修改";

		try {
			roleService.saveRole(roleDto);
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setErrorMessage("出错啦！");
			promptMessageDto.setOperateSuccess(false);
			if (isNewRecord) {
				promptMessageDto.addOperation(new String[] { "确定",
						"roleAdmin.action?operation=add" });
			} else {
				promptMessageDto.addOperation(new String[] {
						"确定",
						"roleAdmin.action?operation=modify&id="
								+ roleDto.getId() });
			}
			return PROMPTMSG;
		}
		promptMessageDto.setPromptMessage("角色保存成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定",
				"roleAdmin.action?moduleID=" + this.getModuleID()});

		SystemLog.log(modID, logMsg + "角色信息成功！");
		return "jsonSuccess";
	}

	/**
	 * 保存角色
	 * 
	 * @param roleDto
	 * @return
	 */
	private String activeOrLockUser(String roleids, boolean activate) {
		promptMessageDto = new PromptMessageDto();
		try {
			roleService.updateRoles(roleids, activate);
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setErrorMessage("出错啦！");
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "确定",
					"roleAdmin.action?moduleID=" + this.getModuleID() });
			return "jsonSuccess";
		}
		if (activate) {
			promptMessageDto.setPromptMessage("角色激活成功！");
		} else {
			promptMessageDto.setPromptMessage("角色锁定成功！");
		}
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定",
				"roleAdmin.action?moduleID=" + this.getModuleID() });
		return "jsonSuccess";
	}

	/**
	 * 删除角色
	 * 
	 * @param ids
	 * @return
	 */
	private String deleteRole(String[] ids, String[] idi) {
		StringBuilder sb = new StringBuilder();
		promptMessageDto = new PromptMessageDto();
		if (!Validators.isEmpty(ids)) {
			try {
				roleService.deleteRoles(ids);
			} catch (Exception e) {
				log.error(e.toString());
				promptMessageDto.setErrorMessage("出错啦！");
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.addOperation(new String[] { "确定",
						"roleAdmin.action?moduleID=" + this.getModuleID() });
				return "jsonSuccess";
			}
		} else {
			if (Validators.isEmpty(idi)) {
				sb.append("请选择要删除的角色！");
			} else {
				sb.append("有").append(idi.length).append("个角色为系统内置角色，无法删除！");
			}
			promptMessageDto.setPromptMessage(sb.toString());
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "确定",
					"roleAdmin.action?moduleID=" + this.getModuleID() });
			return "jsonSuccess";
		}

		if (!Validators.isEmpty(idi)) {
			sb.append("部分角色删除成功！");
			sb.append("有").append(idi.length).append("个角色为系统内置角色，无法删除！");
		} else {
			sb.append("角色删除成功！");
		}
		promptMessageDto.setPromptMessage(sb.toString());
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定",
				"roleAdmin.action?moduleID=" + this.getModuleID() });

		SystemLog.log(modID, "删除角色信息成功！");
		return "jsonSuccess";
	}
	
	private String roleId;
	private String roleName;
	private String description;

	public String checkRoleInfoValid() {
        boolean isRoleNameInvalid = false;
        boolean isRoleDesInvalid = false;
		try {
			isRoleNameInvalid = org.apache.commons.lang.StringUtils.isBlank(roleName)
			        || roleName.getBytes("gbk").length > 50;
	        isRoleDesInvalid = org.apache.commons.lang.StringUtils.isNotBlank(description)
	        && description.getBytes("gbk").length > 255;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

        boolean isNewRecord = false;

        boolean isRoleNameRepeat = false;

        if (roleId == null) {
            isNewRecord = true;
        }

        if (!isRoleNameInvalid) {
            Role _tempDto = roleService.getRoleByName(getLoginInfo()
                    .getUnitID(), roleName);
            if (_tempDto != null && _tempDto.getId() != null) {
                if (isNewRecord) {
                    isRoleNameRepeat = true;
                } else {
                    if (!String.valueOf(_tempDto.getId()).equals(roleId)) {
                        isRoleNameRepeat = true;
                    }
                }
            }
        }
        if (isRoleNameInvalid || isRoleDesInvalid || isRoleNameRepeat) {
            StringBuilder sb = new StringBuilder();
            sb.append("出错啦！");
            if (isRoleNameInvalid) {
                sb.append("角色名称不能为空且长度不能超过50个字符或25个汉字！");
            }
            if (isRoleNameRepeat) {
                sb.append("角色名称重复，请重新输入！");
            }
            if (isRoleDesInvalid) {
                sb.append("角色描述长度不能超过255个字符或127个汉字！");
            }
            jsonError = sb.toString();
        }
        return SUCCESS;
    }
	
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getXtreeScript() {
		return xtreeScript;
	}

	public void setModuleTreeService(ModuleTreeService moduleTreeService) {
		this.moduleTreeService = moduleTreeService;
	}

	public Long[] getModids() {
		return modids;
	}

	public void setModids(Long[] modids) {
		this.modids = modids;
	}

	public Long[] getOperids() {
		return operids;
	}

	public void setOperids(Long[] operids) {
		this.operids = operids;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getTreeName() {
		return treeName;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getRoleids() {
		return roleids;
	}

	public void setRoleids(String roleids) {
		this.roleids = roleids;
	}
//
//	public Long[] getIdi() {
//		return idi;
//	}
//
//	public void setIdi(Long[] idi) {
//		this.idi = idi;
//	}
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
