/* 
 * <p>ZDSoft学籍系统（stusys）V3.5</p>
 * @author zhangza
 * @since 1.0
 * @version $Id: SerialRegisterService.java,v 1.7 2007/02/28 03:46:32 zhongh Exp $
 */
package net.zdsoft.eis.base.data.service;

import javax.servlet.http.HttpServletRequest;

import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.frame.dto.PromptMessageDto;

public interface SerialRegisterService {

	/**
	 * 获得顶级单位
	 * 
	 * @return
	 */
	public BaseUnit getTopUnit();
	

	/**
	 * 按照一定的规则生成顶级单位的unionid
	 * 
	 * @param province
	 *            省
	 * @param city
	 *            市
	 * @param county
	 *            区、县
	 * @param unitDto
	 * @return
	 */
	public String createUnionId(String province, String city, String county,
			BaseUnit unitDto);
	/**
	 * 过期日期
	 * @return
	 */
	public String getExpireDate();

	/**
	 * 子系统权限串
	 * @return
	 */
	public String getFunStr();
	
	/**
	 * 刷新数据库的子系统和模块的启用标志
	 *
	 */
	public void updateRefreshSubSys();
	/**
	 * 验证序列号
	 * @return 返回的数组，第一个元素表示检验结果，第二个元素表示注册的类型(学校或教育局)
	 */
	public String [] VerifySerial(String name,String serial);
	
	/**
	 * 按序列号里的信息初始化子系统表和模块表
	 *
	 */
	public void initSerialRegist();
	/**
	 * 校验是否注册和过期日期，返回“”是通过
	 * @return
	 */
	public String VerifySerial();
	/**
	 * 检查是否重复提交表单
	 * @param request
	 * @return
	 */
	public boolean isReduplicate(HttpServletRequest request);


    /**
     * 注册顶级单位
     */
    public PromptMessageDto registerTopUnit(BaseUnit unitDto, User userDto, String licenseTxt, boolean update) ;
    
    /**
     * 注册顶级单位(部署工具初始化单位时调用)
     */    public PromptMessageDto registerTopUnit(BaseUnit unitDto,  String licenseTxt );
}
