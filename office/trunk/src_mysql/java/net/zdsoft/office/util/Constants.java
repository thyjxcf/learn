/* 
 * @(#)Constants.java    Created on 2009-3-2
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id: Constants.java 23538 2012-11-08 03:26:59Z hejh $
 */
package net.zdsoft.office.util;


/**
 * @author yangm
 * @version $Revision: 23538 $, $Date: 2012-11-08 11:26:59 +0800 (Thu, 08 Nov 2012) $
 */
public class Constants {
	
	public static final int MSG_STATE_DRAFT = 1; // 草稿箱
    public static final int MSG_STATE_SEND = 2; // 发件箱
    public static final int MSG_STATE_RECEIVE = 3; // 收件箱
    public static final int MSG_STATE_DEPT = 34; // 部门邮件
    public static final int MSG_STATE_UNIT = 35; // 单位邮件
    public static final int MSG_STATE_RECYCLE = 4; // 废件箱
    public static final int MSG_STATE_CUSTOMER = 5; // 自定义文件夹
    public static final int MSG_STATE_IMPORT = 6; // 重要邮件
    
 // 操作类型
    public static final String OPERATE_TYPE_SEND = "send";//发送
    public static final String OPERATE_TYPE_RESEND = "reSend";//重新发送
    public static final String OPERATE_TYPE_REPLY = "reply";//回复
    public static final String OPERATE_TYPE_REPLY_ALL = "replyAll";//回复全部
    public static final String OPERATE_TYPE_FORWARDING = "forwarding";//转发


    public static final String REPAIRE_STATE = "1";//报修管理

    public static final int APPLY_STATE_SAVE = 1; //保存
    public static final int APPLY_STATE_NEED_AUDIT = 2; //待审核
    public static final int APPLY_STATE_PASS = 3; //审核通过
    public static final int APPLY_STATE_NOPASS = 4; //审核不通过
    public static final int APPLY_STATE_CANCEL_NEED_AUDIT = 5;//撤销申请中
    public static final int APPLY_STATE_CANCEL_PASS = 6; //撤销通过
    public static final int APPLY_STATE_INVALID = 8; //作废
    
    public static final int LEAVE_APPLY_ALL = 0; //全部
    public static final int LEAVE_APPLY_SAVE = 1; //保存
    public static final int LEAVE_APPLY_FLOWING = 2;//审核中
    public static final int LEAVE_APPLY_FLOW_FINSH_PASS = 3;//审核结束-通过
    public static final int LEAVE_APPLY_FLOW_FINSH_NOT_PASS = 4;//审核结束-未通过
    public static final int LEAVE_APPLY_FLOW_FINSH = 9;//流程结束
	
	/**
	 * 教师请假附件类型
	 */
	public static final String OFFICE_TEACHER_LEAVE_ATT="OFFICE_TEACHER_LEAVE_ATT";
	
	public static final String SEND_TO_OTHER_UNIT = "SEND_TO_OTHER_UNIT";//发消息到其它单位权限
	
	public static final String MESSAGE_ATTACHMENT = "MESSAGE_ATTACHMENT";
	
	public static final Integer UNREAD = 0;//未读
	public static final Integer HASREAD = 1;//已读
	
	public static final Integer NOATTACHMENT = 0;//没有附件
	public static final Integer HASATTACHMENT = 1;//有附件
	
	public static final Integer NOSTAR = 0;//没有加星
	public static final Integer HASSTAR = 1;//加星
	
	public static final Integer MOVE = 0;//移动
	public static final Integer COPY = 1;//拷贝
	
	public static final Integer NEEDTODO = 1;//待办
	
	public static final Integer MSG_COMMON = 1;//信息级别 一般
	
	// 以下信息对应User的ownertype
    public static final Integer STUDENT = 1; // 发给学生
    public static final Integer TEACHER = 2; // 发给教师
    public static final Integer FAMILY = 3; // 发给家长
    public static final Integer DEPT = 4; // 发给部门
    public static final Integer UNIT = 5; // 发给单位
    
    //教室预约  机房和实训室预约，中午特殊表示
    public static final String NOON_NUMBER = "99";//代表节次
    public static final String NOON_NAME = "中午";//节次对应的名称
    
    public static final int STUDENT_LEAVE_TYPE=1;//学生请假类型state
    public static final int TEACHER_LEAVE_TYPE=2;//教师请假类型state
    
    public static final String BULLETIN_MANAGE_ = "bulletin_manage_";//通知管理
    public static final String BULLETIN_AUDIT_ = "bulletin_audit_";//通知审核
    
    public static final String BOARDROOM_AUDIT="boardRoom_audit";//会议室审核
    public static final String BOARDROOM_MANAGE="boardRoom_manage";//会议室管理
    public static final String BOARDROOM_APPLY="boardRoom_apply";//会议室管理
    
    public static final String BULLETIN_MANAGE_TL = "bulletin_manage_tl";//通知管理
    public static final String BULLETIN_TYPE_TL = "99";//桐庐定制通知type
    
    public static final String EXECUTIVE_MEETING_ADD = "EXECUTIVE_MEETING_ADD";//控制管理tab页，包括会议添加、议题审批、权限设置
    
    public static final Integer UNPUBLISH = 0;//未发布
    public static final Integer HASPUBLISH = 1;//已发布
    
    public static final Integer HOST_DEPT = 1;//主办科室
    public static final Integer ATTEND_DEPT = 2;//列席科室
    public static final Integer ATTEND_LEADER = 3;//提报领导
    public static final Integer OPINION_DEPT = 4;//意见征集科室
    
    public static final String OFFICE_EXECUTIVE_ISSUE = "OFFICE_EXECUTIVE_ISSUE";//局务会议附件objectType
    
    public static final String OFFICE_ROOM_ORDER_USER_TYPE_SECTION = "1";
    public static final String OFFICE_ROOM_ORDER_USER_TYPE_TIME = "2";
    
    //套红模板，1系统，2单位，3部门，4个人
    public static final String TEMP_COMMENT_SYSTEM = "1";
    public static final String TEMP_COMMENT_UNIT = "2";
    public static final String TEMP_COMMENT_DEPT = "3";
    public static final String TEMP_COMMENT_USER = "4";
    
    public static final Integer SHOW_NUMBER_BULLETIN = 1;//普通通知公告桌面显示
    public static final Integer SHOW_NUMBER_BULLETIN_XJ = 2;//有套红模
 //出差附件类型
    public static final String OFFICE_BUSINESS_TRIP_ATT="OFFICE_BUSINESS_TRIP_ATT";
  //报修附件类型
    public static final String OFFICE_REPAIRE_AIT="OFFICE_REPAIRE_AIT";
    //工作汇报附件类型
    public static final String OFFICE_WORK_REPORT_AIT="OFFICE_WORK_REPORT_AIT";
    //资产管理附件类型
    public static final String OFFICE_ASSET_AIT="OFFICE_ASSET_AIT";
}
