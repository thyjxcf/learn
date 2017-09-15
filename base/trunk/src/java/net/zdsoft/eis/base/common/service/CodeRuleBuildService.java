package net.zdsoft.eis.base.common.service;

import java.util.List;
import java.util.Observer;

import net.zdsoft.eis.base.common.entity.CodeParam;
import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.leadin.common.entity.MaxCodeMetadata;

/**
 * 号码生成规则的service
 * 
 * @author zhongh
 * @since 1.0
 * @version $Id: CoderuleService.java,v 1.22 2006/12/02 08:41:27 linqz Exp $
 */
public interface CodeRuleBuildService extends Observer {
    /**
     * 是否自动生成代码，取全局号码规则（如果有多条，则取第一条）
     * 
     * @param codeType
     * @return
     */
    public boolean isAutoBuildCode(int codeType);

    /**
     * 取全局号码规则
     * 
     * @param section
     * @param codeType
     * @return
     */
    public CodeRule getCodeRule(String section, int codeType);

    /**
     * 根据学校id,号码类型获得号码生成规则（每种号码生成规则每个学校只有一个时）
     * 
     * @param schoolId
     * @param codeType
     * @return
     */
    public CodeRule getUnitCodeRule(String schoolId, int codeType);

    /**
     * 
     * 取得全局号码的生成规则
     * 
     * @param type
     * @return 没有值返回null
     */
    public List<CodeRuleDetail> getCodeRuleDetails(String section, int type);

    /**
     * 根据学校ID、规则ID得到学号生成规则明细列表
     * 
     * @param ruleId 规则ID
     * @return List
     */
    public List<CodeRuleDetail> getCodeRuleDetails(String ruleId);

    /**
     * 自动生成号码（学号，毕业证号）
     * 
     * @param codeparam
     * @param num
     * @param codeType
     * @param metadata
     * @return
     */
    public String autoCreateUnitcode(CodeParam codeparam, int codeType, MaxCodeMetadata metadata);

    /**
     * 自动生成全局号码（学籍号、会考证号）
     * 
     * @param codeparam 生成学籍号所需的参数
     * @param codeType
     * @param metadata
     * @return String
     */
    public String autoCreateCode(CodeParam codeparam, int codeType, MaxCodeMetadata metadata);

    /**
     * 取出规则前缀，返回真实的前缀
     * 
     * @param codeparam
     * @param codeType
     * @return
     */
    public String getPrefix(CodeParam codeparam, int codeType);

    /**
     * 去掉学校号码规则，重新加载，用于出现异常等情况下保持号码连续
     * 
     * @param unitId
     * @param codeType
     */
    public void removeCodeMap(String unitId, int codeType);

}
