/* 
 * @(#)TaskConstant.java    Created on Sep 20, 2009
 * Copyright (c) 2009 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.leadin.dataimport.common;

import java.util.HashMap;
import java.util.Map;

import net.zdsoft.leadin.common.entity.BusinessTask;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Sep 20, 2009 11:46:52 AM $
 */
public class DataImportConstants {
    /**
     * 任务状态
     */
    public static final int TASK_STATUS_NO_HAND = BusinessTask.TASK_STATUS_NO_HAND;// 待执行
    public static final int TASK_STATUS_IN_HAND = BusinessTask.TASK_STATUS_IN_HAND;// 正在执行
    public static final int TASK_STATUS_SUCCESS = BusinessTask.TASK_STATUS_SUCCESS;// 执行成功
    public static final int TASK_STATUS_ERROR = BusinessTask.TASK_STATUS_ERROR;// 执行失败
    public static final int TASK_STATUS_PRE_HAND = BusinessTask.TASK_STATUS_PRE_HAND;//预处理，和待执行和正在执行区分，以便于消息提示

    /**
     * 文件路径 后面跟导入文件类型，如student_info 等
     */
    public static final String FILE_PATH_IMPORT = "import_data";// 导入文件
    public static final String FILE_PATH_ERROR = "import_error";// 错误文件
    public static final String FILE_PATH_EXPORT_TEMPLATE = "export_template";// 导出模板文件

    /**
     * 文件类型
     */
    public static final String FILE_TYPE_XLSX = "xlsx";
    public static final String FILE_TYPE_XLS = "xls";
    public static final String FILE_TYPE_ZIP = "zip";
    public static final String FILE_TYPE_DBF = "dbf";

    public static Map<String, String[]> fileTypeMap = new HashMap<String, String[]>();
    static {
        fileTypeMap.put(FILE_TYPE_XLSX, new String[] { FILE_TYPE_XLS, FILE_TYPE_XLSX });
        fileTypeMap.put(FILE_TYPE_XLS, new String[] { FILE_TYPE_XLS, FILE_TYPE_XLSX });
        fileTypeMap.put(FILE_TYPE_ZIP, new String[] { FILE_TYPE_ZIP });
        fileTypeMap.put(FILE_TYPE_DBF, new String[] { FILE_TYPE_DBF });
    }

    /**
     * 执行状态
     */
    public static final String STATUS_END = "status_end";
    public static final String STATUS_FILE_UPLOAD_SUCCESS = "status_file_upload_success";

    public static final int DYNAMIC_FIELD_DISPLAY_ORDER_INIT = 100;// 动态字段显示顺序号初始值
    
    
    
    /**
     * 导入类型2
     * 导入到临时表中，在导入历史中查看单个记录，可以对该操作导入的数据进行操作后再导入正式表中。
     */
    public static final String IMPORT_TYPE_TEMP_2 = "2";
    
    

}
