package net.zdsoft.eis.base.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.data.dao.ResearchGroupDao;
import net.zdsoft.eis.base.data.dao.ResearchGroupExDao;
import net.zdsoft.eis.base.data.dto.ResearchGroupDto;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.ResearchGroupService;
import net.zdsoft.eis.eduaffairs.entity.Subject;
import net.zdsoft.eis.eduaffairs.service.SubjectService;
import net.zdsoft.keel.dao.UUIDGenerator;

public class ResearchGroupServiceImpl implements ResearchGroupService{
	
	private ResearchGroupDao researchGroupDao;
	private ResearchGroupExDao researchGroupExDao;
	private BaseTeacherService baseTeacherService;
	private SubjectService subjectService;
	
	private List<ResearchGroup> researchGroupList;
	private List<ResearchGroupEx> researchGroupExList;
	private List<ResearchGroupDto> researchGroupDtoList;
	private List<Subject> subjectList;
	private Map<String,List<ResearchGroupEx>> mapGroup;
	
	private UUIDGenerator uuid = new UUIDGenerator();
	
	public String getSubjectNames(String subjectIds) {
		String[] subjectId = subjectIds.split(",");
		subjectList = subjectService.findListOfSubjectIn(subjectId);
		String str = "";
		for (Subject subject : subjectList) {
			str = str + subject.getSubjectName() + ",";
		} 
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	@Override
	public List<ResearchGroup> getResearchGroupsByUnitId(String unitId) {
		return researchGroupDao.getResearchGroups(unitId);
	}
	
	@Override
	public List<ResearchGroupDto> getResearchGroups(String unitId) {
		researchGroupDtoList = new ArrayList<ResearchGroupDto>();
		mapGroup = new HashMap<String, List<ResearchGroupEx>>();
		researchGroupList = researchGroupDao.getResearchGroups(unitId);
		List<String> groupIds = new ArrayList<String>();
		for (ResearchGroup researchGroup : researchGroupList) {
			ResearchGroupDto researchGroupDto = new ResearchGroupDto();
			groupIds.add(researchGroup.getId());
			researchGroupDto.setId(researchGroup.getId());
			researchGroupDto.setUnitId(researchGroup.getSchoolId());
			researchGroupDto.setSubjectIds(getSubjectNames(researchGroup.getSubjectId()));
			researchGroupDto.setTeachGroupName(researchGroup.getTeachGroupName());
			researchGroupDtoList.add(researchGroupDto);
		}
		for (String groupid : groupIds) {
			researchGroupExList = researchGroupExDao.getResearchGroupExs(groupid);
			mapGroup.put(groupid, researchGroupExList);
		}
		String str1 = "";
		String str2 = "";
		for (ResearchGroupDto dto : researchGroupDtoList) {
			List<ResearchGroupEx> exlist = mapGroup.get(dto.getId());
			for (ResearchGroupEx ex : exlist) {
				if (1 == ex.getType()) {
					str1 = str1 + baseTeacherService.getTeacher(ex.getTeacherId()).getName() + ",";
				}
				if (0 == ex.getType()) {
					str2 = str2 + baseTeacherService.getTeacher(ex.getTeacherId()).getName() + ",";
				}
			}
			str1 = str1.substring(0,str1.length()-1);
			dto.setPrincipalTeacherName(str1);
			str2 = str2.substring(0,str2.length()-1);
			dto.setMemberTeacherName(str2);
			str1 = "";
			str2 = "";
		}
		
		return researchGroupDtoList;
	}

	@Override
	public void save(ResearchGroupDto researchGroupDto) {
		
		String[] principalIDs = researchGroupDto.getPrincipalTeacherID().split(",");
		String[] memberIDs = researchGroupDto.getMemberTeacherID().split(",");
		
		for (int i = 0; i < principalIDs.length; i++) {
			for (int j = 0; j < memberIDs.length; j++) {
				if (principalIDs[i].equals(memberIDs[j])) {
					throw new RuntimeException("负责人和成员选择重复!");
				}
			}
		}
		
		String id = uuid.generateHex();
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setId(id);
		researchGroup.setSchoolId(researchGroupDto.getUnitId());
		researchGroup.setTeachGroupName(researchGroupDto.getTeachGroupName());
		researchGroup.setSubjectId(researchGroupDto.getSubjectIds());
		researchGroup.setCreationTime(new Date());
		researchGroup.setModifyTime(new Date());
		researchGroup.setIsdeleted(0);
		
		
		researchGroupDao.save(researchGroup);
		
		for (int i = 0; i < principalIDs.length; i++) {
			ResearchGroupEx researchGroupEx = new ResearchGroupEx();
			researchGroupEx.setId(uuid.generateHex());
			researchGroupEx.setTeachGroupId(id);
			researchGroupEx.setType(1);
			researchGroupEx.setTeacherId(principalIDs[i]);
			researchGroupExDao.save(researchGroupEx);
		}
		
		for (int i = 0; i < memberIDs.length; i++) {
			ResearchGroupEx researchGroupEx = new ResearchGroupEx();
			researchGroupEx.setId(uuid.generateHex());
			researchGroupEx.setTeachGroupId(id);
			researchGroupEx.setType(0);
			researchGroupEx.setTeacherId(memberIDs[i]);
			researchGroupExDao.save(researchGroupEx);
		}
	}
	
	@Override
	public void saveOne(String researchGroupId, String id, int i) {
		ResearchGroupEx researchGroupEx = new ResearchGroupEx();
		researchGroupEx.setId(uuid.generateHex());
		researchGroupEx.setTeachGroupId(researchGroupId);
		researchGroupEx.setType(i);
		researchGroupEx.setTeacherId(id);
		researchGroupExDao.save(researchGroupEx);
	}
	
	@Override
	public void update(ResearchGroupDto researchGroupDto) {
		
		String[] principalIDs = researchGroupDto.getPrincipalTeacherID().split(",");
		String[] memberIDs = researchGroupDto.getMemberTeacherID().split(",");
		
		for (int i = 0; i < principalIDs.length; i++) {
			for (int j = 0; j < memberIDs.length; j++) {
				if (principalIDs[i].equals(memberIDs[j])) {
					throw new RuntimeException("负责人和成员选择重复!");
				}
			}
		}
		
		ResearchGroup researchGroup = new ResearchGroup();
		researchGroup.setId(researchGroupDto.getId());
		researchGroup.setSchoolId(researchGroupDto.getUnitId());
		researchGroup.setTeachGroupName(researchGroupDto.getTeachGroupName());
		researchGroup.setSubjectId(researchGroupDto.getSubjectIds());
		researchGroup.setModifyTime(new Date());
		researchGroupDao.update(researchGroup);
		
		researchGroupExDao.delete(researchGroupDto.getId());
		
		for (int i = 0; i < principalIDs.length; i++) {
			ResearchGroupEx researchGroupEx = new ResearchGroupEx();
			researchGroupEx.setId(uuid.generateHex());
			researchGroupEx.setTeachGroupId(researchGroupDto.getId());
			researchGroupEx.setType(1);
			researchGroupEx.setTeacherId(principalIDs[i]);
			researchGroupExDao.save(researchGroupEx);
		}
		
		for (int i = 0; i < memberIDs.length; i++) {
			ResearchGroupEx researchGroupEx = new ResearchGroupEx();
			researchGroupEx.setId(uuid.generateHex());
			researchGroupEx.setTeachGroupId(researchGroupDto.getId());
			researchGroupEx.setType(0);
			researchGroupEx.setTeacherId(memberIDs[i]);
			researchGroupExDao.save(researchGroupEx);
		}
		
	}

	@Override
	public void deleteByid(String id) {
		researchGroupExDao.deleteByid(id);
	}
	
	@Override
	public List<ResearchGroup> getTeachGroupNames(String unitId) {
		return researchGroupDao.getTeachGroupNames(unitId);
	}

	@Override
	public ResearchGroup getResearchGroup(String id) {
		return researchGroupDao.getResearchGroup(id);
	}
	
	@Override
	public List<ResearchGroupEx> getResearchGroupExs(String id) {
		return researchGroupExDao.getResearchGroupExs(id);
	}
	
	@Override
	public void delete(String[] ids) {
		researchGroupDao.delete(ids);
	}
	
	@Override
	public List<ResearchGroupEx> getresearchGroupExList(String id) {
		return researchGroupExDao.getresearchGroupExList(id);
	}
	
	@Override
	public List<ResearchGroup> getResearchGroupByIds(String[] researchGroupIds) {
		return researchGroupDao.getResearchGroupByIds(researchGroupIds);
	}
	
	public ResearchGroupDao getResearchGroupDao() {
		return researchGroupDao;
	}

	public void setResearchGroupDao(ResearchGroupDao researchGroupDao) {
		this.researchGroupDao = researchGroupDao;
	}

	public ResearchGroupExDao getResearchGroupExDao() {
		return researchGroupExDao;
	}

	public void setResearchGroupExDao(ResearchGroupExDao researchGroupExDao) {
		this.researchGroupExDao = researchGroupExDao;
	}

	public BaseTeacherService getBaseTeacherService() {
		return baseTeacherService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	@Override
	public List<ResearchGroup> getUseResearchGroups(String unitid) {
		return researchGroupDao.getResearchGroups(unitid);
	}

}
