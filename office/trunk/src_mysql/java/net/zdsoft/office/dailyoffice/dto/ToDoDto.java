package net.zdsoft.office.dailyoffice.dto;

import net.zdsoft.eis.base.common.entity.Module;

/**
 * @author chens
 * @version 创建时间：2015-9-14 下午3:00:32
 * 具体信息展示：【会议】您有1个会议取消 	查看明细
 */
public class ToDoDto {

	private Integer number;//条数，可以包含在模块提示信息里面
	private String moduleSimpleName;//中括号显示名称
	private String moduleContent;//模块提示信息
	private String openUrl;//window.open的地址
	private Module module;//模块，用于跳转
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getModuleSimpleName() {
		return moduleSimpleName;
	}
	public void setModuleSimpleName(String moduleSimpleName) {
		this.moduleSimpleName = moduleSimpleName;
	}
	public String getModuleContent() {
		return moduleContent;
	}
	public void setModuleContent(String moduleContent) {
		this.moduleContent = moduleContent;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public String getOpenUrl() {
		return openUrl;
	}
	public void setOpenUrl(String openUrl) {
		this.openUrl = openUrl;
	}
	
}
