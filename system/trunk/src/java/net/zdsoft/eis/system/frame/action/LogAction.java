package net.zdsoft.eis.system.frame.action;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.UserLog;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserLogService;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.util.ExportUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import com.opensymphony.xwork2.ModelDriven;

public class LogAction extends PageAction implements ModelDriven<UserLog> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -786286079810998269L;

	private String modID = "SYS001";

	private LoginInfo loginInfo;

	private UserLogService userLogService;
	private ModuleService moduleService;
	private UnitService unitService;

	private List<UserLog> userLogList;
	private List<SubSystem> subSystemList;
	private List<Unit> unitList;
	private Integer subSystem;
	private String unitId;

	private Date beginTime;
	private Date endTime;

	private UserLog userLogDto = new UserLog();
	private Map<Object,Object> returnJsonData = new HashMap<Object,Object>();

	public String execute() {
		autoSetCondition();
		loginInfo = getLoginInfo();
		unitList = unitService.getAllUnits(loginInfo.getUnitID(), true);

		//只显示当前安装和启用的子系统
		subSystemList = moduleService.getSubSystems(loginInfo.getUnitClass(),
				loginInfo.getUnitType());

		unitId = loginInfo.getUnitID();
		return SUCCESS;
	}

	public String getLogList() {
		autoSetCondition();
		loginInfo = getLoginInfo();
		if(StringUtils.isBlank(unitId))
		    unitId = loginInfo.getUnitID();
		Unit unitDto = unitService.getUnit(unitId);
		userLogList = userLogService.getUserLogs(beginTime, endTime, subSystem,
				unitId, unitDto.getUnitclass(), this.getPage());
		return SUCCESS;
	}

	public String getLogExport() {
		autoSetCondition();
		loginInfo = getLoginInfo();
		Unit unitDto = unitService.getUnit(unitId);
		userLogList = userLogService.getUserLogs(beginTime, endTime, subSystem,
				unitId, unitDto.getUnitclass(), null);

		String[] fieldTitles = new String[] { "功能模块", "日志描述", "操作用户", "日志时间" };
		String[] propertyNames = new String[] { "modName", "description",
				"userName", "logTime" };
		Map<String, List<UserLog>> records = new HashMap<String, List<UserLog>>();
		records.put("日志信息", userLogList);
		String fileName = (null == beginTime ? null
				: DateFormatUtils.ISO_DATE_FORMAT.format(beginTime))
				+ " -- "
				+ (null == endTime ? null : DateFormatUtils.ISO_DATE_FORMAT
						.format(endTime)) + " Logger";

		ExportUtil exportUtil = new ExportUtil();
		exportUtil.exportXLSFile(fieldTitles, propertyNames, records, fileName);
		return NONE;
	}

	private void autoSetCondition() {
		loginInfo = getLoginInfo();
		if (beginTime == null) {
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE, -1);
			beginTime = ca.getTime();
		}
		if (endTime == null) {
			Calendar ca = Calendar.getInstance();
			endTime = ca.getTime();
		}
		if (userLogDto.getSubSystem() != null
				&& userLogDto.getSubSystem() == -1) {
			subSystem = null;
		} else {
			subSystem = userLogDto.getSubSystem();
		}
		if (userLogDto.getUnitid() != null
				&& userLogDto.getUnitid().equals("-1")) {
			unitId = loginInfo.getUnitID();
		} else {
			unitId = userLogDto.getUnitid();
		}
	}

	public String delLogs() {
		setPromptMessageDto(new PromptMessageDto());
		try {
			userLogService.deleteUserLogs(userLogDto.getArrayIds());
			promptMessageDto.setPromptMessage("删除成功!");
			promptMessageDto.setOperateSuccess(true);
			SystemLog.log(modID, "删除日志成功！");
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setPromptMessage("删除失败!");
			promptMessageDto.setOperateSuccess(false);
			SystemLog.log(modID, "删除日志失败！");
		}
		promptMessageDto.addOperation(new String[] {
				"确定",
				"platformInfoAdmin-logAdmin.action" });
		returnJsonData.put("promptMessageDto", promptMessageDto);
		return SUCCESS;
	}

	public void setUserLogService(UserLogService userLogService) {
		this.userLogService = userLogService;
	}

	public List<UserLog> getUserLogList() {
		return userLogList;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public List<SubSystem> getSubSystemList() {
		return subSystemList;
	}

	public List<Unit> getUnitList() {
		return unitList;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public UserLog getModel() {
		return this.userLogDto;
	}

	public UserLog getUserLogDto() {
		return userLogDto;
	}

	public Integer getSubSystem() {
		return subSystem;
	}

	public String getUnitId() {
		return unitId;
	}
	
	public Map<Object, Object> getReturnJsonData() {
		return returnJsonData;
	}

	public void setReturnJsonData(Map<Object, Object> returnJsonData) {
		this.returnJsonData = returnJsonData;
	}
}
