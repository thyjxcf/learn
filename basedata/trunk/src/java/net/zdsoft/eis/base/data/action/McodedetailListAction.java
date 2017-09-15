package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.entity.Mcodelist;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;
import net.zdsoft.eis.base.data.service.BaseMcodeListService;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.util.AssembleTool;

import com.opensymphony.xwork2.ModelDriven;

public class McodedetailListAction extends PageAction implements ModelDriven<Mcodedetail> {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 48943257878585200L;
    public static final int TYPE_ALL = -1;// 全部可见微代码
    public static final int TYPE_MAINTAIN = 0;// 可维护
    public static final int TYPE_ACTIVE = 1;// 可启停
    public static final int TYPE_ONLYVISIBLE = 2;// 仅可查看

    private static final int MCODE_ERROR = -1;// 微代码修改,异常标志
    private static final int MCODE_SUCCESS = 0;// 微代码修改成功标志
    private static final int MCODE_ERROR_THISID = 1;// 微代码修改,代码重复
    private static final int MCODE_ERROR_CONTENT = 2;// 微代码修改,代码名称重复
    private static final int MCODE_ERROR_THISID_LENGTH = 3;// 微代码修改,代码长度不符合
    private static final int MCODE_NO_ACCREDIT = 4;// 没有微代码操作权限，只有顶级教育局才能修改微代码

    private static final String modID = "SYS001";

    private List<Mcodedetail> mcodedetailList;

    private Integer type;
    private Mcodedetail mcodedetail = new Mcodedetail();
    private Mcodelist mcodelist;
    
    private List<SubSystem> subSystemList;
    private List<Mcodelist> mcodeList = new ArrayList<Mcodelist>();
    public boolean isTopUnit;
    public Integer INT_MAX = Integer.MAX_VALUE;
    private Integer avaOrderId;

    private ModuleService moduleService;
    private BaseMcodeDetailService baseMcodeDetailService;
    private BaseMcodeListService baseMcodeListService;
    private UnitService unitService;
    private String typeId;
    private String subsystemId;
    private String mcodedetailId;
    private String mcodelistId;
	private Map<Object,Object> returnJsonData = new HashMap<Object,Object>();

	
	public String execute() {
        LoginInfo loginInfo = getLoginInfo();
        //只显示当前安装和启用的子系统      
        subSystemList = moduleService.getSubSystems(loginInfo.getUnitClass(), loginInfo
                .getUnitType());
        return SUCCESS;
    }

    public String findMcodeList() throws Exception {
        Integer typeid = Integer.valueOf(typeId);
        Integer subSystemId = Integer.valueOf(subsystemId);
        if (typeid != null && subSystemId != null) {
            switch (typeid) {
            case TYPE_ALL:
                mcodeList = baseMcodeListService.getExceptNovisiableMcodeLists(subSystemId);
                break;
            case TYPE_MAINTAIN:
                mcodeList = baseMcodeListService.getMaintainMcodeLists(subSystemId);
                break;
            case TYPE_ACTIVE:
                mcodeList = baseMcodeListService.getActiveMcodeLists(subSystemId);
                break;
            case TYPE_ONLYVISIBLE:
                mcodeList = baseMcodeListService.getOnlyVisiableMcodeLists(subSystemId);
                break;
            default:
                mcodeList = baseMcodeListService.getExceptNovisiableMcodeLists(subSystemId);
            }
        }
//        return mcodeList.toArray(new Object[] {});
        returnJsonData.put("mcodeList", mcodeList);
        return SUCCESS;
    }

    @SuppressWarnings("unchecked")
    public String getList() {
        isTopUnit = isAccredit();
        mcodelist = baseMcodeListService.getMcodeList(mcodedetail.getMcodeId());
        if(mcodelist == null)
            mcodelist = new Mcodelist();
        mcodedetailList = baseMcodeDetailService.getAllMcodeDetailsByPage(mcodedetail.getMcodeId(),getPage());
        if (mcodedetailList.size() == 0 ) {
            mcodedetailList = new ArrayList();
        } else {
            avaOrderId = baseMcodeDetailService.getAvaOrderId(mcodedetail.getMcodeId());
        }
        Collections.sort(mcodedetailList, new Comparator<Mcodedetail>() {
            public int compare(Mcodedetail o1, Mcodedetail o2) {
                if (o1.getOrderId() != null && o2.getOrderId() != null) {
                    return o1.getOrderId().compareTo(o2.getOrderId());
                } else {
                    return o1.getContent().compareTo(o2.getContent());
                }
            }
        });
        return SUCCESS;
    }

    public String setMcodedetailUsing() {
        if (!isAccredit()) {
        	returnJsonData.put("result", MCODE_NO_ACCREDIT);
            return SUCCESS;
        }
        try {
            mcodedetail = baseMcodeDetailService.getMcodeDetail(mcodedetailId);

            if (baseMcodeDetailService.updateUsingStatus(mcodelistId, mcodedetailId)) {
                SystemLog.log(modID, "启停" + mcodedetail.getContent() + "微代码操作成功！");
                returnJsonData.put("result", MCODE_SUCCESS);
                return SUCCESS;
            } else {
                SystemLog.log(modID, "启停" + mcodedetail.getContent() + "微代码操作失败！");
                returnJsonData.put("result", MCODE_ERROR);
                return SUCCESS;
            }
        } catch (Exception e) {
            SystemLog.log(modID, "启停微代码操作失败！");
            returnJsonData.put("result", MCODE_ERROR);
            return SUCCESS;
        }
    }

    public String updateMcodedetail() {
		Object[] result = new Object[2];

        if (!isAccredit()) {
        	result[0]=MCODE_NO_ACCREDIT;
        	result[1]="没有微代码操作权限，只有顶级教育局才能修改微代码";
            returnJsonData.put("result", result);
            return SUCCESS;
        }

        Mcodedetail mcodedetailDtoNow = baseMcodeDetailService.getMcodeDetail(mcodedetail.getId());
        List<Mcodedetail> list = baseMcodeDetailService.getAllMcodeDetails(mcodedetailDtoNow
                .getMcodeId());
        mcodelist = baseMcodeListService.getMcodeList(mcodedetailDtoNow.getMcodeId());
        if (mcodelist.getLength() != mcodedetail.getThisId().trim().length()) {
        	result[0]=MCODE_ERROR_THISID_LENGTH;
        	result[1]="保存失败,代码长度必须是"+mcodelist.getLength()+"位";
        	returnJsonData.put("result", result);
            return SUCCESS;
        }
        Mcodedetail mcodedetailDto;
        for (int i = 0; i < list.size(); i++) {
            mcodedetailDto = (Mcodedetail) list.get(i);
            if (!mcodedetailDto.getId().equals(mcodedetail.getId())) {
                if (mcodedetailDto.getContent().trim().equals(mcodedetail.getContent().trim())) {
                	result[0]=MCODE_ERROR_CONTENT;
                	result[1]="保存失败,微代码名称已存在";
                	returnJsonData.put("result", result);
                    return SUCCESS;
                }
                if (mcodedetailDto.getThisId().trim().equals(mcodedetail.getThisId().trim())) {
                	result[0]=MCODE_ERROR_THISID;
                	result[1]="保存失败,微代码已存在";
                	returnJsonData.put("result", result);
                    return SUCCESS;
                }
            }
        }
        mcodedetailDto = new Mcodedetail();
        try {
            mcodedetailDto.setId(mcodedetail.getId());
            mcodedetailDto.setOrderId(Integer.valueOf(mcodedetail.getOrderId()));
            mcodedetailDto.setThisId(mcodedetail.getThisId());
            mcodedetailDto.setContent(mcodedetail.getContent());
            baseMcodeDetailService.updateMcodeDetail(mcodedetailDto);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            SystemLog.log(modID, "修改微代码操作失败！");
        	result[0]=MCODE_ERROR;
        	result[1]="保存失败,系统异常:"+e.getMessage();
        	returnJsonData.put("result", result);
            return SUCCESS;
        }
        SystemLog.log(modID, "修改" + mcodedetailDto.getContent() + "微代码操作成功！");
    	result[0]=MCODE_SUCCESS;
    	result[1]="微代码修改成功标志";
    	returnJsonData.put("result", result);
        return SUCCESS;
    }

    @SuppressWarnings("static-access")
    public String saveMcode() {
		Object[] result = new Object[2];
        if (!isAccredit()) {
        	result[0]=MCODE_NO_ACCREDIT;
        	result[1]="没有微代码操作权限，只有顶级教育局才能修改微代码";
        	returnJsonData.put("result", result);
            return SUCCESS;
        }
        List<Mcodedetail> list = baseMcodeDetailService.getAllMcodeDetails(mcodedetail.getMcodeId());
        mcodelist = baseMcodeListService.getMcodeList(mcodedetail.getMcodeId());

        if (mcodelist.getLength() != mcodedetail.getThisId().trim().length()) {
        	result[0]=MCODE_ERROR_THISID_LENGTH;
        	result[1]="保存失败,代码长度必须是"+mcodelist.getLength()+"位";
        	returnJsonData.put("result", result);
            return SUCCESS;
        }
        Mcodedetail mcodedetailNew;
        for (int i = 0; i < list.size(); i++) {
        	mcodedetailNew = (Mcodedetail) list.get(i);
            if (mcodedetailNew.getContent().trim().equalsIgnoreCase(mcodedetail.getContent().trim())) {
            	result[0]=MCODE_ERROR_CONTENT;
            	result[1]="保存失败,微代码名称已存在";
            	returnJsonData.put("result", result);
                return SUCCESS;
            }
            if (mcodedetailNew.getThisId().trim().equals(mcodedetail.getThisId().trim())) {
            	result[0]=MCODE_ERROR_THISID;
            	result[1]="保存失败,代码已存在";
            	returnJsonData.put("result", result);
                return SUCCESS;
            }
        }
        try {
        	mcodedetailNew = new Mcodedetail();
        	mcodedetailNew.setMcodeId(mcodedetail.getMcodeId());
        	mcodedetailNew.setThisId(mcodedetail.getThisId());
        	mcodedetailNew.setContent(mcodedetail.getContent());
        	mcodedetailNew.setOrderId(Integer.valueOf(mcodedetail.getOrderId()));

        	mcodedetailNew.setIsUsing(1);
        	mcodedetailNew.setType(Mcodedetail.MCODEDETAIL_TYPE_USERDEFINE);
            baseMcodeDetailService.insertMcodeDetail(mcodedetailNew);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            SystemLog.log(modID, "新增微代码操作失败！");
            
        	result[0]=MCODE_ERROR;
        	result[1]="保存失败,系统异常:"+e.getMessage();
        	returnJsonData.put("result", result);
            return SUCCESS;
        }
        SystemLog.log(modID, "新增" + mcodedetail.getContent() + "微代码成功！");
    	result[0]=MCODE_SUCCESS;
    	result[1]="微代码修改成功标志";
    	returnJsonData.put("result", result);
        return SUCCESS;
    }

    public String deleteMcode() {
        promptMessageDto = new PromptMessageDto();
        String[] ids = mcodedetail.getArrayIds();
        String[] contentStrArray = baseMcodeDetailService.getMcodeContentByIds(ids);
        String contentStr = AssembleTool.getAssembledString(contentStrArray, ",");
        try {
            baseMcodeDetailService.deleteMcodeDetail(ids);
            promptMessageDto.setPromptMessage("删除" + contentStr + "成功!");
            promptMessageDto.setOperateSuccess(true);
            SystemLog.log(modID, "删除" + contentStr + "微代码成功！");
        } catch (Exception e) {
            log.error(e.toString());
            promptMessageDto.setPromptMessage("删除" + contentStr + "失败!");
            promptMessageDto.setOperateSuccess(false);
            SystemLog.log(modID, "删除" + contentStr + "微代码操作失败！");
        }
        promptMessageDto.addOperation(new String[] {
                "确定",
                "platformInfoAdmin-systemMcodedetailList.action?moduleID=" + this.getModuleID() + "&&mcodeId="
                        + mcodedetail.getMcodeId() + "&&ec_p=" + this.ec_p + "&&ec_crd="
                        + this.ec_crd });
        returnJsonData.put("promptMessageDto", promptMessageDto);
        return SUCCESS;
    }

    public String getMcodedetailById() {
        returnJsonData.put("mcodeDetail", baseMcodeDetailService.getMcodeDetail(mcodedetailId));
        return SUCCESS;
    }

    /**
     * 判断是否当前部署顶级单位
     * 
     * @return
     */
    private boolean isAccredit() {
        LoginInfo loginInfo = getLoginInfo();
        Unit unitDto = unitService.getUnit(loginInfo.getUnitID());
        if (unitDto == null) {
            return false;
        }
        if (Unit.TOP_UNIT_GUID.equals(unitDto.getParentid())) {
            return true;
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public List<SubSystem> getSubSystemList() {
        return subSystemList;
    }

    public List<Mcodelist> getMcodeList() {
        return mcodeList;
    }

    public void setBaseMcodeDetailService(BaseMcodeDetailService baseMcodeDetailService) {
        this.baseMcodeDetailService = baseMcodeDetailService;
    }

    public void setBaseMcodeListService(BaseMcodeListService baseMcodeListService) {
        this.baseMcodeListService = baseMcodeListService;
    }

    public List<Mcodedetail> getMcodedetailList() {
        return mcodedetailList;
    }

    public Mcodedetail getModel() {
        return this.mcodedetail;
    }

    public static int getMCODE_ERROR() {
        return MCODE_ERROR;
    }

    public static int getMCODE_ERROR_CONTENT() {
        return MCODE_ERROR_CONTENT;
    }

    public static int getMCODE_ERROR_THISID() {
        return MCODE_ERROR_THISID;
    }

    public static int getMCODE_SUCCESS() {
        return MCODE_SUCCESS;
    }

    public static int getMCODE_ERROR_THISID_LENGTH() {
        return MCODE_ERROR_THISID_LENGTH;
    }

    public static int getMCODE_NO_ACCREDIT() {
        return MCODE_NO_ACCREDIT;
    }

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public Integer getAvaOrderId() {
        return avaOrderId;
    }

    public Mcodedetail getMcodedetail() {
        return mcodedetail;
    }

    public void setMcodedetail(Mcodedetail mcodedetail) {
        this.mcodedetail = mcodedetail;
    }

    public Mcodelist getMcodelist() {
        return mcodelist;
    }
    
    public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}
	
	public Map<Object, Object> getReturnJsonData() {
		return returnJsonData;
	}

	public void setReturnJsonData(Map<Object, Object> returnJsonData) {
		this.returnJsonData = returnJsonData;
	}

	public String getMcodedetailId() {
		return mcodedetailId;
	}

	public void setMcodedetailId(String mcodedetailId) {
		this.mcodedetailId = mcodedetailId;
	}
	
	public String getMcodelistId() {
		return mcodelistId;
	}

	public void setMcodelistId(String mcodelistId) {
		this.mcodelistId = mcodelistId;
	}
}
