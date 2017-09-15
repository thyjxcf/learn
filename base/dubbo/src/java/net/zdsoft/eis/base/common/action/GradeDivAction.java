package net.zdsoft.eis.base.common.action;

import java.util.List;

import net.zdsoft.eis.base.simple.entity.SimpleObject;
import net.zdsoft.eisu.base.common.entity.EisuGrade;
import net.zdsoft.eisu.base.common.service.EisuGradeService;

public class GradeDivAction extends ObjectDivAction<EisuGrade> {

	private static final long serialVersionUID = -7625918007547162371L;

	private EisuGradeService eisuGradeService;

	@Override
	protected List<EisuGrade> getDatasByUnitId() {
		return eisuGradeService.getGrades(getUnitId());
	}
	
	@Override
	protected List<EisuGrade> getDatasByConditon() {
		return eisuGradeService.getActiveGrades(getUnitId());
	}

	@Override
	protected void toObject(EisuGrade grade, SimpleObject object) {
		if (grade == null) {
			return;
		}
		if (object == null) {
			return;
		}
		object.setId(grade.getAcadyear());
		object.setObjectName(grade.getGradename());
		object.setUnitId(grade.getSchid());

	}

	public void setEisuGradeService(EisuGradeService eisuGradeService) {
		this.eisuGradeService = eisuGradeService;
	}

}
