package net.zdsoft.eis.base.common.action;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.SpecialtyType;
import net.zdsoft.eisu.base.common.service.SpecialtyTypeService;

public class SpecialtyTypeDivAction extends ObjectDivAction<SpecialtyType> {
	
	private static final long serialVersionUID = 1L;
	private SpecialtyTypeService specialtyTypeService;
	
	public SpecialtyTypeDivAction(){
		setShowLetterIndex(true);
	}

	@Override
	protected void toObject(SpecialtyType specialtyType, SimpleObject object) {
		if (specialtyType == null) {
			return;
		}
		if (object == null) {
			return;
		}

		object.setId(specialtyType.getId());
		object.setObjectCode(specialtyType.getTypeCode());
		object.setObjectName(specialtyType.getTypeName());
		object.setUnitId(specialtyType.getUnitId());
	}

	@Override
	protected List<SpecialtyType> getDatasByUnitId() {
		String unitid =this.getLoginInfo().getUnitID();
		List<SpecialtyType> specialtyTypeList = new ArrayList<SpecialtyType>();
		try {
			specialtyTypeList = specialtyTypeService.getSpecialtyTypes(unitid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return specialtyTypeList;
	}

	public void setSpecialtyTypeService(SpecialtyTypeService specialtyTypeService) {
		this.specialtyTypeService = specialtyTypeService;
	}

}
