package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.TypeReference;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: UnitiveCodeRuleDto.java,v 1.3 2007/01/12 03:08:46 linqz Exp $
 */
public class CodeRuleDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String ruleId;// 规则主表id
    private String dataType; // 数据名称
    private int rulePosition; // 序列位置
    private int ruleNumber; // 位数
    private String constant; // 默认值
    private String remark; // 备注
    private boolean inSerialNumber;// 是否计入流水号

    // ===================辅助字段=====================
    private String dataTypeDisplay; // 数据类型名称显示值
    private String length; // 类型长度
    
    public static List<CodeRuleDetail> dt(String data) {
		List<CodeRuleDetail> ts = SUtils.dt(data, new TypeReference<List<CodeRuleDetail>>() {
		});
		if (ts == null)
			ts = new ArrayList<CodeRuleDetail>();
		return ts;

	}
    
    public String getDataTypeDisplay() {
        return dataTypeDisplay;
    }

    public void setDataTypeDisplay(String dataTypeDisplay) {
        this.dataTypeDisplay = dataTypeDisplay;
    }

    public CodeRuleDetail() {
    }

    public String getConstant() {
        return constant;
    }

    public void setConstant(String constant) {
        this.constant = constant;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getRulePosition() {
        return rulePosition;
    }

    public void setRulePosition(int rulePosition) {
        this.rulePosition = rulePosition;
    }

    public int getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(int ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isInSerialNumber() {
        return inSerialNumber;
    }

    public void setInSerialNumber(boolean inSerialNumber) {
        this.inSerialNumber = inSerialNumber;
    }

}
