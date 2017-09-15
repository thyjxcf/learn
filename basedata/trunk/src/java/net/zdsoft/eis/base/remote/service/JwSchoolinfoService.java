package net.zdsoft.eis.base.remote.service;

import net.zdsoft.eis.base.remote.param.dto.InParamDto;


/* 
 * <p>ZDSoft学籍系统-综合统计</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: JwSchoolInfoService.java, v 1.0 Apr 24, 2007 4:29:33 PM dongzk Exp $
 */
public interface JwSchoolinfoService {
	
	/**
	 * 测试服务是否正常
	 * @return
	 */
	public String test();
	
	/**
	 * 验证学校编号是否存在
	 * @param inParamDto 输入参数
	 * @param schid 学校guid
	 * @param code 学校编号
	 * @return boolean ture:存在；false:不存在
	 */
	public boolean checkCodeIsExist(InParamDto inParamDto, String schid, String code) throws Exception;;
}



