package net.zdsoft.eis.base.data.sync.converter;

import com.winupon.syncdata.basedata.entity.son.MqDept;

import net.zdsoft.eis.base.common.entity.Dept;
import net.zdsoft.eis.base.sync.SyncObjectConvertable;

public class DeptConverter implements SyncObjectConvertable<Dept, MqDept>  {
	  public void toEntity(MqDept m, Dept e) {
	        // 置值：自动生成或默认值

//			e.setMark(m.getDeptType());
			e.setJymark(m.getDeptType());
			e.setPrincipan(m.getTeacherId());
			e.setOrderid(m.getDisplayOrder());
			e.setAbout(m.getRemark());

			e.setDeptCode(m.getDeptCode());
			e.setUnitId(m.getUnitId());
			e.setId(m.getId());
			e.setParentid(m.getParentId());
			e.setDeptname(m.getDeptName());
			e.setDepttel(m.getDeptTel());
			e.setDefault(m.isDefault());
	    }

	    public void toMq(Dept e, MqDept m) {
	    	m.setDeptCode(e.getDeptCode());
			m.setUnitId(e.getUnitId());
			m.setId(e.getId());
			m.setParentId(e.getParentid());
			m.setDeptName(e.getDeptname());
			m.setDeptTel(e.getDepttel());
	    }

	

}
