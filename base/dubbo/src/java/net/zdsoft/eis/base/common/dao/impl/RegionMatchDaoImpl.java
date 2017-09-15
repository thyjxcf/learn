package net.zdsoft.eis.base.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import net.zdsoft.eis.base.common.dao.RegionMatchDao;
import net.zdsoft.eis.base.common.entity.RegionMatch;
import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.keel.dao.MultiRowMapper;

public class RegionMatchDaoImpl extends BaseDao<RegionMatch> implements RegionMatchDao {
    private static final String SQL_FIND_REGION_MATCH_BY_REGIONCODE = "SELECT * From SYS_REGION_MATCH WHERE region_code = ?";

    @Override
    public RegionMatch setField(ResultSet rs) throws SQLException {
        RegionMatch match = new RegionMatch();
        match.setId(rs.getString("id"));
        match.setRegionCode(rs.getString("region_code"));
        match.setRegionCodeMatch(rs.getString("region_code_match"));
        return match;
    }

    
    public List<String> getRegionMatchByCode(String regionCode) {
        return query(SQL_FIND_REGION_MATCH_BY_REGIONCODE, regionCode, new MultiRowMapper<String>() {

            @Override
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {                
                return rs.getString("region_code_match");
            }
        });
    }

}
