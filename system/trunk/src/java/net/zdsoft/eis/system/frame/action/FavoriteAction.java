package net.zdsoft.eis.system.frame.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.opensymphony.xwork2.ModelDriven;

import net.zdsoft.eis.base.common.service.ServerService;
import net.zdsoft.eis.base.common.service.SubSystemService;
import net.zdsoft.eis.base.constant.BaseConstant;
import net.zdsoft.eis.frame.action.BaseAction;
import net.zdsoft.eis.frame.client.LoginInfo;
import net.zdsoft.eis.frame.dto.PromptMessageDto;
import net.zdsoft.eis.system.frame.entity.Favorite;
import net.zdsoft.eis.system.frame.service.FavoriteService;

public class FavoriteAction extends BaseAction implements ModelDriven<Object> {
    private static final long serialVersionUID = 1L;

    private FavoriteService favoriteService;
    private SubSystemService subSystemService;
    private ServerService serverService;

    private static final int OPERATION_TYPE_ADD = 4; // 增加模块
    @SuppressWarnings("unused")
    private static final int OPERATION_TYPE_PB = 0; // 教务管理系统
    private static final int OPERATION_TYPE_FOLDER = 2; // 增加目录
    private static final int OPERATION_TYPE_URL = 3; // 增加URL
    private static final int OPERATION_TYPE_JAVA = 1; // 其他模块,需要直接调用url的
    private static final int LIST_TYPE_ADMIN = 1; // 返回的列表是处于管理中

    private String sectionName;
    private String mName;
    private boolean displayFavorite;
    private String nowId; // 当前选中的目录id
    private String mParentId;
    private static final String DEFAULT_ID = BaseConstant.ZERO_GUID;
    private List<Favorite> favoriteList;
    private List<Favorite> favoriteFloderList;
    private Favorite favorite = new Favorite();
    private int operationType;
    private int listType = 0; // 0 只列出显示，不进行管理
    private String favoriteId; // 选中的favorite id

    private String pageIndexType; // 1 表示首页上的初始化

    private String number;

    public String getPageIndexType() {
        return pageIndexType;
    }

    public void setPageIndexType(String indexType) {
        this.pageIndexType = indexType;
    }

    public int getListType() {
        return listType;
    }

    public void setListType(int listType) {
        this.listType = listType;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setOperationType(int operationType) {
        this.operationType = operationType;
    }

    public int getOperationType() {
        return operationType;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public Favorite getFavorite() {
        return favorite;
    }

    public List<Favorite> getFavoriteFloderList() {
        return favoriteFloderList;
    }

    public String getMParentId() {
        return mParentId;
    }

    public boolean getDisplayFavorite() {
        return displayFavorite;
    }

    public List<Favorite> getFavoriteList() {
        return favoriteList;
    }

    public String getMName() {
        return mName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setFavoriteService(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    public String getNowId() {
        if (nowId == null)
            nowId = DEFAULT_ID;
        return nowId;
    }

    public void setNowId(String nowId) {
        this.nowId = nowId;
    }

    public boolean isExistsName(String id, String moduleName, String type, String parentId,
            int subSystem) {
        pageIndexType = "0";
        return favoriteService.isExistsName(id, parentId, getLoginInfo().getUser().getId(),
                subSystem, type, moduleName);
    }

    public String addFavorite() {
        pageIndexType = "0";
        LoginInfo loginInfo = getLoginInfo();
        favoriteFloderList = favoriteService.getFavoriteDirsByParentId(null, loginInfo.getUser()
                .getId());
        // 如果有数据，则显示出来，否则为新增
        if (favoriteId != null && favoriteId.length() > 0) {
            favorite = favoriteService.getFavorite(favoriteId);
            if (favorite != null) {
                operationType = favorite.getType();
                nowId = favorite.getParentId();
            }
        } else {
            favorite.setType(operationType == OPERATION_TYPE_ADD ? OPERATION_TYPE_JAVA
                    : operationType);
            if (nowId == null) {
                nowId = DEFAULT_ID;
            }
            favorite.setOrderNum(favoriteService.getMaxOrderNumByParent(nowId, loginInfo.getUser()
                    .getId()));
        }
        return SUCCESS;
    }

    public String newFavoriteFloder() {
        pageIndexType = "0";
        LoginInfo loginInfo = getLoginInfo();
        favoriteFloderList = favoriteService.getFavoriteDirsByParentId(null, loginInfo.getUser()
                .getId());
        return SUCCESS;
    }

    public String saveFavorite() {
        pageIndexType = "0";
        LoginInfo loginInfo = getLoginInfo();
        String defaultModuleId = "CUSTOM_MODULE_ID";
        if (favorite.getId() != null && favorite.getId().length() == 0)
            favorite.setId(null);

        favorite.setUserId(loginInfo.getUser().getId());
        if (favorite.getType() == null) {
            favorite.setType(OPERATION_TYPE_JAVA);
        }
        if (favorite.getModuleId() == null || favorite.getModuleId().equals("")) {
            favorite.setModuleId(defaultModuleId);
        }
        if (favorite.getPicUrl() == null || favorite.getPicUrl().length() == 0)
            if (favorite.getType() == OPERATION_TYPE_FOLDER)
                favorite.setPicUrl("system/images/folder.bmp");
            else
                favorite.setPicUrl("system/images/sear_icon.gif");

        // 2007-04-10 zhaosf 加入收藏夹时如果存在相同名称且用户选择覆盖则更新原收藏夹内容
        if (operationType == OPERATION_TYPE_ADD || operationType == OPERATION_TYPE_FOLDER
                || operationType == OPERATION_TYPE_URL) {
            Favorite tmp = favoriteService.getFavorite(favorite.getParentId(),
                    favorite.getUserId(), favorite.getSubSystem(), favorite.getType(), favorite
                            .getModuleName());
            if (null != tmp) {
                String id = tmp.getId();
                favorite.setId(id);
            }
        }

        HttpServletRequest request = getRequest();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath() + "/";

        if (OPERATION_TYPE_JAVA == favorite.getType()) {
            favorite.setUrl(getText("eis.subsystem.homepage.postfix") + "?moduleID="
                    + favorite.getModuleId().trim() + "&appId=" + favorite.getSubSystem());
            String code = subSystemService.getSubSystem(favorite.getSubSystem()).getCode();
            favorite.setServerId(serverService.getServerByServerCode(code).getId());
        }

//        String picUrl = favorite.getPicUrl();
//        if (OPERATION_TYPE_JAVA != favorite.getType()&&StringUtils.indexOf(picUrl, request.getScheme() + "://") != 0) {
//            picUrl = basePath + picUrl;
//            favorite.setPicUrl(picUrl);
//        }

        if (favorite.getId() == null) {
            favoriteService.insertFavorite(favorite);
        } else {
            favoriteService.updateFavorite(favorite);
        }
        setPromptMessageDto(new PromptMessageDto());
        if (operationType == OPERATION_TYPE_ADD) {
            promptMessageDto.setPromptMessage("保存成功！");
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.addOperation(new String[] { "关闭", "javascript:window.close();" });
        } else {
            promptMessageDto.setPromptMessage("保存成功！");
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.addOperation(new String[] {
                    "返回",
                    "favorite.action?nowId=" + favorite.getParentId() + "&listType="
                            + LIST_TYPE_ADMIN });
        }
        return PROMPTMSG;
    }

    public String deleteFavorite() {
        favorite = favoriteService.getFavorite(favoriteId);
        setPromptMessageDto(new PromptMessageDto());
        if (favorite == null) {
            promptMessageDto.setPromptMessage("该收藏已经不存在！");
            promptMessageDto.setOperateSuccess(true);
            promptMessageDto.addOperation(new String[] { "返回",
                    "favorite.action?nowId=" + nowId + "&listType=" + LIST_TYPE_ADMIN });
            return PROMPTMSG;
        }
        String parentId = favorite.getParentId();
        favoriteService.deleteFavorite(favoriteId);

        promptMessageDto.setPromptMessage("删除成功！");
        promptMessageDto.setOperateSuccess(true);
        promptMessageDto.addOperation(new String[] { "返回",
                "favorite.action?nowId=" + parentId + "&listType=" + LIST_TYPE_ADMIN });

        return PROMPTMSG;
    }

    public String getAllFavorite() {
        if (pageIndexType == null || "".equals(pageIndexType))
            pageIndexType = "0";
        sectionName = "工具箱";
        mName = "管理收藏夹";
        displayFavorite = false;
        String id;
        LoginInfo loginInfo = getLoginInfo();
        if (nowId != null && nowId.length() > 0) {
            id = nowId;
            if (!DEFAULT_ID.equals(id)) {
                Favorite favorite = favoriteService.getFavorite(id);
                mParentId = favorite.getParentId();
            } else {
                mParentId = id;
            }
        } else {
            id = DEFAULT_ID;
            mParentId = id;
        }
        // 1表示首页上的系统初始化模块
        if (pageIndexType.equals("1")) {
            favoriteList = favoriteService.getFavoritesByParentId(id, "0");
            favoriteFloderList = favoriteService.getFavoriteDirsByParentId(null, "0");
        } else {
            favoriteList = favoriteService.getFavoritesByParentId(id, loginInfo.getUser().getId());
            favoriteFloderList = favoriteService.getFavoriteDirsByParentId(null, loginInfo
                    .getUser().getId());
        }
        int size = favoriteList.size();
        String moduleId;
        Favorite favoriteDto;
        if (size > 0)
            for (int i = size - 1; i >= 0; i--) {
                favoriteDto = favoriteList.get(i);
                moduleId = favoriteDto.getModuleId();
                if (!getLoginInfo().validateAllModel(Integer.parseInt(moduleId))
                        && favoriteDto.getType() == 1) {
                    favoriteList.remove(i);
                }
            }

        if (NumberUtils.isNumber(number) && NumberUtils.toInt(number) > 0
                && favoriteList.size() > NumberUtils.toInt(number)) {
            favoriteList = favoriteList.subList(0, NumberUtils.toInt(number));
        }

        return SUCCESS;
    }

    public String execute() {
        pageIndexType = "0";
        sectionName = "工具箱";
        mName = "管理收藏夹";
        displayFavorite = false;
        String id;
        LoginInfo loginInfo = getLoginInfo();
        if (nowId != null && nowId.length() > 0) {
            id = nowId;
            if (!DEFAULT_ID.equals(id)) {
                Favorite favorite = favoriteService.getFavorite(id);
                mParentId = favorite.getParentId();
            } else {
                mParentId = id;
            }
        } else {
            id = DEFAULT_ID;
            mParentId = id;
        }
        if (appId == 0) {
            favoriteList = favoriteService.getFavoritesByParentId(id, loginInfo.getUser().getId());
        } else {
            favoriteList = favoriteService.getFavoritesByParentId(id, loginInfo.getUser().getId(),
                    appId);
        }
        favoriteFloderList = favoriteService.getFavoriteDirsByParentId(null, loginInfo.getUser()
                .getId());
        return SUCCESS;
    }

    public Object getModel() {
        return favorite;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setSubSystemService(SubSystemService subSystemService) {
        this.subSystemService = subSystemService;
    }

    public void setServerService(ServerService serverService) {
        this.serverService = serverService;
    }

}
