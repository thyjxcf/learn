package net.zdsoft.eis.system.frame.entity;

import net.zdsoft.keelcnet.entity.EntityObject;

public class ModelOperator extends EntityObject {

    // Fields
    private static final long serialVersionUID = 848327665178058216L;

    private Long moduleid;

    private String operatorname;

    private String operheading;

    private String description;

    private Integer operatortype;

    private boolean isactive;

    private Integer orderid;

    private Integer opergroup;

    private Integer operweight;

    public Long getModuleid() {
        return this.moduleid;
    }

    public void setModuleid(Long moduleid) {
        this.moduleid = moduleid;
    }

    public String getOperatorname() {
        return this.operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    public String getOperheading() {
        return this.operheading;
    }

    public void setOperheading(String operheading) {
        this.operheading = operheading;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOperatortype() {
        return this.operatortype;
    }

    public void setOperatortype(Integer operatortype) {
        this.operatortype = operatortype;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public Integer getOrderid() {
        return this.orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getOpergroup() {
        return this.opergroup;
    }

    public void setOpergroup(Integer opergroup) {
        this.opergroup = opergroup;
    }

    public Integer getOperweight() {
        return this.operweight;
    }

    public void setOperweight(Integer operweight) {
        this.operweight = operweight;
    }
}
