package net.zdsoft.eis.school.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.school.entity.BaseSchool;
import net.zdsoft.eis.school.service.BaseSchoolService;

import org.apache.commons.collections.CollectionUtils;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 学校基本信息设置
 * 
 * @author weixh
 * @since May 20, 2011
 */
@SuppressWarnings("serial")
public class BaseSchoolAdminAction extends BaseAction implements
		ModelDriven<BaseSchool> {
	private BaseSchool school = new BaseSchool();

	private BaseSchoolService eisuBaseSchoolService;
	private McodedetailService mcodedetailService;

	// 首页
	public String execute() {
		school = eisuBaseSchoolService.getBaseSchool(this.getLoginInfo()
				.getUnitID());
		return SUCCESS;
	}

	// 保存
	public String save() {
		try {
			if (!checkValues()) {
				return SUCCESS;
			}
			eisuBaseSchoolService.updateSchool(school);
			promptMessageDto.setPromptMessage("学校信息保存成功！");
			promptMessageDto.setOperateSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setErrorMessage("学校信息保存失败！");
			promptMessageDto.setOperateSuccess(false);
		}
		return SUCCESS;
	}

	/** 字段校验 */
	private boolean checkValues() {
		if (eisuBaseSchoolService.isExistSchoolCode(this.getLoginInfo()
				.getUnitID(), school.getCode())) {
			promptMessageDto.setErrorMessage("该学校代码已经存在，请重新输入！");
			return false;
		}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            
		return true;
	}

	public BaseSchool getModel() {
		return school;
	}

	public void setEisuBaseSchoolService(BaseSchoolService eisuBaseSchoolService) {
		this.eisuBaseSchoolService = eisuBaseSchoolService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	// 学校类别微代码
	public List<Mcodedetail> getSchoolTypes() {
		// 过滤掉小初高的类别微代码
		List<Mcodedetail> types = null;
		types = mcodedetailService.getMcodeDetails("DM-XXLB" );
		if(CollectionUtils.isEmpty(types)){
			types = new ArrayList<Mcodedetail>();
		}
		return types;
	}
	
	public String getTypeName(){
		return mcodedetailService.getMcodeDetail("DM-XXLB", school.getType()).getContent();
	}
}
