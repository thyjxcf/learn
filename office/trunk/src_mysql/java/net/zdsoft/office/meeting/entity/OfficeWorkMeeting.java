package net.zdsoft.office.meeting.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.atlassian.mail.MailUtils.Attachment;
/**
 * office_work_meeting
 * @author 
 * 
 */
public class OfficeWorkMeeting implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String unitId;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private Date meetingDate;
	/**
	 * 
	 */
	private float days;
	/**
	 * 
	 */
	private String place;
	/**
	 * 
	 */
	private String otherPersons;
	/**
	 * 
	 */
	private int forecastNumber;
	/**
	 * 
	 */
	private String remark;
	/**
	 * 记录维护人
	 */
	private String minutesPeople;
	private String minutesPeopleStr;
	private String minutesId;
	/**
	 * 1：未提交，2：未审核，3：通过，4：未通过
	 */
	private int state;
	/**
	 * 0：未发布，1：已发布
	 */
	private boolean isPublish;
	
	public static final int STATE_UNSUBMIT = 1;
    public static final int STATE_UNAUDIT = 2;
    public static final int STATE_PASS = 3;
    public static final int STATE_UNPASS = 4;
	
    public static final String NOT_PUBLISH = "0";//未发布
    public static final String HAS_PUBLISH = "1";//已发布
    public static final String NOT_END = "1";//未结束
    public static final String HAS_END = "0";//已结束
    
    private int attendState;//当前登录人员的参会状态
    private String transferDetail;//转交具体路径或者具体原因
	/**
	 * 
	 */
	private boolean isDeleted;
	/**
	 * 微代码DM-MEETINGTYPE
	 */
	private String type;
	/**
	 * 
	 */
	private String auditIdea;
	/**
	 * 
	 */
	private String createUserId;
	
	public String getMinutesId() {
		return minutesId;
	}
	public void setMinutesId(String minutesId) {
		this.minutesId = minutesId;
	}
	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private String auditUserId;
	private String auditUser;
	/**
	 * 
	 */
	private Date auditTime;
	
	private String hostDept;//主办科室
	private String otherDept;//列席科室
	private String leader;//局领导
	
	private String hostDeptStr;
	private String otherDeptStr;
	private String leaderStr;
	
	
	
	
	//------
	private String deptName;//部门名称
	private String sueState;//1已结束，0未结束
	private String attendNames;//确认参会人员
	private String notAttendNames;//确认不参会人员
	private String notSureNames;//不确定参会人员
	private String noleaders;//未设置负责人部门
	
	private int attendNum;//确认参加人数
	private int notAttendNum;//确认不参加人数
	private int notSureNum;//不确认人数
	private int noLeaderNum;//未设置负责人人数
	private List<Attachment> attachments=new ArrayList<Attachment>();
	
	public String getHostDept() {
		return hostDept;
	}
	public void setHostDept(String hostDept) {
		this.hostDept = hostDept;
	}
	public String getOtherDept() {
		return otherDept;
	}
	public void setOtherDept(String otherDept) {
		this.otherDept = otherDept;
	}
	public String getLeader() {
		return leader;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public String getHostDeptStr() {
		return hostDeptStr;
	}
	public void setHostDeptStr(String hostDeptStr) {
		this.hostDeptStr = hostDeptStr;
	}
	public String getOtherDeptStr() {
		return otherDeptStr;
	}
	public void setOtherDeptStr(String otherDeptStr) {
		this.otherDeptStr = otherDeptStr;
	}
	public String getLeaderStr() {
		return leaderStr;
	}
	public void setLeaderStr(String leaderStr) {
		this.leaderStr = leaderStr;
	}

	
	/**
	 * 设置
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 * 获取
	 */
	public String getId(){
		return this.id;
	}
	/**
	 * 设置
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 * 获取
	 */
	public String getUnitId(){
		return this.unitId;
	}
	/**
	 * 设置
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 * 获取
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * 设置
	 */
	public void setMeetingDate(Date meetingDate){
		this.meetingDate = meetingDate;
	}
	/**
	 * 获取
	 */
	public Date getMeetingDate(){
		return this.meetingDate;
	}
	/**
	 * 设置
	 */
	public void setDays(float days){
		this.days = days;
	}
	/**
	 * 获取
	 */
	public float getDays(){
		return this.days;
	}
	/**
	 * 设置
	 */
	public void setPlace(String place){
		this.place = place;
	}
	/**
	 * 获取
	 */
	public String getPlace(){
		return this.place;
	}
	/**
	 * 设置
	 */
	public void setOtherPersons(String otherPersons){
		this.otherPersons = otherPersons;
	}
	/**
	 * 获取
	 */
	public String getOtherPersons(){
		return this.otherPersons;
	}
	/**
	 * 设置
	 */
	public void setForecastNumber(int forecastNumber){
		this.forecastNumber = forecastNumber;
	}
	/**
	 * 获取
	 */
	public int getForecastNumber(){
		return this.forecastNumber;
	}
	/**
	 * 设置
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 * 获取
	 */
	public String getRemark(){
		return this.remark;
	}
	/**
	 * 设置
	 */
	public void setMinutesPeople(String minutesPeople){
		this.minutesPeople = minutesPeople;
	}
	/**
	 * 获取
	 */
	public String getMinutesPeople(){
		return this.minutesPeople;
	}
	/**
	 * 设置1：未提交，2：未审核，3：通过，4：未通过
	 */
	public void setState(int state){
		this.state = state;
	}
	/**
	 * 获取1：未提交，2：未审核，3：通过，4：未通过
	 */
	public int getState(){
		return this.state;
	}
	/**
	 * 设置0：未发布，1：已发布
	 */
	public void setIsPublish(boolean isPublish){
		this.isPublish = isPublish;
	}
	/**
	 * 获取0：未发布，1：已发布
	 */
	public boolean getIsPublish(){
		return this.isPublish;
	}
	/**
	 * 设置
	 */
	public void setIsDeleted(boolean isDeleted){
		this.isDeleted = isDeleted;
	}
	/**
	 * 获取
	 */
	public boolean getIsDeleted(){
		return this.isDeleted;
	}
	/**
	 * 设置微代码DM-MEETINGTYPE
	 */
	public void setType(String type){
		this.type = type;
	}
	/**
	 * 获取微代码DM-MEETINGTYPE
	 */
	public String getType(){
		return this.type;
	}
	/**
	 * 设置
	 */
	public void setAuditIdea(String auditIdea){
		this.auditIdea = auditIdea;
	}
	/**
	 * 获取
	 */
	public String getAuditIdea(){
		return this.auditIdea;
	}
	/**
	 * 设置
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	
	public String getMinutesPeopleStr() {
		return minutesPeopleStr;
	}
	public void setMinutesPeopleStr(String minutesPeopleStr) {
		this.minutesPeopleStr = minutesPeopleStr;
	}
	/**
	 * 获取
	 */
	public String getCreateUserId(){
		return this.createUserId;
	}
	/**
	 * 设置
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 * 获取
	 */
	public Date getCreateTime(){
		return this.createTime;
	}
	/**
	 * 设置
	 */
	public void setAuditUserId(String auditUserId){
		this.auditUserId = auditUserId;
	}
	/**
	 * 获取
	 */
	public String getAuditUserId(){
		return this.auditUserId;
	}
	/**
	 * 设置
	 */
	public void setAuditTime(Date auditTime){
		this.auditTime = auditTime;
	}
	/**
	 * 获取
	 */
	public Date getAuditTime(){
		return this.auditTime;
	}

	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public int getAttendState() {
		return attendState;
	}
	public void setAttendState(int attendState) {
		this.attendState = attendState;
	}
	public String getTransferDetail() {
		return transferDetail;
	}
	public void setTransferDetail(String transferDetail) {
		this.transferDetail = transferDetail;
	}

	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	public String getSueState() {
		return sueState;
	}
	public void setSueState(String sueState) {
		this.sueState = sueState;
	}
	public String getAttendNames() {
		return attendNames;
	}
	public void setAttendNames(String attendNames) {
		this.attendNames = attendNames;
	}
	public String getNotAttendNames() {
		return notAttendNames;
	}
	public void setNotAttendNames(String notAttendNames) {
		this.notAttendNames = notAttendNames;
	}
	public String getNotSureNames() {
		return notSureNames;
	}
	public void setNotSureNames(String notSureNames) {
		this.notSureNames = notSureNames;
	}
	public String getNoleaders() {
		return noleaders;
	}
	public void setNoleaders(String noleaders) {
		this.noleaders = noleaders;
	}
	public int getAttendNum() {
		return attendNum;
	}
	public void setAttendNum(int attendNum) {
		this.attendNum = attendNum;
	}
	public int getNotAttendNum() {
		return notAttendNum;
	}
	public void setNotAttendNum(int notAttendNum) {
		this.notAttendNum = notAttendNum;
	}
	public int getNotSureNum() {
		return notSureNum;
	}
	public void setNotSureNum(int notSureNum) {
		this.notSureNum = notSureNum;
	}
	public int getNoLeaderNum() {
		return noLeaderNum;
	}
	public void setNoLeaderNum(int noLeaderNum) {
		this.noLeaderNum = noLeaderNum;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	
}