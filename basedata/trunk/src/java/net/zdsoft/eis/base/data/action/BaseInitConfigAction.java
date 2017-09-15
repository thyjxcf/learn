package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.data.service.BaseSysOptionService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.exception.FormatException;

public class BaseInitConfigAction extends BaseAction {

	
	private static final long serialVersionUID = 5502200899511834081L;

	private SysOption[] baseOptionArray;
	private String[] optionCode;
	private String[] name;
	private String[] nowValue;
	private String[] description;
	private String[] defaultValue;

	private BaseSysOptionService baseSysOptionService;

	public String takeInitConfig() {
		List<SysOption> sysOptionList = baseSysOptionService
				.getSysOptions();
		baseOptionArray = sysOptionList.toArray(new SysOption[0]);
		return SUCCESS;

	}

	public String saveBaseInitConfig() {
		//依赖于界面的传值顺序
		baseOptionArray = buildBaseOptionArray();
		promptMessageDto = new PromptMessageDto();
		try {
			baseSysOptionService.saveSysOptions(baseOptionArray);
		} catch (FormatException e) {
			addFieldError(e.getField(), e.getMessage());
			return INPUT;
		}
		promptMessageDto.setPromptMessage("修改成功！");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto.addOperation(new String[] { "确定",
				"baseInitConfig.action" });
		return PROMPTMSG;
	}

	private SysOption[] buildBaseOptionArray() {
		//依赖于界面的传值顺序
		SysOption[] optionArray = new SysOption[optionCode.length];
		for (int i = 0; i < optionArray.length; i++) {
			optionArray[i] = new SysOption();
			optionArray[i].setOptionCode(optionCode[i].trim());
			optionArray[i].setName(name[i].trim());
			optionArray[i].setNowValue(nowValue[i].trim());
			optionArray[i].setDescription(description[i].trim());
			optionArray[i].setDefaultValue(defaultValue[i].trim());
		}
		return optionArray;
	}

	/*
	 * set and get
	 */
	public SysOption[] getBaseOptionArray() {
		return baseOptionArray;
	}

	public void setBaseOptionArray(SysOption[] baseOptionArray) {
		this.baseOptionArray = baseOptionArray;
	}
	
	public String[] getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String[] optionCode) {
		this.optionCode = optionCode;
	}

	public String[] getName() {
		return name;
	}

	public void setName(String[] name) {
		this.name = name;
	}

	public String[] getNowValue() {
		return nowValue;
	}

	public void setNowValue(String[] nowValue) {
		this.nowValue = nowValue;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String[] getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String[] defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setBaseSysOptionService(
			BaseSysOptionService baseSysOptionService) {
		this.baseSysOptionService = baseSysOptionService;
	}

}
