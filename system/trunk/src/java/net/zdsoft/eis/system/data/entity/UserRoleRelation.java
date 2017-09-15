package net.zdsoft.eis.system.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

/**
 * @author luxingmu
 * @version $Revision: 1.3 $, $Date: 2007/01/09 10:04:35 $
 */
public class UserRoleRelation extends BaseEntity {
    private static final long serialVersionUID = -4562369048612429550L;

    private String roleid;
    private String userid;
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
