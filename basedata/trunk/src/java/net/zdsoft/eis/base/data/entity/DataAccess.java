package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class DataAccess extends BaseEntity {

    private String apCode;
    private String dataId;
    private int actionType;
    private int dataType;

    public String getApCode() {
        return apCode;
    }

    public void setApCode(String apCode) {
        this.apCode = apCode;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
