package net.zdsoft.eis.base.data.sync.service.impl;

import java.util.List;
import java.util.Map;

import com.winupon.syncdata.basedata.entity.son.MqTeacher;

import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.sync.AbstractHandlerTemplate;
import net.zdsoft.eis.base.sync.BatchHandlable;
import net.zdsoft.eis.base.sync.EventSourceType;
import net.zdsoft.leadin.exception.BusinessErrorException;

public class TeacherSyncServiceImpl extends AbstractHandlerTemplate<BaseTeacher, MqTeacher> implements BatchHandlable<BaseTeacher>{

	private BaseTeacherService baseTeacherService;
	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	@Override
	public void addData(BaseTeacher e) throws BusinessErrorException {
		baseTeacherService.addTeacherFromMq(e);
	}

	@Override
	public void deleteData(String id, EventSourceType eventSource) throws BusinessErrorException {
		baseTeacherService.deleteTeacher(new String[]{id}, eventSource);
	}

	@Override
	public BaseTeacher fetchOldEntity(String id) {
		return baseTeacherService.getBaseTeacher(id);
	}

	@Override
	public void updateData(BaseTeacher e) throws BusinessErrorException {
		baseTeacherService.updateTeacher(e);
	}

	@Override
	public void addDatas(List<BaseTeacher> entities) throws BusinessErrorException {
		baseTeacherService.addTeachers(entities);
		
	}

	@Override
	public void deleteDatas(String[] ids, EventSourceType eventSource) throws BusinessErrorException {
		baseTeacherService.deleteTeacher(ids, eventSource);
		
	}

	@Override
	public void updateDatas(List<BaseTeacher> entities) throws BusinessErrorException {
		baseTeacherService.updateTeachers(entities);
		
	}

    @Override
    public Map<String, BaseTeacher> fetchOldEntities(String[] ids) {
        return null;
    }

}
