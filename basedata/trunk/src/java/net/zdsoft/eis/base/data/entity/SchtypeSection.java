package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * AbstractCnet2SysUser generated by MyEclipse - Hibernate Tools
 */

public class SchtypeSection extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = -4908222817567884575L;
    private String schoolType;
    private String section;

    public String getSchoolType() {
        return schoolType;
    }

    public String getSection() {
        return section;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public void setSection(String section) {
        this.section = section;
    }

}
