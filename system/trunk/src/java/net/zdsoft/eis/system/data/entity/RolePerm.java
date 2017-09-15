package net.zdsoft.eis.system.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author luxingmu
 * @version $Revision: 1.5 $, $Date: 2007/01/09 10:04:35 $
 */
public class RolePerm extends BaseEntity {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 8290431245587003932L;

    private String roleid;

    private Long moduleid;

    private String operids;

    public Long getModuleid() {
        return moduleid;
    }

    public void setModuleid(Long moduleid) {
        this.moduleid = moduleid;
    }

    public String getOperids() {
        return operids;
    }

    public void setOperids(String operids) {
        this.operids = operids;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

}
