package net.zdsoft.eis.base.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.cache.BaseCacheConstants;
import net.zdsoft.eis.base.common.dao.RegionDao;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.base.common.service.RegionService;
import net.zdsoft.eis.frame.cache.DefaultCacheManager;

import org.apache.commons.lang.StringUtils;

public class RegionServiceImpl extends DefaultCacheManager implements RegionService {

    private RegionDao regionDao;

    public void setRegionDao(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    // -------------------缓存信息 begin------------------------
    @Override
    public boolean isUseCache() {
        return true;
    }
    
	public List<Region> getRegionsByRegionName (final String regionName){
		return getRegionsByRegionName(regionName, Region.TYPE_BUSINESS);
	}
    
    public Region getRegion(final String code) {
        return getRegion(code, Region.TYPE_BUSINESS);
    }

    public Region getRegionByFullCode(final String fullCode) {
    	return getRegionByFullCode(fullCode, Region.TYPE_BUSINESS);
    }

    public List<Region> getSubRegionsBy2() {
        return getSubRegionsBy2(Region.TYPE_BUSINESS);
    }

    public List<Region> getSubRegionsBy4(final String code) {
    	return getSubRegionsBy4(code, Region.TYPE_BUSINESS);
    }

	@Override
	public String getFullName(String code) {
		return getFullName(code, Region.TYPE_BUSINESS);
	}

	@Override
	public String getFullNameByFullCode(String fullCode) {
		return getFullNameByFullCode(fullCode, Region.TYPE_BUSINESS);
	}

	@Override
	public String getRegionName(String code) {
		return getRegionName(code, Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubRegions(String regionCode) {
		return getSubRegions(regionCode, Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubRegionsByFullCode(String fullCode) {
		return getSubRegionsByFullCode(fullCode, Region.TYPE_BUSINESS);
	}

	@Override
	public List<String[]> getRegionCodeNames() {
		return getRegionCodeNames(Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getRegions() {
		return getRegions(Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubRegionsBy6(String code) {
		return getSubRegionsBy6(code, Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubUnitRegionsBy2() {
		return getSubUnitRegionsBy2(Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubUnitRegionsBy4(String code) {
		return getSubUnitRegionsBy4(code, Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getSubUnitRegionsBy6(String code) {
		return getSubUnitRegionsBy6(code, Region.TYPE_BUSINESS);
	}

	@Override
	public List<Region> getRegionListByCodes(String[] regionCodes) {
		return getRegionListByCodes(regionCodes, Region.TYPE_BUSINESS);
	}

	@Override
	public Map<String, String> getRegionMap() {
		return getRegionMap(Region.TYPE_BUSINESS);
	}

	@Override
	public Map<String, String> getRegionFullCodeMap() {
		return getRegionFullCodeMap(Region.TYPE_BUSINESS);
	}

	@Override
	public Map<String, String> getRegionNameMap() {
		return getRegionNameMap(Region.TYPE_BUSINESS);
	}

	
    //==============================未指定行政区划类型====================================
	
	@Override
	public List<Region> getRegionsByRegionName(final String regionName, final String type) {
		return getEntitiesFromCache(new CacheEntitiesParam<Region>() {

			@Override
			public List<Region> fetchObjects() {
				return regionDao.getRegionsByRegionName(regionName, type);
			}

			@Override
			public String fetchKey() {
				return BaseCacheConstants.EIS_REGION_NAME + type + "_" + regionName;
			}
		});
	}

	@Override
	public Region getRegion(final String code, final String type) {
		return getEntityFromCache(new CacheEntityParam<Region>() {
            public Region fetchObject() {
                return regionDao.getRegion(code, type);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_REGION_CODE + type + "_" + code;
            }
        });
	}

	@Override
	public Region getRegionByFullCode(final String fullCode, final String type) {
		return getEntityFromCache(new CacheEntityParam<Region>() {
            public Region fetchObject() {
                return regionDao.getRegionByFullCode(fullCode, type);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_REGION_FULLCODE + type + "_" + fullCode;
            }
        });
	}

	@Override
	public List<Region> getSubRegionsBy2(final String type) {
		return getEntitiesFromCache(new CacheEntitiesParam<Region>() {

            public List<Region> fetchObjects() {
                return regionDao.getSubRegionsBy2(type);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_REGION_LIST_LEVEL_CODE + type;
            }
        });
	}

	@Override
	public List<Region> getSubRegionsBy4(final String code, final String type) {
		 return getEntitiesFromCache(new CacheEntitiesParam<Region>() {

	            public List<Region> fetchObjects() {
	                return regionDao.getSubRegionsBy4(code, type);
	            }

	            public String fetchKey() {
	                return BaseCacheConstants.EIS_REGION_LIST_LEVEL_CODE + type + "_" + code;
	            }
	        });
	}

	public List<Region> getSubRegionsBy6(final String code, final String type) {
        return getEntitiesFromCache(new CacheEntitiesParam<Region>() {

            public List<Region> fetchObjects() {
                return regionDao.getSubRegionsBy6(code, type);
            }

            public String fetchKey() {
                return BaseCacheConstants.EIS_REGION_LIST_LEVEL_CODE + type + "_" + code;
            }
        });
    }

    // -------------------缓存信息 end--------------------------

    public String getFullName(String code, String type) {
        Region region = getRegion(code, type);
        if (null == region) {
            return null;
        } else {
            return region.getFullName();
        }
    }

    public String getFullNameByFullCode(String fullCode, String type) {
        Region region = getRegionByFullCode(fullCode, type);
        if (null == region) {
            return null;
        } else {
            return region.getFullName();
        }
    }

    /**
     * 去除名称中的“（*）”符号
     * 
     * @param region 行政区对象
     * @return Region
     */
    private Region moveSign(Region region) {
        String name = region.getRegionName();

        if (name.indexOf("＊") >= 0) {
            name = name.replaceAll("＊", "");
        }
        if (name.indexOf("（") >= 0) {
            name = name.replaceAll("（", "");
        }
        if (name.indexOf("）") >= 0) {
            name = name.replaceAll("）", "");
        }

        if (name.indexOf("*") >= 0) {
            name = name.replaceAll("*", "");
        }
        if (name.indexOf("(") >= 0) {
            name = name.replaceAll("(", "");
        }
        if (name.indexOf(")") >= 0) {
            name = name.replaceAll(")", "");
        }

        region.setRegionName(name);

        return region;
    }

    public String getRegionName(String code, String type) {
        Region region = getRegion(code, type);
        if (region == null)
            return "";
        region = this.moveSign(region);

        return region.getRegionName();
    }

    public List<Region> getSubRegions(String regionCode, String type) {
        if (regionCode == null) {
            return new ArrayList<Region>();
        }
        String code = regionCode.trim();
        List<Region> resultList = new ArrayList<Region>();
        if (code.length() == 0) {
            resultList = getSubRegionsBy2(type);
        } else if (code.length() == 2) {
            resultList = getSubRegionsBy4(code, type);
        } else if (code.length() == 4) {
            resultList = getSubRegionsBy6(code, type);
        }
        return resultList;
    }

    public List<String[]> getRegionCodeNames(String type) {
        List<Region> list = getRegions(type);

        List<String[]> rtnList = new ArrayList<String[]>();
        for (int i = 0; i < list.size(); i++) {
            Region region = (Region) list.get(i);
            rtnList.add(new String[] { region.getFullCode(), region.getFullName() });
        }
        return rtnList;
    }

    public List<Region> getRegions(String type) {
        return regionDao.getRegions(type);
    }

    public List<Region> getSubRegionsByFullCode(String fullCode, String type) {
        if (StringUtils.isBlank(fullCode)) {
            return new ArrayList<Region>();
        }
        String orinalCode = fullCode;
        List<Region> listOfRegion;
        if (fullCode.length() == 2) {
            fullCode = fullCode + "__00";
        } else if (fullCode.length() == 4) {
            fullCode = fullCode + "__";
        } else if (fullCode.length() == 6) {
            String firstSection = fullCode.substring(0, 2);
            String secondSection = fullCode.substring(2, 4);
            String thirdSection = fullCode.substring(4, 6);

            if (secondSection.equals("00")) {
                fullCode = firstSection + "__00";
            } else if (thirdSection.equals("00")) {
                fullCode = firstSection + secondSection + "__";
            }

        } else {
            return new ArrayList<Region>();
        }
        listOfRegion = regionDao.getSubRegions(fullCode, orinalCode, type);
        return listOfRegion;
    }

    public List<Region> getSubUnitRegionsBy2(String type) {
        return regionDao.getSubUnitRegionsBy2(type);
    }

    public List<Region> getSubUnitRegionsBy4(String code, String type) {
        return regionDao.getSubUnitRegionsBy4(code, type);
    }

    public List<Region> getSubUnitRegionsBy6(String code, String type) {
        return regionDao.getSubUnitRegionsBy6(code, type);
    }

    public List<Region> getRegionListByCodes(String[] regionCodes, String type) {
        return regionDao.getRegionListByCodes(regionCodes, type);
    }

    public Map<String, String> getRegionMap(String type) {
        return regionDao.getRegionMap(type);
    }

    public Map<String, String> getRegionFullCodeMap(String type){
        return regionDao.getRegionFullCodeMap(type);
    }
    
    public Map<String, String> getRegionNameMap(String type) {
        return regionDao.getRegionNameMap(type);
    }

	@Override
	public Region getRegionByFullName(String fullName, String type) {
		// TODO Auto-generated method stub
		return regionDao.getRegionByFullName(fullName,type);
	}
}
