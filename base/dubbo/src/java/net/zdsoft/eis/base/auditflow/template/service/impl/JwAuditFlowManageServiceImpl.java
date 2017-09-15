package net.zdsoft.eis.base.auditflow.template.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.template.dao.JwAuditFlowManageDao;
import net.zdsoft.eis.base.auditflow.template.dto.Business;
import net.zdsoft.eis.base.auditflow.template.entity.FlowStep;
import net.zdsoft.eis.base.auditflow.template.service.JwAuditFlowManageService;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;

import org.apache.commons.lang.ArrayUtils;


public class JwAuditFlowManageServiceImpl implements JwAuditFlowManageService {

	private JwAuditFlowManageDao jwAuditFlowManageDao;
	private UnitService unitService;

	/**
	 * 获得系统教育局中的最高级别
	 * 
	 * @return 最高级别号 (2省教育厅,3市教育局,4区县教育局)
	 */
	public int getRegionLevel(){
	    Unit unit = unitService.getTopEdu();
	    if(null == unit){
	        return 0; 
	    }else{
	        return unit.getRegionlevel();
	    }
	}
	
	/**
	 * 获得异动所有可能出现的情况
	 * 
	 * @return 情况列表
	 */
	public List getAuditTypeList(Integer nowRegionLevel){
		int startRegionLevel = 0 ;
		if(nowRegionLevel == null){
			startRegionLevel = getRegionLevel();
		}else{
			startRegionLevel = nowRegionLevel;
		}
		//过滤掉直属情况
		if (startRegionLevel == 1) {
			startRegionLevel += 1;
		}
		int regionLevel = startRegionLevel;
		int endRegionLevel = 5;
		List auditTypeList = new ArrayList();
		String[] unitName = {"","","省","市","区县","乡镇"};
		startRegionLevel--;
		for(int i=startRegionLevel;i<=endRegionLevel;i++){
			String[] str = new String[(endRegionLevel-startRegionLevel+1)*2];
			int num = 0;
			if(i==startRegionLevel){
				for(int j=startRegionLevel;j<=endRegionLevel;j++){
					if(j==startRegionLevel){
						str[num] = "";
						str[num+1] = "0";
					}else{
						str[num] = unitName[j]+"直属学校学生申请流程";
						str[num+1] = j+"-0";
					}
					num = num + 2;
				}
			}else{
				for(int j=startRegionLevel;j<=endRegionLevel;j++){
					if(j==startRegionLevel){
						str[num] = unitName[i]+"直属学校学生申请流程";
						str[num+1] = "-1";
						num = num + 2;
					}else{
						if(i==startRegionLevel+1 && j==startRegionLevel+1 && regionLevel==2){
							str[num] = "省-省";
							str[num+1] = "2-2";
						}else{
							str[num] = unitName[i]+"--"+unitName[j];
							str[num+1] = i+"-"+j;
						}
						num = num + 2;
					}
				}
			}
			auditTypeList.add(str);
		}
		return auditTypeList;
	}
	
	/**
	 * 获得默认审核列表
	 * 
	 * @param regionLevel 系统最高单位级别
	 * @param businessType 异动类别，-255
	 * @param auditType 异动方式
	 * @param section 学段
	 * @return 审核列表
	 */
	@SuppressWarnings("unchecked")
	public List getAuditFlowList(String regionLevel,Integer nowRegionLevel,String businessType,String auditType, String section, boolean schConfirm){
		List list = jwAuditFlowManageDao.getFlowTypeNoteData(businessType,auditType,section);
		if(list != null && list.size()>0){
			//数据库已存在此种情况
			return getInitializtionAuditFlowList(list, regionLevel, auditType);
		}else{
			//数据库无此种情况记录
			return getNonentityNoteData(regionLevel,nowRegionLevel, auditType, schConfirm);
		}
	}
	
	public boolean getAuditFlowList(String businessType,String auditType,String section){
		List list = jwAuditFlowManageDao.getFlowTypeNoteData(businessType,auditType,section);
		if(list != null && list.size()>0){
			//数据库已存在此种情况
			return true;
		}else{
			//数据库无此种情况记录
			return false;
		}
	}
	
	/**
	 * 根据数据库已存在的记录生成审核列表
	 * 
	 * @param list 数据库中此种方式的数据
	 * @param regionLevel 系统最高单位级别
	 * @param auditType 异动方式
	 * 
	 * @return 审核列表
	 */
	public List getInitializtionAuditFlowList(List<FlowStep> list, String regionLevel,String auditType){
		List auditFlowList = new ArrayList();
		String[] auditTypes = auditType.split("-");//切割转出-转入单位的级别。
		/*保存审核每个层次的信息，
		 * 0-序号
		 * 1-级别
		 * 2-起止点标示（0起点，-1止点，1为中间单位）
		 * 3-转出、转入（0转出，1转入,-1最高级别）
		 * 4-审核单位名称
		 * 5-单位类型 （1教育局，2学校）*/
		String[] str = null;
		String[] unitName = {"","","省教育厅","市教育局","区县教育局","乡镇教育局"};
		
		//保存转出学校
		str = new String[6];
		str[0] = "1";
		str[1] = (Integer.parseInt(auditTypes[0])+1)+"";
		str[2] = "0";
		str[3] = "0";
		if(auditTypes[1].equals("0"))
			str[4] = "申请学校";
		else
			str[4] = "转出学校";
		str[5] = "2";//单位类型 （2学校）
		auditFlowList.add(str);
//		flow_id，流程模板id；
//		audit_unit_type，审核单位类型：1教育局，2学校；
//		region_level，审核单位级别；
//		step_value:步骤，-1表示最后一步，其他的从0开始；
//		io_type，单位来源类型，如果是0表示转出方，1表示转入方
		Collections.sort(list,
				Business.BUSINESS_TEMPLATE_STEP_ORDER);
		
		FlowStep step = list.get(0);
		if (step.getStepValue() == -1){
			list.add(step);
			list.remove(0);
		}
		
		for (int i = 0; i < list.size(); i++) {
			FlowStep flowStep = (FlowStep) list
					.get(i);
			
			str = new String[6];
			str[0] = (auditFlowList.size()+1)+"";
			str[1] = flowStep.getRegionLevel()+"";
			if(flowStep.getStepValue()==-1 && !auditTypes[1].equals("0"))
				str[2] = "-1";//最后一步
			else
				str[2] = "1";
			str[3] = flowStep.getIoType()+"";
			if(auditTypes[1].equals("0")){//其他异动情况
				if(flowStep.getAuditUnitType()==1){//单位性质为教育局
					str[4] = unitName[flowStep.getRegionLevel()];
					str[5] = String.valueOf(flowStep.getAuditUnitType());
				}else{
					str[4] = "学校确认";
					str[5] = "2";//单位类型 （2学校）
				}
			}
			else{//转学异动情况
				if(flowStep.getAuditUnitType()==1){//单位性质为教育局
					//2代表省顶级教育局
					if (flowStep.getRegionLevel() == 2) {
						str[4] = unitName[flowStep.getRegionLevel()];//顶级教育局
					}else{
						if(flowStep.getIoType() == 0 )
							str[4] = "转出"+unitName[flowStep.getRegionLevel()];	//转出单位
						else if(flowStep.getIoType() == 1)
							str[4] = "转入"+unitName[flowStep.getRegionLevel()];	//转入单位
					}
					str[5] = "1";//单位类型 （1教育局）
				}else{//单位性质为学校
					str[4] = "转入学校";	
					str[5] = "2";//单位类型 （2学校）
				}
					
			}
			auditFlowList.add(str);
		}
		
		return auditFlowList;
	}
	
	/**
	 * 根据实际异动情况生成审核列表
	 * 
	 * @param regionLevel 系统最高单位级别
	 * @param nowRegionLevel 当前单位级别
	 * @param auditType 异动方式
	 * @return 审核列表
	 */
	public List getNonentityNoteData(String regionLevel,Integer nowRegionLevel, String auditType, boolean schConfirm){
		List<String[]> auditFlowList = new ArrayList<String[]>();
		try {
			String[] auditTypes = auditType.split("-");//切割转出-转入单位的级别。
			int out = Integer.parseInt(auditTypes[0]);
			int in = Integer.parseInt(auditTypes[1]);
			int regionLevelInt = Integer.parseInt(regionLevel);
			Map<String, String[]> map = new HashMap<String, String[]>();
			/*保存审核每个层次的信息，
			 * 0-序号
			 * 1-级别
			 * 2-起止点标示（0起点，-1止点，1为中间单位）
			 * 3-转出、转入（0转出，1转入,-1最高级别）
			 * 4-审核单位名称
			 * 5-单位类型 （1教育局，2学校）*/
			String[] str = null;
			String[] unitName = {"","","省教育厅","市教育局","区县教育局","乡镇教育局"};
			//默认：转出学校-转入学校-转出教育局-转入教育局
			//保存转出学校
			int count = 1; 
			str = new String[6];
			str[0] = String.valueOf(count);
			str[1] = String.valueOf(out+1);
			str[2] = "0";
			str[3] = "0";
			if(auditTypes[1].equals("0"))
				str[4] = "申请学校";
			else
				str[4] = "转出学校";
			str[5] = "2";//单位类型 （2学校）
			auditFlowList.add(str);
			
			//需要学校确认
			if(schConfirm){
				count ++;
				//保存学校确认
				str = new String[6];
				str[0] = String.valueOf(count);
				str[1] = String.valueOf(0);
				str[2] = "1";
				str[3] = "0";
				str[4] = "学校确认";
				str[5] = "2";//单位类型 （2学校）
				auditFlowList.add(str);
			}
			
			if(!auditTypes[1].equals("0")){
				count ++;
				//保存转入学校
				str = new String[6];
				str[0] = String.valueOf(count);
				str[1] = String.valueOf(in+1);
				str[2] = "1";
				str[3] = "1";
				str[4] = "转入学校";
				str[5] = "2";//单位类型 （2学校）
				auditFlowList.add(str);
			}

			//保存转出审核单位信息
			for(int i = out; i > regionLevelInt ; i--){
				str = new String[6];
				str[0] = (auditFlowList.size()+1)+"";
				str[1] = String.valueOf(i);
				str[2] = "1";
				if(auditTypes[1].equals("0")){
					if(i>=nowRegionLevel){
						str[4] = unitName[i];
					}else{
						continue;
					}
				}
				else
					str[4] = "转出"+unitName[i];	
				str[3] = "0";
				str[5] = "1";//单位类型 （1教育局）
				map.put("1_" + i, str);
//				auditFlowList.add(str);
			}
			
			
			
			if(!auditTypes[1].equals("0")){
				//保存转入审核单位信息
				for(int i = regionLevelInt+1; i <= in; i++){
					str = new String[6];
					str[0] = (auditFlowList.size()+1)+"";
					str[1] = String.valueOf(i);
					str[2] = "1";
					str[4] = "转入"+unitName[i];
					str[3] = "1";
					str[5] = "1";//单位类型 （1教育局）
					map.put("2_" + i, str);
//					auditFlowList.add(str);
				}
				
				//保存转入学校
//				str = new String[5];
//				str[0] = (auditFlowList.size()+1)+"";
//				str[1] = (Integer.parseInt(auditTypes[1])+1)+"";
//				str[2] = "-1";
//				str[3] = "1";
//				str[4] = "转入学校";
//				auditFlowList.add(str);
			}
			int cyc = out > in ? out : in;
			for(int i = cyc; i >= regionLevelInt; i --){
				String[] ss = map.get("1_" + i);
				if (!ArrayUtils.isEmpty(ss)){
					ss[0] = String.valueOf(++ count); 
					auditFlowList.add(ss);
				}
				ss = map.get("2_" + i);
				if (!ArrayUtils.isEmpty(ss)){
					ss[0] = String.valueOf(++ count); 
					auditFlowList.add(ss);
				}
			}
			//保存审核最高级别单位信息
			if(!auditTypes[1].equals("0") || regionLevelInt==nowRegionLevel){
				str = new String[6];
				str[0] = (auditFlowList.size()+1)+"";
				str[1] = regionLevel;
				str[2] = "1";
				str[3] = "1";
				str[4] = unitName[regionLevelInt];
				str[5] = "1";//单位类型 （1教育局）
				auditFlowList.add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return auditFlowList;
	}
	
	/**
	 * 清楚数据
	 * 
	 * @param auditType 异动方式
	 * @param section 学段
	 * @param businessType 异动类别，-255
	 */
	public void deleteAuditFlow(String auditType,String section,String businessType){
		jwAuditFlowManageDao.deleteAuditFlow(auditType, section, businessType);
	}
	
	/**
	 * 保存数据
	 * 
	 * @param auditType 异动方式
	 * @param section 学段
	 * @param businessType 异动类别，-255
	 * @param auditFlowList 最终审核单位列表
	 */
	public void addAuditFlow(String auditType,String section,String businessType,List auditFlowList){
		jwAuditFlowManageDao.addAuditFlow(auditType, section, businessType, auditFlowList);
	}
	
	/**
	 * 获得所有易动类型
	 * 
	 * @return 所有易动类型
	 */
//	public List getFlowTypeList(){
//		return jwAuditFlowManageDao.getFlowTypeList();
//	}
	
	public JwAuditFlowManageDao getJwAuditFlowManageDao() { 
		return jwAuditFlowManageDao;
	}
	public void setJwAuditFlowManageDao(JwAuditFlowManageDao jwAuditFlowManageDao) {
		this.jwAuditFlowManageDao = jwAuditFlowManageDao;
	}

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }
	

}
