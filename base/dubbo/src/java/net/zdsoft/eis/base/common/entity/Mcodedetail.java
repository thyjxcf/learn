package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.util.Pagination;

public class Mcodedetail extends BaseEntity {
    private static final long serialVersionUID = 4814663081429400649L;
    
    public static final int MCODEDETAIL_TYPE_SYSTEMDEFINE = 0;// 系统定义代码
    public static final int MCODEDETAIL_TYPE_USERDEFINE = 1;// 用户定义代码
    public static final int MCODEDETAIL_TYPE_SYSTEMDEFINE_NONACTIVE = 2;// 系统定义代码,并且不允许停用    
    
    private String mcodeId; // 父级代码id
    private String thisId; // 这个微代码项的id
    @JSONField(name="mcodeContent")
    private String content; // 名称
    private Integer isUsing; // 是否在用（0为否，1为是）
    @JSONField(name="mcodeType")
    private Integer type; // 类型（0系统定义或1自定义,默认为系统定义）
    @JSONField(name="displayOrder")
    private Integer orderId; // 排序字段
    private String parentThisId; //父结点值
    
    //新加字段 by zhangkc 20150401
    //相同mcodeId可能有多组不同类型的微代码组合，根据fieldType+fieldValue区分
    private McodeFieldType fieldType;//微代码区域类型
    private String fieldValue;//微代码区域值
    
    //业务用，实际上数据库没有此字段
    private boolean isFolder;
    private int level;
    
    // ====================辅助字段=================
    private String[] arrayIds;

    /**
     * 默认构造方法
     */
    public Mcodedetail() {
    }
    
    public static List<Mcodedetail> dt(String data) {
		List<Mcodedetail> ts = SUtils.dt(data, new TypeReference<List<Mcodedetail>>() {
		});
		if (ts == null)
			ts = new ArrayList<Mcodedetail>();
		return ts;
	}

	public static List<Mcodedetail> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<Mcodedetail> ts = SUtils.dt(json.getString("data"), new TypeReference<List<Mcodedetail>>() {
		});
		if (ts == null)
			ts = new ArrayList<Mcodedetail>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		return ts;

	}

	public static Mcodedetail dc(String data) {
		return SUtils.dc(data, Mcodedetail.class);
	}

    public String getContent() {
        if (content != null) {
            return content.trim();
        }
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

    public String getMcodeId() {
        if (mcodeId != null) {
            return mcodeId.trim();
        }
        return mcodeId;
    }

    public void setMcodeId(String mcodeId) {
        this.mcodeId = mcodeId;
    }

    public String getThisId() {
        if (thisId != null) {
            return thisId.trim();
        }
        return thisId;
    }

    public void setThisId(String thisId) {
        this.thisId = thisId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String[] getArrayIds() {
        return arrayIds;
    }

    public void setArrayIds(String[] arrayIds) {
        this.arrayIds = arrayIds;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

	public McodeFieldType getFieldType() {
		return fieldType;
	}

	public void setFieldType(McodeFieldType fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

    public String getParentThisId() {
        return parentThisId;
    }

    public void setParentThisId(String parentThisId) {
        this.parentThisId = parentThisId;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void setFolder(boolean isFolder) {
        this.isFolder = isFolder;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
