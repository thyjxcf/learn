/**
 * 
 */
package net.zdsoft.eis.base.remote.param.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.remote.param.dto.InParamDto;
import net.zdsoft.eis.base.remote.param.dto.OutParamDto;
import net.zdsoft.eis.base.remote.param.service.CommonParamService;

/**
 * <p>
 * ZDSoft学籍系统（stusys）V3.5
 * </p>
 * 
 * @author zhaosf
 * @since 1.0
 * @version $Id: CommonParamServiceImpl.java,v 1.6 2007/01/25 08:02:04 zhaosf Exp $
 */

public class CommonParamServiceImpl implements CommonParamService {
    private static final Logger log = LoggerFactory.getLogger(CommonParamServiceImpl.class);


    /**
     * 单位信息验证返回信息
     */
    private static final int UNIT_VALIDATE_SUCCESS = 1;// 成功
    public static final int PARAM_GUID_NOTNULL = -1;// 参数异常,guid不能为空
    private static final int PARAM_NAME_NOTNULL = -2;// 参数异常,单位不能为空
    public static final int ERROR_GUID_NOTEXIST = 20;// guid单位不存在
    public static final int ERROR_UNIT_DELETE = 21;// guid单位已删除
    private static final int ERROR_GUID_NAME_MATCHE = 23;// 单位guid和单位名称不一致
    private static final int ERROR_GUID_PASSWORD_MATCHE = 10;// 单位管理员密码不正确
    private static final int ERROR_ADMIN_NOTEXIST = 11;// 单位管理员不存在,或已删除
    private static final int ERROR_UNIT_MARK_ERROR = 24;// 单位状态异常
    private static final int ERROR_UNIT_MARK_NOAUDIT = 25;// 单位状态未审核
    private static final int ERROR_UNIT_MARK_LOCK = 26;// 单位状态锁定
    
    private UnitService unitService;// 单位信息
    private UserService userService;// 用户信息

    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public InParamDto getInParamDto() throws Exception {
        log.info("getInParamDto: get inparam");

        InParamDto inParamDto = new InParamDto();

        // 取顶级单位
        Unit unitDto = unitService.getTopEdu();
        if (null == unitDto) {
            throw new Exception("取不到顶级单位信息");
        }

        inParamDto.setUnitguid(unitDto.getId());
        inParamDto.setUnionid(unitDto.getUnionid());
        inParamDto.setUnitname(unitDto.getName());

        // 取用户信息
        User userDto = userService.getUnitAdmin(unitDto.getId());
        if (null == userDto) {
            throw new Exception("取不到顶级单位管理员信息");
        }

        inParamDto.setLoginname(userDto.getName());
        inParamDto.setPwd(userDto.getPassword());

        return inParamDto;
    }

    public OutParamDto checkInParamValidity(InParamDto inParamDto) throws Exception {
        log.info("checkInParamValidity: check inparam validity");

        // 校验单位及用户有效性
        int rtn = checkReport(inParamDto.getPwd(), inParamDto.getUnitguid(),
                inParamDto.getUnitname()).intValue();
        String content = getContentByCode(rtn);

        OutParamDto outParamDto = new OutParamDto();
        if (UNIT_VALIDATE_SUCCESS == rtn) {
            outParamDto.setCode(OutParamDto.CODE_OK);
        } else {
            outParamDto.setCode(rtn);
        }
        outParamDto.setContent(content);
        return outParamDto;
    }

    public String getContentByCode(Integer code) {
        log.debug("getContentByCode: code=" + String.valueOf(code));

        String content = "";
        switch (code.intValue()) {
        case UNIT_VALIDATE_SUCCESS:
            content = "单位校验成功";
            break;
        case PARAM_GUID_NOTNULL:
            content = "参数异常,单位guid不能为空";
            break;
        case PARAM_NAME_NOTNULL:
            content = "参数异常,单位名称不能为空";
            break;
        case ERROR_GUID_NOTEXIST:
            content = "单位不存在";
            break;
        case ERROR_UNIT_DELETE:
            content = "单位已删除";
            break;
        case ERROR_GUID_NAME_MATCHE:
            content = "单位guid和单位名称不一致";
            break;
        case ERROR_GUID_PASSWORD_MATCHE:
            content = "单位管理员账号或密码不正确";
            break;
        case ERROR_ADMIN_NOTEXIST:
            content = "单位管理员不存在,或已删除";
            break;
        case ERROR_UNIT_MARK_ERROR:
            content = "单位状态异常";
            break;
        case ERROR_UNIT_MARK_NOAUDIT:
            content = "单位未审核";
            break;
        case ERROR_UNIT_MARK_LOCK:
            content = "单位锁定";
            break;
        default:
            content = "未知错误";
            break;
        }
        return content;
    }

    public Integer checkReport(String password, String unitId, String unitName) {
        if (unitId == null)
            return PARAM_GUID_NOTNULL;
        if (unitName == null)
            return PARAM_NAME_NOTNULL;

        Unit unit = unitService.getUnit(unitId);
        if (unit == null)
            return ERROR_GUID_NOTEXIST;
        if (unit.getIsdeleted())
            return ERROR_UNIT_DELETE;

        if (unit.getMark() == null) {
            return ERROR_UNIT_MARK_ERROR;
        }
        switch (unit.getMark().intValue()) {
        case Unit.UNIT_MARK_NOTAUDIT:
            return ERROR_UNIT_MARK_NOAUDIT;
        case Unit.UNIT_MARK_LOCK:
            return ERROR_UNIT_MARK_LOCK;
        case Unit.UNIT_MARK_NORAML:
            break;
        default:
            return ERROR_UNIT_MARK_ERROR;
        }

        if (!unitName.trim().equals(unit.getName().trim()))
            return ERROR_GUID_NAME_MATCHE;

        User user = userService.getUnitAdmin(unitId);
        if (user == null)
            return ERROR_ADMIN_NOTEXIST;
        if (password == null)
            password = "";
        if (user.getPassword() == null && (password == null || "".equals(password))) {
            return UNIT_VALIDATE_SUCCESS;
        } else {
            if (password.equals(user.getPassword() == null ? "" : user.getPassword())) {
                return UNIT_VALIDATE_SUCCESS;
            } else {
                return ERROR_GUID_PASSWORD_MATCHE;
            }
        }
    }

}
