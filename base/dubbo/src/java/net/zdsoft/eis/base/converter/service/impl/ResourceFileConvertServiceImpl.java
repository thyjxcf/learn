/* 
 * @(#)ResourceFileConvertServiceImpl.java    Created on Feb 13, 2012
 * Copyright (c) 2012 ZDSoft Networks, Inc. All rights reserved.
 * $Id$
 */
package net.zdsoft.eis.base.converter.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.zdsoft.eis.base.common.service.SystemIniService;
import net.zdsoft.eis.base.converter.asynchronous.AsynchronousService;
import net.zdsoft.eis.base.converter.asynchronous.EisCallable;
import net.zdsoft.eis.base.converter.entity.ConverterFile;
import net.zdsoft.eis.base.converter.entity.ConverterProperties;
import net.zdsoft.eis.base.converter.service.ConverterFileService;
import net.zdsoft.eis.base.converter.service.ConverterFileTypeService;
import net.zdsoft.eis.base.converter.service.DocumentConverterService;
import net.zdsoft.eis.base.converter.service.VideoConverterService;
import net.zdsoft.eis.base.converter.util.StorePathUtil;
import net.zdsoft.keel.action.Reply;
import net.zdsoft.keel.util.FileUtils;
import net.zdsoft.keel.util.Pagination;
import net.zdsoft.leadin.common.entity.BusinessTask;
import net.zdsoft.leadin.common.service.BusinessTaskService;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhaosf
 * @version $Revision: 1.0 $, $Date: Feb 13, 2012 2:28:23 PM $
 * 
 * 资源文件转换 
 */
public class ResourceFileConvertServiceImpl implements BusinessTaskService {   
    private SystemIniService systemIniService;
    private ConverterFileTypeService converterFileTypeService;
    private ConverterFileService converterFileService;
    private DocumentConverterService documentConverterService;
    private VideoConverterService videoConverterService;

	public void setSystemIniService(SystemIniService systemIniService) {
		this.systemIniService = systemIniService;
	}

	public void setConverterFileTypeService(ConverterFileTypeService converterFileTypeService) {
		this.converterFileTypeService = converterFileTypeService;
	}

	public void setConverterFileService(ConverterFileService converterFileService) {
		this.converterFileService = converterFileService;
	}

	public void setDocumentConverterService(DocumentConverterService documentConverterService) {
		this.documentConverterService = documentConverterService;
	}

	public void setVideoConverterService(VideoConverterService videoConverterService) {
		this.videoConverterService = videoConverterService;
	}

	
	public int getConcurrentcyNum() {
        return 1;
    }

    public String getStartSign() {
        return "eis.fileconvert.start";
    }

    public String getChineseTaskName() {
        return "资源文件类型转换";
    }

    public String getTaskName() {
        return "eis-fileconvert";
    }
    
	@Override
	public String getSchedulerTokenCode() {
		return "eis.resource.file";
	}
	
    /**
     * 开始转换任务
     */
    @Override
    public void saveHandleBusinessTask(BusinessTask job, Reply reply) throws Exception {
        ConverterFile task = (ConverterFile)job;
   
        String fileType = task.getFileType();
        if (StringUtils.isEmpty(fileType)) {
            fileType = FileUtils.getExtension(task.getFilePath());
        }

        if (StringUtils.isNotBlank(fileType)){
            fileType = fileType.toLowerCase();
        }
        String sourcePath = task.getFilePath();
        if (StringUtils.isNotEmpty(sourcePath)) {
            File file = StorePathUtil.getStoreFile(sourcePath);
            sourcePath = file.getAbsolutePath();
            if(!(file.exists())){
                throw new FileNotFoundException("文件不存在");
            }

            ConverterProperties converterProperties = task.getProperties();
            if (converterFileTypeService.isDocument(fileType)) {
                int[] size = getDocumentPreviewPictureSize(converterProperties);
                //是否需要添加水印
                boolean isAddMark = isAddMark(task.getResourceType());
                //文档转换
                documentConverterService.conver(sourcePath, isAddMark, size[0], size[1]);
                
            } else if (converterFileTypeService.isVideo(fileType)) {
                int[] size = getVideoPreviewPictureSize(converterProperties);
                //视频转换
                videoConverterService.conver(sourcePath, size[0], size[1]);
                
            } else {
                throw new Exception("文件转换不支持该格式");
            }
        } else {
            throw new FileNotFoundException("文件为空");
        }
        
        // 更新记录
        task.setStatus(BusinessTask.TASK_STATUS_SUCCESS);
        task.setJobEndTime(new Date());
        task.setResultMsg("转换成功");
        updateJobFinished(task);
            
  
    }

    /**
     * 取文档预览图大小
     * @return
     */
	protected int[] getDocumentPreviewPictureSize(ConverterProperties converterPropertie) {
		int width = converterPropertie.getDocumentPreviewWidth();
		int height = converterPropertie.getDocumentPreviewHeight();
		return new int[] { width, height };
	}
    
	/**
	 * 取视频预览图大小
	 * @return
	 */
	protected int[] getVideoPreviewPictureSize(ConverterProperties converterPropertie) {
		int width = converterPropertie.getVidoePreviewWidth();
		int height = converterPropertie.getVidoePreviewHeight();
		return new int[] { width, height };
	}

    @Override
    public boolean isControlOneDataByUnitPerConcurrent() {
        return false;
    }
    
    /**
     * 获取下一条转换任务
     */
    @Override
    public List<BusinessTask> findNextNoHandJobs(int n) {
     	//获取转换资源
    	String[] extNames = converterFileTypeService.getExtNames();
    	Pagination page = getPage(n);
     	List<ConverterFile> list = converterFileService.findNextNoHandJobs(page, extNames, BusinessTask.TASK_STATUS_NO_HAND);   
     	     	
		List<BusinessTask> tasks = new ArrayList<BusinessTask>();
		for (ConverterFile file : list) {
			tasks.add(file);
		}
		return tasks;
    }
 
    /**
     * 完成后更新时间
     */
    @Override
	public void updateJobFinished(final BusinessTask job) {
        //使用异步，重新开启线程，为了与前面的不使用同一个connection。
        //因为转换时间过长，connection过期时，更新状态不能使用
        AsynchronousService.submit(new EisCallable("转换服务更新状态") {
            @Override
            public Object run() throws Exception { 
                converterFileService.updateJobFinished((ConverterFile)job);
                return null;
            }
        });
    }

    @Override
    public void updateJobNoHand(int resetTime) {
       	converterFileService.updateJobInHand2NoHand(resetTime);
    }
    /**
     * 更新运行时间
     */
	@Override
	public void updateRunTime(int status, Date jobRunTime, BusinessTask job) {
		converterFileService.updateRunTime(status, jobRunTime, (ConverterFile)job);
	}
	
	/**
	 * 获取默认分页
	 * @return
	 */
	private Pagination getPage(int n){
		Pagination page = new Pagination(n, false);
		page.setPageIndex(1);
		page.setMaxRowCount(0);
		page.initialize();
		return page;
	}

	/**
	 * 是否需要添加水印
	 * @param type
	 * @return
	 */
	private Boolean isAddMark(String type){
		if (StringUtils.isEmpty(type))
			return false;
		
    	String addMarkResourceType = systemIniService.getValue("SYSTEM.CONVERTOR.ADD.MARK.RESOURCE.TYPE");
    	boolean isAddMark = false;
    	if (StringUtils.isNotBlank(addMarkResourceType)) {
    		isAddMark = addMarkResourceType.indexOf("," + type + ",") > -1;
		}
    	return isAddMark;
	}
}
