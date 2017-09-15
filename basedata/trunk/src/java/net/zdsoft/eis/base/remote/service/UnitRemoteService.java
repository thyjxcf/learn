package net.zdsoft.eis.base.remote.service;

import java.util.Map;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.remote.dto.UnitRegisterResultDto;
import net.zdsoft.eis.base.remote.exception.UnitRegisterException;
import net.zdsoft.leadin.exception.ItemExistsException;

public interface UnitRemoteService {

    /**
     * 远程服务测试方法
     * 
     * @return
     */
    public String test();

    /**
     * 验证单位管理员密码、单位guid、单位名称的一致性
     * 
     * @param pwd
     * @param unitId
     * @param unitName
     * @return
     */
    public Integer checkReport(String pwd, String unitId, String unitName);

    /**
     * 单位远程注册
     * 
     * @param unitObjs
     * @param userObjs
     */
    public UnitRegisterResultDto saveRemoteUnitRegister(BaseUnit[] unitObjs,
            User[] userObjs) throws UnitRegisterException;
    
    /**
     * 单位远程注册（只注册单位，没有用户信息，地市级平台注册到省级平台使用，适用于eis3.6平台）
     * 
     * @param unitObjs
     * @return
     */
    public UnitRegisterResultDto saveRemoteUnitRegister2Prv(BaseUnit[] unitObjs)
        throws UnitRegisterException;

    /**
     * 对于远程注册上来的单位，更新该单位一些属性值
     * 
     * @param unit
     */
    public void updateUnitProperty(BaseUnit unitDto)
            throws UnitRegisterException;

    /**
     * 远程检查单位是否已存在，包括名称重复,guid重复,unionid重复，同时变更顶级单位的parentid为相应行政区划的上级单位id
     * 
     * @param unit
     * @throws ItemExistsException
     */
    public UnitRegisterResultDto checkUnitExists(BaseUnit unitDto,
            Map<String, BaseUnit> unitMap) throws UnitRegisterException;

    /**
     * 检查单位上级单位是否存在，并更改顶级单位的parentid
     * 
     * @param unitDto
     * @param unitMap
     * @param isValidate
     *            是否忽略该单位的存在性，用户更新信息时的标志量,true需要校验
     * @throws UnitRegisterException
     */
    public void checkParentUnit(BaseUnit unitDto, Map<String, BaseUnit> unitMap,
            boolean isValidate) throws UnitRegisterException;

}
