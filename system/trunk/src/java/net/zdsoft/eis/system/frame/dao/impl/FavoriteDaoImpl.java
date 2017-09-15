package net.zdsoft.eis.system.frame.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import net.zdsoft.eis.frame.client.BaseDao;
import net.zdsoft.eis.system.frame.dao.FavoriteDao;
import net.zdsoft.eis.system.frame.entity.Favorite;
import net.zdsoft.keel.dao.MultiRowMapper;

public class FavoriteDaoImpl extends BaseDao<Favorite> implements FavoriteDao {
    private static final String SQL_INSERT_FAVORITE = "INSERT INTO base_favorite(id,module_id,user_id,"
            + "parent_id,order_num,module_name,module_description,type,pic_url,url,"
            + "sub_system,server_id) " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_FAVORITE_BY_ID = "DELETE base_favorite WHERE id=? ";
    private static final String SQL_DELETE_FAVORITE_BY_PARENTID = "DELETE base_favorite WHERE parent_id=? ";

    private static final String SQL_UPDATE_FAVORITE = "UPDATE base_favorite SET module_id=?,user_id=?,"
            + "parent_id=?,order_num=?,module_name=?,module_description=?,type=?,pic_url=?,url=?,"
            + "sub_system=?,server_id=? WHERE id=?";

    private static final String SQL_FIND_CNT_BY_PARENTID_USERID_SUBSYSTEM_TYPE_NAME = "SELECT max(order_num) as num FROM base_favorite "
            + "WHERE parent_id=? AND user_id=? AND sub_system=? AND type=? AND module_name = ?";
    private static final String SQL_FIND_MAX_ORDERNUM_BY_PARENTID_USERID = "SELECT max(order_num) as num FROM base_favorite "
            + "WHERE parent_id=? AND user_id=?";

    private static final String SQL_FIND_FAVORITE_BY_ID = "SELECT * FROM base_favorite WHERE id=?";

    private static final String SQL_FIND_FAVORITES_BY_USERID = "SELECT * FROM base_favorite "
            + "WHERE user_id=? ";
    private static final String SQL_FIND_FAVORITES_BY_USERID_SUBSYSTEM = "SELECT * FROM base_favorite "
            + "WHERE user_id=? AND sub_system=? ";
    private static final String SQL_FIND_FAVORITES_BY_USERID_TYPE = "SELECT * FROM base_favorite "
            + "WHERE user_id=? AND type=? ORDER BY type ASC,order_num ASC";

    private static final String SQL_FIND_FAVORITES_BY_PARENTID_USERID = "SELECT * FROM base_favorite "
            + "WHERE parent_id=? AND user_id=? ORDER BY type ASC,order_num ASC";
    private static final String SQL_FIND_FAVORITES_BY_PARENTID_USERID_SUBSYSTEM = "SELECT * FROM base_favorite "
            + "WHERE parent_id=? AND user_id=? AND sub_system=? ORDER BY type ASC,order_num ASC";
    private static final String SQL_FIND_FAVORITES_BY_PARENTID_USERID_TYPE = "SELECT * FROM base_favorite "
            + "WHERE parent_id=? AND user_id=? AND type=? ORDER BY type ASC,order_num ASC";
    private static final String SQL_FIND_FAVORITE_BY_PARENTID_USERID_SUBSYSTEM_TYPE_NAME = "SELECT * FROM base_favorite "
            + "WHERE parent_id=? AND user_id=? AND sub_system=? AND type=? AND module_name=? ";

    public Favorite setField(ResultSet rs) throws SQLException {
        Favorite favorite = new Favorite();
        favorite.setId(rs.getString("id"));
        favorite.setModuleId(rs.getString("module_id"));
        favorite.setUserId(rs.getString("user_id"));
        favorite.setParentId(rs.getString("parent_id"));
        favorite.setOrderNum(rs.getInt("order_num"));
        favorite.setModuleName(rs.getString("module_name"));
        favorite.setModuleDescription(rs.getString("module_description"));
        favorite.setType(rs.getInt("type"));
        favorite.setPicUrl(rs.getString("pic_url"));
        favorite.setUrl(rs.getString("url"));
        favorite.setSubSystem(rs.getInt("sub_system"));
        favorite.setServerId(rs.getString("server_id"));
        return favorite;
    }

    public void insertFavorite(Favorite favorite) {
        favorite.setId(createId());
        update(SQL_INSERT_FAVORITE, new Object[] { favorite.getId(), favorite.getModuleId(),
                favorite.getUserId(), favorite.getParentId(), favorite.getOrderNum(),
                favorite.getModuleName(), favorite.getModuleDescription(), favorite.getType(),
                favorite.getPicUrl(), favorite.getUrl(), favorite.getSubSystem(),
                favorite.getServerId() }, new int[] { Types.CHAR, Types.CHAR, Types.CHAR,
                Types.CHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.CHAR });
    }

    public void deleteFavorite(String id) {
        update(SQL_DELETE_FAVORITE_BY_ID, id);
    }

    public void deleteFavoriteByParentId(String parentId) {
        update(SQL_DELETE_FAVORITE_BY_PARENTID, parentId);
    }

    public void updateFavorite(Favorite favorite) {
        update(SQL_UPDATE_FAVORITE, new Object[] { favorite.getModuleId(), favorite.getUserId(),
                favorite.getParentId(), favorite.getOrderNum(), favorite.getModuleName(),
                favorite.getModuleDescription(), favorite.getType(), favorite.getPicUrl(),
                favorite.getUrl(), favorite.getSubSystem(), favorite.getServerId(),
                favorite.getId() }, new int[] { Types.CHAR, Types.CHAR, Types.CHAR, Types.INTEGER,
                Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR,
                Types.INTEGER, Types.CHAR, Types.CHAR });
    }

    public int getMaxOrder(String parentId, String userId) {
        List<Integer> list = query(SQL_FIND_MAX_ORDERNUM_BY_PARENTID_USERID, new Object[] {
                parentId, userId }, new MultiRowMapper<Integer>() {

            public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getInt("num");
            }

        });
        if (list == null || list.isEmpty()) {
            return 1;
        } else {
            if (list.get(0) == null)
                return 1;
            else
                return (Integer) list.get(0) + 1;
        }
    }

    public boolean isExistsName(String id, String parentId, String userId, int subsystem,
            String type, String moduleName) {
        Object[] objs = null;
        String sql = SQL_FIND_CNT_BY_PARENTID_USERID_SUBSYSTEM_TYPE_NAME;
        if (null == id || "".equals(id)) {
            objs = new Object[] { parentId, userId, Integer.valueOf(subsystem),
                    Integer.valueOf(type), moduleName };
        } else {
            objs = new Object[] { parentId, userId, Integer.valueOf(subsystem),
                    Integer.valueOf(type), moduleName, id };
            sql += " and id <> ? ";
        }

        List<Integer> list = query(sql, objs, new MultiRowMapper<Integer>() {

            public Integer mapRow(ResultSet rs, int arg1) throws SQLException {
                return rs.getInt("num");
            }

        });
        if (list == null || list.isEmpty()) {
            return false;
        } else {
            if ((Integer) list.get(0) <= 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    public Favorite getFavorite(String favoriteId) {
        return query(SQL_FIND_FAVORITE_BY_ID, favoriteId, new SingleRow());
    }

    public Favorite getFavorite(String parentId, String userId, Integer subSystem, Integer type,
            String name) {
        return query(SQL_FIND_FAVORITE_BY_PARENTID_USERID_SUBSYSTEM_TYPE_NAME, new Object[] {
                parentId, userId, subSystem, type, name }, new SingleRow());
    }

    public List<Favorite> getFavorites(String userId) {
        return query(SQL_FIND_FAVORITES_BY_USERID, userId, new MultiRow());
    }

    public List<Favorite> getFavorites(String userId, int subsystemId) {
        return query(SQL_FIND_FAVORITES_BY_USERID_SUBSYSTEM, new Object[] { userId, subsystemId },
                new MultiRow());
    }

    public List<Favorite> getFavoritesByParentId(String parentId, String userId) {
        return query(SQL_FIND_FAVORITES_BY_PARENTID_USERID, new Object[] { parentId, userId },
                new MultiRow());
    }

    public List<Favorite> getFavoritesByParentId(String parentId, String userId, Integer subSystem) {
        return query(SQL_FIND_FAVORITES_BY_PARENTID_USERID_SUBSYSTEM, new Object[] { parentId,
                userId, subSystem }, new MultiRow());
    }

    public List<Favorite> getFavoriteDirsByParentId(String parentId, String userId) {
        return query(SQL_FIND_FAVORITES_BY_PARENTID_USERID_TYPE,
                new Object[] { parentId, userId, 2 }, new MultiRow());
    }

    public List<Favorite> getFavoriteDirsByParentId(String userId) {
        return query(SQL_FIND_FAVORITES_BY_USERID_TYPE, new Object[] { userId, 2 }, new MultiRow());
    }

}
