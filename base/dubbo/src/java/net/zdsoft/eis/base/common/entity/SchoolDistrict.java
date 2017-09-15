package net.zdsoft.eis.base.common.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * <p>
 * 项目：学籍二期(stusys)
 * 
 * 学区基本信息--数据保存类
 * 
 * @author Kobe Su,2007-5-17
 */
public class SchoolDistrict extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String code; // 学区编号
    private String name; // 学区名称
    private String eduid; // 所属教育局
    private String region; // 区域范围
    private String remark; // 备注

    // =====================辅助字段====================
    private String eduName; // 所属教育局名称
    private String[] checkid; // 选择框名

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEduid() {
        return eduid;
    }

    public void setEduid(String eduid) {
        this.eduid = eduid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEduName() {
        return eduName;
    }

    public void setEduName(String eduName) {
        this.eduName = eduName;
    }

    public String[] getCheckid() {
        return checkid;
    }

    public void setCheckid(String[] checkid) {
        this.checkid = checkid;
    }

}
