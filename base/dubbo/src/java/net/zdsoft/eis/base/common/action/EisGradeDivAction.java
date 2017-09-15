package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Grade;
import net.zdsoft.eis.base.common.service.GradeService;
import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.service.EisuGradeService;

public class EisGradeDivAction extends ObjectDivAction<Grade> {

	private static final long serialVersionUID = -7625918007547162371L;

	private GradeService gradeService;

	@Override
	protected List<Grade> getDatasByUnitId() {
		return gradeService.getGrades(getUnitId());
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

}
