package net.zdsoft.eis.base.data.action;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.common.entity.ColsDisplay;
import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseColsDisplayService;
import net.zdsoft.eis.base.data.service.BaseEduInfoService;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

/**
 * <p>ZDSoft学籍系统(stusys)V3.5</p>
 * <p> 教育局基本信息的action </p>
 * @author Zhanghh
 * @version $Revision: 1.9 $,  2006/10/17
 * @since 1.5
 */
public class EduInfoAction extends BaseAction implements ModelDriven<EduInfo> {

	private static final String DEFAULT_EDUID = BaseConstant.ZERO_GUID;
	private static final String STUDENT_INFO_MAINTAINCE_SWITCH = "STUSYS.STUDENT.INFO.MAINTAINCE.SWITCH";// 是否允许学校维护学生信息
	private static final String CONFIGE_TYPE_STUDENT_AUDIT = "studentaudit";
	
	private static final long serialVersionUID = -2619638553342947118L;

	private String eduid;// 当前教育局编号

	private String adjustIds;

	private String allowed = "0";

	private EduInfo eduInfo = new EduInfo(); // 显示和接收表单

	private BaseEduInfoService baseEduInfoService; // 教育局
	private BaseUnitService baseUnitService;

	private BaseColsDisplayService baseColsDisplayService;

	private BaseSystemIniService baseSystemIniService;

	private List<Unit> underlingEduList;

	private List<ColsDisplay> displayList;

	private List<ColsDisplay> hideList;

	/*
	 * 显示教育局信息
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SuppressWarnings("unchecked")
	public String execute() throws Exception {

		if (!this.loadEduid()) {
			this.addActionError("没有取到单位（教育局）编号！");
			return ERROR;
		}

		// 获取下属教育局列表,必须从所在单位取
		underlingEduList = baseUnitService.getUnderlingUnits(this.getLoginInfo()
				.getUnitID(), 1);
		Unit unit1 = baseUnitService.getUnit(this.getLoginInfo().getUnitID());
		underlingEduList.add(0, unit1);

		eduInfo = baseEduInfoService.getBaseEduInfo(eduid); // 从数据库里取出dto
		if (null == eduInfo) {
			eduInfo = new EduInfo();
			eduInfo.setIsAutonomy(false);
			eduInfo.setIsFrontier(false);
			eduInfo.setCreationTime(new Date());
			eduInfo.setModifyTime(new Date());
			eduInfo.setIsdeleted(false);
			eduInfo.setIsUseDomain(false);

			BaseUnit unit = baseUnitService.getBaseUnit(eduid);
			eduInfo.setId(unit.getId());
			eduInfo.setName(unit.getName());
			eduInfo.setTelephone(unit.getLinkPhone());
			eduInfo.setPostalcode(unit.getPostalcode());
			eduInfo.setAddress(unit.getAddress());
			eduInfo.setFax(unit.getFax());
			eduInfo.setEmail(unit.getEmail());
			eduInfo.setCreationTime(unit.getCreationTime());
			eduInfo.setPrincipal(unit.getLinkMan());
			eduInfo.setEduCode(unit.getUnionid());
		}

		return SUCCESS;
	}

	public String stuAuditConf() {
		eduid = this.getLoginInfo().getUnitID();
		String type = CONFIGE_TYPE_STUDENT_AUDIT;
		if (StringUtils.isBlank(eduid)) {
			this.addActionError("没有取到单位（教育局）GUID编号！");
			return ERROR;
		}
		// 检查是否已存在设置 --教育局的设置信息
		List<ColsDisplay> colsSchTypeList = baseColsDisplayService
				.getColsDisplays(eduid, type);
		if (colsSchTypeList == null || colsSchTypeList.size() == 0) {

			// 没有该教育局的数据 显示初始数据
			colsSchTypeList = baseColsDisplayService.getColsDisplays(
					DEFAULT_EDUID, type);
			if (colsSchTypeList == null || colsSchTypeList.size() == 0) {
				promptMessageDto = new PromptMessageDto();
				promptMessageDto.setErrorMessage("显示信息设置的数据初始化有误");
				return PROMPTMSG;
			} else {
				eduid = DEFAULT_EDUID;
			}

		}
		allowed = baseSystemIniService.getSystemIni(
				STUDENT_INFO_MAINTAINCE_SWITCH)
				.getNowValue();
		displayList = baseColsDisplayService.getColsWithoutDefault(eduid, type,
				1); // 1表示启用，0不启用
		Collections.sort(displayList, new Comparator<ColsDisplay>() {

			public int compare(ColsDisplay o1, ColsDisplay o2) {
				return o2.getColsConstraint() - o1.getColsConstraint();
			}
		});

		hideList = baseColsDisplayService.getColsWithoutDefault(eduid, type, 0);
		return SUCCESS;
	}

	/**
	 * 保存学生审核信息设置
	 * @return
	 */
	public String confSave() {
		String eduID = this.getLoginInfo().getUnitID();
		try {
			baseColsDisplayService.saveColsDisplay(eduID, eduid, adjustIds,
					CONFIGE_TYPE_STUDENT_AUDIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		baseSystemIniService.saveMod(
				STUDENT_INFO_MAINTAINCE_SWITCH, allowed);
		promptMessageDto = new PromptMessageDto();
		promptMessageDto.setPromptMessage("保存信息成功！");
		promptMessageDto.setOperateSuccess(true);
		String back = "stuAuditConfigAdmin.action";

		promptMessageDto.addOperation(new String[] { "返回", back });
		return PROMPTMSG;

	}

	/**
	 * 保存教育局信息
	 * @return
	 */
	public String save() {

		// 保存
		eduInfo.setId(eduid); // eduid在dto中返回,所以不用再取一次
		baseEduInfoService.updateUnitByEduInfo(eduInfo);

		setPromptMessageDto(new PromptMessageDto());
		promptMessageDto.setPromptMessage("保存成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "返回",
				"eduinfo.action?abc=123&eduid=" + eduid });

		return PROMPTMSG;
	}

	/**
	 * 从参数中取得教育局ID,若没有就取当前所在单位
	 * @return 返回true为有效教育局
	 */
	public boolean loadEduid() {

		// 从参数中取得下属教育局ID,若没有就取当前所在单位
		eduid = this.getRequest().getParameter("eduid");
		if (eduid != null && eduid.length() > 0)
			return true;

		// 获取单位ID
		if (this.getLoginInfo() != null) {
			eduid = this.getLoginInfo().getUnitID();
		}
		if (eduid != null && eduid.length() > 0)
			return true;

		return false;
	}

	public EduInfo getModel() {
		return eduInfo;
	}

	/**
	 * @return eduid
	 */
	public String getEduid() {
		return eduid;
	}

	/**
	 * @param eduid 要设置的 eduid
	 */
	public void setEduid(String eduid) {
		this.eduid = eduid;
	}

	/**
	 * @return eduInfo
	 */
	public EduInfo getEduInfo() {
		return eduInfo;
	}

	/**
	 * @param eduInfo 要设置的 eduInfo
	 */
	public void setEduInfo(EduInfo eduInfo) {
		this.eduInfo = eduInfo;
	}

	public void setBaseEduInfoService(BaseEduInfoService baseEduInfoService) {
		this.baseEduInfoService = baseEduInfoService;
	}	

	public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    /**
	 * @return underlingEduList
	 */
	public List<Unit> getUnderlingEduList() {
		return underlingEduList;
	}

	public List<ColsDisplay> getDisplayList() {
		return displayList;
	}

	public List<ColsDisplay> getHideList() {
		return hideList;
	}

	public void setBaseColsDisplayService(
			BaseColsDisplayService baseColsDisplayService) {
		this.baseColsDisplayService = baseColsDisplayService;
	}

	public String getAdjustIds() {
		return adjustIds;
	}

	public void setAdjustIds(String adjustStuid) {
		this.adjustIds = adjustStuid;
	}

	public void setBaseSystemIniService(
			BaseSystemIniService baseSystemIniService) {
		this.baseSystemIniService = baseSystemIniService;
	}

	public void setAllowed(String allowed) {
		this.allowed = allowed;
	}

	public String getAllowed() {
		return allowed;
	}

}
