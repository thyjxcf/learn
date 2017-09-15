package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.TeachArea;
import net.zdsoft.eisu.base.common.service.TeachAreaService;

public class TeachAreaDivAction extends ObjectDivAction<TeachArea>{

	private static final long serialVersionUID = 7366973724358628316L;
	protected TeachAreaService teachAreaService;

	@Override
	protected List<TeachArea> getDatasByUnitId() {
		return teachAreaService.getTeachAreas(getUnitId());
	}

	@Override
	protected void toObject(TeachArea area, SimpleObject object) {
		if (area == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(area.getId());
		object.setObjectName(area.getAreaName());
		object.setUnitId(area.getUnitId());
	}

	public void setTeachAreaService(TeachAreaService teachAreaService) {
		this.teachAreaService = teachAreaService;
	}
	
}
