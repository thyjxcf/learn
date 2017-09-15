package net.zdsoft.eis.base.data.entity;

import net.zdsoft.eis.frame.client.BaseEntity;

public class Mcodelist extends BaseEntity {
    private static final long serialVersionUID = -6101806198760608421L;

    // ************************************
    // 微代码状态定义
    // ************************************
    public static final int MCODE_ISUSING = 1;// 微代码使用中标识
    public static final int MCODE_NOTUSING = 0;// 微代码停用标识
    public static final int MCODE_NOT_MAINTAIN = 0;// 不可维护
    public static final int MCODE_MAINTAIN = 1;// 可维护

    public static final int MCODE_TYPE_STATIC_VISIABLE = 0;// 静态,可见
    public static final int MCODE_TYPE_ACTIVE = 1;// 动态,可启用停用
    public static final int MCODE_TYPE_STATIC_NOVISIABLE = 2;// 静态,不可见
    
    public static final int SUBSYSTEM_ALL = -1;// 不限子系统选择微代码
    
    
    private String mcodeId;     //类型id
    private String mcodeName;   //类型名称
    private Integer length;     //类型长度
    private Integer maintain;   //是否维护
    private String describe;    //类型描述定义
    private Integer subSystem;  //子系统
    private Integer isReport;   //是否报送
    private Integer isUsing;    //是否使用
    private Integer type; // 是否可以设置启用状态

    // ==================辅助字段==========================

   
    public String getDescribe() {
        if(describe != null) {
            return describe.trim();
        }
        return describe;
    }
   
    public void setDescribe(String describe) {
        this.describe = describe;
    }
  
    public Integer getIsReport() {
        return isReport;
    }
  
    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }
    
    public Integer getLength() {
        return length;
    }
    
    public void setLength(Integer length) {
        this.length = length;
    }
   
    public Integer getMaintain() {
        return maintain;
    }
    
    public void setMaintain(Integer maintain) {
        this.maintain = maintain;
    }
  
    public String getMcodeId() {
        if(mcodeId != null) {
            return mcodeId.trim();
        }
        return mcodeId;
    }
    
    public void setMcodeId(String mcodeId) {
        this.mcodeId = mcodeId;
    }
   
    public String getMcodeName() {
        if(mcodeName != null) {
            return mcodeName.trim();
        }
        return mcodeName;
    }
    
    public void setMcodeName(String mcodeName) {
        this.mcodeName = mcodeName;
    }
    
    public Integer getSubSystem() {
        return subSystem;
    }
    
    public void setSubSystem(Integer subSystem) {
        this.subSystem = subSystem;
    }
        
    public Integer getType() {
        return type;
    }
    
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsUsing() {
        return isUsing;
    }

    public void setIsUsing(Integer isUsing) {
        this.isUsing = isUsing;
    }
    
    
}
