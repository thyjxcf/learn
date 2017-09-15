package net.zdsoft.office.desktop.entity;

import java.io.Serializable;
import java.util.Date;

public class Memo implements Serializable {

    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -4568519784019891794L;
    /**
     * 数据库主键ID，用于唯一标识一条记录
     */
    private String id;
    /**
     * 时间
     */
    private Date time;
    /**
     * 符合页面显示要求的时间格式字符串
     */
    private String timeString;
    /**
     * 备忘录内容
     */
    private String content;
    /**
     * 用户ID
     */
    private String userId;
    
    private String userIds;
    private String userIdss;
    private String userIdsss;
    private String userNames;
    private String userNamess;
    private String userNamesss;
    private String send;
    
    private String memoExId;
    /**
     * 是否短信提醒
     */
    private int isSmsAlarm;
    /**
     * 短信提醒时间
     */
    private Date smsAlarmTime;
    /**
     * 符合页面显示要求的短信提醒时间字符串
     */
    private String alarmTimeString;

    /**
     * 数据库主键ID，用于唯一标识一条记录
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 数据库主键ID，用于唯一标识一条记录
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 时间
     * 
     * @return
     */
    public Date getTime() {
        return time;
    }

    /**
     * 时间
     * 
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 备忘录内容
     * 
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * 备忘录内容
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 用户ID
     * 
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 用户ID
     * 
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 符合页面显示要求的时间格式字符串
     * 
     * @return
     */
    public String getTimeString() {
        return null == timeString ? "" : timeString;
    }

    /**
     * 符合页面显示要求的时间格式字符串
     * 
     * @param timeString
     */
    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    /**
     * 是否短信提醒
     * 
     * @return
     */
    public int getIsSmsAlarm() {
        return isSmsAlarm;
    }

    /**
     * 是否短信提醒
     * 
     * @param isSmsAlarm
     */
    public void setIsSmsAlarm(int isSmsAlarm) {
        this.isSmsAlarm = isSmsAlarm;
    }

    /**
     * 短信提醒时间
     * 
     * @return
     */
    public Date getSmsAlarmTime() {
        return smsAlarmTime;
    }

    /**
     * 短信提醒时间
     * 
     * @param smsAlarmTime
     */
    public void setSmsAlarmTime(Date smsAlarmTime) {
        this.smsAlarmTime = smsAlarmTime;
    }

    /**
     * 符合页面显示要求的短信提醒时间字符串
     * 
     * @return
     */
    public String getAlarmTimeString() {
        return alarmTimeString;
    }

    /**
     * 符合页面显示要求的短信提醒时间字符串
     * 
     * @param alarmTimeString
     */
    public void setAlarmTimeString(String alarmTimeString) {
        this.alarmTimeString = alarmTimeString;
    }

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getMemoExId() {
		return memoExId;
	}

	public void setMemoExId(String memoExId) {
		this.memoExId = memoExId;
	}

	public String getUserIdss() {
		return userIdss;
	}

	public void setUserIdss(String userIdss) {
		this.userIdss = userIdss;
	}

	public String getUserNamess() {
		return userNamess;
	}

	public void setUserNamess(String userNamess) {
		this.userNamess = userNamess;
	}

	public String getUserIdsss() {
		return userIdsss;
	}

	public void setUserIdsss(String userIdsss) {
		this.userIdsss = userIdsss;
	}

	public String getUserNamesss() {
		return userNamesss;
	}

	public void setUserNamesss(String userNamesss) {
		this.userNamesss = userNamesss;
	}

}
