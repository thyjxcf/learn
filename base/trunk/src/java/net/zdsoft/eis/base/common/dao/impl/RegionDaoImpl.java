package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.zdsoft.eis.base.common.dao.RegionDao;
import net.zdsoft.eis.base.common.entity.Region;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MapRowMapper;

public class RegionDaoImpl extends BaseDao<Region> implements RegionDao {

    private static final String SQL_FIND_REGION_BY_CODE = "SELECT * FROM base_region WHERE type=? and region_code=?";

    private static final String SQL_FIND_REGION_BY_FULLCODE = "SELECT * FROM base_region where type=? and full_code = ?";

    private static final String SQL_FIND_REGIONS = "SELECT * FROM base_region where type=?";

    private static final String SQL_FIND_REGION_BY_CODES = "SELECT * FROM base_region WHERE type=? and region_code IN";

    private static final String SQL_FIND_REGION_BY_LIKE_FULLCODE = "SELECT * FROM base_region WHERE type=? and full_code like ? and full_code not like ?";

    private static final String SQL_FIND_REGION_BY_REGIONNAME = "SELECT * FROM BASE_REGION WHERE type=? and REGION_NAME like ?";
    
    private static final String SQL_FIND_REGION_BY_NAME = "SELECT * FROM base_region where full_name=? and type = ?";
    public Region setField(ResultSet rs) throws SQLException {
        Region region = new Region();
        region.setId(rs.getString("id"));
        region.setFullCode(rs.getString("full_code"));
        region.setFullName(rs.getString("full_name"));
        region.setRegionCode(rs.getString("region_code"));
        region.setRegionName(rs.getString("region_name"));
        region.setComName(rs.getString("com_name"));
        region.setRomeLetter(rs.getString("rome_letter"));
        region.setLetterCode(rs.getString("letter_code"));
        region.setPostalcode(rs.getString("postalcode"));
        region.setType(rs.getString("type"));
        return region;
    }

    public List<Region> getRegionsByRegionName (String regionName, String type){
    	return query(SQL_FIND_REGION_BY_REGIONNAME, new Object[]{type,regionName + "%"}, new MultiRow());
    }
    
    public Region getRegion(String code, String type) {
        return query(SQL_FIND_REGION_BY_CODE, new Object[]{type, code}, new SingleRow());
    }

    public Region getRegionByFullCode(String fullCode, String type) {
        return query(SQL_FIND_REGION_BY_FULLCODE, new Object[]{type, fullCode}, new SingleRow());
    }

    public List<Region> getRegions(String type) {
        return (List<Region>) query(SQL_FIND_REGIONS, type, new MultiRow());
    }

    public List<Region> getSubRegionsBy2(String type) {
        return getSubRegions(null, 2, type);
    }

    public List<Region> getSubRegionsBy4(String code, String type) {
        return getSubRegions(code, 4, type);
    }

    public List<Region> getSubRegionsBy6(String code, String type) {
        return getSubRegions(code, 6, type);
    }

    private List<Region> getSubRegions(String code, int layer, String type) {
        String queryString = "SELECT * FROM base_region Where type=? and ";
        if (layer == 2) {
            queryString += " length(trim(region_code)) = 2 AND region_code <> '00'";
        } else if (layer == 4) {
            queryString += " length(trim(region_code)) = 4 AND substr(region_code, 1, 2) = ?";
        } else if (layer == 6) {
            queryString += " length(trim(region_code)) = 6 AND substr(region_code, 1, 4) = ?";
        }

        queryString += " order by region_code";
        if (StringUtils.isNotBlank(code)) {
            return query(queryString, new Object[] { type, code }, new MultiRow());
        } else {
            return query(queryString, type, new MultiRow());
        }
    }

    private List<Region> getSubUnitRegions(String code, int layer, String type) {

        String hql = " SELECT sr.* FROM base_region sr,base_unit u "
                + " WHERE sr.full_code=u.region_code AND u.unit_class=1 AND sr.type=? and ";

        if (layer == 2) {
            hql = hql
                    + " length(trim(sr.region_code))=2 AND sr.region_code <>'00' ORDER BY sr.region_code";
            return query(hql, type, new MultiRow());
        } else if (layer == 4) {
            hql = hql
                    + " length(trim(sr.region_code))=4 AND substr(sr.region_code,1,2)=? ORDER BY sr.region_code";
            return query(hql, new Object[]{type, code}, new MultiRow());
        } else if (layer == 6) {

            hql = hql
                    + " length(trim(sr.region_code))=6 AND substr(sr.region_code,1,4)=? ORDER BY sr.region_code";
            return query(hql, new Object[]{type, code}, new MultiRow());
        }
        return new ArrayList<Region>();
    }

    public List<Region> getSubUnitRegionsBy2(String type) {
        return this.getSubUnitRegions(null, 2, type);
    }

    public List<Region> getSubUnitRegionsBy4(String code, String type) {
        return this.getSubUnitRegions(code, 4, type);
    }

    public List<Region> getSubUnitRegionsBy6(String code, String type) {
        return this.getSubUnitRegions(code, 6, type);
    }

    public List<Region> getRegionListByCodes(String[] regionCodes, String type) {
        return queryForInSQL(SQL_FIND_REGION_BY_CODES, new Object[]{type}, regionCodes, new MultiRow(),
                " ORDER BY region_code");
    }

    public List<Region> getSubRegions(String fullCode, String excludeFullCode, String type) {
        return query(SQL_FIND_REGION_BY_LIKE_FULLCODE, new String[] { type, fullCode, excludeFullCode },
                new MultiRow());

    }

    public Map<String, String> getRegionMap(String type) {
        return queryForMap(SQL_FIND_REGIONS, new Object[]{type}, new MapRowMapper<String, String>() {

            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("region_code");
            }

            public String mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("full_name");
            }
        });
    }

    public Map<String, String> getRegionFullCodeMap(String type){
        return queryForMap(SQL_FIND_REGIONS, new Object[]{type}, new MapRowMapper<String, String>() {

            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("full_code");
            }

            public String mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("full_name");
            }
        });
    }
    
    public Map<String, String> getRegionNameMap(String type) {
        return queryForMap(SQL_FIND_REGIONS, new Object[]{type}, new MapRowMapper<String, String>() {

            public String mapRowKey(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("full_name");
            }

            public String mapRowValue(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString("full_code");
            }
        });
    }

	@Override
	public Region getRegionByFullName(String fullName, String type) {
		return query(SQL_FIND_REGION_BY_NAME, new Object[]{fullName, type}, new SingleRow());
	}

}
