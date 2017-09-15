package net.zdsoft.eis.system.data.action;

import java.util.Map;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.Teacher;
import net.zdsoft.eis.base.common.service.TeacherService;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.system.data.entity.SysUserBind;
import net.zdsoft.eis.system.data.service.SysUserBindService;
import net.zdsoft.smsplatform.client.ZDConstant;
import net.zdsoft.smsplatform.client.ZDUtils;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author weixh
 * @since 2017-1-12 下午8:44:36
 */
@SuppressWarnings("serial")
public class SysUserBindAction extends BaseAction {
	private SysUserBindService sysUserBindService;
	private TeacherService teacherService;
	
	private SysUserBind bind = new SysUserBind();
	
	public String execute(){
		bind = sysUserBindService.getSysUserBindByUserId(getLoginInfo().getUser().getId());
		if(bind == null){
			bind = new SysUserBind();
			bind.setUserId(getLoginInfo().getUser().getId());
			Teacher tea = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
			if(StringUtils.isBlank(tea.getIdcard())){
				promptMessageDto.setErrorMessage("还没有维护过身份证号，请先维护！");
				return PROMPTMSG;
			}
		}
		return SUCCESS;
	}

	public String unbind(){
		try {
			sysUserBindService.delete(new String[]{bind.getRemoteUserId()});
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("用户解绑成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("用户解绑失败！");
		}
		return SUCCESS;
	}
	
	public String save(){
		try {
			SysUserBind bi = sysUserBindService.getSysUserBindByUsername(bind.getRemoteUsername());
			if(bi != null 
					&& !StringUtils.equals(bi.getUserId(), bind.getUserId())){
				promptMessageDto.setOperateSuccess(false);
				promptMessageDto.setErrorMessage("该平台用户已经被绑定过了！");
				return SUCCESS;
			}
			if(PROMPTMSG.equals(checkRemoteUsername())){
				promptMessageDto.setOperateSuccess(false);
				return SUCCESS;
			}
			sysUserBindService.save(bind);
			promptMessageDto.setOperateSuccess(true);
			promptMessageDto.setPromptMessage("用户绑定成功！");
		} catch (Exception e){
			log.error(e.getMessage(), e);
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("用户绑定失败！");
		}
		return SUCCESS;
	}
	
	/**
	 * 获取用户信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private String checkRemoteUsername() throws Exception {
		String url = "http://www.ahedu.cn/jgkj/authorization-token/oauth/token?"
				+"client_id=anqing&client_secret=874895e5c922ded5&grant_type=client_credentials";
		String res = ZDUtils.readContent(url, ZDConstant.METHOD_GET, null, null, "UTF-8");
		System.out.println("uuctoken ===="+res);
		JSONObject json = JSONObject.fromObject(res);
		String uucToken = (String) json.get("access_token");
		if(StringUtils.isBlank(uucToken)){
			promptMessageDto.setErrorMessage("校验用户名失败：获取token失败！");
			return PROMPTMSG;
		}
		
		
		url = "http://www.ahedu.cn/SNS/index.php?"
				+"app=mobileapi&mod=Researchaq&act=getUserinfoByKey&key=login_name"
				+"&value="+bind.getRemoteUsername()+"&service=sns.aq.getUserInfo&appkey=anqing"
				+"&token="+uucToken;
		res = ZDUtils.readContent(url);
		System.out.println(url);
		System.out.println("userinfo ===="+res);
		json = JSONObject.fromObject(res);
		Integer sts = (Integer) json.get("status");
		if(200 != sts){
			promptMessageDto.setErrorMessage("校验用户名失败：获取用户信息失败！");
			return PROMPTMSG;
		}
		Map<String, String> bodyMap = (Map<String, String>) json.get("data");
		if(bodyMap == null){
			promptMessageDto.setErrorMessage("校验用户名失败：没有获取到用户信息！");
			return PROMPTMSG;
		}
		String remoteIdcard = bodyMap.get("idCardNo");
		Teacher tea = teacherService.getTeacher(getLoginInfo().getUser().getTeacherid());
		if(!StringUtils.equalsIgnoreCase(remoteIdcard, tea.getIdcard())){
			promptMessageDto.setErrorMessage("校验用户名失败：用户的身份证号与当前用户不一致！");
			return PROMPTMSG;
		}
		String pwd = "IFLYTEK_ENCODE_" + bodyMap.get("userPassword");
		promptMessageDto.addHiddenText(new String[]{"pwd", pwd});
		promptMessageDto.addHiddenText(new String[]{"uucToken", uucToken});
		return "";
	}

	public SysUserBind getBind() {
		return bind;
	}

	public void setBind(SysUserBind bind) {
		this.bind = bind;
	}

	public void setSysUserBindService(SysUserBindService sysUserBindService) {
		this.sysUserBindService = sysUserBindService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}
	
}
