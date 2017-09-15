/* 
 * @(#)StorageDir.java    Created on Dec 6, 2010
 * Copyright (c) 2010 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.data.action;

import java.util.List;
import java.util.Map;

import net.zdsoft.eis.base.common.entity.StorageDir;
import net.zdsoft.eis.base.common.entity.StorageDir.StorageDirType;
import net.zdsoft.eis.base.data.service.BaseStorageDirService;
import net.zdsoft.eis.frame.action.BaseAction;
import org.apache.commons.lang.StringUtils;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 存储目录
 * 
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Dec 6, 2010 11:04:42 AM $
 */
public class StorageDirAction extends BaseAction implements ModelDriven<StorageDir>{
    private static final long serialVersionUID = -8253213800103400007L;

    private BaseStorageDirService baseStorageDirService;

    private List<StorageDir> storageDirs;
    private String[] ids;
    private StorageDir storageDir = new StorageDir();
    
    private String jsonString;
    private String jsonError;
    
    public String execute() throws Exception {
        storageDirs = baseStorageDirService.findStorageDirs();
        return SUCCESS;
    }

    public String detail() {
        if (null == storageDir || StringUtils.isBlank(storageDir.getId())) {
            storageDir = new StorageDir();
        } else {
            storageDir = baseStorageDirService.getStorageDir(storageDir.getId());
        }

        return SUCCESS;
    }

    public String remoteSave() throws Exception {
//    	JSONObject json = JSONObject.fromObject(jsonString);
//    	storageDir = (StorageDir) JSONObject.toBean(json, StorageDir.class);
    	try{
    		promptMessageDto = baseStorageDirService.saveStorageDir(storageDir);
    	}catch(Exception e){
//    		jsonError = e.getMessage();
    		promptMessageDto.setErrorMessage(e.getMessage());
    	}
        return SUCCESS;
    }

    /**
     * @deprecated
     * @return
     */
    public String remoteDelete() {
        try {
            Map<String, StorageDir> dirs = baseStorageDirService.getStorageDirs(new String[]{jsonString});
            for (StorageDir dir : dirs.values()) {
                if (!dir.isPreset()) {
                	baseStorageDirService.deleteStorageDir(new String[]{jsonString});
                }
                else{
                	jsonError = "此目录存在下级目录，不能删除！";
                }
            }
        } catch (Exception e) {
        	jsonError = e.getMessage();
        	
        }
        return SUCCESS;
    }
    public String delete() {
    	 try {
             Map<String, StorageDir> dirs = baseStorageDirService.getStorageDirs(new String[]{jsonString});
             for (StorageDir dir : dirs.values()) {
                 if (!dir.isPreset()) {
                 	baseStorageDirService.deleteStorageDir(new String[]{jsonString});
                 }
                 else{
                 	jsonError = "此目录存在下级目录，不能删除！";
                 }
             }
         } catch (Exception e) {
         	jsonError = e.getMessage();
         	
         }
         return SUCCESS;
    }
    public StorageDirType[] getDirTypes(){
        StorageDirType[] types = StorageDirType.values();
        return types;
    }
    
    public List<StorageDir> getStorageDirs() {
        return storageDirs;
    }

    public StorageDir getStorageDir() {
        return storageDir;
    }

    public void setStorageDir(StorageDir storageDir) {
        this.storageDir = storageDir;
    }

    public void setBaseStorageDirService(BaseStorageDirService baseStorageDirService) {
        this.baseStorageDirService = baseStorageDirService;
    }

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getJsonError() {
		return jsonError;
	}

	public void setJsonError(String jsonError) {
		this.jsonError = jsonError;
	}

	public StorageDir getModel() {
		return storageDir;
	}
}
