package net.zdsoft.eis.base.remote.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.zdsoft.eis.base.common.service.SchoolService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.remote.param.dto.InParamDto;
import net.zdsoft.eis.base.remote.param.dto.OutParamDto;
import net.zdsoft.eis.base.remote.param.service.CommonParamService;
import net.zdsoft.eis.base.remote.service.JwSchoolinfoService;


/* 
 * <p>ZDSoft学籍系统-综合统计</p>
 * @author Dongzk
 * @since 1.0
 * @version $Id: JwSchoolinfoServiceImpl.java, v 1.0 Apr 24, 2007 4:31:26 PM dongzk Exp $
 */
public class JwSchoolinfoServiceImpl implements JwSchoolinfoService {
	private static final Logger log = LoggerFactory.getLogger(JwSchoolinfoServiceImpl.class);
	
	private CommonParamService commonParamService;//ws参数
	private SchoolService schoolService;
	
	public void setCommonParamService(CommonParamService commonParamService) {
		this.commonParamService = commonParamService;
	}
	
	public void setSchoolService(
			SchoolService schoolService) {
		this.schoolService = schoolService;
	}


	public String test() {
		return BaseConstant.SUCCESS;
	}

	
	/*
	 * (non-Javadoc)
	 * @see net.zdsoft.stusys.edusys.basic.service.JwSchoolinfoService#checkCodeIsExist(net.zdsoft.stusys.system.webservice.dto.InParamDto, java.lang.String, java.lang.String)
	 */
	public boolean checkCodeIsExist(InParamDto inParamDto, String schid, String code) throws Exception {
		log.debug("checkCodeIsExist: check school code if exist");
		
		// 校验单位的有效性
		OutParamDto outParamDto = null;	
		outParamDto = commonParamService.checkInParamValidity(inParamDto);
		if(OutParamDto.CODE_OK != outParamDto.getCode()){
			throw new RuntimeException(outParamDto.getContent());
		}
		
		// 检查学校编号是否已经存在(true存在，false不存在)
		if(code == null || "".equals(code)) return false;
		if(schid == null || "".equals(schid)) schid = "";
        String _schid = schoolService.getSchoolIdByCode(code);
        if(_schid!=null && !_schid.equals("") && !_schid.equals(schid)){
            return true;
        }else{
        	return false;
        }
	}

	
}



