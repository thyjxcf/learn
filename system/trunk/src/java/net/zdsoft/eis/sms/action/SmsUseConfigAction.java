package net.zdsoft.eis.sms.action;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.sms.constant.SmsConstant;
import net.zdsoft.eis.sms.service.SmsClientService;
import net.zdsoft.eis.sms.service.BaseSmsUseConfigService;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.smsc.client.SmscException;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author jiangf
 * @version 创建时间：2011-8-5 下午04:19:44
 */

public class SmsUseConfigAction extends BaseAction {

	private static final long serialVersionUID = -4187417787177601963L;

	private BaseSmsUseConfigService baseSmsUseConfigService;
	
	private SmsClientService smsClientService;
	
	private SystemIniService systemIniService;
	
	private UnitService unitService;

	private String isSmsUsed="false";
	
	private String clientId;

	public String execute() {
		//判断是否是SDK模式还是数字校园模式
		String smsMode = systemIniService.getValue("SYSTEM.SMS.MODE");
		if (!"1".equals(smsMode)) {
			promptMessageDto.setErrorMessage("当前短信模式下本单位无需启用");
			return PROMPTMSG;
		}
		//判断是否是顶级单位统一订购短信还是各个单位分别订购模式
		String model = systemIniService.getValue(SmsConstant.SMS_USE_MODEL);
		if (SmsConstant.SMS_USE_MODEL_TOP.equals(model) && getLoginInfo().getUnitType() !=1){
			promptMessageDto.setErrorMessage("当前短信设置模式下本单位无需启用");
			return PROMPTMSG;
		}

		if(baseSmsUseConfigService.isSmsUsed(getUnitId()))
				isSmsUsed="true";
		clientId = baseSmsUseConfigService.getClientId(getUnitId());
		return SUCCESS;
	}
	
	public Reply saveUseConfig(int isSmsUsed,String clientId){
		Reply reply=new Reply();
		try{
			if(isSmsUsed == 1){
				if(StringUtils.isBlank(clientId)){
					String _clientId = baseSmsUseConfigService.getClientId(getUnitId());
					if(_clientId == null || _clientId.trim().length() == 0){
						reply.addFieldError("clientId","必须输入短信客户帐号");
						return reply;
					}
				}
				else{
					//如果是首次开启短信功能，尚未保存clientId的情况下，
					//前台传入的clientId必须到短信接入管理平台中去验证，保证开通了该帐户
					try{
						smsClientService.queryExtSpNumber(clientId);
					}catch(SmscException e){
						reply.addFieldError("clientId",e.getErrorMessage());
						return reply;
					}
					//前台传入的clientId必须验证一下其它单位是否已经使用，保证只使用自己单位的
					String _unitguid = baseSmsUseConfigService.isExistClientId(getUnitId(), clientId);
					if(_unitguid != null){
						log.debug("已经使用该短信帐号的单位unitguid=" + _unitguid);
						reply.addFieldError("clientId", unitService.getUnit(_unitguid).getName()
								+ "已经使用该帐号");
						return reply;
					}
				}
				baseSmsUseConfigService.saveUseConfig(getUnitId(), isSmsUsed, clientId);
			}
			else{
				baseSmsUseConfigService.deleteUseConfig(getUnitId());
			}
			reply.addActionMessage("恭喜，成功设置短信启用配置。");
			return reply;
		}
		catch(Exception e){
			reply.addActionError("对不起，操作出现异常。" + e.getMessage());
			return reply;
		}
	}

	public String getIsSmsUsed() {
		return isSmsUsed;
	}

	public void setIsSmsUsed(String isSmsUsed) {
		this.isSmsUsed = isSmsUsed;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setBaseSmsUseConfigService(
			BaseSmsUseConfigService baseSmsUseConfigService) {
		this.baseSmsUseConfigService = baseSmsUseConfigService;
	}

	public void setSmsClientService(SmsClientService smsClientService) {
		this.smsClientService = smsClientService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

}
