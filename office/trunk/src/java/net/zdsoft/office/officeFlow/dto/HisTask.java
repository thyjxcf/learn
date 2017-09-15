package net.zdsoft.office.officeFlow.dto;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.jbpm.core.entity.Comment;

public class HisTask {
	
	private String taskId;
	
	private String taskName;//步骤名称
	
	private String assigneeId;
	
	private List<String> candidateUsers = new ArrayList<String>();
	
	private Comment comment = new Comment();
	
	private String assigneeName="";//处理人名字,拼接字符串之间中“,”隔开
	
	private String unitId;//comment中处理人单位
	
	private String unitName;//comment中处理人单位名称
	
	private String step;// 对应的步骤 this_id
	
	private String taskDefinitionKey;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(String assigneeId) {
		this.assigneeId = assigneeId;
	}

	public List<String> getCandidateUsers() {
		return candidateUsers;
	}

	public void setCandidateUsers(List<String> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	
}
