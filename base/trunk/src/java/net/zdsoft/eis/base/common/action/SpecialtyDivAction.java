package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.Specialty;
import net.zdsoft.eisu.base.common.service.SpecialtyService;

import org.apache.commons.lang.StringUtils;

public class SpecialtyDivAction extends ObjectDivAction<Specialty> {

	private static final long serialVersionUID = -5899505490171352190L;

	private SpecialtyService specialtyService;

	@Override
	protected List<Specialty> getDatasByConditon() {
		if (StringUtils.isBlank(teacherId))
			teacherId = getLoginInfo().getUser().getTeacherid();
		List<Specialty> list = specialtyService.getSpecialtysByUnitId(getUnitId());
		List<Specialty> resultList =new ArrayList<Specialty>();
		List<String> ids = new ArrayList<String>();
		if (stusysShowPopedom) {
			ids = subsystemPopedomService.getPopedomSpecialtyIds(teacherId);
			for(Specialty sp : list){
        		if(ids.contains(sp.getId()))
        			resultList.add(sp);
        	}
        	list = resultList;
		}
		return list;
	}

	@Override
	protected void toObject(Specialty specialty, SimpleObject object) {
		if (specialty == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(specialty.getId());
		object.setObjectCode(specialty.getSpecialtyCode());
		object.setObjectName(specialty.getSpecialtyName());
		object.setUnitId(specialty.getUnitId());
	}

	public void setSpecialtyService(SpecialtyService specialtyService) {
		this.specialtyService = specialtyService;
	}

}
