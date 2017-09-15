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
import net.zdsoft.eis.base.common.entity.Student;
import net.zdsoft.eis.base.common.service.StudentService;

public class BusinessStudentServiceImpl implements BusinessOwnerService {
	private StudentService studentService;


	public static final List<String> fields = new ArrayList<String>();
	static { 
		fields.add("学籍号");
		fields.add("姓名");
	}

	public String getMacroPage(){
		return "/base/auditflow/studentOwner.ftl";
	}
	
	@Override
	public List<String> getFieldHeads() {
		return fields;
	}

	@Override
	public BusinessOwner getOwner(String ownerId) {
		Student student = studentService.getStudent(ownerId);
		BusinessOwner owner = new BusinessOwner(student.getId(), student.getUnitivecode(),
				student.getStuname());
		return owner;
	}

	@Override
	public Map<String, BusinessOwner> getOwnerMap(String[] ownerIds) {
		Map<String, BusinessOwner> ownerMap = new HashMap<String, BusinessOwner>();
		
		List<Student> students = studentService.getStudentsByIdsWithDeleted(ownerIds);
		for (Student student : students) {
			BusinessOwner owner = new BusinessOwner(student.getId(), student.getUnitivecode(),
					student.getStuname());
			ownerMap.put(owner.getId(), owner);
		}
		return ownerMap;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

}
