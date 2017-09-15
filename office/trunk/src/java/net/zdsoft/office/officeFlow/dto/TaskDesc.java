package net.zdsoft.office.officeFlow.dto;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.jbpm.core.entity.Comment;

import org.apache.commons.lang.StringUtils;

public class TaskDesc {
	private String processInstanceId;
	private String taskId;
	private String taskDefinitionKey;

	private String taskName = "";// 名称
	private String assigneeId;// 任务执行者：退回和撤回对应原来的执行者
	private String assigneeName;
	
	private List<String> candidateUsers = new ArrayList<String>();// 候选人：通过和跳转时对应真实的用户
	private List<String> proxyUsers = new ArrayList<String>();// 代理用户
	private List<String> delegateUsers = new ArrayList<String>();// 代办用户

	private String step;// 对应的步骤
	
	private String proxyUsersIds;//代理用户ids
	private String proxyUsersNames;//代理用户名字
	private String proxyDetailNames;//
	private String delegateUsersIds;//代办用户ids
	private String delegateUsersNames;//代办用户名字
	private String delegateDetailNames;//
	
	private String userIds;//用于保存
	
	//
	private Comment comment = new Comment();
	
	//add20160603
	private String assigneeDetailNames;//
	private String checkHalfUsers;
	
	/**
	 * 取负责人
	 * 
	 * @return
	 */
	public List<String> getPrincipals() {
		List<String> principals = new ArrayList<String>();
		if (StringUtils.isEmpty(assigneeId)) {
			principals.addAll(candidateUsers);
		} else {
			principals.add(assigneeId);
		}
		return principals;
	}
	/**
	 * 取所有参与人
	 * 
	 * @return
	 */
	public List<String> getParticipants() {
		List<String> participants = getPrincipals();
		participants.addAll(proxyUsers);
		participants.addAll(delegateUsers);
		return participants;
	}
	public String getProxyDetailNames() {
		return proxyDetailNames;
	}

	public void setProxyDetailNames(String proxyDetailNames) {
		this.proxyDetailNames = proxyDetailNames;
	}

	public String getDelegateDetailNames() {
		return delegateDetailNames;
	}

	public void setDelegateDetailNames(String delegateDetailNames) {
		this.delegateDetailNames = delegateDetailNames;
	}

	public String getProxyUsersIds() {
		return proxyUsersIds;
	}
	public void setProxyUsersIds(String proxyUsersIds) {
		this.proxyUsersIds = proxyUsersIds;
	}
	public String getProxyUsersNames() {
		return proxyUsersNames;
	}
	public void setProxyUsersNames(String proxyUsersNames) {
		this.proxyUsersNames = proxyUsersNames;
	}
	public String getDelegateUsersIds() {
		return delegateUsersIds;
	}
	public void setDelegateUsersIds(String delegateUsersIds) {
		this.delegateUsersIds = delegateUsersIds;
	}
	public String getDelegateUsersNames() {
		return delegateUsersNames;
	}
	public void setDelegateUsersNames(String delegateUsersNames) {
		this.delegateUsersNames = delegateUsersNames;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
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
	public String getAssigneeName() {
		return assigneeName;
	}
	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}
	public List<String> getCandidateUsers() {
		return candidateUsers;
	}
	public void setCandidateUsers(List<String> candidateUsers) {
		this.candidateUsers = candidateUsers;
	}
	public List<String> getProxyUsers() {
		return proxyUsers;
	}
	public void setProxyUsers(List<String> proxyUsers) {
		this.proxyUsers = proxyUsers;
	}
	public List<String> getDelegateUsers() {
		return delegateUsers;
	}
	public void setDelegateUsers(List<String> delegateUsers) {
		this.delegateUsers = delegateUsers;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public String getAssigneeDetailNames() {
		return assigneeDetailNames;
	}
	public void setAssigneeDetailNames(String assigneeDetailNames) {
		this.assigneeDetailNames = assigneeDetailNames;
	}
	public String getCheckHalfUsers() {
		return checkHalfUsers;
	}
	public void setCheckHalfUsers(String checkHalfUsers) {
		this.checkHalfUsers = checkHalfUsers;
	}
}
