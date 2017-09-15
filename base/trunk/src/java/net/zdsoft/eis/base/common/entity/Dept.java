package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.client.BaseEntity;

public class Dept extends BaseEntity {

    private static final long serialVersionUID = -5723632358394425862L;

    public static final int SECTION_OFFICE_MARK = 1;// 科室标识
    public static final int STUFF_ROOM_MARK = 2;// 教研组标识
    public static final int DEPTNAME_LENGTH = 50;
    public static final int DEPTID_LENGTH = 6;
    public static final int ABOUT_LENGTH = 255;
    public static final int ORDERID_LENGTH = 4;
    public static final int DEPTTEL_LENGTH = 20;

    /**
     * 上级类型:学校
     */
    public static final int PARENT_SCHOOL = BaseConstant.PARENT_SCHOOL;

    /**
     * 上级类型:院系
     */
    public static final int PARENT_INSTITUTE = BaseConstant.PARENT_INSTITUTE;

    /**
     * 上级类型:部门
     */
    public static final int PARENT_DEPT = BaseConstant.PARENT_DEPT;

    // Fields
    private String deptCode;
    private String about;
    private Integer mark;
    private String depttel;
    private Integer jymark;
    private String principan;
    private String parentid;
    private String instituteId;// 院系id 该部门不是挂在院系下的话，设置该值为32 个0
    private Long orderid;
    private String unitId;
    private String deptname;
    private String leaderId;// 分管领导
    private boolean isDefault;// 是否管理员组
    private String deputyHeadId;//副校长
    private String deptShortName;  //部门简称
    
    // ======================辅助字段======================
    private String[] arrayIds;
    private String principanname;
    private String parentName;
    private String preParentId;// 前上级部门ID的记录
    private String leaderName;// 分管领导
    private String deputyHeadName;// 副校长
    
    private String unitName;//单位名称,通讯录使用
    
    public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/** 教学区Id by zhangkc 2013-12-23 */
	private String areaId;
	
	//辅助字段
	/** 教学区名称 */
	private String areaName;

    public static final int GROUPID_LENGTH = 6;// 部门编号最大长度

    /**
     * 虚拟部门,即无关联职工用户所在部门的UUID
     */
    public static final String VIRTURAL_GROUP_GUID = "11111111111111111111111111111111";

    /**
     * 顶级部门标识
     */
    public static final String TOP_GROUP_GUID = BaseConstant.ZERO_GUID;

    public static final String TOP_GROUP_NAME = "(单位顶级部门)";// 顶级部门显示名称

    public static final String SCH_ADMIN_GROUP_NAME = "学校管理员组";// 默认学校管理员关联职工所在部门名称

    public static final String EDU_ADMIN_GROUP_NAME = "教育局管理员组";// 默认学校管理员关联职工所在部门名称

    /**
     * 虚拟部门,即无关联职工用户所在部门名称
     */
    public static final String VIRTURAL_GROUP_NAME = "其他用户";

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getDepttel() {
        return depttel;
    }

    public void setDepttel(String depttel) {
        this.depttel = depttel;
    }

    public Integer getJymark() {
        return jymark;
    }

    public void setJymark(Integer jymark) {
        this.jymark = jymark;
    }

    public String getPrincipan() {
        return principan;
    }

    public void setPrincipan(String principan) {
        this.principan = principan;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String[] getArrayIds() {
        return arrayIds;
    }

    public void setArrayIds(String[] arrayIds) {
        this.arrayIds = arrayIds;
    }

    public String getPrincipanname() {
        return principanname;
    }

    public void setPrincipanname(String principanname) {
        this.principanname = principanname;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getPreParentId() {
        return preParentId;
    }

    public void setPreParentId(String preParentId) {
        this.preParentId = preParentId;
    }

    public String getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getDeputyHeadId() {
		return deputyHeadId;
	}

	public void setDeputyHeadId(String deputyHeadId) {
		this.deputyHeadId = deputyHeadId;
	}

	public String getDeputyHeadName() {
		return deputyHeadName;
	}

	public void setDeputyHeadName(String deputyHeadName) {
		this.deputyHeadName = deputyHeadName;
	}

	public String getDeptShortName() {
		return deptShortName;
	}

	public void setDeptShortName(String deptShortName) {
		this.deptShortName = deptShortName;
	}
	
	
}
