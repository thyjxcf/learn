package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.TeachPlace;
import net.zdsoft.eisu.base.common.service.TeachPlaceService;

import org.apache.commons.lang.StringUtils;

public class TeachPlaceDivAction extends ObjectDivAction<TeachPlace> {

	private static final long serialVersionUID = -7734117014607973171L;

	protected List<TeachPlace> teachPlaceList;
	private String submitMethod;
	private String closeMethod;
	private String cancelMethod;

	protected TeachPlaceService teachPlaceService;
	private String placeType;

	public TeachPlaceDivAction() {
		setShowLetterIndex(true);
	}

	@Override
	public List<TeachPlace> getDatasByGroupId() {
		if (StringUtils.isNotBlank(groupId)) {
			teachPlaceList = teachPlaceService.getTeachPlaceByTypeInArea(
					groupId, placeType);
		} else {
			teachPlaceList = teachPlaceService
					.getTeachPlacesByUnitId(getUnitId());
		}
		return teachPlaceList;
	}

	/**
	 * 左匹配姓名
	 * 
	 * @return
	 */
	@Override
	public List<TeachPlace> getDatasFaintness() {
		teachPlaceList = teachPlaceService.getTeachPlacesByFaintness(
				getUnitId(), queryObjectName, queryObjectCode, placeType);
		return teachPlaceList;
	}

	protected void toObject(TeachPlace tp, SimpleObject object) {
		if (tp == null) {
			return;
		}
		if (object == null) {
			return;
		}

		object.setId(tp.getId());
		object.setObjectCode(tp.getPlaceCode());
		object.setObjectName(tp.getPlaceName());
		object.setUnitiveCode("");
		object.setGroupId(tp.getTeachAreaId());
		object.setUnitId(tp.getUnitId());
	}

	/*
	 * set and get
	 */
	public String getClassId() {
		return groupId;
	}

	public void setClassId(String classId) {
		this.groupId = classId;
	}

	public void setTeachPlaceService(TeachPlaceService teachPlaceService) {
		this.teachPlaceService = teachPlaceService;
	}

	public String getSubmitMethod() {
		return submitMethod;
	}

	public void setSubmitMethod(String submitMethod) {
		this.submitMethod = submitMethod;
	}

	public String getCloseMethod() {
		return closeMethod;
	}

	public void setCloseMethod(String closeMethod) {
		this.closeMethod = closeMethod;
	}

	public String getCancelMethod() {
		return cancelMethod;
	}

	public void setCancelMethod(String cancelMethod) {
		this.cancelMethod = cancelMethod;
	}

	public List<TeachPlace> getTeachPlaceList() {
		return teachPlaceList;
	}

	public String getQueryStudentCode() {
		return queryObjectCode;
	}

	public void setQueryStudentCode(String queryStudentCode) {
		this.queryObjectCode = queryStudentCode;
	}

	public String getQueryStudentName() {
		return queryObjectName;
	}

	public void setQueryStudentName(String queryStudentName) {
		this.queryObjectName = queryStudentName;
	}

	public String getTdName() {
		return getNameObjectId();
	}

	public void setTdName(String tdName) {
		setNameObjectId(tdName);
	}

	public String getIdName() {
		return getIdObjectId();
	}

	public void setIdName(String idName) {
		setIdObjectId(idName);
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}
}
