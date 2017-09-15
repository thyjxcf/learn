package net.zdsoft.eis.base.remote.dto;

import net.zdsoft.eis.base.remote.param.dto.OutParamDto;

public class UnitRegisterResultDto extends OutParamDto {

    private String[] selectUnitIds;

    private String[] regSuccUnitIds;
    private String[] regSuccUserIds;

    private String[] modifyUnitIds;
    private String[] modifyUserIds;

    private String[] errorUnitIds;
    private String[] errorUserIds;

    private String[] errorContent;

    private String[] updateUnionIdUnitIds;//编号
    private String[] updateUnionIds;//guid

    /**
	 * 远程注册返回信息标志 对应父类的code
	 */
	public static final int REMOTE_REGISTER_MODIFY = -900000;// 远程单位已存在，需要修改
	public static final int REMOTE_REGISTER_ERROR_OTHERS = -991588;// 单位远程注册时出现其他未可预料的错误
	public static final int REMOTE_REGISTER_ERROR_AUTHRONIZE = -991589;// 单位授权无可用
	public static final int REMOTE_REGISTER_ERROR_EXISTS_UNIONID = -991591;// 单位unionid已存在
	public static final int REMOTE_REGISTER_ERROR_EXISTS_UNITNAME = -991592;// 单位名称已存在
	public static final int REMOTE_REGISTER_ERROR_NULL = -991594;// 非空项验证
	public static final int REMOTE_REGISTER_ERROR_UNIONID = -991595;// 统一编号有误
	public static final int REMOTE_REGISTER_ERROR_PARENTUNIT = -991596;// 上级单位未注册
	public static final int REMOTE_REGISTER_ERROR_REGION = -991597;// 上级单位的区域码不是本平台的有效区域码:
	public static final int REMOTE_REGISTER_ERROR_EXISTS_USERNAME = -991598;// 单位管理员用户名称已存在
	public static final int REMOTE_REGISTER_SUCCESS = 991500;// 单位远程注册成功
	
    public String[] getModifyUnitIds() {
        return modifyUnitIds;
    }

    public String[] getModifyUserIds() {
        return modifyUserIds;
    }

    public String[] getRegSuccUnitIds() {
        return regSuccUnitIds;
    }

    public String[] getRegSuccUserIds() {
        return regSuccUserIds;
    }

    public void setModifyUnitIds(String[] modifyUnitIds) {
        this.modifyUnitIds = modifyUnitIds;
    }

    public void setModifyUserIds(String[] modifyUserIds) {
        this.modifyUserIds = modifyUserIds;
    }

    public void setRegSuccUnitIds(String[] regSuccUnitIds) {
        this.regSuccUnitIds = regSuccUnitIds;
    }

    public void setRegSuccUserIds(String[] regSuccUserIds) {
        this.regSuccUserIds = regSuccUserIds;
    }

    public String[] getErrorUnitIds() {
        return errorUnitIds;
    }

    public String[] getErrorUserIds() {
        return errorUserIds;
    }

    public void setErrorUnitIds(String[] errorUnitIds) {
        this.errorUnitIds = errorUnitIds;
    }

    public void setErrorUserIds(String[] errorUserIds) {
        this.errorUserIds = errorUserIds;
    }

    private String[] addArrayString(String[] preStr, String newStr) {
        if (preStr == null) {
            return newStr == null ? null : new String[] { newStr };
        }
        String[] resStr = new String[preStr.length + 1];
        for (int i = 0; i < preStr.length; i++) {
            resStr[i] = preStr[i];
        }
        resStr[resStr.length - 1] = newStr;
        return resStr;
    }

    public void addModifyUnitId(String unitId) {
        if (getModifyUnitIds() == null) {
            setModifyUnitIds(new String[] { unitId });
        }
        else {
            setModifyUnitIds(this.addArrayString(getModifyUnitIds(), unitId));
        }
    }

    public void addModifyUserId(String userId) {
        if (getModifyUserIds() == null) {
            setModifyUserIds(new String[] { userId });
        }
        else {
            setModifyUserIds(this.addArrayString(getModifyUserIds(), userId));
        }
    }

    public void addRegSuccUnitId(String unitId) {
        if (getRegSuccUnitIds() == null) {
            setRegSuccUnitIds(new String[] { unitId });
        }
        else {
            setRegSuccUnitIds(this.addArrayString(getRegSuccUnitIds(), unitId));
        }
    }

    public void addRegSuccUserId(String userId) {
        if (getRegSuccUserIds() == null) {
            setRegSuccUserIds(new String[] { userId });
        }
        else {
            setRegSuccUserIds(this.addArrayString(getRegSuccUserIds(), userId));
        }
    }

    public void addErrorUnitId(String unitId, String errorContent) {
        if (getErrorUnitIds() == null) {
            setErrorUnitIds(new String[] { unitId });
            setErrorContent(new String[] { errorContent });
        }
        else {
            setErrorUnitIds(this.addArrayString(getErrorUnitIds(), unitId));
            setErrorContent(this
                    .addArrayString(getErrorContent(), errorContent));
        }
    }

    public void addErrorUseId(String userId) {
        if (getErrorUserIds() == null) {
            setErrorUserIds(new String[] { userId });
        }
        else {
            setErrorUserIds(this.addArrayString(getErrorUserIds(), userId));
        }
    }

    public String[] getErrorContent() {
        return errorContent;
    }

    public void setErrorContent(String[] errorContent) {
        this.errorContent = errorContent;
    }

    public boolean hasError() {
        return errorUnitIds != null;
    }

    public String[] getSelectUnitIds() {
        return selectUnitIds;
    }

    public void setSelectUnitIds(String[] selectUnitIds) {
        this.selectUnitIds = selectUnitIds;
    }

    public void addSelectUnitId(String unitId) {
        if (getSelectUnitIds() == null) {
            setSelectUnitIds(new String[] { unitId });
        }
        else {
            setSelectUnitIds(this.addArrayString(getSelectUnitIds(), unitId));
        }
    }

    /**
     * 增加更新unionid单位及其unionid
     * 
     * @param ids
     */
    public void addUpdateUnionIdUnit(String[] ids) {
        if (updateUnionIds == null && updateUnionIdUnitIds == null) {
            this.updateUnionIdUnitIds = new String[] { ids[0] };
            this.updateUnionIds = new String[] { ids[1] };
        }
        else {
            this.updateUnionIdUnitIds = this.addArrayString(
                    updateUnionIdUnitIds, ids[0]);
            this.updateUnionIds = this.addArrayString(updateUnionIds, ids[1]);
        }
    }

    /**
     * 得到更新unionid单位及其unionid
     * 
     * @return
     */
    public String[][] findUpdateUnionIdUnit() {
        if (this.updateUnionIds == null || this.updateUnionIdUnitIds == null) {
            return null;
        }

        String[][] uu=new String[updateUnionIds.length][2];
        for (int i = 0; i < updateUnionIds.length; i++) {
            uu[i][0] = updateUnionIds[i];
            uu[i][1] = updateUnionIdUnitIds[i];
        }
        return uu;
    }

	public String[] getUpdateUnionIds() {
		return updateUnionIds;
	}

	public void setUpdateUnionIds(String[] updateUnionIds) {
		this.updateUnionIds = updateUnionIds;
	}

	public String[] getUpdateUnionIdUnitIds() {
		return updateUnionIdUnitIds;
	}

	public void setUpdateUnionIdUnitIds(String[] updateUnionIdUnitIds) {
		this.updateUnionIdUnitIds = updateUnionIdUnitIds;
	}

    
}
