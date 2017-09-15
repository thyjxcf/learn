/**
 * 
 */
package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqFamily;

import net.zdsoft.eis.base.common.entity.Family;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

/**
 * @author yanb
 * 
 */
public class FamilyConverter implements SyncObjectConvertable<Family, MqFamily> {

    @Override
    public void toEntity(MqFamily m, Family e) {
        e.setRelation(m.getRelation());
        e.setName(m.getRealName());
        e.setCompany(m.getCompany());
        e.setDuty(m.getDuty());
        e.setLinkPhone(m.getLinkPhone());
        e.setWorkCode(m.getWorkCode());
        e.setProfessionCode(m.getProfessionCode());
        e.setDutyLevel(m.getDutyLevel());
        e.setPoliticalStatus(m.getPoliticalStatus());
        e.setMaritalStatus(m.getMaritalStatus());
        e.setEmigrationPlace(m.getEmigrationPlace());
        e.setBirthday(m.getBirthday());
        e.setCulture(m.getCulture());
        e.setStudentId(m.getStudentId());
        e.setSchoolId(m.getSchoolId());
        e.setIdentityCard(m.getIdentityCard());
        e.setNation(m.getNation());
        e.setGuardian(m.isGuardian());
        e.setSex(String.valueOf(m.getSex()));
        e.setRemark(m.getRemark());
        e.setPostalcode(m.getPostalcode());
        e.setLinkAddress(m.getLinkAddress());
        e.setEmail(m.getEmail());
        e.setMobilePhone(m.getMobilePhone());
        e.setOfficeTel(m.getOfficeTel());
        e.setHomepage(m.getHomepage());
        e.setChargeNumber(m.getChargeNumber());
        e.setChargeNumberType(m.getChargeNumberType());
        e.setRegionCode(m.getRegionCode());
        e.setId(m.getId());
        e.setLeaveSchool(m.getLeaveSchool());
    }

    @Override
    public void toMq(Family e, MqFamily m) {
        m.setStudentId(e.getStudentId());
        m.setSchoolId(e.getSchoolId());
        m.setRelation(e.getRelation());
        m.setRealName(e.getName());
        m.setGuardian(e.isGuardian());
        m.setCompany(e.getCompany());
        m.setDuty(e.getDuty());
        m.setLinkPhone(e.getLinkPhone());
        m.setWorkCode(e.getWorkCode());
        m.setProfessionCode(e.getProfessionCode());
        m.setDutyLevel(e.getDutyLevel());
        m.setPoliticalStatus(e.getPoliticalStatus());
        m.setMaritalStatus(e.getMaritalStatus());
        m.setEmigrationPlace(e.getEmigrationPlace());
        m.setBirthday(e.getBirthday());
        m.setCulture(e.getCulture());
        m.setIdentityCard(e.getIdentityCard());
        m.setNation(e.getNation());
        m.setHomepage(e.getHomepage());
        m.setRemark(e.getRemark());
        m.setPostalcode(e.getPostalcode());
        m.setLinkAddress(e.getLinkAddress());
        m.setEmail(e.getEmail());
        m.setMobilePhone(e.getMobilePhone());
        m.setOfficeTel(e.getOfficeTel());
        m.setSex(Integer.parseInt(e.getSex()));
        m.setRegionCode(e.getRegionCode());
        m.setChargeNumber(e.getChargeNumber());
        m.setChargeNumberType(e.getChargeNumberType());
        m.setLeaveSchool(e.getLeaveSchool());
        m.setId(e.getId());
    }

    public static void main(String[] args) {
        net.zdsoft.leadin.tmptool.DtoAssemblerTmp.printSetCode("m", "e", Family.class, true);
    }

}
