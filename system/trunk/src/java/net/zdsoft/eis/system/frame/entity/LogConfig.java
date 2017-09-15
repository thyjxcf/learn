package net.zdsoft.eis.system.frame.entity;

import net.zdsoft.keelcnet.entity.EntityObject;

public class LogConfig extends EntityObject {
    private static final long serialVersionUID = -2745574166702422431L;
    
    private Integer subSystem;
    private Integer flag;
    private Integer days;

    // ==============辅助字段========
    private String subSystemName;

    public LogConfig() {

    }

    public Integer getDays() {
        return days;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }

    public String getSubSystemName() {
        return subSystemName;
    }

    public void setSubSystemName(String subSystemName) {
        this.subSystemName = subSystemName;
    }

}
