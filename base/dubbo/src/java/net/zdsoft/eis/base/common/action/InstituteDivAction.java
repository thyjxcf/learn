package net.zdsoft.eis.base.common.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.Institute;
import net.zdsoft.eisu.base.common.service.InstituteService;

public class InstituteDivAction extends ObjectDivAction<Institute> {

	private static final long serialVersionUID = -714319466636553016L;

	private InstituteService instituteService;

	private String parentId;

	private int parentType;

	private int instituteKind;

	@Override
	protected List<Institute> getDatasByConditon() {
		if (StringUtils.isBlank(parentId)) {
			return instituteService.getInstitutesByUnitId(getUnitId());
		} else {
			return instituteService.getInstitutesByParent(parentId, parentType,
					instituteKind, true);
		}

	}

	@Override
	protected void toObject(Institute institute, SimpleObject object) {
		if (institute == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(institute.getId());
		object.setObjectCode(institute.getInstituteCode());
		object.setObjectName(institute.getInstituteName());
		object.setUnitId(institute.getUnitId());
	}

	public void setInstituteService(InstituteService instituteService) {
		this.instituteService = instituteService;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getParentType() {
		return parentType;
	}

	public void setParentType(int parentType) {
		this.parentType = parentType;
	}

	public int getInstituteKind() {
		return instituteKind;
	}

	public void setInstituteKind(int instituteKind) {
		this.instituteKind = instituteKind;
	}

}
