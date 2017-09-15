package net.zdsoft.eis.system.frame.action;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.SystemPatch;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.service.ProductParamService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemVersionService;
import net.zdsoft.eis.frame.action.BaseAction;

public class SystemInforAction extends BaseAction {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    public SubSystem subSystemDto;
    public SystemVersion systemVersionDto;
    public List<SystemPatch> systemPatchList;
    public Map<String, String> productParamMap;

    private SubSystemService subSystemService;
    private SystemVersionService systemVersionService;
    private ProductParamService productParamService;

    private Integer subSystem;
    private SystemPatch systemPatchDto;

    public String execute() throws Exception {
        List<SubSystem> list = subSystemService.getSubSystems(new Integer[] { subSystem });
        if (!CollectionUtils.isEmpty(list)) {
            subSystemDto = list.get(0);
        }
        systemVersionDto = systemVersionService.getSystemVersion();
        systemPatchDto = systemVersionService.getSubSystemPatch(subSystem);

        productParamMap = productParamService.getProductParamCodeValueMap();

        return SUCCESS;
    }

    public Object getModel() {
        return subSystemDto;
    }

    public Integer getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public SystemPatch getSystemPatchDto() {
        return systemPatchDto;
    }

    public void setSystemPatchDto(SystemPatch systemPatchDto) {
        this.systemPatchDto = systemPatchDto;
    }

    public Map<String, String> getProductParamMap() {
        return productParamMap;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setSystemVersionService(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }

    public void setProductParamService(ProductParamService productParamService) {
        this.productParamService = productParamService;
    }

}
