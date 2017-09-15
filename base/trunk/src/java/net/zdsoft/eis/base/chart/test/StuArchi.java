/* 
 * @(#)StuArchi.java    Created on 2013-1-10
 * Copyright (c) 2013 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.chart.test;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: 2013-1-10 上午10:06:28 $
 */
public class StuArchi {
	private String stuCode;
	private String stuName;
	private int subjectCode;
	private String subjectName;
	private int score;

	public StuArchi(String stuCode, String stuName, int subjectCode, String subjectName, int score) {
		super();
		this.stuCode = stuCode;
		this.stuName = stuName;
		this.subjectCode = subjectCode;
		this.subjectName = subjectName;
		this.score = score;
	}

	public String getStuName() {
		return stuName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public int getScore() {
		return score;
	}

	public String getStuCode() {
		return stuCode;
	}

	public int getSubjectCode() {
		return subjectCode;
	}

}
