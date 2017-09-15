package net.zdsoft.office.dutyinformation.service.impl;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.office.dutyinformation.entity.OfficeDutyPlace;
import net.zdsoft.office.dutyinformation.entity.OfficePatrolPlace;
import net.zdsoft.office.dutyinformation.service.OfficeDutyPlaceService;
import net.zdsoft.office.dutyinformation.service.OfficePatrolPlaceService;
import net.zdsoft.office.dutyinformation.dao.OfficeDutyPlaceDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_duty_place
 * @author 
 * 
 */
public class OfficeDutyPlaceServiceImpl implements OfficeDutyPlaceService{
	private OfficeDutyPlaceDao officeDutyPlaceDao;
	private OfficePatrolPlaceService officePatrolPlaceService;

	@Override
	public OfficeDutyPlace save(OfficeDutyPlace officeDutyPlace){
		return officeDutyPlaceDao.save(officeDutyPlace);
	}

	@Override
	public Integer delete(String[] ids){
		return officeDutyPlaceDao.delete(ids);
	}

	@Override
	public Integer update(OfficeDutyPlace officeDutyPlace){
		return officeDutyPlaceDao.update(officeDutyPlace);
	}

	@Override
	public void batchUpdateOrSave(List<OfficeDutyPlace> insertList,
			List<OfficeDutyPlace> updateList) {
		officeDutyPlaceDao.batchSave(insertList);
		officeDutyPlaceDao.batchUpdate(updateList);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndUserId(
			String unitId, String userId, String dutyApplyId, Date dutyTime) {
		List<OfficeDutyPlace> officeDutyPlaces=officeDutyPlaceDao.getOfficeDutyPlacesByUnitIdAndUserId(unitId, userId, dutyApplyId, dutyTime);
		if(CollectionUtils.isNotEmpty(officeDutyPlaces)){
			Set<String> placeId=new HashSet<String>();
			for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
				placeId.add(officeDutyPlace.getPatrolPlaceId());
			}
			Map<String,OfficePatrolPlace> officePMap=officePatrolPlaceService.getOfficePMap(placeId.toArray(new String[0]));
			for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
				officeDutyPlace.setIdExit(true);
				if(officePMap.containsKey(officeDutyPlace.getPatrolPlaceId())){
					OfficePatrolPlace officePatrolPlace=officePMap.get(officeDutyPlace.getPatrolPlaceId());
					if(officePatrolPlace!=null){
						officeDutyPlace.setPlaceName(officePatrolPlace.getPlaceName());
					}else{
						officeDutyPlace.setPlaceName("(该巡查地点已删除)");
					}
				}else{
					officeDutyPlace.setPlaceName("(该巡查地点已删除)");
				}
			}
		}
		return officeDutyPlaces;
	}

	@Override
	public Map<String,OfficeDutyPlace> getOfficeDutyPlacesByUnitIdAndOthers(
			String unitId, String setId, Date startTime, Date endTime) {
		return officeDutyPlaceDao.getOfficeDutyPlacesByUnitIdAndOthers(unitId, setId, startTime, endTime);
	}

	@Override
	public Map<String, List<String>> getOfficeDMap(String unitId,
			String setId) {
		List<OfficeDutyPlace> officeDutyPlaces=officeDutyPlaceDao.getOfficeDMap(unitId, setId);
		Map<String,List<String>> map=new HashMap<String, List<String>>();
		Set<Date> dateSet=new HashSet<Date>();
		for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
			dateSet.add(officeDutyPlace.getDutyTime());
		}
		for (Date date : dateSet) {
			Set<String> userSet=new HashSet<String>();
			List<String> userlist=new ArrayList<String>();
			for (OfficeDutyPlace officeDutyPlace : officeDutyPlaces) {
				if(date.compareTo(officeDutyPlace.getDutyTime())==0){
					userSet.add(officeDutyPlace.getDutyUserId());
				}
			}
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy.MM.dd");
			String timeStr=sdf.format(date);
			userlist.addAll(userSet);
			map.put(timeStr, userlist);
		}
		return map;
	}

	@Override
	public OfficeDutyPlace getOfficeDutyPlaceById(String id){
		return officeDutyPlaceDao.getOfficeDutyPlaceById(id);
	}

	@Override
	public Map<String, OfficeDutyPlace> getOfficeDutyPlaceMapByIds(String[] ids){
		return officeDutyPlaceDao.getOfficeDutyPlaceMapByIds(ids);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceList(){
		return officeDutyPlaceDao.getOfficeDutyPlaceList();
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlacePage(Pagination page){
		return officeDutyPlaceDao.getOfficeDutyPlacePage(page);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdList(String unitId){
		return officeDutyPlaceDao.getOfficeDutyPlaceByUnitIdList(unitId);
	}

	@Override
	public List<OfficeDutyPlace> getOfficeDutyPlaceByUnitIdPage(String unitId, Pagination page){
		return officeDutyPlaceDao.getOfficeDutyPlaceByUnitIdPage(unitId, page);
	}

	public void setOfficeDutyPlaceDao(OfficeDutyPlaceDao officeDutyPlaceDao){
		this.officeDutyPlaceDao = officeDutyPlaceDao;
	}

	public void setOfficePatrolPlaceService(
			OfficePatrolPlaceService officePatrolPlaceService) {
		this.officePatrolPlaceService = officePatrolPlaceService;
	}
	
}
