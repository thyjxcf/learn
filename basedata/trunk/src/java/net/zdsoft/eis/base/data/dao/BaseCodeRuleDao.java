package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CodeRule;

/**
 * @author yanb
 * 
 */
public interface BaseCodeRuleDao {
    /**
     * 增加
     * 
     * @param codeRule
     */
    public void insertUnitCodeRule(CodeRule codeRule);

    /**
     * 更新
     * 
     * @param codeRule
     */
    public void updateUnitCodeRule(CodeRule codeRule);
    

    /**
     * 根据主键得到学号规则信息
     * 
     * @param id 规则ID
     * @return BasicStucoderule
     */
    public CodeRule getUnitCodeRule(String id);
    /**
     * 根据规则类型得到学号规则信息
     * 
     * @param id 规则ID
     * @return BasicStucoderule
     */
    public List<CodeRule> getUnitCodeRuleList(String type);
    
    public CodeRule getInitCodeRule(String type);

}
