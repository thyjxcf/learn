package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import net.zdsoft.eis.base.common.entity.CodeRule;
import net.zdsoft.eis.base.common.entity.CodeRuleDetail;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.CodeRuleBuildService;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.data.dao.BaseCodeRuleDao;
import net.zdsoft.eis.base.data.dao.BaseCodeRuleDetailDao;
import net.zdsoft.eis.base.data.entity.CodeRuleSetAdminDto;
import net.zdsoft.eis.base.data.service.BaseCodeRuleService;
import net.zdsoft.eis.base.data.service.BaseSchoolService;
import net.zdsoft.eis.base.data.util.CodeRuleUtils;
import net.zdsoft.eis.base.observe.CodeRuleObserveParam;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.eis.base.util.HtmlUtil;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: StuCodeRuleServiceImpl.java,v 1.4 2007/01/29 02:07:37 linqz Exp $
 */
public class BaseCodeRuleServiceImpl extends Observable implements
		BaseCodeRuleService {
	private final static Logger log = LoggerFactory.getLogger(BaseCodeRuleServiceImpl.class);

	private BaseCodeRuleDao baseCodeRuleDao;
	private BaseCodeRuleDetailDao baseCodeRuleDetailDao;
	private BaseSchoolService baseSchoolService;
	private CodeRuleBuildService codeRuleBuildService;
	private McodedetailService mcodedetailService;

    public void setMcodedetailService(McodedetailService mcodedetailService) {
        this.mcodedetailService = mcodedetailService;
    }

    public void setBaseCodeRuleDao(BaseCodeRuleDao baseCodeRuleDao) {
		this.baseCodeRuleDao = baseCodeRuleDao;
	}

	public void setBaseCodeRuleDetailDao(
			BaseCodeRuleDetailDao baseCodeRuleDetailDao) {
		this.baseCodeRuleDetailDao = baseCodeRuleDetailDao;
	}

	public void setBaseSchoolService(BaseSchoolService baseSchoolService) {
        this.baseSchoolService = baseSchoolService;
    }
    
	public void setCodeRuleBuildService(
			CodeRuleBuildService codeRuleBuildService) {
		this.codeRuleBuildService = codeRuleBuildService;
		
		//添加观察者
		this.addObserver(this.codeRuleBuildService);
	}
	public PromptMessageDto createCodeRuleByType(String unitid,String codeType){
		//初始化主表
		CodeRule coderule=baseCodeRuleDao.getInitCodeRule(codeType);
		CodeRule coderuleToSave=new CodeRule();
		CodeRuleSetAdminDto.ruleEntityToEntity(coderule, coderuleToSave);
		coderuleToSave.setUnitId(unitid);
		coderuleToSave.setId(null);
		coderuleToSave.setSystemInit(false);
		baseCodeRuleDao.insertUnitCodeRule(coderuleToSave);
		//初始化详情表
		List<CodeRuleDetail> detailList=baseCodeRuleDetailDao.getDetailByRuleid(coderule.getId());
		List<CodeRuleDetail> toSaveList=new ArrayList<CodeRuleDetail>();
		for (CodeRuleDetail codeRuleDetail : detailList) {
			codeRuleDetail.setId(null);
			codeRuleDetail.setRuleId(coderuleToSave.getId());
			toSaveList.add(codeRuleDetail);
		}
		return saveRule(toSaveList);
		
	}

	/**
	 * 保存号码规则的详细内容
	 *
	 *@author "yangk"
	 * Aug 10, 2010 8:24:13 PM
	 * @param entity
	 * @param codeRuleContentList
	 * @return
	 */
	private PromptMessageDto saveRule(List<CodeRuleDetail> codeRuleContentList) {
		PromptMessageDto msgDto = new PromptMessageDto();
		// 插入学号规则主表
		baseCodeRuleDetailDao
				.batchInsertUnitCodeRuleList(codeRuleContentList);
		msgDto.setOperateSuccess(true);
		return msgDto;
	}
	 public CodeRule getUnitCodeRule(String id){
		 return baseCodeRuleDao.getUnitCodeRule(id);
	 }

	public PromptMessageDto updateAutomatism(String ruleId, String automatism) {

		PromptMessageDto msgDto = new PromptMessageDto(); 
		CodeRule entity = baseCodeRuleDao.getUnitCodeRule(ruleId);
		entity.setAutomatism(automatism);

		baseCodeRuleDao.updateUnitCodeRule(entity);
		
		saveCodeRuleEx(entity.getCodeType(), entity.getUnitId());
		msgDto.setOperateSuccess(true);
		msgDto.setPromptMessage("修改是否启用号码生成规则成功！");
		return msgDto;
	}
	public CodeRuleSetAdminDto getUnitCodeRuleList(String schoolId,
			String codeType,boolean initCodeRule,String ruleid) {
		CodeRuleSetAdminDto dto = new CodeRuleSetAdminDto();
		boolean flag=false;
		if("global".equals(CodeRuleUtils.codeTypeRangeMap.get(codeType)) || initCodeRule){
			flag=true;
		}else{
		// 先判断学校信息是否已添加
		 flag = baseSchoolService.isExistSchoolCode(schoolId);
		}
		if (flag) {
			if("global".equals(CodeRuleUtils.codeTypeRangeMap.get(codeType)) || initCodeRule){
			   dto.setExistSchool("false");
			}else{
				 dto.setExistSchool("true");
			}
			CodeRule ruleEntity=null;
			List<CodeRuleDetail> rulelistentityList=null;
			if(!initCodeRule){
			 ruleEntity = codeRuleBuildService.getUnitCodeRule(
					schoolId, Integer.parseInt(codeType));
			}else{
				ruleEntity=baseCodeRuleDao.getUnitCodeRule(ruleid);
			}
			if (ruleEntity != null ) {
				// 学号规则主表赋值
				dto.setStucoderuleid(ruleEntity.getId());
				dto.setSchid(schoolId);		
				dto.setAutomatism(ruleEntity.getAutomatism());

				// 学号规则明细赋值
				if(initCodeRule){
					rulelistentityList=baseCodeRuleDetailDao.getDetailByRuleid(ruleid);
				}else{
				    rulelistentityList = ruleEntity
						.getCodeRuleDetails();
			    }
				if (rulelistentityList != null) {
					List<CodeRuleDetail> tempList = new ArrayList<CodeRuleDetail>(
							rulelistentityList.size());

					for (Iterator<CodeRuleDetail> iter = rulelistentityList
							.iterator(); iter.hasNext();) {
						CodeRuleDetail rule = iter.next();
						// 将各值转换成页面显示格式
						String dataType = rule.getDataType();
						rule.setRuleId(ruleEntity.getId());
						rule.setDataType(CodeRuleUtils
								.getCoderuleParamName(dataType));
						rule.setLength(CodeRuleUtils
								.getCoderuleParamLength(dataType));

						tempList.add(rule);
					}
					dto.setStuCodeRuleList(tempList);
				}
			}
		} else {
			dto.setExistSchool("false");
		}

		// 是否自动生成的select html语句
		String automatismHtml = HtmlUtil.getSelectHtmlTag(dto.getAutomatism(),
				getAutomatismList(), false);
		dto.setAutomatismHtml(automatismHtml);

		return dto;
	}

    /**
     * 得到是否自动生成学号的数据值和显示值的选择List
     * 
     * @return
     */
    private final static List<String[]> getAutomatismList() {
        List<String[]> automatismList = new ArrayList<String[]>(2);       
        automatismList.add(new String[] { "0", "否" });
        automatismList.add(new String[] { "1", "是" });
        return automatismList;
    }
    
	public CodeRuleSetAdminDto getUnitCodeRuleList(String ruleListId) {
		CodeRuleDetail rulelistEntity = baseCodeRuleDetailDao
				.getUnitCodeRuleList(ruleListId);

		if (rulelistEntity != null) {
			CodeRuleSetAdminDto dto = new CodeRuleSetAdminDto();
			// 赋rulelist各项值
			dto.setRulelistid(rulelistEntity.getId());
			dto.setRuleid(rulelistEntity.getRuleId());
			dto.setDatatype(rulelistEntity.getDataType());
			dto.setRuleposition(rulelistEntity.getRulePosition());
			dto.setRulenumber(rulelistEntity.getRuleNumber());
			dto.setConstant(rulelistEntity.getConstant());
			dto.setRemark(rulelistEntity.getRemark());
			dto.setInSerialNumber(rulelistEntity.isInSerialNumber());

			// 判断当前编辑的是不是流水号
			String datatypeHtml = "";
			if ("serialno".equals(rulelistEntity.getDataType())) {
				datatypeHtml = HtmlUtil.getSelectHtmlTag("serialno",
				        CodeRuleUtils.getCoderuleParamListSerialno(), false);
			} else {
				datatypeHtml = HtmlUtil.getSelectHtmlTag(rulelistEntity
						.getDataType(), getCoderuleParamList(false), false);
			}
			// 赋数据类型的select html语句值
			dto.setDatatypeHtml(datatypeHtml);

			// 赋数据类型的最大长度map值
			dto.setDatatypeLengthMap(CodeRuleUtils
					.getCoderuleParamLengthMap());

			return dto;
		} else {
			return null;
		}

	}

	private List<String[]> getCoderuleParamList(boolean isContainSerialno){
	    List<Mcodedetail> details = mcodedetailService.getMcodeDetails("DM-HMGZSJLX");
	    List<String[]> coderuleParamsList = new ArrayList<String[]>();
	    for (Mcodedetail detail : details) {
            coderuleParamsList.add(new String[] { detail.getThisId(), detail.getContent() });
        }

	    if(isContainSerialno){
	        coderuleParamsList.addAll(CodeRuleUtils.getCoderuleParamListSerialno());
	    }
        return coderuleParamsList;
	}
	
	public CodeRuleSetAdminDto insertUnitCodeRuleList(String schoolId,
			String ruleId) {

		CodeRuleSetAdminDto dto = new CodeRuleSetAdminDto();
		// 添加规则前的处理
		// 1、得到要新增的序号，除去流水号
		int maxPos = baseCodeRuleDetailDao.getMaxRuleposition(ruleId);
		dto.setRuleposition(maxPos + 1);

		// 2、判断有没有流水号
		boolean flagSerialno = baseCodeRuleDetailDao.isExistsSerialno(ruleId);
		String datatypeHtml = "";		
		
		if (flagSerialno) {
			// 已有
			datatypeHtml = HtmlUtil.getSelectHtmlTag(getCoderuleParamList(false), false);
		} else {
			// 没有
			datatypeHtml = HtmlUtil.getSelectHtmlTag(getCoderuleParamList(true), false);
		}
		// 赋数据类型的select html语句值
		dto.setDatatypeHtml(datatypeHtml);
		dto.setSchid(schoolId);
		dto.setRuleid(ruleId);
		dto.setDatatype(CodeRuleUtils.DEFAULT_CODE_RULE_PARAM);

		// 赋数据类型的最大长度map值
		dto.setDatatypeLengthMap(CodeRuleUtils.getCoderuleParamLengthMap());

		return dto;
	}

	public PromptMessageDto saveUnitCodeRuleList(CodeRuleDetail detail,
			String codetype, String unitid) {
		PromptMessageDto msgDto = new PromptMessageDto();

		// 得到规则主表对象
		// 判断是新增还是编辑
		if (detail.getId() == null || "".equals(detail.getId())) {
			// 新增
			baseCodeRuleDetailDao.insertUnitCodeRuleList(detail);

		} else {
			// 编辑
			// unitCodeRuleListDao.saveOrUpdate(tempEntity);
			baseCodeRuleDetailDao.updateUnitCodeRuleList(detail);
		}
		saveCodeRuleEx(Integer.parseInt(codetype), unitid);
		msgDto.setPromptMessage("保存号码规则明细信息成功！");
		msgDto.setOperateSuccess(true);

		return msgDto;
	}

	public void updateUnitCodeRuleListFromMq(CodeRuleDetail detail) {
		CodeRule coderule = baseCodeRuleDao.getUnitCodeRule(detail.getRuleId());
		// 编辑
		// unitCodeRuleListDao.saveOrUpdate(tempEntity);
		baseCodeRuleDetailDao.updateUnitCodeRuleList(detail);
		saveCodeRuleEx(coderule.getCodeType(), coderule.getUnitId());
	}

	public void saveUnitCodeRuleListFromMq(CodeRuleDetail detail) {
		CodeRule coderule = baseCodeRuleDao.getUnitCodeRule(detail.getRuleId());
		// 得到规则主表对象
		// 新增
		baseCodeRuleDetailDao.insertUnitCodeRuleList(detail);

		//可能此时主表还未同步，所以为空
		if (null != coderule) {
			saveCodeRuleEx(coderule.getCodeType(), coderule.getUnitId());
		}
	}
	public void deleteUnitCodeRuleList4Mq(String ruleListId,EventSourceType e) {
		baseCodeRuleDetailDao.deleteUnitCodeRuleList(ruleListId,e);
	}
	public PromptMessageDto deleteUnitCodeRuleList(String ruleListId) {
		PromptMessageDto msgDto = new PromptMessageDto();

		// 得到删除的Entity
		CodeRuleDetail entity = baseCodeRuleDetailDao
				.getUnitCodeRuleList(ruleListId);
		int ruleposition = entity.getRulePosition();
		List<CodeRuleDetail> list =new ArrayList<CodeRuleDetail>();
		String ruleid="";
		int type=0;
		String unitId=null;
			//得到号码规则记录
			CodeRule codeRule = baseCodeRuleDao.getUnitCodeRule(entity
					.getRuleId());
			  ruleid = codeRule.getId();
			  // 删除规则明细信息
			  baseCodeRuleDetailDao.deleteUnitCodeRuleList(ruleListId,EventSourceType.LOCAL);
			  unitId=codeRule.getUnitId();
			  type=codeRule.getCodeType();

		// 重新排序号，若是最后一位或者是99（即流水号）时，不用重排
		if (ruleposition != 99) {
				list=baseCodeRuleDetailDao
						.getMoreRuleposition(ruleid, ruleposition);
			if (list != null && list.size() > 0) {
				CodeRuleDetail[] entities = new CodeRuleDetail[list.size()];
				for (int i = 0; i < list.size(); i++) {
					CodeRuleDetail tempEntity = list.get(i);
					tempEntity
							.setRulePosition(tempEntity.getRulePosition() - 1);
					entities[i] = tempEntity;
				}

				baseCodeRuleDetailDao.updateUnitCodeRuleLists(entities);
			}
		}

		saveCodeRuleEx(type, unitId);
		msgDto.setPromptMessage("删除号码规则明细成功！");
		msgDto.setOperateSuccess(true);

		log.info("删除学号规则明细信息成功！");

		return msgDto;
	}
public boolean updateRulePositionall(String srcRuleListId, String dstRuleListId){
	// 得到两个明细Entity
	CodeRuleDetail srcRulelistEntity = baseCodeRuleDetailDao
			.getUnitCodeRuleList(srcRuleListId);
	//得到号码规则记录
	CodeRule srcCodeRule = baseCodeRuleDao
			.getUnitCodeRule(srcRulelistEntity.getRuleId());
	
//	if("global".equals(BasedataConstants.codeTypeRangeMap.get(srcCodeRule.getCodeType()+""))){
//		
//		return updateGlobalRulePosition(srcRulelistEntity, srcRuleListId, dstRuleListId, srcCodeRule.getCodeType()+"", srcCodeRule.getUnitId());
//	}else{
		return updateRulePosition(srcRulelistEntity, srcCodeRule, srcRuleListId, dstRuleListId);
	//}
}

@Override
public void updateRulePositionall(String ruleId,String[] ruleDetailId) {
	// TODO Auto-generated method stub
	int i = 1;
	List<CodeRuleDetail> tempRuleList = baseCodeRuleDetailDao.getMoreRuleposition(ruleId, 0);
	if(tempRuleList.size()>0){
		CodeRule srcCodeRule = baseCodeRuleDao
				.getUnitCodeRule(tempRuleList.get(0).getRuleId());
		for (String id : ruleDetailId) {
			for (CodeRuleDetail rule : tempRuleList) {
				if(rule.getId().equals(id)){
					rule.setRulePosition(i);
				}
			}
			i++;
		}
		baseCodeRuleDetailDao.updateUnitCodeRuleLists(tempRuleList.toArray(new CodeRuleDetail[0]));
		
		String schid = srcCodeRule.getUnitId();
		saveCodeRuleEx(srcCodeRule.getCodeType(), schid);
	}
}



	public boolean updateRulePosition(CodeRuleDetail srcRulelistEntity,CodeRule srcCodeRule,String srcRuleListId, String dstRuleListId) {
		CodeRuleDetail dstRulelistEntity = baseCodeRuleDetailDao
				.getUnitCodeRuleList(dstRuleListId);
		// 交换位置号码
		int srcPos = srcRulelistEntity.getRulePosition();
		srcRulelistEntity.setRulePosition(dstRulelistEntity.getRulePosition());
		dstRulelistEntity.setRulePosition(srcPos);

		// 保存数据
		baseCodeRuleDetailDao.updateUnitCodeRuleList(srcRulelistEntity);
		baseCodeRuleDetailDao.updateUnitCodeRuleList(dstRulelistEntity);

		String schid = srcCodeRule.getUnitId();
		saveCodeRuleEx(srcCodeRule.getCodeType(), schid);
	
		return true;

	}
	public boolean updateGlobalRulePosition(CodeRuleDetail srcRuleEntity,String srcRuleListId, String dstRuleListId,String type,String unitid) {
		// 得到两个明细Entity
		CodeRuleDetail dstRuleEntity = baseCodeRuleDetailDao.getUnitCodeRuleList(dstRuleListId);
		
		// 同时交换小中高三个学段 add by jiangf 2008-6-4

		int srcPos = srcRuleEntity.getRulePosition();
		int dstPos = dstRuleEntity.getRulePosition();

		// 根据号码位置和类型找出小中高三个学段的号码规则明细，用于同时将小中高三个学段调整位置
		List<CodeRuleDetail> srcRuleEntityList = baseCodeRuleDetailDao.getDetailByTypeAndPosition(type,srcPos);

		List<CodeRuleDetail> dstRuleEntityList = baseCodeRuleDetailDao.getDetailByTypeAndPosition(type,dstPos);
		// 先判断交换与被交换的数量是否一致
		int count = srcRuleEntityList.size() <= dstRuleEntityList.size() ? dstRuleEntityList
				.size()
				: dstRuleEntityList.size();
		// 交换位置号码
		for (int i = 0; i < count; i++) {
			srcRuleEntity = srcRuleEntityList.get(i);
			dstRuleEntity = dstRuleEntityList.get(i);
			srcRuleEntity.setRulePosition(dstPos);
			dstRuleEntity.setRulePosition(srcPos);
			// 保存数据
			baseCodeRuleDetailDao.updateUnitCodeRuleList(srcRuleEntity);
			baseCodeRuleDetailDao.updateUnitCodeRuleList(dstRuleEntity);

		}


		saveCodeRuleEx(Integer.parseInt(type), unitid);
		return true;

	}
	private void saveCodeRuleEx(int type,String unitid){
		// 改变更新标志，并通知观察者(即CoderuleServiceImpl对象)已经发生变化
		this.setChanged();
		this.notifyObservers(new CodeRuleObserveParam(type, unitid));
	}
	public CodeRule getInitCodeRule(String type){
		return baseCodeRuleDao.getInitCodeRule(type);
	}
    public void saveUnitCodeRule(CodeRule codeRule){
    	baseCodeRuleDao.insertUnitCodeRule(codeRule);
    	saveCodeRuleEx(codeRule.getCodeType(), codeRule.getUnitId());
    }

    public void updateUnitCodeRule(CodeRule codeRule){
    	baseCodeRuleDao.updateUnitCodeRule(codeRule);
    	saveCodeRuleEx(codeRule.getCodeType(), codeRule.getUnitId());
    }
    
    public void batchInsertUnitCodeRuleList(
			List<CodeRuleDetail> codeRuleContentList) {
    	baseCodeRuleDetailDao.batchInsertUnitCodeRuleList(codeRuleContentList);
    }
    public void updateUnitCodeRuleList(CodeRuleDetail codeRuleDetail){
    	baseCodeRuleDetailDao.updateUnitCodeRuleList(codeRuleDetail);
    }

	
}
