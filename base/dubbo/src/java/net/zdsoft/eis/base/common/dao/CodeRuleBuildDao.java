package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;

/**
 * @author yanb
 * 
 */
public interface CodeRuleBuildDao {
    /**
     * 取全局号码规则
     * 
     * @param section
     * @param type
     * @return
     */
    public CodeRule getCodeRule(String section, int type);

    /**
     * 取全局号码规则
     * 
     * @param section
     * @param type
     * @return
     */
    public List<CodeRule> getCodeRules(int type);

    /**
     * 根据学校id,号码类型获得号码生成规则（每种号码生成规则每个学校只有一个时）
     * 
     * @param schoolId
     * @param codeType
     * @return
     */
    public CodeRule getUnitCodeRule(String schoolId, int codeType);

    /**
     * 根据学校ID和规则ID得到规则的明细列表
     * 
     * @param ruleId 规则ID
     * 
     * @return List
     */
    public List<CodeRuleDetail> getCodeRuleDetails(String ruleId);

}
