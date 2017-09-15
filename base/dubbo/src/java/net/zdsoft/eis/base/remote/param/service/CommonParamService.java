/**
 * 
 */
package net.zdsoft.eis.base.remote.param.service;

import net.zdsoft.eis.base.remote.param.dto.InParamDto;
import net.zdsoft.eis.base.remote.param.dto.OutParamDto;


/** 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhaosf
 * @since 1.0
 * @version $Id: CommonParamService.java,v 1.3 2007/01/25 08:01:54 zhaosf Exp $
 */

public interface CommonParamService {
	/**
	 * 取接口参数
	 * @return
	 */
	public InParamDto getInParamDto() throws Exception;
	
	/**
	 * 校验参数有效性
	 * @param inParamDto 输入参数
	 * @return 输出参数
	 */
	public OutParamDto checkInParamValidity(InParamDto inParamDto) throws Exception;
	
	/**
	 * 根据校验单位的有效性的返回代码取返回信息
	 * @param code 代码
	 * @return
	 */
	public String getContentByCode(Integer code);
	
    /**
     * 验证单位管理员密码、单位guid、单位名称的一致性
     * 
     * @param pwd
     * @param unitId
     * @param unitName
     * @return
     */
    public Integer checkReport(String pwd, String unitId, String unitName);
}
