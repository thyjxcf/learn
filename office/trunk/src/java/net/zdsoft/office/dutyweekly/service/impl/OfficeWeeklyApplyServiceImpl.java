package net.zdsoft.office.dutyweekly.service.impl;

import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import net.zdsoft.office.dutyweekly.entity.OfficeDutyRemark;
import net.zdsoft.office.dutyweekly.entity.OfficeDutyWeekly;
import net.zdsoft.office.dutyweekly.entity.OfficeWeeklyApply;
import net.zdsoft.office.dutyweekly.service.OfficeDutyRemarkService;
import net.zdsoft.office.dutyweekly.service.OfficeDutyWeeklyService;
import net.zdsoft.office.dutyweekly.service.OfficeWeeklyApplyService;
import net.zdsoft.office.dutyweekly.dao.OfficeWeeklyApplyDao;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.util.UUIDGenerator;
/**
 * office_weekly_apply
 * @author 
 * 
 */
public class OfficeWeeklyApplyServiceImpl implements OfficeWeeklyApplyService{
	private OfficeWeeklyApplyDao officeWeeklyApplyDao;
	private OfficeDutyRemarkService officeDutyRemarkService;
	private OfficeDutyWeeklyService officeDutyWeeklyService;

	@Override
	public OfficeWeeklyApply save(OfficeWeeklyApply officeWeeklyApply){
		return officeWeeklyApplyDao.save(officeWeeklyApply);
	}

	@Override
	public Integer delete(String[] ids){
		return officeWeeklyApplyDao.delete(ids);
	}

	@Override
	public Integer update(OfficeWeeklyApply officeWeeklyApply){
		return officeWeeklyApplyDao.update(officeWeeklyApply);
	}

	@Override
	public OfficeWeeklyApply getOfficeWeeklyApplyById(String id){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyById(id);
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWeeklyApplyMapByIds(String[] ids){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyMapByIds(ids);
	}

	@Override
	public void saveOfficeWeeklyApply(String unitId, String userId,
			String[] applyRooms, String[] fullSore, String dutyWeeklyId,
			String dutyRemark,Date dutyDate) {
		List<OfficeWeeklyApply> officeWeeklyApplies=new ArrayList<OfficeWeeklyApply>();
		OfficeDutyRemark officeDutyRemark=null;
		if(StringUtils.isNotBlank(dutyRemark)){
			officeDutyRemark=new OfficeDutyRemark();
			officeDutyRemark.setCreateTime(dutyDate);
			officeDutyRemark.setCreateUserId(userId);
			officeDutyRemark.setDutyWeeklyId(dutyWeeklyId);
			officeDutyRemark.setRemark(dutyRemark);
			officeDutyRemark.setUnitId(unitId);
			officeDutyRemark.setId(UUIDGenerator.getUUID());
		}
		//if(ArrayUtils.isNotEmpty(applyRooms)){
		//	Arrays.sort(applyRooms);
		//	for (String apply : applyRooms) {
		//		OfficeWeeklyApply officeWeeklyApply=new OfficeWeeklyApply();
		//		String[] idPeriod = apply.split("_");
		//		officeWeeklyApply.setClassId(idPeriod[1]);
		//		officeWeeklyApply.setDutyProjectId(idPeriod[0]);
		//		officeWeeklyApply.setDutyDate(dutyDate);
		//		officeWeeklyApply.setDutyWeeklyId(dutyWeeklyId);
		//		if(officeDutyRemark!=null&&StringUtils.isNotBlank(officeDutyRemark.getId())){
		//			officeWeeklyApply.setDutyRemarkId(officeDutyRemark.getId());
		//		}
		//		officeWeeklyApply.setScore(Integer.valueOf(idPeriod[2]));
		//		officeWeeklyApply.setUnitId(unitId);
		//		officeWeeklyApplies.add(officeWeeklyApply);
		//	}
		//}
		if(ArrayUtils.isNotEmpty(fullSore)){
			Arrays.sort(fullSore);
			for (String apply : fullSore) {
				OfficeWeeklyApply officeWeeklyApply=new OfficeWeeklyApply();
				String[] idPeriod = apply.split("_");
				officeWeeklyApply.setClassId(idPeriod[1]);
				officeWeeklyApply.setDutyProjectId(idPeriod[0]);
				officeWeeklyApply.setDutyDate(dutyDate);
				officeWeeklyApply.setDutyWeeklyId(dutyWeeklyId);
				if(officeDutyRemark!=null&&StringUtils.isNotBlank(officeDutyRemark.getId())){
					officeWeeklyApply.setDutyRemarkId(officeDutyRemark.getId());
				}
				if(idPeriod.length>2&&StringUtils.isNotBlank(idPeriod[2])){
					officeWeeklyApply.setScore(Integer.valueOf(idPeriod[2]));
				}else{
					officeWeeklyApply.setScore(0);
				}
				officeWeeklyApply.setUnitId(unitId);
				officeWeeklyApplies.add(officeWeeklyApply);
			}
		}
		if(CollectionUtils.isNotEmpty(officeWeeklyApplies)){
			this.deleteRecordes(unitId, dutyWeeklyId, dutyDate);//删除老数据
			if(StringUtils.isNotBlank(dutyRemark)){
				officeDutyRemarkService.deleteOfficeDutyRemark(unitId, dutyWeeklyId, dutyDate);
				officeDutyRemarkService.save(officeDutyRemark);
			}else{
				officeDutyRemarkService.deleteOfficeDutyRemark(unitId, dutyWeeklyId, dutyDate);
			}
			this.batchSave(officeWeeklyApplies);
		}
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWeeklyApplyMapByUnitIdAndWeeklyId(
			String unitId, String weeklyId, Date dutyDate) {
		return officeWeeklyApplyDao.getOfficeWeeklyApplyMapByUnitIdAndWeeklyId(unitId, weeklyId, dutyDate);
	}

	@Override
	public void batchSave(List<OfficeWeeklyApply> officeWeeklyApply) {
		officeWeeklyApplyDao.batchSave(officeWeeklyApply);
	}

	@Override
	public void deleteRecordes(String unitId, String dutyWeeklyId, Date dutyDate) {
		officeWeeklyApplyDao.deleteRecordes(unitId, dutyWeeklyId, dutyDate);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyList(){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyList();
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyPage(Pagination page){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyPage(page);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdList(String unitId){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyByUnitIdList(unitId);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplyByUnitIdPage(String unitId, Pagination page){
		return officeWeeklyApplyDao.getOfficeWeeklyApplyByUnitIdPage(unitId, page);
	}

	
	@Override
	public Map<String, OfficeWeeklyApply> getOfficeCountMapByUnitIdAndWeeklyId(
			String unitId, String weeklyId, Date dutyDate) {
		return officeWeeklyApplyDao.getOfficeCountMapByUnitIdAndWeeklyId(unitId, weeklyId, dutyDate);
	}

	@Override
	public Map<String, String> getOfficeWMap(String unitId, String acadayear,
			String smster, String week, Date startTime, Date endTime,
			Date[] dutyTime) {
		List<OfficeWeeklyApply> officeWeeklyApplies=officeWeeklyApplyDao.getOfficeWMap(unitId, acadayear, smster, week, null, null, null);
		Map<String,String> coreMap=new HashMap<String, String>();
		if(CollectionUtils.isNotEmpty(officeWeeklyApplies)){
			Map<String,List<OfficeWeeklyApply>> listMap=new HashMap<String, List<OfficeWeeklyApply>>();
			Set<String> weekSet=new HashSet<String>();
			for (OfficeWeeklyApply officeWeeklyApply : officeWeeklyApplies) {
				weekSet.add(officeWeeklyApply.getDutyWeeklyId());
			}
			Map<String,OfficeDutyWeekly> dutyMap=officeDutyWeeklyService.getOfficeDMap(weekSet.toArray(new String[0]));
			for (OfficeWeeklyApply officeWeeklyApply : officeWeeklyApplies) {
				if(MapUtils.isNotEmpty(dutyMap)&&dutyMap.containsKey(officeWeeklyApply.getDutyWeeklyId())){
					OfficeDutyWeekly officeDutyWeekly=dutyMap.get(officeWeeklyApply.getDutyWeeklyId());
					if(officeDutyWeekly!=null){
						officeWeeklyApply.setWeek(officeDutyWeekly.getWeek()+"");
					}
				}
			}
			
			for (OfficeWeeklyApply officeWeeklyApply : officeWeeklyApplies) {
				List<OfficeWeeklyApply> list=listMap.get(officeWeeklyApply.getWeek());
				if(CollectionUtils.isNotEmpty(list)){
					list.add(officeWeeklyApply);
					listMap.put(officeWeeklyApply.getWeek(), list);
				}else{
					list=new ArrayList<OfficeWeeklyApply>();
					list.add(officeWeeklyApply);
					listMap.put(officeWeeklyApply.getWeek(), list);
				}
			}
			if(MapUtils.isNotEmpty(listMap)){
				for (List<OfficeWeeklyApply> officeWeeklyApplies2 : listMap.values()) {//周次对应记录
//					int core=0;
					Map<String,List<OfficeWeeklyApply>> classMap=new HashMap<String, List<OfficeWeeklyApply>>();//周次下面按班级id封装map
					for (OfficeWeeklyApply officeWeeklyApply : officeWeeklyApplies2) {
						if(classMap.containsKey(officeWeeklyApply.getClassId())){
							List<OfficeWeeklyApply> lala=classMap.get(officeWeeklyApply.getClassId());
							lala.add(officeWeeklyApply);
							classMap.put(officeWeeklyApply.getClassId(), lala);
						}else{
							List<OfficeWeeklyApply> lala=new ArrayList<OfficeWeeklyApply>();
							lala.add(officeWeeklyApply);
							classMap.put(officeWeeklyApply.getClassId(), lala);
						}
//						core+=officeWeeklyApply.getScore();
					}
					
					for (List<OfficeWeeklyApply> officeWeeklyApply : classMap.values()) {
						int core=0;
						String weeklyId="";
						for (OfficeWeeklyApply officeWeeklyApply2 : officeWeeklyApply) {
							core+=officeWeeklyApply2.getScore();
							weeklyId=officeWeeklyApply2.getClassId();
						}
						if(5+core==3){
							if(MapUtils.isEmpty(coreMap)){
								coreMap.put(weeklyId+"_"+"3", 1+"");
								continue;
							}
							if(!coreMap.containsKey(weeklyId+"_"+"3")){
								coreMap.put(weeklyId+"_"+"3", 1+"");
							}else{
								int num=Integer.parseInt(coreMap.get(weeklyId+"_"+"3"));
								if(num==0){
									coreMap.put(weeklyId+"_"+"3", 1+"");
								}else{
									coreMap.put(weeklyId+"_"+"3", (num+1)+"");
								}
							}
						}else if(5+core==5){
							if(MapUtils.isEmpty(coreMap)){
								coreMap.put(weeklyId+"_"+"5", 1+"");
								continue;
							}
							if(!coreMap.containsKey(weeklyId+"_"+"5")){
								coreMap.put(weeklyId+"_"+"5", 1+"");
							}else{
								int num=Integer.parseInt(coreMap.get(weeklyId+"_"+"5"));
								if(num==0){
									coreMap.put(weeklyId+"_"+"5", 1+"");
								}else{
									coreMap.put(weeklyId+"_"+"5", (num+1)+"");
								}
							}
						}else if(5+core==4){
							if(MapUtils.isEmpty(coreMap)){
								coreMap.put(weeklyId+"_"+"4", 1+"");
								continue;
							}
							if(!coreMap.containsKey(weeklyId+"_"+"4")){
								coreMap.put(weeklyId+"_"+"4", 1+"");
							}else{
								int num=Integer.parseInt(coreMap.get(weeklyId+"_"+"4"));
								if(num==0){
									coreMap.put(weeklyId+"_"+"4", 1+"");
								}else{
									coreMap.put(weeklyId+"_"+"4", (num+1)+"");
								}
							}
						}else{
							if(MapUtils.isEmpty(coreMap)){
								coreMap.put(weeklyId+"_"+"2", 1+"");
								continue;
							}
							if(!coreMap.containsKey(weeklyId+"_"+"2")){
								coreMap.put(weeklyId+"_"+"2", 1+"");
							}else{
								int num=Integer.parseInt(coreMap.get(weeklyId+"_"+"2"));
								if(num==0){
									coreMap.put(weeklyId+"_"+"2", 1+"");
								}else{
									coreMap.put(weeklyId+"_"+"2", (num+1)+"");
								}
							}
						}
					}
				}
			}
		}
		return coreMap;
	}

	@Override
	public Map<String, OfficeWeeklyApply> getOfficeWMapCount(String unitId, String weeklyId,Date[] dutyTime) {
		return officeWeeklyApplyDao.getOfficeWMapCount(unitId, weeklyId,dutyTime);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyApplies(String unitId,
			String weeklyId) {
		return officeWeeklyApplyDao.getOfficeWeeklyApplies(unitId, weeklyId);
	}

	@Override
	public List<OfficeWeeklyApply> getOfficeWeeklyAppliesByUnitId(
			String unitId, String[] projectId) {
		return officeWeeklyApplyDao.getOfficeWeeklyAppliesByUnitId(unitId, projectId);
	}

	public void setOfficeWeeklyApplyDao(OfficeWeeklyApplyDao officeWeeklyApplyDao){
		this.officeWeeklyApplyDao = officeWeeklyApplyDao;
	}

	public void setOfficeDutyRemarkService(
			OfficeDutyRemarkService officeDutyRemarkService) {
		this.officeDutyRemarkService = officeDutyRemarkService;
	}

	public void setOfficeDutyWeeklyService(
			OfficeDutyWeeklyService officeDutyWeeklyService) {
		this.officeDutyWeeklyService = officeDutyWeeklyService;
	}
	
}
