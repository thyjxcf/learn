/* 
 * @(#)AffairServiceImpl.java    Created on Dec 30, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.affair;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import net.zdsoft.eis.base.common.entity.SubSystem;
import net.zdsoft.eis.base.common.entity.Unit;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.common.service.UnitService;
import net.zdsoft.keel.util.DateUtils;
import net.zdsoft.leadin.util.Assert;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 30, 2010 2:15:05 PM $
 */
public class AffairServiceImpl implements AffairService {
	private AffairDao affairDao;
	private UnitService unitService;
	private SubSystemService subSystemService;

	public void setUnitService(UnitService unitService) {
		this.unitService = unitService;
	}

	public void setAffairDao(AffairDao affairDao) {
		this.affairDao = affairDao;
	}

	public void setSubSystemService(SubSystemService subSystemService) {
		this.subSystemService = subSystemService;
	}

	public void addAffair(Affair affair) {
		affairDao.addAffair(affair);
	}

	public void updateAffair(Affair affair) {
		affairDao.updateAffair(affair);
	}

	public void saveAffair(Affair affair) {
		Assert.notEmpty(affair.getIdentifier());
		Assert.notEmpty(affair.getReceiverId());
		Assert.notEmpty(affair.getSenderId());

		Affair oldAffair = affairDao.getAffair(affair.getIdentifier(),
				affair.getReceiverId(), affair.getSenderId());
		if (null == oldAffair) {
			addAffair(affair);
		} else {
			affair.setId(oldAffair.getId());
			updateAffair(affair);
		}
	}

	public void updateAffairCompleteSign(Affair affair) {
		Assert.notEmpty(affair.getIdentifier());
		Assert.notEmpty(affair.getReceiverId());

		affairDao.updateAffairCompleteSign(affair);
	}

	public void updateAffairCompleteSign(Transactable transactable) {
		Affair affair = new Affair(transactable.getIdentifier(),
				transactable.getReceiverId(), transactable.isComplete());
		updateAffairCompleteSign(affair);
	}

	public void updateAffairSystemReceiverId() {
		Unit unit = unitService.getTopEdu();
		if (null != unit) {
			affairDao.updateAffairSystemReceiverId(unit.getId());
			return;
		}
	}

	public void deleteAffairs(String[] affairIds) {
		affairDao.deleteAffairs(affairIds);
	}

	public void deleteAffair(Transactable transactable) {
		Assert.notEmpty(transactable.getIdentifier());
		Assert.notEmpty(transactable.getReceiverId());

		affairDao.deleteAffair(transactable.getIdentifier(),
				transactable.getReceiverId());
	}

	public void runAffairJob() {
		ConcurrentMap<String, Transactable> transactObjectMap = AffairHelper
				.getTransactObjectMap();

		// 获取当前时间在时间范围内的事项
		List<Affair> affairs = getAffairsByCurrentTime();
		for (Affair affair : affairs) {
			Transactable transactable = transactObjectMap.get(affair
					.getIdentifier());

			// 根据完成情况更新标志
			affair.setComplete(transactable.isComplete());
			updateAffairCompleteSign(affair);
		}
	}

	public Affair getAffair(String affairId) {
		return affairDao.getAffair(affairId);
	}

	private List<Affair> getAffairsByCurrentTime() {
		int currentDate = Integer.parseInt(DateUtils.date2String(new Date(),
				"MMdd"));
		List<Affair> affairs = new ArrayList<Affair>();
		List<Affair> allAffairs = affairDao
				.getAffairs(Affair.AFFAIR_SOURCE_SYSTEM);
		for (Affair affair : allAffairs) {
			int beginDate = 0;
			int endDate = 0;
			if (StringUtils.isNotBlank(affair.getBeginDate())) {
				beginDate = Integer.parseInt(affair.getBeginDate());
			}
			if (StringUtils.isNotBlank(affair.getEndDate())) {
				endDate = Integer.parseInt(affair.getEndDate());
			}

			if (beginDate != 0 && endDate != 0) {
				// 如果结束日期小于开始日期，则表明是下一年
				if (endDate - beginDate < 0) {
					endDate = endDate + 1200;// 加12个月
				}
				if (currentDate < beginDate || currentDate > endDate) {
					continue;
				}
			} else if (beginDate != 0) {
				if (currentDate < beginDate) {
					continue;
				}
			} else if (endDate != 0) {
				if (currentDate > endDate) {
					continue;
				}
			}
			affairs.add(affair);
		}
		return affairs;
	}

	public List<Affair> getAffairByReceiverId(String receiverId,
			Set<Integer> modSet, Set<String> operSet, int rowNum) {
		List<Affair> affairList = null;
		if (rowNum == 0) {
			affairList = affairDao.getAffairByReceiverId(receiverId);
		} else {
			affairList = affairDao.getTopAffairByReceiverId(receiverId, rowNum);
		}
		List<Affair> retureList = new ArrayList<Affair>();
		Set<String> unitIds = new HashSet<String>();
		for (Affair affair : affairList) {
			if (modSet.contains(affair.getModuleId())) {
				if (StringUtils.isNotBlank(affair.getOperationHeading())) {
					if (operSet.contains(affair.getOperationHeading())) {
						unitIds.add(affair.getSenderId());
						SubSystem subSystem = subSystemService
								.getSubSystem(affair.getSubsystemId());
						affair.setUrl(subSystem.getIndexUrl() + "&moduleID="
								+ affair.getModuleId());
						retureList.add(affair);
					}
				} else {
					unitIds.add(affair.getSenderId());
					SubSystem subSystem = subSystemService.getSubSystem(affair
							.getSubsystemId());
					affair.setUrl(subSystem.getIndexUrl() + "&moduleID="
							+ affair.getModuleId());
					retureList.add(affair);
				}
			}
		}

		return retureList;
	}

	public int getAffairNumByReceiverId(String receiverId, Set<Integer> modSet,
			Set<String> operSet) {
		List<Affair> affairList = affairDao.getAffairByReceiverId(receiverId);
		List<Affair> retureList = new ArrayList<Affair>();
		int count = 0;
		Set<String> unitIds = new HashSet<String>();
		for (Affair affair : affairList) {
			if (modSet.contains(affair.getModuleId())) {
				if (StringUtils.isNotBlank(affair.getOperationHeading())) {
					if (operSet.contains(affair.getOperationHeading())) {
						count++;
						unitIds.add(affair.getSenderId());
						retureList.add(affair);
					}
				} else {
					count++;
					unitIds.add(affair.getSenderId());
					retureList.add(affair);
				}
			}
		}
		return retureList.size();
	}

}
