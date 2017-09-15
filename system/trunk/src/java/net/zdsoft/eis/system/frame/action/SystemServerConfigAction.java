package net.zdsoft.eis.system.frame.action;

import java.util.HashMap;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.MailServerDto;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.MailServerService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eis.system.frame.dto.SmsServerDto;
import net.zdsoft.eis.system.frame.service.SystemServerService;

public class SystemServerConfigAction extends BaseAction {
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;

    private SystemServerService systemServerService;
    private MailServerService mailServerService;
    private SmsClientService smsClientService;

	private UnitService unitService;

	private MailServerDto mailServerDto;

	private SmsServerDto smsServerDto;
	private SystemIniService systemIniService;

	private String smsMode;//短信接入方式
	private boolean isTopEduAdmin;
	
	private String  password_default = BaseConstant.PASSWORD_VIEWABLE;
	private Map<Object,Object> returnJsonData = new HashMap<Object,Object>();

	@Override
	public String execute() throws Exception {
		Unit unit = unitService.getUnit(getLoginInfo().getUnitID());
		if (unit.getParentid().equals(Unit.TOP_UNIT_GUID)){
			isTopEduAdmin = true;
		}
		mailServerDto = mailServerService.getMailServerInfo();
		smsServerDto = systemServerService.getSmsConfig();
		
		smsMode = systemIniService.getValue("SYSTEM.SMS.MODE");		
		
		return SUCCESS;
	}

	/**
	 * ajax调用方法
	 * 
	 * @param dto
	 * @return
	 */
	public String saveMailConfig() {
		Object[] result = new Object[2];
		try {
			systemServerService.saveMailConfig(mailServerDto);
			result[0] = new Integer(1);
			result[1] = "邮件服务器配置保存成功";
		} catch (Exception e) {
			result[0] = new Integer(0);
			result[1] = "邮件服务器配置保存失败,原因说明：" + e.getMessage();
			returnJsonData.put("result", result);
			return SUCCESS;
		}
		returnJsonData.put("result", result);
		return SUCCESS;
	}

	/**
	 * ajax调用方法
	 * 
	 * @param dto
	 * @return
	 */
	public String saveSmsConfig() {
		Object[] result = new Object[2];
		
		try {
			if(smsClientService.isSmsUsed(getUnitId())){
				result[0] = new Integer(0);
				result[1] = "请先停用短信服务";
				returnJsonData.put("result", result);
				return SUCCESS;
			}
			systemServerService.saveSmsConfig(smsServerDto);
			result[0] = new Integer(1);
			result[1] = "通信服务器配置保存成功";
		} catch (RuntimeException e) {
			result[0] = new Integer(0);
			result[1] = "通信服务器配置保存失败,原因说明：" + e.getMessage();
			returnJsonData.put("result", result);
			return SUCCESS;
		}catch (Exception e) {
			result[0] = new Integer(0);
			result[1] = "通信服务器配置保存失败,原因说明：" + e.getMessage();
			returnJsonData.put("result", result);
			return SUCCESS;
		}
		returnJsonData.put("result", result);
		return SUCCESS;
	}

	public boolean getIsTopEduAdmin() {
		return isTopEduAdmin;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public MailServerDto getMailServerDto() {
		return mailServerDto;
	}

	public void setMailServerDto(MailServerDto mailServerDto) {
		this.mailServerDto = mailServerDto;
	}

	public SmsServerDto getSmsServerDto() {
		return smsServerDto;
	}
	
	public String getPassword_default() {
		return password_default;
	}

	public void setSystemServerService(SystemServerService systemServerService) {
		this.systemServerService = systemServerService;
	}

    public void setSystemIniService(SystemIniService systemIniService) {
        this.systemIniService = systemIniService;
    }

    public String getSmsMode() {
        return smsMode;
    }

    public void setMailServerService(MailServerService mailServerService) {
        this.mailServerService = mailServerService;
    }

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	public Map<Object, Object> getReturnJsonData() {
		return returnJsonData;
	}

	public void setReturnJsonData(Map<Object, Object> returnJsonData) {
		this.returnJsonData = returnJsonData;
	}

	public void setSmsServerDto(SmsServerDto smsServerDto) {
		this.smsServerDto = smsServerDto;
	}
	
}
