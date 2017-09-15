package net.zdsoft.eis.support.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.support.dao.RecommendSchoolDao;
import net.zdsoft.eis.support.entity.RecommendSchool;
import net.zdsoft.keel.dao.BasicDAO;
import net.zdsoft.keel.dao.MultiRowMapper;
import net.zdsoft.keel.dao.SingleRowMapper;
import net.zdsoft.keel.util.Pagination;

/* 
 * <p>ZDSoft数字校园</p>
 * 
 * @author Cuibz 2007/08/18
 */
public class RecommendSchoolDaoImpl extends BasicDAO implements RecommendSchoolDao {
    private static final String SQL_RECOMMEND_SCHOOL = "select * from base_school "
            + "where region_code like ? order by region_code, school_name";

    private static final String SQL_RECOMMEND_SCHOOL_ALLLIST = "select * from base_school "
            + " order by updatestamp desc where rownum<=@parameter";

    private static final String SQL_RECOMMEND_SCHOOL_QUERYBYId = "select * from base_school "
            + "where id = ?";

    private static final String SQL_RECOMMEND_SCHOOL_BY_NAME = "select * from base_school where school_name like ? ";

    private static final String SQL_RECOMMEND_SCHOOLINFO_BY_NAME = "select * from base_school where school_name = ?";
    private static final String SQL_RECOMMEND_SCHOOL_QUERYALLSCHOOL = "select * from base_school "
            + "order by school_name desc";

    private static final String SQL_RECOMMEND_SCHOOL_UPDATEINFO = "update stusys_school_recommend set school_name=?,"
            + "school_homepage=?,school_blog=?,school_type=?,school_address=?,school_postcode=?,school_phone=?,"
            + "school_fax=?,school_introduction=?,school_regionCode=?,school_picture_url=?,school_email=? where id=?";

    private static final String SQL_RECOMMEND_SCHOOL_TEMPUPDATESCHOOLRECOMMEND = "update stusys_school_recommend "
            + "set school_recommend = 0 where school_recommend = 1";

    private static final String SOL_RECOMMEND_ADDRECOMMENDSVHOOL = "insert into stusys_school_recommend "
            + "(id,school_name,school_homepage,school_blog,school_type,school_address,school_postcode,school_phone,"
            + "school_fax,school_introduction,school_regionCode,school_recommend,school_picture_url,school_recommend_date,school_email) values(?,?,?,?,?,?,?,?,?,?,?,0,?,getDate(),?)";

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getRecommendSchoolList(String schRegionCode) {
        return query(SQL_RECOMMEND_SCHOOL, schRegionCode, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getRecommendSchoolList(String schRegionCode, Pagination page) {
        return query(SQL_RECOMMEND_SCHOOL, schRegionCode, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        }, page);
    }

    private RecommendSchool takeRecommendSchool(ResultSet rs) {
        RecommendSchool recommendSchool = new RecommendSchool();
        try {
            recommendSchool.setSchoolId(rs.getString("id"));
            recommendSchool.setSchoolName(rs.getString("school_name"));
            recommendSchool.setSchoolHomepage(rs.getString("homepage"));
            // recommendSchool.setSchoolBlog("");
            recommendSchool.setSchoolType(rs.getString("school_type"));
            recommendSchool.setSchoolAddress(rs.getString("address"));
            recommendSchool.setSchoolPostcode(rs.getString("postalcode"));
            recommendSchool.setSchoolPhone(rs.getString("link_phone"));
            recommendSchool.setSchoolFax(rs.getString("fax"));
            recommendSchool.setSchoolIntroduction(rs.getString("introduction"));
            recommendSchool.setSchoolRegionCode(rs.getString("region_code"));
            // recommendSchool.setSchoolRecommend(Integer.parseInt(rs
            // .getString("school_recommend")));
            recommendSchool.setSchoolRecommendDate(new Date(rs.getLong("updatestamp")));
            // recommendSchool.setSchoolRecommendDate(rs
            // .getDate("school_recommend_date"));
            // recommendSchool.setSchoolPictureUrl(rs
            // .getString("school_picture_url"));
            recommendSchool.setSchoolEmail(rs.getString("email"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommendSchool;
    }

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getAllRecommendSchoolList(int top) {
        String sql = SQL_RECOMMEND_SCHOOL_ALLLIST.replace("@parameter", String.valueOf(top));
        return query(sql, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public RecommendSchool getRecommendSchoolInfo(String schId) {
        return (RecommendSchool) query(SQL_RECOMMEND_SCHOOL_QUERYBYId, schId,
                new SingleRowMapper() {
                    public Object mapRow(ResultSet rs) throws SQLException {
                        return takeRecommendSchool(rs);
                    }
                });
    }

    public int updateRecommendSchool(Object[] recommendSchool) {
        return update(SQL_RECOMMEND_SCHOOL_UPDATEINFO, recommendSchool);
    }

    public int updateSchoolRecommendState(String schId[]) {
        StringBuffer SQL_RECOMMEND_SCHOOL_UPDATERECOMMEND = new StringBuffer(
                "update stusys_school_recommend set " + "school_recommend=1 where id=?");
        // 此处是在真正修改学校推荐状态之前先将所有的学校的推荐状态修改为0
        update(SQL_RECOMMEND_SCHOOL_TEMPUPDATESCHOOLRECOMMEND);

        if (schId != null && schId.length != 0) {
            // 拼装SQL语句
            for (int i = 1; i < schId.length; i++) {
                SQL_RECOMMEND_SCHOOL_UPDATERECOMMEND.append(" or id=?");
            }
            // 真正将选中的学校修改为推荐状态
            return update(SQL_RECOMMEND_SCHOOL_UPDATERECOMMEND.toString(), schId);
        }
        return 0;
    }

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getAllSchool() {
        return query(SQL_RECOMMEND_SCHOOL_QUERYALLSCHOOL, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getSchools(String name, Pagination page) {
        return query(SQL_RECOMMEND_SCHOOL_BY_NAME, name, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        }, page);
    }

    public int addRecommendSchool(Object[] recommendSchoolInfo) {
        return update(SOL_RECOMMEND_ADDRECOMMENDSVHOOL, recommendSchoolInfo);
    }

    @SuppressWarnings("unchecked")
    public List<RecommendSchool> getSchool(String SchName) {
        return query(SQL_RECOMMEND_SCHOOLINFO_BY_NAME, SchName, new MultiRowMapper() {
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                return takeRecommendSchool(rs);
            }
        });
    }

}
