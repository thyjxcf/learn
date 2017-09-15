package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.common.action.ObjectDivAction;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.basedata.teachplace.entity.BaseTeachPlace;
import net.zdsoft.eisu.basedata.teachplace.service.BaseTeachPlaceService;

public class TeachPlaceDivAction extends ObjectDivAction<BaseTeachPlace>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<BaseTeachPlace> baseTeachPlaceList;
	private BaseTeachPlaceService baseTeachPlaceService;
	private String teachPlaceName;
	
	public TeachPlaceDivAction() {
		setShowLetterIndex(true);
	}

	public List<BaseTeachPlace> getDatasByGroupId(){
		baseTeachPlaceList = baseTeachPlaceService.getBaseTeachPlaceListByUnitId(getUnitId());
		return baseTeachPlaceList;
	}
	
	@Override
	protected void toObject(BaseTeachPlace e, SimpleObject object) {
		if (e == null) {
			return;
		}
		if (object == null) {
			return;
		}

		object.setId(e.getId());
		object.setObjectName(e.getPlaceName());
		object.setUnitId(e.getUnitId());		
	}

	public List<BaseTeachPlace> getBaseTeachPlaceList() {
		return baseTeachPlaceList;
	}

	public void setBaseTeachPlaceList(List<BaseTeachPlace> baseTeachPlaceList) {
		this.baseTeachPlaceList = baseTeachPlaceList;
	}

	public void setBaseTeachPlaceService(BaseTeachPlaceService baseTeachPlaceService) {
		this.baseTeachPlaceService = baseTeachPlaceService;
	}

	public String getTeachPlaceName() {
		return teachPlaceName;
	}

	public void setTeachPlaceName(String teachPlaceName) {
		this.teachPlaceName = teachPlaceName;
	}

	
}
