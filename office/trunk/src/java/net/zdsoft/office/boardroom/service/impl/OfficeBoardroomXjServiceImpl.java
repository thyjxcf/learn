package net.zdsoft.office.boardroom.service.impl;

import java.util.*;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.office.boardroom.entity.OfficeBoardroomXj;
import net.zdsoft.office.boardroom.service.OfficeBoardroomXjService;
import net.zdsoft.office.boardroom.dao.OfficeBoardroomXjDao;
import net.zdsoft.keel.util.Pagination;
/**
 * office_boardroom_xj
 * @author 
 * 
 */
public class OfficeBoardroomXjServiceImpl implements OfficeBoardroomXjService{
	private OfficeBoardroomXjDao officeBoardroomXjDao;

	@Override
	public OfficeBoardroomXj save(OfficeBoardroomXj officeBoardroomXj){
		return officeBoardroomXjDao.save(officeBoardroomXj);
	}

	@Override
	public Integer delete(String[] ids){
		return officeBoardroomXjDao.delete(ids);
	}

	@Override
	public Integer update(OfficeBoardroomXj officeBoardroomXj){
		return officeBoardroomXjDao.update(officeBoardroomXj);
	}

	@Override
	public OfficeBoardroomXj getOfficeBoardroomXjById(String id){
		return officeBoardroomXjDao.getOfficeBoardroomXjById(id);
	}

	@Override
	public Map<String, OfficeBoardroomXj> getOfficeBoardroomXjMapByIds(String[] ids){
		return officeBoardroomXjDao.getOfficeBoardroomXjMapByIds(ids);
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjList(){
		return officeBoardroomXjDao.getOfficeBoardroomXjList();
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjPage(Pagination page){
		return officeBoardroomXjDao.getOfficeBoardroomXjPage(page);
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdList(String unitId){
		return officeBoardroomXjDao.getOfficeBoardroomXjByUnitIdList(unitId);
	}

	@Override
	public List<OfficeBoardroomXj> getOfficeBoardroomXjByUnitIdPage(String unitId, Pagination page){
		List<OfficeBoardroomXj> officeBoardroomXjList= officeBoardroomXjDao.getOfficeBoardroomXjByUnitIdPage(unitId, page);
		for (OfficeBoardroomXj officeBoardroomXj : officeBoardroomXjList) {
			String content="";
			if(!StringUtils.equals(String.valueOf(officeBoardroomXj.getRostrum()),"0")){
				content+="主席台("+officeBoardroomXj.getRostrum()+"人)";
			}
			if(!StringUtils.equals(String.valueOf(officeBoardroomXj.getConferenceSeats()),"0")){
				content+=content.isEmpty()?"会议席("+officeBoardroomXj.getConferenceSeats()+"人)":"、会议席("+officeBoardroomXj.getConferenceSeats()+"人)";
			}
			if(!StringUtils.equals(String.valueOf(officeBoardroomXj.getTableType()),"0")){
				content+=content.isEmpty()?"围桌式("+officeBoardroomXj.getTableType()+"人)":"、围桌式("+officeBoardroomXj.getTableType()+"人)";
			}
			if(!StringUtils.equals(String.valueOf(officeBoardroomXj.getAttendNumber()),"0")){
				content+=content.isEmpty()?"列席("+officeBoardroomXj.getAttendNumber()+"人)":"、列席("+officeBoardroomXj.getAttendNumber()+"人)";
			}
			if(officeBoardroomXj.getIsProjector()){
				content+=content.isEmpty()?"带投影设备":"、带投影设备";
			}
			officeBoardroomXj.setContent(content);
		}
		return officeBoardroomXjList;
	}
	
	@Override
	public boolean isExistConflict(String unitId, String name,String id) {
		return officeBoardroomXjDao.isExistConflict(unitId, name,id);
	}
	
	@Override
	public Map<String, OfficeBoardroomXj> getOfficeBoardroomMapByUnitId(
			String unitId) {
		return officeBoardroomXjDao.getOfficeBoardroomMapByUnitId(unitId);
	}

	public void setOfficeBoardroomXjDao(OfficeBoardroomXjDao officeBoardroomXjDao){
		this.officeBoardroomXjDao = officeBoardroomXjDao;
	}
}
