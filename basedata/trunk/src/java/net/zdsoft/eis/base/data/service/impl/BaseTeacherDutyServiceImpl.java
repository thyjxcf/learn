package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.data.dao.BaseTeacherDutyDao;
import net.zdsoft.eis.base.data.entity.BaseTeacherDuty;
import net.zdsoft.eis.base.data.service.BaseMcodeDetailService;
import net.zdsoft.eis.base.data.service.BaseTeacherDutyService;
import net.zdsoft.eis.base.sync.EventSourceType;

public class BaseTeacherDutyServiceImpl implements BaseTeacherDutyService {

	private BaseTeacherDutyDao baseTeacherDutyDao;

	private BaseMcodeDetailService baseMcodeDetailService;

	public void setBaseTeacherDutyDao(BaseTeacherDutyDao baseTeacherDutyDao) {
		this.baseTeacherDutyDao = baseTeacherDutyDao;
	}

	public void setBaseMcodeDetailService(
			BaseMcodeDetailService baseMcodeDetailService) {
		this.baseMcodeDetailService = baseMcodeDetailService;
	}

	@Override
	public void addTeacherDuty(BaseTeacherDuty duty) {
		baseTeacherDutyDao.insertTeacherDuty(duty);
	}

	@Override
	public void updateTeacherDuty(BaseTeacherDuty duty) {
		baseTeacherDutyDao.updateTeacherDuty(duty);
	}

	@Override
	public void addTeacherDuties(List<BaseTeacherDuty> dutyList) {
		baseTeacherDutyDao.insertTeacherDuties(dutyList);
	}

	@Override
	public void updateTeacherDuties(List<BaseTeacherDuty> dutyList) {
		baseTeacherDutyDao.updateTeacherDuties(dutyList);
	}

	@Override
	public void deleteTeacherDutiesByTeacherIds(String[] teacherIds,
			EventSourceType eventSource) {
		baseTeacherDutyDao.deleteTeacherDutiesByTeacherIds(teacherIds,
				eventSource);
	}

	@Override
	public void deleteTeacherDutiesByIds(String[] ids,
			EventSourceType eventSource) {
		baseTeacherDutyDao.deleteTeacherDutiesByIds(ids, eventSource);
	}

	@Override
	public List<BaseTeacherDuty> getTeacherDutyListByTeacherId(String teacherId) {
		return baseTeacherDutyDao.getTeacherDutyListByTeacherId(teacherId);
	}

	@Override
	public List<BaseTeacherDuty> getTeacherDutyListByTeacherIds(
			String[] teacherIds) {
		return baseTeacherDutyDao.getTeacherDutyListByTeacherIds(teacherIds);
	}

	@Override
	public Map<String, List<BaseTeacherDuty>> getTeacherDutyMapByTeacherIds(
			String[] teacherIds) {
		Map<String, List<BaseTeacherDuty>> dutyMap = new HashMap<String, List<BaseTeacherDuty>>();
		List<BaseTeacherDuty> dutyList = getTeacherDutyListByTeacherIds(teacherIds);
		for (BaseTeacherDuty duty : dutyList) {
			List<BaseTeacherDuty> dList = new ArrayList<BaseTeacherDuty>();
			if (dutyMap.containsKey(duty.getTeacherId())) {
				dList = dutyMap.get(duty.getTeacherId());
			}
			dList.add(duty);
			dutyMap.put(duty.getTeacherId(), dList);
		}
		return dutyMap;
	}

	public String getTeacherDutyNames(String teacherId) {
		Map<String, Mcodedetail> map = baseMcodeDetailService
				.getMcodeDetailMap("DM-XXZW");
		List<BaseTeacherDuty> dutyList = getTeacherDutyListByTeacherId(teacherId);
		String dutyNames = "";
		if (CollectionUtils.isNotEmpty(dutyList)) {
			for (BaseTeacherDuty duty : dutyList) {
				if(map.get(duty.getDutyCode())!=null)
					dutyNames += map.get(duty.getDutyCode()).getContent() + ",";
			}
			if (StringUtils.isNotBlank(dutyNames))
				dutyNames = dutyNames.substring(0, dutyNames.length() - 1);
		}
		return dutyNames;
	}

}
