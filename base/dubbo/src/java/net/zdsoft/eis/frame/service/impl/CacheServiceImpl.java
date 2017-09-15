package net.zdsoft.eis.frame.service.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.dao.McodedetailDao;
import net.zdsoft.eis.base.common.dao.RegionDao;
import net.zdsoft.eis.base.common.dao.SysOptionDao;
import net.zdsoft.eis.base.common.dao.SystemIniDao;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.entity.SysOption;
import net.zdsoft.eis.base.common.entity.SystemIni;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.eis.frame.service.CacheService;
import net.zdsoft.eis.frame.util.RedisUtils;
import net.zdsoft.leadin.util.ConfigFileUtils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class CacheServiceImpl extends DefaultCacheManager implements
		CacheService {

	private McodedetailDao mcodedetailDao;

	private SysOptionDao sysOptionDao;

	public void setSysOptionDao(SysOptionDao sysOptionDao) {
		this.sysOptionDao = sysOptionDao;
	}

	private SystemIniDao systemIniDao;

	public void setSystemIniDao(SystemIniDao systemIniDao) {
		this.systemIniDao = systemIniDao;
	}

	public void setMcodedetailDao(McodedetailDao mcodedetailDao) {
		this.mcodedetailDao = mcodedetailDao;
	}

	private RegionDao regionDao;

	public void setRegionDao(RegionDao regionDao) {
		this.regionDao = regionDao;
	}

	static Map<String, List<Mcodedetail>> mcodeDetailMap = new HashMap<String, List<Mcodedetail>>();
	static Map<String, Map<String, String>> detailMap = new HashMap<String, Map<String, String>>();
	static Map<String, String> options = new HashMap<String, String>();
	static Map<String, String> systemInis = new HashMap<String, String>();
	static List<Region> regions = new ArrayList<Region>();
	static Element root;

	@Override
	public void initElement() {
		final String fid = "dgstusys_student_remote.xml";
		URL configFileUrl = ConfigFileUtils.getConfigFileUrl(fid);
		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(configFileUrl);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		root = document.getRootElement();
	}

	@Override
	public Element getRoot() {
		return root;
	}

	@Override
	public void initRegion() {
		regions = regionDao.getRegions(Region.TYPE_BUSINESS);
	}

	@Override
	public Map<String, List<Mcodedetail>> getMcodeListMap() {
		return mcodeDetailMap;
	}

	@Override
	public Map<String, Map<String, String>> getMcodeMap() {
		return detailMap;
	}

	@Override
	public void initMcode() {
		String[] mcodeIds = { "DM-JKZK", "DM-COUNTRY", "DM-GATQ", "DM-CAJLX",
				"DM-LSETLX", "DM-FNYHKLX", "DM-XB", "DM-MZ", "DM-ZZMM",
				"DM-DGRXFS", "DM-SBJD", "DM-JDFS", "DM-HKXZ", "DM-SFZJLX",
				"DM-JZZJLX", "DM-SXXJTFS", "DM-GX", "DM-FNYHKLX", "DM-XSLY",
				"DM-XX", "DM-BOOLEAN", "DM-DGRDFS", "DM-JRDGFJYY",
				"DM-JNLJRDGFJ", "DM-DGRHMD" };
		for (String mcodeId : mcodeIds) {
			List<Mcodedetail> mcodeDetails;
			if (!"DM-BOOLEAN".equals(mcodeId) && !"DM-XB".equals(mcodeId)) {
				mcodeDetails = new ArrayList<Mcodedetail>();
				Mcodedetail mcode = new Mcodedetail();
				mcode.setThisId("");
				mcode.setContent("--请选择--");
				mcodeDetails.addAll(mcodedetailDao.getMcodeDetails(mcodeId));
			} else {
				mcodeDetails = mcodedetailDao.getMcodeDetails(mcodeId);
			}
			Map<String, String> map = new HashMap<String, String>();
			for (Mcodedetail m : mcodeDetails) {
				map.put(m.getThisId(), m.getContent());
			}
			detailMap.put(mcodeId, map);
			mcodeDetailMap
					.put(mcodeId, mcodedetailDao.getMcodeDetails(mcodeId));
		}
	}

	@Override
	public List<Mcodedetail> getByMcodeId(String mcodeId) {
		return mcodeDetailMap.get(mcodeId);
	}

	@Override
	public List<Region> getRegions() {
		return regions;
	}

	@Override
	public Map<String, String> getOption() {
		return options;
	}

	@Override
	public Map<String, String> getSystemIni() {
		return systemInis;
	}

	@Override
	public void initOption() {
		List<SysOption> opts = sysOptionDao.getSysOptions();
		for(SysOption option : opts){
			options.put(option.getOptionCode(), option.getNowValue());
		}
	}

	@Override
	public void initSystemIni() {
		List<SystemIni> opts = systemIniDao.getSystemInis(1);
		for(SystemIni option : opts){
			systemInis.put(option.getIniid(), option.getNowValue());
		}
		opts = systemIniDao.getSystemInis(0);
		for(SystemIni option : opts){
			systemInis.put(option.getIniid(), option.getNowValue());
		}
	}
	@Override
	public <T> T get(CacheEntityParam<T> params) {
		String key = params.fetchKey();
		T t = (T)RedisUtils.getObject(key);
		if (t == null) {
			t = params.fetchObject();
			if (t == null) {
				return null;
			} else {
				RedisUtils.setObject(key, t);
			}
		}
		return t;
	}

	@Override
	public void add(String key, Object v) {
		RedisUtils.setObject(key, v);
	}

	@Override
	public void add(String key, Object v, int seconds) {
		RedisUtils.setObject(key, v, seconds);
	}

}
