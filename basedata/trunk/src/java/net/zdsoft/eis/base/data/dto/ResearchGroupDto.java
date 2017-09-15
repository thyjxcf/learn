package net.zdsoft.eis.base.data.dto;

import java.util.List;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.eduaffairs.entity.Subject;

public class ResearchGroupDto {
	
	private String id;                    //id
	private String unitId;                //单位id
	private String teachGroupName;        //教研组名称
	private String subjectIds;             //科目ids
	private String subjectNames;           //科目名称
	private String principalTeacherID;    //负责人ids
	private String principalTeacherName;  //负责人名字
	private String memberTeacherID;       //成员ids
	private String memberTeacherName;     //成员名字
	private String[] arrayIds;            //选择ids
	private String idDto;                 //传id值
	private String beforeName;
	private String section;				  //学段
	private List<Mcodedetail> mcodeList;  //微代码  
	
	private List<Subject> subjectsList;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getTeachGroupName() {
		return teachGroupName;
	}
	public void setTeachGroupName(String teachGroupName) {
		this.teachGroupName = teachGroupName;
	}
	public String getPrincipalTeacherID() {
		return principalTeacherID;
	}
	public void setPrincipalTeacherID(String principalTeacherID) {
		this.principalTeacherID = principalTeacherID;
	}
	public String getPrincipalTeacherName() {
		return principalTeacherName;
	}
	public void setPrincipalTeacherName(String principalTeacherName) {
		this.principalTeacherName = principalTeacherName;
	}
	public String getMemberTeacherID() {
		return memberTeacherID;
	}
	public void setMemberTeacherID(String memberTeacherID) {
		this.memberTeacherID = memberTeacherID;
	}
	public String getMemberTeacherName() {
		return memberTeacherName;
	}
	public void setMemberTeacherName(String memberTeacherName) {
		this.memberTeacherName = memberTeacherName;
	}
	public String[] getArrayIds() {
		return arrayIds;
	}
	public void setArrayIds(String[] arrayIds) {
		this.arrayIds = arrayIds;
	}
	public String getIdDto() {
		return idDto;
	}
	public void setIdDto(String idDto) {
		this.idDto = idDto;
	}
	public String getBeforeName() {
		return beforeName;
	}
	public void setBeforeName(String beforeName) {
		this.beforeName = beforeName;
	}
	public String getSubjectIds() {
		return subjectIds;
	}
	public void setSubjectIds(String subjectIds) {
		this.subjectIds = subjectIds;
	}
	public String getSubjectNames() {
		return subjectNames;
	}
	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}
	public List<Subject> getSubjectsList() {
		return subjectsList;
	}
	public void setSubjectsList(List<Subject> subjectsList) {
		this.subjectsList = subjectsList;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	public List<Mcodedetail> getMcodeList() {
		return mcodeList;
	}
	public void setMcodeList(List<Mcodedetail> mcodeList) {
		this.mcodeList = mcodeList;
	}
	
}
