package net.zdsoft.eis.base.common.dao;

import java.util.List;

public interface RegionMatchDao {
	/**
	 * 根据行政区划代码，取出行政区划匹配信息
	 * @param regionCode
	 * @return
	 */
	List<String> getRegionMatchByCode(String regionCode);
   
}
