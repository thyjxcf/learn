/* 
 * @(#)DutyAction.java    Created on 2007-8-14
 * Copyright (c) 2006 ZDSoft Networks, Inc. All rights reserved.
 * $Header$
 */
package net.zdsoft.eis.base.data.action;

import java.util.List;

import net.zdsoft.eis.base.data.entity.Duty;
import net.zdsoft.eis.base.data.service.DutyService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.leadin.util.UUIDGenerator;

public class DutyAction extends BaseAction {
	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	private DutyService dutyService;
	private List<Duty> listOfDutyDto;

	public Reply updateDuty(List<Duty> listOfDutyDto) {
		Reply reply = new Reply();
		try {
			for (Duty dto : listOfDutyDto) {
				dto.setUnitId(getLoginInfo().getUnitID());
				dto.setIsDeleted("0");
				dto.setType(1); // 1教师，2学生
			}
			dutyService.saveDuty(listOfDutyDto.toArray(new Duty[0]));
		} catch (Exception e) {
			reply.addActionError(e.getMessage());
		}
		reply.addActionMessage("教师职务信息保存成功!");
		return reply;
	}

	public String takeListOfDutyDto() {
		listOfDutyDto = dutyService.getDuties(getLoginInfo().getUnitID());
		return SUCCESS;
	}

	public String takeNewId() {
		return UUIDGenerator.getUUID();
	}

	public Reply deleteDuty(String... dutyIds) {
		Reply reply = new Reply();
		try {
			dutyService.deleteDuty(dutyIds);
		} catch (Exception e) {
			reply.addActionError(e.getMessage());
		}
		reply.addActionMessage("删除教师职务信息成功!");
		return reply;
	}

	public void setDutyService(DutyService dutyService) {
		this.dutyService = dutyService;
	}

	public List<Duty> getListOfDutyDto() {
		return listOfDutyDto;
	}

	public void setListOfDutyDto(List<Duty> listOfDutyDto) {
		this.listOfDutyDto = listOfDutyDto;
	}

}
