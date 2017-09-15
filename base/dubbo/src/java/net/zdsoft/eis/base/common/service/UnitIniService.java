package net.zdsoft.eis.base.common.service;

import java.util.List;
import net.zdsoft.eis.base.common.entity.UnitIni;
import net.zdsoft.eis.base.common.entity.User;

public interface UnitIniService {

    /**
     * 新增单位密码设置信息
     * 
     * @param unitId
     * @param password
     */
    public void saveUnitPasswordOption(String unitId);

    /**
     * 更新单位设置信息: 如果value为空,则恢复默认值; flag设置扩展位,为空则不更改
     * 
     * @param unitId
     * @param option
     * @param value
     * @param flag
     */
    public void updateUnitOption(String unitId, String option, String value, String flag);

    /**
     * 新增或修改单位设置信息
     * 
     * @param unitId
     * @param option
     * @param value
     * @param flag
     */
    public void saveUnitOption(String unitId, String option, String value, String flag);

    /**
     * 新增单位设置,根据systemini中标志,单位所需设置
     * 
     * @param unitId
     */
    public void initUnitOption(String unitId);

    /**
     * 
     * @param unitIni
     */
    public void saveUnitOption(UnitIni unitIni);

    /**
     * 得到Unitid单位iniid项设置的信息
     * 
     * @param unitId
     * @param iniId
     * @return
     */
    public UnitIni getUnitOption(String unitId, String iniId);

    /**
     * 得到Unitid单位公共树相关的设置项列表
     * 
     * @param unitId
     * @param iniId
     * @return
     */
    public List<UnitIni> getUnitTreeParamOptions(String unitId);

    /**
     * 得到unitid单位iniid项设置的当前值
     * 
     * @param unitId
     * @param iniId
     * @return
     */
    public String getUnitOptionValue(String unitId, String iniId);
    
    /**
     * 得到unitid单位iniid项设置的当前值
     * 
     * @param iniId
     * @return
     */
    public boolean getUnitOptionBooleanValue(String unitId, String iniId);

    /**
     * 对于密码为空的用户，根据配置生成密码，该密码已加密
     * 
     * @param user
     * @return
     */
    public User getUserPass(User user);


    /**
     * 保存单位选项
     */
	public void saveUnitOptions(UnitIni[] options);

}
