package net.zdsoft.eis.base.data.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.sync.EventSourceType;

/**
 * @author yanb
 * 
 */
public interface BaseCodeRuleDetailDao {

    /**
     * 
     * @param codeRuleDetail
     */
    public void insertUnitCodeRuleList(CodeRuleDetail codeRuleDetail);

    /**
     * 批量新增
     *
     *@author "yangk"
     * Aug 10, 2010 8:19:12 PM
     * @param codeRuleContentList
     */
    public void batchInsertUnitCodeRuleList(
			List<CodeRuleDetail> codeRuleContentList) ;
    /**
     * 更新
     * 
     * @param codeRuleDetail
     */
    public void updateUnitCodeRuleList(CodeRuleDetail codeRuleDetail);

    /**
     * 批量更新
     * 
     * @param unitCodeRuleLists
     */
    public void updateUnitCodeRuleLists(CodeRuleDetail[] unitCodeRuleLists);
    
    public void deleteUnitCodeRuleList(String id,EventSourceType e);

    /**
     * 根据规则明细ID得规则明细的Entity
     * 
     * @param id 规则明细ID
     * @return BasicStucoderulelist
     */
    public CodeRuleDetail getUnitCodeRuleList(String id);

    /**
     * 得到大于指定位置序号的规则明细列表，但要除去流水号（即ruleposition=99的行）]
     * 
     * @param ruleid 学号规则ID
     * @param ruleposition 指定序号
     * @return List
     */
    public List<CodeRuleDetail> getMoreRuleposition(String ruleid, int ruleposition);
    /**
     * 根据规则类型 位置序号得到规则详情
     * @param type
     * @param ruleposition
     * @return
     */
    public List<CodeRuleDetail> getDetailByTypeAndPosition(String type,int ruleposition);
    /**
     * 根据RULEID得到规则详情
     * @param ruleid
     * @return
     */
    public List<CodeRuleDetail> getDetailByRuleid(String ruleid);

    /**
     * 得到学号规则中最大的号码位置
     * 
     * @param ruleId 学号规则ID
     * @return int
     */
    public int getMaxRuleposition(String ruleId);

    /**
     * 根据ruleid得判断是否在该学号规则中已经存在了流水号
     * 
     * @param ruleId 规则ID
     * @return boolean (true：存在，false：不存在)
     */
    public boolean isExistsSerialno(String ruleId);
    

}
