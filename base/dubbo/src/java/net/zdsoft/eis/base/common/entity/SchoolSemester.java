package net.zdsoft.eis.base.common.entity;


import java.util.Date;

import net.zdsoft.eis.frame.client.BaseEntity;

public class SchoolSemester extends BaseEntity {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 3046186552057261370L;
    
    public static final String DEFAULT_SEMESTER="1";
    public static final String DEFAULT_ACADYEAR="";
    
    private String schid;       //学校id
    private String acadyear;    //学年
    private String semester;    //学期
    private Date workbegin;     //工作开始日期
    private Date workend;       //工作结束日期
    private Date semesterbegin; //学期开始日期
    private Date semesterend;   //学期结束日期
    private Date registerdate;  //注册日期
    private short edudays;      //周天数
    private short classhour;    //课长
    private short amperiods;    //上午课节数
    private short pmperiods;    //下午课节数
    private short nightperiods; //晚上课节数
    private short mornperiods;  //早上课节数
    private short week;//学期周数,base_semester表同步添加此字段，程序代码暂时没有修改

    private long updatestamp;
    //=====================辅助字段==========================
    private String name;  //学校名称   
    
    public String getId() {
        return id;
    }
    
    //默认构造方法
    public SchoolSemester(){
    }
    

    public String getSchid(){
    	return schid;
    }
    
	public void setSchid(String schid){
    	this.schid = schid;
    }
    
	public String getAcadyear() {
		return acadyear;
	}
	
	public void setAcadyear(String acadyear) {
		this.acadyear = acadyear;
	}
	
	public short getAmperiods() {
		return amperiods;
	}
	
	public void setAmperiods(short amperiods) {
		this.amperiods = amperiods;
	}
	
	public short getClasshour() {
		return classhour;
	}
	
	public void setClasshour(short classhour) {
		this.classhour = classhour;
	}
	
	public short getEdudays() {
		return edudays;
	}
	
	public void setEdudays(short edudays) {
		this.edudays = edudays;
	}
	
	public short getNightperiods() {
		return nightperiods;
	}
	
	public void setNightperiods(short nightperiods) {
		this.nightperiods = nightperiods;
	}
	
	public short getPmperiods() {
		return pmperiods;
	}
	
	public void setPmperiods(short pmperiods) {
		this.pmperiods = pmperiods;
	}
	
	public Date getRegisterdate() {
		return registerdate;
	}
	
	public void setRegisterdate(Date registerdate) {
		this.registerdate = registerdate;
	}
	
	public String getSemester() {
		return semester;
	}
	
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	public Date getSemesterbegin() {
		return semesterbegin;
	}
	
	public void setSemesterbegin(Date semesterbegin) {
		this.semesterbegin = semesterbegin;
	}
	
	public Date getSemesterend() {
		return semesterend;
	}
	
	public void setSemesterend(Date semesterend) {
		this.semesterend = semesterend;
	}
	
	public Date getWorkbegin() {
		return workbegin;
	}
	
	public void setWorkbegin(Date workbegin) {
		this.workbegin = workbegin;
	}
	
	public Date getWorkend() {
		return workend;
	}
	
	public void setWorkend(Date workend) {
		this.workend = workend;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



    public long getUpdatestamp() {
        return updatestamp;
    }


    public void setUpdatestamp(long updatestamp) {
        this.updatestamp = updatestamp;
    }

	public short getWeek() {
		return week;
	}

	public void setWeek(short week) {
		this.week = week;
	}

	public short getMornperiods() {
		return mornperiods;
	}

	public void setMornperiods(short mornperiods) {
		this.mornperiods = mornperiods;
	}
	
	
    
}



