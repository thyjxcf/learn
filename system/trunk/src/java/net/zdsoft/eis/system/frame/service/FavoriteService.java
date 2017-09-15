package net.zdsoft.eis.system.frame.service;

import java.util.List;

import net.zdsoft.eis.system.frame.entity.Favorite;

/**
 * @author linqzh
 * @version $Revision: 1.7 $, $Date: 2007/01/30 14:15:52 $
 */
public interface FavoriteService {
    // ==========================维护========================
    /**
     * 增加
     * 
     * @param favorite
     */
    public void insertFavorite(Favorite favorite);
    
    /**
     * 更新favorite
     * 
     * @param favorite
     */
    public void updateFavorite(Favorite favorite);

    /**
     * 跟成用递归删除的方法，将该目录下的所有目录都删除
     * 
     * @param id
     */
    public void deleteFavorite(String id);

    /**
     * 取得最大order
     * 
     * @param parentId
     * @return
     */
    public int getMaxOrderNumByParent(String parentId, String userId);

    /**
     * 是否已经存在相同的model
     * 
     * @param subsystem
     * @param type
     * @param moduleName
     * @return
     */
    public boolean isExistsName(String id, String parentId, String userId, int subsystem,
            String type, String moduleName);

    // ==========================查询单个对象========================
    /**
     * 根据id取得favorite
     * 
     * @return
     */
    public Favorite getFavorite(String id);

    /**
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
     * @param userId
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

    // ==========================按parentId查询多个对象========================
    /**
     * 取出下级模块名
     * 
     * @param parentId
     * @return
     */
    public List<Favorite> getFavoritesByParentId(String parentId, String userId, Integer subSystem);

    /**
     * 取出下级模块名
     * 
     * @param parentId
     * @return
     */
    public List<Favorite> getFavoritesByParentId(String parentId, String userId);

    /**
     * 取得favorite目录
     * 
     * @param parentId
     * @return
     */
    public List<Favorite> getFavoriteDirsByParentId(String parentId, String userId);

}
