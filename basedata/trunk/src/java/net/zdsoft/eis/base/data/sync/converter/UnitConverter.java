/* 
 * @(#)UnitConverter.java    Created on Dec 9, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.sync.converter;

import org.apache.commons.lang.math.NumberUtils;

import com.winupon.syncdata.basedata.entity.son.MqUnit;

import net.zdsoft.eis.base.data.entity.BaseUnit;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 9, 2010 11:07:30 AM $
 */
public class UnitConverter implements SyncObjectConvertable<BaseUnit, MqUnit> {

    public void toEntity(MqUnit m, BaseUnit e) {
        // 置值：自动生成或默认值，在增加方法中置值
        // e.setUnionid(m.getUnionid());
        // e.setMark(m.getMark());
        // e.setUsetype(m.getUsetype());
        // e.setAuthorized(m.getAuthorized());
        // e.setRegcode(m.getRegcode());
        // e.setUnitusetype(m.getUnitusetype());
        // e.setUnitPartitionNum(m.getUnitPartitionNum());

        // 非数据库中字段
        // e.setSrcParentId(m.getSrcParentId());//刷新缓存使用

        e.setName(m.getUnitName());
        e.setUnitclass(m.getUnitClass());
        e.setUnittype(m.getUnitType());
        e.setParentid(m.getParentId());
//        e.setOrderid(NumberUtils.toLong(m.getDisplayOrder()));
        if(org.apache.commons.lang3.StringUtils.isNotBlank(m.getDisplayOrder()))
			 e.setOrderid(NumberUtils.toLong(m.getDisplayOrder().replaceAll("[^0-9]", "")));
        e.setRegion(m.getRegionCode());
        e.setEtohSchoolId(m.getSerialNumber());
        e.setTeacherEnableSms(m.isTeacherSms() ? 1 : 0);
        e.setGuestbookSms(m.isGuestbookSms() ? 1 : 0);
        e.setBalance(m.getBalance());
        e.setFeeType(m.getFeeType());
        e.setSld(m.getSecondLevelDomain());
        e.setPostalcode(m.getPostalcode());
        e.setEmail(m.getEmail());
        e.setFax(m.getFax());
        e.setLinkMan(m.getLinkMan());
        e.setLinkPhone(m.getLinkPhone());
        e.setMobilePhone(m.getMobilePhone());
        e.setHomepage(m.getHomepage());
        e.setAddress(m.getAddress());
        e.setLimitTeacher(m.getLimitTeacher());
        e.setSmsFree(m.isSmsFree() ? 1 : 0);
        e.setId(m.getId());
    }

    public void toMq(BaseUnit e, MqUnit m) {
        m.setUnitName(e.getName());
        m.setUnitClass(e.getUnitclass());
        m.setUnitType(e.getUnittype());
        m.setRegionCode(e.getRegion());
        m.setSerialNumber(e.getEtohSchoolId());
        m.setSecondLevelDomain(e.getSld());
        m.setParentId(e.getParentid());
        m.setDisplayOrder(e.getOrderid()+"");
        m.setPostalcode(e.getPostalcode());
        m.setEmail(e.getEmail());
        m.setFax(e.getFax());
        m.setLinkMan(e.getLinkMan());
        m.setLinkPhone(e.getLinkPhone());
        m.setMobilePhone(e.getMobilePhone());
        m.setHomepage(e.getHomepage());
        m.setAddress(e.getAddress());
        m.setTeacherSms(null == e.getTeacherEnableSms() ? false : Boolean.parseBoolean(String
                .valueOf(e.getTeacherEnableSms().intValue())));
        m.setGuestbookSms(null == e.getGuestbookSms() ? false : Boolean.parseBoolean(String
                .valueOf(e.getGuestbookSms().intValue())));
        m.setBalance(null == e.getBalance() ? 0 : e.getBalance().intValue());
        m.setFeeType(null == e.getFeeType() ? 0 : e.getFeeType());
        m.setLimitTeacher(null == e.getLimitTeacher() ? 0 : e.getLimitTeacher());
        m.setSmsFree(null == e.getSmsFree() ? false : Boolean.parseBoolean(String.valueOf(e
                .getSmsFree().intValue())));
        m.setId(e.getId());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("m", "e", BaseUnit.class, true);
    }
}
