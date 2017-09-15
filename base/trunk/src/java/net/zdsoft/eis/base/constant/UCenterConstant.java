package net.zdsoft.eis.base.constant;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * uc对接相关参数
 * 
 * @author weixh
 * @since 2016-12-2 上午11:14:13
 */
public class UCenterConstant {
	/**
	 * 是否连接uc
	 */
	public final static String SYSTEM_UCENTER_SWITCH = "SYSTEM.UCENTER.SWITCH";
	
	/**
	 * uctoken
	 */
	public final static String SYSTEM_UCENTER_UCTOKEN = "SYSTEM.UCENTER.UCTOKEN";
	
	/**
	 * uctoken
	 */
	public final static String SYSTEM_UCENTER_URL = "SYSTEM.UCENTER.URL";
	
	/**
	 * 密码修改用户角色
	 */
	public final static String SYSTEM_UCENTER_OWNERTYPE = "SYSTEM.UCENTER.OWNERTYPE";
	
	/**
	 * 修改密码接口
	 */
	public final static String CHANGE_PASSWORD_PATH = "/webapi/UCenter.asmx/ChangePassword";
	
	/**
	 * 获取用户当前accesstoken接口
	 */
	public final static String GET_ACCESSTOKEN_PATH = "/webapi/UCenter.asmx/GetAccessToken";
	
	/**
	 * 修改手机号
	 */
	public final static String UPDATE_TELNUM = "/webapi/UCenter.asmx/UpdateTelephone";
	
	public final static String ACCESSTOKEN_CACHE_NAME = "_access_token";
	
	/**
	 * 修改手机号成功标识
	 */
	public static final String UPDATETEL_SUCCESS_RESULT_CODE = "1";
	
	/**
	 * 操作成功标识
	 */
	public final static String RESULT_CODE_SUC = "0";
	
	public final static Map<String, String> errorResultMsgMap;
	
	static {
		errorResultMsgMap = new HashMap<String, String>();
		errorResultMsgMap.put("-1", "Token分解错误");  
		errorResultMsgMap.put("-2", "AccessToken分解错误");  
		errorResultMsgMap.put("-3", "用户已注销");  
		errorResultMsgMap.put("-4", "用户没有访问应用的权限");  
		errorResultMsgMap.put("-5", "用户已被停用");  
		errorResultMsgMap.put("-6", "应用未开启同步登录");  
		errorResultMsgMap.put("-7", "应用未开启非绑定用户登录,或用户需要绑定后才能登录");  
		errorResultMsgMap.put("-8", "登录超时,重新生成Token");  
		errorResultMsgMap.put("-9", "用户名为空");  
		errorResultMsgMap.put("-10", "未找到相应的用户名"); 
		errorResultMsgMap.put("-11", "未知错误"); 
		errorResultMsgMap.put("-12", "登录超时,跳转至登录界面"); 
		errorResultMsgMap.put("-13", "旧密码错误"); 
	}
	
	/**
	 * 获取错误结果说明
	 * @param code
	 * @return
	 */
	public static String getErrorResultMsg(String code){
		if(StringUtils.isEmpty(code) || !errorResultMsgMap.containsKey(code)){
			return errorResultMsgMap.get("-11");
		}
		return errorResultMsgMap.get(code);
	} 
}

