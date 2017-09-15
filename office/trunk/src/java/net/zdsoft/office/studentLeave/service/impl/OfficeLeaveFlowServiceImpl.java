package net.zdsoft.office.studentLeave.service.impl;

import java.util.*;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.studentLeave.entity.OfficeLeaveFlow;
import net.zdsoft.office.studentLeave.service.OfficeLeaveFlowService;
import net.zdsoft.office.studentLeave.dao.OfficeLeaveFlowDao;
import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.component.flowManage.constant.FlowConstant;
import net.zdsoft.eis.component.flowManage.entity.Flow;
import net.zdsoft.eis.component.flowManage.service.FlowManageService;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
/**
 * office_leave_flow
 * @author 
 * 
 */
public class OfficeLeaveFlowServiceImpl implements OfficeLeaveFlowService{
	private OfficeLeaveFlowDao officeLeaveFlowDao;
	private FlowManageService flowManageService;
	private GradeService gradeService;

	@Override
	public OfficeLeaveFlow save(OfficeLeaveFlow officeLeaveFlow){
		return officeLeaveFlowDao.save(officeLeaveFlow);
	}

	@Override
	public Integer delete(String[] ids){
		return officeLeaveFlowDao.delete(ids);
	}

	@Override
	public Integer update(OfficeLeaveFlow officeLeaveFlow){
		return officeLeaveFlowDao.update(officeLeaveFlow);
	}

	@Override
	public OfficeLeaveFlow getOfficeLeaveFlowById(String id){
		return officeLeaveFlowDao.getOfficeLeaveFlowById(id);
	}

	@Override
	public Map<String, OfficeLeaveFlow> getOfficeLeaveFlowMapByIds(String[] ids){
		return officeLeaveFlowDao.getOfficeLeaveFlowMapByIds(ids);
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlowList(){
		return officeLeaveFlowDao.getOfficeLeaveFlowList();
	}

	@Override
	public void deleteOfficeLeaveFlowByType(String unitId,String gradeId, String type) {
		officeLeaveFlowDao.deleteOfficeLeaveFlowByType(unitId,gradeId, type);
	}
	@Override
	public List<OfficeLeaveFlow> getFlowsByGradeIdAndType(String unitId,
			String gradeId, String type) {
		return officeLeaveFlowDao.getFlowsByGradeIdAndType(unitId,gradeId,type);
	}
	@Override
	public void saveOfficeLeaveFlowByUnitIdAndType(String unitId,String gradeId, String type,
			String[] flowIds) {
		if(ArrayUtils.isNotEmpty(flowIds)){//先删除再更新
			List<OfficeLeaveFlow> officeLeaveFlows=new ArrayList<OfficeLeaveFlow>();
			for (String flowId : flowIds) {
				OfficeLeaveFlow officeLeaveFlow=new OfficeLeaveFlow();
				officeLeaveFlow.setFlowId(flowId);
				officeLeaveFlow.setLeaveType(type);
				officeLeaveFlow.setGradeId(gradeId);
				officeLeaveFlow.setUnitId(unitId);
				officeLeaveFlows.add(officeLeaveFlow);
			}
			officeLeaveFlowDao.deleteOfficeLeaveFlowByType(unitId,gradeId, type);
			officeLeaveFlowDao.batchSave(officeLeaveFlows);
		}else{
			officeLeaveFlowDao.deleteOfficeLeaveFlowByType(unitId,gradeId, type);
			OfficeLeaveFlow officeLeaveFlow=new OfficeLeaveFlow();
			officeLeaveFlow.setLeaveType(type);
			officeLeaveFlow.setUnitId(unitId);
			officeLeaveFlow.setGradeId(gradeId);
			officeLeaveFlowDao.save(officeLeaveFlow);
		}
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlowsByUnitId(String unitId,String type) {
		Map<String,List<OfficeLeaveFlow>> officeLMap=getOfficeLMap(unitId,type);
		List<OfficeLeaveFlow> officeLeaveFlows=new ArrayList<OfficeLeaveFlow>();
		Map<String,Grade> gradeMap=gradeService.getGradeMapBySchid(unitId);
		if(MapUtils.isNotEmpty(officeLMap)){
			for (String gradeId : officeLMap.keySet()) {
				String flowNames = "";
				String flowIds = "";
				OfficeLeaveFlow officeLeaveFlow=new OfficeLeaveFlow();
				List<OfficeLeaveFlow> officeList=officeLMap.get(gradeId);
				officeLeaveFlow.setId(UUIDGenerator.getUUID());
				officeLeaveFlow.setLeaveType(type);
				officeLeaveFlow.setUnitId(unitId);
				if(gradeMap.containsKey(gradeId)){
					Grade grade=gradeMap.get(gradeId);
					officeLeaveFlow.setGradeName(grade.getGradename());
					officeLeaveFlow.setAcadyear(grade.getAcadyear());
					officeLeaveFlow.setSection(grade.getSection());
					officeLeaveFlow.setGradeId(grade.getId());
				}
				if(CollectionUtils.isEmpty(officeList)){
					officeLeaveFlows.add(officeLeaveFlow);
				}else{
					for (OfficeLeaveFlow officeLeaveFlow2 : officeList) {
						if(StringUtils.isNotBlank(officeLeaveFlow2.getFlowName())){
							flowNames +=officeLeaveFlow2.getFlowName()+",";
							flowIds +=officeLeaveFlow2.getFlowId()+",";
						}
					}
					if(StringUtils.isNotBlank(flowNames)){
						flowNames=flowNames.substring(0, flowNames.length()-1);
						flowIds=flowIds.substring(0, flowIds.length()-1);
					}
					officeLeaveFlow.setFlowIds(flowIds);
					officeLeaveFlow.setFlowName(flowNames);
					officeLeaveFlows.add(officeLeaveFlow);
				}
			}
		}
		
		if(CollectionUtils.isNotEmpty(officeLeaveFlows)){
			Collections.sort(officeLeaveFlows, new Comparator<OfficeLeaveFlow>() {

				@Override
				public int compare(OfficeLeaveFlow o1, OfficeLeaveFlow o2) {
					if(o1.getSection()==o2.getSection()){
						return o2.getAcadyear().compareTo(o1.getAcadyear());
					}
					return o1.getSection().compareTo(o2.getSection());
				}
			});
		}
		
		return officeLeaveFlows;
	}

	@Override
	public Map<String, List<OfficeLeaveFlow>> getOfficeLMap(String unitId,String type) {
		List<OfficeLeaveFlow> officeLeaveFlows=officeLeaveFlowDao.getOfficeLeaveFlows(unitId,type);
		if(CollectionUtils.isEmpty(officeLeaveFlows)){
			initOfficeLeaveFlow(unitId);
			officeLeaveFlows=officeLeaveFlowDao.getOfficeLeaveFlows(unitId,type);
		}
		Map<String,List<OfficeLeaveFlow>> leaveMap=new HashMap<String, List<OfficeLeaveFlow>>();
		List<Flow> flowList=new ArrayList<Flow>();
		if(StringUtils.equals(type, "1")){
			flowList=flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "2")){
			flowList=flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_TEMPORARY,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "3")){
			flowList=flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_LIVE,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}else if(StringUtils.equals(type, "4")){
			flowList=flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_LONG,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		}
		//List<Flow> flowList=flowManageService.getFinishFlowList(unitId, FlowConstant.FLOW_OWNER_UNIT, FlowConstant.OFFICE_STUDENT_LEAVE_GENERAL,FlowConstant.OFFICE_SUBSYSTEM,FlowConstant.OFFICEDOC_FLOW_EASY_LEVEL_1);
		Map<String,Flow> flowMap=new HashMap<String, Flow>();
		if(CollectionUtils.isNotEmpty(flowList)){
			for (Flow flow : flowList) {
				flowMap.put(flow.getFlowId(), flow);
			}
		}
		for (OfficeLeaveFlow leaveFlow : officeLeaveFlows) {
			List<OfficeLeaveFlow> tempList=leaveMap.get(leaveFlow.getGradeId());
			if(CollectionUtils.isEmpty(tempList)){
				tempList=new ArrayList<OfficeLeaveFlow>();
			}
			Flow flow=flowMap.get(leaveFlow.getFlowId());
			if(flow!=null){
				leaveFlow.setFlowName(flow.getFlowName());
			}
			tempList.add(leaveFlow);
			leaveMap.put(leaveFlow.getGradeId(), tempList);
		}
		
		Set<String> gradeIdSet=new HashSet<String>();
		Set<String> newGradeIdSet=new HashSet<String>();
		Set<String> noGradeId=new HashSet<String>();
		Set<String> allGradeId=new HashSet<String>();
		for (Entry<String, List<OfficeLeaveFlow>> entry : leaveMap.entrySet()) {
			gradeIdSet.add(entry.getKey());
		}
		List<Grade> grades=gradeService.getGrades(unitId);
		for (Grade grade : grades) {
			allGradeId.add(grade.getId());
		}
		for (String gradeId : allGradeId) {
			if(!gradeIdSet.contains(gradeId)){
				newGradeIdSet.add(gradeId); 
			}
		}
		for (String gradeId : newGradeIdSet) {
			List<OfficeLeaveFlow> tempList=new ArrayList<OfficeLeaveFlow>();
			leaveMap.put(gradeId, tempList);
		}
		
		for (String gradeId : gradeIdSet) {
			if(!allGradeId.contains(gradeId)){
				noGradeId.add(gradeId);
			}
		}
		for (String gradeId : noGradeId) {
			leaveMap.remove(gradeId);
		}
		return leaveMap;
	}

	private void initOfficeLeaveFlow(String unitId){
		List<OfficeLeaveFlow> officeLeaveFlows=new ArrayList<OfficeLeaveFlow>();
		List<Grade> grades=gradeService.getGrades(unitId);
		for (int i = 1; i < 5; i++) {
			for (Grade grade : grades) {
				OfficeLeaveFlow officeLeaveFlow=new OfficeLeaveFlow();
				officeLeaveFlow.setUnitId(unitId);
				officeLeaveFlow.setLeaveType(i+"");
				officeLeaveFlow.setGradeId(grade.getId());
				officeLeaveFlows.add(officeLeaveFlow);
			}
		}
		batchSave(officeLeaveFlows);
	}
	
	@Override
	public void batchSave(List<OfficeLeaveFlow> officeLeaveFlows) {
		officeLeaveFlowDao.batchSave(officeLeaveFlows);
	}

	@Override
	public List<OfficeLeaveFlow> getOfficeLeaveFlowPage(Pagination page){
		return officeLeaveFlowDao.getOfficeLeaveFlowPage(page);
	}

	public void setOfficeLeaveFlowDao(OfficeLeaveFlowDao officeLeaveFlowDao){
		this.officeLeaveFlowDao = officeLeaveFlowDao;
	}

	public void setFlowManageService(FlowManageService flowManageService) {
		this.flowManageService = flowManageService;
	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}
	
}

