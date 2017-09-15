package net.zdsoft.office.officeFlow.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.officeFlow.entity.OfficeFlowStepInfo;
import net.zdsoft.office.officeFlow.service.OfficeFlowStepInfoService;
import net.zdsoft.office.officeFlow.dao.OfficeFlowStepInfoDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_flow_step_info
 * @author 
 * 
 */
public class OfficeFlowStepInfoServiceImpl implements OfficeFlowStepInfoService{
	private OfficeFlowStepInfoDao officeFlowStepInfoDao;

	@Override
	public OfficeFlowStepInfo save(OfficeFlowStepInfo officeFlowStepInfo){
		return officeFlowStepInfoDao.save(officeFlowStepInfo);
	}

	@Override
	public Integer delete(String[] ids){
		return officeFlowStepInfoDao.delete(ids);
	}

	@Override
	public Integer update(OfficeFlowStepInfo officeFlowStepInfo){
		return officeFlowStepInfoDao.update(officeFlowStepInfo);
	}

	@Override
	public OfficeFlowStepInfo getOfficeFlowStepInfoById(String id){
		return officeFlowStepInfoDao.getOfficeFlowStepInfoById(id);
	}

	@Override
	public Map<String, OfficeFlowStepInfo> getOfficeFlowStepInfoMapByIds(String[] ids){
		return officeFlowStepInfoDao.getOfficeFlowStepInfoMapByIds(ids);
	}

	@Override
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoList(){
		return officeFlowStepInfoDao.getOfficeFlowStepInfoList();
	}

	@Override
	public List<OfficeFlowStepInfo> getOfficeFlowStepInfoPage(Pagination page){
		return officeFlowStepInfoDao.getOfficeFlowStepInfoPage(page);
	}

	@Override
	public void batchUpdateInfo(String flowId, String flowStepInfo){
		//info：type-stepId-taskUserId-stepUserType+type-stepId-taskUserId-stepUserType
		String[] flowStepInfos = flowStepInfo.split("\\+");
		Set<String> removeIds = new HashSet<String>();
		Map<String, String> stepInfoMap = new HashMap<String, String>();
		List<OfficeFlowStepInfo> list = new ArrayList<OfficeFlowStepInfo>();
		for(int i = 0; i < flowStepInfos.length; i++){
			String[] stepDetail = flowStepInfos[i].split("-");
			stepInfoMap.put(stepDetail[1], flowStepInfos[i]);
			removeIds.add(stepDetail[1]);
		}
		for(String stepId : stepInfoMap.keySet()){
			String[] stepDetail = stepInfoMap.get(stepId).split("-");
			if(!"remove".equals(stepDetail[0])){
				OfficeFlowStepInfo item = new OfficeFlowStepInfo();
				item.setFlowId(flowId);
				item.setStepId(stepId);
				String userIds = this.filterUserId(stepDetail[2]);
				item.setTaskUserId(userIds);
				item.setStepType(stepDetail[3]);
				list.add(item);
			}
		}
		this.batchRemove(flowId, removeIds.toArray(new String[0]));
		this.batchInsert(list);
	}
	
	public String filterUserId(String userIds){//过滤半选人ID
		Set<String> set = new HashSet<String>();
		String[] list = userIds.split(",");
		if(list.length > 0){
			for(String item : list){
				if(!item.equals("00000000000000000000000000000001") 
						&& !item.equals("00000000000000000000000000000002")
						&& !item.equals("00000000000000000000000000000003")
						&& !item.equals("00000000000000000000000000000004")
						&& !item.equals("office_schoolmaster")){
					set.add(item);
				}
			}
		}
		if(set.size()>0){
			String returnStr = "";
			for(String item : set){
				if(StringUtils.isBlank(returnStr))
					returnStr = item;
				else
					returnStr += "," + item;
			}
			return returnStr;
		}
		else{
			return "";
		}
	}
	
	@Override
	public void batchRemove(String flowId, String[] stepIds){
		officeFlowStepInfoDao.batchRemove(flowId, stepIds);
	}
	
	@Override
	public void batchInsert(List<OfficeFlowStepInfo> list){
		officeFlowStepInfoDao.batchInsert(list);
	}
	
	@Override
	public OfficeFlowStepInfo getStepInfo(String flowId, String stepId){
		List<OfficeFlowStepInfo> infos = officeFlowStepInfoDao.getStepInfo(flowId, stepId);
		if(infos.size() > 0)
			return infos.get(0);
		else
			return null;
	}
	
	@Override
	public void batchUpdateByFlowId(String oldFlowId, String newFlowId){
		List<OfficeFlowStepInfo> infos = this.getStepInfosByFlowIds(oldFlowId);
		List<OfficeFlowStepInfo> insertList = new ArrayList<OfficeFlowStepInfo>();
		if(CollectionUtils.isNotEmpty(infos)){
			for(OfficeFlowStepInfo info : infos){
				OfficeFlowStepInfo stepInfo = new OfficeFlowStepInfo();
				stepInfo.setFlowId(newFlowId);
				stepInfo.setStepId(info.getStepId());
				stepInfo.setTaskUserId(info.getTaskUserId());
				stepInfo.setStepType(info.getStepType());
				insertList.add(stepInfo);
			}
			this.batchRemove(newFlowId, null);
			this.batchInsert(insertList);
		}
	}
	
	@Override
	public List<OfficeFlowStepInfo> getStepInfosByFlowIds(String flowId){
		return officeFlowStepInfoDao.getStepInfo(flowId, null);
	}
	
	public void setOfficeFlowStepInfoDao(OfficeFlowStepInfoDao officeFlowStepInfoDao){
		this.officeFlowStepInfoDao = officeFlowStepInfoDao;
	}
}
