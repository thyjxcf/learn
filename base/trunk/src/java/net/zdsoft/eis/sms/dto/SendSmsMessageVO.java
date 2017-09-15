package net.zdsoft.eis.sms.dto;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.background2.message.eschool.AccountInfo;
import net.zdsoft.background2.message.eschool.PhoneInfo;

/* 
 * <p>ZDSoft电子政务系统V3.6</p>
 * @author lilj
 * @since 1.0
 * @version $Id: SendSmsMessageVO.java,v 1.2 2006/10/13 09:12:31 lilj Exp $
 */

public class SendSmsMessageVO {
   
	private int smsBusinessType;//短信业务类型
	private List<PhoneInfo> phoneList = new ArrayList<PhoneInfo>(); 
	private List<AccountInfo> accountList = new ArrayList<AccountInfo>();
	
    private String unitId;
    private boolean isTiming; //是否定时发送
    private String sendTime; //组装后的发送时间 
    private int feeType; //短信计费方式


    //短信息,500个字符
    private String msg ="";
    
    //发送人
    private String userName =  "";
    
    //短信分拆条数
    private String  itemsCount = "1";
	/**
	 * 单位短信计费类型 对应feeType
	 */
	public static final int FEE_TYPE_TRY = 0; //试用   
	public static final int FEE_TYPE_FREE = 1; //免费
	public static final int FEE_TYPE_PAY = 2; //收费

  

    public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
  
    /**
     * @return Returns the msg.
     */
    public String getMsg() {
        return msg;
    }
    /**
     * @param msg The msg to set.
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }
  


	public String getItemsCount() {
		return itemsCount;
	}

	public void setItemsCount(String itemsCount) {
		this.itemsCount = itemsCount;
	}

	public int getSmsBusinessType() {
		return smsBusinessType;
	}

	public void setSmsBusinessType(int smsBusinessType) {
		this.smsBusinessType = smsBusinessType;
	}
	
    public List<PhoneInfo> getMobiles(){

        return phoneList;
    }
    public void addMobile(String mobile,String smsid){
    	PhoneInfo phoneInfo = new PhoneInfo();
        phoneInfo.setPhone(mobile);
        phoneInfo.setRecordId(smsid);//唯一标识号，为短信发送表的主键
        phoneList.add(phoneInfo);
    } 
    
	public List<AccountInfo> getAccounts() {
		return accountList;
	}

	public void addAccount(String accountId,String smsid) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.setAccountId(accountId);//用户PASSPORT帐号
        accountInfo.setRecordId(smsid);//唯一标识号，为短信发送表的主键
        accountList.add(accountInfo);
	}

	public boolean isTiming() {
		return isTiming;
	}

	public void setTiming(boolean isTiming) {
		this.isTiming = isTiming;
	}



	public void setPhoneList(List<PhoneInfo> phoneList) {
		this.phoneList = phoneList;
	}

	public void setAccountList(List<AccountInfo> accountList) {
		this.accountList = accountList;
	}

	public int getFeeType() {
		return feeType;
	}

	public void setFeeType(int feeType) {
		this.feeType = feeType;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

}
