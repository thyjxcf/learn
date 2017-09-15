package net.zdsoft.office.basic.constant;

import java.util.ArrayList;
import java.util.List;

import net.zdsoft.office.basic.entity.OfficeAppParm;

/**
 * 
 * @author Administrator
 *
 */
public enum OfficeAppEnum {
//	OFFICE_TEACHER_LEAVE("1","请假管理",true,OfficeAppConstants.UNIT_AUTH_TYPE_0),
//	OFFICE_GO_OUT("2","外出管理",true,OfficeAppConstants.UNIT_AUTH_TYPE_0), 
//	OFFICE_EVECTION("3","出差管理",true,OfficeAppConstants.UNIT_AUTH_TYPE_0),
//	OFFICE_EXPENSE("4","报销管理",true,OfficeAppConstants.UNIT_AUTH_TYPE_0),
//	OFFICE_GOODS("5","物品管理",true,OfficeAppConstants.UNIT_AUTH_TYPE_0),
	
	ATTEND_LECTURE("6","听课管理",false,OfficeAppConstants.UNIT_AUTH_TYPE_2);
	
	private String type;//模块type
	private String remark;//模块名称
	private boolean isUsingDefault;//默认是否开启手机端
	private int unitAuthType;//模块权限类型  1--教育局  2--学校  0--通用
	
	private OfficeAppEnum(String type, String remark, boolean isUsingDefault, int unitAuthType){
		this.type = type;
		this.remark = remark;
		this.isUsingDefault = isUsingDefault;
		this.unitAuthType = unitAuthType;
	}
	
	/**
	 * 获取app模块list
	 * @return
	 */
	public static List<OfficeAppParm> getOfficeAppList(int unitClass){
		List<OfficeAppParm> list = new ArrayList<OfficeAppParm>();
		
		for(OfficeAppEnum ent : OfficeAppEnum.values()){
			if(ent.getUnitAuthType() == unitClass || ent.getUnitAuthType() == OfficeAppConstants.UNIT_AUTH_TYPE_0){
				OfficeAppParm obj = new OfficeAppParm();
				obj.setType(ent.getType());
				obj.setModuleName(ent.getRemark());
				obj.setIsUsing(ent.isUsingDefault());
				list.add(obj);
			}
		}
		
		return list;
	}
	
	/**
	 * 获取type对应的名称
	 * @param type
	 * @return
	 */
	public static String getRemarkByType(String type){
		String str = "";
		for(OfficeAppEnum ent : OfficeAppEnum.values()){
			if(ent.getType().equals(type)){
				str = ent.getRemark();
				break;
			}
		}
		return str;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isUsingDefault() {
		return isUsingDefault;
	}

	public void setUsingDefault(boolean isUsingDefault) {
		this.isUsingDefault = isUsingDefault;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getUnitAuthType() {
		return unitAuthType;
	}

	public void setUnitAuthType(int unitAuthType) {
		this.unitAuthType = unitAuthType;
	}
	
}
