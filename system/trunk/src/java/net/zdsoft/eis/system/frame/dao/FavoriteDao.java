package net.zdsoft.eis.system.frame.dao;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.Favorite;

/**
 * @author linqzh
 * @version $Revision: 1.6 $, $Date: 2007/01/30 14:15:52 $
 */
public interface FavoriteDao {
    // ==========================维护========================
    /**
     * 增加
     * 
     * @param favorite
     */
    public void insertFavorite(Favorite favorite);

    /**
     * 删除
     * 
     * @param id
     */
    public void deleteFavorite(String id);

    /**
     * 删除
     * 
     * @param parentId
     */
    public void deleteFavoriteByParentId(String parentId);

    /**
     * 更新favorite
     * 
     * @param favorite
     */
    public void updateFavorite(Favorite favorite);

    /**
     * 取出同一级目录下的最大编号
     * 
     * @param parentId
     * @return
     */
    public int getMaxOrder(String parentId, String userId);

    /**
     * 模块name是否重复
     * 
     * @param id
     * @param parentId
     * @param userId
     * @param subsystem
     * @param type
     * @param moduleName
     * @return
     */
    public boolean isExistsName(String id, String parentId, String userId, int subsystem,
            String type, String moduleName);

    // ==========================查询单个对象========================
    /**
     * 根据favoriteId取得favorite
     * 
     * @return
     */
    public Favorite getFavorite(String favoriteId);

    /**
     * 取收藏信息
     * 
     * @param name
     * @return
     */
    public Favorite getFavorite(String parentId, String userId, Integer subSystem, Integer type,
            String name);

    // ==========================按用户id查询多个对象========================
    /**
     * 根据用户的ID取出收藏夹
     * 
     * @param userIntId
     * @return
     */
    public List<Favorite> getFavorites(String userId);

    /**
     * 根据用户id和子系统id取收藏夹信息
     * 
     * @param userIntId
     * @param subsystemId
     * @return
     */
    public List<Favorite> getFavorites(String userId, int subsystemId);

    /**
     * 取得favorite目录
     * 
     * @param userId
     * @return
     */
    public List<Favorite> getFavoriteDirsByParentId(String userId);

    // ==========================按parentId查询多个对象========================
    /**
     * 取出下级模块名
     * 
     * @param parentId
     * @param userId
     * @return
     */
    public List<Favorite> getFavoritesByParentId(String parentId, String userId);

    /**
     * 取出下级模块名
     * 
     * @param parentId
     * @return
     */
    public List<Favorite> getFavoritesByParentId(String parentId, String userId, Integer subSystem);

    /**
     * 取得favorite目录
     * 
     * @param parentId
     * @return
     */
    public List<Favorite> getFavoriteDirsByParentId(String parentId, String userId);

}
