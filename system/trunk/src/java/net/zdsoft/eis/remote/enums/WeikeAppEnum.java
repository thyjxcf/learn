package net.zdsoft.eis.remote.enums;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.eis.base.constant.WeikeAppConstant;

/**
 * 微课工作  模块
 * @author Administrator
 *
 */
public enum WeikeAppEnum {
	
	/*
	 * 配置微课工作界面不获取权限的应用配置 如：待我审批，我发起的，邮件消息（其他应用配置通过角色授权控制，sys_model中parm=office_mobile）
	 * url--customReturn:0 系统返回，1 自定义返回(wkGoBack)
	 */
	//顶部
	AUDIT_DATA(WeikeAppConstant.AUDIT_DATA,"待我审批",WeikeAppEnum.APP_TYPE_TOP){
		public String getUrl(){
			return "/common/open/remote/weike/officeAuditData.action?customReturn=1&hideRightButton=1";
		}
		public String getIconPath(){
			return "/static/images/weike/officeAuditData.png";
		}
	},
	APPLY_DATA(WeikeAppConstant.APPLY_DATA,"我发起的",WeikeAppEnum.APP_TYPE_TOP){
		public String getUrl(){
			return "/common/open/remote/weike/officeApplyData.action?customReturn=1&hideRightButton=1";
		}
		public String getIconPath(){
			return "/static/images/weike/officeApplyData.png";
		}
	},
	MESSAGE(WeikeAppConstant.MESSAGE,"邮件消息",WeikeAppEnum.APP_TYPE_TOP){
		public String getUrl(){
			return "/common/open/remote/weike/officeMessage.action?customReturn=1&hideRightButton=1";
		}
		public String getIconPath(){
			return "/static/images/weike/officeMessage.png";
		}
	},
	
	//公文--发文
	OFFICEDOC_SEND(WeikeAppConstant.OFFICEDOC_SEND,"发文",WeikeAppEnum.APP_TYPE_OFFICEDOC){
		public String getUrl(){
			return "";
		}
		public String getIconPath(){
			return "/static/images/weike/officedocSend.png";
		}
	},
	//公文--收文
	OFFICEDOC_RECEIVE(WeikeAppConstant.OFFICEDOC_RECEIVE,"收文",WeikeAppEnum.APP_TYPE_OFFICEDOC){
		public String getUrl(){
			return "";
		}
		public String getIconPath(){
			return "/static/images/weike/officedocReceive.png";
		}
	},
	;
	
	private String code;
	private String name;
	private String type;//top--顶部应用，office--办公应用
	private static final String APP_TYPE_TOP = "top";//顶部应用
	private static final String APP_TYPE_OFFICEDOC = "officedoc";//办公应用
	private static final String APP_TYPE_OFFICE = "office";//办公应用
	
	private WeikeAppEnum(String code, String name, String type){
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public abstract String getUrl();
	public abstract String getIconPath();
	
	/**
	 * 获取工作顶部应用配置
	 * @return
	 */
	public static List<WeikeAppEnum> getTopApps(){
		List<WeikeAppEnum> list = new ArrayList<WeikeAppEnum>();
		for(WeikeAppEnum ent : WeikeAppEnum.values()){
			if(WeikeAppEnum.APP_TYPE_TOP.equals(ent.getType())){
				list.add(ent);
			}else{
				continue;
			}
		}
		return list;
	}
	
	/**
	 * 获取工作公文应用配置
	 * @return
	 */
	public static List<WeikeAppEnum> getOfficedocApps(){
		List<WeikeAppEnum> list = new ArrayList<WeikeAppEnum>();
		for(WeikeAppEnum ent : WeikeAppEnum.values()){
			if(WeikeAppEnum.APP_TYPE_OFFICEDOC.equals(ent.getType())){
				list.add(ent);
			}else{
				continue;
			}
		}
		return list;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
