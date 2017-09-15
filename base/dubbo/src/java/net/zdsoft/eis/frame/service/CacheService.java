package net.zdsoft.eis.frame.service;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.leadin.cache.EntityCacheCall.CacheEntityParam;

import org.dom4j.Element;

public interface CacheService {

	public <T> T get(CacheEntityParam<T> params);

	public void add(String key, Object v);
	
	public void add(String key, Object v,int seconds);

	public List<Mcodedetail> getByMcodeId(String mcodeId);

	public Map<String, List<Mcodedetail>> getMcodeListMap();

	public Map<String, Map<String, String>> getMcodeMap();
	
	public Map<String, String> getOption();
	
	public Map<String, String> getSystemIni();

	public Element getRoot();

	public List<Region> getRegions();

	public void initMcode();

	public void initElement();

	public void initRegion();
	
	public void initOption();
	
	public void initSystemIni();

}
