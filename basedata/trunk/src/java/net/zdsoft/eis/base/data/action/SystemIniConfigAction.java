package net.zdsoft.eis.base.data.action;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.service.BaseSystemIniService;
import net.zdsoft.eis.base.util.SystemLog;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.leadin.exception.FormatException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

public class SystemIniConfigAction extends BaseAction {

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String modID = "SYS001";
	
	private static final String SYSTEM_REMOTESERVER_MAIN = "SYSTEM.REMOTESERVER-MAIN";// 数据报送主服务器地址
	private Integer systeminiValueMaxLength = BasedataConstants.SYSTEMINI_VALUE_MAXLENGTH;
	private UnitService unitService;
	private LoginInfo loginInfo;
	private SystemIni[] systemIniDtos;
	private BaseSystemIniService baseSystemIniService;

	private String iniid;
	private String name;
	private String nowValue;
	private String description;
	private String defaultValue;

	//是否后台管理页
	protected String background;
	
	
	//-------------------------------------------V6.0新增加代码
	private String jsonString;
	
	public String saveOne(){
		baseSystemIniService.saveMod(iniid, nowValue);
		return SUCCESS;
	}
	
	public String saveAll(){
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Set<String> keySet = jsonObject.keySet();
		List<SystemIni> inis = new ArrayList<SystemIni>();
		for(String key : keySet){
			SystemIni ini = new SystemIni();
			ini.setIniid(key);
			ini.setNowValue(jsonObject.getString(key));
			inis.add(ini);
		}
		try {
			baseSystemIniService.saveAllMod(inis.toArray(new SystemIni[0]));
		} catch (FormatException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	protected <T> T convert(T t, JSONObject jsonObject){
		Map<String, Object> map2 = new HashMap<String, Object>();
		Set<String> keySet = jsonObject.keySet();
		for(String key : keySet){
			String key2 = key;
			if(key2.indexOf(".") >= 0){
				key2 = key2.substring(key2.indexOf(".") + 1);
			}
			key2 = key2.toLowerCase();
			while(key2.indexOf("_") >= 0){
				key2 = key2.substring(0, key2.indexOf("_")) + key2.substring(key2.indexOf("_") + 1 , key2.indexOf("_") + 2).toUpperCase() + key2.substring(key2.indexOf("_") + 2, key2.length());
			}
			map2.put(key2, jsonObject.get(key));
			map2.put(key2.toLowerCase(), jsonObject.get(key));
		}
		 for(Class<?> clazz = t.getClass();  clazz != Object.class;  clazz = clazz.getSuperclass()){
			 for(Field field : clazz.getDeclaredFields()){
					String name = field.getName();
					try {
						Object o = map2.get(name);
						if(o != null){
							field.setAccessible(true);
							System.out.println("----" + name + ":" + map2.get(name) + ", " + field.getType());
							if(field.getType().equals(int.class)){
								field.set(t, NumberUtils.toInt((String)map2.get(name)));
							}
							else if(field.getType().equals(Integer.class)){
								field.set(t, NumberUtils.createInteger((String)map2.get(name)));
							}
							else if(field.getType().equals(long.class)){
								field.set(t, NumberUtils.toLong((String)map2.get(name)));
							}
							else if(field.getType().equals(Long.class)){
								field.set(t, NumberUtils.createLong((String)map2.get(name)));
							}
							else if(field.getType().equals(Float.class)){
								field.set(t, NumberUtils.createFloat((String)map2.get(name)));
							}
							else if(field.getType().equals(float.class)){
								field.set(t, NumberUtils.toFloat((String)map2.get(name)));
							}
							else{
								field.set(t, map2.get(name));
							}
						}
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						//设置不成功
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						//设置不成功
					}
				}
		 }
		
		return t;
	}

	@Override
	/**
	 * 通过getModel里的方法取得数组
	 * 默认取得前台访问的,如果isBackground有值，则取的后台管理维护的数据
	 */
	public String execute() throws Exception {
		int isVisible = 1;
		if (StringUtils.isNotBlank(background)) {
			isVisible = 0;
		}
		List<SystemIni> sysList = baseSystemIniService
				.getVisibleSystemIni(isVisible);
		if (CollectionUtils.isNotEmpty(sysList)) {
			systemIniDtos = sysList.toArray(new SystemIni[sysList.size()]);
		}
		return SUCCESS;
	}

	public String saveSystemIni() {
		//依赖于界面的传值顺序
		String[] iniids = iniid.split(",");
		String[] names = name.split(",");
		String[] nowValues = nowValue.split(",");
		String[] descriptions = description.split(",");
		String[] defaultValues = defaultValue.split(",");
		systemIniDtos = new SystemIni[iniids.length];
		for (int i = 0; i < iniids.length; i++) {
			systemIniDtos[i] = new SystemIni();
			systemIniDtos[i].setIniid(iniids[i]);
			systemIniDtos[i].setName(names[i]);
			systemIniDtos[i].setNowValue(nowValues[i].replace(";",","));
			systemIniDtos[i].setDescription(descriptions[i]);
			systemIniDtos[i].setDefaultValue(defaultValues[i]);
		}

		promptMessageDto = new PromptMessageDto();

		Unit unitDto = null;
		//如果是前台维护，则取得相应单位信息
		if (!isBackGround()) {
			loginInfo = this.getLoginInfo();//(LoginInfo) getSession(BaseConstant.SESSION_LOGININFO);
			unitDto = unitService.getUnit(loginInfo.getUnitID());
			if (unitDto == null) {
				addActionError("当前用户单位信息不存在，或已删除");
				return INPUT;
			}
		}

		for (int i = 0; i < systemIniDtos.length; i++) {
			if (!isBackGround()) {
				if (SYSTEM_REMOTESERVER_MAIN
						.equals(systemIniDtos[i].getIniid())
						&& Unit.TOP_UNIT_GUID.equals(unitDto.getParentid())) {
					// 顶级单位,数据报送主服务器地址可以为空;
					continue;
				}
				if (systemIniDtos[i].getNowValue() == null
						|| systemIniDtos[i].getNowValue().trim().length() == 0) {
					addFieldError("systemIniDtos[" + i + "].nowValue",
							systemIniDtos[i].getName() + "不能为空");
					return INPUT;
				}
			}
		}

		if (!isBackGround()
				&& !Unit.TOP_UNIT_GUID.equals(unitDto.getParentid())) {
			promptMessageDto.setErrorMessage("对不起平台参数设定只有顶级教育局才有权限修改");
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.addOperation(new String[] { "返回",
					"platformInfoAdmin-systemIniConfig.action" });
			return PROMPTMSG;
		}

		try {
			baseSystemIniService.saveAllMod(systemIniDtos);
			promptMessageDto.setPromptMessage("修改成功！");
			promptMessageDto.setOperateSuccess(true);
			SystemLog.log(modID, "修改平台参数设定成功!");
		} catch (FormatException e) {
			addFieldError(e.getField(), e.getMessage());
			return INPUT;
		} catch (Exception e) {
			log.error(e.toString());
			promptMessageDto.setOperateSuccess(false);
			promptMessageDto.setErrorMessage("修改失败！");
			SystemLog.log(modID, "修改平台参数设定失败!");
		}
		promptMessageDto.addOperation(new String[] { "确定",
				"platformInfoAdmin-systemIniConfig.action" });
		promptMessageDto
				.addHiddenText(new String[] { "background", background });
		return PROMPTMSG;

	}

	private boolean isBackGround() {
		if (StringUtils.isNotBlank(background)) {
			return true;
		} else {
			return false;
		}
	}

	public Integer getSysteminiValueMaxLength() {
		return systeminiValueMaxLength;
	}

	public void setBaseSystemIniService(
			BaseSystemIniService baseSystemIniService) {
		this.baseSystemIniService = baseSystemIniService;
	}

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	/**
	 * 判断是否顶级单位，如果是后台管理，则直接返回true
	 *
	 *@author "yangk"
	 * Sep 15, 2010 7:23:30 PM
	 * @return
	 */
	public boolean isTopUnit() {
		if (isBackGround()) {
			return true;
		} else {
			Unit unit = unitService.getTopEdu();
			if (unit != null) {
				if (getLoginInfo().getUnitID().equals(unit.getId())) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
	}

	public SystemIni[] getSystemIniDtos() {
		return systemIniDtos;
	}

	public String getIniid() {
		return iniid;
	}

	public void setIniid(String iniid) {
		this.iniid = iniid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNowValue() {
		return nowValue;
	}

	public void setNowValue(String nowValue) {
		this.nowValue = nowValue;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

}