package net.zdsoft.eis.base.common.entity;

import java.util.Date;

import net.zdsoft.keelcnet.entity.EntityObject;

public class SystemPatch extends EntityObject {

    private static final long serialVersionUID = 1L;

    // Fields
    private String patchname;
    private String patchversion;
    private String description;
    private String compatible;
    private Date createtime;
    private String proversion;
    private Integer mainversion;
    private Integer type;
    private String project;
    private Integer subsystem;

    // Constructors

    /** default constructor */
    public SystemPatch() {
    }

    // Property accessors

    public String getPatchname() {
        return this.patchname;
    }

    public void setPatchname(String patchname) {
        this.patchname = patchname;
    }

    public String getPatchversion() {
        return this.patchversion;
    }

    public void setPatchversion(String patchversion) {
        this.patchversion = patchversion;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompatible() {
        return this.compatible;
    }

    public void setCompatible(String compatible) {
        this.compatible = compatible;
    }

    public Date getCreatetime() {
        return this.createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getProversion() {
        return this.proversion;
    }

    public void setProversion(String proversion) {
        this.proversion = proversion;
    }

    public Integer getMainversion() {
        return this.mainversion;
    }

    public void setMainversion(Integer mainversion) {
        this.mainversion = mainversion;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public Integer getSubsystem() {
        return this.subsystem;
    }

    public void setSubsystem(Integer subsystem) {
        this.subsystem = subsystem;
    }

}
