package net.zdsoft.eis.system.frame.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import net.zdsoft.eis.system.frame.dao.FavoriteDao;
import net.zdsoft.eis.system.frame.entity.Favorite;
import net.zdsoft.eis.system.frame.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao;

    public void setFavoriteDao(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    public void insertFavorite(Favorite favorite){
        favoriteDao.insertFavorite(favorite);
    }
    
    public void updateFavorite(Favorite favorite) {
        favoriteDao.updateFavorite(favorite);
    }

    public void deleteFavorite(String id) {
        Favorite fav = favoriteDao.getFavorite(id);
        List<Favorite> list = favoriteDao.getFavoritesByParentId(id, fav.getUserId());
        while (!CollectionUtils.isEmpty(list)) {
            for (Favorite f : list) {
                deleteFavorite(f.getId());
            }
            list = favoriteDao.getFavoritesByParentId(id, fav.getUserId());
        }
        favoriteDao.deleteFavoriteByParentId(id);
        favoriteDao.deleteFavorite(id);
    }

    public int getMaxOrderNumByParent(String parentId, String userId) {
        return favoriteDao.getMaxOrder(parentId, userId);
    }

    public boolean isExistsName(String id, String parentId, String userId, int subsystem,
            String type, String moduleName) {
        return favoriteDao.isExistsName(id, parentId, userId, subsystem, type, moduleName);
    }

    public Favorite getFavorite(String id) {
        return favoriteDao.getFavorite(id);
    }

    public Favorite getFavorite(String parentId, String userId, Integer subSystem, Integer type,
            String name) {
        return favoriteDao.getFavorite(parentId, userId, subSystem, type, name);
    }

    public List<Favorite> getFavorites(String userId) {
        return favoriteDao.getFavorites(userId);
    }

    public List<Favorite> getFavorites(String userId, int subSystemId) {
        List<Favorite> listOfFav = favoriteDao.getFavorites(userId, subSystemId);
        List<Favorite> resultList = new ArrayList<Favorite>();
        for (Favorite f : listOfFav) {
            if (f.getType() == 1) {
                resultList.add(f);
            }
        }

        return resultList;
    }

    public List<Favorite> getFavoritesByParentId(String parentId, String userId, Integer subSystem) {
        return favoriteDao.getFavoritesByParentId(parentId, userId, subSystem);
    }

    public List<Favorite> getFavoritesByParentId(String parentId, String userId) {
        return favoriteDao.getFavoritesByParentId(parentId, userId);
    }

    public List<Favorite> getFavoriteDirsByParentId(String parentId, String userId) {
        if (parentId == null) {
            return favoriteDao.getFavoriteDirsByParentId(userId);
        } else
            return favoriteDao.getFavoriteDirsByParentId(parentId, userId);
    }

}
