/* 
 * @(#)BusinessTeacherServiceImpl.java    Created on 2012-11-26
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.auditflow.manager.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.auditflow.manager.entity.BusinessOwner;
import net.zdsoft.eis.base.auditflow.manager.service.BusinessOwnerService;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.TeacherService;

public class BusinessTeacherServiceImpl implements BusinessOwnerService {
	private TeacherService teacherService;

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public static final List<String> fields = new ArrayList<String>();
	static {
		fields.add("职工编号");
		fields.add("职工姓名");
	}

	public String getMacroPage(){
		return "/base/auditflow/teacherOwner.ftl";
	}
	
	@Override
	public List<String> getFieldHeads() {
		return fields;
	}

	@Override
	public BusinessOwner getOwner(String ownerId) {
		Teacher teacher = teacherService.getTeacherContainDelete(ownerId);
		BusinessOwner owner = new BusinessOwner(teacher.getId(), teacher.getTchId(),
				teacher.getName());
		return owner;
	}

	@Override
	public Map<String, BusinessOwner> getOwnerMap(String[] ownerIds) {
		Map<String, BusinessOwner> ownerMap = new HashMap<String, BusinessOwner>();
		List<Teacher> teachers = teacherService.getTeachersAll(ownerIds);
		for (Teacher teacher : teachers) {
			BusinessOwner owner = new BusinessOwner(teacher.getId(), teacher.getTchId(),
					teacher.getName());
			ownerMap.put(owner.getId(), owner);
		}
		return ownerMap;
	}

}
