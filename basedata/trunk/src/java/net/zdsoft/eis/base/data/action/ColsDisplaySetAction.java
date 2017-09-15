package net.zdsoft.eis.base.data.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClass;
import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.service.BaseColsDisplayService;
import net.zdsoft.eis.frame.action.ModelBaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

import org.apache.commons.lang.StringUtils;

/**
 * 显示信息(列显示、不显示)设置的初始化、保存
 * 
 */
public class ColsDisplaySetAction extends ModelBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6512818703427030327L;

	private BaseColsDisplayService baseColsDisplayService;

	private List<ColsDisplay> displayList;

	private List<ColsDisplay> hideList;

	private BasicClassService basicClassService;

	private String schGUID; // 学校主键GUID，直接从logInfo中取得

	private String adjustIds;
	private String type;
	private String classid;
	private String backUrl;// 返回地址

	// 显示信息(列显示、不显示)设置页面，初始页面显示默认可选和已选择显示的字段
	public String execute() throws Exception {

		if (StringUtils.isNotBlank(classid)) {
			BasicClass clazz = basicClassService.getClass(classid);
			if (clazz != null)
				schGUID = clazz.getSchid();
		}

		if (StringUtils.isBlank(schGUID))
			schGUID = getLoginInfo().getUnitID();

		if (StringUtils.isBlank(schGUID)) {
			this.addActionError("没有取到单位（学校）GUID编号！");
			return ERROR;
		}

		// 检查是否已存在设置 //一学校的设置信息
		List<ColsDisplay> colsSchTypeList = baseColsDisplayService
				.getColsDisplays(schGUID, type);
		if (colsSchTypeList == null || colsSchTypeList.size() == 0) {

			// 没有该学校的数据 显示初始数据
			colsSchTypeList = baseColsDisplayService.getColsDisplays(
					BaseConstant.ZERO_GUID, type);
			if (colsSchTypeList == null || colsSchTypeList.size() == 0) {
				promptMessageDto = new PromptMessageDto();
				promptMessageDto.setErrorMessage("显示信息设置的数据初始化有误");
				return PROMPTMSG;
			}

		}

		displayList = baseColsDisplayService.getColsDisplays(schGUID, type, 1); // 1表示启用，0不启用
		Collections.sort(displayList, new Comparator<ColsDisplay>() {

			public int compare(ColsDisplay o1, ColsDisplay o2) {
				return o2.getColsConstraint() - o1.getColsConstraint();
			}
		});

		hideList = baseColsDisplayService.getColsDisplays(schGUID, type, 0);
		return SUCCESS;
	}

	// 保存设置
	public String saveColsDisplaySet() {
		String schID = this.getLoginInfo().getUnitID();
		baseColsDisplayService.saveColsDisplay(schID, schGUID, adjustIds, type);
		promptMessageDto = new PromptMessageDto();
		promptMessageDto.setPromptMessage("保存信息成功！");
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;

	}

	public void setBaseColsDisplayService(
			BaseColsDisplayService baseColsDisplayService) {
		this.baseColsDisplayService = baseColsDisplayService;
	}

	public List<ColsDisplay> getDisplayList() {
		return displayList;
	}

	public List<ColsDisplay> getHideList() {
		return hideList;
	}

	public void setSchGUID(String schGUID) {
		this.schGUID = schGUID;
	}

	public String getSchGUID() {
		return schGUID;
	}

	public String getAdjustIds() {
		return adjustIds;
	}

	public void setAdjustIds(String adjustStuid) {
		this.adjustIds = adjustStuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

}
