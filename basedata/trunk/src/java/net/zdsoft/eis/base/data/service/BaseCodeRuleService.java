package net.zdsoft.eis.base.data.service;

import java.util.List;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.data.entity.CodeRuleSetAdminDto;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

/* 
 * @author Dongzk
 * @since 1.0
 * @version $Id: StuCodeRuleService.java,v 1.5 2006/11/20 07:16:17 dongzk Exp $
 */
public interface BaseCodeRuleService {
	public CodeRule getInitCodeRule(String type);
    /**
     * 插入学号规则表中一行数据，并同时在其明细规则中插入流水号行
     * 
     * @param schoolId 学校ID
     * @return PromptMessageDto
     */
	public PromptMessageDto createCodeRuleByType(String unitid,String codeType);
    /**
     * 根据主键得到学号规则信息
     * 
     * @param id 规则ID
     * @return BasicStucoderule
     */
    public CodeRule getUnitCodeRule(String id);

    /**
     * 根据学校ID得到学号生成规则明细，反应到页面上（用于每个学校只有一个学号生成规则的情况）
     * 
     * @param schoolId 学校ID
     * @return CodeRuleSetAdminDto
     */
    public CodeRuleSetAdminDto getUnitCodeRuleList(String schoolId, String codeType,boolean initCodeRule,String ruleid);
    /**
     * 修改是否自动更新字段
     * 
     * @param ruleId 学号规则ID
     * @param automatism 是否启动自动更新（1启用，0不启动）
     * @return
     */
    public PromptMessageDto updateAutomatism(String ruleId, String automatism);

    /**
     * 添加学号规则明细
     * 
     * @param schoolId 学校ID
     * @param ruleId 学号规则ID
     * @return CodeRuleSetAdminDto
     */
    public CodeRuleSetAdminDto insertUnitCodeRuleList(String schoolId, String ruleId);

    /**
     * 根据规则明细ID得到BasicStucoderulelist信息
     * 
     * @param ruleListId 规则明细ID
     * @return CodeRuleSetAdminDto
     */
    public CodeRuleSetAdminDto getUnitCodeRuleList(String ruleListId);

    /**
     * 保存学号规则明细信息
     * 
     * @param dto CodeRuleSetAdminDto
     * @return PromptMessageDto
     */
    public PromptMessageDto saveUnitCodeRuleList(CodeRuleDetail codeDetail,String codetype,String unitid);
    
    public void deleteUnitCodeRuleList4Mq(String ruleListId,EventSourceType e); 
    
    public void saveUnitCodeRuleListFromMq(CodeRuleDetail codeDetail);
    
    public void updateUnitCodeRuleListFromMq(CodeRuleDetail codeDetail);

    /**
     * 删除指定的规则明细
     * 
     * @param ruleListId 规则明细ID
     * @return PormptMessageDto
     */
    public PromptMessageDto deleteUnitCodeRuleList(String ruleListId);
    

    /**
     * 交换学号规则明细中的两个号码位置
     * 
     * @param srcRuleListId
     * @param dstRuleListId
     * @return boolean true:交换成功；false:交换失败
     */
    public boolean updateRulePositionall(String srcRuleListId, String dstRuleListId);
    
    /**
     * 增加
     * 
     * @param codeRule
     */
    public void saveUnitCodeRule(CodeRule codeRule);

    /**
     * 更新
     * 
     * @param codeRule
     */
    public void updateUnitCodeRule(CodeRule codeRule);

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
     * 更新学生号规则排序
     * @param rulePosition
     */
	public void updateRulePositionall(String ruleId,String[] ruleDetailId);
    


}
