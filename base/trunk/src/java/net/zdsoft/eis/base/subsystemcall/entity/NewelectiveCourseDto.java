package net.zdsoft.eis.base.subsystemcall.entity;

import net.zdsoft.eis.base.common.entity.Timetable;

public class NewelectiveCourseDto {
	
	private Timetable tent;
	//辅助老师
	private String teacherIds;//多个
	
	private String queryUnitId;
	private String queryAcadyear;
	private String querySemester;
	
	private String queryCourseId;
	//上课时间
	private String queryTimeWeek;//星期
	private String queryTimePeriod;//节次
	private String queryTimeInterval;//时段
	
	//判断区间
	private Integer queryStartWorkTime;//开始周次
	private Integer queryWeek;//开始星期
	
	private Integer queryEndWorkTime;//结束周次

	//老师
	private String queryTeacherId;//主教师
	//场地
	private String queryPlaceId;
	//queryWeek 可以不填，可以配合 wwttype 0：全删，1：删除当前周  -1删当前周及以后数据
	private int wwttype;

	public Timetable getTent() {
		return tent;
	}

	public void setTent(Timetable tent) {
		this.tent = tent;
	}

	public String getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(String teacherIds) {
		this.teacherIds = teacherIds;
	}

	public String getQueryUnitId() {
		return queryUnitId;
	}

	public void setQueryUnitId(String queryUnitId) {
		this.queryUnitId = queryUnitId;
	}

	public String getQueryAcadyear() {
		return queryAcadyear;
	}

	public void setQueryAcadyear(String queryAcadyear) {
		this.queryAcadyear = queryAcadyear;
	}

	public String getQuerySemester() {
		return querySemester;
	}

	public void setQuerySemester(String querySemester) {
		this.querySemester = querySemester;
	}

	public String getQueryCourseId() {
		return queryCourseId;
	}

	public void setQueryCourseId(String queryCourseId) {
		this.queryCourseId = queryCourseId;
	}

	public String getQueryTimeWeek() {
		return queryTimeWeek;
	}

	public void setQueryTimeWeek(String queryTimeWeek) {
		this.queryTimeWeek = queryTimeWeek;
	}

	public String getQueryTimePeriod() {
		return queryTimePeriod;
	}

	public void setQueryTimePeriod(String queryTimePeriod) {
		this.queryTimePeriod = queryTimePeriod;
	}

	public String getQueryTimeInterval() {
		return queryTimeInterval;
	}

	public void setQueryTimeInterval(String queryTimeInterval) {
		this.queryTimeInterval = queryTimeInterval;
	}

	public Integer getQueryStartWorkTime() {
		return queryStartWorkTime;
	}

	public void setQueryStartWorkTime(Integer queryStartWorkTime) {
		this.queryStartWorkTime = queryStartWorkTime;
	}

	public Integer getQueryWeek() {
		return queryWeek;
	}

	public void setQueryWeek(Integer queryWeek) {
		this.queryWeek = queryWeek;
	}

	public Integer getQueryEndWorkTime() {
		return queryEndWorkTime;
	}

	public void setQueryEndWorkTime(Integer queryEndWorkTime) {
		this.queryEndWorkTime = queryEndWorkTime;
	}

	public String getQueryTeacherId() {
		return queryTeacherId;
	}

	public void setQueryTeacherId(String queryTeacherId) {
		this.queryTeacherId = queryTeacherId;
	}

	public String getQueryPlaceId() {
		return queryPlaceId;
	}

	public void setQueryPlaceId(String queryPlaceId) {
		this.queryPlaceId = queryPlaceId;
	}

	public int getWwttype() {
		return wwttype;
	}

	public void setWwttype(int wwttype) {
		this.wwttype = wwttype;
	}
	
	
}
