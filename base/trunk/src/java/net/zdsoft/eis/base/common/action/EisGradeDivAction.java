package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.BasicClassService;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;

import org.apache.commons.lang3.StringUtils;

public class EisGradeDivAction extends ObjectDivAction<Grade> {

	private static final long serialVersionUID = -7625918007547162371L;

	private GradeService gradeService;
	private BasicClassService basicClassService;
	private String acadyear;

	public String getAcadyear() {
		return acadyear;
	}

	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	

	@Override
	protected List<Grade> getDatasByUnitId() {
		if(StringUtils.isBlank(acadyear)){
			return gradeService.getGrades(getUnitId());
		}else{
			return basicClassService.getGrades(getUnitId(),acadyear);
		}
	}
	
	@Override
	protected void toObject(Grade grade, SimpleObject object) {
		if (grade == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(grade.getId());
		object.setObjectName(grade.getGradename());
		object.setUnitId(grade.getSchid());

	}

	public void setGradeService(GradeService gradeService) {
		this.gradeService = gradeService;
	}

	public void setBasicClassService(BasicClassService basicClassService) {
		this.basicClassService = basicClassService;
	}
	
}
