package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Module;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.data.service.BaseModuleService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

public class ModuleAdminAction extends PageAction {

	private static final long serialVersionUID = -4860498584554282475L;
	private List<Module> moduleList = new ArrayList<Module>();
	private List<SubSystem> subSysList = new ArrayList<SubSystem>();
	private String unitClass = "";
	private String subSystemId = "";
	private String id;//模块id
	private Module module;

	private BaseModuleService baseModuleService;
	private SubSystemService subSystemService;

	@Override
	public String execute() {
		subSysList = subSystemService.getSubSystems();
		return SUCCESS;
	}

	public String moduleList() {
		moduleList = baseModuleService.findAllModules(unitClass, subSystemId,
				this.getPage());
		return SUCCESS;
	}

	public String moduleEdit() {
		module = baseModuleService.getModuleByIntId(new Long(id));
		
		moduleList = findParentModules(module.getParentid(), String.valueOf(module.getSubsystem()),
                    String.valueOf(module.getUnitclass()));
		subSysList = subSystemService.getSubSystems();
		return SUCCESS;
	}

	public List<Module> findParentModules(long parentId, String subSysId, String unitclass) {
        List<Module> moduleList = null;
        if (parentId != -1) {
            moduleList = baseModuleService.findParentModules(subSysId, unitclass);
        } else {
            moduleList = new ArrayList<Module>();
            Module m = new Module();
            m.setId(-1L);
            m.setName("无");
            moduleList.add(m);
        }

        return moduleList;
    }

	public String moduleUpdate() {
		baseModuleService.updateModule(module);

		promptMessageDto = new PromptMessageDto();
		promptMessageDto.setPromptMessage("更新模块信息成功");
		promptMessageDto.setOperateSuccess(true);
		promptMessageDto
				.addOperation(new String[] { "返回", "module-list.action" });
		promptMessageDto.addHiddenText(new String[] { "unitClass", unitClass });
		promptMessageDto.addHiddenText(new String[] { "subSystemId",
				subSystemId });
		promptMessageDto.addHiddenText(new String[] { "pageIndex",
				"" + getPageIndex() });
		return PROMPTMSG;
	}

	/*
	 * set and get
	 */

	public List<Module> getModuleList() {
		return moduleList;
	}

	public String getUnitClass() {
		return unitClass;
	}

	public void setUnitClass(String unitClass) {
		this.unitClass = unitClass;
	}

	public String getSubSystemId() {
		return subSystemId;
	}

	public void setSubSystemId(String subSystemId) {
		this.subSystemId = subSystemId;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public List<SubSystem> getSubSysList() {
		return subSysList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setBaseModuleService(BaseModuleService baseModuleService) {
		this.baseModuleService = baseModuleService;
	}

}
