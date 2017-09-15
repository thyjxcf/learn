package net.zdsoft.eis.base.data.action;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.common.entity.EduInfo;
import net.zdsoft.eis.base.common.entity.SchoolDistrict;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.EduInfoService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SchoolDistrictService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.entity.BaseSchool;
import net.zdsoft.eis.base.data.service.BaseClassService;
import net.zdsoft.eis.base.data.service.BaseGradeService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.remote.param.dto.InParamDto;
import net.zdsoft.eis.base.remote.param.service.CommonParamService;
import net.zdsoft.eis.base.remote.service.JwSchoolinfoService;
import net.zdsoft.eis.base.util.SoapProxy;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.keel.util.StringUtils;

/* 
 * <p>ZDSoft学籍系统(stusys)V3.5</p>
 * <p> 学校基本信息的action </p>
 * @author zhongh
 * @since 1.0
 * @version $Id: SchoolInfoAction.java,v 1.11 2006/12/21 02:11:47 zhanghh Exp $
 */
public class SchoolInfoAction extends BaseAction implements ModelDriven<BaseSchool> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(SchoolInfoAction.class);

	private BaseSchool schoolinfo = new BaseSchool(); // 显示和接收表单

	private RegionService regionService;
	private CommonParamService commonParamService;

	private String operation; // 操作方式
	private String introduction; // 历史沿革
	private String schid; // 学校编号
	private String typemessage; // 学校类别的可修改控制,提示信息
	private boolean typecontrol = false; // 学校类别的修改控制,true表示不能修改
	private boolean selfDeploy = false; // 是否独立布署

	private BaseSchoolService baseSchoolService;
	private BaseClassService baseClassService;
	private BaseGradeService baseGradeService;
	private SchoolDistrictService schoolDistrictService;
	//private AppRegistryService appRegistryService;
	private EduInfoService eduInfoService; // 教育局
	private boolean gradeyear_readonly; // 小学规定年制是否可修改
	private boolean junioryear_readonly; // 中学规定年制是否可修改
	private boolean senioryear_readonly; // 高中规定年制是否可修改

	private boolean infantYearReadonly; // 幼儿园规定年制是否可修改

	private UnitService unitService;

	private List<SchoolDistrict> schDistriList; // 学区列表

	private String regionName;
	private String regionId;

	public String execute() throws Exception {
		String result;

		if (this.getLoginInfo() != null) {
			schid = this.getLoginInfo().getUnitID();
		}

		if (schid == null || "".equals(schid)) {
			this.addActionError("没有取到单位（学校）GUID编号！");
			return ERROR;
		}

		if (operation == null) {// 显示详情
			this.getSchoolinfoDto();
			result = "detail";
		} else if (operation.equals("save")) {// 保存
			// 检查学校编号是否已经存在
			String temp = baseSchoolService.getSchoolIdByCode(schoolinfo
					.getCode());
			if (temp != null && !temp.equals(schid)) {
				addFieldError("code", "学校代码已存在！");
				return "detail";
			}

			// if (StringUtils.getRealLength(schoolinfo.getIntroduction()) >
			// 400) {
			// addFieldError("introduction", "学校历史沿革不能超过400个字符！");
			// return "detail";
			// }

			if (StringUtils.getRealLength(schoolinfo.getArea()) > 20) {
				addFieldError("area", "占地面积不能超过20个字符！");
				return "detail";
			}
			// serialNum
			if ((schoolinfo.getEtohSchoolId() == null || "".equals(schoolinfo
					.getEtohSchoolId()))) {
				Unit unit = unitService.getUnit(schid);
				String eToHSchoolId = "";
				if (unit != null) {
					eToHSchoolId = unit.getEtohSchoolId();
				}
				schoolinfo.setEtohSchoolId(eToHSchoolId);
			}
			this.saveSchoolinfo();
			setPromptMessageDto(new PromptMessageDto());
			promptMessageDto.setPromptMessage("保存成功！");
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.addOperation(new String[] { "返回",
					"schoolinfo.action" });
			result = PROMPTMSG;
		} else if (operation.equals("detail")) {
			this.getSchoolinfoDto();
			result = "detail";
		} else {
			result = "main";
		}
		return result;
	}

	// 独立布署的学校远程验证学校编号是否存在
	public String checkRemoteCode(String code) {
		log.debug("checkRemoteCode action......");

		// 独立学校
		if (isSchoolSelfDeploy()) {
			JwSchoolinfoService jwSchoolinfoService = null;
			try {
				jwSchoolinfoService = (JwSchoolinfoService) SoapProxy
						.newInstance(JwSchoolinfoService.class);
			} catch (Exception e) {
				log.error(e.getMessage());
				return "不能远程校验，创建远程服务失败！提示信息：" + e.getMessage();
			}

			// 测试目标服务是否正常
			String result = jwSchoolinfoService.test();
			if (!BaseConstant.SUCCESS.equals(result)) {
				return "不能远程校验，目标服务异常！";
			}

			// 本地校验单位有效性参数
			InParamDto inParamDto = null;
			try {
				inParamDto = commonParamService.getInParamDto();
			} catch (Exception e) {
				log.error(e.getMessage());
				return "不能远程校验，单位数据本地有误！提示信息：" + e.getMessage();
			}

			// 检查学校编号是否已经存在(true存在，false不存在)
			boolean flag = false;
			if (this.getLoginInfo() != null) {
				schid = this.getLoginInfo().getUnitID();
			}
			try {
				flag = jwSchoolinfoService.checkCodeIsExist(inParamDto, schid,
						code);
			} catch (Exception e) {
				log.error(e.getMessage());
				return "不能远程校验，单位数据远程有误！提示信息：" + e.getMessage();
			}

			return String.valueOf(flag);

		} else {
			return "不能远程验证，学校不是独立布署！";
		}
	}

	/**
	 * 获得用来显示的dto
	 */
	@SuppressWarnings("null")
	private void getSchoolinfoDto() {
		// 从数据库里取出dto
		schoolinfo = baseSchoolService.getBaseSchool(schid);

		// 所在学区编码转换为名称
		if (schoolinfo.getSchdistrictid() != null
				&& !schoolinfo.getSchdistrictid().trim().equals("")) {
			SchoolDistrict district = schoolDistrictService
					.getSchoolDistrict(schoolinfo.getSchdistrictid());
			if (district == null) {
				schoolinfo.setSchdistrictname("");
			} else {
				schoolinfo.setSchdistrictname(district.getName());
			}
		}

		Unit unit = unitService.getUnit(schid);
		if ((schoolinfo.getEtohSchoolId() == null || "".equals(schoolinfo
				.getEtohSchoolId()))) {
			String eToHSchoolId = "";
			if (unit != null) {
				eToHSchoolId = unit.getEtohSchoolId();
			}
			schoolinfo.setEtohSchoolId(eToHSchoolId);
		}

		/* 检查该学校下是否已设置了班级,如果已设置了班级,就不能修改学校类别 */
		if (baseClassService.isExistsClass(schid)) {
			typecontrol = true;
			typemessage = "该学校类别下已有班级在使用，不能修改！";
		}
		if (baseClassService.isExistsClass(schid, 0)) {
			infantYearReadonly = true;
		}
		// 该学校下没有小学班级时小学规定年制才可修改
		if (baseClassService.isExistsClass(schid, 1)) {
			gradeyear_readonly = true;
		}
		// 该学校下没有中学班级时中学规定年制才可修改
		if (baseClassService.isExistsClass(schid, 2)) {
			junioryear_readonly = true;
		}
		// 该学校下没有高中班级时高中规定年制才可修改
		if (baseClassService.isExistsClass(schid, 3)) {
			senioryear_readonly = true;
		}
		// 小学默认年制是6年
		if (schoolinfo.getGradeyear() == 0) {
			schoolinfo.setGradeyear(6);
		}
		// 初中默认年制是3年
		if (schoolinfo.getJunioryear() == 0) {
			schoolinfo.setJunioryear(3);
		}
		// 高中默认年制是3年
		if (schoolinfo.getSenioryear() == 0) {
			schoolinfo.setSenioryear(3);
		}
		// 小学默认入学年龄
		if (schoolinfo.getGradeage() == 0) {
			schoolinfo.setGradeage(8);
		}
		// 初中默认入学年龄
		if (schoolinfo.getJuniorage() == 0) {
			schoolinfo.setJuniorage(13);
		}
		// 学校是否独立布署
		selfDeploy = isSchoolSelfDeploy();
		EduInfo edu=eduInfoService.getEduInfo(unit.getParentid());
		Assert.notNull(edu, "学校对应的上级单位在base_eduinfo中没有对应记录");
		schoolinfo.setEducode(edu.getEduCode());
	}

	/**
	 * 学区基本信息列表
	 */
	public List<SchoolDistrict> getSchDistriList() {
		String eduid = unitService.getUnit(getLoginInfo().getUnitID())
				.getParentid();
		schDistriList = schoolDistrictService.getSchoolDistricts(eduid);
		return schDistriList;
	}

	/**
	 * 保存学校基本信息
	 */
	private void saveSchoolinfo() {
		schoolinfo.setId(schid);
		schoolinfo.setCreationTime(new Date());
		baseSchoolService.updateSchool(schoolinfo);
		try {
			List<String[]> sectionList;
			sectionList = baseSchoolService.getSchoolSections(schid);
			if (sectionList.size() > 0) {
				String section = sectionList.get(0)[1];
//				if (org.apache.commons.lang.StringUtils.isNotBlank(section)) {
//					baseGradeService.initGrades(schid);
//				}
			}
		} catch (Exception e) {
			log.error("initial grade Info error!", e);
			//			e.printStackTrace();
		}
	}

	public BaseSchool getModel() {
		return this.schoolinfo;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public BaseSchool getSchoolinfo() {
		return schoolinfo;
	}

	public void setSchoolinfo(BaseSchool schoolinfodto) {
		this.schoolinfo = schoolinfodto;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setSchid(String schid) {
		this.schid = schid;
	}

	public void setBaseClassService(BaseClassService baseClassService) {
        this.baseClassService = baseClassService;
    }

    public boolean isTypecontrol() {
		return typecontrol;
	}

	public String getTypemessage() {
		return typemessage;
	}

	public boolean isGradeyear_readonly() {
		return gradeyear_readonly;
	}

	public void setGradeyear_readonly(boolean gradeyear_readonly) {
		this.gradeyear_readonly = gradeyear_readonly;
	}

	public boolean isJunioryear_readonly() {
		return junioryear_readonly;
	}

	public void setJunioryear_readonly(boolean junioryear_readonly) {
		this.junioryear_readonly = junioryear_readonly;
	}

	public boolean isSenioryear_readonly() {
		return senioryear_readonly;
	}

	public void setSenioryear_readonly(boolean senioryear_readonly) {
		this.senioryear_readonly = senioryear_readonly;
	}

	public String getOperation() {
		return operation;
	}

	public String getSchid() {
		return schid;
	}

	public void setTypecontrol(boolean typecontrol) {
		this.typecontrol = typecontrol;
	}

	public void setTypemessage(String typemessage) {
		this.typemessage = typemessage;
	}

	public boolean isSelfDeploy() {
		return selfDeploy;
	}

	public void setSelfDeploy(boolean selfDeploy) {
		this.selfDeploy = selfDeploy;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	//	public void setAppRegistryService(AppRegistryService appRegistryService) {
	//		this.appRegistryService = appRegistryService;
	//	}

	public String getRegionName() {
		if (schoolinfo != null) {
			regionName = schoolinfo.getRegionname();
			if (regionName == null || "".equals(regionName)) {
				regionName = regionService.getFullName(getRegionId());
			}
		}
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionId() {
		if (schoolinfo != null) {
			regionId = schoolinfo.getRegion();
			if (regionId == null || regionId.equals("")) {
				Unit unitDto = unitService.getUnit(getLoginInfo().getUnitID());
				if (null != unitDto) {
					String unionId = unitDto.getUnionid();
					if (unionId != null) {
						if (unionId.length() > 6) {
							unionId = unionId.substring(0, 6);
						}
						regionId = unionId;
					}
				}
			}
		}
		return regionId;
	}

	public void setRegionId(String region) {
		this.regionId = region;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setEduInfoService(EduInfoService eduInfoService) {
		this.eduInfoService = eduInfoService;
	}

	public boolean isInfantYearReadonly() {
		return infantYearReadonly;
	}

	public void setInfantYearReadonly(boolean infantYearReadonly) {
		this.infantYearReadonly = infantYearReadonly;
	}

	public void setBaseGradeService(BaseGradeService baseGradeService) {
		this.baseGradeService = baseGradeService;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
		this.baseSchoolService = baseSchoolService;
	}

	public void setSchoolDistrictService(
			SchoolDistrictService schoolDistrictService) {
		this.schoolDistrictService = schoolDistrictService;
	}

	public void setCommonParamService(CommonParamService commonParamService) {
		this.commonParamService = commonParamService;
	}

}
