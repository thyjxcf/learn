package net.zdsoft.eis.base.common.dao;

import java.util.List;

import net.zdsoft.eis.base.common.entity.BasicClsReqTeaPlan;

public interface BasicClsReqTeaPlanDao {
	public List<BasicClsReqTeaPlan> getBasicClsReqTeaPlanList(String unitid,String acadyear,String semester);
}
