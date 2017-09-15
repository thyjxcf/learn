package net.zdsoft.eis.base.data.action;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.SerialRegisterService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.frame.serial.SerialManager;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;
import net.zdsoft.keel.util.ServletUtils;
import net.zdsoft.keel.util.SpellUtils;
import net.zdsoft.keel.util.StringUtils;
import net.zdsoft.keel.util.Validators;
import net.zdsoft.license.LicenseInfo;
import net.zdsoft.license.service.LicenseService;

import com.opensymphony.xwork2.ModelDriven;

public class SerialRegisterAction extends BaseAction implements
		ModelDriven<Object> {

	private static final long serialVersionUID = -3644894249900293481L;

	private McodedetailService mcodedetailService;
	private RegionService regionService;
	private SerialRegisterService serialRegisterService;
	private BaseUnitService baseUnitService;
	private SerialManager serialManager;

	private SubSystemService subSystemService;
	private ModuleService moduleService;
	private ModelOperatorService modelOperatorService;

	private String province;// 省
	private String city;// 市
	private String county;// 县区
	private String msg;

	private List<Region> provinceList;// 省列表
	private List<Region> cityList;// 城市列表
	private List<Region> countyList;// 县区列表

	private String country;// 乡镇教育局
	private boolean addAdmin;// 是否新增管理员
	private String newOrUpdate = "new";// 初次注册还是修改注册
	private BaseUnit unitDto = new BaseUnit();

	private String pwd;
	private String username;

	private String licenseTxt;
	private LicenseService licenseService;

	public String execute() throws Exception {
		// 取得顶级单位的信息
		getFormValue();
		Unit unit = baseUnitService.getTopEdu();
		if (unit == null) {
			return SUCCESS;
		}

		newOrUpdate = "update";
		// 检查是否能打开注册页面//"sys010"序列号注册
		/*
		 * if (getLoginInfo() != null && (getLoginInfo().validateAllModel(3010)
		 * || getLoginInfo().validateAllModel(3510) ||
		 * getLoginInfo().getUser().getType() == 0 || getLoginInfo().getUser()
		 * .getType() == 1) &&
		 * getLoginInfo().getUnitID().equals(unitDto.getId())) {
		 * 
		 * } else { setPromptMessageDto(new PromptMessageDto());
		 * promptMessageDto.setPromptMessage("只有顶级单位的有权限的用户才能进行序列号注册");
		 * promptMessageDto.setOperateSuccess(true);
		 * 
		 * return PROMPTMSG; }
		 */

		return SUCCESS;
	}

	public String initTopUnitAdmin() throws Exception {
		Unit topUnit = baseUnitService.getTopEdu();
		if (topUnit != null
				&& org.apache.commons.lang.StringUtils.isNotBlank(topUnit
						.getId())) {
			ServletUtils.print(getResponse(), "顶级单位已经存在，不需要再注册！！");
			return ERROR;
		}

		unitDto = new BaseUnit();
		unitDto.setCreationTime(new Date());
		provinceList = regionService.getSubRegionsBy2();
		if (province != null) {
			cityList = regionService.getSubRegions(province);
		}
		if (city != null) {
			countyList = regionService.getSubRegions(city);
		}
		return SUCCESS;
	}

	public String saveTopUnit() throws Exception {
		provinceList = regionService.getSubRegionsBy2();
		if (province != null) {
			cityList = regionService.getSubRegions(province);
		}
		if (city != null) {
			countyList = regionService.getSubRegions(city);
		}
		String result = null;
		if (Validators.isEmpty(unitDto.getName())
				|| StringUtils.getRealLength(unitDto.getName()) > 150) {
			addFieldError("name", "单位名称不能为空！并且长度不能超过150个字符！");
			result = INPUT;
		}
		if (Validators.isEmpty(unitDto.getUnitusetype())) {
			addFieldError("unitusetype", "必需要选择单位使用类别！");
			result = INPUT;
		}
		if ((province == null || province.equals(""))
				&& newOrUpdate.equals("new")) {// 初次注册时可以选择单位行政级别
			addFieldError("province", "必需要选择单位行政级别!");
			result = INPUT;
		}

		if (!Validators.isEmpty(result)) {
			return result;
		}

		try {
			initUnitDto();
			User userDto = initUserDto();
			baseUnitService.saveUnit(unitDto, userDto);
			subSystemService.clearCache();
			moduleService.clearCache();
			modelOperatorService.clearCache();
		} catch (Exception e) {
			log.error("初始化顶级单位失败！信息：" + e.getMessage(), e);
			addActionError("保存顶级单位信息出错：" + e.getMessage());
			return INPUT;
		}

		addActionMessage("保存顶级单位信息成功！");
		return SUCCESS;
	}

	private void initUnitDto() {
		// 去掉字符串里的空字符
		if ("".endsWith(unitDto.getId().trim()))
			unitDto.setId(null);

		unitDto.setName(unitDto.getName().replaceAll(" ", ""));
		unitDto.setMark(BaseUnit.UNIT_MARK_NORAML);
		unitDto.setAuthorized(1);
		unitDto.setTeacherEnableSms(1);
		unitDto.setGuestbookSms(1);
		unitDto.setParentid(BaseUnit.TOP_UNIT_GUID);
		unitDto.setCreationTime(new Date());
		unitDto.setModifyTime(new Date());
		unitDto.setIsdeleted(false);

		if (province != null && !province.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_PROVINCE);
		}
		if (city != null && !city.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_CITY);
		}
		if (county != null && !county.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_COUNTY);
		}
		if (county != null && !county.equals("") && country != null
				&& !country.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_LEVEL);
		}
		String unionid = serialRegisterService.createUnionId(province, city,
				county, unitDto);
		unitDto.setUnionid(unionid);
//		unitDto.setOrderid(unitDto.getUnionid());
		 if(org.apache.commons.lang3.StringUtils.isNotBlank(unitDto.getUnionid()))
				unitDto.setOrderid(NumberUtils.toLong(unitDto.getUnionid().replaceAll("[^0-9]", "")));
		String region = null;
		if (county != null && !county.equals("")) {
			region = county;
		} else if (city != null && !city.equals("")) {
			region = city;
		} else if (province != null && !province.equals("")) {
			region = province;
		}
		String regionCode = regionService.getRegion(region).getFullCode();
		unitDto.setRegion(regionCode);
	}

	private User initUserDto() {
		User userDto = new User();
		userDto.setName(unitDto.getAdminName());
		userDto.setPassword(unitDto.getPassword());
		userDto.setMark(User.USER_MARK_NORMAL);
		// userDto.setType(1);
		userDto.setType(User.USER_TYPE_TOPADMIN);
		userDto.setOwnerType(User.TEACHER_LOGIN);

		return userDto;
	}

	/**
	 * 系统初次注册
	 * 
	 * @return
	 */
	public String initRegist() {
		// 取得顶级单位的信息
		getFormValue();

		// 检查是否能打开注册页面
		String[] tempStr = serialRegisterService.VerifySerial(
				unitDto.getName(), unitDto.getRegcode());
		if (!tempStr[0].equals("")
				|| !serialRegisterService.VerifySerial().equals("")) {// 系统未注册,或超过有效期

		} else {
			setPromptMessageDto(new PromptMessageDto());
			promptMessageDto
					.setPromptMessage("系统已经注册成功!要修改注册信息，请进入系统管理的序列号注册。");
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.addOperation(new String[] { "关闭", "",
					"window.close()" });

			return PROMPTMSG;
		}

		return SUCCESS;
	}

	/**
	 * 取得页面上要显示的值
	 * 
	 */
	private void getFormValue() {
		unitDto = serialRegisterService.getTopUnit();

		if (unitDto != null) {
			String unionId = unitDto.getUnionid();
			if (unionId.length() >= 6)
				county = unionId.substring(0, 6);
			if (unionId.length() >= 4)
				city = unionId.substring(0, 4);
			if (unionId.length() >= 2)
				province = unionId.substring(0, 2);
		} else {
			unitDto = new BaseUnit();
			unitDto.setCreationTime(new Date());
		}
		if (unitDto.getCreationTime() == null) {
			unitDto.setCreationTime(new Date());
		}

		provinceList = regionService.getSubRegionsBy2();
		if (province != null) {
			cityList = regionService.getSubRegions(province);
		}
		if (city != null) {
			countyList = regionService.getSubRegions(city);
		}
		licenseTxt = licenseService.getEncryptedLicenseStr();
	}

	/**
	 * 保存注册信息
	 * 
	 * @return
	 */
	public String Update() {

		// 更新序列号时,provice等不提交,为了出错时返回显示,这里重现取一下
		if (newOrUpdate.equals("update")) {
			Unit tmpUnit = baseUnitService.getTopEdu();
			String unionId = tmpUnit.getUnionid();
			if (unionId.length() >= 6)
				county = unionId.substring(0, 6);
			if (unionId.length() >= 4)
				city = unionId.substring(0, 4);
			if (unionId.length() >= 2)
				province = unionId.substring(0, 2);
		}

		provinceList = regionService.getSubRegionsBy2();
		if (province != null) {
			cityList = regionService.getSubRegions(province);
		}
		if (city != null) {
			countyList = regionService.getSubRegions(city);
		}
		String result = null;
		if (Validators.isEmpty(unitDto.getName())
				|| StringUtils.getRealLength(unitDto.getName()) > 150) {
			addFieldError("name", "单位名称不能为空！并且长度不能超过150个字符！");
			result = INPUT;
		}
		if (Validators.isEmpty(licenseTxt)) {
			addFieldError("regcode", "序列串不能为空！");
			result = INPUT;
		}
		if (Validators.isEmpty(unitDto.getUnitusetype())) {
			addFieldError("unitusetype", "必需要选择单位使用类别！");
			result = INPUT;
		}
		if ((province == null || province.equals(""))
				&& newOrUpdate.equals("new")) {// 初次注册时可以选择单位行政级别
			addFieldError("province", "必需要选择单位行政级别!");
			result = INPUT;
		}

		if (!Validators.isEmpty(result)) {
			return result;
		}

		if (newOrUpdate.equals("new")) {
			if (username == null || username.trim().equals("")) {
				addFieldError("username", "账号不能为空");
				return INPUT;
			}

			if (pwd == null || pwd.trim().equals("")) {
				addFieldError("pwd", "密码不能为空");
				return INPUT;
			}
		}
		// 验证注册序列号
		PromptMessageDto msgDto = licenseService.verifyLicense(
				unitDto.getName(), licenseTxt);
		if (msgDto.getOperateSuccess() == false) {
			addFieldError("licenseTxt", msgDto.getErrorMessage());
			return INPUT;
		}

		LicenseInfo licenseInfo = licenseService.decodeLicense(licenseTxt);

		String productCode = licenseInfo.getProductCode();
		if (productCode.endsWith("S")) {
			unitDto.setUnitclass(2);
		} else {
			unitDto.setUnitclass(1);
		}

		// 检验单位类型和单位使用类别是否对应
		if (unitDto.getUnitclass().compareTo(BaseUnit.UNIT_CLASS_EDU) == 0) {
			if (!unitDto.getUnitusetype().equals("01")) {// 单位使用类别不是教育局
				addFieldError("unitusetype", "单位使用类别必需是教育局！");
				return INPUT;
			}
		} else if (unitDto.getUnitclass().compareTo(BaseUnit.UNIT_CLASS_SCHOOL) == 0) {
			if (unitDto.getUnitusetype().equals("01")) {// 单位使用类别不是教育局
				addFieldError("unitusetype", "单位使用类别不能是教育局！");
				return INPUT;
			}
		}
		// 检验这次注册是否改变了单位类型，unitclass
		if (unitDto.getId() != null && !unitDto.getId().equals("")) {
			BaseUnit t_unitDto = serialRegisterService.getTopUnit();
			if (t_unitDto.getUnitclass() != null
					&& unitDto.getUnitclass().compareTo(
							t_unitDto.getUnitclass()) != 0) {
				String returnMessage = "";
				if (t_unitDto.getUnitclass().compareTo(BaseUnit.UNIT_CLASS_EDU) == 0) {
					returnMessage = "教育局";
				} else if (t_unitDto.getUnitclass().compareTo(
						BaseUnit.UNIT_CLASS_SCHOOL) == 0) {
					returnMessage = "学校";
				} else {
					returnMessage = "原来";
				}

				addFieldError("regcode", "输入的注册码不是" + returnMessage + "类型的注册码！");
				return INPUT;
			}
		}

		// 设置regionlevel
		if (province != null && !province.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_PROVINCE);
		}
		if (city != null && !city.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_CITY);
		}
		if (county != null && !county.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_COUNTY);
		}
		if (county != null && !county.equals("") && country != null
				&& !country.equals("")) {
			unitDto.setRegionlevel(BaseUnit.UNIT_REGION_LEVEL);
		}
		// 生成unionid
		String temp_unionid = serialRegisterService.createUnionId(province,
				city, county, unitDto);

		unitDto.setUnionid(temp_unionid);
		unitDto.setParentid(BaseUnit.TOP_UNIT_GUID);
		// 顶级单位是学校
		if (unitDto.getUnitclass() == 2) {
			unitDto.setRegion(temp_unionid.substring(0, 6));
		} else {
			unitDto.setRegion(temp_unionid);
		}

		// 去掉字符串里的空字符
		unitDto.setName(unitDto.getName().replaceAll(" ", ""));

		// 保存注册信息
		User userDto = new User();
		boolean update = true;
		if (newOrUpdate.equals("new")) {
			update = false;
			userDto.setName(username);
			userDto.setPassword(pwd);
			userDto.setEmail(username + "@default.com");
			userDto.setType(User.TYPE_TOPADMIN);
		}
		msgDto = serialRegisterService.registerTopUnit(unitDto, userDto,
				licenseTxt, update);
		if (msgDto.getOperateSuccess() == false) {
			addActionError("注册失败,原因:" + msgDto.getErrorMessage());
			return INPUT;
		}

		// 修改版本信息
		// systemVersionService.updateCurProduct(unitDto.getUnitclass().toString());

		if (addAdmin)
			unitDto.setAdminName(SpellUtils.getFirstSpell(unitDto.getName())
					+ "_admin");

		addActionMessage("注册成功！");

		newOrUpdate = "update";
		getFormValue();
		return SUCCESS;
	}

	public String simpleValidateInfo() {
		msg = serialManager.verifySerial();
		return SUCCESS;
	}

	/**
	 * buffalo 接口：读取制定代码的下级行政区划列表
	 * 
	 * @param code
	 * @return
	 */
	public List<Region> RemoteRegion(String code) {
		List<Region> regionList = regionService.getSubRegions(code);
		return regionList;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	// end 2009-1-7

	/**
	 * @param serialRegisterService
	 *            the serialRegisterService to set
	 */
	public void setSerialRegisterService(
			SerialRegisterService serialRegisterService) {
		this.serialRegisterService = serialRegisterService;
	}

	public List<Region> getProvinceList() {
		return provinceList;
	}

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public List<Mcodedetail> getUnitClassList() {
		return mcodedetailService.getMcodeDetails("DM-DWLX");// 单位类型微代码;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public List<Region> getCityList() {
		return cityList;
	}

	public List<Region> getCountyList() {
		return countyList;
	}

	public BaseUnit getUnit() {
		return unitDto;
	}

	public Object getModel() {
		return this.unitDto;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public boolean isAddAdmin() {
		return addAdmin;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public UnitService getUnitService() {
		return baseUnitService;
	}

	public String getNewOrUpdate() {
		return newOrUpdate;
	}

	public void setNewOrUpdate(String newOrUpdate) {
		this.newOrUpdate = newOrUpdate;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public void setModelOperatorService(
			ModelOperatorService modelOperatorService) {
		this.modelOperatorService = modelOperatorService;
	}

	public void setLicenseTxt(String licenseTxt) {
		this.licenseTxt = licenseTxt;
	}

	public String getLicenseTxt() {
		return licenseTxt;
	}

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setSerialManager(SerialManager serialManager) {
		this.serialManager = serialManager;
	}

	public SerialManager getSerialManager() {
		return this.serialManager;
	}

}
