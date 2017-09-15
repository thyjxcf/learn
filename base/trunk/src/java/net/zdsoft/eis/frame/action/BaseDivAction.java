/* 
 * @(#)BaseDivAction.java    Created on May 31, 2011
 * Copyright (c) 2011 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.frame.action;

/**
 * 弹出div
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: May 31, 2011 2:37:55 PM $
 */
public class BaseDivAction extends BaseAction {

    private static final long serialVersionUID = -612514552757148531L;

    private String idObjectId;// id控件的id
    private String nameObjectId;// name控件的id
    
    private String selectedValue;//选中的值

    private String url;// 内容
    private String callback;// 回调方法
    private String params;//参数

    private String useCheckbox = "true";
    private String objectIds;// 多选的对象ID
    
    private String onclick;//点击事件
    private String mode="s";//模式 单选还是多选
    private String defaultItem="Y";
    private String divName;
    private String dependson;//依赖的参数
    private String referto;//适用于的参数
    private String tipMsg;
    
    private String idObjectName; //id控件的name
    private String nameObjectName; //name控件的name
    
    public String getUseCheckbox() {
        return useCheckbox;
    }

    public void setUseCheckbox(String useCheckbox) {
        this.useCheckbox = useCheckbox;
    }
    
    public String getObjectIds() {
        return objectIds;
    }

    public void setObjectIds(String objectIds) {
        this.objectIds = objectIds;
    }
    
    public String getIdObjectId() {
        return idObjectId;
    }

    public void setIdObjectId(String idObjectId) {
        this.idObjectId = idObjectId;
    }

    public String getNameObjectId() {
        return nameObjectId;
    }

    public void setNameObjectId(String nameObjectId) {
        this.nameObjectId = nameObjectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCallback() {        
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDefaultItem() {
		return defaultItem;
	}

	public void setDefaultItem(String defaultItem) {
		this.defaultItem = defaultItem;
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	public String getDivName() {
		return divName;
	}

	public void setDivName(String divName) {
		this.divName = divName;
	}

	public String getSelectedValue() {
		return selectedValue;
	}

	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public String getDependson() {
		return dependson;
	}

	public void setDependson(String dependson) {
		this.dependson = dependson;
	}

	public String getReferto() {
		return referto;
	}

	public void setReferto(String referto) {
		this.referto = referto;
	}

	public String getTipMsg() {
		return tipMsg;
	}

	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}

	public String getIdObjectName() {
		return idObjectName;
	}

	public void setIdObjectName(String idObjectName) {
		this.idObjectName = idObjectName;
	}

	public String getNameObjectName() {
		return nameObjectName;
	}

	public void setNameObjectName(String nameObjectName) {
		this.nameObjectName = nameObjectName;
	}
}
