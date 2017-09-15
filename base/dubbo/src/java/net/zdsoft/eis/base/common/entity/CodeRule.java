package net.zdsoft.eis.base.common.entity;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;

import net.zdsoft.eis.base.util.SUtils;
import net.zdsoft.eis.frame.client.BaseEntity;
import net.zdsoft.keel.util.Pagination;

/**
 * 号码规则主表
 */
public class CodeRule extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    public static List<CodeRule> dt(String data) {
		List<CodeRule> ts = SUtils.dt(data, new TypeReference<List<CodeRule>>() {
		});
		if (ts == null)
			ts = new ArrayList<CodeRule>();
		return ts;

	}

	/*public static List<CodeRule> dt(String data, Pagination page) {
		JSONObject json = JSONObject.parseObject(data);
		List<CodeRule> ts = SUtils.dt(json.getString("data"), new TypeReference<List<CodeRule>>() {
		});
		if (ts == null)
			ts = new ArrayList<CodeRule>();
		if (json.containsKey("count"))
			page.setMaxRowCount(json.getInteger("count"));
		return ts;

	}*/

	public static CodeRule dc(String data) {
		return SUtils.dc(data, CodeRule.class);
	}

    
    
    

    public CodeRule() {
    }

    private String unitId; // 学校id
    private String automatism; // 是否自动生成
    private int codeType; // 号码规则类型
    private int section;// 学段
    @JSONField(name="isSystemInit")
    private boolean systemInit;// 是否系统初始化

    // ======================辅助字段========================
    private List<CodeRuleDetail> codeRuleDetails = new ArrayList<CodeRuleDetail>();// 规则明细

    public void addCodeRuleDetail(CodeRuleDetail codeRuleDetail) {
        this.codeRuleDetails.add(codeRuleDetail);
    }

    /**
     * 获得学号规则明细
     * 
     * @return
     */
    public List<CodeRuleDetail> getCodeRuleDetails() {
        return codeRuleDetails;
    }

    public void setCodeRuleDetails(List<CodeRuleDetail> codeRuleDetails) {
        this.codeRuleDetails = codeRuleDetails;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAutomatism() {
        return automatism;
    }

    public void setAutomatism(String automatism) {
        this.automatism = automatism;
    }

    public int getCodeType() {
        return codeType;
    }

    public void setCodeType(int codeType) {
        this.codeType = codeType;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public boolean isSystemInit() {
        return systemInit;
    }

    public void setSystemInit(boolean systemInit) {
        this.systemInit = systemInit;
    }

}
