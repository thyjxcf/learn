package net.zdsoft.eis.base.remote.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.entity.User;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.base.common.service.UnitIniService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.base.data.BasedataConstants;
import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.data.service.BaseUnitService;
import net.zdsoft.eis.base.data.service.BaseUserService;
import net.zdsoft.eis.base.remote.dto.UnitRegisterResultDto;
import net.zdsoft.eis.base.remote.exception.UnitRegisterException;
import net.zdsoft.eis.base.remote.param.dto.OutParamDto;
import net.zdsoft.eis.base.remote.param.service.CommonParamService;
import net.zdsoft.eis.base.remote.service.UnitRemoteService;
import net.zdsoft.keelcnet.entity.DtoAssembler;

public class UnitRemoteServiceImpl implements UnitRemoteService {
	private RegionService regionService;
	private UnitIniService unitIniService;
	private BaseUserService baseUserService;
	private BaseUnitService baseUnitService;
	private CommonParamService commonParamService;

	public void setRegionService(RegionService regionService) {
		this.regionService = regionService;
	}

	public void setUnitIniService(UnitIniService unitIniService) {
		this.unitIniService = unitIniService;
	}

	public void setBaseUnitService(BaseUnitService baseUnitService) {
		this.baseUnitService = baseUnitService;
	}

	public void setBaseUserService(BaseUserService baseUserService) {
		this.baseUserService = baseUserService;
	}

	public void setCommonParamService(CommonParamService commonParamService) {
		this.commonParamService = commonParamService;
	}

	public String test() {
		return BaseConstant.TEST_STRING;
	}

	public Integer checkReport(String password, String unitId, String unitName) {
		return commonParamService.checkReport(password, unitId, unitName);
	}

	public UnitRegisterResultDto saveRemoteUnitRegister(BaseUnit[] unitObjs,
			User[] userObjs) throws UnitRegisterException {
		UnitRegisterResultDto outParam = new UnitRegisterResultDto();
		if (unitObjs == null || userObjs == null) {
			outParam.setCode(UnitRegisterResultDto.REMOTE_REGISTER_ERROR_NULL);
			outParam.setContent("请选择一个单位进行远程注册");
			return outParam;
		}
		BaseUnit unit;
		BaseUnit unitDto, remoteUnit;
		User user, remoteUser;
		User userDto;
		String userPassword, topUnitGuid = null, sysVersion = null;

		StringBuffer unitNameSB = new StringBuffer();

		String[] unitIds = new String[unitObjs.length]; // 单位id
		Map<String, BaseUnit> unitMap = new HashMap<String, BaseUnit>();
		for (int i = 0; i < unitObjs.length; i++) {
			unitMap.put(unitObjs[i].getId(), unitObjs[i]);
			outParam.addSelectUnitId(unitObjs[i].getId());
			unitIds[i] = unitObjs[i].getId();
		}
		Map<String, BaseUnit> remoteUnitMap = baseUnitService.getBaseUnitMap(unitMap
				.keySet().toArray(new String[] {}));

		Set<String> userGuids = new HashSet<String>();
		Set<String> userNames = new HashSet<String>();
		for (int i = 0; i < userObjs.length; i++) {
			userGuids.add(userObjs[i].getId());
			userNames.add(userObjs[i].getName());
		}
		Map<String, User> userMap = baseUserService.getUsersMap(userGuids
				.toArray(new String[] {}));
		Map<String, Integer> userNamesCountMap = baseUserService
				.getCountsByUserNames(userNames.toArray(new String[] {}));

		List<BaseUnit> unitList = new ArrayList<BaseUnit>();
		List<User> userList = new ArrayList<User>();
		List<BaseUnit> updateUnitList = new ArrayList<BaseUnit>();
		List<User> updateUserList = new ArrayList<User>();

		for (int i = 0; i < unitObjs.length; i++) {
			unitDto = unitObjs[i];
			userDto = userObjs[i];
			userDto.setRegion(unitDto.getRegion());

			// 如果单位guid不已存在,先判断单位名称是否重复,便于将错误信息反馈给用户
			// (由于名称重复一般是由于注册成功后,下级删除该单位,然后再增加,再注册上报所导致)
			if (!remoteUnitMap.containsKey(unitDto.getId())) {
				// 如果修改了unionid,则将相关信息返回
				String unionid = unitDto.getUnionid();
				this.checkUnitExists(unitDto, unitMap);
				if (!unionid.equals(unitDto.getUnionid())) {
					outParam.addUpdateUnionIdUnit(new String[] { unionid,
							unitDto.getId() });
				}
			}

			userPassword = userDto.findClearPassword();
			userDto.setPassword(userPassword);

			checkUserNameExists(userDto, userMap, userNamesCountMap);

			if (userDto.getType() == User.TYPE_TOPADMIN) {
				userDto.setType(User.TYPE_ADMIN);// 用户类型顶级管理员变更为普通管理员
			}

			this.updateUnitProperty(unitDto);

			// 如果单位guid已存在，则修改远程单位信息
			if (remoteUnitMap.containsKey(unitDto.getId())) {
				remoteUser = baseUserService.getUnitAdmin(unitDto.getId());
				remoteUnit = remoteUnitMap.get(unitDto.getId());

				this.checkUnitModify(unitDto, remoteUnit, userDto, remoteUser);

				// 如果上下级unionid不一致,则将相关信息(主要由于注册时上级注册下级,下级修改信息时失败引起)
				String unionid = remoteUnit.getUnionid();
				if (!unionid.equals(unitDto.getUnionid())) {
					outParam.addUpdateUnionIdUnit(new String[] { unionid,
							remoteUnit.getId() });
				}

				// 顶级单位记录parentid和版本
				if (unitDto.getParentid().equals(Unit.TOP_UNIT_GUID)) {
					topUnitGuid = unitDto.getId();
					sysVersion = unitDto.getSysVersion();
				}
				this.checkParentUnit(unitDto, unitMap, false);

				user = new User();
				DtoAssembler.toEntity(userDto, user);

				unit = new BaseUnit();
				DtoAssembler.toEntity(unitDto, unit);
				//unit.setUnitintid(remoteUnit.getUnitintid());
				unit.setParentid(remoteUnit.getParentid());

				outParam.addModifyUnitId(unit.getId());
				if (remoteUnit.getEtohSchoolId() != null)
					unit.setEtohSchoolId(remoteUnit.getEtohSchoolId());
				updateUnitList.add(unit);

				if (null == remoteUser) {
					remoteUser = user;
					remoteUser.setId(null);
					//remoteUser.setUnitintid(remoteUnit.getUnitintid());
				} else {
					user.setId(remoteUser.getId());
					//user.setUnitintid(remoteUser.getUnitintid());
					DtoAssembler.toEntity(user, remoteUser);
				}
				updateUserList.add(remoteUser);
				continue;
			}

			// 记录顶级单位id，用于保存本地版本信息
			if (unitDto.getParentid().equals(Unit.TOP_UNIT_GUID)) {
				topUnitGuid = unitDto.getId();
				sysVersion = unitDto.getSysVersion();
			}

			this.checkParentUnit(unitDto, unitMap, true);

			user = new User();
			DtoAssembler.toEntity(userDto, user);
			user.setId(null);
			userList.add(user);

			unit = new BaseUnit();
			DtoAssembler.toEntity(unitDto, unit);
			//unit.setUnitintid(0l);

			unitList.add(unit);
			unitNameSB.append(unitDto.getName());
			if (i != unitObjs.length - 1) {
				unitNameSB.append("、");
			}

			outParam.addRegSuccUnitId(unit.getId());
		}

		saveOrUpdateRegisterUnit(unitList, unitMap, remoteUnitMap, outParam);
		updateUserUnitIntId(unitList, userList);

		try {
			baseUserService.saveUsers(userList.toArray(new User[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}

		saveOrUpdateRegisterUnit(updateUnitList, unitMap, remoteUnitMap,
				outParam);
		updateUserUnitIntId(updateUnitList, updateUserList);

		baseUserService.updateUsers(updateUserList.toArray(new User[] {}),
				unitIds);

		unitIniService.saveUnitOption(topUnitGuid,
				BasedataConstants.UNIT_SYSTEM_VERSION, sysVersion, null);

		outParam.setCode(UnitRegisterResultDto.REMOTE_REGISTER_SUCCESS);
		outParam.setContent("远程注册单位：" + unitNameSB.toString() + "成功！");
		return outParam;

	}

	public void updateUnitProperty(BaseUnit unitDto) throws UnitRegisterException {
		UnitRegisterResultDto outParam = new UnitRegisterResultDto();
		if (unitDto == null) {
			outParam.setCode(UnitRegisterResultDto.REMOTE_REGISTER_ERROR_NULL);
			outParam.setContent("请选择一个单位进行远程注册");
			throw new UnitRegisterException(outParam);
		}

		unitDto.setAuthorized(BaseUnit.UNIT_APPAUTHORIZED);// 设置单位授权类型为附属授权
		unitDto.setMark(Unit.UNIT_MARK_NOTAUDIT);// 设置单位状态为未审核
		unitDto.setUsetype(Unit.UNIT_USETYPE_REPORT);// 设置单位报送类型

		// 顶级单位修正单位类型为EISS
		if (unitDto.getUnittype() == Unit.UNIT_EDU_TOP) {
			if (unitDto.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
				unitDto.setUnittype(Unit.UNIT_SCHOOL_EISS);
			}
		}

		return;
	}

	public UnitRegisterResultDto checkUnitExists(BaseUnit unitDto,
			Map<String, BaseUnit> unitMap) throws UnitRegisterException {
		UnitRegisterResultDto outParam = new UnitRegisterResultDto();

		if (unitDto == null) {
			outParam.setCode(UnitRegisterResultDto.REMOTE_REGISTER_ERROR_NULL);
			outParam.setContent("请选择一个单位进行远程注册");
			return outParam;
		}

		if (baseUnitService.getCountUnitByName(unitDto.getName()) > 0) {
			outParam
					.addErrorUnitId(
							unitDto.getId(),
							unitDto.getName()
									+ "单位名称在上级单位中已经存在,请联系上级单位删除该单位并重新进行注册！可能原因: 1)本平台数据库更换；2)该单位进行远程注册后(上级已存在该单位),"
									+ "进行单位删除且重新增加该单位！");
			throw new UnitRegisterException(outParam);
		} else if (baseUnitService.getCountUnionId(unitDto.getUnionid()) > 0) {
			// 如果已经存在,则重新生成一个编号,调用返回后,同时修改调用端的编号，新增的单位在saveOrUpdateRegisterUnit中有判断
			// 教育局(除去非教育局和乡镇教育局类型)的重复，需提示
			if ((unitDto.getUnittype() != Unit.UNIT_NOTEDU_NOTSCH && unitDto
					.getRegionlevel() != Unit.UNIT_REGION_LEVEL)
					&& unitDto.getUnitclass() == Unit.UNIT_CLASS_EDU) {
				outParam.addErrorUnitId(unitDto.getId(), unitDto.getUnionid()
						+ "单位统一编号已存在");
				throw new UnitRegisterException(outParam);
			}

			// String unionid = unitService.createUnionid(unitDto.getParentid(),
			// unitDto.getUnitclass());
			// unitDto.setUnionid(unionid);
			// outParam.addUpdateUnionIdUnit(new String[] { unionid,
			// unitDto.getId() });
		}

		outParam.setCode(OutParamDto.CODE_OK);
		return outParam;
	}

	public void checkParentUnit(BaseUnit unitDto, Map<String, BaseUnit> unitMap,
			boolean isValidate) throws UnitRegisterException {
		UnitRegisterResultDto outParam = new UnitRegisterResultDto();
		// 上级单位验证及更改
		if (unitDto.getParentid().equals(Unit.TOP_UNIT_GUID)) {
			// 仅对于下属教育局需验证unionid的唯一性，独立部署学校及非教育局单位将重新生成unionid
			if (isValidate && unitDto.getUnittype() == Unit.UNIT_EDU_SUB) {
				if (baseUnitService.getUnitByUnionId(unitDto.getUnionid()) != null) {
					String regionName = regionService.getFullName(unitDto
							.getUnionid());
					outParam.addErrorUnitId(unitDto.getId(),
							regionName == null ? "" : regionName
									+ "行政区域下已存在相应单位");
					throw new UnitRegisterException(outParam);
				}
			}
			String parentUnionId = getUpRegionCode(unitDto.getUnionid());
			BaseUnit pUnit = baseUnitService.getBaseUnitByUnionId(parentUnionId);
			System.out.println("********************parentUnionId="
					+ parentUnionId);
			if (pUnit == null) {
				outParam.addErrorUnitId(unitDto.getId(), unitDto.getName()
						+ "单位的上级单位区域码不是远程服务平台的有效区域码");
				throw new UnitRegisterException(outParam);
			}

			unitDto.setParentid(pUnit.getId());
			unitMap.put(pUnit.getId(), pUnit);// 便于生成编号时取上级单位信息
			if (unitDto.getUnittype() == Unit.UNIT_EDU_TOP) {
				unitDto.setUnittype(Unit.UNIT_EDU_SUB);
			}
		} else {
			if (!unitMap.containsKey(unitDto.getParentid())) {
				Unit localUnit = baseUnitService.getUnit(unitDto.getParentid());
				if (localUnit == null) {
					outParam.addErrorUnitId(unitDto.getId(), unitDto.getName()
							+ "单位的上级单位不存在，请先注册他的上级单位");
					throw new UnitRegisterException(outParam);
				}
			}
		}
	}

	/**
	 * 根据unionid得到上级单位unionid
	 * 
	 * @param regionCode
	 * @return
	 */
	private static String getUpRegionCode(String regionCode) {
		if (regionCode == null) {
			return null;
		}
		regionCode = regionCode.trim();
		int length = regionCode.length();
		String upRegionCode = null;
		switch (length) {
		case 2:
			upRegionCode = "00";
		case 4:
			upRegionCode = regionCode.substring(0, length - 2);
			break;
		case 6:
			upRegionCode = regionCode.substring(0, length - 2);
			break;
		case 9:
			upRegionCode = regionCode.substring(0, length - 3);
			break;
		case 12:
			upRegionCode = getEduBySchool(regionCode);
			break;
		default:
			upRegionCode = regionCode;
		}
		return upRegionCode;
	}

	/**
	 * 根据学校unionid得到该学校上级单位unionid
	 * 
	 * @param unionId
	 * @return
	 */
	private static String getEduBySchool(String unionId) {
		if (unionId == null || unionId.trim().length() != 12) {
			return unionId;
		}
		String[] str = new String[4];
		str[0] = unionId.substring(0, 2);
		str[1] = unionId.substring(2, 4);
		str[2] = unionId.substring(4, 6);
		str[3] = unionId.substring(6, 9);
		StringBuffer sb;
		for (int i = str.length - 1; i >= 0; i--) {
			for (int j = 0; j < str[i].length(); j++) {
				if (str[i].charAt(j) != "0".charAt(0)) {
					sb = new StringBuffer();
					for (int k = 0; k <= i; k++) {
						sb.append(str[k]);
					}
					return sb.toString();
				}
			}
		}
		return "00";
	}

	/**
	 * 检查用户名称在远程服务端是否存在，本人不算在内
	 * 
	 * @param userDto
	 * @param userMap
	 * @param userNamesCountMap
	 * @return
	 */
	private void checkUserNameExists(User userDto, Map<String, User> userMap,
			Map<String, Integer> userNamesCountMap) {
		if (userDto == null || userMap == null || userNamesCountMap == null) {
			return;
		}

		UnitRegisterResultDto outParam = new UnitRegisterResultDto();

		int bound = 0;
		// 如果远程已包含该用户,计数标准为1
		if (userMap.containsKey(userDto.getId())) {
			User remUser = userMap.get(userDto.getId());
			if (remUser.getName().equals(userDto.getName())) {
				bound = 1;
			}
		}

		if (userNamesCountMap.containsKey(userDto.getName())) {
			if (userNamesCountMap.get(userDto.getName()) > bound) {
				String adviceUserName = baseUserService
						.getAdviceUserName4Register(userDto.getName());

				outParam.addErrorUnitId(userDto.getUnitid(), "该单位管理员账号["
						+ userDto.getName()
						+ "]在远程服务端已存在，请在本地修改后再进行注册，建议您可以使用[" + adviceUserName
						+ "]作为该管理员账号");
				throw new UnitRegisterException(outParam);
			}
		}
	}

	/**
	 * 更新用户unitintid字段
	 * 
	 * @param unitList
	 * @param userList
	 */
	private void updateUserUnitIntId(List<BaseUnit> unitList, List<User> userList) {
		if (CollectionUtils.isEmpty(unitList)
				|| CollectionUtils.isEmpty(userList)) {
			return;
		}
		Map<String, BaseUnit> unitMap = new HashMap<String, BaseUnit>();
		for (BaseUnit unit : unitList) {
			unitMap.put(unit.getId(), unit);
		}

		for (User user : userList) {
			if (unitMap.containsKey(user.getUnitid())) {
				//user.setUnitintid(unitMap.get(user.getUnitid()).getUnitintid());
			}
		}
	}

	/**
	 * 如果是修改注册信息，检查单位信息是否有修改，如没有修改则将状态置回正常，其余仍置为未审核 检查的单位信息仅包括单位名称、排序编号、用户登录名
	 * 
	 * @param localUnitDto
	 * @param remoteUnitDto
	 * @param localUserDto
	 * @param remoteUser
	 */
	private void checkUnitModify(BaseUnit localUnitDto, BaseUnit remoteUnitDto,
			User localUserDto, User remoteUser) {
		if (localUnitDto == null || remoteUnitDto == null
				|| localUserDto == null || remoteUser == null) {
			return;
		}

		if (!localUnitDto.getName().equals(remoteUnitDto.getName())) {
			return;
		} else if (!(localUnitDto.getOrderid() == remoteUnitDto.getOrderid())) {
			return;
		} else if (!localUserDto.getName().equals(remoteUser.getName())) {
			return;
		}

		localUnitDto.setMark(remoteUnitDto.getMark());
	}

	/**
	 * 保存单位信息，如果是新增，则根据远程数据库重新生成unionid
	 * 
	 * @param unitList
	 * @param unitMap
	 * @param remoteUnitMap
	 */
	private void saveOrUpdateRegisterUnit(List<BaseUnit> unitList,
			Map<String, BaseUnit> unitMap, Map<String, BaseUnit> remoteUnitMap,
			UnitRegisterResultDto resultDto) {
		if (CollectionUtils.isEmpty(unitList)) {
			return;
		}

		if (resultDto == null) {
			resultDto = new UnitRegisterResultDto();
		}

		Set<String> unionIdSet = new HashSet<String>();
		for (BaseUnit unit : unitList) {
			unionIdSet.add(unit.getUnionid());
		}
		Map<String, Integer> unionMap = baseUnitService
				.getCountsByUnionIds(unionIdSet.toArray(new String[] {}));

		BaseUnit parentUnit, remoteUnitDto;
		String unionId;
		for (BaseUnit unit : unitList) {
			Unit u = baseUnitService.getUnit(unit.getId());

			if (!remoteUnitMap.containsKey(unit.getId())) {
				// 是学校单位或非教育局单位和乡镇教育局时才可能重复
				// 原来是独立学校，注册上来后就eiss中小学.
				if (((unit.getUnittype() == Unit.UNIT_NOTEDU_NOTSCH || unit
						.getRegionlevel() == Unit.UNIT_REGION_LEVEL) && unit
						.getUnitclass() == Unit.UNIT_CLASS_EDU)
						|| unit.getUnitclass() == Unit.UNIT_CLASS_SCHOOL) {
					if (unionMap.containsKey(unit.getUnionid())) { // 包含表示重复，重新生成
						parentUnit = unitMap.get(unit.getParentid());
						if (parentUnit == null) {
							parentUnit = remoteUnitMap.get(unit.getParentid());
						}
						if (unit.getUnittype() == Unit.UNIT_NOTEDU_NOTSCH) {
							unionId = baseUnitService
									.createSpecialUnionid(parentUnit.getId());
						} else {
							unionId = baseUnitService.createUnionid(parentUnit
									.getId(), unit.getUnitclass());
						}
						resultDto.addUpdateUnionIdUnit(new String[] { unionId,
								unit.getId() });
						unit.setUnionid(unionId);
					}
				}

				baseUnitService.insertUnit(unit);
			} else {
				remoteUnitDto = remoteUnitMap.get(unit.getId());
				unit.setUnionid(remoteUnitDto.getUnionid());
				unit.setSrcParentId(u.getParentid());//用于刷新缓存
				baseUnitService.updateUnit(unit);
			}
		}
	}

	public UnitRegisterResultDto saveRemoteUnitRegister2Prv(BaseUnit[] unitObjs)
			throws UnitRegisterException {

		/**
		 * 直接调用省平台的接口，本地不做实现
		 */
		return null;
	}
}
