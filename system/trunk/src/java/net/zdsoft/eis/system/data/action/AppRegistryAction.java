package net.zdsoft.eis.system.data.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.AppRegistry;
import net.zdsoft.eis.base.common.service.AppRegistryService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.ModelBaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.leadin.exception.ItemExistsException;
import net.zdsoft.leadin.util.RegUtils;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: AppRegistryAction.java,v 1.12 2007/01/23 02:34:59 zhaosf Exp $
 */

public class AppRegistryAction extends ModelBaseAction {
    private static final long serialVersionUID = 3494871435108301711L;

    private String modID = "SYS400";//模块号

	private AppRegistryService appRegistryService;//应用注册信息
	private SubSystemService subSystemService;

	private AppRegistry dto = new AppRegistry();
	private List<AppRegistry> appRegistryList;
	private String targetid; //更新排序号的目标对象
	private String operateType;//操作类型：上移、下移
	private Map<String, String> idsMap = new HashMap<String, String>();//移动对象id

	/**
	 * 构造函数
	 */
	public AppRegistryAction() {
		promptMessageDto = new PromptMessageDto();
	}

	@Override
	public Object getModel() {
		return dto;
	}

	/**
	 * 取当前单位
	 */
	public String getUnitid() {
		return getLoginInfo().getUnitID(); // 当前用户所在单位
	}

	/**
	 * 编辑应用
	 */
	public String edit() {
		log.info("===edit is executing... ");

		String appid = dto.getId();
		if (org.apache.commons.lang.StringUtils.isBlank(appid)) {
			dto.setUrl("http://");
			dto.setIsusing(BaseConstant.STR_YES);
			dto.setIssync(BaseConstant.STR_YES);
			dto.setUnderlingUnitUse(BaseConstant.STR_NO);
			dto.setUnitid(this.getLoginInfo().getUnitID());
		} else {
			dto = appRegistryService.getAppRegistry(appid);
			AppRegistry appDto = this.remoteGetApplication(dto.getSysid());
			if (null != appDto) {
				dto.setUrlExample(appDto.getUrlExample());
			}
		}

		return SUCCESS;
	}

	/**
	 * 保存应用
	 */
	public String save() {
		log.info("===save is executing... ");

		try {
			if (Validators.isEmpty(dto.getSysid())) {
				addFieldError("sysid", "请选择应用");
			}

			if (Validators.isEmpty(dto.getAppname())) {
				addFieldError("appname", "注册名称不能为空");
			}

			if (Validators.isEmpty(dto.getAppcode())) {
				addFieldError("appcode", "注册码不能为空");
			}

			if (Validators.isEmpty(dto.getUrl())) {
				addFieldError("url", "应用主页不能为空");
			}

			if (!RegUtils.regexValidator("http://(\\S+)+$", dto.getUrl())) {
				addFieldError("url", "url格式不正确");
			}

			if (hasFieldErrors())
				return INPUT;

			if ("".equals(dto.getId()))
				dto.setId(null);
			if (dto.getIsusing() == null)
				dto.setIsusing(BaseConstant.STR_NO);
			if (dto.getIssync() == null)
				dto.setIssync(BaseConstant.STR_NO);
			if (dto.getUnderlingUnitUse() == null)
				dto.setUnderlingUnitUse(BaseConstant.STR_NO);
			appRegistryService.saveAppRegistry(dto);

			String msg = "应用注册[" + dto.getAppname() + "]成功";

			if (null == dto.getId()) {
				SystemLog.log(modID, "增加" + msg);
			} else {
				SystemLog.log(modID, "修改" + msg);
			}
		} catch (ItemExistsException e) {
			addFieldError(e.getField(), e.getMessage());
			return INPUT;

		} catch (Exception e) {
			String error = "保存应用注册信息出错: " + e.getMessage();
			log.error(error);
			addActionError(error);
			return INPUT;
		}

		promptMessageDto.setPromptMessage("保存应用注册信息成功");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "返回",
				"appRegistryAdmin.action" });
		return PROMPTMSG;
	}

	/**
	 * 删除应用
	 */
	public String delete() {
		log.info("===delete is executing... ");

		String appid = dto.getId();
		try {
			String[] ids = appid.split(BaseConstant.COMMA);
			for (int i = 0; i < ids.length; i++) {
				ids[i] = ids[i].trim();
			}
			String appnames = "";
			List<AppRegistry> appList = appRegistryService.getAppRegistries(ids);
			for (Object object : appList) {
				AppRegistry dto = (AppRegistry) object;
				appnames += BaseConstant.COMMA + dto.getAppname();
			}
			if (!"".equals(appnames))
				appnames = appnames.substring(1);

			appRegistryService.deleteAppRegistries(ids);

			SystemLog.log(modID, "批量删除应用注册[" + appnames + "]成功");
		} catch (Exception e) {
			String error = "删除应用注册信息出错: " + e.getMessage();
			log.error(error);
			addActionError(error);
			promptMessageDto.setErrorMessage(error);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"appRegistryAdmin.action" });
			return PROMPTMSG;
		}

		return SUCCESS;
	}

	/**
	 * 已注册的应用列表
	 */
	public String list() {
		log.info("===list is executing... ");

		appRegistryList = appRegistryService.getAppRegistries(this.getUnitid());
		return SUCCESS;
	}

	/**
	 * 更新显示顺序
	 */
	public String updateDisplayOrder() {
		log.info("===updateDisplayOrder is executing... ");

		String appid = dto.getId();
		try {
			String[] ids = appid.split(BaseConstant.COMMA);
			for (int i = 0; i < ids.length; i++) {
				ids[i] = ids[i].trim();
				idsMap.put(ids[i], ids[i]);
			}
			appRegistryService.updateDisplayOrder(ids, targetid, operateType);
		} catch (Exception e) {
			String error = "更新排序号出错: " + e.getMessage();
			log.error(error);
			addActionError(error);
			promptMessageDto.setErrorMessage(error);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"appRegistryAdmin.action" });
			return PROMPTMSG;
		}

		return this.list();
	}

	//配置文件中的应用列表
	@SuppressWarnings("unchecked")
	public List getAppList() {
		return appRegistryService.getAppsFromConfig();
	}

	//配置文件中的应信息
	public AppRegistry remoteGetApplication(String sysid) {
	    Map<String, AppRegistry> map = appRegistryService.getAppMapFromConfig();
		AppRegistry dto = (AppRegistry) map.get(sysid);
		return dto;
	}

	public List<AppRegistry> getAppRegistryList() {
		return appRegistryList;
	}

	public AppRegistry getDto() {
		return dto;
	}

	public String getTargetid() {
		return targetid;
	}

	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public Map<String, String> getIdsMap() {
		return idsMap;
	}

	public void setAppRegistryService(AppRegistryService appRegistryService) {
		this.appRegistryService = appRegistryService;
	}

	public SubSystemService getSubSystemService() {
		return subSystemService;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}
}
