/** 
 
 * @author wangsn
 * @since 1.0
 * @version $Id: BaseDataServiceImpl.java, v 1.0 2011-6-8 下午01:27:44 wangsn Exp $
 */
package net.zdsoft.eis.dataservice.server.service.impl.v3;

import java.util.Date;

import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.SerialRegisterService;
import net.zdsoft.eis.base.data.sync.converter.UnitConverter;
import net.zdsoft.eis.base.data.sync.converter.UserConverter;
import net.zdsoft.eis.base.deploy.SystemDeployService;
import net.zdsoft.eis.dataservice.middle.exception.EisDataServiceException;
import net.zdsoft.eis.dataservice.middle.service.v3.BaseDataService;
import net.zdsoft.eis.dataservice.middle.util.DataServiceUtils;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.license.LicenseInfo;
import net.zdsoft.license.service.LicenseService;

import com.winupon.syncdata.basedata.entity.son.MqUnit;
import com.winupon.syncdata.basedata.entity.son.MqUser;

public class BaseDataServiceImpl implements BaseDataService {

    private UnitConverter unitConverter;
    private UserConverter userConverter;
    private BaseUnitService baseUnitService;
    private LicenseService licenseService;
    private SerialRegisterService serialRegisterService;
    private SystemDeployService systemDeployService;

    public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    public void setUnitConverter(UnitConverter unitConverter) {
        this.unitConverter = unitConverter;
    }

    public void setUserConverter(UserConverter userConverter) {
        this.userConverter = userConverter;
    }

    @Override
    public void registerTopUnit(MqUnit mqUnit, MqUser mqUser, String verifyKey)
            throws EisDataServiceException {
        DataServiceUtils.verifyKey(verifyKey);
        BaseUnit unit = new BaseUnit();
        unitConverter.toEntity(mqUnit, unit);

        User user = new User();
        userConverter.toEntity(mqUser, user);

        try {
            baseUnitService.registerTopUnitFromMq(unit, user);
        } catch (Exception e) {
            throw new EisDataServiceException(e);
        }

    }

	@Override
	public LicenseInfo getLicenseInfo(String verifyKey) throws EisDataServiceException {
        DataServiceUtils.verifyKey(verifyKey);
		return licenseService.getLicense();
	}
	
	public void registerAdminUser(MqUnit mqUnit, MqUser mqUser, String verifyKey) {
		 User user = new User();
         userConverter.toEntity(mqUser, user);
         user.setOwnerType(User.TEACHER_LOGIN);
         try {
	         baseUnitService.saveAdminUser(mqUnit.getId(), user);
         } catch (Exception e) {
        	 throw new EisDataServiceException(e);
         }
	}

	@Override
	public void registerTopUnit(MqUnit mqUnit, String license, String verifyKey)
			throws EisDataServiceException {
			 DataServiceUtils.verifyKey(verifyKey);
	        BaseUnit unit = new BaseUnit();
	        unitConverter.toEntity(mqUnit, unit);

	        baseUnitService.createUnionidFromMq(unit);
	        unit.setMark(BaseUnit.UNIT_MARK_NORAML);
	        unit.setUsetype(BaseUnit.UNIT_USETYPE_LOCAL);
	        unit.setAuthorized(BaseUnit.UNIT_AUTHORIZED);
	        unit.setRegcode(BaseUnit.UNIT_REGCODE_DEF);
	        
	        if (unit.getUnitclass() == 1) {
	            unit.setUnitusetype("01");// 教育局
	        } else {
	            if (SystemVersion.PRODUCT_EIS.equals(getSystemDeployService().getProductId())) {
	                unit.setUnitusetype("11");// 中学
	            } else if (SystemVersion.PRODUCT_EISU.equals(getSystemDeployService().getProductId())) {
	                unit.setUnitusetype("41");// 中等职业学校
	            } else {
	                unit.setUnitusetype("11");// 中学
	            }
	        }

	        try {
	        	
	        	PromptMessageDto msgDto = licenseService.verifyLicense(unit.getName(), license);
	        	if (msgDto.getOperateSuccess() == false) {
	        		throw new EisDataServiceException(msgDto.getErrorMessage());
	        	}
	        	
	        	msgDto = serialRegisterService.registerTopUnit(unit,license);
	        	if (msgDto.getOperateSuccess() == false) {
	        		throw new EisDataServiceException(msgDto.getErrorMessage());
	        	}
	        } catch (Exception e) {
	            throw new EisDataServiceException(e);
	        }
		
	}

	@Override
	public void updateLicense(String unitName, String license, String verifyKey)
			throws EisDataServiceException {
		 DataServiceUtils.verifyKey(verifyKey);
		 BaseUnit unit = serialRegisterService.getTopUnit();
		 unit.setName(unitName);
    	 PromptMessageDto msgDto = serialRegisterService.registerTopUnit(unit, null, license, true);
    	 if (msgDto.getOperateSuccess() == false ) {
    		 throw new EisDataServiceException(msgDto.getErrorMessage());
    	 }
		
	}

	 /**
     * 验证序列号是否合法
     * @param verifyKey校验码 
     * @return 如果有异常，则为出错消息
     * 0: 序列号不存在，需注册序列号
     * 1: 序列号合法
     * -1：序列号不合法（过期），需更新序列号
     */
	public int verifyLicense(String verifyKey) throws EisDataServiceException {
		LicenseInfo license = licenseService.getLicense();
		if (license == null ) {
			return 0;
		}
		if (license.getExpireDate().compareTo(new Date()) < 0) {
			return -1;
		}
		return 1;
	}

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setSerialRegisterService(SerialRegisterService serialRegisterService) {
		this.serialRegisterService = serialRegisterService;
	}

	public SerialRegisterService getSerialRegisterService() {
		return serialRegisterService;
	}

	public void setSystemDeployService(SystemDeployService systemDeployService) {
		this.systemDeployService = systemDeployService;
	}

	public SystemDeployService getSystemDeployService() {
		return systemDeployService;
	}

}
