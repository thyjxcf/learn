package net.zdsoft.eis.base.data.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.DuplicateFormatFlagsException;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.zdsoft.eis.base.common.dao.SystemVersionDao;
import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.base.common.entity.SystemVersion;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.ModuleService;
import net.zdsoft.eis.base.common.service.SimpleModuleService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.common.service.UserService;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.SerialRegisterService;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.eis.system.frame.serial.SerialRegister;
import net.zdsoft.eis.system.frame.service.ModelOperatorService;
import net.zdsoft.license.LicenseInfo;
import net.zdsoft.license.service.LicenseService;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 电子政务系统V3.6
 * </p>
 * <p>
 * </p>
 * 
 * @author zhongh
 * @since 1.0
 * @version $Id: SerialRegisterServiceImpl.java, v 1.0 2007-1-4 上午11:27:44 zhongh Exp $
 */
public class SerialRegisterServiceImpl implements SerialRegisterService {
    private static final Logger log = LoggerFactory.getLogger(SerialRegisterService.class);

    private static String funStr;// 子系统权限串
    private static int unitCountLimit = -1;// 可用单位数
    private static String expireDate;// 过期日期
    @SuppressWarnings("unused")
    private static String modIdStr = "0";// 模块权限串

    private BaseUnitService baseUnitService;
    private UserService userService;
    private ModuleService moduleService;
    private SimpleModuleService simpleModuleService;
    private SubSystemService subSystemService;
    private ModelOperatorService modelOperatorService;
    private LicenseService licenseService;
    private SystemIniService systemIniService;
    private SystemVersionDao systemVersionDao;
    private static boolean devModel = false;
    
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("struts");
        if(bundle != null) {
            devModel = BooleanUtils.toBoolean(bundle.getString("struts.devMode"));
        }
    }
    

    public void setModelOperatorService(ModelOperatorService modelOperatorService) {
        this.modelOperatorService = modelOperatorService;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setBaseUnitService(BaseUnitService baseUnitService) {
        this.baseUnitService = baseUnitService;
    }

    public void setModuleService(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    public void setSimpleModuleService(SimpleModuleService simpleModuleService) {
		this.simpleModuleService = simpleModuleService;
	}

	public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public BaseUnit getTopUnit() {
        List<Unit> unitList = baseUnitService.getUnderlingUnits(BaseUnit.TOP_UNIT_GUID);
        if (unitList != null && unitList.size() > 1) {
            log.error("发现多于1个的顶级单位，数据有错误，请改正单位表中的数据后再使用！");
            throw new DuplicateFormatFlagsException("发现多于1个的顶级单位，数据有错误，请改正单位表中的数据后再使用！");
        }

        if (unitList == null || unitList.size() == 0) {
            return null;
        } else {

        }
        BaseUnit unitDto = baseUnitService.getBaseUnit(unitList.get(0).getId());
        return unitDto;
    }
    
    public PromptMessageDto registerTopUnit(BaseUnit unitDto,  String licenseTxt ) {
    	 PromptMessageDto msgDto = new PromptMessageDto();
         msgDto.setOperateSuccess(true);
         msgDto.setPromptMessage("注册成功");
         try {
             licenseService.saveLicense(unitDto.getName(), licenseTxt);
             baseUnitService.saveUnitWithoutUser(unitDto);
             
             LicenseInfo licenseInfo = licenseService.getLicense();
             getSystemVersionDao().updateProduct(licenseInfo.getProductCode());
             updateRefreshSubSys();

         } catch (Exception e) {
             e.printStackTrace();
             msgDto.setErrorMessage(e.getMessage());
             msgDto.setOperateSuccess(false);
         }

         return msgDto;
    }

    public PromptMessageDto registerTopUnit(BaseUnit unitDto, User userDto, String licenseTxt, boolean update) {
        PromptMessageDto msgDto = new PromptMessageDto();
        try {
        
            msgDto = licenseService.saveLicense(unitDto.getName(), licenseTxt);
            if (msgDto.getOperateSuccess() == false ) {
            	throw new Exception(msgDto.getErrorMessage());
            }
            LicenseInfo licenseInfo = licenseService.getLicense();
            
            if (update){
            	String newProductCode = licenseInfo.getProductCode(); 
            	String oldProductCode = getSystemVersionDao().getSystemVersion().getProductId();
            	if (!oldProductCode.equals(newProductCode)) {
            		throw new Exception("序列号所对应的产品和当前数据库不对应");
            	}
                BaseUnit oldTopUnit = getTopUnit();
                oldTopUnit.setName(unitDto.getName());
                baseUnitService.updateUnit(oldTopUnit);
            } else {
                baseUnitService.saveUnit(unitDto, userDto);
                getSystemVersionDao().updateProduct(licenseInfo.getProductCode());
            }

            BaseUnit topUnit = getTopUnit();
            List<BaseUnit> units = baseUnitService.getAllBaseUnits(topUnit.getId(), true);
            for (BaseUnit unit : units) {
                unit.setLimitTeacher(licenseInfo.getUserNumPerUnit());
                baseUnitService.updateUnit(unit);
            }

            updateRefreshSubSys();
          

        } catch (Exception e) {
            e.printStackTrace();
            msgDto.setErrorMessage(e.getMessage());
            msgDto.setOperateSuccess(false);
            return msgDto;
        }

        msgDto.setOperateSuccess(true);
        msgDto.setPromptMessage("注册成功");
        return msgDto;
    }

  
    /**
     * 按照一定的规则生成顶级单位的unionid
     * 
     * @param province 省
     * @param city 市
     * @param county 区、县
     * @param unitDto
     * @return
     */
    public String createUnionId(String province, String city, String county, BaseUnit unitDto) {
        StringBuffer sb = new StringBuffer();

        String unionid = null;
        if (county != null && !county.equals("")) {
            unionid = county;
        } else if (city != null && !city.equals("")) {
            unionid = city;
        } else if (province != null && !province.equals("")) {
            unionid = province;
        } else {
            return null;
        }
        sb.append(unionid);
        // 教育局
        if (unitDto.getUnitclass() == Unit.UNIT_CLASS_EDU) {
            // 对于第四级教育局(即乡镇级教育局)的unionid生成
            if (unionid.trim().length() == BaseUnit.FINAL_EDU_LENGHT
                    && unitDto.getRegionlevel() != null && 5 == unitDto.getRegionlevel()) {
                for (int i = 0; i < BaseUnit.UNIONID_LENGTH_EDU4; i++) {
                    if (i == BaseUnit.UNIONID_LENGTH_EDU4 - 1) {
                        sb.append("1");
                    } else {
                        sb.append("0");
                    }
                }
            }
        }

        // 学校
        if (unitDto.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
            // 学校单位的统一编号生成方式
            sb.append(net.zdsoft.keel.util.StringUtils.createCountsSymbol("0", BaseUnit.FINAL_SCH_LENGTH - unionid.length()
                    - BaseUnit.SCHOOLE_UNION));
            for (int i = 0; i < BaseUnit.SCHOOLE_UNION; i++) {
                if (i == BaseUnit.SCHOOLE_UNION - 1) {
                    sb.append("1");
                    break;
                }
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 把序列号里的子系统权限串，可用单位数，过期日期存放到内存；
     * 
     * @param name 单位名称
     * @param serial 注册序列号
     * @return
     */
    private String createSerialRegistInfo(String name, String serial) {
        String[] verifyResult = { "null", "null", "0" };
        if (name != null && serial != null) {
            verifyResult = VerifySerial(name, serial);
        }
        // 序列号合法或者已过期
        if (verifyResult[0] != null && (verifyResult[0].equals("") || verifyResult[2].equals("-2"))) {
            funStr = SerialRegister.getFunStr(serial);
            expireDate = SerialRegister.GetExpireDate(serial);
            // 计算时,再加上顶级单位
            unitCountLimit = SerialRegister.getUnitCountLimit(serial) + 1;
            modIdStr = SerialRegister.getModIds(serial);
        } else {
            funStr = "00000000000000000000000000000000";
            expireDate = "";
            unitCountLimit = -1;
            modIdStr = "0";

        }

        log.debug(funStr);
        log.debug(expireDate);
        log.debug(String.valueOf(unitCountLimit));

        return verifyResult[0];

    }

    /**
     * 刷新数据库的子系统和模块的启用标志
     * 
     */
	public void updateRefreshSubSys() {
	    
	    if(devModel) {
	        return;
	    }
		if (! getSystemIniService().getBooleanValue("SYSTEM.CHECKLICENSE")) return;
		LicenseInfo licenseInfo = licenseService.getLicense();
		//如果还没注册, 返回
		if (licenseInfo == null ) return;
		Set<String> availSubsystemSet = licenseInfo.getAvailableSubsystems();

		List<SubSystem> subsystems = subSystemService.getSubSystemsWithoutCache();
		for (SubSystem subsystem : subsystems) {
			String subsystemId = String.valueOf(subsystem.getId().longValue());
			String code = subsystem.getCode();
			if (!availSubsystemSet.contains(code)) {
				moduleService.disableModules(Integer.parseInt(subsystemId));
				simpleModuleService.disableModules(Integer.parseInt(subsystemId));
				subsystem.setMark(0);
				subSystemService.saveSubsystem(subsystem);
			} else {
				moduleService.enableModules(Integer.parseInt(subsystemId));
				simpleModuleService.enableModules(Integer.parseInt(subsystemId));
				subsystem.setMark(1);
				subSystemService.saveSubsystem(subsystem);
			}
		}
		subSystemService.clearCache();
		moduleService.clearCache();
		modelOperatorService.clearCache();
	}

    /**
     * 按序列号里的信息初始化子系统表和模块表
     * 
     */
    public void initSerialRegist() {        
        try {
//            //初始化系统参数
//            RedisUtils.remove("EIS.SYSTEM.INI");
//            List<SystemIni> inis = getSystemIniService().getSystemIniByViewable(0);
//            for(SystemIni ini : inis) {
//                RedisUtils.setHashField("EIS.SYSTEM.INI", ini.getIniid(), StringUtils.trimToEmpty(ini.getNowValue()));
//            }
//            inis = getSystemIniService().getSystemIniByViewable(1);
//            for(SystemIni ini : inis) {
//                RedisUtils.setHashField("EIS.SYSTEM.INI", ini.getIniid(), StringUtils.trimToEmpty(ini.getNowValue()));
//            }
            String contextRoot = getClass().getResource("/").getPath() + "../../";
            File libDir = new File(new File(contextRoot), "META-INF/MANIFEST.MF");
            String line = "";
            String version = "";
            try {
                BufferedReader fr = new BufferedReader(new FileReader(libDir));
                while ((line = fr.readLine()) != null) {
                    if(StringUtils.startsWith(StringUtils.trim(line), "Implementation-Version:")) {
                        version = StringUtils.substringAfter(line, "Implementation-Version:").trim();
                        break;
                    }
                }
                RedisUtils.set("EIS.SYSTEM.VERSION", version);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
            }
            
            updateRefreshSubSys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getExpireDate() {
        return expireDate;
    }

    public String getFunStr() {
        return funStr;
    }

    public String[] VerifySerial(String name, String serial) {
        // 第一位是序列号验证的说明,第二位标识学校或教育局,第三位表示序列号验证返回的代码
        String[] result = { "", "", "" };
        // 检查是学校还是教育局
        int iFlag = SerialRegister.VerifySerial(name, serial, SystemVersion.PRODUCT_EIS_E);

        if (iFlag == 0 || iFlag == 1 || iFlag == -5) {
            result[1] = String.valueOf(Unit.UNIT_CLASS_EDU);
        }
        if (iFlag == -3) {
            iFlag = SerialRegister.VerifySerial(name, serial, SystemVersion.PRODUCT_EIS_S);
            if (iFlag == 0 || iFlag == 1 || iFlag == -5) {
                result[1] = String.valueOf(Unit.UNIT_CLASS_SCHOOL);
            }
        }
        result[0] = SerialRegister.getVerifyStatus(iFlag);
        result[2] = String.valueOf(iFlag);

        return result;
    }

    public String VerifySerial() {

        String resultStr = "";

        if (expireDate != null && expireDate.length() >= 10) {
            String tempDate = expireDate + " 23:59:59";
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Date date = null;
            try {
                date = dateFormat.parse(tempDate);
            } catch (ParseException e) {
                log.error(e.getMessage());
            }
            if (date.before(new Date())) {
                resultStr = "系统序列号过期，请重新注册!";
            }
        } else {
            resultStr = "不是有效的注册序列号，请注册系统序列号！";
        }

        return resultStr;
    }

    @SuppressWarnings("unchecked")
    public boolean isReduplicate(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String reqString = (String) session.getAttribute("reqString");
        StringBuffer newReq = new StringBuffer();
        Enumeration<String> parameters = request.getParameterNames();
        String para = null;
        while (parameters.hasMoreElements()) {
            para = parameters.nextElement();
            newReq.append(para);
            newReq.append(request.getParameter(para));
        }
        session.setAttribute("reqString", newReq.toString());
        if (reqString == null) {
            return false;
        } else {
            if (reqString.equals(newReq.toString())) {
                // reduplicate request
                return true;
            } else {
                // correct request
                return false;

            }
        }
    }

	public void setLicenseService(LicenseService licenseService) {
		this.licenseService = licenseService;
	}

	public LicenseService getLicenseService() {
		return licenseService;
	}

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public SystemIniService getSystemIniService() {
		return systemIniService;
	}

	public void setSystemVersionDao(SystemVersionDao systemVersionDao) {
		this.systemVersionDao = systemVersionDao;
	}

	public SystemVersionDao getSystemVersionDao() {
		return systemVersionDao;
	}

	
}
