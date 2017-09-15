package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.McodedetailDao;
import net.zdsoft.eis.base.common.entity.Mcodedetail;
import net.zdsoft.eis.base.common.service.McodedetailService;
import net.zdsoft.eis.base.constant.enumeration.McodeFieldType;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;
import net.zdsoft.keel.util.Pagination;

public class McodedetailServiceImpl extends DefaultCacheManager implements
        McodedetailService {
    private McodedetailDao mcodedetailDao;

    public void setMcodedetailDao(McodedetailDao mcodedetailDao) {
        this.mcodedetailDao = mcodedetailDao;
    }

    // -------------------缓存信息 begin------------------------    
   
    public void initCache(){
        
    }    

    // =================== 从缓存中取数据 =====================

    /**
     * 检索微代码信息
     * 
     * @param mcodeId 微代码类型ID
     * @param id 代码ID
     * @return 微代码信息
     */
    public Mcodedetail getMcodeDetail(final String mcodeId, final String thisId) {
        return getEntityFromCache(new CacheEntityParam<Mcodedetail>() {

            public String fetchKey() {
                return BaseCacheConstants.EIS_MCODEDETAIL_LIST_MCODEIDTHISID + mcodeId + "_"
                        + thisId;
            }

            public Mcodedetail fetchObject() {
                return mcodedetailDao.getMcodeDetail(mcodeId, thisId);
            }
        });
    }
    
    @Override
	public Mcodedetail getMcodeDetail(final String mcodeId, final String thisId,
			final McodeFieldType fieldType, final String fieldValue) {
    	return getEntityFromCache(new CacheEntityParam<Mcodedetail>() {

            public String fetchKey() {
                return BaseCacheConstants.EIS_MCODEDETAIL_LIST_FIELD_MCODEIDTHISID + fieldType.getCode() + "_" 
                		+ fieldValue + "_" + mcodeId + "_" + thisId;
            }

            public Mcodedetail fetchObject() {
                return mcodedetailDao.getMcodeDetail(mcodeId, thisId, fieldType, fieldValue);
            }
        });
	}

	@Override
	public List<Mcodedetail> getMcodeDetails(final String mcodeId,
			final McodeFieldType fieldType, final String fieldValue) {
		return getEntitiesFromCache(new CacheEntitiesParam<Mcodedetail>() {

            public String fetchKey() {
                return BaseCacheConstants.EIS_MCODEDETAIL_LIST_FIELD_MCODEID_ + fieldType.getCode() + "_" 
                		+ fieldValue + "_" +  mcodeId;
            }

            public List<Mcodedetail> fetchObjects() {
                return mcodedetailDao.getMcodeDetails(mcodeId, fieldType, fieldValue);
            }
        });
	}

	/**
     * 检索有效的微代码集合
     * 
     * @param mcodeId 微代码类型ID
     * @return 有效的微代码集合
     */
    public List<Mcodedetail> getMcodeDetails(final String mcodeId) {
        return getEntitiesFromCache(new CacheEntitiesParam<Mcodedetail>() {

            public String fetchKey() {
                return BaseCacheConstants.EIS_MCODEDETAIL_LIST_MCODEID_ + mcodeId;
            }

            public List<Mcodedetail> fetchObjects() {
                return mcodedetailDao.getMcodeDetails(mcodeId);
            }
        });
    }

    /**
     * 检索所有的微代码集合，包含有效的和无效的
     * 
     * @param mcodeId 微代码类型ID
     * @return 所有的微代码集合
     */
    public List<Mcodedetail> getAllMcodeDetails(final String mcodeId) {
        return getEntitiesFromCache(new CacheEntitiesParam<Mcodedetail>() {

            public String fetchKey() {
                return BaseCacheConstants.EIS_MCODEDETAILALL_LIST_MCODEID_ + mcodeId;
            }

            public List<Mcodedetail> fetchObjects() {
                return mcodedetailDao.getAllMcodeDetails(mcodeId);
            }
        });
    }
    
    // -------------------缓存信息 end------------------------

    public List<Mcodedetail> getMcodeDetails(String mcodeId, String[] thisIds) {
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        List<Mcodedetail> mcodeList = getMcodeDetails(mcodeId);
        Map<String, Mcodedetail> mcodeMap = new LinkedHashMap<String, Mcodedetail>();
        for (Mcodedetail m : mcodeList) {
            mcodeMap.put(m.getThisId(), m);
        }
        for (String thisId : thisIds) {
            Mcodedetail m = mcodeMap.get(thisId);
            if (m != null) {
                mcodeMap.remove(thisId);
            }
        }
        list.addAll(mcodeMap.values());
        return list;
    }
    
    public List<Mcodedetail> getMcodeDetails(String mcodeId, String name, String[] thisIds) {
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        List<Mcodedetail> mcodeList = mcodedetailDao.getMcodeDetails(mcodeId, name);
        Map<String, Mcodedetail> mcodeMap = new LinkedHashMap<String, Mcodedetail>();
        for (Mcodedetail m : mcodeList) {
            mcodeMap.put(m.getThisId(), m);
        }
        for (String thisId : thisIds) {
            Mcodedetail m = mcodeMap.get(thisId);
            if (m != null) {
                mcodeMap.remove(thisId);
            }
        }
        list.addAll(mcodeMap.values());
        return list;
    }

    public List<Mcodedetail> getMcodeDetailFaintness(String mcodeId, String thisId) {
        return mcodedetailDao.getMcodeDetailFaintness(mcodeId, thisId);
    }

    public Map<String, List<Map<String, String>>> getContentListMap(String[] mcodeIds) {
        Map<String, List<Map<String, String>>> map = new HashMap<String, List<Map<String, String>>>();
        Map<String, String> detaiMap = new HashMap<String, String>();
        Map<String, String> detaiMap2 = new HashMap<String, String>();
        List<Map<String, String>> detailList = new ArrayList<Map<String, String>>();

        // 取微代码列表
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        for (String mcodeId : mcodeIds) {
            list.addAll(getMcodeDetails(mcodeId));
        }

        String preMcodeId = "";// 初始化

        if (list.size() > 0) {
            Mcodedetail detail = (Mcodedetail) list.get(0);
            preMcodeId = detail.getMcodeId();

            for (Object object : list) {
                detail = (Mcodedetail) object;
                String mcodeId = detail.getMcodeId();

                if (preMcodeId.equals(mcodeId)) {
                    detaiMap.put(detail.getThisId(), detail.getContent());
                    detaiMap2.put(detail.getContent(), detail.getThisId());
                } else {
                    detailList.add(detaiMap);
                    detailList.add(detaiMap2);
                    map.put(preMcodeId, detailList);

                    detaiMap = new HashMap<String, String>();
                    detaiMap2 = new HashMap<String, String>();
                    detaiMap.put(detail.getThisId(), detail.getContent());
                    detaiMap2.put(detail.getContent(), detail.getThisId());
                    detailList = new ArrayList<Map<String, String>>();
                }
                preMcodeId = mcodeId;
            }
            // 最后一项
            detailList.add(detaiMap);
            detailList.add(detaiMap2);
            map.put(preMcodeId, detailList);
        }
        return map;
    }

    public Map<String, Map<String, String>> getContent2Map(String[] mcodeIds) {
        Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        Map<String, String> detaiMap = new HashMap<String, String>();

        // 取微代码列表
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        for (String mcodeId : mcodeIds) {
            list.addAll(getMcodeDetails(mcodeId));
        }
        if (list.size() > 0) {
            String preMcodeId = "";// 初始化
            Mcodedetail detail = (Mcodedetail) list.get(0);
            preMcodeId = detail.getMcodeId();

            for (Object object : list) {
                detail = (Mcodedetail) object;
                String mcodeId = detail.getMcodeId();

                if (preMcodeId.equals(mcodeId)) {
                    detaiMap.put(detail.getThisId(), detail.getContent());
                } else {
                    map.put(preMcodeId, detaiMap);
                    detaiMap = new HashMap<String, String>();
                    detaiMap.put(detail.getThisId(), detail.getContent());
                }
                preMcodeId = mcodeId;
            }
            // 最后一项
            map.put(preMcodeId, detaiMap);
        }
        return map;
    }

    public Map<String, String> getTotalContents(String[] mcodeIds) {
        Map<String, String> map = new HashMap<String, String>();
        String contents = "";
        // 取微代码列表
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        for (String mcodeId : mcodeIds) {
            list.addAll(getMcodeDetails(mcodeId));
        }
        if (list.size() > 0) {
            String preMcodeId = "";// 初始化
            Mcodedetail detail = (Mcodedetail) list.get(0);
            preMcodeId = detail.getMcodeId();

            for (Object object : list) {
                detail = (Mcodedetail) object;
                String mcodeId = detail.getMcodeId();

                if (preMcodeId.equals(mcodeId)) {
                    contents += " " + detail.getContent();
                } else {
                    map.put(preMcodeId, contents);
                    contents = "";
                    contents += detail.getContent();
                }
                preMcodeId = mcodeId;
            }
            // 最后一项
            map.put(preMcodeId, contents);
        }
        return map;
    }

    public Map<String, Mcodedetail> getMcodeDetailMap(String mcodeId) {
        return mcodedetailDao.getMcodeDetailMap(mcodeId);
    }

    public Map<String, String> getContentMap(String[] mcodeIds) {
        Map<String, String> detaiMap = new HashMap<String, String>();
        // 取微代码列表
        List<Mcodedetail> list = new ArrayList<Mcodedetail>();
        for (String mcodeId : mcodeIds) {
            list.addAll(getMcodeDetails(mcodeId));
        }
        for (Object object : list) {
            Mcodedetail detail = (Mcodedetail) object;
            detaiMap.put(detail.getMcodeId() + detail.getThisId(), detail.getContent());
        }
        return detaiMap;
    }

	@Override
	public List<Mcodedetail> getAllMcodeDetailsByPage(String mcodeId,
			Pagination page) {
		return mcodedetailDao.getAllMcodeDetailsByPage(mcodeId, page);
	}
}
