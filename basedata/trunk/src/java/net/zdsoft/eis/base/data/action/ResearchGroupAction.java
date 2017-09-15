package net.zdsoft.eis.base.data.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.data.dto.ResearchGroupDto;
import net.zdsoft.eis.base.data.entity.BaseTeacher;
import net.zdsoft.eis.base.data.entity.ResearchGroup;
import net.zdsoft.eis.base.data.entity.ResearchGroupEx;
import net.zdsoft.eis.base.data.service.BaseTeacherService;
import net.zdsoft.eis.base.data.service.ResearchGroupService;
import net.zdsoft.eis.eduaffairs.entity.Subject;
import net.zdsoft.eis.eduaffairs.service.SubjectService;
import net.zdsoft.eis.frame.action.PageAction;
import net.zdsoft.eis.frame.client.LoginInfo;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ModelDriven;

public class ResearchGroupAction extends PageAction implements ModelDriven<ResearchGroupDto>{
	
	private ResearchGroupService researchGroupService; 
	private BaseTeacherService baseTeacherService;
	private SubjectService subjectService;
	private McodedetailService mcodedetailService; 
	private SchoolService schoolService;
	
	private List<ResearchGroup> researchGroupList;
	private List<ResearchGroupEx> researchGroupExList;
	private List<ResearchGroupDto> researchGroupDtoList;
	private List<Subject> subjectsList;
	private List<String[]> sectionList;
	private List<String> sections;
	
	private ResearchGroupDto researchGroupDto = new ResearchGroupDto();
	private ResearchGroup researchGroup;
	private String researchGroupId;
	private LoginInfo loginInfo;
	private String unitId;
	private String principalTeacherID = "";    //负责人ids
	private String principalTeacherName = "";  //负责人名字
	private String memberTeacherID = "";       //成员ids
	private String memberTeacherName = "";     //成员名字
	
	public String getSubjectNames(String subjectIds) {
		String[] subjectId = subjectIds.split(",");
		subjectsList = subjectService.findListOfSubjectIn(subjectId);
		String str = "";
		for (Subject subject : subjectsList) {
			str = str + subject.getSubjectName() + ",";
		} 
		str = str.substring(0, str.length()-1);
		return str;
	}
	
	public String getResearchGroupAdmin() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId)) {
			unitId = loginInfo.getUser().getUnitid();
		}
		researchGroupDtoList = researchGroupService.getResearchGroups(unitId);
		return SUCCESS;
	}
	

	//新增
	public String add() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId)) {
			unitId = loginInfo.getUser().getUnitid();
		}
		subjectsList = subjectService.findListOfAllEduAndSchSubject(unitId, null);
		researchGroupDto.setSubjectsList(subjectsList);
		sectionList = schoolService.getSchoolSections(unitId);
		sections = new ArrayList<String>();
		for (String[] str : sectionList) {
			sections.add(str[1]);
		}
		Collections.sort(sections);
		if (!sections.isEmpty()) {
			researchGroupDto.setSection(sections.get(0));
		}
		List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-XD");
		Iterator it = mcodeList.iterator();
		while(it.hasNext()) {
			Mcodedetail mcodedetail = (Mcodedetail) it.next();
			if (!sections.contains(mcodedetail.getThisId())) {
				it.remove();
			}
		}
		researchGroupDto.setMcodeList(mcodeList);
		return SUCCESS;
	}
	
	//维护
	public String edit() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId)) {
			unitId = loginInfo.getUser().getUnitid();
		}
		subjectsList = subjectService.findListOfAllEduAndSchSubject(unitId, null);
		researchGroupDto.setSubjectsList(subjectsList);
		researchGroup = researchGroupService.getResearchGroup(researchGroupId);
		researchGroupExList = researchGroupService.getResearchGroupExs(researchGroupId);
		for (ResearchGroupEx researchGroupEx : researchGroupExList) {
			if (1 == researchGroupEx.getType()) {
				principalTeacherID = principalTeacherID+ researchGroupEx.getTeacherId() + ",";
			}
			if (0 == researchGroupEx.getType()) {
				memberTeacherID = memberTeacherID+ researchGroupEx.getTeacherId() + ",";
			}
		}
		principalTeacherID = principalTeacherID.substring(0,principalTeacherID.length() - 1);
		memberTeacherID = memberTeacherID.substring(0,memberTeacherID.length() - 1);
		String[] principalTeacherIDs = principalTeacherID.split(",");
		String[] memberTeacherIDs = memberTeacherID.split(",");
		List<BaseTeacher> principalList = baseTeacherService.getBaseTeachers(principalTeacherIDs);
		List<BaseTeacher> memberList = baseTeacherService.getBaseTeachers(memberTeacherIDs);
		principalTeacherID = "";
		memberTeacherID = "";
		for (BaseTeacher baseTeacher : principalList) {
			principalTeacherID = principalTeacherID + baseTeacher.getId() + ",";
			principalTeacherName = principalTeacherName + baseTeacher.getName() + ",";
		}
		for (BaseTeacher baseTeacher : memberList) {
			memberTeacherID = memberTeacherID + baseTeacher.getId() + ",";
			memberTeacherName = memberTeacherName + baseTeacher.getName() + ",";
		}
		principalTeacherID = principalTeacherID.substring(0,principalTeacherID.length()-1);
		memberTeacherID = memberTeacherID.substring(0, memberTeacherID.length()-1);
		principalTeacherName = principalTeacherName.substring(0,principalTeacherName.length() - 1);
		memberTeacherName = memberTeacherName.substring(0,memberTeacherName.length() - 1);
		researchGroupDto.setUnitId(researchGroup.getSchoolId());
		researchGroupDto.setTeachGroupName(researchGroup.getTeachGroupName());
		researchGroupDto.setSubjectIds(researchGroup.getSubjectId());
		researchGroupDto.setSubjectNames(getSubjectNames(researchGroup.getSubjectId()));
		researchGroupDto.setPrincipalTeacherID(principalTeacherID);
		researchGroupDto.setPrincipalTeacherName(principalTeacherName);
		researchGroupDto.setMemberTeacherID(memberTeacherID);
		researchGroupDto.setMemberTeacherName(memberTeacherName);
		researchGroupDto.setBeforeName(researchGroup.getTeachGroupName());
		String[] subjectId = researchGroup.getSubjectId().split(",");
		Subject subject = subjectService.findSubjectById(subjectId[0]);
		researchGroupDto.setSection(subject.getSection());
		sectionList = schoolService.getSchoolSections(unitId);
		sections = new ArrayList<String>();
		for (String[] str : sectionList) {
			sections.add(str[1]);
		}
		List<Mcodedetail> mcodeList = mcodedetailService.getMcodeDetails("DM-XD");
		Iterator it = mcodeList.iterator();
		while(it.hasNext()) {
			Mcodedetail mcodedetail = (Mcodedetail) it.next();
			if (!sections.contains(mcodedetail.getThisId())) {
				it.remove();
			}
		}
		researchGroupDto.setMcodeList(mcodeList);
		return SUCCESS;
	}
	
	//修改
	public String update() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId)) {
			unitId = loginInfo.getUser().getUnitid();
		}
		researchGroupDto.setUnitId(unitId);
		//判断教研组名称不能重复
		researchGroupList = researchGroupService.getTeachGroupNames(unitId);
		for (ResearchGroup group : researchGroupList) {
			if (StringUtils.isNotBlank(researchGroupDto.getBeforeName()) && researchGroupDto.getTeachGroupName().equals(researchGroupDto.getBeforeName())) {
				continue;
			}
			if (researchGroupDto.getTeachGroupName().equals(group.getTeachGroupName())) {
				promptMessageDto.setPromptMessage("教研组名称已存在！");
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
		}
		
		researchGroupDto.setId(researchGroupId);
		try {
			researchGroupService.update(researchGroupDto);
		} catch (Exception e) {
			promptMessageDto.setPromptMessage("修改错误：" + e.getMessage());
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}
		
		promptMessageDto.setPromptMessage("修改成功！");
		promptMessageDto.setOperateSuccess(true);
		return SUCCESS;
	}
	
	//保存
	public String save() {
		loginInfo = getLoginInfo();
		if (StringUtils.isBlank(unitId)) {
			unitId = loginInfo.getUser().getUnitid();
		}
		researchGroupDto.setUnitId(unitId);
		//判断教研组名称不能重复
		researchGroupList = researchGroupService.getTeachGroupNames(unitId);
		for (ResearchGroup group : researchGroupList) {
			if (researchGroupDto.getTeachGroupName().equals(group.getTeachGroupName())) {
				promptMessageDto.setPromptMessage("教研组名称已存在！");
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
		}
			try {
				researchGroupService.save(researchGroupDto);
			} catch (Exception e) {
				promptMessageDto.setPromptMessage("保存错误：" + e.getMessage());
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
			
			promptMessageDto.setPromptMessage("保存成功！");
			promptMessageDto.setOperateSuccess(true);
			return SUCCESS;
	}
	
	public String delete() {
		try {
			String[] ids = researchGroupDto.getArrayIds();
			researchGroupService.delete(ids);
			promptMessageDto.setPromptMessage("操作成功！");
			promptMessageDto.setOperateSuccess(true);
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			promptMessageDto.setErrorMessage("删除失败!");
			promptMessageDto.setOperateSuccess(false);
			return SUCCESS;
		}
	}
	
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public ResearchGroupDto getResearchGroupDto() {
		return researchGroupDto;
	}

	public void setResearchGroupDto(ResearchGroupDto researchGroupDto) {
		this.researchGroupDto = researchGroupDto;
	}

	@Override
	public ResearchGroupDto getModel() {
		return researchGroupDto;
	}

	public ResearchGroupService getResearchGroupService() {
		return researchGroupService;
	}

	public void setResearchGroupService(ResearchGroupService researchGroupService) {
		this.researchGroupService = researchGroupService;
	}

	public List<ResearchGroup> getResearchGroupList() {
		return researchGroupList;
	}

	public void setResearchGroupList(List<ResearchGroup> researchGroupList) {
		this.researchGroupList = researchGroupList;
	}

	public List<ResearchGroupDto> getResearchGroupDtoList() {
		return researchGroupDtoList;
	}

	public void setResearchGroupDtoList(List<ResearchGroupDto> researchGroupDtoList) {
		this.researchGroupDtoList = researchGroupDtoList;
	}
	
	public BaseTeacherService getBaseTeacherService() {
		return baseTeacherService;
	}

	public void setBaseTeacherService(BaseTeacherService baseTeacherService) {
		this.baseTeacherService = baseTeacherService;
	}

	public String getResearchGroupId() {
		return researchGroupId;
	}

	public void setResearchGroupId(String researchGroupId) {
		this.researchGroupId = researchGroupId;
	}

	public SubjectService getSubjectService() {
		return subjectService;
	}

	public void setSubjectService(SubjectService subjectService) {
		this.subjectService = subjectService;
	}

	public McodedetailService getMcodedetailService() {
		return mcodedetailService;
	}

	public void setMcodedetailService(McodedetailService mcodedetailService) {
		this.mcodedetailService = mcodedetailService;
	}

	public SchoolService getSchoolService() {
		return schoolService;
	}

	public void setSchoolService(SchoolService schoolService) {
		this.schoolService = schoolService;
	}

	public List<String> getSections() {
		return sections;
	}

	public void setSections(List<String> sections) {
		this.sections = sections;
	}
	
}

